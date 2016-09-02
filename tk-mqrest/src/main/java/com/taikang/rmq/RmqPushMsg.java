package com.taikang.rmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;


/**
 * 推送消息实现类
 * @author lierl
 *
 */
public class RmqPushMsg {

	/**
	 * 
	 * @param params
	 * @param connectionFactory
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String push(String queue,ConnectionFactory connectionFactory){
		try {
			SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
			container.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					
				}
			});
//			container.addQueueNames("queueTest");
//			container.addQueueNames("2222");
////			queue 实际上添加的是callbackmethod
//			container.start();
			
		} catch (Exception e) {
		
		}
		return null;
	}
}
