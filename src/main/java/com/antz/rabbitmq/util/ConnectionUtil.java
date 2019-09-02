package com.antz.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory() ;

        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/vhost_antz");
        factory.setUsername("antz");
        factory.setPassword("123123");

        return factory.newConnection();
    }

}
