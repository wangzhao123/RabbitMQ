package com.taikang.listener;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.taikang.web.common.CommonUtil;
import com.taikang.web.controller.MsgQController;
import com.taikang.web.mapper.RabbitMapper;
import com.taikang.web.model.ConnectInfor;

@Component
public class MQMessageListener implements MessageListener {
	private static Logger logger = LoggerFactory.getLogger(MsgQController.class);
	private ConnectInfor info;
	private RabbitMapper rabbitMapper;

	public MQMessageListener(ConnectInfor info, RabbitMapper rabbitMapper) {
		this.info = info;
		this.rabbitMapper = rabbitMapper;
	}

	public MQMessageListener() {

	}

	@Override
	public void onMessage(Message message) {
		String encode = message.getMessageProperties().getContentEncoding();
		if (StringUtils.isEmpty(encode)) {
			encode = "utf-8";
		}
		try {
			sendPost(new String(message.getBody(), encode));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendPost(String message) throws Exception {
		try {
			CommonUtil.sendPost(info.getCallbackurl(), info.getCallbackparam() + "=" + message);
		} catch (Exception e) {
			ConnectInfor connectInfor = rabbitMapper.getConnectInfoById(info.getId());

			if (connectInfor != null && connectInfor.getCallbackurl().equals(info.getCallbackurl())
					&& connectInfor.getCallbackparam().equals(info.getCallbackparam())) {
				logger.error("--------->>回调地址没有变化，线程等待一分钟-->申请ID=" + info.getId() + "-->申请地址=" + info.getCallbackurl()
						+ "-->申请参数=" + info.getCallbackparam());
				
				Thread.sleep(60000);
			} else {
				logger.info("--------->>回调地址出现变化，重新执行新的回调方法");

				this.info = connectInfor;
			}
			sendPost(message);
		}

	}
}
