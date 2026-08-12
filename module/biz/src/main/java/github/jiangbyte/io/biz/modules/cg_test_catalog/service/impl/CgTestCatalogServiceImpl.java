package github.jiangbyte.io.biz.modules.cg_test_catalog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.common.mybatis.datasource.ReadDataSource;
import github.jiangbyte.io.common.mybatis.datascope.OwnerDeptDataScope;
import github.jiangbyte.io.common.security.datascope.DataScopeConstraint;
import github.jiangbyte.io.biz.modules.cg_test_catalog.convert.CgTestCatalogConvert;
import github.jiangbyte.io.biz.modules.cg_test_catalog.entity.CgTestCatalog;
import github.jiangbyte.io.biz.modules.cg_test_catalog.mapper.CgTestCatalogMapper;
import github.jiangbyte.io.biz.modules.cg_test_catalog.param.CgTestCatalogAddParam;
import github.jiangbyte.io.biz.modules.cg_test_catalog.param.CgTestCatalogEditParam;
import github.jiangbyte.io.biz.modules.cg_test_catalog.param.CgTestCatalogPageParam;
import github.jiangbyte.io.biz.modules.cg_test_catalog.service.CgTestCatalogService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link github.jiangbyte.io.biz.modules.cg_test_catalog.service.CgTestCatalogService} 实现：目录持久化、分页与 Hutool 树构建。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class CgTestCatalogServiceImpl extends ServiceImpl<CgTestCatalogMapper, CgTestCatalog> implements CgTestCatalogService {

    private static final String PERMISSION_KEY = "biz:cgtestcatalog:page";

    private final CgTestCatalogConvert cgTestCatalogConvert;
    private final OwnerDeptDataScope ownerDeptDataScope;

    @Override
    @Transactional
    public void create(CgTestCatalogAddParam param) {
        // 参数转实体后保存
        CgTestCatalog entity = cgTestCatalogConvert.toEntity(param);
        this.save(entity);
    }

    @Override
    @Transactional
    public void update(CgTestCatalogEditParam param) {
        // 加载实体；不存在则 404
        // 覆盖字段后更新
        CgTestCatalog entity = this.getById(param.getId());
        if (entity == null) {
            throw new BizException(404, "CgTestCatalog not found");
        }
        ownerDeptDataScope.assertAccessible(entity.getCreatedBy(), entity.getOwnerDeptId(), PERMISSION_KEY);
        cgTestCatalogConvert.update(param, entity);
        this.updateById(entity);
    }

    @Override
    @Transactional
    public void delete(IdsParam param) {
        // 空列表直接返回；否则按 ID 删除
        if (param.getIds() == null || param.getIds().isEmpty()) {
            return;
        }
        List<CgTestCatalog> entities = this.listByIds(param.getIds());
        DataScopeConstraint scope = ownerDeptDataScope.resolve(PERMISSION_KEY);
        for (CgTestCatalog entity : entities) {
            ownerDeptDataScope.assertAccessible(entity.getCreatedBy(), entity.getOwnerDeptId(), scope);
        }
        this.removeByIds(param.getIds());
    }

    @Override
    @ReadDataSource
    public CgTestCatalog detail(String id) {
        // 按 ID 查询，不存在则 404
        CgTestCatalog entity = this.getById(id);
        if (entity == null) {
            throw new BizException(404, "CgTestCatalog not found");
        }
        ownerDeptDataScope.assertAccessible(entity.getCreatedBy(), entity.getOwnerDeptId(), PERMISSION_KEY);
        return entity;
    }

    @Override
    @ReadDataSource
    public Page<CgTestCatalog> page(CgTestCatalogPageParam param) {
        // 按编码/名称等条件分页查询
        LambdaQueryWrapper<CgTestCatalog> wrapper = Wrappers.<CgTestCatalog>lambdaQuery()
                .like(StringUtils.hasText(param.getCode()), CgTestCatalog::getCode, param.getCode())
                .like(StringUtils.hasText(param.getName()), CgTestCatalog::getName, param.getName())
                .like(StringUtils.hasText(param.getCategory()), CgTestCatalog::getCategory, param.getCategory())
                .eq(param.getStatus() != null && StringUtils.hasText(param.getStatus()), CgTestCatalog::getStatus, param.getStatus())
                .orderByDesc(CgTestCatalog::getCreatedAt);
        ownerDeptDataScope.apply(wrapper, PERMISSION_KEY, CgTestCatalog::getCreatedBy, CgTestCatalog::getOwnerDeptId);
        return this.page(new Page<>(param.getCurrent(), param.getSize()), wrapper);
    }

    @Override
    @ReadDataSource
    public List<Tree<String>> tree(String keyword) {
        // 按关键字查询目录列表
        // 缺失父节点时挂到根并构建树
        List<CgTestCatalog> rows = this.list(Wrappers.<CgTestCatalog>lambdaQuery()
                .like(StringUtils.hasText(keyword), CgTestCatalog::getName, keyword)
                .orderByAsc(CgTestCatalog::getCreatedAt));
        if (rows.isEmpty()) {
            return List.of();
        }

        Set<String> ids = rows.stream().map(CgTestCatalog::getId).collect(Collectors.toSet());
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("id");
        config.setParentIdKey("parent_id");
        config.setNameKey("name");
        config.setWeightKey("weight");
        config.setChildrenKey("children");
        return TreeUtil.build(rows, null, config, (row, tree) -> {
            String parentId = row.getParentId();
            if (!StringUtils.hasText(parentId) || !ids.contains(parentId)) {
                parentId = null;
            }
            BeanUtil.beanToMap(row, false, true).forEach((key, value) -> {
                if (!"children".equals(key)) {
                    tree.putExtra(StrUtil.toUnderlineCase(key), value);
                }
            });
            tree.setId(row.getId());
            tree.setParentId(parentId);
            tree.setName(row.getName());
            tree.setWeight(0);
        });
    }
}
