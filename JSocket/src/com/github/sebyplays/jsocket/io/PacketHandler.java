package com.github.sebyplays.jsocket.io;

import com.github.sebyplays.jsocket.io.packet.InvalidPacketFormat;
import com.github.sebyplays.jsocket.io.packet.Packet;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

public class PacketHandler {

    public PacketHandler(Packet packet){
    }

    public PacketHandler(){
    }

    public String[] destructPacket(Packet packet){
        return this.toString(packet.getBytePacket()).replaceAll("%20", " ").split("::");
    }

    public Packet constructCustomPacket(String sourceAddress, String destinationAddress, String packetInformation) throws InvalidPacketFormat {
        if(sourceAddress.contains("::") || destinationAddress.contains("::") || packetInformation.contains("::"))
            throw new InvalidPacketFormat("Parameters can't contain '::' ");
        return new Packet(sourceAddress + "::" + destinationAddress + "::" + packetInformation);
    }

    @SneakyThrows
    public Packet constructPacket(Socket socket, String packetInformation){
        return constructCustomPacket(InetAddress.getLocalHost().toString(), socket.getInetAddress().toString(), packetInformation);
    }

    public Packet reconstructPacket(String packet){
        return new Packet(new PacketHandler().toString(new PacketHandler().parseByte(packet)));
    }

    public byte[] parseByte(String byteString){
        String[] byteValues = byteString.substring(1, byteString.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i=0, length=bytes.length; i<length; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
        return bytes;
    }

    public String toString(byte[] bytes){
        return new String(bytes, Charset.forName("UTF-8"));
    }

}
