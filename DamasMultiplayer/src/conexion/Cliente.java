/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import interfaz.PanelDeJuego;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Veronica
 */
public class Cliente extends Thread { //hereda de thread para que pueda correr en segundo plano
        
        
            private final int PUERTO = 5000;
            private Socket sc = null;
            private DataInputStream in;
            private DataOutputStream out;
            private final PanelDeJuego paneljuego;
            private final String ipHost;
            
            
//con el constructor recibe tu pantalla de juego y la IP del servidor
    public Cliente(PanelDeJuego paneljuego, String ipHost) {
        this.paneljuego = paneljuego;
        this.ipHost = ipHost;
    } 
         
    
    @Override
    public void run() {
        try {
            // el cliente ejecuta al Socket directamente para intentar conectar con la IP y el Puerto
            sc = new Socket(ipHost, PUERTO);
            System.out.println("Conectado con éxito al servidor en la IP: " + ipHost);
            
            //aqui se abren los canales de comunicacion
            in = new DataInputStream(sc.getInputStream()); 
            out = new DataOutputStream(sc.getOutputStream());
            
            // se queda esperando las jugadas del servidor
            while (true) {
                int fOrigen = in.readInt();
                int cOrigen = in.readInt();
                int fDestino = in.readInt();
                int cDestino = in.readInt();
                
                //se le envian los datos del participante al tablero
                paneljuego.RecibirMovimientoParticipante(fOrigen, cOrigen, fDestino, cDestino);
            }
           
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error de conexión. ¿El servidor está encendido?");
        }
    }
    
   
    public void EnviarMovimiento(int fOrig, int cOrig, int fDest, int cDest) {
        try {
            if (out != null) {
                out.writeInt(fOrig);
                out.writeInt(cOrig);
                out.writeInt(fDest);
                out.writeInt(cDest);
                out.flush(); 
            }
        } catch (IOException e) {
            System.out.println("Error al enviar jugada al servidor: " + e.getMessage());
        }
    }
} 