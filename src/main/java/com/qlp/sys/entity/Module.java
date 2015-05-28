package com.qlp.sys.entity;

import com.qlp.commons.entity.TopEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qlp on 2014/12/15.
 * 功能模块
 */
@Entity
@Table(name = "com_qlp_sys_module")
public class Module extends TopEntity{

    private String name;   //模块名
    private String permission;  //权限关键字
    private String url;  //链接地址
    private String description;  //描述

    private int sort;  //排序字段

    private boolean display = Boolean.TRUE;  //是否可见
    private boolean enable = Boolean.TRUE;  //是否启用

    private Module parent;  //  父模块

    private List<Module> children = new ArrayList<Module>();  //子模块

    @Column(length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 30)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Column(length = 30)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(length = 50)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    public Module getParent() {
        return parent;
    }

    public void setParent(Module parent) {
        this.parent = parent;
    }

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Module.class, cascade = CascadeType.ALL, mappedBy = "parent")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("sort desc")
    public List<Module> getChildren() {
        return children;
    }

    public void setChildren(List<Module> children) {
        this.children = children;
    }
}
