package com.github.sebyplays.jsocket.net.port;

public class PortAlreadyOccupied extends Exception {

    public PortAlreadyOccupied(){
        super();
    }

    public PortAlreadyOccupied(String message){
        super(message);
    }

}
