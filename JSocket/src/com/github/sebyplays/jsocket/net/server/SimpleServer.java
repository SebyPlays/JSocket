package com.github.sebyplays.jsocket.net.server;

import com.github.sebyplays.jsocket.net.port.Port;
import com.github.sebyplays.logmanager.api.LogManager;
import com.github.sebyplays.logmanager.api.LogType;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer extends Thread {
    @Getter private ServerSocket serverSocket;
    @Setter @Getter private Socket socket;
    @Getter @Setter private PrintWriter printWriter;
    @Getter @Setter private BufferedReader bufferedReader;
    @Getter private Port port;


    @SneakyThrows
    public SimpleServer(Port port){
        this.port = port;
        this.serverSocket = new ServerSocket(this.port.getPort());
        this.serverContent();
        new LogManager("JSocket").log(LogType.INFORMATION, "Initializing new socket. [Port-Binder: " + this.getPort() + "]", true, false, true, true);
    }

    public void serverContent() {
    }

    public void write(String content){
        this.printWriter.println(content);
        this.printWriter.flush();
    }

    @SneakyThrows
    public String read(){
        return this.bufferedReader.readLine();
    }

    public void build(){

    }

    @SneakyThrows
    public void terminate(){
        this.socket.close();
        this.bufferedReader.close();
        this.printWriter.close();
    }

    @SneakyThrows
    public void accept(){
        this.socket = this.serverSocket.accept();
        this.initIO();
    }

    public void initIO() throws IOException {
        this.printWriter = new PrintWriter(socket.getOutputStream());
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

}
