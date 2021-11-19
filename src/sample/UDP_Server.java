package sample;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDP_Server {
    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(5824);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        Main main = new Main();
        while (true) {

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            char [] Exit = new char[4] ;
            sentence.getChars(0,4,Exit,0);
            String re = String.valueOf(Exit);
            if(re.equals("Exit"))
            {
                System.out.println("Client Request: " + re);
                String res = main.FindCourses(sentence);
                sendData = res.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
                return;
            }
            else {
                System.out.println("Client Request: " + sentence);
                String res = main.FindCourses(sentence);
                sendData = res.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }
        }

    }




}
