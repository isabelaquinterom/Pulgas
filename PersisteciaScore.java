/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.Pulgas.modelos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que maneja la persistencia del puntaje máximo en un archivo de texto.
 */
public class PersistenciaScore {
    private String nombreArchivo;
    
    /**
     * Constructor para crear un nuevo manejador de persistencia.
     * 
     * @param nombreArchivo Nombre del archivo donde se guarda el puntaje.
     */
    public PersistenciaScore(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        // Crear el archivo si no existe
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                // Inicializar con puntaje 0
                guardarMaxPuntaje(0);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de puntaje: " + e.getMessage());
            }
        }
    }
    
    /**
     * Guarda el puntaje máximo en el archivo.
     * 
     * @param puntaje Puntaje máximo a guardar.
     */
    public void guardarMaxPuntaje(int puntaje) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write(String.valueOf(puntaje));
        } catch (IOException e) {
            System.err.println("Error al guardar el puntaje máximo: " + e.getMessage());
        }
    }
    
    /**
     * Carga el puntaje máximo desde el archivo.
     * 
     * @return Puntaje máximo guardado o 0 si hay error.
     */
    public int cargarMaxPuntaje() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea = reader.readLine();
            if (linea != null && !linea.isEmpty()) {
                return Integer.parseInt(linea.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar el puntaje máximo: " + e.getMessage());
        }
        return 0; // Valor por defecto en caso de error
    }
}