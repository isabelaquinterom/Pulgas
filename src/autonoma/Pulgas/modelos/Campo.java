package autonoma.Pulgas.modelos;

/**
 *
 * @author Asus
 */
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
    
    // Dimensiones para las pulgas
    private static final int ANCHO_PULGA_NORMAL = 40;
    private static final int ALTO_PULGA_NORMAL = 40;
    private static final int ANCHO_PULGA_MUTANTE = 50;
    private static final int ALTO_PULGA_MUTANTE = 50;
    
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
        int maxIntentos = 50; // Limitar número de intentos para evitar bucle infinito
        
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
        int maxIntentos = 50; // Limitar número de intentos para evitar bucle infinito
        
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
     */
    public void hacerSaltarPulgas() {
        // Hacer que cada pulga salte a una nueva posición
        for (Pulga pulga : pulgas) {
            boolean posicionValida = false;
            int maxIntentos = 50;
            
            for (int i = 0; i < maxIntentos && !posicionValida; i++) {
                // Guardar posición original
                int xOriginal = pulga.getX();
                int yOriginal = pulga.getY();
                
                // Saltar a nueva posición
                pulga.saltar(ancho, alto);
                
                // Verificar colisiones con otras pulgas
                posicionValida = true;
                for (Pulga otra : pulgas) {
                    if (pulga != otra && pulga.colisiona(otra)) {
                        posicionValida = false;
                        // Restaurar posición original
                        pulga = new PulgaNormal(xOriginal, yOriginal);
                        break;
                    }
                }
            }
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
