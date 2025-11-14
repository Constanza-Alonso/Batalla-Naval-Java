package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Tablero;
import vista.Vista;

public class Controlador implements ActionListener {
    private Tablero tablero;
    private Vista vista;
    private final int MAX_DISPAROS = 20;
    private int tirosRestantes = MAX_DISPAROS;

    public Controlador(Tablero tablero, Vista vista) {
        this.tablero = tablero;
        this.vista = vista;

        this.vista.getBotonReiniciar().addActionListener(this);
        this.vista.getBotonSalir().addActionListener(this);

        configurarCasillas();
        iniciarJuego();
    }

    private void configurarCasillas() {
        for (int fila = 0; fila < vista.getCasillas().length; fila++) {
            for (int columna = 0; columna < vista.getCasillas()[fila].length; columna++) {
                final int f = fila;
                final int c = columna;
                vista.getCasillas()[fila][columna].addActionListener(e -> manejarClickCasilla(f, c));
            }
        }
    }

    public void iniciarJuego() {
    int maxCasillasOcupadas = MAX_DISPAROS;
    int maxBarcos = Math.min(maxCasillasOcupadas, 6); // Límite superior para evitar demasiados barcos

    tablero.colocarBarcosAleatorios(maxBarcos);
    actualizarVista();
    imprimirTableroEnConsola();
}


    private void actualizarVista() {
        char[][] estadoTablero = tablero.getTablero();
        for (int fila = 0; fila < estadoTablero.length; fila++) {
            for (int columna = 0; columna < estadoTablero[fila].length; columna++) {
                vista.getCasillas()[fila][columna].setBackground(Color.BLUE);
                vista.getCasillas()[fila][columna].setText("");
                vista.getCasillas()[fila][columna].setEnabled(true);
            }
        }
    }

    public void manejarClickCasilla(int fila, int columna) {
        if (tirosRestantes <= 0) {
            JOptionPane.showMessageDialog(vista, "¡No te quedan tiros!", "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        char[][] estadoTablero = tablero.getTablero();

        if (!vista.getCasillas()[fila][columna].isEnabled()) {
            return;
        }

        if (estadoTablero[fila][columna] == 'B') {
            vista.getCasillas()[fila][columna].setBackground(Color.RED);
            tablero.reducirBarcosRestantes();
        } else {
            vista.getCasillas()[fila][columna].setText("-");
        }

        vista.getCasillas()[fila][columna].setEnabled(false);
        tirosRestantes--;
        actualizarTirosRestantes(tirosRestantes);

        if (tablero.todosLosBarcosHundidos()) {
            deshabilitarTablero();
            JOptionPane.showMessageDialog(vista, "¡Ganaste!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        } else if (tirosRestantes <= 0) {
            JOptionPane.showMessageDialog(vista, "¡Perdiste!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
            reiniciarJuego();
        }
    }

    private void imprimirTableroEnConsola() {
        char[][] estadoTablero = tablero.getTablero();
        System.out.println("Tablero:");
        for (int fila = 0; fila < estadoTablero.length; fila++) {
            for (int columna = 0; columna < estadoTablero[fila].length; columna++) {
                System.out.print(estadoTablero[fila][columna] + " ");
            }
            System.out.println();
        }
    }

   public void reiniciarJuego() {
        tablero = new Tablero(8, 8);
        tirosRestantes = MAX_DISPAROS;
        reiniciarVista();
        iniciarJuego();  
    }


    private void reiniciarVista() {
        for (int fila = 0; fila < vista.getCasillas().length; fila++) {
            for (int columna = 0; columna < vista.getCasillas()[fila].length; columna++) {
                vista.getCasillas()[fila][columna].setBackground(Color.BLUE);
                vista.getCasillas()[fila][columna].setText("");
                vista.getCasillas()[fila][columna].setEnabled(true);
            }
        }
    }

    private void actualizarTirosRestantes(int tirosRestantes) {
        vista.getjLabel1().setText("Tiros restantes: " + tirosRestantes);
    }

    private void deshabilitarTablero() {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                vista.getCasillas()[fila][columna].setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if (source == vista.getBotonReiniciar()) {
            reiniciarJuego();
        } else if (source == vista.getBotonSalir()) {
            System.exit(0);
        }
    }
}
