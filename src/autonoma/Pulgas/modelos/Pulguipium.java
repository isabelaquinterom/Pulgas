package autonoma.Pulgas.modelos;

import java.util.ArrayList;

/**
 * Clase que representa la pistola Pulguipium en el juego.
 * Este arma afecta a una única pulga que sea impactada directamente.
 */
public class Pulguipium extends Arma {
    
    /**
     * Ataca a una única pulga que se encuentre en las coordenadas (x,y).
     * 
     * @param pulgas Lista de pulgas en el campo.
     * @param x      Coordenada X del disparo.
     * @param y      Coordenada Y del disparo.
     * @return 1 si una pulga fue eliminada, 0 en caso contrario.
     */
    @Override
    public int atacar(ArrayList<Pulga> pulgas, int x, int y) {
        // Recorrer la lista de pulgas en busca de una que contenga el punto (x,y)
        for (int i = 0; i < pulgas.size(); i++) {
            Pulga pulga = pulgas.get(i);
            if (pulga.contienePunto(x, y)) {
                // Impactar la pulga
                Pulga resultado = pulga.impactar();
                if (resultado == null) {
                    // La pulga murió
                    pulgas.remove(i);
                    return 1; // Una pulga eliminada
                } else {
                    // La pulga mutante se convirtió en normal
                    pulgas.set(i, resultado);
                    return 0; // No se eliminó pulga pero sí se transformó
                }
            }
        }
        return 0; // No se encontró pulga para atacar...
    }
}