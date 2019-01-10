package com.seafood.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TopicMessageListener implements MessageListener {

	@Override
	public void onMessage(Message age) {
		TextMessage message = (TextMessage) age;
		try {
			System.out.println("我监听到topic消息到来了："+message.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}

