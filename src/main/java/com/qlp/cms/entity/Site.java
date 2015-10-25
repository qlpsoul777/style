package com.qlp.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.qlp.commons.entity.TopEntity;

@Entity
@Table(name = "com_qlp_cms_site")
public class Site extends TopEntity {
	
	private String name;  //站点中文名
	private String siteNo;  //站点编号
	private String domain;//绑定域名
	private String protocol = "http://";//访问协议(http://;https://)
	private int status = 1;//是否启用(0:禁用；1：启用)
	private String imgPath;//站点封面
	
	@Column(length = 30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length = 30)
	public String getSiteNo() {
		return siteNo;
	}
	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}
	
	@Column(length = 100)
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	@Column(length = 10)
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@Column(length = 1)
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(length = 100)
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	

}
