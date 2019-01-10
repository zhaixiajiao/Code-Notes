package com.seafood.queue;


import javax.jms.Destination;
import org.springframework.jms.core.JmsTemplate;


public interface ProducerService{
	/**
	 * 发送消息到指定目标
	 * @param destination
	 * @param msg
	 */
	public void sendMessage(Destination destination, final String msg);
	/**
	 * 发送消息
	 * @param msg
	 */
	public void sendMessage(final String msg);
	/**
	 * 发送消息到指定目标
	 * @param destination
	 * @param msg
	 */
	public String send(String userId, String msg);
}
