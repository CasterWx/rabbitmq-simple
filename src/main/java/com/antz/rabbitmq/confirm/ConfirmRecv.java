package com.antz.rabbitmq.confirm;

import com.antz.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmRecv {

    private static final String QUEUE_NAME = "test_queue_confirm_1" ;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection() ;
        Channel channel = connection.createChannel() ;

        channel.queueDeclare(QUEUE_NAME, false, false, false, null );

        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("(recv):"+ new String(body,"utf8"));
            }
        });


    }
}
