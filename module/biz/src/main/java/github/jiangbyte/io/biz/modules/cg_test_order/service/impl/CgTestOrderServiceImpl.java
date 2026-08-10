package github.jiangbyte.io.biz.modules.cg_test_order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.common.mybatis.datasource.ReadDataSource;
import github.jiangbyte.io.biz.modules.cg_test_order.convert.CgTestOrderConvert;
import github.jiangbyte.io.biz.modules.cg_test_order.entity.CgTestOrder;
import github.jiangbyte.io.biz.modules.cg_test_order.mapper.CgTestOrderMapper;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderAddParam;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderEditParam;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderPageParam;
import github.jiangbyte.io.biz.modules.cg_test_order.service.CgTestOrderService;
import github.jiangbyte.io.biz.modules.cg_test_order.convert.CgTestOrderItemConvert;
import github.jiangbyte.io.biz.modules.cg_test_order.entity.CgTestOrderItem;
import github.jiangbyte.io.biz.modules.cg_test_order.mapper.CgTestOrderItemMapper;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderItemAddParam;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderItemEditParam;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderItemPageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link github.jiangbyte.io.biz.modules.cg_test_order.service.CgTestOrderService} 实现：订单与明细持久化及条件分页查询。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class CgTestOrderServiceImpl extends ServiceImpl<CgTestOrderMapper, CgTestOrder> implements CgTestOrderService {

    private final CgTestOrderConvert cgTestOrderConvert;
    private final CgTestOrderItemMapper cgTestOrderItemMapper;
    private final CgTestOrderItemConvert cgTestOrderItemConvert;

    @Override
    @Transactional
    public void create(CgTestOrderAddParam param) {
        // 参数转实体后保存
        CgTestOrder entity = cgTestOrderConvert.toEntity(param);
        this.save(entity);
    }

    @Override
    @Transactional
    public void update(CgTestOrderEditParam param) {
        // 加载实体；不存在则 404
        // 覆盖字段后更新
        CgTestOrder entity = this.getById(param.getId());
        if (entity == null) {
            throw new BizException(404, "CgTestOrder not found");
        }
        cgTestOrderConvert.update(param, entity);
        this.updateById(entity);
    }

    @Override
    @Transactional
    public void delete(IdsParam param) {
        // 空列表直接返回；否则按 ID 删除
        if (param.getIds() == null || param.getIds().isEmpty()) {
            return;
        }
        this.removeByIds(param.getIds());
    }

    @Override
    @ReadDataSource
    public CgTestOrder detail(String id) {
        // 按 ID 查询，不存在则 404
        CgTestOrder entity = this.getById(id);
        if (entity == null) {
            throw new BizException(404, "CgTestOrder not found");
        }
        return entity;
    }

    @Override
    @ReadDataSource
    public Page<CgTestOrder> page(CgTestOrderPageParam param) {
        // 按订单号/客户等条件分页查询
        return this.page(new Page<>(param.getCurrent(), param.getSize()),
                Wrappers.<CgTestOrder>lambdaQuery()
                        .like(StringUtils.hasText(param.getOrderNo()), CgTestOrder::getOrderNo, param.getOrderNo())
                        .like(StringUtils.hasText(param.getName()), CgTestOrder::getName, param.getName())
                        .like(StringUtils.hasText(param.getCustomerName()), CgTestOrder::getCustomerName, param.getCustomerName())
                        .eq(param.getStatus() != null && StringUtils.hasText(param.getStatus()), CgTestOrder::getStatus, param.getStatus())
                        .like(StringUtils.hasText(param.getType()), CgTestOrder::getType, param.getType())
                        .orderByDesc(CgTestOrder::getCreatedAt));
    }

    @Override
    @Transactional
    public void childCreate(CgTestOrderItemAddParam param) {
        // 明细参数转实体后插入
        CgTestOrderItem entity = cgTestOrderItemConvert.toEntity(param);
        cgTestOrderItemMapper.insert(entity);
    }

    @Override
    @Transactional
    public void childUpdate(CgTestOrderItemEditParam param) {
        // 加载明细；不存在则 404
        // 覆盖字段后更新
        CgTestOrderItem entity = cgTestOrderItemMapper.selectById(param.getId());
        if (entity == null) {
            throw new BizException(404, "CgTestOrderItem not found");
        }
        cgTestOrderItemConvert.update(param, entity);
        cgTestOrderItemMapper.updateById(entity);
    }

    @Override
    @Transactional
    public void childDelete(IdsParam param) {
        // 空列表直接返回；否则按 ID 删除明细
        if (param.getIds() == null || param.getIds().isEmpty()) {
            return;
        }
        cgTestOrderItemMapper.deleteByIds(param.getIds());
    }

    @Override
    @ReadDataSource
    public CgTestOrderItem childDetail(String id) {
        // 按 ID 查询明细，不存在则 404
        CgTestOrderItem entity = cgTestOrderItemMapper.selectById(id);
        if (entity == null) {
            throw new BizException(404, "CgTestOrderItem not found");
        }
        return entity;
    }

    @Override
    @ReadDataSource
    public Page<CgTestOrderItem> childPage(CgTestOrderItemPageParam param) {
        // 按订单 ID 分页查询明细
        return cgTestOrderItemMapper.selectPage(new Page<>(param.getCurrent(), param.getSize()),
                Wrappers.<CgTestOrderItem>lambdaQuery()
                        .eq(StringUtils.hasText(param.getOrderId()), CgTestOrderItem::getOrderId, param.getOrderId())
                        .orderByDesc(CgTestOrderItem::getCreatedAt));
    }
}
