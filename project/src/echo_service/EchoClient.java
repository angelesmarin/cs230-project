package echo_service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws Exception{ //is a string array
        if(args.length != 2) {
            System.out.println("Please specify <serverIP> and <serverPort>");
            return;
        }
        InetAddress serverIP = InetAddress.getByName(args[0]);

        int serverPort = Integer.parseInt(args[1]);

        DatagramSocket socket = new DatagramSocket();

        DatagramPacket request = new DatagramPacket(
                    message.getBytes(),
                    message.getBytes().length,
                        serverIP,
                        serverPort
        );
        socket.send(request);
            DatagramPacket reply = new DatagramPacket(
                    new byte[1024],
                    1024
            );
            socket.receive(reply);
            socket.close();
        byte[] serverMessage = Arrays.copyOf(
                        reply.getData(),
                        reply.getLength()
                );
        System.out.println(new String(serverMessage));

        byte[] serverMessage = Arrays.copyOf(reply.getData(), reply.getLength());
        System.out.println("Server response: " + new String(serverMessage));



        // trail and error
        //handle the 4-byte time packet from the server
        if (reply.getLength() >= 4) {
            byte[] timeData = Arrays.copyOfRange(reply.getData(), 0, 4);  // First 4 bytes are the time data
            ByteBuffer byteBuffer = ByteBuffer.wrap(timeData);
            long secondsSince1900 = byteBuffer.getInt() & 0xFFFFFFFFL;  // Convert to unsigned 32-bit integer

            // Offset between 1900-01-01 and 1970-01-01 in seconds
            long secondsBetween1900and1970 = 2208988800L;
            long secondsSince1970 = secondsSince1900 - secondsBetween1900and1970;

            // Convert to an Instant and then to ZonedDateTime in Eastern Time
            Instant instant = Instant.ofEpochSecond(secondsSince1970);
            ZonedDateTime easternTime = instant.atZone(ZoneId.of("America/New_York"));

            // Format the time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            String formattedTime = easternTime.format(formatter);

            // Output the result
            System.out.println("Eastern Time: " + formattedTime);

        }
    }
}
