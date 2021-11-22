package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;


public class control {

    //All TextFiled
    public javafx.scene.control.TextField TCP_Server_Port = new TextField();
    public TextField TCP_Server_IP = new TextField();
    public TextField Local_IP = new TextField();
    public TextField Local_Port = new TextField();
    public TextField Remote_IP = new TextField();
    public TextField Remote_Port = new TextField();
    public TextField Status = new TextField();
    public TextField Username = new TextField();

    //Button send
    public Button Send = new Button();
    public Button Refresh = new Button();

    //Areas Text
    public TextArea Area_Users = new TextArea();
    public TextArea Area_Chat = new TextArea();
    public TextArea Area_Text = new TextArea();

    //Array
    public ArrayList array = new ArrayList();

    //DatagramSocket
    public DatagramSocket clientSocket;

    //ComboBox
    public ComboBox comboBox = new ComboBox();
    public ObservableList<String> options1 = FXCollections.observableArrayList();


    public void initialing() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Interface.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 850, 560));
        primaryStage.show();


    }


    public void Button_Test_Chat()
    {
        if (Local_Port.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "please enter port number");
        }
        else
            {
            int port = Integer.parseInt(Local_Port.getText());
            try
            {
                clientSocket = new DatagramSocket(null);


                if (Local_IP.getText().equals(""))
                {
                    Local_IP.setText(comboBox.getValue().toString());
                    Remote_IP.setText(comboBox.getValue().toString());
                    JOptionPane.showMessageDialog(null, "Your local ip is : " + comboBox.getValue().toString());

                }
                InetSocketAddress addr = new InetSocketAddress(Local_IP.getText(), port);
                clientSocket.setReuseAddress(true);
                clientSocket.bind(addr);
                JOptionPane.showMessageDialog(null, "Test is Successfully");
                Local_IP.setEditable(false);
                Remote_IP.setEditable(false);
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public void Button_send_Chat()
    {
        if (Remote_Port.getText().equals("") && Remote_IP.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "please enter port number and ip address for remote");
        }
        else
         {
            try
            {
                int po = Integer.parseInt(Remote_Port.getText());
                InetSocketAddress add = new InetSocketAddress(Remote_IP.getText(), po);
                byte[] sendData = new byte[1024];
                String sent = Username.getText() + ">>" + Area_Text.getText();
                sendData = sent.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
                sendPacket.setSocketAddress(add);
                clientSocket.send(sendPacket);
                Area_Chat.appendText(Username.getText()+">>"+Area_Text.getText()+"\n");
                Area_Text.setText("");
                JOptionPane.showMessageDialog(null, "Message has been sent");

            } catch (Exception ex) { ex.printStackTrace();}
        }
    }

    public void Button_server_Chat()
    {
        byte[] receiveData = new byte[1024];
        if(true)
        {

            try
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());

                try
                {
                    Area_Chat.appendText( sentence+"\n");
                }
                catch (Exception ex)
                { ex.printStackTrace();}

                Status.setText("You Received Message From IP=  " +  receivePacket.getAddress().toString()+ "  , Port it "+receivePacket.getPort());

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }


    }

    public void Send_To_Group()
    {
        array.add(55);
        array.add(66);
        byte[] sendData = new byte[1024];
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
     for(int i =0 ; i< 1 ; i++) {

         try {

             System.out.println(array.get(i));
             int po = (int) array.get(i);
             InetSocketAddress add = new InetSocketAddress(Remote_IP.getText(), po);


             String sent = Local_Port.getText().toString() + ">>" + Area_Text.getText();
             sendData = sent.getBytes();
             sendPacket.setData(sendData);


             sendPacket.setSocketAddress(add);
             clientSocket.send(sendPacket);
         } catch (Exception ex) {
         }
     }

             Area_Chat.appendText("you"+">>"+Area_Text.getText()+"\n");

             Area_Text.setText("");

             JOptionPane.showMessageDialog(null, "send to all user");



         }

    public void setIP_List() throws SocketException {
        comboBox.getItems().clear();
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while (e.hasMoreElements()) {

            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                String s = i.getHostAddress().toString();
                if (s.charAt(0) == '1') {
                    options1.add(i.getHostAddress());
                    comboBox.setItems(options1);
                }

            }
        }
    }

}
