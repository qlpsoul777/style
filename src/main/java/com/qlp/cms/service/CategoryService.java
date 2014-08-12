package com.qlp.cms.service;

import com.qlp.cms.entity.Category;
import com.qlp.commons.entity.TreeNode;

import java.util.List;

/**
 * Created by qlp on 2014/6/20.
 */
public interface CategoryService {

    Category get(String id);

    Category save(Category category);

    List<TreeNode> findAllCategory(String siteId);

    List<Category> findChildren(String categoryId);
}
