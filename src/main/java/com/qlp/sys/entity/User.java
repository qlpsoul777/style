package com.qlp.sys.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.qlp.commons.entity.TopEntity;
import com.qlp.commons.enums.Gender;
import com.qlp.commons.enums.Type;
import com.qlp.commons.enums.UserStatus;

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
    private Date birthday;  //出生日期
    private UserStatus status = UserStatus.ENABLE;  //是否启用账号默认为启用状态
    private Integer sex = Gender.UNKNOWN.ordinal();  //性别
    private String email;  //邮箱地址
    private String phoneNum;  //联系方式
    private String address;  //联系地址
    private String imgPath;  //头像地址
    private Type type = Type.INNER;  //用户类型
    private String salt;  //加盐值（用作加密解密用）
    private List<Role> roles;

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
    
    @Column(length = 1,nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Column(length = 1,nullable = false)
    public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(length = 50)
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

    @Column(length = 150)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(length = 15,nullable = false)
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
    
    @Column(length = 150)
    public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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
    
    public User(){}

	public User(String loginName, String name, String email) {
		super();
		this.loginName = loginName;
		this.name = name;
		this.email = email;
	}

}