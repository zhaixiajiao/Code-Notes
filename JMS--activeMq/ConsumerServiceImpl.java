package com.seafood.queue;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Resource
	private JmsTemplate jmsTemplate;

	@Override
	public void receive(Destination destination){
		TextMessage tm = (TextMessage) jmsTemplate.receive(destination);
		try {
			System.out.println("从队列" + destination.toString() + "收到了消息：\t" + tm.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public String receive1(Destination destination) {
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        try {
            System.out.println("从队列" + destination.toString() + "收到了消息：\t" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return textMessage.toString();
    }
	
	@Override
    public String receive2(String userId) {
        Queue queue = new ActiveMQQueue(userId+"?consumer.prefetchSize=4");
        Message message = null;
        String property=null;
        try {
            message=jmsTemplate.receive(queue);
            property=message.getStringProperty(userId);
            System.out.println("从队列" + queue.toString() + "收到了消息：\t" + property);
        } catch (JMSException e1) {
        	e1.printStackTrace();
        }
        return property;
    }
}