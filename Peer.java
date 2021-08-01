import java.io.*;
import java.util.*;
import java.net.*;
import java.nio.file.*;

public class Peer extends Thread{

    private Socket socket;

    public Peer(Socket socket){
        this.socket = socket;
    }


    public Socket getSocket(){
        return this.socket;
    }

    private void sendFile(String name){

    }

    private void receiveFile(DataInputStream input, String name){

    }

    private void storeFile(){

    }

    private void syncFile(DataInputStream input){

    }

    private void deleteFile(DataInputStream input){

    }



}
