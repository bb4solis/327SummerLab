import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

//we're going to have to rewrite this please ignore for now'
/*
public class serverSetUp{
  private static String serverName;//used later
  private static int portNum;//used later

  //Socket Server template
public static void main(String[]args){

  try{
    ServerSocket ss = new ServerSocket(66666);
    Socket s = ss.accept();//establishes connection
    DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
  
    String str="",str2="";  
    while(!str.equals("stop")){  
    str=din.readUTF();  
    System.out.println("client says: "+str);  
    str2=br.readLine();  
    dout.writeUTF(str2);  
    dout.flush();  
}  
    din.close();  
    s.close();  
    ss.close();
}
}
*/