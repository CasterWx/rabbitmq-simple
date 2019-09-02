package com.antz.rabbitmq.exchange;

import com.antz.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_queue" ;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection() ;
        Channel channel = connection.createChannel() ;

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout") ;

        // 发送消息
        String msg = "hello" ;

        channel.basicPublish(EXCHANGE_NAME, "" , null, msg.getBytes());
        System.out.println("send:"+msg);

        channel.close();
        connection.close();
    }
}
