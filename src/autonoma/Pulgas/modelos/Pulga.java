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