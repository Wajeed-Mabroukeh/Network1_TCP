package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;



public class control {

    //All TextFiled
    public javafx.scene.control.TextField TCP_Server_Port;
    public TextField TCP_Server_IP;
    public TextField Local_IP;
    public TextField Local_Port;
    public TextField Remote_IP;
    public TextField Remote_Port;
    public TextField Status;
    public TextField Username;

    //Button send
    public Button Send;

    //Areas Text
    public TextArea Area_Users;
    public TextArea Area_Chat;
    public TextArea Area_Text;



    public DatagramSocket clientSocket;

//   public  control() throws SocketException {
        
//Enumeration e = NetworkInterface.getNetworkInterfaces();
//while(e.hasMoreElements())
//    {
//
//        NetworkInterface n = (NetworkInterface) e.nextElement();
//        Enumeration ee = n.getInetAddresses();
//        while (ee.hasMoreElements())
//        {
//            InetAddress i = (InetAddress) ee.nextElement();
//            String s= i.getHostAddress().toString();
//            if(s.charAt(0)=='1'){
//             //  Area_Users.setText(i.getHostAddress());
//System.out.println(i.getHostAddress());
//                // count ++;
//            }
//
//
//        }


   // }
  // }
    public void initialing() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Interface.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 560));
        primaryStage.show();

    }


    public void Button_Test_Chat() {
        if (Local_Port.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "please enter port number");
        } else {
            int por = Integer.parseInt(Local_Port.getText());
            try {
                clientSocket = new DatagramSocket(null);

                if (Local_IP.getText().equals("")) {

                    Local_IP.setText("127.0.0.1");
                    JOptionPane.showMessageDialog(null, "your local ip is : " + "127.0.0.1");

                }

                //SocketAddress addr = new  SocketAddress(por , ip.getText());
                InetSocketAddress addr = new InetSocketAddress(Local_IP.getText(), por);
                clientSocket.setReuseAddress(true);
                clientSocket.bind(addr);

                JOptionPane.showMessageDialog(null, "test is successfully");
                Local_IP.setEditable(false);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }

        }


    }


    public void Button_send_Chat()
    {
        if (Remote_Port.getText().equals("") && Remote_IP.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "please enter port number and ip address for remote");

        } else {


            try {
                //int por = Integer.parseInt(port.getText());

                //clientSocket = new DatagramSocket(por);

                int po = Integer.parseInt(Remote_Port.getText());
                InetSocketAddress add = new InetSocketAddress(Remote_IP.getText(), po);

                byte[] sendData = new byte[1024];
                if (Username.getText().equals("")) {
                    String sent = Local_Port.getText().toString() + ">>" + Area_Text.getText();
                    sendData = sent.getBytes();
                } else {
                    String sent = Username.getText().toString() + ">>" + Area_Text.getText();
                    sendData = sent.getBytes();

                }

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);

                sendPacket.setSocketAddress(add);
                clientSocket.send(sendPacket);


//                Style style = Area_Chat.addStyle("", null);
//                StyleConstants.setForeground(style, Color.red);

//                StyledDocument doc = Area_Chat.getStyledDocument();
//                doc.insertString(doc.getLength(), "you" + ">>" + Area_Text.getText() + "\n", style);
                Area_Chat.appendText("you" + ">>" + Area_Text.getText() + "\n");

                Area_Text.setText("");

                JOptionPane.showMessageDialog(null, "تم الارسال");


            } catch (Exception ex) {

            }
        }
    }

}
