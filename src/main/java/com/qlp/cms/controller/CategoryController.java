package com.qlp.cms.controller;

import com.qlp.cms.entity.Category;
import com.qlp.cms.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 新闻栏目
 * Created by qlp on 2014/6/20.
 */
@Controller
@RequestMapping(value = "cms/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 做二次值绑定
     *
     * @param id
     * @return
     */
    @ModelAttribute("category")
    public Category getCategory(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return categoryService.get(id);
        } else {
            return new Category();
        }
    }
}
