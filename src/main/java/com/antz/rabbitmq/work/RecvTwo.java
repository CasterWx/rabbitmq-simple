package com.antz.rabbitmq.work;

import com.antz.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RecvTwo {


    private static final String QUEUE_NAME = "test_work_queue" ;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection() ;
        Channel channel = connection.createChannel() ;

        channel.queueDeclare(QUEUE_NAME, false, false, false, null) ;

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[2] recv:"+new String(body,"utf-8"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done");
                }
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer) ;
    }
}
