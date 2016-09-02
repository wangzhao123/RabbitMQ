package com.taikang.rmq;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.util.StringUtils;

import com.taikang.web.common.Constant;

/**
 * 发送消息实现类
 * 
 * @author liel
 *
 */
public class RmqSendMsg {

	private static Logger logger = LoggerFactory.getLogger(RmqSendMsg.class);

	/**
	 * 发送实现方法
	 * 
	 * @param params
	 *            客户端传递参数
	 * @param connectionFactory
	 *            连接工厂
	 * @return
	 */
	public static String send(Map<String, Object> params, String contentType, ConnectionFactory connectionFactory) {

		String messageId = (String) params.get("messageId");
		String syscode = (String) params.get("sysCode");
		RabbitTemplate template = new RabbitTemplate(connectionFactory);

		String exchange = (String) params.get("destination");
		String body = params.get("body").toString();

		try {
			template.setConfirmCallback(new ConfirmCallback() {
				@Override
				public void confirm(CorrelationData correlationData, boolean ack, String cause) {
					if (!ack) {
						logger.info("syscode:" + syscode + ";message : " + messageId + "--->" + "send message failed : "
								+ cause);
						throw new RuntimeException("send error " + cause);
					}
				}
			});
		} catch (Exception e1) {
			logger.info("ack error " + e1.getMessage());
			return Constant.INNER_ERROR;
		}

		try {
			if (!StringUtils.isEmpty(exchange) && !StringUtils.isEmpty(body)) {

				MessageProperties mp = new MessageProperties();
				mp.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

				if (!StringUtils.isEmpty(contentType) && contentType.split(";").length > 1) {
					String charset = contentType.split(";")[1];
					if (!StringUtils.isEmpty(charset) && charset.split("=").length>1) {
						String encoding = charset.split("=")[1];
						mp.setContentEncoding(encoding);
					}
				}

				Message message = new Message(body.getBytes(), mp);

				template.convertAndSend(exchange, "", message);

				logger.info("{syscode:" + syscode + ";message : " + messageId + "} send message success");
				return Constant.SUCCESS;

			} else {
				logger.info("{syscode:" + syscode + ";message : " + messageId + "} postType is error");
				return Constant.PARAM_ERROR;
			}
		} catch (AmqpException e) {
			logger.info("{syscode:" + syscode + ";message : " + messageId + "} send message fail " + e.getMessage());
			return Constant.INNER_ERROR;
		}
	}
}
