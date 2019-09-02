package com.antz.rabbitmq.simple;

import com.antz.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {

    private static final String QUEUE_NAME = "test_simple_quque" ;

    public static void main(String[] args) {
        try {
            newRecv();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void newRecv() throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel() ;

        channel.queueDeclare(QUEUE_NAME, false, false, false, null) ;

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8") ;
                System.out.println("(recv):" + msg);
            }
        } ;

        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    public static void oldRecv() throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();
        QueueingConsumer consumer = new QueueingConsumer(channel) ;
        channel.basicConsume(QUEUE_NAME,true , consumer);

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery() ;
            String msg = new String(delivery.getBody()) ;
            System.out.println("(revc):"+msg);
        }
    }
}
