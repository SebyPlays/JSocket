package com.github.sebyplays.jsocket.net;

import com.github.sebyplays.jsocket.io.PacketHandler;
import com.github.sebyplays.jsocket.net.client.ComplexClient;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Ping {

    private ComplexClient complexClient;

    public Ping(ComplexClient complexClient){
        this.complexClient = complexClient;
    }

    @SneakyThrows
    public boolean delayedPing(int intervalSeconds){
        TimeUnit.SECONDS.sleep(intervalSeconds);
        return ping();
    }

    public boolean ping(){
        if(!this.complexClient.sendComplexPacket(new PacketHandler().constructPacket(complexClient.getSocket(), "ping")).equals(null)){
            return true;
        }
        return false;
    }

}
