package com.taikang.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.taikang.listener.MQListenerRegistry;
import com.taikang.rmq.RmqSendMsg;
import com.taikang.session.ISession;
import com.taikang.session.SessionManager;
import com.taikang.web.common.CommonUtil;
import com.taikang.web.common.Constant;
import com.taikang.web.common.exception.ParamException;
import com.taikang.web.mapper.RabbitMapper;
import com.taikang.web.model.ConnectInfor;

@RestController
public class MsgQController {

	private static Logger logger = LoggerFactory.getLogger(MsgQController.class);
	@Autowired
	RabbitMapper rabbitMapper;
	@Autowired
	ConnectionFactory connectionFactory;
	@Autowired
	MQListenerRegistry registry;

	/**
	 * Description：生产
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	/**
	 * 数据格式
	 * {
		“token":”23dfasdkasldksdjksjdsljd”,
		"sysCode": "app",
		“destination”:”exchangeTest”,
		“messageId”:”11111”,
		“transitionId”:”222222222”,
		“body”:”具体的内容”
		}

	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/produce", method = RequestMethod.POST)
	public String produce(HttpServletRequest request) {
		try {
			
			String result = request.getParameter("params");
			String contentType = request.getContentType();
			if(!StringUtils.isEmpty(result)){
				Map<String, Object> params = (Map<String, Object>) JSON.parse(result);
				if (!params.isEmpty()) {
					String token = (String) params.get("token");
					String transitionId = (String) params.get("transitionId");
					String syscode = (String) params.get("sysCode");
					
//					String encrypt = TokenUtil.encrypt(transitionId,syscode,username,password);

//					if(!StringUtils.isEmpty(token)){
//						if(token.equals(encrypt)){
//						}
//					}
							
					if(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(transitionId) && !StringUtils.isEmpty(syscode)){
						return RmqSendMsg.send(params, contentType,connectionFactory);
					}
				}
			}
			logger.info("send params style is error--->" + result);
			return Constant.PARAM_ERROR;
		} catch (Exception e) {
			logger.info("rest api error");
			return Constant.INNER_ERROR;
		}
	}

	/**
	 * Description：消息订阅
	 * 
	 * @param message
	 *            { “token":”23dfasdkasldksdjksjdsljd”, "sysCode": "app",
	 *            "destination":"queue", “callBack: { "url":
	 *            "http://10.23.423:8090/appget/getUser", "paramName": "message"
	 *            } }
	 * @author wangzhao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/consume", method = {RequestMethod.POST,RequestMethod.GET})
	public String consume(HttpServletRequest req, HttpServletResponse res) {
		String message = req.getParameter("message");

		if (StringUtils.isEmpty(message))
			return Constant.PARAM_ERROR;
		
		try {
			Map<String, Object> msg = new HashMap<String, Object>();
			try {
				msg = JSON.parseObject(message);
			} catch (Exception e) {
				throw new ParamException();
			}
//			if (!authToken(getKey(msg, "token", true)))
//				return Constant.USERNAME_PASSWORD_ERROR;

			String sysCode = getKey(msg, "sysCode", true);
			String destination = getKey(msg, "destination", true);

			ConnectInfor info = rabbitMapper.getConnectInfoBySysAndDes(sysCode, destination);

			if (info == null) {
				info = new ConnectInfor();
			}

			Map<String, Object> callBack = (Map<String, Object>) msg.get("callBack");

			info.setUpdatetime(getSystemDate());
			info.setCallbackparam(getKey(callBack, "paramName", true));
			info.setCallbackurl(getKey(callBack, "url", true));
			info.setGroupname(sysCode + "_" + destination);
			info.setState(1);
			info.setSyscode(sysCode);
			info.setQueuename(destination);
			
			if (StringUtils.isEmpty(info.getContainerid())) {
				info.setContainerid(UUID.randomUUID().toString());
			}
			return registry.saveOrUpdateRegistry(info)?Constant.SUCCESS:Constant.INNER_ERROR;

		}catch (ParamException e) {
			logger.error("参数异常：------------->>>"+e.getMessage());
			return Constant.PARAM_ERROR;
		}catch (Exception e) {
			logger.error("内部处理异常：------------->>>");
			e.printStackTrace();
			return Constant.INNER_ERROR;
		}
	}
	
	/**
	 * @Description 取值
	 * @param msgMap
	 * @param key
	 * @param isNeed
	 * @author wangzhao
	 * @return
	 * @throws Exception
	 */
	private String getKey(Map<String, Object> msgMap, String key, Boolean isNeed) throws ParamException {
		if (StringUtils.isEmpty(msgMap.get(key))) {
			if (isNeed) {
				throw new ParamException();
			}
			return "";
		}
		return msgMap.get(key).toString();
	}

	private Timestamp getSystemDate() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * @Description 验证token
	 * @author wangzhao
	 * @param token
	 * @return
	 */
	@SuppressWarnings("unused")
	private Boolean authToken(String token) {
		ISession session = SessionManager.getInstance().getSession(token);
		if (session == null)
			return false;

		logger.info(session.getValue("username") + "------->" + session.getValue("syscode"));
		return true;
	}
	@RequestMapping(value = "/getConnectInfoById", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String getConnectInfoById(HttpServletRequest req) {
		String id = req.getParameter("id");
		ConnectInfor info = rabbitMapper.getConnectInfoById(id);
		
		return JSON.toJSONString(info);
	}

	/**
	 * 修改链接信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editConnectInfo", method = RequestMethod.POST)
	@ResponseBody
	public String editConnectInfo(HttpServletRequest req) {

		String queueid = req.getParameter("queueid");
		String id = req.getParameter("id");
		String configid = req.getParameter("configid");
		
		rabbitMapper.updateConnectInfoConfig(queueid,configid,id);
		
		ConnectInfor info = rabbitMapper.getConnectInfoById(id);

		if (info == null) {
			return CommonUtil.formatMsg("error", "修改失败");
		}
		
		info.setState(1);
		
		registry.saveOrUpdateRegistry(info);

		return CommonUtil.formatMsg("success", "修改成功");
	}
}
