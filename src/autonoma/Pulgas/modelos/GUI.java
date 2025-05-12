package autonoma.Pulgas.modelos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase que maneja la interfaz gráfica del juego.
 * Simula la batalla contra las pulgas locas y mutantes.
 */
public class GUI {
    private Juego juego;
    private JFrame ventana;
    private PanelJuego panelJuego;
    private JLabel lblPuntaje;
    private JLabel lblMaxPuntaje;
    private JButton btnReiniciar;
    private JLabel lblInstrucciones;
    private Timer timer;
    
    // Frecuencia de actualización en milisegundos (reducida para mayor fluidez)
    private static final int FRECUENCIA_ACTUALIZACION = 20; // 50 FPS.
    
    /**
     * Constructor para crear una nueva interfaz gráfica.
     * 
     * @param juego Juego a mostrar.
     */
    public GUI(Juego juego) {
        this.juego = juego;
        inicializarComponentes();
        inicializarEventos();
    }
    
    /**
     * Inicializa los componentes de la interfaz grafica...
     */
    private void inicializarComponentes() {
        // Crear ventana principal
        ventana = new JFrame("Simulador Antipulgas");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());
        
        // Panel de juego
        panelJuego = new PanelJuego(juego, juego.getCampo());
        ventana.add(panelJuego, BorderLayout.CENTER);
        
        // Panel de información y controles
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new GridLayout(2, 1));
        panelControles.setBackground(new Color(200, 220, 240));
        
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSuperior.setBackground(new Color(200, 220, 240));
        
        // Etiquetas de puntaje
        lblPuntaje = new JLabel("Pulgas Eliminadas: 0");
        lblPuntaje.setFont(new Font("Arial", Font.BOLD, 14));
        lblMaxPuntaje = new JLabel("Máximo: " + juego.getMaxPuntaje());
        lblMaxPuntaje.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Botón de reinicio
        btnReiniciar = new JButton("Reiniciar Simulacion");
        
        // Añadir componentes al panel superior
        panelSuperior.add(lblPuntaje);
        panelSuperior.add(lblMaxPuntaje);
        panelSuperior.add(btnReiniciar);
        
        // Panel para instrucciones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(new Color(200, 220, 240));
        
        lblInstrucciones = new JLabel("<html><b>Controles:</b> [p] Pulga normal | [m] Pulga mutante | [s] Saltar | [ESPACIO] Pulgoson | [q] Salir</html>");
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panelInferior.add(lblInstrucciones);
        
        panelControles.add(panelSuperior);
        panelControles.add(panelInferior);
        
        ventana.add(panelControles, BorderLayout.SOUTH);
        
        // Configurar timer para actualizar la interfaz - frecuencia aumentada
        timer = new Timer(FRECUENCIA_ACTUALIZACION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarInterfaz();
            }
        });
        
        // Ajustar el tamaño y hacer visible
        ventana.pack();
        ventana.setLocationRelativeTo(null); // Centrar en pantalla
        ventana.setResizable(false);
        ventana.setVisible(true);
    }
    
    // El resto de la clase se mantiene igual
    // (métodos inicializarEventos, actualizarInterfaz, iniciar)
    
    /**
     * Inicializa los eventos de la interfaz gráfica.
     */
    private void inicializarEventos() {
        // Evento de clic en el panel (disparo de pistola Pulguipium)
        panelJuego.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (juego.esJuegoActivo() && SwingUtilities.isLeftMouseButton(e)) {
                    juego.dispararPistola(e.getX(), e.getY());
                }
            }
        });
        
        // Evento del botón reiniciar
        btnReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                juego.reiniciar();
            }
        });
        
        // Eventos de teclado
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    switch (e.getKeyChar()) {
                        case 'p': // Agregar pulga normal
                            if (juego.esJuegoActivo()) {
                                juego.agregarPulgaNormal();
                            }
                            break;
                        case 'm': // Agregar pulga mutante
                            if (juego.esJuegoActivo()) {
                                juego.agregarPulgaMutante();
                            }
                            break;
                        case 's': // Hacer saltar a las pulgas
                            if (juego.esJuegoActivo()) {
                                juego.hacerSaltarPulgas();
                            }
                            break;
                        case 'q': // Terminar la simulación
                            if (juego.esJuegoActivo()) {
                                juego.finalizar();
                            }
                            break;
                        case ' ': // Barra espaciadora - Lanzar misil Pulgoson
                            if (juego.esJuegoActivo()) {
                                juego.dispararMisil();
                            }
                            break;
                    }
                }
                return false;
            }
        });
        
        // Evento de cierre de ventana
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (juego.esJuegoActivo()) {
                    juego.finalizar();
                }
                timer.stop();
            }
        });
    }
    
    /**
     * Actualiza la interfaz gráfica con los cambios en el juego.
     */
    private void actualizarInterfaz() {
        // Actualizar etiquetas de puntaje
        lblPuntaje.setText("Pulgas Eliminadas: " + juego.getPuntaje());
        lblMaxPuntaje.setText("Máximo: " + juego.getMaxPuntaje());
        
        // Repintar el panel de juego
        panelJuego.repaint();
        
        // Mostrar mensaje si el juego ha finalizado
        if (!juego.esJuegoActivo() && juego.getPuntaje() > 0) {
            int opcion = JOptionPane.showConfirmDialog(ventana, 
                "¡Simulación terminada!\nPulgas eliminadas: " + juego.getPuntaje() + 
                "\nMaximo puntaje: " + juego.getMaxPuntaje() + 
                "\n\n¿Deseas reiniciar la simulacion?",
                "Fin de la simulacion", JOptionPane.YES_NO_OPTION);
            
            if (opcion == JOptionPane.YES_OPTION) {
                juego.reiniciar();
            } else {
                System.exit(0); // Salir de la aplicación
            }
        }
    }
    
    /**
     * Inicia la interfaz gráfica.
     */
    public void iniciar() {
        // Iniciar el juego
        juego.iniciar();
        
        // Iniciar el timer
        timer.start();
    }
}