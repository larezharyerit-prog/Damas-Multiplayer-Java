
package logicajuego;


public class Tablero {
    //creacion de la matriz para el tablero de damas
    private int[][] matrizTablero = new int[8][8];

    //constructor de la clase que nos permite rellenar la matriz
    public Tablero() {
        inicializarTablero();
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

}

