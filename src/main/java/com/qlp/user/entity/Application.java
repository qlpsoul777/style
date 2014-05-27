package com.qlp.user.entity;

import com.qlp.utils.ParameterUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qlp on 14-5-19.
 */
@Entity
@Table(name = "com_qlp_style_app")
public class Application{

    private String id;  //主键
    private String name;  //名称
    private String description;  //描述
    private String visiable = ParameterUtils.ENABLE;  //可见性，默认为可见
    private String uri;  //链接地址
    private Integer sort;  //排序字段

    private List<Role> roles = new ArrayList<Role>();  //对应的角色
    private List<Functions> funcs = new ArrayList<Functions>();  //对应的功能

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

    public String getVisiable() {
        return visiable;
    }

    public void setVisiable(String visiable) {
        this.visiable = visiable;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY,mappedBy = "apps")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("name")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "application")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("sort")
    public List<Functions> getFuncs() {
        return funcs;
    }

    public void setFuncs(List<Functions> funcs) {
        this.funcs = funcs;
    }
}
