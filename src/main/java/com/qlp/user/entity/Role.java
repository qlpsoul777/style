package com.qlp.user.entity;

import com.qlp.commons.entity.TopEntity;
import com.qlp.utils.ParameterUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qlp on 14-5-19.
 * 角色实体
 */
@Entity
@Table(name = "com_qlp_style_role")
public class Role extends TopEntity {

    private String name;  //角色名称
    private String state = ParameterUtils.ENABLE;  //是否启用，默认为启用该角色
    private String description;  //角色描述

    private List<User> members = new ArrayList<User>();  //角色下的用户
    private List<Application> apps = new ArrayList<Application>();  //角色拥有的应用

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 角色为关系的维护端
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "roles")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("loginName")
    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    /**
     * 角色为关系的维护端
     */
    @ManyToMany(targetEntity = Application.class, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "com_qlp_style_role_app", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "app_id")})
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id")
    public List<Application> getApps() {
        return apps;
    }

    public void setApps(List<Application> apps) {
        this.apps = apps;
    }
}
