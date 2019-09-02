# rabbitmq-simple
RabbitMQ simple.

* 获得Connection.

```java
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory() ;
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/vhost_antz");
        factory.setUsername("antz");
        factory.setPassword("123123");
        return factory.newConnection();
    }
```

* 向队列推送一个消息

```java
    Connection connection = ConnectionUtil.getConnection() ;

    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME,false,false,false,null) ;
    String msg = "hello simple!" ;
    channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
    System.out.println("(send):"+msg);

    channel.close();
    connection.close();
```

* 向队列拿取一个消息

```java
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
```

* 轮询

传入true.

```java
    channel.basicConsume(QUEUE_NAME, true, consumer) ;
```

* 按需分配

```java
     /**
     *  每个消费者发送确认消息之前只发一个 [发一个确认一个]
     * */
     channel.basicQos(1);
     
     /**
     *  处理完成后发送ACK以继续获取
     * */
     channel.basicAck(envelope.getDeliveryTag(), false);
     
     // 此时需关闭轮询
     channel.basicConsume(QUEUE_NAME, false, consumer) ;
```

* 交换机

```java
    /**
    * 发送时发送到交换机
    */
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout") ;
    
    /**
    * 获取时绑定到交换机
    */    
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "") ;
```

