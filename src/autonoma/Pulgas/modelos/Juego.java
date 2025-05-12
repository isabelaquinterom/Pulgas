package autonoma.Pulgas.modelos;

/**
 * Clase que controla la lógica del juego.,
 */
public class Juego {
    private Campo campo;
    private Pulguipium pistola;
    private Pulgoson misil;
    private int puntaje;
    private int maxPuntaje;
    private boolean juegoActivo;
    private PersistenciaScore persistencia;
    private GeneradorPulgas generador;
    
    // Dimensiones del campo
    private static final int ANCHO_CAMPO = 800;
    private static final int ALTO_CAMPO = 600;
    
    /**
     * Constructor para crear un nuevo juego.
     */
    public Juego() {
        campo = new Campo(ANCHO_CAMPO, ALTO_CAMPO); // Dimensiones del campo
        pistola = new Pulguipium();
        misil = new Pulgoson();
        puntaje = 0;
        persistencia = new PersistenciaScore("max_puntaje.txt");
        maxPuntaje = persistencia.cargarMaxPuntaje();
        juegoActivo = false;
        generador = new GeneradorPulgas(this);
    }
    
    /**
     * Inicia el juego.
     */
    public void iniciar() {
        juegoActivo = true;
        
        // Iniciar el generador de pulgas
        generador.iniciar();
    }
    
    /**
     * Reinicia el juego.
     */
    public void reiniciar() {
        // Detener el generador
        if (generador != null) {
            generador.detener();
        }
        
        // Limpiar el campo
        campo.limpiarCampo();
        
        // Reiniciar puntaje
        puntaje = 0;
        
        // Recargar el máximo puntaje
        maxPuntaje = persistencia.cargarMaxPuntaje();
        
        // Volver a iniciar
        iniciar();
    }
    
    /**
     * Finaliza el juego.
     */
    public void finalizar() {
        juegoActivo = false;
        
        if (generador != null) {
            generador.detener();
        }
        
        // Actualizar máximo puntaje si es necesario
        if (puntaje > maxPuntaje) {
            maxPuntaje = puntaje;
            persistencia.guardarMaxPuntaje(maxPuntaje);
        }
    }
    
    /**
     * Dispara la pistola en las coordenadas (x,y).
     * 
     * @param x Coordenada X del disparo.
     * @param y Coordenada Y del disparo.
     */
    public void dispararPistola(int x, int y) {
        if (!juegoActivo) return;
        
        int pulgasEliminadas = pistola.atacar(campo.getPulgas(), x, y);
        actualizarPuntaje(pulgasEliminadas);
        
        // Verificar si se acabaron las pulgas
        verificarFinJuego();
    }
    
    /**
     * Dispara el misil Pulgoson.
     */
    public void dispararMisil() {
        if (!juegoActivo) return;
        
        int pulgasEliminadas = misil.atacar(campo.getPulgas(), 0, 0);
        actualizarPuntaje(pulgasEliminadas);
        
        // Verificar si se acabaron las pulgas
        verificarFinJuego();
    }
    
    /**
     * Agrega una pulga normal al campo.
     * 
     * @return true si la pulga fue agregada, false en caso contrario.
     */
    public boolean agregarPulgaNormal() {
        if (!juegoActivo) {
            iniciar(); // Iniciar el juego si está inactivo
        }
        
        return campo.agregarPulgaNormal();
    }
    
    /**
     * Agrega una pulga mutante al campo.
     * 
     * @return true si la pulga fue agregada, false en caso contrario.
     */
    public boolean agregarPulgaMutante() {
        if (!juegoActivo) {
            iniciar(); // Iniciar el juego si está inactivo
        }
        
        return campo.agregarPulgaMutante();
    }
    
    /**
     * Hace que todas las pulgas salten a nuevas posiciones.
     */
    public void hacerSaltarPulgas() {
        if (!juegoActivo) return;
        
        campo.hacerSaltarPulgas();
    }
    
    /**
     * Verifica si el juego ha terminado (no quedan pulgas).
     */
    private void verificarFinJuego() {
        if (campo.pulgasRestantes() == 0 && juegoActivo) {
            finalizar();
        }
    }
    
    /**
     * Actualiza el puntaje con los puntos obtenidos.
     * 
     * @param puntos Puntos a añadir.
     */
    public void actualizarPuntaje(int puntos) {
        puntaje += puntos;
    }
    
    /**
     * Obtiene el puntaje actual.
     * 
     * @return Puntaje actual.
     */
    public int getPuntaje() {
        return puntaje;
    }
    
    /**
     * Obtiene el puntaje máximo.
     * 
     * @return Puntaje máximo.
     */
    public int getMaxPuntaje() {
        return maxPuntaje;
    }
    
    /**
     * Verifica si el juego está activo.
     * 
     * @return true si el juego está activo, false en caso contrario.
     */
    public boolean esJuegoActivo() {
        return juegoActivo;
    }
    
    /**
     * Obtiene el campo de batalla.
     * 
     * @return Campo de batalla.
     */
    public Campo getCampo() {
        return campo;
    }
}