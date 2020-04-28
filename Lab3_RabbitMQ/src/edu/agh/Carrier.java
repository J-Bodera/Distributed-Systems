package edu.agh;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Carrier {

    public static void main(String[] args) throws Exception {
        // info
//        String service1 = "people_carry";
        String service1 = "satellite_placement";
        String service2 = "cargo_carry";
        int ID = new Random().nextInt(100);

        // print info
        System.out.println("CARRIER [ID: " + ID + "]");
        System.out.println("Supported service types:");
        System.out.println("   - " + service1);
        System.out.println("   - " + service2);

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        Channel channelAdmin = connection.createChannel();

        // exchange
        String EXCHANGE_NAME = "exchange";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // queue
        String QUEUE_NAME_1 = "queue_" + service1;
        channel.queueDeclare(QUEUE_NAME_1, false, false, false, null);
        channel.queueBind(QUEUE_NAME_1, EXCHANGE_NAME, service1);
        System.out.println("Created queue: " + QUEUE_NAME_1);

        String QUEUE_NAME_2 = "queue_" + service2;
        channel.queueDeclare(QUEUE_NAME_2, false, false, false, null);
        channel.queueBind(QUEUE_NAME_2, EXCHANGE_NAME, service2);
        System.out.println("Created queue: " + QUEUE_NAME_2);

        // exchange admin
        String EXCHANGE_ADMIN = "exchange_admin";
        channelAdmin.exchangeDeclare(EXCHANGE_ADMIN, BuiltinExchangeType.TOPIC);

        // queue admin
        String QUEUE_ADMIN = "Carrier" + ID;
        String ROUTING_KEY_ADMIN = "space.carrier";
        channelAdmin.queueDeclare(QUEUE_ADMIN, false, false, false, null);
        channelAdmin.queueBind(QUEUE_ADMIN, EXCHANGE_ADMIN, ROUTING_KEY_ADMIN);
        System.out.println("Created admin queue: " + QUEUE_ADMIN + " with key " + ROUTING_KEY_ADMIN);

        // consumer (handle msg)
        Receiver receiver = new Receiver(channel, QUEUE_NAME_1, EXCHANGE_NAME, ID, ROUTING_KEY_ADMIN, service1);
        Thread rec = new Thread(receiver);
        rec.start();

        Receiver receiver1 = new Receiver(channel, QUEUE_NAME_2, EXCHANGE_NAME, ID, ROUTING_KEY_ADMIN, service2);
        Thread rec1 = new Thread(receiver1);
        rec1.start();

        // consumer admin (handle msg)
        Consumer consumerAdmin = new DefaultConsumer(channelAdmin) {
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

        // close
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int type = Integer.parseInt(br.readLine());

        if(type == 0) {
            channel.close();
            connection.close();
        }

    }

    static class Receiver implements Runnable {
        Channel channel;
        String QUEUE_NAME;
        String EXCHANGE_NAME;
        int ID;
        String ROUTING_KEY_ADMIN;
        String service;

        public Receiver(Channel channel, String QUEUE_NAME, String EXCHANGE_NAME, int ID, String ROUTING_KEY_ADMIN, String service) {
            this.channel = channel;
            this.QUEUE_NAME = QUEUE_NAME;
            this.EXCHANGE_NAME = EXCHANGE_NAME;
            this.ID = ID;
            this.ROUTING_KEY_ADMIN = ROUTING_KEY_ADMIN;
            this.service = service;
        }

        @Override
        public void run(){
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                channel.basicAck(envelope.getDeliveryTag(), false);
                    String msg = new String(body, "UTF-8");
                    System.out.println("Received: " + msg);
                    String[] types = msg.split("]");
                    String agencyID = types[0].substring(1);

                    System.out.println("### HANDLING ORDER [" + service + "] [5 sec.]  ###");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String replay = msg + " \t[DONE] \t\t\t[carrierID: " + ID + "]";

                    channel.basicPublish(EXCHANGE_NAME, agencyID, null, replay.getBytes("UTF-8"));
                    System.out.println("Sent: " + msg + " \t[DONE]\t\t\t[->agencyID: " + agencyID + "]");

                    msg = msg + " \t[carrier]";
                    channel.basicPublish(EXCHANGE_NAME, "admin", null, msg.getBytes("UTF-8"));
                    System.out.println("Sent: " + msg + " \t\t[->Admin]");
                }
            };

            // start listening
            System.out.println("Waiting for messages...");
            try {
                channel.basicConsume(QUEUE_NAME, true, consumer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
