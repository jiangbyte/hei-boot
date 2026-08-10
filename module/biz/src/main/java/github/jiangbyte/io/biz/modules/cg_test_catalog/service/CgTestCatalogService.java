package github.jiangbyte.io.biz.modules.cg_test_catalog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.biz.modules.cg_test_catalog.entity.CgTestCatalog;
import github.jiangbyte.io.biz.modules.cg_test_catalog.param.CgTestCatalogAddParam;
import github.jiangbyte.io.biz.modules.cg_test_catalog.param.CgTestCatalogEditParam;
import github.jiangbyte.io.biz.modules.cg_test_catalog.param.CgTestCatalogPageParam;
import cn.hutool.core.lang.tree.Tree;

import java.util.List;

/**
 * 测试目录领域服务：目录 CRUD、分页与按关键字构建树。
 *
 * Author: Charlie
 */
public interface CgTestCatalogService extends IService<CgTestCatalog> {

    /**
     * 创建测试目录节点。
     */
    void create(CgTestCatalogAddParam param);

    /**
     * 更新测试目录；不存在则 404。
     */
    void update(CgTestCatalogEditParam param);

    /**
     * 按 ID 列表批量删除目录。
     */
    void delete(IdsParam param);

    /**
     * 按 ID 查询目录详情。
     */
    CgTestCatalog detail(String id);

    /**
     * 按编码/名称/分类/状态分页查询目录。
     */
    Page<CgTestCatalog> page(CgTestCatalogPageParam param);

    /**
     * 按关键字查询并构建目录树。
     */
    List<Tree<String>> tree(String keyword);
}
