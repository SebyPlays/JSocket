# JSocket

-
## What is the JSocket API?
```
  Primarily the JSocket api is supposed to advance and simplify the usage of Sockets in java.
```
-
## How does it work?
```
The usage of this api may be a bit more complex in comparison to
the other apis i 've been posting.
```

### Server Classes
```
There are multiple types of servers, that require a different type of client.
```
#### SimpleServer
```
The "SimpleServer" class is the foundation of every server.
This class contains the basic functionality of a server,
receiving and sending the packet. 
after that, closing the socket.
```

- Usage:
```java
public static void main(String[] args){
  SimpleServer simpleServer = new SimpleServer(new Port(<int>));
  //do some stuff with the read and write packets.
}
```
##### read() Method:
```
  The read() method returns the clients send packet as a string.
```

##### write() Method:
```
  The write() method will send the client a packet.
```

##### terminate() method:
```
The terminate() method is obviously terminating the connection between client and server.
```
-
#### SimpleComplexServer
```
At this point, things are getting more complex.
The SimpleComplexServer works and implements the use of ComplexPackages and
multiple new methods.

ComplexPackages are being utilized by the PacketHandler to contain ordered and quickly readable data in byte[].

If a package comes in, the current data of the server is being passed to the 
serverContents method parameters.

all methods from the SimpleServer class have been inherited.
```
- Usage:
```java
public static void main(String[] args){
    new SimpleComplexServer(new Port(<int>)){
      public void serverContent(ServerSocket serverSocket, PacketHandler packetHandler, Packet packet){
        //do some stuff with the passed information;
      }
    };
}
```
##### writePacket() method:
```
The writePacket() method utilizes the basic write() method to send packets in a specific format and in bytes.
```
##### readPacket() method:
```
The readPacket() method utilizes the basic read() method to read packets in a specific format and in bytes.
```

##### writePacketNoCallback() method:
```
the writePacketNoCallback() method is sending a packet without expecting a response.
``` 

-
#### ComplexServer
```
The ComplexServer is the most difficult class in this list.
This class adds the ability to connect with multiple clients 
and send multiple packets at once.

Each connected client is being set in a new ClassInstance in another Thread.
```
- Usage:
```java
public static void main(String[] args){
  new ComplexServer(new Port(<int>)){
    public void cserverContent(ServerSocket serverSocket, PacketHandler packetHandler, Packet packet){
      //Do some stuff with the params
    }
  };
}
```
-
#### FileServer
```
The FileServer is the most different and only server class, 
that does not inherit from the other ServerClasses due to compatibility issues.
So, the fileserver type is separated from the other server types.
```
- Usage:
```java
public static void main(String[] args){
  FileServer fileServer = new FileServer(new Port(<int>));
  fileServer.receiveFile(String fileName);
  fileServer.terminate();
}
```
##### receiveFile(String fileName) method:
```
The receiveFile(<fileName>); method, is basically just there for building the DataInputStream to a file.
The only parameter is the name, the file should have after output.

Pretty much selfexplanatory.
```

##### terminate() method:
```
The terminate(); method disconnects the client and closes the server.
```

### Client Classes:

-
#### SimpleClient
```
The SimpleClient Class is the ClientForm equivalent to the SimpleServer.
It consists of the main functionality of every other client inheriting this class. e.g. ComplexClient.
```

- Usage:
```java
public static void main(String[] args){
  SimpleClient simpleClient = new SimpleClient();
  simpleClient.connect(<host>, <port>);
  simpleClient.sendBasicPacket("This is some rando packet info");
  simpleClient.terminate();
}
```

##### connect(<host>, <port>) method:

```
The connect(<host>,<port>) method requires 2 parameters. in this case a host and a port.
This method is connecting the client to the server.
```
##### sendBasicPacket(<String>) method:

```
The sendBasicPacket(<String>) method is simply sending information to the server the client is connected to.
```

##### sendBasicPacketNoCallBack(<String>) method:

```
The sendBasicPacketNoCallBack(<String>) method is the same as the "sendBasicPacket(<String>)" method, with the only
difference, that this method isn't expecting anything, so it isn't returning anything.
```

#### ComplexClient:
```
The ComplexClient class is inheriting all methods from the SimpleClient Class, so
everything mentioned above is applying to this class too with some differences.
```
- Usage:
```java
public static void main(String[] args){
  ComplexClient complexClient = new ComplexClient(){
    public void clientContent(Packet packet){
      //do sth. with the received packets..
    }
  };
  complexClient.connect("127.0.0.1", new Port(2141));
  complexClient.sendComplexPacket(new PacketHandler().constructPacket(complexClient.getSocket(), "Packet information"));
  complexClient.disconnect();
}
```

#### FileClient(<String(host)>, <Port>):
  
```
The FileClient(<>,<>) class is completely separated from any other client class and has no
inheritance at all.

The FileClient is the complete opposite of the FileServer.
This classes constructor requires two parameters, the server details.
```
- Usage:
```java
public static void main(String[] args){
  FileClient fileClient = new FileClient("127.0.0.1", new Port(2141));
  fileClient.sendFile(String path);
  fileClient.terminate();
}
```
##### sendFile(String path) method:
```
The sendFile(String path) method is (obviously) for sending a file to the FileServer.
```

### Packet Classes
```
The Packet classes are either for handeling, directing, or the Datatype itself.
Packets contain all the information that is being sent back and forth. 
```
#### Packet
```
The Packet class itself is the raw datatype, that contains all information. 
this class and these informations can be processed and contructed through the PacketHandler class.
```

#### PacketHandler
```
The PacketHandler class is utilizing the Packet class and 
reads, translates, redirects and writes information
into/from a packet
```
##### constructPacket(<Socket>, <String>) method:

```
the constructPacket(<Socket>, <String>) method is obviously constructing and returning a packet.
all information provided in the parameters are required to have a big variety of possibilities to
handle a packet
```

##### destructPacket(<Packet>) method:

```
the destructPacket(<Packet>) method is returning a String[] array which consists of the packet's info content.
```

