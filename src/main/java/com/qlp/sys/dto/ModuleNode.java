package com.qlp.sys.dto;

import java.util.List;

public class ModuleNode {
	
	private Long id;
	private String name;
	private String permission;
	private String url;
	private boolean enable;
	private Integer sort;
	private Integer level;
	
	private List<ModuleNode> children;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<ModuleNode> getChildren() {
		return children;
	}

	public void setChildren(List<ModuleNode> children) {
		this.children = children;
	}

	public ModuleNode(Long id, String name, String permission, String url,
			boolean enable,Integer sort,Integer level) {
		this.id = id;
		this.name = name;
		this.permission = permission;
		this.url = url;
		this.enable = enable;
		this.sort = sort;
		this.level = level;
	}

	public ModuleNode() {}

}
