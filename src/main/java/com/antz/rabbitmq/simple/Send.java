package com.antz.rabbitmq.simple;

import com.antz.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "test_simple_quque" ;

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection() ;

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null) ;

        String msg = "hello simple!" ;

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("(send):"+msg);

        channel.close();
        connection.close();
    }
}
