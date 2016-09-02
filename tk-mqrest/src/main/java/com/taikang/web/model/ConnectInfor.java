package com.taikang.web.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * 数据库存储连接信息 
 * @author itw_wangzhao
 *
 */
public class ConnectInfor implements Serializable {
	public ConnectInfor(){
		setId(UUID.randomUUID().toString());
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * mq用户名
	 */
	private String id;
	/**
	 * 队列名
	 */
	private String queuename;
	private String configid;
	private String sysid;
	private String queueid;
	
	public String getConfigid() {
		return configid;
	}
	
	public String getQueueid() {
		return queueid;
	}

	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}

	public void setConfigid(String configid) {
		this.configid = configid;
	}
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
	/**
	 * 返回IP
	 */
	private String callbackurl;
	/**
	 * 返回param
	 */
	private String callbackparam;
	private String configname;
	
	public String getConfigname() {
		return configname;
	}

	public void setConfigname(String configname) {
		this.configname = configname;
	}
	/**
	 * 状态
	 */
	private int state;
	/**
	 * 时间
	 */
	private Timestamp updatetime;
	
	//密钥
	private String secret;

	private String groupname;
	private String syscode;
	private String containerid;
	private String applyperson;
	private String contactinfo;
	private String sysname;
	public String getApplyperson() {
		return applyperson;
	}
	public void setApplyperson(String applyperson) {
		this.applyperson = applyperson;
	}
	public String getContactinfo() {
		return contactinfo;
	}
	public void setContactinfo(String contactinfo) {
		this.contactinfo = contactinfo;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	public String getSyscode() {
		return syscode;
	}
	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}
	public String getContainerid() {
		return containerid;
	}
	public void setContainerid(String containerid) {
		this.containerid = containerid;
	}
	public String getQueuename() {
		return queuename;
	}
	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}
	public String getCallbackurl() {
		return callbackurl;
	}
	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}
	public String getCallbackparam() {
		return callbackparam;
	}
	public void setCallbackparam(String callbackparam) {
		this.callbackparam = callbackparam;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
}
