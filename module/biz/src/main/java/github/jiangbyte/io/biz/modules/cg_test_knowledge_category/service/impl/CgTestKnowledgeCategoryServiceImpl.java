package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.common.mybatis.datasource.ReadDataSource;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.convert.CgTestKnowledgeCategoryConvert;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity.CgTestKnowledgeCategory;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.mapper.CgTestKnowledgeCategoryMapper;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeCategoryAddParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeCategoryEditParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeCategoryPageParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.service.CgTestKnowledgeCategoryService;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.convert.CgTestKnowledgeDocConvert;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity.CgTestKnowledgeDoc;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.mapper.CgTestKnowledgeDocMapper;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeDocAddParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeDocEditParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeDocPageParam;
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
 * {@link github.jiangbyte.io.biz.modules.cg_test_knowledge_category.service.CgTestKnowledgeCategoryService} 实现：分类与文档持久化、分页与树构建。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class CgTestKnowledgeCategoryServiceImpl extends ServiceImpl<CgTestKnowledgeCategoryMapper, CgTestKnowledgeCategory> implements CgTestKnowledgeCategoryService {

    private final CgTestKnowledgeCategoryConvert cgTestKnowledgeCategoryConvert;
    private final CgTestKnowledgeDocMapper cgTestKnowledgeDocMapper;
    private final CgTestKnowledgeDocConvert cgTestKnowledgeDocConvert;

    @Override
    @Transactional
    public void create(CgTestKnowledgeCategoryAddParam param) {
        // 参数转实体后保存
        CgTestKnowledgeCategory entity = cgTestKnowledgeCategoryConvert.toEntity(param);
        this.save(entity);
    }

    @Override
    @Transactional
    public void update(CgTestKnowledgeCategoryEditParam param) {
        // 加载实体；不存在则 404
        // 覆盖字段后更新
        CgTestKnowledgeCategory entity = this.getById(param.getId());
        if (entity == null) {
            throw new BizException(404, "CgTestKnowledgeCategory not found");
        }
        cgTestKnowledgeCategoryConvert.update(param, entity);
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
    public CgTestKnowledgeCategory detail(String id) {
        // 按 ID 查询，不存在则 404
        CgTestKnowledgeCategory entity = this.getById(id);
        if (entity == null) {
            throw new BizException(404, "CgTestKnowledgeCategory not found");
        }
        return entity;
    }

    @Override
    @ReadDataSource
    public Page<CgTestKnowledgeCategory> page(CgTestKnowledgeCategoryPageParam param) {
        // 按编码/名称/状态分页查询
        return this.page(new Page<>(param.getCurrent(), param.getSize()),
                Wrappers.<CgTestKnowledgeCategory>lambdaQuery()
                        .like(StringUtils.hasText(param.getCode()), CgTestKnowledgeCategory::getCode, param.getCode())
                        .like(StringUtils.hasText(param.getName()), CgTestKnowledgeCategory::getName, param.getName())
                        .eq(param.getStatus() != null && StringUtils.hasText(param.getStatus()), CgTestKnowledgeCategory::getStatus, param.getStatus())
                        .orderByDesc(CgTestKnowledgeCategory::getCreatedAt));
    }

    @Override
    @ReadDataSource
    public List<Tree<String>> tree(String keyword) {
        // 按关键字查询分类列表
        // 缺失父节点时挂到根并构建树
        List<CgTestKnowledgeCategory> rows = this.list(Wrappers.<CgTestKnowledgeCategory>lambdaQuery()
                .like(StringUtils.hasText(keyword), CgTestKnowledgeCategory::getName, keyword)
                .orderByAsc(CgTestKnowledgeCategory::getCreatedAt));
        if (rows.isEmpty()) {
            return List.of();
        }

        Set<String> ids = rows.stream().map(CgTestKnowledgeCategory::getId).collect(Collectors.toSet());
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

    @Override
    @Transactional
    public void childCreate(CgTestKnowledgeDocAddParam param) {
        // 文档参数转实体后插入
        CgTestKnowledgeDoc entity = cgTestKnowledgeDocConvert.toEntity(param);
        cgTestKnowledgeDocMapper.insert(entity);
    }

    @Override
    @Transactional
    public void childUpdate(CgTestKnowledgeDocEditParam param) {
        // 加载文档；不存在则 404
        // 覆盖字段后更新
        CgTestKnowledgeDoc entity = cgTestKnowledgeDocMapper.selectById(param.getId());
        if (entity == null) {
            throw new BizException(404, "CgTestKnowledgeDoc not found");
        }
        cgTestKnowledgeDocConvert.update(param, entity);
        cgTestKnowledgeDocMapper.updateById(entity);
    }

    @Override
    @Transactional
    public void childDelete(IdsParam param) {
        // 空列表直接返回；否则按 ID 删除文档
        if (param.getIds() == null || param.getIds().isEmpty()) {
            return;
        }
        cgTestKnowledgeDocMapper.deleteByIds(param.getIds());
    }

    @Override
    @ReadDataSource
    public CgTestKnowledgeDoc childDetail(String id) {
        // 按 ID 查询文档，不存在则 404
        CgTestKnowledgeDoc entity = cgTestKnowledgeDocMapper.selectById(id);
        if (entity == null) {
            throw new BizException(404, "CgTestKnowledgeDoc not found");
        }
        return entity;
    }

    @Override
    @ReadDataSource
    public Page<CgTestKnowledgeDoc> childPage(CgTestKnowledgeDocPageParam param) {
        // 按分类 ID 分页查询文档
        return cgTestKnowledgeDocMapper.selectPage(new Page<>(param.getCurrent(), param.getSize()),
                Wrappers.<CgTestKnowledgeDoc>lambdaQuery()
                        .eq(StringUtils.hasText(param.getCategoryId()), CgTestKnowledgeDoc::getCategoryId, param.getCategoryId())
                        .orderByDesc(CgTestKnowledgeDoc::getCreatedAt));
    }
}
