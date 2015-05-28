package com.qlp.sys.entity;

import com.qlp.commons.entity.TopEntity;


import com.qlp.commons.enums.Gender;
import com.qlp.commons.enums.Type;
import com.qlp.commons.enums.UserStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qlp on 14-4-2.
 * 用户实体
 */
@Entity
@Table(name = "com_qlp_sys_user")
public class User extends TopEntity {

    private String loginName;  //登录名
    private String password;  //密码
    private String name;  //真实姓名
    private Date birthday;  //出身日期
    private UserStatus status = UserStatus.ENABLE;  //是否启用账号默认为启用状态
    private Gender sex = Gender.MAN;  //性别
    private String email;  //邮箱地址
    private String phoneNum;  //联系方式
    private String address;  //联系地址
    private Type type;  //用户类型
    private String salt;  //加盐值（用作加密解密用）


    private List<Role> roles = new ArrayList<>();

    @Column(unique = true, nullable = false,length = 30)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(nullable = false,length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Temporal(value = TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Enumerated(value = EnumType.ORDINAL)
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(length = 5,nullable = false)
    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    @Column(length = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(length = 13)
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Column(length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "com_qlp_sys_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("name")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}