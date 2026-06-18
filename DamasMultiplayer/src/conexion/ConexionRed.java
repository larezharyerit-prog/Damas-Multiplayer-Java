/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

/**
 *
 * @author Veronica
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import interfaz.*;

public class ConexionRed implements Runnable {

    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    
    private String ip;
    private final int PUERTO = 5000;
    private boolean esServidor;
    private boolean miTurno;
    
    private VentanaMenu ventanaMenu; // Para poder cerrarla al conectar
    private VentanaJuego nuevaVentanaJuego; // La nueva partida en red

    // Constructor si actúas como Cliente (recibe la IP del formulario)
    public boolean isServidor() {
        return this.esServidor;
    }
    public ConexionRed(String ip,VentanaMenu menu) {
        this.ip = ip;
        this.ventanaMenu = menu;
        this.esServidor = false;
        this.miTurno = false; // El cliente espera
    }

    // Constructor si actúas como Servidor (IP vacía, esperas conexión)
    public ConexionRed(VentanaMenu menu) {
        this.ventanaMenu = menu;
        this.esServidor = true;
        this.miTurno = true; // El servidor arranca
    }

    @Override
    public void run() {
        try {
            if (esServidor) {
                System.out.println("Esperando jugador en el puerto " + PUERTO + "...");
                ServerSocket serverSocket = new ServerSocket(PUERTO);
                socket = serverSocket.accept(); // Se detiene aquí hasta que alguien se conecte
                System.out.println("¡Jugador conectado desde: " + socket.getInetAddress() + "!");
            } else {
                System.out.println("Conectando al servidor en " + ip + ":" + PUERTO + "...");
                socket = new Socket(ip, PUERTO);
                System.out.println("¡Conexión establecida con éxito!");
            }

            // Inicializar los flujos de datos para comunicarse
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());

            java.awt.EventQueue.invokeLater(() -> {
                ventanaMenu.dispose(); // Cierra el menú de la IP
                
                // Creamos una NUEVA instancia de la VentanaJuego pasándole la red y el turno
                nuevaVentanaJuego = new VentanaJuego(this, miTurno);
                nuevaVentanaJuego.setVisible(true);
            });

            // Bucle infinito escuchando los movimientos del oponente
            while (socket != null && !socket.isClosed()) {
                String mensaje = entrada.readUTF();
                
                // Le pasamos el string de red a la nueva ventana activa
                java.awt.EventQueue.invokeLater(() -> {
                    nuevaVentanaJuego.recibirMovimientoRed(mensaje);
                });
            }

        } catch (Exception e) {
            System.err.println("Error en la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para enviar datos (ej. un movimiento de ficha)
    public void enviarMovimiento(String datosMovimiento) {
        try {
            if (salida != null) {
                salida.writeUTF(datosMovimiento);
                salida.flush();
            }
        } catch (Exception e) {
            System.err.println("Error al enviar datos: " + e.getMessage());
        }
    }
}