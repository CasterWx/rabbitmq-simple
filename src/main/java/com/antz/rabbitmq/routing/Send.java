package com.antz.rabbitmq.routing;

import com.antz.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_direct" ;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection() ;
        Channel channel = connection.createChannel() ;

        channel.exchangeDeclare(EXCHANGE_NAME, "direct") ;

        // 发送消息
        String msg = "hello warning" ;
        String routing_key = "warning" ;
        channel.basicPublish(EXCHANGE_NAME, routing_key , null, msg.getBytes());
        System.out.println("send:"+msg);

        channel.close();
        connection.close();
    }
}
