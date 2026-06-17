/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
//se importa las librerias
import interfaz.PanelDeJuego;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
 import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread {     
            //se crea las clases
            private ServerSocket servidor = null;
            private Socket sc = null; //socket del cliente
            //DataInput y Output sirve para la comunicacion
            private DataInputStream in;
            private DataOutputStream out;
            private final PanelDeJuego paneljuego;
            //colocacion del puerto
            private final int PUERTO = 5000;
            
            
          //aqui el contructor recibe el panel de juego para comunicarse con el
        public Servidor(PanelDeJuego paneljuego) {
        this.paneljuego = paneljuego;
    }
         
    @Override
    public void run() {
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor Iniciado en el puerto " + PUERTO + ". Esperando participante");
            
            sc = servidor.accept(); //se queda esperando hasta que el cliente se conecte
            System.out.println("Participante conectado con éxito.");
            
            in = new DataInputStream(sc.getInputStream()); 
            out = new DataOutputStream(sc.getOutputStream());
            
            
            while (true) {
                // el servidor se queda esperando que el participante mande sus 4 coordenadas por el cable
                int fOrigen = in.readInt();
                int cOrigen = in.readInt();
                int fDestino = in.readInt();
                int cDestino = in.readInt();
                
                // le envia los datos al PanelDeJuego para que actualice la lógica y los botones
                paneljuego.RecibirMovimientoParticipante(fOrigen, cOrigen, fDestino, cDestino);
            }
           
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void EnviarMovimiento(int fOrig, int cOrig, int fDest, int cDest) {
        try {
            if (out != null) {
                out.writeInt(fOrig);
                out.writeInt(cOrig);
                out.writeInt(fDest);
                out.writeInt(cDest);
                out.flush(); // asegura que los datos salgan por el cable
            }
        } catch (IOException e) {
            System.out.println("Error al enviar jugada al participante: " + e.getMessage());
        }
    
     }
}