package TCP;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public JList<String> list55=new JList<String>();
    public DefaultListModel<String> model = new DefaultListModel<>();

    public String AreaChat = "";
    public String AreaStats = "";

    public void Server()  {
        list55 =new JList<>(model);
        Button_send_Chat();
    }

    public void Button_send_Chat(){

        new Thread() {
            @Override
            public void run() {     try {
                ServerSocket server;
                //socket server port on which it will listen
                int port = 8888;
                server = new ServerSocket(port);
                AreaStats = InetAddress.getLocalHost()+" :"+port;
                // Area_status.setEditable(false);
                //keep listens indefinitely until receives 'exit' call or program terminates
                while(true){
                    System.out.println("Waiting for the client request");
                    //creating socket and waiting for client connection
                    Socket socket = server.accept();
                    //read from socket to ObjectInputStream object
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    //convert ObjectInputStream object to String
                    String message;

                    message = (String) ois.readObject();
                    if(message.contains("login")){
                        message =message.replace("login", "");
                        model.addElement(message);

                    }else if(message.contains("logout")){

                        message =message.replace("logout", "");
                        if( model.removeElement(message))
                            System.out.println("logout");
                    }
                    list55.setModel(model);
                    System.out.println("Message Received: " + message);
                    AreaChat = message;
                    //Tcp.set_Areacaht(message);
                    //create ObjectOutputStream object
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    //write object to Socket

                    oos.writeObject(model);
                    try {
                        online();
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //close resources
                    ois.close();
                    oos.close();
                    socket.close();
                    //terminate the server if client sends exit reque st
                    if(message.equalsIgnoreCase("exit")) break;
                }
                System.out.println("Shutting down Socket server!!");
                //close the ServerSocket object
                server.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }.start();

    }
    public String getAreaChat()
    {
        return  AreaChat;
    }

    public void online() throws CloneNotSupportedException{

        for(int i=0;i<model.getSize();i++){
            try {
                InetAddress host;

                host = InetAddress.getLocalHost();

                Socket socket = null;
                ObjectOutputStream oos = null;
                ObjectInputStream ois = null;


                String[] stringarry = model.get(i).split(":");
                String ports =stringarry[1];
                //  System.out.println(ports);
                DefaultListModel<String> modeltemp = new DefaultListModel<>();
                for(Object obj :model.toArray()){
                    modeltemp.addElement((String) obj);
                }

                modeltemp.removeElement(model.get(i));
                //establish socket connection to server
                socket = new Socket(host.getHostName(), Integer.valueOf(ports));

                //write to socket using ObjectOutputStream
                oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Sending request to Socket Server");
                // if(i==4)oos.writeObject("exit");
                oos.writeObject(modeltemp );
                //read the server response message
                ois = new ObjectInputStream(socket.getInputStream());
                String message;

                modeltemp =  (DefaultListModel<String>) ois.readObject();
                // login = true;
                //  System.out.println("Message: " + message);
                //jList1.setModel(model);
                //close resources
                ois.close();
                oos.close();
                Thread.sleep(100);

            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, ex);
                Logger.getLogger(Control_TCP.class.getName()).log(Level.SEVERE, null, ex);
            }catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
                Logger.getLogger(Control_TCP.class.getName()).log(Level.SEVERE, null, ex);
            }catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex);
                Logger.getLogger(Control_TCP.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
                Logger.getLogger(Control_TCP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().Server();

            }
        });
    }
}
