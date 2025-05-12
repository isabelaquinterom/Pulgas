package autonoma.Pulgas.modelos;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que representa el campo de batalla donde se encuentran las pulgas.
 */
public class Campo {
    private int ancho;
    private int alto;
    private ArrayList<Pulga> pulgas;
    private Random random;
    private int posicionMouseX;
    private int posicionMouseY;
    
    // Dimensiones para las pulgas
    private static final int ANCHO_PULGA_NORMAL = 40;
    private static final int ALTO_PULGA_NORMAL = 40;
    private static final int ANCHO_PULGA_MUTANTE = 50;
    private static final int ALTO_PULGA_MUTANTE = 50;
    
    // Número máximo de intentos para encontrar posición sin colisión
    private static final int MAX_INTENTOS = 50;
    
    /**
     * Constructor para crear un nuevo campo de batalla.
     * 
     * @param ancho Ancho del campo.
     * @param alto  Alto del campo.
     */
    public Campo(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.pulgas = new ArrayList<>();
        this.random = new Random();
        this.posicionMouseX = 0;
        this.posicionMouseY = 0;
    }
    
    /**
     * Actualiza la posición del mouse en el campo.
     * 
     * @param x Coordenada X del mouse.
     * @param y Coordenada Y del mouse.
     */
    public void actualizarPosicionMouse(int x, int y) {
        this.posicionMouseX = x;
        this.posicionMouseY = y;
    }
    
    /**
     * Actualiza las pulgas, haciendo que esquiven el cursor si está cerca.
     */
    public void actualizarPulgas() {
        for (Pulga pulga : pulgas) {
            pulga.esquivarCursor(posicionMouseX, posicionMouseY, ancho, alto);
        }
    }
    
    /**
     * Agrega una pulga al campo verificando que no colisione con otras.
     * 
     * @param pulga Pulga a agregar.
     * @return true si la pulga fue agregada, false si hubo colisión.
     */
    public boolean agregarPulga(Pulga pulga) {
        // Verificar colisiones con otras pulgas
        for (Pulga p : pulgas) {
            if (pulga.colisiona(p)) {
                return false; // Hay colisión, no se agrega
            }
        }
        pulgas.add(pulga);
        return true;
    }
    
    /**
     * Crea y agrega una pulga normal en una posición aleatoria.
     * 
     * @return true si la pulga fue agregada, false si no fue posible.
     */
    public boolean agregarPulgaNormal() {
        int maxIntentos = MAX_INTENTOS;
        
        for (int i = 0; i < maxIntentos; i++) {
            int x = random.nextInt(ancho - ANCHO_PULGA_NORMAL);
            int y = random.nextInt(alto - ALTO_PULGA_NORMAL);
            
            PulgaNormal nuevaPulga = new PulgaNormal(x, y);
            if (agregarPulga(nuevaPulga)) {
                return true;
            }
        }
        
        return false; // No se pudo agregar después de varios intentos
    }
    
    /**
     * Crea y agrega una pulga mutante en una posición aleatoria.
     * 
     * @return true si la pulga fue agregada, false si no fue posible.
     */
    public boolean agregarPulgaMutante() {
        int maxIntentos = MAX_INTENTOS;
        
        for (int i = 0; i < maxIntentos; i++) {
            int x = random.nextInt(ancho - ANCHO_PULGA_MUTANTE);
            int y = random.nextInt(alto - ALTO_PULGA_MUTANTE);
            
            PulgaMutante nuevaPulga = new PulgaMutante(x, y);
            if (agregarPulga(nuevaPulga)) {
                return true;
            }
        }
        
        return false; // No se pudo agregar después de varios intentos
    }
    
    /**
     * Obtiene la lista de pulgas en el campo.
     * 
     * @return Lista de pulgas.
     */
    public ArrayList<Pulga> getPulgas() {
        return pulgas;
    }
    
    /**
     * Hace que todas las pulgas salten a nuevas posiciones aleatorias.
     * Versión modificada para evitar colisiones entre pulgas al saltar.
     */
    public void hacerSaltarPulgas() {
        // Para cada pulga, buscar una nueva posición sin colisiones
        for (Pulga pulga : pulgas) {
            // Guardar posición original
            int xOriginal = pulga.getX();
            int yOriginal = pulga.getY();
            
            // Intentar encontrar una posición válida
            boolean posicionValida = false;
            
            for (int intento = 0; intento < MAX_INTENTOS && !posicionValida; intento++) {
                // Generar nueva posición aleatoria
                int nuevoX = random.nextInt(ancho - pulga.getAncho());
                int nuevoY = random.nextInt(alto - pulga.getAlto());
                
                // Temporalmente mover la pulga a la nueva posición
                pulga.setPosicion(nuevoX, nuevoY);
                
                // Verificar colisiones
                posicionValida = true;
                for (Pulga otra : pulgas) {
                    if (pulga != otra && pulga.colisiona(otra)) {
                        posicionValida = false;
                        break;
                    }
                }
                
                // Si encontramos colisión, volver a la posición original y probar otra
                if (!posicionValida) {
                    pulga.setPosicion(xOriginal, yOriginal);
                }
            }
            
            // Si no encontramos posición válida después de MAX_INTENTOS, dejar en posición original
            // La pulga ya está en su posición original o en una nueva sin colisiones
        }
    }
    
    /**
     * Dibuja todas las pulgas en el componente gráfico.
     * 
     * @param g Contexto gráfico en el que se dibuja.
     */
    public void dibujar(Graphics g) {
        for (Pulga pulga : pulgas) {
            pulga.dibujar(g);
        }
    }
    
    /**
     * Elimina una pulga del campo.
     * 
     * @param pulga Pulga a eliminar.
     */
    public void eliminarPulga(Pulga pulga) {
        pulgas.remove(pulga);
    }
    
    /**
     * Obtiene el número de pulgas restantes en el campo.
     * 
     * @return Número de pulgas.
     */
    public int pulgasRestantes() {
        return pulgas.size();
    }
    
    /**
     * Vacía el campo eliminando todas las pulgas.
     */
    public void limpiarCampo() {
        pulgas.clear();
    }
}