package com.taikang.web.model;

import java.sql.Timestamp;
import java.util.UUID;

public class QueueBind {
	private String id;
	private String queuename;
	private String topicid;
	private String createuser;
	private Timestamp createtime;
	private String topicname;
	private String topiccode;
	private String info;
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
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
	public QueueBind(){
		this.setId(UUID.randomUUID().toString());
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQueuename() {
		return queuename;
	}
	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}
	public String getTopicid() {
		return topicid;
	}
	public void setTopicid(String topicid) {
		this.topicid = topicid;
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
	
	
}
