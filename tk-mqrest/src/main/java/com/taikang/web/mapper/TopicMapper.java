package com.taikang.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.taikang.web.model.Config;
import com.taikang.web.model.QueueBind;
import com.taikang.web.model.SystemInfo;
import com.taikang.web.model.Topic;

@Mapper
public interface TopicMapper {
	@Select("select * from tk_sys_topic order by createtime desc")
	public List<Topic> getAllTopic();

	@Select("select * from tk_sys_queue where topicid =#{topicId} order by createtime desc")
	public List<QueueBind> getQueuesByTopicId(@Param("topicId") String topicId);
	
	@Select("SELECT t.*,e.topicname as topicname ,e.topiccode as  topiccode FROM tk_sys_queue t LEFT JOIN tk_sys_topic e ON t.topicid=e.id ORDER BY t.createtime desc")
	public List<QueueBind> getAllQueue();
	
	@Select("select * from tk_sys_infomation  where id in (select sysid from tk_sys_queue_system where queueid = #{id})")
	public List<SystemInfo> getSystemByQueue(@Param("id") String id);
	@Select("select * from tk_sys_infomation ")
	public List<SystemInfo> getAllSystem();
	@Insert("insert into tk_sys_infomation (id,syscode,sysname,createtime,createuser) values (#{id},#{syscode},#{sysname},#{createtime},#{createuser})")
	public void addSystemInfo(SystemInfo sys);
	@Select("select * from tk_sys_infomation  where syscode = #{syscode}")
	public SystemInfo getSysByCode(@Param("syscode") String syscode);
	@Select("select * from tk_sys_queue  where queuename = #{queuename}")
	public QueueBind getQueueByName(@Param("queuename")String queuename);
	@Insert("insert into tk_sys_queue (id,queuename,topicid,createuser,createtime,info) values (#{id},#{queuename},#{topicid},#{createuser},#{createtime},#{info})")
	public void addQueueInfo(QueueBind queue);
	@Select("select * from tk_sys_topic  where topiccode = #{topicCode}")
	public Topic getTopicByCode(@Param("topicCode") String topicCode);
	@Insert("insert into tk_sys_topic (id,topicname,topiccode,createuser,createtime,info) values (#{id},#{topicname},#{topiccode},#{createuser},#{createtime},#{info})")
	public void addTopic(Topic topic);
	@Select("select t.*,e.sysname from tk_sys_config t left join  tk_sys_infomation e on t.sysid=e.id ")
	public List<Config> getAllConfig();
	@Insert("insert into tk_sys_config (id,callbackurl,sysid,callbackparam,createtime,configname) values (#{id},#{callbackurl},#{sysid},#{callbackparam},#{createtime},#{configname})")
	public void addConfig(Config config);
}
