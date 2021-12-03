package TCP;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Control_TCP {

    //All TextFiled
    public javafx.scene.control.TextField TCP_Server_Port = new TextField();
    public TextField TCP_Server_IP = new TextField();
    public TextField Local_IP = new TextField();
    public TextField Local_Port = new TextField();
    public TextField Remote_IP = new TextField();
    public TextField Remote_Port = new TextField();
    public TextField Status = new TextField();
    public TextField Username = new TextField();
    public TextField Remote_Port1 = new TextField();

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
    public JList<String> list55 = new JList<String>();

    public Channel channel = new Channel();
    public InetSocketAddress address = null;
    public String name = "";
    public DefaultListModel<String> model = new DefaultListModel<>();
    public Server hh = new Server();


    public void set_Areacaht() {
        Area_Chat.appendText(hh.getAreaChat());
        Status.appendText(hh.getAreaChat());

    }

    public void initialing() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Interface1.fxml"));
        primaryStage.setTitle("Chatting");
        primaryStage.setScene(new Scene(root, 850, 560));
        primaryStage.show();


    }


    public void Button_Test_Chat() {
        // if (Local_IP.getText().equals(""))
        //  {
        // Local_IP.setText(comboBox.getValue().toString());
        //  Remote_IP.setText(comboBox.getValue().toString());
        //JOptionPane.showMessageDialog(null, "Your local ip is : " + comboBox.getValue().toString());

        //}

        int sourcePort = 0;
        String destinationIP = "";
        int destinationPort = 0;
        if (!Remote_IP.getText().equals(destinationIP) || Integer.parseInt(Local_Port.getText()) != sourcePort || Integer.parseInt(Remote_Port.getText()) != destinationPort)
            try {
                // name = Username.getText();

                sourcePort = Integer.parseInt(Local_Port.getText());

                destinationIP = Remote_IP.getText();

                destinationPort = Integer.parseInt(Remote_Port.getText());


                channel.bind(sourcePort);
                channel.start(); // Start Receive
                address = new InetSocketAddress(destinationIP, destinationPort);

                // Status.setText(name);
            } catch (SocketException ex) {
                Logger.getLogger(Control_TCP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
                Logger.getLogger(Control_TCP.class.getName()).log(Level.SEVERE, null, ex);
            }
    }


    public void Button_send_Chat() {
        if (Remote_Port.getText().equals("") && Remote_IP.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "please enter port number and ip address for remote");
        } else {
            try {

                String msg = Area_Text.getText();
                if (!msg.isEmpty()) {
                    msg = Local_Port.getText() + " >> " + msg;
                    try {
                        channel.sendTo(address, msg);

                    } catch (IOException ex) {
                        Logger.getLogger(Control_TCP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(msg);

                }

                if (!Username.getText().equals("")) {
                    Area_Chat.appendText(Username.getText() + ">>" + Area_Text.getText() + "\n");
                    Area_Text.setText("");
                    JOptionPane.showMessageDialog(null, "Message has been sent");
                } else {
                    Area_Chat.appendText(Local_Port.getText() + ">>" + Area_Text.getText() + "\n");
                    Area_Text.setText("");
                    JOptionPane.showMessageDialog(null, "Message has been sent");
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (Local_Port.getText().equals("") || Local_IP.getText().equals(""))
            JOptionPane.showMessageDialog(null, " Enter your Adress ");
        else {
            try {
                InetAddress host = InetAddress.getLocalHost();
                Socket socket = null;
                ObjectOutputStream oos = null;
                ObjectInputStream ois = null;
                //establish socket connection to server
                socket = new Socket(host.getHostName(), Integer.valueOf(TCP_Server_Port.getText()));
                //write to socket using ObjectOutputStream
                oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Sending request to Socket Server");
                // if(i==4)oos.writeObject("exit");
                oos.writeObject("login" + Local_IP.getText() + ":" + Local_Port.getText());
                //read the server response message
                ois = new ObjectInputStream(socket.getInputStream());
                // String message;
                model = (DefaultListModel<String>) ois.readObject();
                //login = true;
                //  System.out.println("Message: " + message);
                list55.setModel(model);
                ois.close();
                oos.close();
                // Thread.sleep(100);

//                } catch (InterruptedException ex) {
//                    JOptionPane.showMessageDialog(null, ex);
                //login = false;
                //Logger.getLogger(chat.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                //login = false;
                JOptionPane.showMessageDialog(null, ex);
                //Logger.getLogger(chat.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                //login = false;
                JOptionPane.showMessageDialog(null, ex);
                //Logger.getLogger(chat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        public void Button_server_Chat()
        {

//            new Thread() {
//                @Override
//                public void run() {

                    try {
                        ServerSocket server;
                        //socket server port on which it will listen
                        int port = Integer.valueOf(Local_IP.getText());
                        server = new ServerSocket(port);
                        //keep listens indefinitely until receives 'exit' call or program terminates
                        while (true) {
                            System.out.println("Waiting for the client request");
                            //creating socket and waiting for client connection
                            Socket socket = server.accept();
                            //read from socket to ObjectInputStream object
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            //convert ObjectInputStream object to String
                            // String message;
                            set_Areacaht();
                            System.out.println(ois.readUTF());
                            System.out.println(name);

                            model = (DefaultListModel<String>) ois.readObject();

                            list55.setModel(model);


                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            //write object to Socket

                            oos.writeObject(model);

                            //close resources
                            ois.close();
                            oos.close();
                            socket.close();
                            //terminate the server if client sends exit reque st
                            // if(message.equalsIgnoreCase("exit")) break;
                        }
                        // System.out.println("Shutting down Socket server!!");
                        //close the ServerSocket object

                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, ex);
                    } catch (Exception ex) {

                        // Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, ex);
                    }

                }
           // }.start();



       public void Send_To_Group()
        {
//            int po;
//            byte[] sendData = new byte[1024];
//            String sent = "";
//            InetSocketAddress add;
//            DatagramPacket sendPacket;
//            String c = "";
//            array.add(Remote_Port.getText());
//            array.add(Remote_Port1.getText());
//
//
//            if(Username.getText().equals(""))
//            {
//                Area_Chat.appendText("you" + ">>" + Area_Text.getText() + "\n");
//            }
//            else
//            {
//                Area_Chat.appendText(Username.getText() + ">>" + Area_Text.getText() + "\n");
//
//            }
//
//            for (int i = 0; i < 2; i++)
//            {
//                try {
//                    po = Integer.parseInt(String.valueOf(array.get(i)));
//                    add = new InetSocketAddress(Remote_IP.getText(), po);
//                    sent = Local_Port.getText().toString() + ">>" + Area_Text.getText();
//                    sendData = sent.getBytes();
//                    sendPacket = new DatagramPacket(sendData, sendData.length);
//                    sendPacket.setSocketAddress(add);
//                    clientSocket.send(sendPacket);
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//            Area_Text.setText("");
//            JOptionPane.showMessageDialog(null, "send to all user");
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



        //////////
        private class Channel implements  Runnable {

            private DatagramSocket socket;
            private boolean running;
            private String msg = "a";
            //public chat ch = new chat();


            public void bind(int port) throws SocketException
            {
                socket = new DatagramSocket(port);
            }

            public void start()
            {
                Thread thread = new Thread(this);
                thread.start();
            }

            public void stop()
            {
                running = false;
                socket.close();
            }

            @Override
            public void run()
            {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                running = true;
                while(running)
                {
                    try
                    {
                        clientSocket.receive(packet);
                        msg = new String(buffer, 0, packet.getLength());
                        String msg2= "Received from:IP="+packet.getAddress()+", Port ="+packet.getPort();
                        Status.setText(msg2);
                    }
                    catch (IOException e)
                    {
                        break;
                    }
                }
            }
            public  String getmessege(){
                return msg;
            }

            public void sendTo(InetSocketAddress address, String msg) throws IOException
            {
                byte[] buffer = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                packet.setSocketAddress(address);
                socket.send(packet);
            }
        }
    }

