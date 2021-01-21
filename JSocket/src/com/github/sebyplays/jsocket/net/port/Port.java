package com.github.sebyplays.jsocket.net.port;

import com.github.sebyplays.logmanager.api.LogManager;
import com.github.sebyplays.logmanager.api.LogType;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.Socket;

public class Port {

    @Getter private int port;

    @SneakyThrows
    public Port(int port) throws PortAlreadyOccupied {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Choose a valid port between the port-range '0-65535'");
        }
        if(!isAvailable()){
            throw new PortAlreadyOccupied("Port " + this.port + " is already being used by another instance!");
        }
        this.port = port;
    }

    public boolean isAvailable(){
        try { Socket socket = new Socket("localhost", this.port);
            return false;
        } catch (IOException e){
            return true;
        }
    }

}
