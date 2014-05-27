package com.qlp.user.entity;

import com.qlp.utils.ParameterUtils;

import javax.persistence.*;

/**
 * Created by qlp on 14-5-19.
 * 模块下的功能实体
 */
@Entity
@Table(name = "com_qlp_style_func")
public class Functions {

    private String id;  //主键
    private String name;  //名称
    private String description;  //描述
    private String uri;  //链接
    private String visiable = ParameterUtils.ENABLE;  //可见性，默认为可见
    private Integer sort;  //排序字段

    private Application application;  //关联模块

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVisiable() {
        return visiable;
    }

    public void setVisiable(String visiable) {
        this.visiable = visiable;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
