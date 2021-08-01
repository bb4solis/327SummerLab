import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Designed for Client to make connections to the LAN, in other words, to detect broadcasts of other clients on the
 * same LAN.
 */
public class Connections implements Runnable{

    //This is a list of updated peers, meaning at least one peer has joined or left the LAN.
    private HashMap<String, Integer> peers;
    public Connections(){
        peers = new HashMap<>();
    }

    /**
     * return the list of updated peers
     * @return a hashmap of peers
     */
    public HashMap<String, Integer> getPeers() {
        return peers;
    }

    /**
     * Sending "JOIN" request to all the network interfaces on LAN
     */
    @Override
    public void run() {
        try {
            // this socket is responsible for sending the JOIN request.
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            // connection packet
            byte[] send = "JOIN".getBytes();
            try{
                DatagramPacket packet = new DatagramPacket(send, send.length, InetAddress.getByName("127.0.0.1"),1000 );
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // broadcasting message to all the network interfaces
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            // loop through each interface to send packet to each one of them
            while(interfaces.hasMoreElements()){
                NetworkInterface ni = interfaces.nextElement();
                for (InterfaceAddress inAddr : ni.getInterfaceAddresses()){
                    InetAddress addr = inAddr.getBroadcast();
                    if(addr == null){
                        continue;
                    }
                    try{
                    DatagramPacket packet1 = new DatagramPacket(send, send.length,addr, 1000);
                    socket.send(packet1);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
            // socket is waiting for the response from the early request
            for (int i = 0; i<=5; i++){
                byte[] receive = new byte[10000];
                DatagramPacket packet = new DatagramPacket(receive, receive.length);
                socket.receive(packet);
                String msg = new String(packet.getData()).trim();
                // if the response is JOINED, the client have made connection to the LAN
                if(msg.equals("JOINED")){
                    String connection = packet.getAddress().getHostAddress();
                    if(!peers.containsKey(connection)){
                        peers.put(connection, packet.getPort());
                    }
                }
            }
            System.out.println("Joined");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
