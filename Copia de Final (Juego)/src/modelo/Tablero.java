package modelo;

public class Tablero {
    private char[][] tablero;
    private int casillasOcupadas; // casillas ocupadas por barcos
    private final int MAX_DISPAROS = 20; 

    public Tablero(int filas, int columnas) {
        tablero = new char[filas][columnas];
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int fila = 0; fila < tablero.length; fila++) {
            for (int columna = 0; columna < tablero[fila].length; columna++) {
                tablero[fila][columna] = '*';  // agua
            }
        }
        casillasOcupadas = 0; 
    }

    public boolean colocarBarco(int fila, int columna, int tamano, boolean horizontal) {
        if (casillasOcupadas + tamano > MAX_DISPAROS) {
            return false; // Evita colocar más barcos de los que se pueden disparar
        }

        if (horizontal) {
            if (columna + tamano > tablero[0].length) return false;
            for (int i = 0; i < tamano; i++) {
                if (tablero[fila][columna + i] == 'B') return false;
            }
            for (int i = 0; i < tamano; i++) {
                tablero[fila][columna + i] = 'B';
            }
        } else {
            if (fila + tamano > tablero.length) return false;
            for (int i = 0; i < tamano; i++) {
                if (tablero[fila + i][columna] == 'B') return false;
            }
            for (int i = 0; i < tamano; i++) {
                tablero[fila + i][columna] = 'B';
            }
        }
        casillasOcupadas += tamano; // Sumar las casillas ocupadas por barcos
        return true;
    }

    public char[][] getTablero() {
        return tablero;
    }

    public boolean todosLosBarcosHundidos() {
        return casillasOcupadas == 0; // Compara las casillas ocupadas por barcos
    }

    public void reducirBarcosRestantes() {
        if (casillasOcupadas > 0) {
            casillasOcupadas--; // Decrementa las casillas ocupadas cuando se hunde un barco
        }
    }
    
    public void colocarBarcosAleatorios(int cantidad) {
        int barcosColocados = 0;
        cantidad = 3;
        
        while (barcosColocados < cantidad && casillasOcupadas < MAX_DISPAROS) {
            int fila = (int) (Math.random() * tablero.length);
            int columna = (int) (Math.random() * tablero[0].length);
            int tamano = Math.min((int) (Math.random() * 4) + 1, MAX_DISPAROS - casillasOcupadas); // Asegura no exceder disparos
            boolean horizontal = Math.random() < 0.5;

            if (tamano > 0 && casillasOcupadas + tamano <= MAX_DISPAROS) { // Solo coloca si el tamaño es válido
                boolean barcoColocado = colocarBarco(fila, columna, tamano, horizontal);
                if (barcoColocado) {
                    barcosColocados++;
                }
            }
        }
    }
}
