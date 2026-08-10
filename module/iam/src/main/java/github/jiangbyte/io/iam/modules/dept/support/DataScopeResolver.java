package github.jiangbyte.io.iam.modules.dept.support;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import github.jiangbyte.io.common.security.datascope.DataScopeConstraint;
import github.jiangbyte.io.common.security.datascope.DataScopeSupport;
import github.jiangbyte.io.iam.modules.dept.entity.SysDept;
import github.jiangbyte.io.iam.modules.dept.mapper.SysDeptMapper;
import github.jiangbyte.io.iam.modules.relation.constants.IamRelationTypes;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

/**
 * 数据权限解析器：将当前登录范围转为查询约束，
 * 并支持负责人/部门列过滤与部门子树展开。
 *
 * Author: Charlie
 */
@Component
@RequiredArgsConstructor
public class DataScopeResolver {

    private final SysDeptMapper deptMapper;

    /** 解析当前登录用户对权限键的数据范围约束。 */
    public DataScopeConstraint resolve(String permissionKey) {
        return DataScopeSupport.resolveCurrent(permissionKey, this::listDeptAndChildIds);
    }

    /**
     * 应用负责人/部门列数据权限（角色、用户组、岗位、部门）。
     * @param wrapper 查询包装器
     * @param permissionKey 权限键
     * @param ownerColumn 负责人列
     * @param deptColumn 部门列
     */
    public <T> void applyOwnerOrDept(
            LambdaQueryWrapper<T> wrapper,
            String permissionKey,
            SFunction<T, ?> ownerColumn,
            SFunction<T, ?> deptColumn) {
        // 1. 解析当前权限键的数据范围
        DataScopeConstraint constraint = resolve(permissionKey);
        // 2. 按范围类型写入负责人/部门过滤
        switch (constraint) {
            case DataScopeConstraint.All ignored -> {
                // 全量：不加过滤
            }
            case DataScopeConstraint.Self self -> {
                // 仅本人：按负责人列过滤；无列则拒绝
                if (ownerColumn == null) {
                    // 无负责人列：恒假拒绝
                    deny(wrapper);
                } else {
                    wrapper.eq(ownerColumn, self.accountId());
                }
            }
            case DataScopeConstraint.Depts depts -> {
                // 部门范围：按部门列 IN；无列或空集则拒绝
                if (deptColumn == null || depts.deptIds().isEmpty()) {
                    // 无部门列或空部门集：恒假拒绝
                    deny(wrapper);
                } else {
                    wrapper.in(deptColumn, depts.deptIds());
                }
            }
            case DataScopeConstraint.Deny ignored -> deny(wrapper);
        }
    }

    /** 账号分页/列表：SELF 按账号 id 过滤；部门范围经 ACCOUNT_DEPT 关系过滤。 */
    public <T> void applyAccountScope(
            LambdaQueryWrapper<T> wrapper,
            String permissionKey,
            SFunction<T, ?> accountIdColumn) {
        // 解析范围：ALL 不过滤；SELF 按账号 id；部门经 ACCOUNT_DEPT 子查询
        DataScopeConstraint constraint = resolve(permissionKey);
        switch (constraint) {
            case DataScopeConstraint.All ignored -> {
            }
            case DataScopeConstraint.Self self -> wrapper.eq(accountIdColumn, self.accountId());
            case DataScopeConstraint.Depts depts -> {
                if (depts.deptIds().isEmpty()) {
                    // 空部门集：恒假拒绝
                    deny(wrapper);
                } else {
                    String inList = depts.deptIds().stream()
                            .map(id -> "'" + id.replace("'", "''") + "'")
                            .collect(Collectors.joining(","));
                    wrapper.inSql(accountIdColumn,
                            "select subject_id from sys_iam_relation where subject_type = '"
                                    + IamRelationTypes.SUBJECT_ACCOUNT
                                    + "' and relation_type = '"
                                    + IamRelationTypes.ACCOUNT_DEPT
                                    + "' and target_type = '"
                                    + IamRelationTypes.TARGET_DEPT
                                    + "' and target_id in (" + inList + ")");
                }
            }
            case DataScopeConstraint.Deny ignored -> deny(wrapper);
        }
    }

    /** 展开部门及其全部子部门 id。 */
    public List<String> listDeptAndChildIds(Collection<String> rootDeptIds) {
        // 1. 空根直接返回
        if (rootDeptIds == null || rootDeptIds.isEmpty()) {
            return List.of();
        }
        // 2. 一次性加载父子索引
        List<SysDept> all = deptMapper.selectList(Wrappers.<SysDept>lambdaQuery()
                .select(SysDept::getId, SysDept::getParentId));
        Map<String, List<String>> childrenByParent = new HashMap<>();
        for (SysDept dept : all) {
            String parentId = dept.getParentId() == null ? "" : dept.getParentId();
            childrenByParent.computeIfAbsent(parentId, key -> new ArrayList<>()).add(dept.getId());
        }
        // 3. 栈式 BFS/DFS 展开子孙并去重
        Set<String> result = new HashSet<>();
        ArrayDeque<String> stack = new ArrayDeque<>();
        rootDeptIds.stream().filter(StringUtils::hasText).sorted().forEach(stack::push);
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!result.add(current)) {
                continue;
            }
            List<String> children = childrenByParent.getOrDefault(current, List.of());
            for (String child : children) {
                stack.push(child);
            }
        }
        // 4. 排序后返回稳定 id 列表
        return result.stream().sorted().collect(Collectors.toList());
    }

    /** 写入恒假条件以拒绝查询结果。 */
    private static <T> void deny(LambdaQueryWrapper<T> wrapper) {
        wrapper.apply("1 = 0");
    }
}
