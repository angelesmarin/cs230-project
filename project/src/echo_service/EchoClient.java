package echo_service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EchoClient {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Please specify <serverIP> and <serverPort>");
            return;
        }

        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);

        DatagramSocket socket = new DatagramSocket();
        DatagramPacket request = new DatagramPacket(new byte[0], 0, serverIP, serverPort);
        socket.send(request);

        DatagramPacket reply = new DatagramPacket(new byte[4], 4);
        socket.receive(reply);
        socket.close();

        byte[] serverReplyBytes = reply.getData();

        int intValue = ByteBuffer.wrap(serverReplyBytes).getInt();
        long unsignedValue = Integer.toUnsignedLong(intValue);
        long secondsSince1970 = unsignedValue - 2208988800L;

        Instant time = Instant.ofEpochSecond(secondsSince1970);
        ZonedDateTime dateTime = time.atZone(ZoneId.of("America/New_York"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        System.out.println("Current date and time: " + dateTime.format(formatter));
    }
}
