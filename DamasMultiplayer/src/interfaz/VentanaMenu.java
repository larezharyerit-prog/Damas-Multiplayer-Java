package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.io.InputStream;
import conexion.ConexionRed;

public class VentanaMenu extends javax.swing.JFrame {

    private Font fuentePrincipal;
    private Font fuenteSecundaria;
    private Color colorTitulo;
    private Color colorEtiquetas;
    private Color colorBotonesTxt;

    private Color colorFondoIni = Color.WHITE;
    private Color colorFondoFin = new Color(180, 225, 240);

    private JTextField txtIP;
    private JButton btnHost;
    private JButton btnJoin;

    public VentanaMenu() {
        setTitle("Menu Principal - Damas");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        BackgroundPanel panelPrincipal = new BackgroundPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        setContentPane(panelPrincipal);

        inicializarEstilos();

        panelPrincipal.add(Box.createVerticalGlue());

        HeaderPanel panelHeader = new HeaderPanel();
        panelHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(panelHeader);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 40)));

        configurarFormulario(panelPrincipal);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        configurarBotones(panelPrincipal);

        panelPrincipal.add(Box.createVerticalGlue());

        configurarEventos();
    }

    private void inicializarEstilos() {
        try {
            InputStream is = getClass().getResourceAsStream("/fuentes/ArchivoBlack-Regular.ttf");

            if (is != null) {
                Font fuenteBase = Font.createFont(Font.TRUETYPE_FONT, is);

                fuentePrincipal = fuenteBase.deriveFont(64f);

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(fuentePrincipal);

            } else {
                fuentePrincipal = new Font("Arial", Font.BOLD, 36);
            }
        } catch (Exception e) {
            fuentePrincipal = new Font("Arial", Font.BOLD, 36);
        }

        fuenteSecundaria = new Font("Arial", Font.PLAIN, 16);

        colorTitulo = new Color(0, 167, 147);
        colorEtiquetas = Color.BLACK;
        colorBotonesTxt = Color.BLACK;
    }

    ;

    private void configurarFormulario(JPanel contenedor) {
        JLabel lblIP = new JLabel("Ingrese el IP del Servidor");
        lblIP.setFont(fuenteSecundaria);
        lblIP.setForeground(colorEtiquetas);
        lblIP.setAlignmentX(CENTER_ALIGNMENT);
        contenedor.add(lblIP);

        contenedor.add(Box.createRigidArea(new Dimension(0, 10)));

        txtIP = new JTextField(15);
        txtIP.setMaximumSize(new Dimension(280, 40));
        txtIP.setHorizontalAlignment(JTextField.CENTER);
        txtIP.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenedor.add(txtIP);
    }

    private void configurarBotones(JPanel contenedor) {
        btnHost = new JButton("Nueva Partida");
        btnHost.setMaximumSize(new Dimension(280, 50));
        btnHost.setFont(fuenteSecundaria);
        btnHost.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(btnHost);

        contenedor.add(Box.createRigidArea(new Dimension(0, 15)));

        btnJoin = new JButton("Unirse a Partida");
        btnJoin.setMaximumSize(new Dimension(280, 50));
        btnJoin.setFont(fuenteSecundaria);
        btnJoin.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(btnJoin);
    }

    private void configurarEventos() {
        btnHost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexionRed red;
                //System.out.println("Servidor Iniciado. Esperando jugador...");
                red = new ConexionRed(VentanaMenu.this);
                
                Thread hiloRed = new Thread(red);
                hiloRed.start();
            }
        });
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexionRed red;
                String ip = txtIP.getText().trim();
                
                if(ip.isEmpty()){
                    JOPtionPanel.showMessageDIalog(VentanaMenu.this,
                            "Por favor, ingresa la IP del servidor.",
                            "IP Vacía", JOptionPane.WARNING_MESSAGE
                            );
                    return;
                }
                //System.out.println("Conectando a " + ip + "...");
                red = new ConexionRed(ip,VentanaMenu.this);
                
                Thread hiloRed = new Thread(red);
                hiloRed.start();
            }
        });
    }

    private class BackgroundPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gradient = new GradientPaint(0, 0, colorFondoIni, 0, getHeight(), colorFondoFin);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private class HeaderPanel extends JPanel {

        public HeaderPanel() {
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            setPreferredSize(new Dimension(400, 160));
            setMaximumSize(new Dimension(400, 160));

            JLabel lblSub = new JLabel("Let's Play!");
            Font fuenteSubtitulo = fuentePrincipal.deriveFont(20f);
            lblSub.setFont(fuenteSubtitulo);
            lblSub.setForeground(Color.BLACK);
            lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblTitulo = new JLabel("O Damas O");
            lblTitulo.setFont(fuentePrincipal.deriveFont(52f));
            lblTitulo.setForeground(colorTitulo);
            lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(lblSub);
            add(Box.createRigidArea(new Dimension(0, 0)));
            add(lblTitulo);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

