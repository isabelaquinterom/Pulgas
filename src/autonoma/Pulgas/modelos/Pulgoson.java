package autonoma.Pulgas.modelos;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que representa el misil Pulgoson en el juego.
 * Este arma elimina el 50% de las pulgas que se encuentren en el campo.
 */
public class Pulgoson extends Arma {
    private Random random = new Random();
    
    /**
     * Ataca aleatoriamente al 50% de las pulgas en el campo.
     * Las coordenadas x e y no se utilizan para este tipo de arma.
     * 
     * @param pulgas Lista de pulgas en el campo.
     * @param x      No utilizado para esta arma.
     * @param y      No utilizado para esta arma.
     * @return Número de pulgas eliminadas.
     */
    @Override
    public int atacar(ArrayList<Pulga> pulgas, int x, int y) {
        if (pulgas.isEmpty()) {
            return 0;
        }
        
        int numeroAtaques = Math.max(1, pulgas.size() / 2);
        int pulgasEliminadas = 0;
        
        // Crear una copia de la lista para evitar problemas con la modificación durante la iteración
        ArrayList<Pulga> pulgasCopia = new ArrayList<>(pulgas);
        
        // Mezclar la lista para seleccionar aleatoriamente
        java.util.Collections.shuffle(pulgasCopia, random);
        
        // Atacar al 50% de las pulgas
        for (int i = 0; i < numeroAtaques && i < pulgasCopia.size(); i++) {
            Pulga pulga = pulgasCopia.get(i);
            int indiceOriginal = pulgas.indexOf(pulga);
            
            if (indiceOriginal != -1) {  // Asegurarse de que la pulga todavía existe
                Pulga resultado = pulga.impactar();
                
                if (resultado == null) {
                    // La pulga murió
                    pulgas.remove(indiceOriginal);
                    pulgasEliminadas++;
                } else {
                    // La pulga mutante se convirtió en normal
                    pulgas.set(indiceOriginal, resultado);
                }
            }
        }
        
        return pulgasEliminadas;
    }
}