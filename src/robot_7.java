package server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class robot_7 {
    public static void main(String[] args) throws IOException, InterruptedException {
        int listenPort = 3310;
        ServerSocket listenSocket = new ServerSocket(listenPort); //create a serversoket on 3310 and wait for connection
        System.out.println("\nReady to accept connection on port " + listenPort);
        System.out.println("Waiting for connection...");

        //When accept is called, the function is blocked and wait until a client is connected
        Socket s1 = listenSocket.accept();//create the socket when there is a connection
        //The listen socket is no longer required
        System.out.print("connected");
        listenSocket.close();
        BufferedReader s1_in = new BufferedReader(new InputStreamReader(s1.getInputStream())); //make a s1 input stream
        char[] studentID = new char[10];
        int byteRead = 0;
        int byteLeft = 10;
        do {
            byteRead = s1_in.read(studentID, byteRead, byteLeft); //receive the student id
        } while ((byteLeft -= byteRead) > 0);
        System.out.println("Student ID received: " + new String(studentID));
        String studentIP = s1.getInetAddress().getHostAddress();//get the client ip
        //Generate a random port number and ask STUDENT to listen

        Random random = new Random();
        int iTCPPort2Connect = random.nextInt() % 10000 + 20000;

        System.out.print("Requesting STUDENT to accept TCP <" + iTCPPort2Connect + ">...");
        //Send the port required to the STUDENT
        PrintWriter s1_out = null;
        s1_out = new PrintWriter(s1.getOutputStream());//getting the s1 output stream
        s1_out.write(iTCPPort2Connect + "");//send the rand port number
        s1_out.flush();
        System.out.println("Done");

        Thread.sleep(1000);
        System.out.print("\nConnecting to the STUDENT s1 <" + iTCPPort2Connect + ">...");

        //Connect to the server (student s2)
        Socket s2 = new Socket(studentIP, iTCPPort2Connect);//create the socket on random port
        System.out.println("Done");
        OutputStreamWriter s2_out = null;
        s2.setReceiveBufferSize(500);
        s2_out = new OutputStreamWriter(s2.getOutputStream());

        long start = 0;
        char[] bs;
        start = System.currentTimeMillis();//get current time
        bs = new char[7];//make the received char array with specific size
        s1_in.read(bs, 0, 7);//read the received buffered size
        System.out.println("The buffer size is " + new String(bs));
        do {
            s2_out.write("3310"); //keep writing the data to output stream in 30s
        } while (System.currentTimeMillis() - start <= 30 * 1000);


        s1_in.close();
        s1_out.close();
        s2_out.close();
        s1.close();
        s2.close();


    }


}
