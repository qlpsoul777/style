package com.qlp.cms.entity;

import com.qlp.commons.entity.TopEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2014/8/19.
 */
@Entity
@Table(name = "com_qlp_style_cms_content")
public class Content extends TopEntity {

    private String title;//新闻标题
    private String depTitle;//新闻副标题
    private String type;//信息类型
    private String keywords;//关键字
    private String isNew;//是否是新内容
    private String canReview;//是否允许评论内容
    private String contentBody;//内容正文
    private String imgPath;//图片地址
    private Long viewCount = 0L;//浏览量
    private Date publishTime;//发布时间
    private String author; //作者

    private Category category;//所属分类
    private Site site;//所属站点

    private List<Version> versions = new ArrayList<Version>();//附件列表

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepTitle() {
        return depTitle;
    }

    public void setDepTitle(String depTitle) {
        this.depTitle = depTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getCanReview() {
        return canReview;
    }

    public void setCanReview(String canReview) {
        this.canReview = canReview;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }
}
