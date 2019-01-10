package com.seafood.queue;

import javax.jms.Destination;

public interface ConsumerService {
	/**
	 * 接收消息
	 * @param destination
	 */
	public void receive(Destination destination);
	/**
	 * 接收消息
	 * @param destination
	 */
	public String receive1(Destination destination);
	/**
	 * 接收消息
	 * @param userId 指定目标
	 */
	public String receive2(String userId);
}
