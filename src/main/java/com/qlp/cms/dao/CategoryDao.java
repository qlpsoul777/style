package com.qlp.cms.dao;

import com.qlp.cms.entity.Category;
import com.qlp.commons.orm.QlpJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by qlp on 2014/6/20.
 */
public interface CategoryDao extends QlpJpaRepository<Category, String> {

    List<Category> findBySiteIdAndParentCategoryIdIsNull(String siteId);

    @Query("SELECT c FROM Category c where c.parentCategory.id = ?1 order by c.sort asc")
    List<Category> findByPid(String categoryId);

    @Query("SELECT distinct c FROM Category c where c.id in ?1 ")
    Page<Category> findByIds(List<String> ids,Pageable pageable);

}
