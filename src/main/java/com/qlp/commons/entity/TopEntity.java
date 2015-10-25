package com.qlp.commons.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by qlp on 14-4-2.
 * 顶层实体类用于被其他类继承
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public class TopEntity {
    protected String id;//主键
    protected Date createTime = new Date();//创建时间
    protected Date updateTime = new Date();//更新时间

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(updatable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TopEntity other = (TopEntity) obj;
        if (getId() == null) {
            return false;
        }
        return getId().equals(other.getId());
    }
}
