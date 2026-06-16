package logicajuego;

public class Tablero {

    //declaracion de variables y matriz(tablero)
    private final int[][] matrizTablero;
    private int turnoActual;

    /*constructor de la clase. configuracion incial del juego que permite:
    -llenar la matriz 
    -asignar el primer turno al jugador 1
     */
    public Tablero() {
        this.matrizTablero = new int[8][8];
        inicializarTablero();
        this.turnoActual = 1;
    }

    //metodo que asigna las fichas a las casillas correspondientes para cada jugador
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
    public boolean movEsDiagonal(int filaOrig, int columnaOrig, int filaDest, int columnaDest) {
        return Math.abs(filaDest - filaOrig) == Math.abs(columnaDest - columnaOrig);
    }

    //metodo de validacion general; el movimiento solo sera valido si ambas validaciones son true
    public boolean validarMovimientos(int filaOrig, int columnaOrig, int filaDest, int columnaDest) {

        boolean destinoOk = casillaEsValida(filaDest, columnaDest);

        boolean diagonalOk = movEsDiagonal(filaOrig, columnaOrig, filaDest, columnaDest);

        boolean direccionOk = false;

        if (this.turnoActual == 1 && filaDest < filaOrig) {
            direccionOk = true;
        } else if (this.turnoActual == 2 && filaDest > filaOrig) {
            direccionOk = true;
        }
        return destinoOk && diagonalOk && direccionOk;
    }

    /*metodo para mover una ficha. valida si se puede el movimiento,
    le asigna el valor 0 a la casilla que deja y le asigna el valor
    del jugador a la casilla a la que se mueve. luego, cambia el turno.
     */
    public boolean moverFicha(int filaOrig, int columnaOrig, int filaDest, int columnaDest) {
        if (validarMovimientos(filaOrig, columnaOrig, filaDest, columnaDest)) {

            //si el movimiento es basico
            setCasilla(filaOrig, columnaOrig, 0);

            setCasilla(filaDest, columnaDest, this.turnoActual);

            cambiarTurno();

            return true;

            //si el movimiento es una captura
        } else if (validarCaptura(filaOrig, columnaOrig, filaDest, columnaDest)) {

            setCasilla(filaOrig, columnaOrig, 0);

            setCasilla(filaDest, columnaDest, this.turnoActual);

            int filaMed = (filaOrig + filaDest) / 2;
            int columnaMed = (columnaOrig + columnaDest) / 2;
            setCasilla(filaMed, columnaMed, 0);

            cambiarTurno();

            return true;

        }

        return false;
    }

    /*metodo para validar la captura de una ficha:
    - pregunta si la casilla esta libre y dentro del tablero
    - verifica que el salto sea de 2 casillas
    - calcula la posicion de la casilla del medio (donde se encuentra la ficha rival)
    - verifica que hay una ficha alli
        = si es del jugador 2, el jugador 1 la captura
        = si es del jugador 1, el jugador 2 la captura
     */
    public boolean validarCaptura(int filaOrig, int columnaOrig, int filaDest, int columnaDest) {
        if (!casillaEsValida(filaDest, columnaDest)) {
            return false;
        }

        if (Math.abs(filaDest - filaOrig) != 2 || Math.abs(columnaDest - columnaOrig) != 2) {
            return false;
        }

        int filaMed = (filaOrig + filaDest) / 2;
        int columnaMed = (columnaOrig + columnaDest) / 2;
        int fichaMed = obtenerCasilla(filaMed, columnaMed);

        if (this.turnoActual == 1 && fichaMed == 2) {
            return true;
        }

        return this.turnoActual == 2 && fichaMed == 1;
    }

    /*metodo para verificar ganador:
    -recorre la matriz verificando y contando cuantas 
    fichas tiene cada jugador
    -verifica cual de los dos tiene 0, y devuelve el contrario
    si no se cumple nada de esto, la partida continua
     */
    public int verificarGanador() {
        int fichasJugador1 = 0;
        int fichasJugador2 = 0;

        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                int casilla = obtenerCasilla(f, c);
                if (casilla == 1) {
                    fichasJugador1++;
                } else if (casilla == 2) {
                    fichasJugador2++;
                }
            }
        }

        if (fichasJugador1 == 0) {
            return 2;
        }
        if (fichasJugador2 == 0) {
            return 1;
        }
        return 0;
    }

}
