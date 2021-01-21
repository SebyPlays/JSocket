package com.github.sebyplays.jsocket.net.client;

import com.github.sebyplays.jsocket.io.PacketHandler;
import com.github.sebyplays.jsocket.io.packet.Packet;
import com.github.sebyplays.jsocket.net.Ping;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ComplexClient extends SimpleClient {

    @SneakyThrows
    public ComplexClient(){
    }

    @SneakyThrows
    public Packet sendComplexPacket(Packet packet){
        packet.setSourceAddress(InetAddress.getLocalHost().toString());
        sendBasicPacketNoCallback(Arrays.toString(packet.getBytePacket()));
        return readPacket();
    }

    public void sendComplexPacketNoCallback(Packet packet){
        packet.setSourceAddress(getSocket().getInetAddress().toString());
        super.sendBasicPacketNoCallback(Arrays.toString(packet.getBytePacket()));
    }

    public Packet readPacket(){
        String reads = super.read();
        clientContent(new PacketHandler().reconstructPacket(reads));
        return new PacketHandler().reconstructPacket(reads);
    }

    public void clientContent(Packet packet){

    }

    public boolean isConnected(){
        return this.ping(0);
    }

    public boolean ping(int interval){
        if(interval == 0){
            return new Ping(this).ping();
        }
        return new Ping(this).delayedPing(10);
    }

    public void disconnect(){
        this.sendComplexPacketNoCallback(new PacketHandler().constructPacket(getSocket(), "exitSocket"));
        this.terminate();
    }



    public void receiveFile(){

    }

}

enum IOMode{
    PacketStream,
    DataStream;
}
