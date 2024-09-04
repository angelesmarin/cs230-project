package echo_service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws Exception{ //is a string array
        if(args.length != 2) {
            System.out.println("Please specify <serverIP> and <serverPort>");
                                                //1st arg in array  //2nd arg in array
            return;
        }
        InetAddress serverIP = InetAddress.getByName(args[0]); //take a string -> convert to address in special format
        //ip address taken as string: stored as first element in args array

        //handle port number -is taken as string (user input) (16bit ints)
        int serverPort = Integer.parseInt(args[1]); //converts string to coresponding int

        //read short message from keyboard
        Scanner keyboard = new Scanner(System.in);
        String message = keyboard.nextLine();

        //before send/ receive -> need to read data from socket
        DatagramSocket socket = new DatagramSocket();
        //request socket from os (create socket) (random num)

        //create UDP packet
        DatagramPacket request = new DatagramPacket(

                //inbed message in packet -specify raw data in byte array format
                //convert message from string to byte array
                    message.getBytes(),

                //specify how many bytes are in byte array
                    message.getBytes().length,

                //server ip to send packet to
                        serverIP,

                //server port to send packet to
                        serverPort
        );
        socket.send(request); //give packet to socket -placed in internal buffer of socket

        //receive a reply - prepare empty container/ empty packet to store reply from server
            DatagramPacket reply = new DatagramPacket(

                    //create empty byte array -storage of packet - will store bytes received from server
                    new byte[1024],
                    1024//packet is backed by array
                    //max bytes that can be stored in packet -byte array is internal storage of packet
            );
            socket.receive(reply);
            socket.close();

            //create another byte array for data
            //copy data from previous byte array to new byte array

        byte[] serverMessage = Arrays.copyOf(
                        reply.getData(),
                        reply.getLength() //specify number of bytes we get from server

                );

                //can now print out create new string as server message
        //ceate to string so we can print out -convert byte array to string
        System.out.println(new String(serverMessage));
    }
}
