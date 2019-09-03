package com.antz.rabbitmq.spring;

public class MyConsumer {

    public void listen(String foo){
        System.out.println("listen:" + foo);
    }
}
