package com.qlp.cms.entity;

import com.qlp.commons.entity.TopEntity;
import com.qlp.user.entity.User;

import javax.persistence.*;

/**
 * Created by qlp on 2014/7/20.
 * 上传附件实体
 */
@Entity
@Table(name = "t_style_cms_version")
public class Version extends TopEntity {

    private String name;  //附件名称
    private String path;  //保存路径
    private String afterName;  //后缀名
    private String type;  //附件类型
    private long size;  //附件大小

    private User user;  //创建人

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

    public String getAfterName() {
        return afterName;
    }

    public void setAfterName(String afterName) {
        this.afterName = afterName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
