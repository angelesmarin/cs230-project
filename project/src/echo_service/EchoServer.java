package echo_service;
//10.222.71.230

import javax.sound.midi.Soundbank;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;


public class EchoServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(3000); //port number of server
        DatagramPacket request = new DatagramPacket(
                new byte[1024],
                1024
        );
        while (true) {
            socket.receive(request);
            InetAddress clientIP = request.getAddress();
            int clientPort = request.getPort();
            byte[] payload = Arrays.copyOf(
                    request.getData(),
                    request.getLength()
            );
            String clientMessage = new String(payload);
            System.out.println("Client message: " + clientMessage);
            String replyMessage = clientMessage.toUpperCase();

            DatagramPacket reply = new DatagramPacket(
                    replyMessage.getBytes(),
                    replyMessage.getBytes().length,
                    clientIP,
                    clientPort
            );
            socket.send(reply);

        } //while
    }//main
}//class
