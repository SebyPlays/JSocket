package com.github.sebyplays.jsocket.net.server;

import java.util.HashMap;

public class ServerHandler {

    private HashMap<String, SimpleServer> stringSimpleServerHashMap = new HashMap<>();

    public ServerHandler(){
        
    }


    public void addServer(String serverName, SimpleServer simpleServer){
        this.stringSimpleServerHashMap.put(serverName, simpleServer);
    }

    public void removeServer(String serverName){
        this.stringSimpleServerHashMap.remove(serverName);
    }


}
