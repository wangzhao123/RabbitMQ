package com.taikang.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.taikang.web.common.CommonUtil;
import com.taikang.web.mapper.TopicMapper;
import com.taikang.web.model.Config;
import com.taikang.web.model.QueueBind;
import com.taikang.web.model.SystemInfo;
import com.taikang.web.model.Topic;

@Controller
public class TopicController {
	@Autowired
	TopicMapper topicMapper;

	private static Logger logger = LoggerFactory.getLogger(TopicController.class);

	@RequestMapping("/getTopicInformation")
	public String getTopicInformation(ModelMap map) {
		List<Topic> topics = topicMapper.getAllTopic();
		map.addAttribute("topics", topics);
		return "topic_info";
	}

	@RequestMapping("/topic_bind")
	public String topic_bind(ModelMap map, String id) {
		List<QueueBind> bindQueues = topicMapper.getQueuesByTopicId(id);
		List<QueueBind> allQueues = topicMapper.getAllQueue();

		List<String> bindNames = new ArrayList<>();
		List<String> allNames = new ArrayList<>();
		for (QueueBind queueBind : bindQueues) {
			bindNames.add(queueBind.getQueuename());
		}
		for (QueueBind queueBind : allQueues) {
			allNames.add(queueBind.getQueuename());
		}

		map.put("bindNames", bindNames);
		map.put("allNames", allNames);
		return "topic_bind";
	}

	@RequestMapping("/getQueueInformation")
	public String getQueueInformation(ModelMap map) {
		List<QueueBind> queues = topicMapper.getAllQueue();
		List<Topic> topics = topicMapper.getAllTopic();
		List<SystemInfo> systems = topicMapper.getAllSystem();
		
		map.addAttribute("systems", systems);
		map.addAttribute("topics", topics);
		map.addAttribute("queues", queues);
		return "queue_info";
	}

	@RequestMapping("/queue_bind")
	public String queue_bind(ModelMap map, String id) {
		List<SystemInfo> systems = topicMapper.getSystemByQueue(id);

		List<SystemInfo> allSys = topicMapper.getAllSystem();

		List<String> sysCodes = new ArrayList<>();
		// List<String>sysCodes = new ArrayList<>();
		//
		// for (int i = 0; i < array.length; i++) {
		//
		// }
		return "queue_bind";
	}

	@RequestMapping("/getSysInformation")
	public String getSysInformation(ModelMap map) {
		List<SystemInfo> systems = topicMapper.getAllSystem();
	
		map.addAttribute("systems", systems);
		return "system_info";
	}

	@RequestMapping("/getConfigInformation")
	public String getConfigInformation(ModelMap map) {
		List<Config> configs = topicMapper.getAllConfig();
		List<SystemInfo> systems = topicMapper.getAllSystem();
		
		map.addAttribute("systems", systems);
		map.addAttribute("configs", configs);
		return "config_info";
	}
	@ResponseBody
	@RequestMapping("/getQueueByTopic")
	public String getQueueByTopic(String topicid) {
		if(!StringUtils.isEmpty(topicid)){
			return JSON.toJSONString(topicMapper.getQueuesByTopicId(topicid));
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping("/addSystemInfo")
	public String addSystemInfo(SystemInfo sys) {
		if (sys == null) {
			CommonUtil.formatMsg("error", "参数错误");
		}
		if (StringUtils.isEmpty(sys.getSyscode())) {
			return CommonUtil.formatMsg("error", "没有系统编码");
		}
		if (topicMapper.getSysByCode(sys.getSyscode()) != null) {
			return CommonUtil.formatMsg("error", "存在相同系统名");
		}
		if (StringUtils.isEmpty(sys.getSysname())) {
			return CommonUtil.formatMsg("error", "没有系统名称");
		}
		if (StringUtils.isEmpty(sys.getCreateuser())) {
			return CommonUtil.formatMsg("error", "没有创建人");
		}
		sys.setCreatetime(CommonUtil.getSystemDate());

		try {
			topicMapper.addSystemInfo(sys);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建失败--------->" + e.getMessage());
			CommonUtil.formatMsg("error", "创建失败");
		}
		return CommonUtil.formatMsg("success", "创建成功");
	}
	@ResponseBody
	@RequestMapping("/addQueueInfo")
	public String addQueueInfo(QueueBind queue) {
		if (queue == null) {
			CommonUtil.formatMsg("error", "参数错误");
		}
		if (StringUtils.isEmpty(queue.getQueuename())) {
			return CommonUtil.formatMsg("error", "没有队列编码");
		}
		if (topicMapper.getQueueByName(queue.getQueuename()) != null) {
			return CommonUtil.formatMsg("error", "存在相同队列名");
		}
		if (StringUtils.isEmpty(queue.getTopiccode())) {
			return CommonUtil.formatMsg("error", "没有主题编码");
		}
		Topic topic =  topicMapper.getTopicByCode(queue.getTopiccode());
		if(topic==null){
			return CommonUtil.formatMsg("error", "没有主题");
		}
		queue.setTopicid(topic.getId());
		queue.setCreatetime(CommonUtil.getSystemDate());
		try {
			topicMapper.addQueueInfo(queue);
		} catch (Exception e) {
			logger.error("创建失败--------->" + e.getMessage());
			CommonUtil.formatMsg("error", "创建失败");
		}
		return CommonUtil.formatMsg("success", "创建成功");
	}

	
	
	@ResponseBody
	@RequestMapping("/addTopicInfo")
	public String addTopicInfo(Topic topic) {
		if (topic == null) {
			CommonUtil.formatMsg("error", "参数错误");
		}
		if (StringUtils.isEmpty(topic.getTopiccode())) {
			return CommonUtil.formatMsg("error", "没有主题编码");
		}
		if (topicMapper.getTopicByCode(topic.getTopiccode()) != null) {
			return CommonUtil.formatMsg("error", "存在相同主题名");
		}
		topic.setCreatetime(CommonUtil.getSystemDate());
		try {
			topicMapper.addTopic(topic);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建失败--------->" + e.getMessage());
			CommonUtil.formatMsg("error", "创建失败");
		}
		return CommonUtil.formatMsg("success", "创建成功");
	}
	@ResponseBody
	@RequestMapping("/addConfigInfo")
	public String addConfigInfo(Config config) {
		if (config == null) {
			CommonUtil.formatMsg("error", "参数错误");
		}
		if (StringUtils.isEmpty(config.getCallbackurl())) {
			return CommonUtil.formatMsg("error", "没有回调地址");
		}
		if (StringUtils.isEmpty(config.getConfigname())) {
			return CommonUtil.formatMsg("error", "没有回调名称");
		}
		if (StringUtils.isEmpty(config.getCallbackparam())) {
			return CommonUtil.formatMsg("error", "没有回调参数");
		}
		if (StringUtils.isEmpty(config.getSysid())) {
			return CommonUtil.formatMsg("error", "没有系统ID");
		}
		config.setCreatetime(CommonUtil.getSystemDate());
		try {
			topicMapper.addConfig(config);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建失败--------->" + e.getMessage());
			CommonUtil.formatMsg("error", "创建失败");
		}
		return CommonUtil.formatMsg("success", "创建成功");
	}
	
	
}
