package com.github.sebyplays.jsocket.net.server;

import com.github.sebyplays.jsocket.io.PacketHandler;
import com.github.sebyplays.jsocket.io.packet.Packet;
import com.github.sebyplays.jsocket.net.client.ClientHandler;
import com.github.sebyplays.jsocket.net.port.Port;
import lombok.SneakyThrows;

import java.net.ServerSocket;

public class ComplexServer extends SimpleComplexServer implements Runnable {

    private Thread thread = new Thread(this::run);

    @SneakyThrows
    public ComplexServer(Port port) {
        super(port);
        while (true)
            new ClientHandler(this, getServerSocket().accept()).getComplexServer();
    }

    @SneakyThrows
    @Override
    public void run() {
        String read;
        while ((read = this.getBufferedReader().readLine()) != null){
            Packet packet = new PacketHandler().reconstructPacket(read);
            //if(new PacketHandler().destructPacket(packet)[1].toLowerCase().contains(this.getSocket().getInetAddress().toString().toLowerCase()))
            preprocessPacket(packet);
            if(new PacketHandler().destructPacket(packet)[2].equals("exitSocket"))
                break;

            this.cserverContent(this.getServerSocket(), new PacketHandler(), packet);
        }
        this.terminate();
    }

    public void preprocessPacket(Packet packet){
        if(new PacketHandler().destructPacket(packet)[2].equals("ping")){
            writePacketNoCallback(new PacketHandler().constructPacket(getSocket(), "pong"));
        }
    }

    public void cserverContent(ServerSocket serverSocket, PacketHandler packetHandler, Packet packet){
    }

}
