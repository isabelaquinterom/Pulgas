package autonoma.Pulgas.modelos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.*;

public class Puntaje {
    private int actual;
    private int maximo;
    private final String archivo = "puntaje.txt";
    private Image imagenPulgaNormal;

    public Puntaje(Image imagenPulgaNormal) {
        this.imagenPulgaNormal = imagenPulgaNormal;
        this.actual = 0;
        this.maximo = leerPuntaje();
    }

    public void aumentar() {
        actual++;
        if (actual > maximo) {
            maximo = actual;
            guardarPuntaje();
        }
    }

    public int getActual() {
        return actual;
    }

    public int getMaximo() {
        return maximo;
    }

    public Image getImagenPulgaNormal() {
        return imagenPulgaNormal;
    }

    private int leerPuntaje() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            return Integer.parseInt(br.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    private void guardarPuntaje() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write(String.valueOf(maximo));
        } catch (IOException e) {
            System.err.println("No se pudo guardar el puntaje.");
        }
    }

    public void dibujar(Graphics g, Component c) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Puntaje: " + actual, 10, 20);
        g.drawString("Máximo: " + maximo, 10, 40);
    }
}