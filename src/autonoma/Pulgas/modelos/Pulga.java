package autonoma.Pulgas.modelos;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

/**
 * Clase abstracta que representa una pulga en el juego.
 * Define el comportamiento básico de todas las pulgas.
 */
public abstract class Pulga {
    protected int x;
    protected int y;
    protected int ancho;
    protected int alto;
    protected Image imagen;
    protected static final Random random = new Random();
    
    // Distancia a la que la pulga reacciona al cursor
    protected static final int DISTANCIA_REACCION = 100;
    // Velocidad de movimiento al esquivar
    protected static final int VELOCIDAD_EVASION = 5;
    
    /**
     * Constructor para crear una nueva pulga.
     * 
     * @param x      Posición en el eje X.
     * @param y      Posición en el eje Y.
     * @param ancho  Ancho de la pulga.
     * @param alto   Alto de la pulga.
     * @param imagen Imagen que representa la pulga.
     */
    public Pulga(int x, int y, int ancho, int alto, Image imagen) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = imagen;
    }
    
    /**
     * Dibuja la pulga en el componente gráfico.
     * 
     * @param g Contexto gráfico en el que se dibuja.
     */
    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ancho, alto, null);
    }
    
    /**
     * Verifica si esta pulga colisiona con otra.
     * 
     * @param otra Otra pulga para verificar colisión.
     * @return true si hay colisión, false en caso contrario.
     */
    public boolean colisiona(Pulga otra) {
        return x < otra.x + otra.ancho && x + ancho > otra.x &&
               y < otra.y + otra.alto && y + alto > otra.y;
    }
    
    /**
     * Hace que la pulga salte a una posición aleatoria dentro de los límites.
     * 
     * @param maxX Límite en el eje X.
     * @param maxY Límite en el eje Y.
     */
    public void saltar(int maxX, int maxY) {
        x = random.nextInt(maxX - ancho);
        y = random.nextInt(maxY - alto);
    }
    
    /**
     * Método que hace que la pulga reaccione a la cercanía del cursor.
     * Si el cursor está cerca, la pulga se mueve para esquivarlo.
     * 
     * @param mouseX Posición X del cursor.
     * @param mouseY Posición Y del cursor.
     * @param maxX Límite máximo en X.
     * @param maxY Límite máximo en Y.
     * @return true si la pulga se movió, false en caso contrario.
     */
    public boolean esquivarCursor(int mouseX, int mouseY, int maxX, int maxY) {
        // Calcular el centro de la pulga
        int centroX = x + ancho / 2;
        int centroY = y + alto / 2;
        
        // Calcular la distancia al cursor
        double distancia = Math.sqrt(Math.pow(mouseX - centroX, 2) + Math.pow(mouseY - centroY, 2));
        
        // Si el cursor está cerca, moverse en dirección opuesta
        if (distancia < DISTANCIA_REACCION) {
            // Calcular vector dirección
            double dirX = centroX - mouseX;
            double dirY = centroY - mouseY;
            
            // Normalizar el vector
            double longitud = Math.sqrt(dirX * dirX + dirY * dirY);
            if (longitud > 0) {
                dirX = dirX / longitud * VELOCIDAD_EVASION;
                dirY = dirY / longitud * VELOCIDAD_EVASION;
            }
            
            // Actualizar posición con restricciones de límites
            int nuevoX = x + (int)dirX;
            int nuevoY = y + (int)dirY;
            
            // Asegurarse de que no se salga de los límites
            nuevoX = Math.max(0, Math.min(nuevoX, maxX - ancho));
            nuevoY = Math.max(0, Math.min(nuevoY, maxY - alto));
            
            // Actualizar posición
            x = nuevoX;
            y = nuevoY;
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Método abstracto que define el comportamiento cuando la pulga es impactada.
     * Se implementará de forma diferente según el tipo de pulga.
     * 
     * @return La pulga resultante tras el impacto o null si debe morir.
     */
    public abstract Pulga impactar();
    
    // Getters
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getAncho() {
        return ancho;
    }
    
    public int getAlto() {
        return alto;
    }
    
    /**
     * Establece una nueva posición para la pulga.
     * 
     * @param x Nueva posición en X.
     * @param y Nueva posición en Y.
     */
    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Verifica si un punto (x,y) está dentro del área de la pulga.
     * 
     * @param puntoX Coordenada X del punto.
     * @param puntoY Coordenada Y del punto.
     * @return true si el punto está dentro de la pulga, false en caso contrario.
     */
    public boolean contienePunto(int puntoX, int puntoY) {
        return puntoX >= x && puntoX <= x + ancho && 
               puntoY >= y && puntoY <= y + alto;
    }
}