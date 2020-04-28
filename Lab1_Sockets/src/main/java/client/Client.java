package client;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        System.out.println("========================");
        System.out.println("JAVA TCP/UDP CHAT CLIENT");
        System.out.println("========================");

        int portNumber = 9008;
        String hostName = "localhost";
        Socket tcpSocket = new Socket(hostName, portNumber);

        DatagramSocket udpSocket = new DatagramSocket();

        System.out.println("Podaj nick:");
        Scanner input = new Scanner(System.in);
        String nick = input.nextLine();

        final PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true);
        out.println(nick);

        final InetAddress address = InetAddress.getByName(hostName);
        byte[] sendBuffer1 = (":"+nick).getBytes();
        DatagramPacket sendPacket1 = new DatagramPacket(sendBuffer1, sendBuffer1.length, address, portNumber);
        udpSocket.send(sendPacket1);

        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        udpSocket.receive(receivePacket);
        String udpReply = new String(receivePacket.getData());
        udpReply = udpReply.trim();
        if(udpReply.equals("#conUDP#")) {
            System.out.println("Połączono z serwerem przez UDP");

            BufferedReader in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            if(in.ready()) {
                if(in.readLine().equals("#conTCP#")) {
                    System.out.println("Połączono z serwerem przez TCP");
                }
            } else {
                System.out.println("Nie udało się połączyć z serwerem przez TCP, prawdopodobnie przekroczono limit wątków");
            }
        }

        System.out.println("\nWitaj " + nick + "!\n\nPrzydatne komendy");
        System.out.println("/i - wyświetla twoje dane");
        System.out.println("/h - help, wyświetla opis komend");
        System.out.println("/u - zmiana trybu nadawania");
        System.out.println("/s - zakonczenie pracy");
        System.out.println("Startowy tryb nadawania to UDP\n");

        Thread tcpReader = new Thread(tcpReaderThread(tcpSocket));
        Thread udpReader = new Thread(udpReaderThread(udpSocket));
        Thread writer = new Thread(writerThread(portNumber, udpSocket, out, address, nick));

        writer.start();
        tcpReader.start();
        udpReader.start();
    }

    private static Runnable tcpReaderThread(Socket socket) {
        return () -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(true) {
                    String response = in.readLine();
                    System.out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private static Runnable udpReaderThread(DatagramSocket udpSocket) {
        return () -> {
            try (udpSocket) {
                byte[] receiveBuffer = new byte[1024];
                while (true) {
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    udpSocket.receive(receivePacket);
                    String msg = new String(receivePacket.getData());
                    System.out.println(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private static Runnable writerThread(int portNumber, DatagramSocket udpSocket, PrintWriter out, InetAddress address, String nick) {
        return () -> {
            try {
                boolean udp = true;
                while (true) {
                    Scanner scanner = new Scanner(System.in);
                    String scanned = scanner.nextLine();
                    if(scanned.equals("/u")) {      // Change sending method
                        if(!udp) {
                            udp = true;
                            System.out.println("Zmieniono opcje wysyłania na UDP");
                        } else {
                            udp = false;
                            System.out.println("Zmieniono opcję wysyłania na TCP");
                        }
                    }
                    else if (scanned.equals("/i")) {
                        System.out.println("Twój nick: " + nick);
                        System.out.print("Twoja opcja wysyłania to: ");
                        if(udp)
                            System.out.println("UDP");
                        else
                            System.out.println("TCP");

                    }
                    else if (scanned.equals("/h")) {
                        System.out.println("***HELP***");
                        System.out.println("/i - wyświetla twoje dane");
                        System.out.println("/h - help, wyświetla opis komend");
                        System.out.println("/u - zmiana trybu nadawania");
                        System.out.println("/s - zakonczenie pracy\n");
                    }
                    else if (scanned.equals("/s")) {
                        out.println(scanned + nick);

                        byte[] sendBuffer = ("#" + scanned + nick).getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                        udpSocket.send(sendPacket);

                        System.exit(0);
                    }
                    else if(udp){
                        byte[] sendBuffer = ("#" + scanned).getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                        udpSocket.send(sendPacket);
                    }
                    else {
                        out.println(scanned);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}