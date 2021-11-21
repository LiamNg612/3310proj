
import java.io.*;
import java.net.*;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class student_1_5{

    public static void main (String[]args) throws IOException {
        Socket s;
        BufferedOutputStream bout=null;
        char botPort[];
        try {
            s = new Socket(InetAddress.getLocalHost().getHostAddress(), 3310); // create the socket with bot with port 3310
            //s = new Socket("172.20.10.140", 3310); for step 6 we need to change it to ip address of bot's ip
            // ---------------------------------------------------------- //
            // 							Step 2 							  //
            // ---------------------------------------------------------- //
            OutputStream sout=s.getOutputStream(); //get output stream from socket
            bout=new BufferedOutputStream(sout); //use buffered output stream for it
            bout.write("1155136169".getBytes()); //send student id by using bytes array
            bout.flush(); //force to send it

            // ---------------------------------------------------------- //
            // 							Step 3 							  //
            // ---------------------------------------------------------- //
            BufferedReader sin = new BufferedReader(new InputStreamReader(s.getInputStream())); //create a buffered reader for s inputstream
            char[] ddddd = new char[5]; //create a char array to store the port
            sin.read(ddddd,0,5); //read the output stream and store it into ddddd array
            int port1;
            ServerSocket listenSocket = new ServerSocket(port1=Integer.parseInt(new String(ddddd))); //create a serversocket with port ddddd
            Socket s1 = listenSocket.accept();//accept the socket connection from bot and create s1
            System.out.println("connected");
            // ---------------------------------------------------------- //
            // 							Step 4 							  //
            // ---------------------------------------------------------- //
            BufferedReader s1in = new BufferedReader(new InputStreamReader(s1.getInputStream()));//create a buffered reader for s1 inputstream
            char[] UDPPort = new char[11];
            s1in.read(UDPPort, 0, 11); //input stream to read
            String[] UDPs=new String(UDPPort).split(","); //split it with , into two String array
            System.out.println(UDPs[0]); //storing fffff
            System.out.println(UDPs[1]); //storing eeeee
            DatagramSocket datagramSocket = new DatagramSocket(parseInt(UDPs[1])); //create a UDP socket on eeeee port
            System.out.println("Done");
            Random random = new Random();
            int num = random.nextInt(1)%4 + 6; //create a random number
            System.out.println(num);
            byte[] data=(Integer.toString(num)).getBytes();  //make the random integer in to byte array
            DatagramPacket Packet = new DatagramPacket(data,data.length,s.getInetAddress(),Integer.parseInt(UDPs[0])); //make UDP packet with the byte data,data length,the receiver ip and the specific port
            datagramSocket.send(Packet);//send the packet
            System.out.println("sent");
            byte buf[]=new byte[1024];// create a byte array
            Packet=new DatagramPacket(buf,buf.length); // create a packet with the byte and its length for receiving packet
            System.out.println("waiting for receive");
            datagramSocket.receive(Packet);//receive the packet
            String received=new String(Packet.getData(),0,Packet.getLength());//make the packet data into string
            System.out.println(received);
            for(int i=0;i<5;i++){ //send the received char string xxx five times
                Packet = new DatagramPacket(received.getBytes(),received.getBytes().length,s.getInetAddress(),Integer.parseInt(UDPs[0]));
                datagramSocket.send(Packet);
                Thread.sleep(1000);
                System.out.println("UDP packet "+(i+1)+" sent");
            }
            //closing all socket and stream
            bout.close();
            sin.close();
            s1in.close();
            listenSocket.close();
            s1.close();
            datagramSocket.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
