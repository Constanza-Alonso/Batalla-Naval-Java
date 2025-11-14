
package main;

import controlador.Controlador;
import modelo.Tablero;
import vista.Vista;

public class Main {
    
    public static void main(String[] args) {
        
        // Inicializa el tablero de 8x8
        Tablero tablero = new Tablero(8, 8);

        // Crea la vista
        Vista vista = new Vista();

        // Crea el controlador y lo vincula con el tablero y la vista
        Controlador controlador = new Controlador(tablero, vista);


        // Hace visible la vista
        vista.setVisible(true);
    }
}