package com.qlp.cms.entity;

import com.qlp.commons.entity.TopEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * 新闻栏目
 * Created by qlp on 2014/6/20.
 */
@Entity
@Table(name = "com_qlp_style_cms_category")
public class Category extends TopEntity {

    private String name;  //栏目名称
    private String path;  //访问路径
    private String type;  //栏目类型
    private Integer sort = 0;  //排序字段,值越大排序越靠后
    private String visiable = "0";  //是否可见,默认为0(0:不可见;1:可见)
    private String author;  //创建人

    private Category parentCategory;  //父栏目
    private Site site;  //所属站点
    private List<Category> childCategory;  //子栏目

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisiable() {
        return visiable;
    }

    public void setVisiable(String visiable) {
        this.visiable = visiable;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Category.class, cascade = CascadeType.ALL, mappedBy = "parentCategory")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("sort desc")
    public List<Category> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(List<Category> childCategory) {
        this.childCategory = childCategory;
    }

    public interface Type {
        public static final String SIMPLE = "SIMPLE";  //普通栏目
        public static final String LINK = "LINK";  //链接栏目
    }
}
