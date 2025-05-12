package autonoma.Pulgas.modelos;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Clase que representa una pulga mutante en el juego.
 * Una pulga mutante se convierte en normal al ser impactada una vez.
 */
public class PulgaMutante extends Pulga {
    private static final int ANCHO_PULGA = 50;
    private static final int ALTO_PULGA = 50;
    private static Image imagenPulga;

    // Inicialización estática de la imagen
    static {
        try {
            imagenPulga = new ImageIcon(PulgaMutante.class.getResource("/recursos/pulgaMutante.png")).getImage();
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de la pulga mutante: " + e.getMessage());
            // Crear una imagen por defecto en caso de error
            imagenPulga = new ImageIcon().getImage();
        }
    }

    /**
     * Constructor para crear una nueva pulga mutante............
     * 
     * @param x Posición en el eje X.
     * @param y Posición en el eje Y.
     */
    public PulgaMutante(int x, int y) {
        super(x, y, ANCHO_PULGA, ALTO_PULGA, imagenPulga);
    }

    /**
     * Implementación del método impactar para una pulga mutante.
     * Una pulga mutante se convierte en normal cuando es impactada.
     * 
     * @return Una nueva pulga normal en la misma posición.
     */
    @Override
    public Pulga impactar() {
        return new PulgaNormal(x, y); // La pulga mutante se convierte en normal
    }
}