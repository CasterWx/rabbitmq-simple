package com.antz.rabbitmq.exchange;

import com.antz.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RecvTwo {

    private static final String EXCHANGE_NAME = "test_exchange_queue" ;
    private static final String QUEUE_NAME = "test_simple_quque" ;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel() ;

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "") ;

        channel.queueDeclare(QUEUE_NAME, false, false, false, null) ;

        /**
         *  每个消费者发送确认消息之前只发一个 [发一个确认一个]
         * */
        channel.basicQos(1);

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[2] recv:"+new String(body,"utf-8"));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        channel.basicConsume(QUEUE_NAME, false, consumer) ;

    }
}
