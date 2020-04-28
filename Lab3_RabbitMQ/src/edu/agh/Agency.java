package edu.agh;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Agency {

    public static void main(String[] args) throws Exception {
        // info
        int ID = new Random().nextInt(100);
        System.out.println("AGENCY [ID: " + ID + "]");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange
        String EXCHANGE_NAME = "exchange";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // queue
        String QUEUE_NAME = "queue" + ID;
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, Integer.toString(ID));
        System.out.println("Created queue: " + QUEUE_NAME + " with key " + Integer.toString(ID));

        // exchange admin
        String EXCHANGE_ADMIN = "exchange_admin";
        channel.exchangeDeclare(EXCHANGE_ADMIN, BuiltinExchangeType.TOPIC);

        // queue admin
        String QUEUE_ADMIN = "Agency" + ID;
        String ROUTING_KEY_ADMIN = "space.agency";
        channel.queueDeclare(QUEUE_ADMIN, false, false, false, null);
        channel.queueBind(QUEUE_ADMIN, EXCHANGE_ADMIN, ROUTING_KEY_ADMIN);
        System.out.println("Created queue: " + QUEUE_ADMIN + " with routing key " + ROUTING_KEY_ADMIN);

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

        // consumer admin (handle msg)
        Consumer consumerAdmin = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                channel.basicAck(envelope.getDeliveryTag(), false);
                String message = new String(body, "UTF-8");
                System.out.println("Received: " + message);
            }
        };

        // start listening admin
        System.out.println("Waiting for messages...");
        try {
            channel.basicConsume(QUEUE_ADMIN, true, consumerAdmin);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            System.out.println("Enter service type [1 - people carrier; 2 - satellite placement; 3 - cargo carrier]");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int type = Integer.parseInt(br.readLine());

            int orderID = new Random().nextInt(100000) % 10000;
            String msg = "[" + ID + "] [orderID: " + orderID + "] from agency to carrier";

            if(type == 0) {
                break;
            }

            String routingKey = getRoutingKey(type);
            System.out.println(routingKey);

            // publish
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes("UTF-8"));
            System.out.println("Sent: " + msg + " \t\t\t\t\t\t[->" + routingKey + "]");

            msg = msg + " \t[agency]";
            channel.basicPublish(EXCHANGE_NAME, "admin", null, msg.getBytes("UTF-8"));
            System.out.println("Sent: " + msg + " \t\t\t[->Admin]");
        }

        // close
        channel.close();
        connection.close();
    }

    private static String getRoutingKey(int orderType) {
        switch (orderType) {
            case 1:
                return "people_carry";
            case 2:
                return "satellite_placement";
            case 3:
                return "cargo_carry";
            default:
                return "error";
        }
    }

}
