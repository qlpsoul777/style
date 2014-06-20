package com.qlp.cms.service;

import com.qlp.cms.entity.Category;

/**
 * Created by qlp on 2014/6/20.
 */
public interface CategoryService {

    Category get(String id);

    Category save(Category category);
}
