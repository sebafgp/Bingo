package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Esta clase representa a un jugador de bingo. Por cada jugador se desea
 * conocer su nombre y su fecha de nacimiento.
 *
 * @author miguel
 */
public class Jugador {

    //Atributos
    private String rut;
    private String nombre;
    private LocalDate fechaNacimiento;

    //Relaciones
    private ArrayList<Carton> cartones;

    //Constructor
    /**
     * Este constructor permite crear un nuevo jugador. Se debe indicar para
     * ello El nombre del jugador y su fecha de nacimiento
     *
     * @param nom nombre del jugador
     * @param fechaNac fecha de nacimiento del jugador
     */
    public Jugador(String rut, String nom, LocalDate fechaNac) {
        //Precond: nom<>null, fechaNac<>null
        this.rut = rut;
        nombre = nom;
        fechaNacimiento = fechaNac;
        cartones = new ArrayList<Carton>();
    }

    /**
     * Permite obtener el rut del jugador.
     *
     * @return rut del jugador
     */
    public String getRut() {
        return rut;
    }

    /**
     * Permite obtener el nombre del jugador.
     *
     * @return nombre actual del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Permite cambiar el nombre del jugador
     *
     * @param nombre nuevo nombre del jugador. Este reemplaza al nombre actual.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Permite obtener la fecha de nacimiento del juegador.
     *
     * @return fecha de nacimiento del jugador
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Permite cambiar la fecha de nacimiento del juegador.
     *
     * @param fechaNacimiento Nueva fecha de nacimiento del jugador. Reemplaza a
     * la fecha actual de nacimiento.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Agrega una nuevo cartón a la lista de cartones del jugador
     *
     * @param c nuevo cartón que se quiere asociar con el jugador.
     */
    public void addCarton(Carton c) {
        cartones.add(c);
    }

    /**
     * Cuenta cuantos juegos de bingos a ganado el jugador
     *
     * @return cantidad de juegos de bingo ganados. Notar que en un mismo evento
     * de bingo, un carton puede ganar varios de sus juegos.
     */
    public int bingosGanados() {
        ArrayList<Bingo> bingos = new ArrayList<>();
        int contador = 0;
        for (Carton carton : cartones) {
            Bingo b = carton.getBingo();
            
            //Agrega el bingo al auxiliar, y comprueba si ha ganado al menos un juego en el
            if (!bingos.contains(b) && b.getEstado() == Bingo.TERMINADO){
                bingos.add(b);
                if (carton.getGanador().size() > 0){
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Indica el total de bingos que ha jugado un jugador
     *
     * @return el número total de bingos que ha jugado. En este caso se refiere
     * al total de bingos y no al total de juegos de bingos.
     */
    public int bingosJugados() {
        ArrayList<Bingo> bingosJugados = new ArrayList<>();
        for (Carton carton : cartones) {
            Bingo b = carton.getBingo();
            if (!bingosJugados.contains(b) && b.getEstado() != Bingo.NO_INICIADO){
                bingosJugados.add(b);
            }
        }
        return bingosJugados.size();
    }
    
    
}
