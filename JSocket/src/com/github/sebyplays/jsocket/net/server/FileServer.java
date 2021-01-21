package com.github.sebyplays.jsocket.net.server;

import com.github.sebyplays.jsocket.net.port.Port;
import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

    private DataOutputStream dataOutputStream = null;
    private DataInputStream dataInputStream = null;
    private Socket socket;

    @SneakyThrows
    public FileServer(Port port) {
        ServerSocket serverSocket = new ServerSocket(port.getPort());
            this.socket = serverSocket.accept();
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());

    }

    private void receiveFile(String fileName) throws Exception{
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = dataInputStream.readLong();
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;
        }
        fileOutputStream.close();
    }


    public void terminate() throws IOException {
        this.dataInputStream.close();
        this.dataOutputStream.close();
        this.socket.close();
    }
}
