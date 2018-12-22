package Modelo;


import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Esta clase representa un bingo en particular que ocurre en una fecha 
 * determinada y tiene un nombre.
 * @author isabelmargarita, miguelromero
 */
public class Bingo {
    private LocalDate fecha;
    private String nombreEvento; //único, no nulo
    private byte estado;
    private ArrayList<Carton> cartones;
    private ArrayList<Juego> juegos;
    /**
     * Indica que el bingo no ha iniciado
     */
    public static final byte NO_INICIADO=0;
    /**
     * Indica que el bingo se ha iniciado
     */
    public static final byte INICIADO=1;
    /**
     * Indica que el bingo se ha terminado
     */
    public static final byte TERMINADO=2;

    /**
     * Crea un objeto de la clase Bingo. 
     * <p>
     * Para crear un objeto con este constructor se debe proveer de la fecha 
     * y nombre del evento. Cuando se crea un Bingo el estado es no iniciado
     * 
     * @param fecha corresponde a la fecha en que se hace el evento
     * @param nombreEvento el nombre que identifica un evento
     */
    public Bingo(LocalDate fecha, String nombreEvento) {
        this.fecha = fecha;
        this.nombreEvento = nombreEvento;
        this.estado=NO_INICIADO;
        cartones=new ArrayList<Carton>();
        juegos=new ArrayList<Juego>();
    }

    /**
     * Agrega un cartón nuevo al Bingo.
     * <p>
     * @param c Cartón a agregar al bingo. Este cartón no debe repetirse.
     * @return <code>true</code> si el cartón no se ha agregado previamente, falso 
     */
    public boolean addCarton(Carton c){
        if(cartones.contains(c)) return false;
        return true;
        
    }
    /**
     * Agrega un juego al Bingo.
     * @param j Juego a agregar al bingo. Este juego se pueded repetir.
     * 
     */
    public void addJuego(Juego j){
        juegos.add(j);
    }
    /**
     * Retorna la fecha en la cual ocurre el bingo
     * @return fecha del bingo
     */
    public LocalDate getFecha() {
        return fecha;
    }
    
     /**
     * Retorna los juegos del bingo
     * @return juegos
     */
    public ArrayList <Juego> getJuegos() {
        return juegos;
    }
    
    /**
     * Cambia la fecha del bingo
     * @param fecha nueva fecha del evento
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Obtiene el nombre que identifica al Bingo.
     * @return nombre del bingo
     */
    public String getNombreEvento() {
        return nombreEvento;
    }

    /**
     * Cambia el nombre que identifica al bingo.
     * @param nombreEvento nuevo nombre del bingo
     */
    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    /**
     * Obtiene el estado del bingo, el cual puede ser: 
     * <code>NO_INCIADO, INICIADO, TERMINADO</code>
     * @return estado actual del bingo
     */
    public byte getEstado() {
        return estado;
    }

    /**
     * Cambia el estado actual del bingo.
     * @param estado El nuevo estado, que puede ser:
     * <code>NO_INCIADO, INICIADO, TERMINADO</code>
     */
    public void setEstado(byte estado) {
        this.estado = estado;
    }
    
    

    

    
}
