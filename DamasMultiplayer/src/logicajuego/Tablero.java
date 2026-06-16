
package logicajuego;


public class Tablero {

    //declaracion de variables y matriz(tablero)
    private int[][] matrizTablero = new int[8][8];
    private int turnoActual;

    /*constructor de la clase. configuracion incial del juego que permite:
    -llenar la matriz 
    -asignar el primer turno al jugador 1
     */
    public Tablero() {
        inicializarTablero();
        this.turnoActual = 1;
    }

    //metodo que ejecuta el constructor
    private void inicializarTablero() {

        //fichas jugador 1
        for (int f = 0; f <= 2; f++) {
            for (int c = 0; c <= 7; c++) {
                if ((f + c) % 2 != 0) {
                    matrizTablero[f][c] = 1;
                }
            }
        }

        //fichas jugador 2
        for (int f = 5; f <= 7; f++) {
            for (int c = 0; c <= 7; c++) {
                if ((f + c) % 2 != 0) {
                    matrizTablero[f][c] = 2;
                }
            }
        }

    }

    //metodo de alternacion de turnos
    public void cambiarTurno() {
        if (this.turnoActual == 1) {
            this.turnoActual = 2;
        } else {
            this.turnoActual = 1;
        }
    }

    /*metodo para obtener el valor de una casilla:
    - si el valor es 0 = esta vacia
    - si el valor es 1 = hay una ficha del jugador 1
    - si el valor es 2 = hay una ficha del jugador 2
    - si el valor es 3 = hay una dama del jugador 1
    - si el valor es 4 = hay una dama del jugador 2
     */
    public int obtenerCasilla(int fila, int columna) {
        return matrizTablero[fila][columna];
    }

    //metodo que cambia el valor de la posicion que recibe
    public void setCasilla(int fila, int columna, int newVal) {
        this.matrizTablero[fila][columna] = newVal;
    }
    
    /*metodo para ver que la casilla a la que se quiere mover es valida
    es valida si:- ni las filas ni las columnas se salen del tablero (0 a 7)
                 - la casilla esta vacia (valor 0)  
    */
    public boolean casillaEsValida(int fila, int columna) {
        if (fila >= 0 && fila <= 7 && columna >= 0 && columna <= 7) {
            if (obtenerCasilla(fila, columna) == 0) {
                return true;
            }
            
        }
        return false;
    }
    
    //metodo que valida si el movimiento es diagonal
    public boolean movEsDiagonal(int filaOrig, int columnaOrig, int filaDest, int columnaDest){
        return Math.abs(filaDest-filaOrig)==Math.abs(columnaDest-columnaOrig);
    }
    
    //metodo de validacion general; el movimiento solo sera valido si ambas validaciones son true
    public boolean validarMovimientos(int filaOrig, int columnaOrig, int filaDest, int columnaDest){
        boolean destinoOk = casillaEsValida(filaDest, columnaDest);
        boolean diagonalOk = movEsDiagonal(filaOrig, columnaOrig, filaDest, columnaDest);
        
        return destinoOk && diagonalOk;
    }
}
