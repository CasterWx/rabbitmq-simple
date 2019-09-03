package com.antz.rabbitmq.confirm;

import com.antz.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmSend {

    private static final String QUEUE_NAME = "test_queue_confirm_1" ;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection() ;
        Channel channel = connection.createChannel() ;
        channel.queueDeclare(QUEUE_NAME, false, false, false, null );

        channel.confirmSelect();
        String msg = "hello tx" ;

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        if (!channel.waitForConfirms()){
            System.out.println("message send failed");
        }else{
            System.out.println("message send success");
        }

        channel.close();
        connection.close();

    }
}
