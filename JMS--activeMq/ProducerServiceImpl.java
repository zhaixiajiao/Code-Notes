package com.seafood.queue;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService{
	
	@Resource
	private JmsTemplate jmsTemplate;
	private Queue queue;
	
	@Override
	public void sendMessage(Destination destination,final String msg) {
		System.out.println("向队列“" + destination.toString() + "”发送了消息------------" + msg);
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}

	@Override
	public void sendMessage(final String msg) {
		String destination = jmsTemplate.getDefaultDestinationName().toString();
	    System.out.println("向队列“" +destination+ "”发送了消息------------" + msg);
	    jmsTemplate.send(new MessageCreator() {
    		public Message createMessage(Session session) throws JMSException {
    			return session.createTextMessage(msg);
    		}
	    });
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public String send(final String userId, final String msg) {
		System.out.println(Thread.currentThread().getName() + " 向 " + userId + " 的队列" + userId.toString() + "发送消息------>" + msg);
        queue = new ActiveMQQueue(userId);
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message=session.createTextMessage(msg);
                message.setStringProperty(userId, msg);
                return message;
            }
        });
        return "发送成功";
	}

}
