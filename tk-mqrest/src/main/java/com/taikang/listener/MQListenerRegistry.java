package com.taikang.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.taikang.web.mapper.RabbitMapper;
import com.taikang.web.model.ConnectInfor;

@Component
public class MQListenerRegistry {

	private static Logger logger = LoggerFactory.getLogger(MQListenerRegistry.class);

	@Autowired
	RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
	@Autowired
	ConnectionFactory connectionFactory;
	@Autowired
	RabbitMapper rabbitMapper;

	public boolean saveOrUpdateRegistry(ConnectInfor info) {
		MQListenerContainer mqcontainer = null;
		if (rabbitListenerEndpointRegistry.getListenerContainer(info.getContainerid()) != null) {

			mqcontainer = (MQListenerContainer) rabbitListenerEndpointRegistry
					.getListenerContainer(info.getContainerid());
			String[] queueNames = mqcontainer.getQueueNames();
			/**
			 * 更新内容
			 */
			if (queueNames != null && !StringUtils.isEmpty(info.getQueuename())) {
				if (queueNames.length != 1 || !info.getQueuename().equals(queueNames[0])) {
					mqcontainer.addQueueNames(info.getQueuename());
					mqcontainer.removeQueueNames(queueNames[0]);
				}
				mqcontainer.setMessageListener(new MQMessageListener(info, rabbitMapper));
			} else {
				logger.error("error-------->>>队列名称不能为空");
				return false;
			}
		} else {
			addContainer(info);
		}

		try {
			if (rabbitMapper.getConnectInfoById(info.getId()) != null) {
				rabbitMapper.updateConnectInfo(info);
			} else {
				rabbitMapper.addConnectInfor(info);
			}
		} catch (Exception e) {
			logger.error("update error!" + info.toString() + "--------->" + e.getMessage());
		}
		return true;
	}

	/**
	 * @return
	 */
	public Map<String, Integer> getContainersStatus() {
		Map<String, Integer> containers = new HashMap<>();
		Set<String> ids = rabbitListenerEndpointRegistry.getListenerContainerIds();
		for (String id : ids) {
			if (rabbitListenerEndpointRegistry.getListenerContainer(id).isRunning()) {
				containers.put(id, 1);
			} else {
				containers.put(id, 0);
			}
		}
		return containers;
	}

	/**
	 * 开启或者关闭订阅
	 * 
	 * @param ConnectInfor
	 * @return
	 */
	public boolean startOrStopConsume(ConnectInfor connectInfor) {
		if (StringUtils.isEmpty(connectInfor.getContainerid()))
			return false;
		if (rabbitListenerEndpointRegistry.getListenerContainer(connectInfor.getContainerid()) != null) {
			MQListenerContainer mqcontainer = (MQListenerContainer) rabbitListenerEndpointRegistry
					.getListenerContainer(connectInfor.getContainerid());
			if (mqcontainer.isRunning()) {
				mqcontainer.stop();

				connectInfor.setState(0);
			} else {
				String[] queueNames = mqcontainer.getQueueNames();
				/**
				 * 更新内容
				 */
				if (queueNames != null && !StringUtils.isEmpty(connectInfor.getQueuename())) {
					if (queueNames.length != 1 || !connectInfor.getQueuename().equals(queueNames[0])) {
						mqcontainer.addQueueNames(connectInfor.getQueuename());
						mqcontainer.removeQueueNames(queueNames[0]);
					}
					mqcontainer.setMessageListener(new MQMessageListener(connectInfor, rabbitMapper));

					mqcontainer.start();
				} else {
					logger.error("error-------->>>队列名称不能为空");
					return false;
				}

				connectInfor.setState(1);
			}
		} else {
			addContainer(connectInfor);

			connectInfor.setState(1);
		}
		return true;
	}

	/**
	 * 开启订阅
	 * 
	 * @param ConnectInfor
	 * @return
	 */
	public boolean startConsume(ConnectInfor connectInfor) {
		if (StringUtils.isEmpty(connectInfor.getContainerid()))
			return false;

		if (rabbitListenerEndpointRegistry.getListenerContainer(connectInfor.getContainerid()) != null) {
			MQListenerContainer mqcontainer = (MQListenerContainer) rabbitListenerEndpointRegistry
					.getListenerContainer(connectInfor.getContainerid());
			if (mqcontainer.getQueueNames().length == 0) {
				mqcontainer.addQueueNames(connectInfor.getQueuename());
			}
			mqcontainer.start();
		}
		return true;
	}

	public void addContainer(ConnectInfor connectInfor) {
		// 注册
		rabbitListenerEndpointRegistry.registerListenerContainer(new RabbitListenerEndpoint() {
			@Override
			public void setupListenerContainer(MessageListenerContainer listenerContainer) {

			}

			@Override
			public String getId() {
				logger.info("注册ID---------->>" + connectInfor.getContainerid());
				return connectInfor.getContainerid();
			}

			@Override
			public String getGroup() {
				return connectInfor.getGroupname();
			}
		}, new RabbitListenerContainerFactory<MessageListenerContainer>() {
			@Override
			public MessageListenerContainer createListenerContainer(RabbitListenerEndpoint endpoint) {
				MQListenerContainer container = new MQListenerContainer(connectInfor, connectionFactory);
				container.setMessageListener(new MQMessageListener(connectInfor, rabbitMapper));
				return container;
			}
		});
	}

	public void stopConsume(ConnectInfor connectInfor) {
		if (rabbitListenerEndpointRegistry.getListenerContainer(connectInfor.getContainerid()) != null) {
			MQListenerContainer mqcontainer = (MQListenerContainer) rabbitListenerEndpointRegistry
					.getListenerContainer(connectInfor.getContainerid());
			if (mqcontainer.isRunning()) {
				connectInfor.setState(0);
				mqcontainer.stop();
			}
			rabbitMapper.updateConnectInfo(connectInfor);
		}
	}
}
