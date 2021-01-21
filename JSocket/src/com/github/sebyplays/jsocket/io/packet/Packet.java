package com.github.sebyplays.jsocket.io.packet;

import lombok.Getter;
import lombok.Setter;

import java.nio.charset.Charset;

public class Packet {

    //Packet format:
    //sourceAddress::destinationAddress::packetInfo::additional

    @Setter @Getter private byte[] bytePacket;
    @Setter @Getter private String sourceAddress = "";
    @Setter @Getter private String destinationAddress = "";
    @Setter @Getter private String packetInformation = "";
    @Getter private final String additionalInformation = "CPacketV1";

    public Packet(String packet){
        this.destruct(packet);
    }

    public Packet(){

    }

    public void destruct(String packet){
        String[] packetArray = packet.split("::");
        this.sourceAddress = packetArray[0];
        this.destinationAddress = packetArray[1];
        this.packetInformation = packetArray[2];
        this.bytePacket = this.toByte(this);
    }

    public String getContent(){
        return (this.sourceAddress + "::" + this.destinationAddress + "::" + this.packetInformation.replaceAll(" ", "%20"));
    }

    public byte[] toByte(Packet packet){
        return packet.getContent().getBytes();
    }

    public String toString(byte[] bytes){
        return new String(bytes, Charset.forName("UTF-8"));
    }

}
