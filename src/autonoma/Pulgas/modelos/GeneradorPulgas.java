package autonoma.Pulgas.modelos;

/**
 * Clase que genera pulgas automáticamente a intervalos regulares.
 * Ejecuta en un hilo separado.
 */
public class GeneradorPulgas extends Thread {
    private Juego juego;
    private boolean activo;
    private static final long TIEMPO_PULGA_NORMAL = 5000; // 5 segundos
    private static final long TIEMPO_PULGA_MUTANTE = 10000; // 10 segundos
    
    /**
     * Constructor para crear un nuevo generador de pulgas.
     * 
     * @param juego Juego al que se añadirán las pulgas.
     */
    public GeneradorPulgas(Juego juego) {
        this.juego = juego;
        this.activo = false;
    }
    
    /**
     * Inicia el generador de pulgas.
     */
    public void iniciar() {
        activo = true;
        start();
    }
    
    /**
     * Detiene el generador de pulgas.
     */
    public void detener() {
        activo = false;
        interrupt();
    }
    
    /**
     * Ejecución del hilo que genera pulgas periódicamente.
     */
    @Override
    public void run() {
        long ultimaPulgaNormal = System.currentTimeMillis();
        long ultimaPulgaMutante = System.currentTimeMillis();
        
        try {
            while (activo) {
                long tiempoActual = System.currentTimeMillis();
                
                // Verificar si es tiempo de generar una pulga normal
                if (tiempoActual - ultimaPulgaNormal >= TIEMPO_PULGA_NORMAL) {
                    juego.agregarPulgaNormal();
                    ultimaPulgaNormal = tiempoActual;
                }
                
                // Verificar si es tiempo de generar una pulga mutante
                if (tiempoActual - ultimaPulgaMutante >= TIEMPO_PULGA_MUTANTE) {
                    juego.agregarPulgaMutante();
                    ultimaPulgaMutante = tiempoActual;
                }
                
                // Esperar un poco para no consumir muchos recursos
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            // El hilo fue interrumpido, terminar silenciosamente
        }
    }
}