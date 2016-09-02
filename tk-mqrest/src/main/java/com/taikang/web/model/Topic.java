package com.taikang.web.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Topic {
	private String id;
	private String topicname;
	private String topiccode;
	private String createuser;
	private Timestamp createtime;
	private String info;
	
	
	public Topic(){
		this.setId(UUID.randomUUID().toString());
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopicname() {
		return topicname;
	}
	public void setTopicname(String topicname) {
		this.topicname = topicname;
	}
	public String getTopiccode() {
		return topiccode;
	}
	public void setTopiccode(String topiccode) {
		this.topiccode = topiccode;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	

}
