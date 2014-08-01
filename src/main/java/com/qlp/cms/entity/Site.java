package com.qlp.cms.entity;

import com.qlp.commons.entity.TopEntity;

/**
 * 站点管理
 * Created by admin on 2014/8/1.
 */
public class Site extends TopEntity {
    private String cName;  //站点名称
    private String eName;  //英文名称
    private String simpleName;  //站点简称
    private String domianName;  //域名
    private String path;  //访问路径
    private String visiable = "1";  //是否启用站点,默认为1(0:停用;1:启用) //是否启用站点
    private String description;  //站点简介

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVisiable() {
        return visiable;
    }

    public void setVisiable(String visiable) {
        this.visiable = visiable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomianName() {
        return domianName;
    }

    public void setDomianName(String domianName) {
        this.domianName = domianName;
    }
}
