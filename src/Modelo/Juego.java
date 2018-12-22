package Modelo;

import java.util.ArrayList;

/**
 * Esta clase representa una partida de juego que tendrá el bingo, tendrá número
 * de juego, números elegidos, estado y premio.
 */
public class Juego {

    private String premio;
    //tablero es un arreglo de 75 números, 
    //si el número ha salido en el juego guarda true, sino false
    private boolean[] tablero;
    private int numero;
    private byte estado;

    //Asociaciones
    private Carton ganador;
    private Bingo bingo;
    private PatronJuego patron;

    /**
     * Constructor de la clase Juego
     * <p>
     * @param premio nombre del pregio que se le entrega al ganador
     * @param numero número del juego. Se usa para ordenar los juegos del bingo
     * e identificarlos
     * @param bingo bingo al que pertenece el juego
     * @param patron patron que se está jugando
     */
    public Juego(String premio, int numero, Bingo bingo, PatronJuego patron) {
        this.premio = premio;
        this.numero = numero;
        this.bingo = bingo;
        bingo.addJuego(this);//link con bingo
        this.patron = patron;
        patron.addJuego(this);//link con juego
        tablero = new boolean[76];
        estado = Bingo.NO_INICIADO;
    }

    /**
     * setGanador. Agrega el cartón ganador al juego cambiando su estado a
     * TERMINADO, solo si el cartón es el ganador
     *
     * @param c Cartón que ha ganado el juego
     * @return <code>true</code> si el cartón <code>c</code> es un ganador
     * válido, de otro modo retorna <code>false</code>
     */
    public boolean setGanador(Carton c) {
        if (esGanador(c)) {
            ganador = c;
            c.addJuegoGanador(this);//link  con cartón
            estado = Bingo.TERMINADO;
            return true;
        }
        return false;
    }

    /**
     * esGanador. Revisa si un cartón es un ganador. No cambia el estado del
     * juego
     *
     * @param c Cartón que se quiere revisar si es un ganador o no.
     * @return <code>true</code> si el cartón <code>c</code> es un ganador
     * válido, de otro modo retorna <code>false</code>
     */
    public boolean esGanador(Carton c) {
        int[][] patern = patron.getPatron();     
        for (int i = 0; i < c.getMat().length; i++) {
            for (int j = 0; j < c.getMat()[0].length; j++) {
                if (patern[i][j] > 0 && !tablero[c.getMat()[i][j]]) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getPremio() {
        return premio;
    }

    /**
     * getTablero. genera un ArrayList con los números que han salido de la
     * tómbola.
     *
     * @return ArrayList números que han salido de la tómbola
     */
    public ArrayList<Integer> getTablero() {
        ArrayList<Integer> out = new ArrayList<Integer>();
        for (int i = 0; i < tablero.length; i++) {
            if (tablero[i]) {
                out.add(i);
            }
        }
        return out;
    }

    /**
     * sacarNumeroTombola. Saca un número de la tómbola, si el juego no está
     * terminado. Si el juego está iniciado un número entre 1 y 75, sino retorna
     * -1.
     *
     * @return un número entre 1 y 75 que saca de la tómbola, sin repetir.
     */
    public int sacarNumeroTombola() {
        if (estado != Bingo.INICIADO) {
            return -1;
        }

        int x = rnd();
        while (tablero[x]) {//el número esté fuera de la tómbola
            x = rnd();
        }
        tablero[x] = true;
        return x;
    }

    /**
     * rnd: Obtiene un número aleatorio entre 1 y 75
     *
     * @return número aleatorio
     */
    private int rnd() {
        int n = (int) ((Math.random() * 90) + 1);
        return (n > 75) ? 75 : n;

    }

    /**
     * getNumero. Retorna el numero de juego
     *
     * @return numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * getEstado. Retorna el estado de juego
     *
     * @return estado
     */
    public byte getEstado() {
        return estado;
    }

    /**
     * getGanador. Retorna jugador que ganó el juego
     *
     * @return ganador
     */
    public Carton getGanador() {
        return ganador;
    }

    /**
     * getBingo. Retorna el bingo al que pertenece el juego
     *
     * @return bingo
     */
    public Bingo getBingo() {
        return bingo;
    }

    /**
     * getPatron. Retorna el patron que se usando en el juego
     *
     * @return patron
     */
    public PatronJuego getPatron() {
        return patron;
    }

    /**
     * setNumero: Cambia el número de juego por el recibido como parámetro
     *
     * @param numero nuevo número de juego. Este valor reemplaza al anterior.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

}
