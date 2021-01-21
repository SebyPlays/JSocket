package com.github.sebyplays.jsocket.net.client;

import com.github.sebyplays.jsocket.net.port.Port;
import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class FileClient {

    private DataOutputStream dataOutputStream = null;
    private DataInputStream dataInputStream = null;

    @SneakyThrows
    public FileClient(String host, Port port){
        Socket socket = new Socket(host, port.getPort());
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void sendFile(String path) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        this.dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            this.dataOutputStream.write(buffer,0,bytes);
            this.dataOutputStream.flush();
        }
        fileInputStream.close();
    }

    @SneakyThrows
    public void terminate(){
        this.dataInputStream.close();
        this.dataInputStream.close();
    }

}
