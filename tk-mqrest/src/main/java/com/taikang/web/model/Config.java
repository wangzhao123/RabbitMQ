package com.taikang.web.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Config {
	private String configname;
	private String sysname;
	private String id;
	private String callbackurl;
	private String sysid;
	private String callbackparam;
	private Timestamp createtime;
	public Config(){
		this.setId(UUID.randomUUID().toString());
	}
	

	public String getConfigname() {
		return configname;
	}


	public void setConfigname(String configname) {
		this.configname = configname;
	}


	public String getSysname() {
		return sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCallbackurl() {
		return callbackurl;
	}
	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
	public String getCallbackparam() {
		return callbackparam;
	}
	public void setCallbackparam(String callbackparam) {
		this.callbackparam = callbackparam;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	
	
	
}
