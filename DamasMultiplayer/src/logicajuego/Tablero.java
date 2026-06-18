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
        for (int f = 5; f <= 7; f++) {
            for (int c = 0; c <= 7; c++) {
                if ((f + c) % 2 != 0) {
                    matrizTablero[f][c] = 1;
                }
            }
        }

        //fichas jugador 2
        for (int f = 0; f <= 2; f++) {
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

        System.out.println("turno de: " + this.turnoActual);
    }

    /*metodo para obtener el valor de una casilla:
    - si el valor es 0 = esta vacia
    - si el valor es 1 = hay una ficha del jugador 1
    - si el valor es 2 = hay una ficha del jugador 2
    - si el valor es 3 = hay una reina del jugador 1
    - si el valor es 4 = hay una reina del jugador 2
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
            return (obtenerCasilla(fila, columna) == 0);

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

        int ficha = obtenerCasilla(filaOrig, columnaOrig);
        boolean direccionOk = false;

        if (ficha == 3 || ficha == 4) {
            direccionOk = true;
        } else if (ficha == 1 && filaDest < filaOrig) {
            direccionOk = true;
        } else if (ficha == 2 && filaDest > filaOrig) {
            direccionOk = true;
        }

        boolean distanciaOk = Math.abs(filaDest - filaOrig) == 1;

        return destinoOk && diagonalOk && direccionOk && distanciaOk;
    }

    /*metodo que verifica si el jugador llego al extremo contrario
    y convierte su ficha en reina
     */
    public void coronacionValida(int fila, int columna) {
        int ficha = obtenerCasilla(fila, columna);

        if (ficha == 1 && fila == 0) {
            setCasilla(fila, columna, 3);
        } else if (ficha == 2 && fila == 7) {
            setCasilla(fila, columna, 4);
        }

    }

    /*metodo para mover una ficha:
    - obtiene la casilla que se va a mover
    - valida si se puede el movimiento
    - le asigna el valor 0 a la casilla que deja y le asigna el valor
    del jugador a la casilla a la que se mueve.
    - verifica si despues del movimiento, se convierte o no en reina
    - si es posible hacer una captura, bloquea los movimientos que no
    sean la captura
    - cambia el turno
     */
    public boolean moverFicha(int filaOrig, int columnaOrig, int filaDest, int columnaDest) {
        int fichaAMover = obtenerCasilla(filaOrig, columnaOrig);

        //pimero verifica si hay capturas disponibles
        if (hayCapturaDisp(this.turnoActual)) {

            if (!validarCaptura(filaOrig, columnaOrig, filaDest, columnaDest)) {
                System.out.println("DEBUG. Movimiento rechazado. Tienes una captura obligatoria en el Tablero!");
                return false;
            }
        }

        //si el movimiento es basico
        if (validarMovimientos(filaOrig, columnaOrig, filaDest, columnaDest)) {
            setCasilla(filaOrig, columnaOrig, 0);

            setCasilla(filaDest, columnaDest, fichaAMover);

            coronacionValida(filaDest, columnaDest);

            cambiarTurno();

            return true;

            //si el movimiento es una captura
        } else if (validarCaptura(filaOrig, columnaOrig, filaDest, columnaDest)) {

            setCasilla(filaOrig, columnaOrig, 0);

            setCasilla(filaDest, columnaDest, fichaAMover);

            int filaMed = (filaOrig + filaDest) / 2;
            int columnaMed = (columnaOrig + columnaDest) / 2;
            setCasilla(filaMed, columnaMed, 0);

            coronacionValida(filaDest, columnaDest);

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
        if (filaDest < 0 || filaDest > 7 || columnaDest < 0 || columnaDest > 7) {
            return false;
        }

        if (!casillaEsValida(filaDest, columnaDest)) {
            return false;
        }

        if (Math.abs(filaDest - filaOrig) != 2 || Math.abs(columnaDest - columnaOrig) != 2) {
            return false;
        }

        int filaMed = (filaOrig + filaDest) / 2;
        int columnaMed = (columnaOrig + columnaDest) / 2;
        int fichaMed = obtenerCasilla(filaMed, columnaMed);

        int ficha = obtenerCasilla(filaOrig, columnaOrig);
        if (ficha == 1 && filaDest > filaOrig) {
            return false;
        }
        if (ficha == 2 && filaDest < filaOrig) {
            return false;
        }

        if (ficha == 1 || ficha == 3) {
            return (fichaMed == 2 || fichaMed == 4);
        }

        if (ficha == 2 || ficha == 4) {
            return (fichaMed == 1 || fichaMed == 3);
        }
        return false;
    }

    /*metodo que revisa si hay capturas disponibles desde la posicion
    donde se situa el jugador*/
    public boolean hayCapturaDisp(int jugador) {
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                int ficha = obtenerCasilla(f, c);

                if ((jugador == 1 && (ficha == 1 || ficha == 3))
                        || (jugador == 2 && (ficha == 2 || ficha == 4))) {

                    if (validarCaptura(f, c, f - 2, c - 2)) {
                        return true;
                    }
                    if (validarCaptura(f, c, f - 2, c + 2)) {
                        return true;
                    }
                    if (validarCaptura(f, c, f + 2, c - 2)) {
                        return true;
                    }
                    if (validarCaptura(f, c, f + 2, c + 2)) {
                        return true;
                    }

                }
            }
        }
        return false;
    }


//metodo para revisar si el jugador tiene movimientos disponibles
public boolean hayMovDisp(int jugador) {
        int[] df = {-1, -1, 1, 1};
        int[] dc = {-1, -1, 1, 1};

        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                int ficha = obtenerCasilla(f, c);

                if ((jugador == 1 && (ficha == 1 || ficha == 3))
                        || (jugador == 2 && (ficha == 2 || ficha == 4))) {
                    for (int i = 0; i < 4; i++) {
                        if (validarMovimientos(f, c, f + df[i], c + dc[i])) {
                            return true;
                        }
                        if (validarCaptura(f, c, f + (df[i] * 2), c + (dc[i] * 2))) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    /*metodo para verificar ganador:
    -recorre la matriz verificando y contando cuantas 
    fichas tiene cada jugador
    -verifica cual de los dos tiene 0, y devuelve el contrario
    -verifica cual de los dos ya no tiene movimientos disponibles (bloqueado)
    y devuelve el contrario
    -si no se cumple nada de esto, la partida continua
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
        if (this.turnoActual == 2 && !hayMovDisp(1)) {
            return 2;
        }
        if (this.turnoActual == 1 && !hayMovDisp(2)) {
            return 1;
        }
        return 0;
    }
    
    public int obtenerTurno(){
        return this.turnoActual;
    }
}
