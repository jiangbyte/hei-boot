package github.jiangbyte.io.biz.modules.cg_test_activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.common.mybatis.datasource.ReadDataSource;
import github.jiangbyte.io.common.mybatis.datascope.OwnerDeptDataScope;
import github.jiangbyte.io.common.security.datascope.DataScopeConstraint;
import github.jiangbyte.io.biz.modules.cg_test_activity.convert.CgTestActivityConvert;
import github.jiangbyte.io.biz.modules.cg_test_activity.entity.CgTestActivity;
import github.jiangbyte.io.biz.modules.cg_test_activity.mapper.CgTestActivityMapper;
import github.jiangbyte.io.biz.modules.cg_test_activity.param.CgTestActivityAddParam;
import github.jiangbyte.io.biz.modules.cg_test_activity.param.CgTestActivityEditParam;
import github.jiangbyte.io.biz.modules.cg_test_activity.param.CgTestActivityPageParam;
import github.jiangbyte.io.biz.modules.cg_test_activity.service.CgTestActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * {@link github.jiangbyte.io.biz.modules.cg_test_activity.service.CgTestActivityService} 实现：活动实体转换持久化与条件分页查询。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class CgTestActivityServiceImpl extends ServiceImpl<CgTestActivityMapper, CgTestActivity> implements CgTestActivityService {

    private static final String PERMISSION_KEY = "biz:cgtestactivity:page";

    private final CgTestActivityConvert cgTestActivityConvert;
    private final OwnerDeptDataScope ownerDeptDataScope;

    @Override
    @Transactional
    public void create(CgTestActivityAddParam param) {
        // 参数转实体后保存
        CgTestActivity entity = cgTestActivityConvert.toEntity(param);
        this.save(entity);
    }

    @Override
    @Transactional
    public void update(CgTestActivityEditParam param) {
        // 加载实体；不存在则 404
        // 覆盖字段后更新
        CgTestActivity entity = this.getById(param.getId());
        if (entity == null) {
            throw new BizException(404, "CgTestActivity not found");
        }
        ownerDeptDataScope.assertAccessible(entity.getCreatedBy(), entity.getOwnerDeptId(), PERMISSION_KEY);
        cgTestActivityConvert.update(param, entity);
        this.updateById(entity);
    }

    @Override
    @Transactional
    public void delete(IdsParam param) {
        // 空列表直接返回；否则按 ID 删除
        if (param.getIds() == null || param.getIds().isEmpty()) {
            return;
        }
        List<CgTestActivity> entities = this.listByIds(param.getIds());
        DataScopeConstraint scope = ownerDeptDataScope.resolve(PERMISSION_KEY);
        for (CgTestActivity entity : entities) {
            ownerDeptDataScope.assertAccessible(entity.getCreatedBy(), entity.getOwnerDeptId(), scope);
        }
        this.removeByIds(param.getIds());
    }

    @Override
    @ReadDataSource
    public CgTestActivity detail(String id) {
        // 按 ID 查询，不存在则 404
        CgTestActivity entity = this.getById(id);
        if (entity == null) {
            throw new BizException(404, "CgTestActivity not found");
        }
        ownerDeptDataScope.assertAccessible(entity.getCreatedBy(), entity.getOwnerDeptId(), PERMISSION_KEY);
        return entity;
    }

    @Override
    @ReadDataSource
    public Page<CgTestActivity> page(CgTestActivityPageParam param) {
        // 按编码/名称等条件分页查询
        LambdaQueryWrapper<CgTestActivity> wrapper = Wrappers.<CgTestActivity>lambdaQuery()
                .like(StringUtils.hasText(param.getCode()), CgTestActivity::getCode, param.getCode())
                .like(StringUtils.hasText(param.getName()), CgTestActivity::getName, param.getName())
                .like(StringUtils.hasText(param.getCategory()), CgTestActivity::getCategory, param.getCategory())
                .like(StringUtils.hasText(param.getType()), CgTestActivity::getType, param.getType())
                .eq(param.getStatus() != null && StringUtils.hasText(param.getStatus()), CgTestActivity::getStatus, param.getStatus())
                .orderByDesc(CgTestActivity::getCreatedAt);
        ownerDeptDataScope.apply(wrapper, PERMISSION_KEY, CgTestActivity::getCreatedBy, CgTestActivity::getOwnerDeptId);
        return this.page(new Page<>(param.getCurrent(), param.getSize()), wrapper);
    }
}
