package com.qlp.cms.service;

import com.qlp.cms.entity.Category;
import com.qlp.commons.entity.TreeNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by qlp on 2014/6/20.
 */
public interface CategoryService {

    Category get(String id);

    Category save(Category category);

    /**
     * 查询出站点下所有的栏目
     * @param siteId
     * @return
     */
    List<TreeNode> findAllCategory(String siteId);

    /**
     * 查询出当前栏目和栏目的子栏目的id
     * @param categoryId
     * @return
     */
    List<String> findChildrenId(String categoryId);

    Page<Category> findPageByMap(Map<String,Object> map,Pageable pageable);
}
