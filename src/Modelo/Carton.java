package Modelo;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Esta clase representa el material que tienen los jugadores para competir 
 * en el juego de bingo. Cada cartón tiene un tamaño de 5x5, llenado con números
 * aleatorios del 1 al 75, a excepción del espacio del centro que es vacío.
 */

public class Carton {
    private int id;
    private int[][] numeros;
    private Jugador propietario;
    private Bingo   bingo;
    private ArrayList<Juego> ganador;

    public static int NEXT_ID=1;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carton other = (Carton) obj;
        if (!Arrays.deepEquals(this.numeros, other.numeros)) {
            return false;
        }
        return true;
    }
    public Carton() {
        id=getNEXT_ID();
        //creado aleatorio con id autoincremental
    }

    /**
     * Constructor
     * @param id corresponde al codigo identificador del cartón
     * @param mat corresponde a la matriz que contiene los números del cartón
     * @param propietario corresponde al jugador que compro el cartón
     * @param bingo corresponde al bingo a jugar
     * @throws Exception Esta excepción ocurrcuan cuando el cartón está repetido
     * en el bingo al cual se desea agregar y, por lo tanto, no se puede.
     */
    public Carton(int id, int[][] mat, Jugador propietario, Bingo bingo) throws Exception {
        this.id = id;
        this.numeros = mat;
        this.propietario = propietario;
        propietario.addCarton(this);//link con el propietario

        this.bingo=bingo;
        if(!bingo.addCarton(this)){
            throw new Exception("El cartón está repetido, no se puede agregar");
        }
        ganador=new ArrayList<Juego>();
    }
    
    /**
    * addJuegoGanador: Agrega un nuevo Juego ganador
    * @param j Juego que equivale a un nuevo juego ganado
    */
    public void addJuegoGanador(Juego j){
        ganador.add(j);
    }
    
    /**
    * getId(): Obtiene el id
    * @return id del cartón
    */
    public int getId() {
        return id;
    }

    /**
    * getMat(): Retorna números del cartón
    * @return  matriz con los que tiene el cartón
    */
    
    public int[][] getMat() {
        return numeros;
    }

    /* 
    * getPropietario(): Retorna propietario del cartón
    * @return objeto Jugador del propietario del bingo
    */

    public Jugador getPropietario() {
        return propietario;
    }

   /**
    * getGanador(): Retorna juegos ganados
    * @return juegos en donde el cartón ha sido ganador
    */
    public ArrayList<Juego> getGanador() {
        return ganador ;
    }
    
    /**
    * getBingo(): Retorna bingo a jugar
    * @return el bingo asociado al cartón
    */
    public Bingo getBingo() {
        return bingo;
    }

    /**
     * getNEXT_ID(): Permite aumentar incrementalmente el id
     * @return  próximo id
     */
    private static int getNEXT_ID() {
        return NEXT_ID++;
    }
}
