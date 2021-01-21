package com.github.sebyplays.jsocket.net.server;

import com.github.sebyplays.jsocket.io.PacketHandler;
import com.github.sebyplays.jsocket.io.packet.Packet;
import com.github.sebyplays.jsocket.net.port.Port;
import lombok.SneakyThrows;

import java.net.ServerSocket;
import java.util.Arrays;

public class SimpleComplexServer extends SimpleServer {

    private PacketHandler packetHandler = new PacketHandler();
    @SneakyThrows
    public SimpleComplexServer(Port port) {
        super(port);
    }

    public void start(){
        while (true) {
            accept();
            String read;
            while ((read = super.read()) != null) {
                this.serverContent(super.getServerSocket(), this.packetHandler, this.packetHandler.reconstructPacket(read));
                break;
            }
        }
    }

    public Packet readPacket(){
        return this.packetHandler.reconstructPacket(super.read());
    }

    public void writePacketNoCallback(Packet packet){
        super.write(Arrays.toString(packet.getBytePacket()));
    }

    public Packet writePacket(Packet packet){
        super.write(Arrays.toString(packet.getBytePacket()));
        return readPacket();
    }

    public void serverContent(ServerSocket serverSocket, PacketHandler packetHandler, Packet packet){

    }

}
