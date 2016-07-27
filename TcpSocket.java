package com.acker.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpSocket {

    private ServerSocket serverSocket;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    /**
     * For client TCP initialization and connection ,after server
     * socketConnect().
     *
     * @param port        the port on the target host to connect to.
     * @param destAddress the target host name or IP address to connect to.
     */
    public TcpSocket(int port, String destAddress) {
        super();
        try {
            socket = new Socket(destAddress, port);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            tcpEnd();
        } catch (IOException e) {
            e.printStackTrace();
            tcpEnd();
        }
    }

    /**
     * For server TCP initialization.
     *
     * @param port the port on the target host to connect to.
     */
    public TcpSocket(int port) {
        super();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            tcpEnd();
        }
    }

    /**
     * For server TCP connection, after initialization.
     */
    public void socketConnect() {
        try {
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            tcpEnd();
            e.printStackTrace();
        }
    }

    /**
     * Sending data after TCP connection.
     *
     * @param data   the buffer to be sent.
     * @param offset the start position in buffer from where to get bytes.
     * @param count  the number of bytes from buffer to write to the outputstream.
     */
    public void tcpSend(byte[] data, int offset, int count) {
        try {
            if (outputStream != null) {
                outputStream.write(data, offset, count);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            tcpEnd();
        }
    }

    /**
     * Receiving data after TCP connection.
     *
     * @param bufferSize max size you want to receive from TCP recv Buffer
     * @return Buffer type including byte[] and actural size or null if read
     * nothing.
     */
    public Buffer tcpRecv(int bufferSize) {
        byte[] buffer = new byte[bufferSize];
        try {
            if (inputStream != null) {
                int size;
                if ((size = inputStream.read(buffer)) != -1) {
                    Buffer recvBuf = new Buffer(buffer, size);
                    return recvBuf;
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            tcpEnd();
            return null;
        }
    }

    /**
     * Returns the IP address of the target host this socket is connected to, or
     * null if this socket is not yet connected.
     *
     * @return TCP connection source address
     */
    public String getTcpSrcAdd() {
        if (socket != null) {
            String address = socket.getInetAddress().toString();
            String[] temp = address.split("/");
            return temp[1];
        } else {
            return null;
        }
    }

    /**
     * For TCP disconnection.
     */
    public void tcpEnd() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Provide a TCP receiving buffer type.
     *
     * @author Acker
     */
    public class Buffer {
        byte[] buf;
        int size;

        public Buffer(byte[] buf, int size) {
            super();
            this.buf = buf;
            this.size = size;
        }

        public byte[] getBuf() {
            return buf;
        }

        public int getSize() {
            return size;
        }

    }
}