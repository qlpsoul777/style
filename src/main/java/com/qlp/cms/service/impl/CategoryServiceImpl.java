package com.qlp.cms.service.impl;

import com.qlp.cms.dao.CategoryDao;
import com.qlp.cms.dao.SiteDao;
import com.qlp.cms.entity.Category;
import com.qlp.cms.entity.Site;
import com.qlp.cms.service.CategoryService;
import com.qlp.commons.entity.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qlp on 2014/6/20.
 */
@Component
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private SiteDao siteDao;

    public Category get(String id) {
        return categoryDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public Category save(Category category) {
        return categoryDao.saveAndFlush(category);
    }

    public List<TreeNode> findAllCategory(String siteId) {
        List<Category> categories = categoryDao.findBySiteIdAndParentCategoryIdIsNull(siteId);
        Site site = siteDao.findOne(siteId);
        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(new TreeNode("root",site.getcName(),getChildren(categories)));
        return nodes;
    }

    @Override
    public List<Category> findChildren(String categoryId) {
        return categoryDao.findByPid(categoryId);
    }

    private List<TreeNode> getChildren(List<Category> childCategory) {
        List<TreeNode> nodes = new ArrayList<>();
        for(Category c: childCategory){
            TreeNode node = new TreeNode();
            node.setId(c.getId());
            node.setName(c.getName());
            List<Category> children = findChildren(c.getId());
            node.setChildren(getChildren(children));
            nodes.add(node);
        }
        return nodes;
    }
}
