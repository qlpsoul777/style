package com.qlp.user.entity;

import com.qlp.commons.entity.TopEntity;
import com.qlp.utils.ParameterUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qlp on 14-4-2.
 *
 */
@Entity
@Table(name="com_qlp_style_user")
public class User extends TopEntity{

    private String loginName;  //登录名
    private String password;  //密码
    private String name;  //真实姓名
    private Date birthday;  //出身日期
    private String state = ParameterUtils.ENABLE;  //是否启用账号默认为启用状态
    private String sex;  //性别
    private String email;  //邮箱地址
    private String phoneNum;  //联系方式
    private String address;  //联系地址
    private String type;  //用户类型


    private List<Role> roles = new ArrayList<Role>();

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinTable(name = "com_qlp_style_user_role",joinColumns = {@JoinColumn(name = "user_id")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("name")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User(){}
    public User(String loginName,String password) {
        this.loginName = loginName;
        this.password = password;
    }
}