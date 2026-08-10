package github.jiangbyte.io.biz.modules.cg_test_activity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.biz.modules.cg_test_activity.entity.CgTestActivity;
import github.jiangbyte.io.biz.modules.cg_test_activity.param.CgTestActivityAddParam;
import github.jiangbyte.io.biz.modules.cg_test_activity.param.CgTestActivityEditParam;
import github.jiangbyte.io.biz.modules.cg_test_activity.param.CgTestActivityPageParam;

import java.util.List;

/**
 * 测试活动领域服务：活动的增删改查与条件分页。
 *
 * Author: Charlie
 */
public interface CgTestActivityService extends IService<CgTestActivity> {

    /**
     * 创建测试活动。
     */
    void create(CgTestActivityAddParam param);

    /**
     * 更新测试活动；不存在则 404。
     */
    void update(CgTestActivityEditParam param);

    /**
     * 按 ID 列表批量删除活动。
     */
    void delete(IdsParam param);

    /**
     * 按 ID 查询活动详情。
     */
    CgTestActivity detail(String id);

    /**
     * 按编码/名称/分类/类型/状态分页查询活动。
     */
    Page<CgTestActivity> page(CgTestActivityPageParam param);
}
