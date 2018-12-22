package Modelo;


import java.util.ArrayList;

/**
 * Esta clase representa un patron de juego.
 * <p>
 * 
 * Un patrón de juego indica las casillas participantes, de modo tal que el 
 * ganador de la partida es aquel jugador que primero complete el cartón 
 * en esas casillas.  Un ejemplo de patrón es el siguiente:
 *  x - - - x<p>
 *  - x - x -<p>
 *  - - * - -<p>
 *  - x - x -<p>
 *  x - - - x<p>
 * Por ejemplo, para el patrón “x”,  un cartón se considerará completo 
 * si han salido de la tómbola los números sobre la x. 
 * Existen diferentes tipos de patrones de juego, agrupados según la figura que forman. 
 * En efecto, se pueden construir patrones numéricos, alfabéticos, 
 * multi-combinación entre otros. Ejemplos de patrones alfabéticos comunes son
 * las letras: L, A, H, J,Z; y numéricos son: 0, 1, 7, entre otros.
 * Dentro de los patrones multi-combinación y patrones varios, el fin es formar 
 * figuras o rellenar espacios específicos, por ejemplo, se puede jugar a 
 * rellenar las cuatro esquinas de los cartones, casilleros intercalados, 
 * filas salteadas, o formar determinadas figuras como ser un corazón o un 
 * triángulo, entre otras.

 * @author miguel, isabel
 */

public class PatronJuego {
    //Attributos
    private String nombre;
    private String tipo;
    private int[][] patron;
    //mantiene una lista de todos los juegos que usan el patron.
    private ArrayList<Juego> juegos;
    //Constructor
    
    /**
     * Crea un nuevo patron de juego
     * @param nom nombre del patrón de juego
     * @param tipo tipo de patron
     * @param patron una matriz de 5x5 va valores 0 ó 1 que indica si la casilla forma
     * parte del patron o no.
     */
    public PatronJuego(String nom,String tipo,int[][] patron){
        nombre=nom;
        this.tipo=tipo;
        juegos=new ArrayList<Juego>();
        this.patron=patron;
       
    }

    /**
     * Vincula un juego con el patrón.  Cada patron mantiene una lista de los 
     * juegos que implementan el patrón, este método permite agregar el juego 
     * <code>g</code> a la lista.
     * @param g juego a agregar a la lista de juegos del patrón
     */
    public void addJuego(Juego g){
        juegos.add(g);
    }
    
    /**
     * Obtiene el nombre del patrón
     * @return nombre del cartón
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * obtiene el tipo del patrón
     * @return tipo del patrón
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * obtiene la representación matricial del patrón
     * @return representación matricial de patrón
     */
    public int[][] getPatron() {
        return patron;
    }
}
