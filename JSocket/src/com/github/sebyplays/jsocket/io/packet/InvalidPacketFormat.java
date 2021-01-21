package com.github.sebyplays.jsocket.io.packet;

public class InvalidPacketFormat extends Exception {

    public InvalidPacketFormat(){
        super();
    }

    public InvalidPacketFormat(String message){
        super(message);
    }

}
