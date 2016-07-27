package com.acker.android;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpMulticastSocket {

    private MulticastSocket mSocket;
    private DatagramPacket recvPacket;
    private InetAddress groupIp;
    private int port;

    /**
     * Constructs a multicast socket, bound to the specified port on the local
     * host.
     *
     * @param multiAddr specify the multicast group.
     * @param port      the port to bind on the localhost.
     */
    public UdpMulticastSocket(String multiAddr, int port) {
        super();
        try {
            this.groupIp = InetAddress.getByName(multiAddr);
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        this.port = port;
        try {
            mSocket = new MulticastSocket(port);
            mSocket.joinGroup(groupIp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mSocket.setLoopbackMode(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a multicast socket, bound to the specified port on the local
     * host with socket buffer set.
     *
     * @param multiAddr specify the multicast group.
     * @param port      the port to bind on the localhost.
     * @param size      this socket's send and recv buffer size.
     */
    public UdpMulticastSocket(String multiAddr, int port, int size) {
        super();
        try {
            this.groupIp = InetAddress.getByName(multiAddr);
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        this.port = port;
        try {
            mSocket = new MulticastSocket(port);
            mSocket.setSendBufferSize(size);
            mSocket.setReceiveBufferSize(size);
            mSocket.joinGroup(groupIp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mSocket.setLoopbackMode(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends data over this socket.
     *
     * @param data data to be sent.
     */
    public void udpSend(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, groupIp,
                port);
        try {
            mSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
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
            mSocket.receive(recvPacket);
            int len = recvPacket.getLength();
            byte[] mData = new byte[len];
            System.arraycopy(recvPacket.getData(), 0, mData, 0, len);
            return mData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
     * For multicast UDP finalization.
     */
    public void udpEnd() {
        if (mSocket != null) {
            try {
                mSocket.leaveGroup(groupIp);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSocket.close();
        }
    }
}