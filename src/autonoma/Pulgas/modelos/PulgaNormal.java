package autonoma.Pulgas.modelos;


import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Clase que representa una pulga normal en el juego.
 * Una pulga normal muere (desaparece) cuando es impactada una vez.
 */
public class PulgaNormal extends Pulga {
    private static final int ANCHO_PULGA = 40;
    private static final int ALTO_PULGA = 40;
    private static Image imagenPulga;

    // Inicialización estática de la imagen
    static {
        try {
            imagenPulga = new ImageIcon(PulgaNormal.class.getResource("/recursos/pulga.png")).getImage();
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de la pulga normal: " + e.getMessage());
            // Crear una imagen por defecto en caso de error
            imagenPulga = new ImageIcon().getImage();
        }
    }

    /**
     * Constructor para crear una nueva pulga normal.
     * 
     * @param x Posición en el eje X.
     * @param y Posición en el eje Y.
     */
    public PulgaNormal(int x, int y) {
        super(x, y, ANCHO_PULGA, ALTO_PULGA, imagenPulga);
    }

    /**
     * Implementación del método impactar para una pulga normal.
     * Una pulga normal muere (retorna null) cuando es impactada.
     * 
     * @return null, indicando que la pulga debe ser eliminada.
     */
    @Override
    public Pulga impactar() {
        return null; // La pulga normal muere al ser impactada
    }
}