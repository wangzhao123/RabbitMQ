package com.taikang.web.model;

import java.sql.Timestamp;
import java.util.UUID;

public class SystemInfo {
	private String id;
	private String syscode;
	private String sysname;
	private Timestamp createtime;
	private String createuser;
	public SystemInfo(){
		this.setId(UUID.randomUUID().toString());
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSyscode() {
		return syscode;
	}
	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	
}
