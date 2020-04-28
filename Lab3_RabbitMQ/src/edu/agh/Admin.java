package edu.agh;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Admin {

    public static void main(String[] args) throws Exception {
        // info
        System.out.println("ADMIN");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange
        String EXCHANGE_NAME = "exchange";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // queue
        String QUEUE_NAME = "queue_admin";
        String ROUTING_KEY_ADMIN = "admin";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY_ADMIN);
        System.out.println("Created queue: " + QUEUE_NAME + " with key " + ROUTING_KEY_ADMIN);

        // exchange admin
        String EXCHANGE_ADMIN = "exchange_admin";
        channel.exchangeDeclare(EXCHANGE_ADMIN, BuiltinExchangeType.TOPIC);

        // consumer (handle msg)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                channel.basicAck(envelope.getDeliveryTag(), false);
                String message = new String(body, "UTF-8");
                System.out.println("Received: " + message);
            }
        };

        // start listening
        System.out.println("Waiting for messages...");
        try {
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            System.out.println("Enter where you want to write [1 - carrier; 2 - agency; 3 - both]");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int type = Integer.parseInt(br.readLine());

            String msg = "[ADMIN MSG]";

            if(type == 0) {
                break;
            }

            if( type == 3 ) {
                for (int i=1; i<3; i++) {
                    String routingKey = getRoutingKey(i);

                    // publish
                    channel.basicPublish(EXCHANGE_ADMIN, routingKey, null, msg.getBytes("UTF-8"));
                    System.out.println("Sent: " + msg + " to " + routingKey);
                }
            } else {
                String routingKey = getRoutingKey(type);

                // publish
                channel.basicPublish(EXCHANGE_ADMIN, routingKey, null, msg.getBytes("UTF-8"));
                System.out.println("Sent: " + msg + " to " + routingKey);
            }
        }

        // close
        channel.close();
        connection.close();
    }

    private static String getRoutingKey(int orderType) {
        switch (orderType) {
            case 1:
                return "space.carrier";
            case 2:
                return "space.agency";
            case 3:
                return "space.#";
            default:
                return "error";
        }
    }

}
