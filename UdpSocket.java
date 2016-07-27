package com.acker.android;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpSocket {

    private DatagramSocket socket;
    private DatagramPacket recvPacket;
    private InetAddress destAddress;
    private int port;

    /**
     * Constructs a UDP datagram socket which is bound to the specific port.
     *
     * @param port the port to bind on the localhost.
     */
    public UdpSocket(int port) {
        super();
        try {
            this.port = port;
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
            udpEnd();
        }
    }

    /**
     * Constructs a UDP datagram socket which is bound to the specific port with
     * socket buffer size set.
     *
     * @param port the port to bind on the localhost.
     * @param size this socket's send and recv buffer size.
     */
    public UdpSocket(int port, int size) {
        super();
        try {
            this.port = port;
            socket = new DatagramSocket(port);
            socket.setSendBufferSize(size);
            socket.setReceiveBufferSize(size);
        } catch (SocketException e) {
            e.printStackTrace();
            udpEnd();
        }
    }

    /**
     * Setting dest ip address before sending
     *
     * @param ip destinition IP address
     */
    public void udpSendSetting(String ip) {
        try {
            destAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the sender or destination IP address of this datagram packet.
     *
     * @return UDP packet source address.
     */
    public String getUdpSrcAdd() {
        if (recvPacket != null) {
            String address = recvPacket.getAddress().toString();
            String[] temp = address.split("/");
            return temp[1];
        } else {
            return null;
        }
    }

    /**
     * Sends data over this socket.
     *
     * @param data data to be sent.
     */
    public void udpSend(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length,
                destAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            udpEnd();
        }
    }

    /**
     * Wating for data receiving.
     *
     * @param length max packet size you want to receive from UDP socket.
     * @return receive buffer.
     */
    public byte[] udpRecv(int length) {
        try {
            recvPacket = new DatagramPacket(new byte[length], length);
            socket.receive(recvPacket);
            int len = recvPacket.getLength();
            byte[] mData = new byte[len];
            System.arraycopy(recvPacket.getData(), 0, mData, 0, len);
            return mData;
        } catch (IOException e) {
            e.printStackTrace();
            udpEnd();
            return null;
        }
    }

    /**
     * For UDP finalization.
     */
    public void udpEnd() {
        if (socket != null) {
            socket.close();
        }
    }
}