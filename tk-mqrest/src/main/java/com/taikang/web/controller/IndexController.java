package com.taikang.web.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taikang.listener.MQListenerRegistry;
import com.taikang.web.common.CommonUtil;
import com.taikang.web.common.JSONResult;
import com.taikang.web.mapper.RabbitMapper;
import com.taikang.web.mapper.TopicMapper;
import com.taikang.web.model.ConnectInfor;

@Controller
public class IndexController {
	
	@Autowired
	RabbitMapper rabbitMapper;
	@Autowired
	TopicMapper topicMapper;
	@Autowired
	MQListenerRegistry registry;
	@RequestMapping("/index")
	public String index(ModelMap map){
		return "index";
	}
	
	@RequestMapping("/connectInfo")
	public String connectInfo(ModelMap map){
		List<ConnectInfor> connectInfors = rabbitMapper.getAllConnectInfor();
		Map<String,Integer> containers = registry.getContainersStatus();
		for (int i = 0; i < connectInfors.size(); i++) {
			if(!StringUtils.isEmpty(connectInfors.get(i).getContainerid())){
				if(containers.containsKey(connectInfors.get(i).getContainerid())){
					connectInfors.get(i).setState(containers.get(connectInfors.get(i).getContainerid()));
				}else{
					connectInfors.get(i).setState(0);
				}
			}else{
				connectInfors.get(i).setState(0);
			}
		}
		map.addAttribute("systems", topicMapper.getAllSystem());
		map.addAttribute("topics", topicMapper.getAllTopic());
		map.addAttribute("queues", topicMapper.getAllQueue());
		map.addAttribute("configs", topicMapper.getAllConfig());
		
		map.addAttribute("connectInfors", connectInfors);
		return "table_data_tables";
	}
	
	
	
	
	@RequestMapping("/output")
	public String output(ModelMap map){
		return "output_message";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public JSONResult findAll(){
		List<ConnectInfor> connectInfors = rabbitMapper.getAllConnectInfor();
		
		Map<String,Integer> containers = registry.getContainersStatus();
		for (int i = 0; i < connectInfors.size(); i++) {
			if(connectInfors.get(i).getContainerid()!=null){
				if(containers.containsKey(connectInfors.get(i).getContainerid())){
					connectInfors.get(i).setState(containers.get(connectInfors.get(i).getContainerid()));
				}else{
					connectInfors.get(i).setState(0);
				}
			}
		}
		JSONResult json = new JSONResult();
		json.setCode("200");
		json.setDatas(connectInfors);
		return json;
	}
	
	
	/**
	 * @describe 增加链接
	 * @author wangzhao
	 * @param connectInfor
	 * @return
	 */
	@RequestMapping("/addConnectInfo")
	@ResponseBody
	public String addConnectInfo(ConnectInfor connectInfor){
		
		if(connectInfor!=null){
//			ConnectInfor conn =rabbitMapper.getConnectInfoBySysAndDes(connectInfor.getSyscode(),connectInfor.getQueuename());
//			if(conn!=null)return CommonUtil.formatMsg("error", "已经有申请了!");
		
			if(StringUtils.isEmpty(connectInfor.getConfigid())){
				return CommonUtil.formatMsg("error", "没有回调信息");
			}
			if(StringUtils.isEmpty(connectInfor.getQueueid())){
				return CommonUtil.formatMsg("error", "没有队列信息");
			}
			if(StringUtils.isEmpty(connectInfor.getSysid())){
				return CommonUtil.formatMsg("error", "没有系统编码");
			}
			connectInfor.setState(0);
			connectInfor.setContainerid(UUID.randomUUID().toString());
			connectInfor.setGroupname(connectInfor.getSyscode()+"_"+connectInfor.getQueuename());
			connectInfor.setUpdatetime(CommonUtil.getSystemDate());
			
			rabbitMapper.addConnectInfor(connectInfor);
			
			return CommonUtil.formatMsg("success", "创建成功"); 
		}else{
			return  CommonUtil.formatMsg("error", "问题404"); 
		}
		
	}
	

	/**
	 * @describe 状态的开启和关闭
	 * @param id
	 * 			申请信息ID
	 * @return
	 */
	@RequestMapping("/startOrStopConsume")
	public String startOrStopConsume(@Param("id") String id){
		//查询数据
		ConnectInfor connectInfor = rabbitMapper.getConnectInfoById(id);
		if(connectInfor!=null&&!StringUtils.isEmpty(connectInfor.getContainerid())){
			registry.startOrStopConsume(connectInfor);
			connectInfor.setUpdatetime(CommonUtil.getSystemDate());
			
			rabbitMapper.updateConnectInfo(connectInfor);
		}
		
		return "redirect:/connectInfo";
	}
	

}
