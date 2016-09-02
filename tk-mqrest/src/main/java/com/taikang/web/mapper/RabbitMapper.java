package com.taikang.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;

/**
 *    Copyright 2015-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.taikang.web.model.ConnectInfor;
import com.taikang.web.model.ErrorMessage;


/**
 * @author wangzhao
 */
@Mapper
public interface RabbitMapper {
	
	@Insert("INSERT  INTO connectinfor(id,queueid,configid,state,updatetime,groupname,sysid,containerid,applyperson,contactinfo) VALUES ("
			+ "#{id},#{queueid},#{configid},#{state},#{updatetime},#{groupname},#{sysid},#{containerid},#{applyperson},#{contactinfo})")
	public void addConnectInfor(ConnectInfor connectInfo);
	
	@Insert("INSERT INTO errormessage (id,username,password,errorcode,syscode,exchange,messageid,body,destinationsystemcode,time) "
			+ " value (#{id},#{username},#{password},#{errorcode},#{syscode},#{exchange},#{messageid},#{body},#{destinationsystemcode},#{time})")
	public void addErrorsMessage(ErrorMessage errorMessage);

	@Update("UPDATE connectinfor SET  queueid=#{queueid},state=#{state} ,configid=#{configid},updatetime=#{updatetime},groupname=#{groupname} ,sysid=#{sysid} ,containerid=#{containerid} WHERE id=#{id}")
	public void updateConnectInfo(ConnectInfor info);

	@Select("select * from connectinfor where syscode=#{sysCode} and queuename = #{destination}")
	public ConnectInfor getConnectInfoBySysAndDes(@Param("sysCode")String sysCode,@Param("destination")String destination);
	
	@Select("select t.*,r.callbackurl,r.callbackparam,w.queuename,e.syscode,e.sysname from connectinfor t left join tk_sys_config r on t.configid = r.id left join tk_sys_infomation e on t.sysid=e.id left join tk_sys_queue w on t.queueid=w.id  where t.id=#{id}")
	public ConnectInfor getConnectInfoById(@Param("id")String id);
	
	@Select("select t.*,r.callbackurl,r.callbackparam,w.queuename,e.syscode,e.sysname from connectinfor t left join tk_sys_config r on t.configid = r.id left join tk_sys_infomation e on t.sysid=e.id left join tk_sys_queue w on t.queueid=w.id  where t.state=#{state}")
	public List<ConnectInfor> getConnectInfoByState(@Param("state")int state);
	
	@Select("select t.*,r.callbackurl,r.callbackparam,r.configname,w.queuename,e.syscode,e.sysname from connectinfor t left join tk_sys_config r on t.configid = r.id left join tk_sys_infomation e on t.sysid=e.id left join tk_sys_queue w on t.queueid=w.id order by state DESC ,updatetime DESC ")
	public List<ConnectInfor> getAllConnectInfor();
	
	@Update("update connectinfor t set t.state='1' where t.id=#{id}")
	public int updateStatus(String id);
	
	@Select("select id,queuename,callbackurl,callbackparam,state,updatetime from connectinfor where id=#{id}")
	public ConnectInfor findConnectInforById(String id);
	
	@Update("update connectinfor set secret=#{secret} where syscode=#{syscode} and queuename=#{queuename}")
	public void updateSecret(@Param("syscode")String syscode,@Param("secret")String secret,@Param("queuename")String queuename);

	@Update("update connectinfor set configid=#{configid}, queueid=#{queueid}  where id=#{id}")
	public void updateConnectInfoConfig(@Param("queueid")String queueid, @Param("configid")String configid,@Param("id") String id);
	
//	@Update("update connectinfor set cbstatus where id = #{id}")
//	public void updateCBStatusById(@Param("id") String id);
	
}
