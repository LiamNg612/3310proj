
package client;

import java.io.*;
import java.net.*;


public class client {

    public static void main (String[]args) throws IOException, InterruptedException {
        Socket s1;
        BufferedOutputStream bout=null;
        BufferedReader s2_in=null;
        char botPort[];
        try {
            s1 = new Socket(InetAddress.getLocalHost(), 3310); //get socket at the localhost with port3310
            OutputStream sout=s1.getOutputStream();
            bout=new BufferedOutputStream(sout); //get buffered outputstream of s1
            bout.write("1155136169".getBytes()); //send bytes array
            bout.flush();

            // ---------------------------------------------------------- //
            // 							Step 3 							  //
            // ---------------------------------------------------------- //
            BufferedReader sin = new BufferedReader(new InputStreamReader(s1.getInputStream())); //get the inputstream reader of s1
            char[] dddddd = new char[5]; //get the rand port
            sin.read(dddddd,0,5); //read the output stream
            int port1=0;
            ServerSocket listenSocket = new ServerSocket(port1=Integer.parseInt(new String(dddddd))); //create a listensocket on ddddd port
            Socket s2 = listenSocket.accept(); // wait for socket connection and make it to s2
            System.out.println("connected");
            listenSocket.close(); //close the listener
            int num[]={10,50,100,250,500,2000,5000,10000}; //storing the buffer size
            long start=0;
            long count=0;
            long dur=0;
            for(int i=0;i<8;i++){
                dur=0;
                count=0;
                s2.setReceiveBufferSize(num[i]); //set receivebuffersize with the num array
                s2_in = new BufferedReader(new InputStreamReader(s2.getInputStream())); //get s2 inputstream
                String bs="bs"+s2.getReceiveBufferSize();
                System.out.println(bs);
                bout.write(bs.getBytes()); //sent set buffered size
                System.out.println("sent");
                bout.flush();
                start=System.currentTimeMillis();//store the current time in start
                char[] c = new char[4];
                int byteRead = 0;
                int byteLeft = 4;
                do{
                    do{
                        byteRead = s2_in.read(c, byteRead, byteLeft); //receive the student id
                        count++;//record the numver of message
                    }while ((byteLeft -= byteRead) > 0);

                }while((dur=System.currentTimeMillis()-start)<=30*1000); //receive 30s data
                System.out.print("[STUDENT] Number of received messages:" +count+", total received bytes:" +count*4+".");
                System.out.println("Throughput:"+count*4/dur+"bytes/ms");
            }
            //closing
            s2_in.close();
            bout.close();
            sin.close();
            s2.close();
            s1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
