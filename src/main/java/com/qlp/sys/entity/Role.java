package com.qlp.sys.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.qlp.commons.enums.Type;
import com.qlp.commons.enums.UserStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.qlp.commons.entity.TopEntity;

/**
 * Created by qlp on 14-5-19.
 * 角色实体
 */
@Entity
@Table(name = "com_qlp_sys_role")
public class Role extends TopEntity {

    private String name;  //角色名称
    private UserStatus status = UserStatus.ENABLE;  //是否启用，默认为启用该角色
    private Type type;  //角色类型，默认为内部角色
    private String description;  //角色描述

    private List<User> members = new ArrayList<User>();  //角色下的用户
    private List<Module> modules = new ArrayList<Module>();  //角色拥有的模块权限

    @Column(length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(value = EnumType.ORDINAL)
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(length = 6,nullable = false)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(length = 50)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "roles")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("loginName")
    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "com_qlp_sys_role_module", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "module_id")})
    @Fetch(FetchMode.SUBSELECT)
    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

}
