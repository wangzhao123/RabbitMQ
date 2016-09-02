package com.taikang.web.common;

public class JSONResult {
	
	private String code;
	private String message;
	private Object datas;
	
	public JSONResult(String code, String message, Object datas) {
		super();
		this.code = code;
		this.message = message;
		this.datas = datas;
	}
	public JSONResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getDatas() {
		return datas;
	}
	public void setDatas(Object datas) {
		this.datas = datas;
	}

}
