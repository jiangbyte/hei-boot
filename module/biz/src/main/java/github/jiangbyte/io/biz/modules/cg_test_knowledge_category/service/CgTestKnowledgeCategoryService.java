package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity.CgTestKnowledgeCategory;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeCategoryAddParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeCategoryEditParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeCategoryPageParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity.CgTestKnowledgeDoc;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeDocAddParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeDocEditParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeDocPageParam;
import cn.hutool.core.lang.tree.Tree;

import java.util.List;

/**
 * 知识分类领域服务：分类 CRUD/分页/树，以及子级知识文档 CRUD 与分页。
 *
 * Author: Charlie
 */
public interface CgTestKnowledgeCategoryService extends IService<CgTestKnowledgeCategory> {

    /**
     * 创建知识分类。
     */
    void create(CgTestKnowledgeCategoryAddParam param);

    /**
     * 更新知识分类；不存在则 404。
     */
    void update(CgTestKnowledgeCategoryEditParam param);

    /**
     * 按 ID 列表批量删除分类。
     */
    void delete(IdsParam param);

    /**
     * 按 ID 查询分类详情。
     */
    CgTestKnowledgeCategory detail(String id);

    /**
     * 按编码/名称/状态分页查询分类。
     */
    Page<CgTestKnowledgeCategory> page(CgTestKnowledgeCategoryPageParam param);

    /**
     * 按关键字查询并构建分类树。
     */
    List<Tree<String>> tree(String keyword);

    /**
     * 在分类下创建知识文档。
     */
    void childCreate(CgTestKnowledgeDocAddParam param);

    /**
     * 更新知识文档；不存在则 404。
     */
    void childUpdate(CgTestKnowledgeDocEditParam param);

    /**
     * 按 ID 列表批量删除知识文档。
     */
    void childDelete(IdsParam param);

    /**
     * 按 ID 查询知识文档详情。
     */
    CgTestKnowledgeDoc childDetail(String id);

    /**
     * 按分类 ID 分页查询知识文档。
     */
    Page<CgTestKnowledgeDoc> childPage(CgTestKnowledgeDocPageParam param);
}
