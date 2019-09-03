package com.antz.rabbitmq.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:content.xml");

        RabbitTemplate template = ctx.getBean(RabbitTemplate.class) ;

        template.convertAndSend("Hello world");
        Thread.sleep(1000);
        ctx.destroy();
    }
}
