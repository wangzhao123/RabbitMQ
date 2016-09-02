package com.taikang.listener;

import java.util.UUID;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.util.StringUtils;

import com.taikang.web.model.ConnectInfor;
public class MQListenerContainer extends SimpleMessageListenerContainer {

	public MQListenerContainer(ConnectInfor info,ConnectionFactory connectionFactory) {
		this.setConnectionFactory(connectionFactory);
		this.setQueueNames(info.getQueuename());
		this.setAutoStartup(false);
		if(!StringUtils.isEmpty(info.getSyscode())&&!StringUtils.isEmpty(info.getQueuename())){
			this.setConsumerTagStrategy(new ConsumerTagStrategy() {
				@Override
				public String createConsumerTag(String queue) {
					return info.getSyscode()+"."+UUID.randomUUID().toString();	
				}
			});
		}
		this.start();
	}
	
	
	
}
