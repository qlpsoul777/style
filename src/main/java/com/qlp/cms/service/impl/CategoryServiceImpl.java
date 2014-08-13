package com.qlp.cms.service.impl;

import com.qlp.cms.dao.CategoryDao;
import com.qlp.cms.dao.SiteDao;
import com.qlp.cms.entity.Category;
import com.qlp.cms.entity.Site;
import com.qlp.cms.service.CategoryService;
import com.qlp.commons.entity.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qlp on 2014/6/20.
 */
@Component
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

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

    /**
     * 查询出当前栏目和栏目的子栏目的id
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<String> findChildrenId(String categoryId) {
        List<String> ids = new ArrayList<>();
        Category category = get(categoryId);
        if(category != null){
            ids.add(category.getId());
            if(!category.getChildCategory().isEmpty()){
                ids.addAll(findAllChildren(category.getId()));
            }
        }else{
            logger.error("查询出当前栏目和栏目的子栏目的id方法中，根据传入的参数查询不到栏目");
        }
        return null;
    }

    public List<String> findAllChildren(String pid){
        List<String> ids = new ArrayList<>();
        List<Category> categories = findChildren(pid);
        if(!categories.isEmpty()){
            for(Category c : categories){
                if(!c.getChildCategory().isEmpty()){
                    ids.add(c.getId());
                }
            }
        }
        return ids;
    }

    public List<Category> findChildren(String categoryId) {
        return categoryDao.findByPid(categoryId);
    }

    @Override
    public Page<Category> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return categoryDao.queryPageByMap(map,pageable);
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
