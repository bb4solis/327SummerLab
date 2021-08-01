import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This Java Class is designed to discover new peers need to join the LAN and also the peers who want to leave the LAN.
 */
public class Discovery implements Runnable{

    //All the peers
    private HashMap<String, Integer> peers = new HashMap<>();
    //Socket that is built for making the peers discoverable by other peers
    private DatagramSocket socket;

    private Discovery peer = new Discovery();

    public Discovery getDiscovery(){
        return this.peer;
    }

    /**
     * This is used to return all the peers that is active in the network
     * @return Hashmap of peers
     */
    public HashMap<String, Integer> getPeers() {
        return this.peers;
    }

    public DatagramSocket getSocket() {
        return this.socket;
    }

    public void stopDiscovery(){
        this.socket.close();
    }
    @Override
    public void run() {
        try{
            socket = new DatagramSocket(1000, InetAddress.getByName("127.0.0.1"));
            socket.setBroadcast(true);

            //Connection packet send by peers who wish to join or leave the network.
            while(true){
                //packet created
                byte[] received = new byte [10000];
                DatagramPacket packet = new DatagramPacket(received, received.length);
                socket.receive(packet);
                //get the address of the packet
                InetAddress packetAddr = packet.getAddress();
                //get the port of the packet
                int port = packet.getPort();
                //creating host to store the host address
                String host = packetAddr.getHostAddress();
                //append peers to the hashmap
                peers.put(packetAddr.toString(), port);

                //Checking what the message matches to
                String msg = new String(packet.getData()).trim();
                if(msg.equals("JOIN")){
                    byte [] send = "JOINED".getBytes();
                    DatagramPacket packet1 = new DatagramPacket(send, send.length, packetAddr, port);
                    socket.send(packet);
                    peers.put(host, port);
                    //make the peer discoverable for the other peers
                    Thread discoverable = new Thread(peer);
                    discoverable.start();
                    //The peer is now joined to the LAN
                }
                else if(msg.equals("LEAVE")){
                    byte[] leave = "LEFT".getBytes();
                    DatagramPacket packet2 = new DatagramPacket(leave, leave.length, packetAddr, port);
                    socket.send(packet2);
                    peers.remove(host, port);
                    //The peer is now left from the LAN
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
