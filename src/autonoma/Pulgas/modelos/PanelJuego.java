package autonoma.Pulgas.modelos;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

/**
 * Panel que muestra el juego.
 */
public class PanelJuego extends JPanel {
    private Juego juego;
    private Campo campo;
    
    /**
     * Constructor para crear un nuevo panel de juego.
     * 
     * @param juego Juego a mostrar.
     * @param campo Campo de batalla.
     */
    public PanelJuego(Juego juego, Campo campo) {
        this.juego = juego;
        this.campo = campo;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        // Habilitar el focus para recibir eventos de teclado
        setFocusable(true);
    }
    
    /**
     * Dibuja el contenido del panel.
     * 
     * @param g Contexto gráfico en el que se dibuja.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Dibujar fondo
        g.setColor(new Color(220, 240, 255)); // Color celeste claro
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Dibujar las pulgas en el campo
        campo.dibujar(g);
        
        // Si el juego no está activo, mostrar mensaje
        if (!juego.esJuegoActivo()) {
            g.setColor(new Color(0, 0, 0, 150)); // Negro semitransparente
            g.fillRect(0, 0, getWidth(), getHeight());
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String mensaje = "¡Simulador Antipulgas!";
            
            // Calcular posición para centrar el texto
            int stringWidth = g.getFontMetrics().stringWidth(mensaje);
            int x = (getWidth() - stringWidth) / 2;
            int y = getHeight() / 2 - 20;
            
            g.drawString(mensaje, x, y);
            
            // Añadir instrucciones
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            String instrucciones = "Presiona 'p' para agregar pulgas o 'Reiniciar' para comenzar";
            
            stringWidth = g.getFontMetrics().stringWidth(instrucciones);
            x = (getWidth() - stringWidth) / 2;
            y = getHeight() / 2 + 20;
            
            g.drawString(instrucciones, x, y);
        }
    }
}