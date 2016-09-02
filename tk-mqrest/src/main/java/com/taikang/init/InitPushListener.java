package com.taikang.init;

import java.util.List;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.taikang.listener.MQListenerRegistry;
import com.taikang.web.mapper.RabbitMapper;
import com.taikang.web.model.ConnectInfor;

/**
 * 重启服务时 查询数据库启动推送监听
 * @author liel
 *
 */
@Component
public class InitPushListener implements CommandLineRunner{
	@Autowired
	RabbitMapper rabbitMapper;
	@Autowired
	MQListenerRegistry registry;
	@Override
	public void run(String... args) throws Exception {
		List<ConnectInfor> connectlist = rabbitMapper.getConnectInfoByState(1);
		for (int i = 0; i < connectlist.size(); i++) {
			if(!StringUtils.isEmpty(connectlist.get(i).getContainerid())){
				registry.saveOrUpdateRegistry(connectlist.get(i));
			}
		}
	}

}
