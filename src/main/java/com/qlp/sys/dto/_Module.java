package com.qlp.sys.dto;

public class _Module {
	
	private Long id;
	private Long pid;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public _Module(Long id, Long pid, String name) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
	}
	
}
