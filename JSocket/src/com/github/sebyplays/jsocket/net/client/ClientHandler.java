package com.github.sebyplays.jsocket.net.client;

import com.github.sebyplays.jsocket.net.server.ComplexServer;
import com.github.sebyplays.jsocket.net.server.SimpleServer;
import com.github.sebyplays.logmanager.api.LogManager;
import com.github.sebyplays.logmanager.api.LogType;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.Socket;

public class ClientHandler {
    @Getter private ComplexServer complexServer;

    @SneakyThrows
    public ClientHandler(ComplexServer complexServer, Socket socket){
        this.complexServer = complexServer;
        LogManager.getLogManager("ClientHandler").log(LogType.NORMAL, " Init client handler step:0 ()", true, false, true, true);
        this.complexServer.setSocket(socket);
        LogManager.getLogManager("ClientHandler").log(LogType.NORMAL, " Init client handler step:1 (Client prepared::" + complexServer.getSocket().getInetAddress().toString() + ")", true, false, true, true);
        this.complexServer.initIO();
        LogManager.getLogManager("ClientHandler").log(LogType.NORMAL, " Init client handler step:2 (IO prepared::" + complexServer.getSocket().getInetAddress().toString() + ")", true, false, true, true);
        new Thread(complexServer::run).start();
        LogManager.getLogManager("ClientHandler").log(LogType.NORMAL, " Init client handler step:3 (Connection successfully prepared and is ready to use::" + complexServer.getSocket().getInetAddress().toString() + ")", true, false, true, true);

    }
}
