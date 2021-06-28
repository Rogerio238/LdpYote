/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author senho
 */
public class Server {
    private static int port = 6666, nClientes = 1;
    private static Vector<ClientHandler> listaClientes = new Vector<>();
    private static Socket client;
    static int i = 0;
    
      public static void main(String[] args) throws IOException {
 System.out.println("Servidor aceita conexões.");
 ServerSocket ss = new ServerSocket(1234);

 Socket s;
 while(true){
 s = ss.accept();
 System.out.println("Novo client recebido : " + s);

 DataInputStream dis = new DataInputStream(s.getInputStream());
 DataOutputStream dos = new DataOutputStream(s.getOutputStream());

 ClientHandler mtch = new ClientHandler(s, "client " + i, dis, dos);
 Thread t = new Thread(mtch);

 System.out.println("Adiciona cliente "+ i + " à lista ativa.");
 listaClientes.add(mtch);
 t.start();

 i++;
 }
 }
    
    private static class ClientHandler implements Runnable {
            private String name;
            final DataInputStream dis;
                final DataOutputStream dos;
                Socket s;
                boolean isloggedin;
                private ClientHandler(Socket s, String string,
            DataInputStream dis, DataOutputStream dos) {
                this.s = s;
                this.dis = dis;
                this.dos = dos;
                    this.name = string;
               this.isloggedin = true;
 }
 
 @Override
 public void run() {
 String recebido;

 while(true){
 try {
            recebido = dis.readUTF();
            System.out.println(recebido);
            if(recebido.endsWith("logout")){
            this.isloggedin = false;
                this.s.close();
                    break; // while
 }
 /**/
            StringTokenizer st = new StringTokenizer(recebido, "#");
            String MsgToSend = st.nextToken();
            String recipient = st.nextToken();
                for(ClientHandler mc: Server.listaClientes){
                    if(mc.name.equals(recipient) && mc.isloggedin) {
                            mc.dos.writeUTF("boas");
                     break;
        }
 }


 } catch (IOException e) {
                 e.printStackTrace();
 }

 }

 }
 }
}
