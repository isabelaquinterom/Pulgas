/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.Pulgas.modelos;

import java.util.ArrayList;

/**
 * Clase abstracta que representa un arma en el juego.
 * Define el comportamiento básico de todas las armas.
 */
public abstract class Arma {
    
    /**
     * Método que define cómo el arma ataca a las pulgas.
     * Cada tipo de arma implementará su propia lógica de ataque.
     * 
     * @param pulgas Lista de pulgas en el campo.
     * @param x Coordenada X del punto de ataque (si aplica).
     * @param y Coordenada Y del punto de ataque (si aplica).
     * @return Número de pulgas eliminadas o afectadas por el ataque.
     */
    public abstract int atacar(ArrayList<Pulga> pulgas, int x, int y);
}
