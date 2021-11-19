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

import java.awt.*;

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
