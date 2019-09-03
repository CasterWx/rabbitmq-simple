package com.antz.rabbitmq.tx;

import com.antz.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSend {

    private static final String QUEUE_NAME = "test_queue_tx" ;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection() ;
        Channel channel = connection.createChannel() ;


        String msg = "hello tx" ;

        try {
            channel.txSelect();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null );
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            channel.txCommit();
        }catch (Exception e){
            channel.txRollback();
            System.out.println("send message rollback.");
        }

        channel.close();
        connection.close();

    }
}
