package interfaz;

import logicajuego.Tablero;

public class VentanaJuego extends javax.swing.JFrame {

    private final Tablero juego;

    private final javax.swing.JButton[][] botones = new javax.swing.JButton[8][8];

    private javax.swing.ImageIcon fichaJ1Normal;
    private javax.swing.ImageIcon fichaJ2Normal;
    private javax.swing.ImageIcon fichaJ1Reina;
    private javax.swing.ImageIcon fichaJ2Reina;
    
    private javax.swing.JLabel lblTurnoFicha;
    private javax.swing.JButton btnPartidaNueva;

    private boolean primerClic = true;
    private int filaOrigen = -1;
    private int colOrigen = -1;
            
    private javax.swing.JPanel panelTablero;;
    
    public VentanaJuego() {
        
        setTitle("Damas Multiplayer");
        
        this.juego = new Tablero();
        
        this.panelTablero = new javax.swing.JPanel(new java.awt.GridLayout(8, 8));
        
        this.getContentPane().setLayout(new java.awt.BorderLayout(20,0));
        this.getContentPane().setBackground(new java.awt.Color(240,248,247));
        
        panelTablero.setPreferredSize(new java.awt.Dimension(512, 512));
        try {
            fichaJ1Normal = new javax.swing.ImageIcon(getClass().getResource("/imagenes/fichaNegra.png"));
            fichaJ2Normal = new javax.swing.ImageIcon(getClass().getResource("/imagenes/fichaTurquesa.png"));
            fichaJ1Reina = new javax.swing.ImageIcon(getClass().getResource("/imagenes/reinaNegra.png"));
            fichaJ2Reina = new javax.swing.ImageIcon(getClass().getResource("/imagenes/reinaTurquesa.png"));
        } catch (Exception e){
            System.out.println("No se encontraron las imagenes");
        }
        generarTableroGrafico();
        
        panelTablero.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK, 2));
        this.getContentPane().add(panelTablero, java.awt.BorderLayout.CENTER);
        
        javax.swing.JPanel panelLateral = new javax.swing.JPanel();
        panelLateral.setOpaque(false);
        panelLateral.setLayout(new javax.swing.BoxLayout(panelLateral,javax.swing.BoxLayout.Y_AXIS));
        panelLateral.setPreferredSize(new java.awt.Dimension(160,512));
        javax.swing.border.Border bordeExterior=javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK, 2);
        javax.swing.border.Border bordeInterior = javax.swing.BorderFactory.createEmptyBorder(15, 10, 15, 10);
        panelLateral.setBorder(javax.swing.BorderFactory.createCompoundBorder(bordeExterior, bordeInterior));
        
        javax.swing.JLabel lblTextoTurno = new javax.swing.JLabel("Turno de:");
        lblTextoTurno.setFont(new java.awt.Font("Arial Black", java.awt.Font.BOLD, 16));
        lblTextoTurno.setForeground(new java.awt.Color(39, 71, 67)); 
        lblTextoTurno.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        
        lblTurnoFicha = new javax.swing.JLabel();
        lblTurnoFicha.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
      
        btnPartidaNueva = new javax.swing.JButton("Partida Nueva");
        btnPartidaNueva.setFont(new java.awt.Font("Arial Black", java.awt.Font.PLAIN, 12));
        btnPartidaNueva.setMaximumSize(new java.awt.Dimension(140, 35));
        btnPartidaNueva.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnPartidaNueva.setFocusPainted(false);
        
        btnPartidaNueva.addActionListener(e -> {
            new VentanaMenu().setVisible(true);
            this.dispose();
        });
        
        panelLateral.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 40)));
        panelLateral.add(lblTextoTurno);
        panelLateral.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));
        panelLateral.add(lblTurnoFicha);
        panelLateral.add(javax.swing.Box.createVerticalGlue()); 
        panelLateral.add(btnPartidaNueva);
        panelLateral.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 30)));   
        
        this.getContentPane().add(panelLateral, java.awt.BorderLayout.EAST);
        
        this.setSize(700, 550);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        actualizarTableroGrafico();
    } 

    private void generarTableroGrafico() {
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                botones[f][c] = new javax.swing.JButton();

                // Quitar relieves nativos
                botones[f][c].setFocusPainted(false);
                botones[f][c].setBorderPainted(false);
                botones[f][c].setBorder(null);
                botones[f][c].setMargin(new java.awt.Insets(0,0,0,0));
                botones[f][c].setContentAreaFilled(true);
                botones[f][c].setOpaque(true);

                // Colores de tu paleta
                if ((f + c) % 2 == 0) {
                    botones[f][c].setBackground(new java.awt.Color(212, 255, 238));
                } else {
                    botones[f][c].setBackground(new java.awt.Color(31, 122, 107));
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
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                int ficha = juego.obtenerCasilla(f, c);
                botones[f][c].setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 50));

                switch (ficha) {
                    case 0: 
                        botones[f][c].setText("");
                        botones[f][c].setIcon(null);
                        break;
                    case 1: 
                        botones[f][c].setText("");
                        botones[f][c].setIcon(fichaJ1Normal);
                        break;
                    case 2: 
                        botones[f][c].setText("");
                        botones[f][c].setIcon(fichaJ2Normal);
                        break;
                    case 3: 
                        botones[f][c].setText("");
                        botones[f][c].setIcon(fichaJ1Reina);
                        break;
                    case 4: 
                        botones[f][c].setText("");
                        botones[f][c].setIcon(fichaJ2Reina);
                        break;
                }

                // ⚡ Lógica de turnos corregida con tu método real del backend
                if(juego != null && lblTurnoFicha != null){
                    int turno = juego.obtenerTurno(); 
                    
                    if (turno == 1){
                        lblTurnoFicha.setIcon(fichaJ1Normal);
                    } else {
                        lblTurnoFicha.setIcon(fichaJ2Normal);
                    }
                    
                    lblTurnoFicha.revalidate();
                    lblTurnoFicha.repaint();
                }
            }
        }
    }

    private void gestionarClicTablero(int f, int c) {
        if (primerClic) {
            int pieza = juego.obtenerCasilla(f, c);

            if (pieza == 0) {
                System.out.println("Casilla vacía. Selecciona una ficha válida.");
                return;
            }
            
            int turnoActual = juego.obtenerTurno();
            
            if(turnoActual == 1){
                if(pieza != 1 && pieza !=3){
                    System.out.println("Invalido. Es el turno del Jugador 1");
                    return;
                }
            }else if (turnoActual==2){
                if(pieza != 2 && pieza!=4){
                    System.out.println("Invalido. Es el turno del Jugador 2");
                }
            }
            
            filaOrigen = f;
            colOrigen = c;
            primerClic = false; 

            botones[f][c].setBackground(new java.awt.Color(205, 230, 170));

        } else {
            primerClic = true; 

            // Restauración exacta de colores basada en tu paleta actual
            if ((filaOrigen + colOrigen) % 2 == 0) {
                botones[filaOrigen][colOrigen].setBackground(new java.awt.Color(212, 255, 238));
            } else {
                botones[filaOrigen][colOrigen].setBackground(new java.awt.Color(31, 122, 107));
            }

            boolean movio = juego.moverFicha(filaOrigen, colOrigen, f, c);

            if (movio) {
                actualizarTableroGrafico();

                int ganador = juego.verificarGanador();
                if (ganador != 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "¡El Jugador " + ganador + " ha ganado la partida!");
                }
            } else {
                System.out.println("Movimiento inválido según las reglas del juego.");
            }
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

