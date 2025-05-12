package autonoma.Pulgas.main;

import autonoma.Pulgas.modelos.GUI;
import autonoma.Pulgas.modelos.Juego;


/**
 * Clase principal para iniciar el simulador antipulgas.
 * 
 * Este simulador permite entrenar a los soldados para combatir
 * la plaga de pulgas locas y mutantes que acosan a la localidad.
 */
public class SimuladorAntipulgas {
    
    /**
     * .Método principal para arrancar la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Usar SwingUtilities para asegurar que la GUI se crea en el hilo de eventos
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Intentar establecer el Look and Feel del sistema
                    javax.swing.UIManager.setLookAndFeel(
                            javax.swing.UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                // Crear una instancia del juego..
                Juego juego = new Juego();
                
                // Crear la interfaz gráfica
                GUI gui = new GUI(juego);
                
                // Iniciar la interfaz
                gui.iniciar();
            }
        });
    }
}