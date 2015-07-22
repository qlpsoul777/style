package com.qlp.commons.entity;

public class MsgInfo {
	private int code = 0;  //信息号
	private String msg;  //信息详情
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public MsgInfo(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
}
