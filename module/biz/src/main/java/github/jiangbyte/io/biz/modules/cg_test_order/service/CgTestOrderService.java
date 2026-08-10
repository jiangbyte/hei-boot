package github.jiangbyte.io.biz.modules.cg_test_order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.biz.modules.cg_test_order.entity.CgTestOrder;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderAddParam;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderEditParam;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderPageParam;
import github.jiangbyte.io.biz.modules.cg_test_order.entity.CgTestOrderItem;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderItemAddParam;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderItemEditParam;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderItemPageParam;

import java.util.List;

/**
 * 测试订单领域服务：订单 CRUD/分页，以及明细子资源 CRUD 与分页。
 *
 * Author: Charlie
 */
public interface CgTestOrderService extends IService<CgTestOrder> {

    /**
     * 创建测试订单。
     */
    void create(CgTestOrderAddParam param);

    /**
     * 更新测试订单；不存在则 404。
     */
    void update(CgTestOrderEditParam param);

    /**
     * 按 ID 列表批量删除订单。
     */
    void delete(IdsParam param);

    /**
     * 按 ID 查询订单详情。
     */
    CgTestOrder detail(String id);

    /**
     * 按订单号/名称/客户/状态/类型分页查询订单。
     */
    Page<CgTestOrder> page(CgTestOrderPageParam param);

    /**
     * 创建订单明细。
     */
    void childCreate(CgTestOrderItemAddParam param);

    /**
     * 更新订单明细；不存在则 404。
     */
    void childUpdate(CgTestOrderItemEditParam param);

    /**
     * 按 ID 列表批量删除订单明细。
     */
    void childDelete(IdsParam param);

    /**
     * 按 ID 查询订单明细详情。
     */
    CgTestOrderItem childDetail(String id);

    /**
     * 按订单 ID 分页查询明细。
     */
    Page<CgTestOrderItem> childPage(CgTestOrderItemPageParam param);
}
