package client;

import server.Server;

import java.net.InetAddress;

public class UdpClient {
    private String nick;
    private InetAddress address;
    private int port;

    public UdpClient() {
    }

    public UdpClient(String nick, InetAddress address, int port) {
        this.nick = nick;
        this.address = address;
        this.port = port;
    }

    public String getNick() {
        return nick;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public UdpClient findByNick(String nick) {
        for (UdpClient client : Server.udpClients) {
            if (client.getNick().equals(nick))
                return client;
        }
        return null;
    }
}
