package com.github.sebyplays.jsocket;

import com.github.sebyplays.jsocket.io.PacketHandler;
import com.github.sebyplays.jsocket.net.client.ClientHandler;
import com.github.sebyplays.jsocket.net.server.ComplexServer;
import com.github.sebyplays.jsocket.net.server.SimpleComplexServer;
import com.github.sebyplays.logmanager.api.LogManager;
import com.github.sebyplays.logmanager.api.LogType;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;

public class ConnectionHandler {

    public ArrayList<InetAddress> connectedAddresses = new ArrayList<>();
    public ArrayList<InetAddress> blacklistedAddresses = new ArrayList<>();
    public ArrayList<InetAddress> whitelistedAddresses = new ArrayList<>();
    public ArrayList<InetAddress> awaitingConfirmationAddresses = new ArrayList<>();
    public ArrayList<InetAddress> confirmedAddresses = new ArrayList<>();
    public ArrayList<InetAddress> deniedAddresses = new ArrayList<>();
    @Getter @Setter private String accessToken = "";

    public boolean accessCheck(InetAddress inetAddress, String accessToken, AccessMode accessMode) throws IOException {
        if(!this.blacklistedAddresses.contains(inetAddress)){
            switch (accessMode){
                case NONE:
                    return this.access(true, inetAddress);
                case HYBRID:
                    if(this.whitelistedAddresses.contains(inetAddress) && accessToken.equals(this.getAccessToken())){ return this.access(true, inetAddress);}
                    break;

                case WHITELIST:
                    if(this.whitelistedAddresses.contains(inetAddress)){return this.access(true, inetAddress);}
                    break;

                case ACCESS_TOKEN:
                    if(accessToken.equals(this.getAccessToken())){return this.access(true, inetAddress);}
                    break;
            }
        }
        return this.access(false, inetAddress);
    }

    public void confirm(InetAddress inetAddress, boolean confirm) throws IOException {
        if(this.awaitingConfirmationAddresses.contains(inetAddress) && !this.confirmedAddresses.contains(inetAddress) && confirm){
            this.confirmedAddresses.add(inetAddress);
            this.awaitingConfirmationAddresses.remove(inetAddress);
            this.deniedAddresses.remove(inetAddress);
            LogManager.getLogManager("JSocket").log(LogType.INFORMATION, "[" + inetAddress + "] Address confirmed!", true, false, true, true);
            return;
        }
        this.awaitingConfirmationAddresses.remove(inetAddress);
        this.blacklistedAddresses.add(inetAddress);
        LogManager.getLogManager("JSocket").log(LogType.INFORMATION, "§fc[" + inetAddress + "] Address blacklisted!", true, false, true, true);
        return;
    }

    public boolean access(boolean grant, InetAddress inetAddress) throws IOException {
        if(grant){
            if(this.deniedAddresses.contains(inetAddress) || !confirmedAddresses.contains(inetAddress)){ this.awaitingConfirmationAddresses.add(inetAddress);} else { this.connectedAddresses.add(inetAddress); }
            LogManager.getLogManager("JSocket").log(!deniedAddresses.contains(inetAddress) ? LogType.INFORMATION : LogType.WARNING, !deniedAddresses.contains(inetAddress) ? "§faAccess granted to InetAddress -> " + inetAddress.toString() : "Access granted to InetAddress (this address has already been refused before) [Awaiting confirmation] -> " + inetAddress.toString(), true, false, true, true);
            return true;
        }
        LogManager.getLogManager("JSocket").log(LogType.INFORMATION, "§fcAccess denied to InetAddress -> " + inetAddress.toString(), true, false, true, true);
        this.confirmedAddresses.remove(inetAddress);
        deniedAddresses.add(inetAddress);
        return false;
    }

    public void constructClientHandler(AccessMode accessMode, ComplexServer complexServer){
        new Thread("clientHandlerThread"){
            @SneakyThrows
            @Override
            public void run() {
                while(true){
                    ClientHandler clientHandler = new ClientHandler(complexServer, complexServer.getSocket());
                    PacketHandler packetHandler = new PacketHandler();
                    if(accessCheck(complexServer.getSocket().getInetAddress(), packetHandler.destructPacket(complexServer.readPacket())[2], accessMode)){
                    } else { complexServer.terminate();}
                    break;
                }
            }
        }.start();
    }

}

