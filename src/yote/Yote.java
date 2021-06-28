/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author roger
 */
public class Yote extends Application {
    
    private static String serverIP = "127.0.0.1";
    private static final int serverPort = 6666;
    static DataInputStream in;
    static DataOutputStream out;
    String boas = "boas";
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        connectClient();
        
        
    }
 private void connectClient() throws IOException {

        Socket socket = new Socket(serverIP, serverPort);

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
  // Thread que serve para o cliente envia mensagens para o servidor
        Thread enviarMensagem = new Thread(() -> {
            while (true) {
                try {
                                out.writeUTF(boas);
                               
                               
                            } catch (IOException ex) {
                                Logger.getLogger(Yote.class.getName()).log(Level.SEVERE, null, ex);
                            }
            }
        });
        
         Thread lerMensagem;
        lerMensagem = new Thread(() -> {
            while (true) {

              
                    String msg;
                try {
                    msg = in.readUTF();
                    if(msg.contains("boas")){
                       FXMLDocumentController.referencia.setText(msg);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Yote.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
               
            }
        });
 }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
