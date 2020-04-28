package server;

import client.TcpClient;
import client.UdpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    public static List<TcpClient> tcpClients = new ArrayList<>();
    public static List<UdpClient> udpClients = new ArrayList<>();
    public static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException {
        System.out.println("========================");
        System.out.println("JAVA TCP/UDP CHAT SERVER");
        System.out.println("========================");
        int portNumber = 9008;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            Thread udpThread = new Thread(udpConnectionThread(portNumber));
            udpThread.start();

            while (true) {
                Socket socket = serverSocket.accept();
                TcpClient newClient = new TcpClient(socket, new PrintWriter(socket.getOutputStream(), true),
                        new BufferedReader(new InputStreamReader(socket.getInputStream())));
                tcpClients.add(newClient);
                executor.execute(newClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            for(TcpClient tcpClient : tcpClients){
                tcpClient.getClientSocket().close();
            }
            executor.shutdown();
        }
    }

    private static Runnable udpConnectionThread(int portNumber) {
        return ()->{
            DatagramSocket udpSocket = null;
            try {
                udpSocket = new DatagramSocket(portNumber);
                byte[] receiveBuffer = new byte[1024];

                while (true) {
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    udpSocket.receive(receivePacket);
                    String msg = new String(receivePacket.getData());

                    if (msg.startsWith(":")) {
                        msg = msg.trim();
                        msg = msg.substring(1);
                        udpClients.add(new UdpClient(msg, receivePacket.getAddress(), receivePacket.getPort()));
                        System.out.println("[UDP] Użytkownik o nicku: \"" + msg + "\" połączony");

                        byte[] data = ("#conUDP#").getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(data, data.length, receivePacket.getAddress(), receivePacket.getPort());
                        udpSocket.send(sendPacket);
                    }
                    else if (msg.trim().startsWith("#/s")) {
                        String nick = msg.substring(3);
                        String leaveMsg = "[UDP] Użytkownik \"" + nick + "\" opuścił czat.";
                        System.out.print(leaveMsg);
                        UdpClient udpClient = new UdpClient();
                        udpClient.findByNick(nick);
                        udpClients.remove(udpClient);
                    }
                    else if (msg.startsWith("#")) {
                        msg = msg.substring(1);
                        UdpClient sendingClient = null;

                        for (UdpClient client : udpClients) {
                            if (client.getAddress().equals(receivePacket.getAddress()) && client.getPort() == receivePacket.getPort()) {
                                sendingClient = client;
                            }
                        }

                        if (sendingClient != null) {
                            System.out.println("[UDP] " + sendingClient.getNick() + ": " + msg);
                            byte[] data = (sendingClient.getNick() + ": " + msg).getBytes();
                            DatagramPacket sendPacket;
                            for (UdpClient cli : udpClients) {
                                if (!cli.equals(sendingClient)) {
                                    sendPacket = new DatagramPacket(data, data.length, cli.getAddress(), cli.getPort());
                                    udpSocket.send(sendPacket);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (udpSocket != null) {
                    udpSocket.close();
                }
            }
        };
    }
}
