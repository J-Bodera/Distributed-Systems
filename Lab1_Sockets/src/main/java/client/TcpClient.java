package client;

import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class TcpClient implements Runnable {

    private Socket socket;
    private String nick;
    private PrintWriter writer;
    private BufferedReader reader;

    public TcpClient(Socket socket, PrintWriter writer, BufferedReader reader){
        this.socket = socket;
        this.writer = writer;
        this.reader = reader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcpClient that = (TcpClient) o;
        return socket.equals(that.socket) &&
                Objects.equals(nick, that.nick);
    }

    public Socket getClientSocket() {
        return socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if(nick == null) {
                    nick = reader.readLine();
                    System.out.println("[TCP] Użytkownik o nicku \""+nick.trim()+"\" połączony");
                    writer.println("#conTCP#");
                }

                else{
                    String msg = reader.readLine();
                    if (msg.trim().startsWith("/s")) {
                        String nick = msg.substring(2);
                        for(TcpClient tcpClient : Server.tcpClients) {
                            if(tcpClient.equals(this)) {
                                Server.tcpClients.remove(tcpClient);
                            }
                        }
                        System.out.println("[TCP] Użytkownik " + nick + " opuścił czat.");
                        Server.executor.remove(this);
                        socket.close();
                    }
                    else {
                        System.out.println("[TCP] " + nick + ": " + msg);
                        for(TcpClient tcpClient : Server.tcpClients) {
                            if(!tcpClient.equals(this)) {
                                tcpClient.writer.println(nick + ": " + msg);
                            }
                        }
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            Server.tcpClients.remove(this);
        }
    }
}