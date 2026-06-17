package interfaz;

import logicajuego.Tablero;

public class VentanaJuego extends javax.swing.JFrame {

    private final Tablero juego;

    private final javax.swing.JButton[][] botones = new javax.swing.JButton[8][8];

    private boolean primerClic = true; 
    private int filaOrigen = -1;
    private int colOrigen = -1;
    
    public VentanaJuego() {
        initComponents();
        this.juego = new Tablero();
        panelTablero.setPreferredSize(new java.awt.Dimension(512, 512));
        generarTableroGrafico();
        actualizarTableroGrafico();
        this.setSize(528, 550);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    private void generarTableroGrafico() {
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                botones[f][c] = new javax.swing.JButton();

                botones[f][c].setFocusPainted(false);
                botones[f][c].setBorderPainted(false);

                if ((f + c) % 2 == 0) {
                    botones[f][c].setBackground(new java.awt.Color(135, 237, 205));

                } else {
                    botones[f][c].setBackground(new java.awt.Color(39, 71, 67));
                }
                final int filaActual = f;
                final int colActual = c;
                
                botones[f][c].addActionListener(e -> {
                    gestionarClicTablero(filaActual, colActual);
                });
                
                panelTablero.add(botones[f][c]);
            }

        }
        panelTablero.revalidate();
        panelTablero.repaint();

    }

    public void actualizarTableroGrafico() {
        // Recorremos la matriz completa
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {

                // 1. Le preguntamos al motor lógico qué hay en esta casilla
                int ficha = juego.obtenerCasilla(f, c);

                // 2. Configuramos una fuente de texto grande y en negrita para las fichas
                botones[f][c].setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 50));

                // 3. Evaluamos la pieza matemática para asignarle su aspecto visual
                switch (ficha) {
                    case 0: // Casilla vacía
                        botones[f][c].setText("");
                        break;

                    case 1: // Ficha Normal - Jugador 1 (Abajo - por ejemplo, fichas Rojas)
                        botones[f][c].setText("●");
                        botones[f][c].setForeground(java.awt.Color.BLACK);
                        break;

                    case 2: // Ficha Normal - Jugador 2 (Arriba - por ejemplo, fichas Blancas/Negras)
                        botones[f][c].setText("●");
                        botones[f][c].setForeground(java.awt.Color.WHITE);
                        break;

                    case 3: // Reina - Jugador 1
                        botones[f][c].setText("★");
                        botones[f][c].setForeground(java.awt.Color.BLACK);
                        break;

                    case 4: // Reina - Jugador 2
                        botones[f][c].setText("★");
                        botones[f][c].setForeground(java.awt.Color.WHITE);
                        break;
                }
            }
        }
    }
    
    private void gestionarClicTablero(int f, int c) {
        if (primerClic) {
            // 1. PRIMER CLIC: Seleccionar la pieza de origen
            int pieza = juego.obtenerCasilla(f, c);
            
            // Validación rápida: No puede seleccionar una casilla vacía
            if (pieza == 0) {
                System.out.println("Casilla vacía. Selecciona una ficha válida.");
                return; 
            }
            
            filaOrigen = f;
            colOrigen = c;
            primerClic = false; // El siguiente clic será el destino
            
            // Efecto visual: Resaltamos el botón seleccionado cambiando su borde o fondo
            botones[f][c].setBackground(java.awt.Color.YELLOW); 
            
        } else {
            // 2. SEGUNDO CLIC: Seleccionar el destino y procesar el movimiento
            primerClic = true; // Reiniciamos el estado para la próxima jugada
            
            // Restauramos el color original del botón de origen ( recalculando si era claro o oscuro)
            if ((filaOrigen + colOrigen) % 2 == 0) {
                botones[filaOrigen][colOrigen].setBackground(new java.awt.Color(240, 217, 181));
            } else {
                botones[filaOrigen][colOrigen].setBackground(new java.awt.Color(181, 136, 99));
            }
            
            // Mandamos los datos al motor lógico que programaste antes
            boolean movio = juego.moverFicha(filaOrigen, colOrigen, f, c);
            
            if (movio) {
                // Si la lógica dio luz verde, redibujamos las fichas en la pantalla
                actualizarTableroGrafico();
                
                // Verificamos si este movimiento causó que alguien ganara
                int ganador = juego.verificarGanador();
                if (ganador != 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "¡El Jugador " + ganador + " ha ganado la partida!");
                }
            } else {
                System.out.println("Movimiento inválido según las reglas del backend.");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTablero = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Damas Multiplayer");
        setPreferredSize(new java.awt.Dimension(550, 600));

        panelTablero.setMinimumSize(new java.awt.Dimension(500, 500));
        panelTablero.setPreferredSize(new java.awt.Dimension(500, 500));
        panelTablero.setLayout(new java.awt.GridLayout(8, 8));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelTablero;
    // End of variables declaration//GEN-END:variables
}
