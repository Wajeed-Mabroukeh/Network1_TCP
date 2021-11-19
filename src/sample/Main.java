package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.net.*;

public class Main extends Application {

    //All TextFiled
    public javafx.scene.control.TextField TCP_Server_Port;
    public TextField TCP_Server_IP;
    public TextField Local_IP;
    public TextField Local_Port;
    public TextField Remote_IP;
    public TextField Remote_Port;
    public TextField Status;

    //Button send
    public Button Send;

    //Areas Text
    public TextArea Area_Users;
    public TextArea Area_Chat;
    public TextArea Area_Text;

    public TextField username;



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Interface.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 560));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    public void Button_send_Chat()
    {

        if(Remote_Port.getText().equals("") && Remote_IP.getText().equals("")){
            JOptionPane.showMessageDialog(null, "please enter port number and ip address for remote");


        }
        else{


            try {


                int po = Integer.parseInt(Remote_Port.getText());
                InetSocketAddress add = new InetSocketAddress(Remote_IP.getText(),po);

                byte[] sendData = new byte[1024];
                if(username.getText().equals("")){
                    String sent=Local_Port.getText().toString()+">>"+Area_Text.getText();
                    sendData = sent.getBytes();
                }
                else{
                    String sent=username.getText().toString()+">>"+Area_Text.getText();
                    sendData = sent.getBytes();

                }





                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length);

                sendPacket.setSocketAddress(add);
                clientSocket.send(sendPacket);


                Style style = data.addStyle("", null);
                StyleConstants.setForeground(style, Color.red);

                StyledDocument doc =data.getStyledDocument();
                doc.insertString(doc.getLength(), "you"+">>"+msg.getText()+"\n", style);



                msg.setText("");

                JOptionPane.showMessageDialog(null, "تم الارسال");





            }   catch (Exception ex) {
                Logger.getLogger(ptop.class.getName()).log(Level.SEVERE, null, ex);
            } }
        TCP_Server_IP.setText("dcd");
        UDP_Client Client = new UDP_Client();
        UDP_Server Server = new UDP_Server();


    }

    public  String  FindCourses(String Key)
    {
        try
        {
            Area_Chat.appendText(Area_Text.getText());
            Area_Text.setText("");
        }
        catch (Exception hh)
        {
            System.out.println("An error occurred.");
            hh.printStackTrace();
        }
        return "Error 404";
    }


}
