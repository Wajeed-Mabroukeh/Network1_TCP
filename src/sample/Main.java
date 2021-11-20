package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Main extends Application {

    private control co = new control();

    //private UDP_Client client = new UDP_Client(co);
    //private UDP_Server sersver = new UDP_Server(co);


    @Override
    public void start(Stage primaryStage) throws Exception {
        co.initialing();
        //client.run();
        //sersver.run1();
    }

    public static void main(String[] args) {
        launch(args);
    }



}
