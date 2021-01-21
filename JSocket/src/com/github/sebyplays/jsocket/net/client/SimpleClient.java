package com.github.sebyplays.jsocket.net.client;

import com.github.sebyplays.jsocket.net.port.Port;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class SimpleClient {

    @Getter private Socket socket;
    @Getter private PrintWriter printWriter;
    @Getter private BufferedReader bufferedReader;

    @Getter private DataInputStream dataInputStream;
    @Getter private DataOutputStream dataOutputStream;

    @Getter private IOMode ioMode;

    public SimpleClient(){

    }

    @SneakyThrows
    public void connect(String host, Port port){
        socket = new Socket(host, port.getPort());
        switchIOMode(IOMode.PacketStream);
    }

    @SneakyThrows
    public String sendBasicPacket(String packetInformation){
        this.write(packetInformation);
        return this.read();
    }

    @SneakyThrows
    public void sendBasicPacketNoCallback(String packetInformation){
        write(packetInformation);
        return;
    }

    @SneakyThrows
    public void write(String content){
        this.printWriter.println(content);
        this.printWriter.flush();
    }

    @SneakyThrows
    public String read(){
        return bufferedReader.readLine();
    }

    @SneakyThrows
    public void terminate(){
        this.socket.close();
        this.bufferedReader.close();
        this.printWriter.close();
    }

    @SneakyThrows
    public void switchIOMode(IOMode ioMode){
        switch (ioMode){
            case PacketStream:
                this.printWriter = new PrintWriter(socket.getOutputStream());
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.ioMode = IOMode.PacketStream;
                break;
            case DataStream:
                this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
                this.dataInputStream = new DataInputStream(socket.getInputStream());
                this.ioMode = IOMode.DataStream;
                break;
        }
    }

}
