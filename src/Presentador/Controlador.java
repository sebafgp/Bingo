/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentador;

import Modelo.*;
import Vista.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Sebastian Gonzalez S2, Dominique del Valle S1
 */
public class Controlador {

    //Singleton
    private Controlador() {
    }

    public static Controlador getInstance() {
        return ControladorHolder.instancia;
    }

    private static class ControladorHolder {

        private static Controlador instancia = new Controlador();
    }
    //----------

    private final static String LINE_SEPARATOR_PATTERN = "\r\n|[\n\r\u2028\u2029\u0085]"; //Definido por defecto en el Scanner
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private ArrayList<Juego> juegos = new ArrayList<>();
    private ArrayList<Carton> cartones = new ArrayList<>();
    private ArrayList<PatronJuego> patrones = new ArrayList<>();
    private ArrayList<Bingo> bingos = new ArrayList<>();
    private Modelo.Juego juegoActual;
    private Modelo.Bingo bingoActual;

    public static void main(String[] args) throws FileNotFoundException, Exception {
        Controlador.getInstance().leeArchivo();
        SistemaGestion s = new SistemaGestion();
        s.setVisible(true);
    }

    
    /*
    * Comprueba si existe el bingo cuyo nombre fue pasado por parametro.
    * Retorna un boolean indicando la existencia del bingo.
    */
    public boolean estaBingo(String nombre) {
        Bingo buscar = buscaBingo(nombre);
        return bingos.contains(buscar);
    }

    
    /*
    * Crea un archivo "reporteJugadores.txt" con los nombres de todos los jugadores registrados.
    * Se cambio el retrono de File a void debido al mensaje del profesor.
    */
    public void reporteJugadores() throws FileNotFoundException {
        File reporte = new File("reporteJugadores.txt");
        FileOutputStream fos = new FileOutputStream(reporte);
        PrintWriter salida = new PrintWriter(fos);

        salida.println("Reporte Jugadores");
        for (Jugador jugador : jugadores) {
            String rut, nombre, bingosJugados, bingosGanados;
            rut = jugador.getRut();
            nombre = jugador.getNombre();
            bingosJugados = Integer.toString(jugador.bingosJugados());
            bingosGanados = Integer.toString(jugador.bingosGanados());
            salida.println(rut + "," + nombre + ":" + bingosJugados + "," + bingosGanados);
        }

        salida.flush();
        salida.close();

    }
    
    
    /*
    * Crea un archivo "reporteJuegos.txt" con los nombres de todos los jugadores registrados.
    * Se cambio el retrono de File a void debido al mensaje del profesor.
    */
    public void reporteJuegos(String bingo) throws FileNotFoundException {
        File reporte = new File("reporteJuegos.txt");
        FileOutputStream fos = new FileOutputStream(reporte);
        PrintWriter salida = new PrintWriter(fos);
        Bingo bingoJugado = buscaBingo(bingo);

        salida.println("Reporte Juegos del Bingo: " + bingo);
        for (Juego juego : bingoJugado.getJuegos()) {
            String numeroJuego, nombreGanador, premio;
            boolean tieneGanador;
            numeroJuego = Integer.toString(juego.getNumero());
            try {
                nombreGanador = juego.getGanador().getPropietario().getNombre();
                tieneGanador = true;

            } catch (NullPointerException e) {
                nombreGanador = "";
                tieneGanador = false;
            }
            premio = juego.getPremio();
            if (tieneGanador) {
                salida.println(numeroJuego + ":" + nombreGanador + "," + premio);
            } else {
                salida.println(numeroJuego + ":" + premio);
            }
        }

        salida.flush();
        salida.close();

    }
    
    
    /*
    * Inicia el juego pasado por parametro, junto con su bingo.
    */
    public void iniciarJuego(int numero) {
        Juego juego = buscaJuego(numero);
        Bingo bingo = juego.getBingo();
        bingo.setEstado(Bingo.INICIADO);
        juego.setEstado(Bingo.INICIADO);
        juegoActual = juego;
        bingoActual = bingo;
    }

    
    /*
    * Termina el juego actual, declarando el carton pasado por parametro como ganador.
    */
    public void terminarJuegoActual(int cartonGanador) {
        Carton carton = buscaCarton(cartonGanador);
        juegoActual.setGanador(carton);
    }
    
    /*
    * Obtiene el juego actual.
    */
    public Juego getJuegoActual() {
        return juegoActual;
    }

    /*
    * Obtiene el bingo actual.
    */
    public Bingo getBingoActual() {
        return bingoActual;
    }

    
    /*
    * Saca un numero de la tombola para asignarlo al juego actual.
    * Retorna el numero seleccionado por la tombola
    */
    public int sacarNumero() {
        return juegoActual.sacarNumeroTombola();
    }

    
    /*
    * Comprueba si el carton cuyo numero se pasa por parametro es ganador.
    * Retorna un boolean con las siguientes condiciones:
    *   -Comprueba si el carton es ganador para el juego actual.
    *   -Comprueba si el carton pertenece al mismo bingo que se esta jugando
    *    actualmente.
    */
    public boolean esGanador(int carton) {
        Carton c = buscaCarton(carton); 
        return ((juegoActual.esGanador(c)) && (c.getBingo() == bingoActual));
    }
    
    /*
    * Retorna un String con el numero del juego actual y su respectivo patron.
    */
    public String nombreJuegoActual() {
        return Integer.toString(juegoActual.getNumero()) + "(" + juegoActual.getPatron().getNombre() + ")";
    }
    
    /*
    * Retorna un String indicando el premio del juego actual.
    */
    public String nombrePremioActual() {
        return juegoActual.getPremio();
    }
    
    /*
    * Inicializa el siguiente juego pendiente para ese bingo.
    * Lanza la excepcion Exception en caso de que no queden juegos pendientes.
    */
    public void siguienteJuego() throws Exception {
        boolean quedanJuegos = false;
        for (Juego juego : juegos) {
            if (juego.getEstado() == Bingo.NO_INICIADO && juego.getBingo() == bingoActual) {
                quedanJuegos = true;
                int numero = juego.getNumero();
                iniciarJuego(numero);
                break;
            }
        }
        if (!quedanJuegos) {
            bingoActual.setEstado(Bingo.TERMINADO);
            throw new Exception();
        }
    }

    
    /*
    * Retorna un arreglo de Strings con el nombre de todos los bingos en
    * el sistema.
    */
    public String[] cabeceraListaDeBingos() { //tampoco estoy seguro
        ArrayList<String> listaBingos = new ArrayList<>();
        for (Bingo bingo : bingos) {
            listaBingos.add(bingo.getNombreEvento());
        }

        String[] arregloLista = listaBingos.toArray(new String[listaBingos.size()]);
        return arregloLista;
    }

    
    /*
    * Retrona un ArrayList de Strings con toda la informacion de los bingos en
    * el siguiente formato:
    *   fecha del bingo,nombre del bingo,estado del bingo
    */
    public ArrayList<String> listaDeBingos() {
        ArrayList<String> lista = new ArrayList<>();

        for (Bingo bingo : bingos) {
            String estado;
            int getEstado = bingo.getEstado();
            switch (getEstado) {
                case 0:
                    estado = "NO INICIADO";
                    break;
                case 1:
                    estado = "INICIADO";
                    break;
                case 2:
                    estado = "TERMINADO";
                    break;
                default:
                    estado = "";
            }
            lista.add(bingo.getFecha().toString() + "," + bingo.getNombreEvento() + "," + estado);
        }

        return lista;
    }

    
    /*
    * Retorna un String con el nombre del bingo actual.
    */
    public String getBingo() {
        return bingoActual.getNombreEvento();
    }
    
    /*
    * Asigna el bingo cuyo nombre se pasa por parametro como bingo actual.
    */
    public void setBingo(String nombre) {
        Bingo bingo = buscaBingo(nombre);
        bingoActual = bingo;
    }
    
    
    /*
    * Lee los archivos para jugadores, patrones, bingos, cartones y juegos.
    * Lanza las siguientes excepciones:
    *   -FileNotFoundException: Cuando no se encuentra el archivo especificado.
    *   -Exception: Al ser posible lanzamiento de leeCartones
    */
    private void leeArchivo() throws FileNotFoundException, Exception {
        //Cada lectura se separa en una funcion para hacerlo mas claro
        leeJugadores();
        leePatrones();
        leeBingos();
        leeCartones();
        leeJuegos();
    }

    private void leeJugadores() throws FileNotFoundException {
        File archivoJugadores = new File("jugadores.txt");
        Scanner lectorArchivo = new Scanner(archivoJugadores);
        lectorArchivo.useDelimiter(",|-|\\r\n");
        String rut, digitoVerificador, rutFinal, nombre;
        LocalDate fechaNacimiento;
        while (lectorArchivo.hasNext()) {
            rut = lectorArchivo.next();
            digitoVerificador = lectorArchivo.next();
            rutFinal = rut + "-" + digitoVerificador;

            nombre = lectorArchivo.next();

            String diaNacimiento = lectorArchivo.next();
            String mesNacimiento = lectorArchivo.next();
            String anioNacimiento = lectorArchivo.next();

            fechaNacimiento = LocalDate.parse(anioNacimiento + "-" + mesNacimiento + "-" + diaNacimiento);

            Jugador jugador = new Jugador(rutFinal, nombre, fechaNacimiento);
            jugadores.add(jugador);
        }

        lectorArchivo.close();
    }

    private void leeCartones() throws FileNotFoundException, Exception {
        File archivoCartones = new File("cartones.txt");
        Scanner lectorArchivo = new Scanner(archivoCartones);
        lectorArchivo.useDelimiter(",|:|\\r\n");
        int id;
        int[][] numerosCarton = new int[5][5];
        String rutPropietario, nombreBingo; // bingo == nombreEvento? ej. Bingo 123
        while (lectorArchivo.hasNext()) {
            id = Integer.parseInt(lectorArchivo.next());
            rutPropietario = lectorArchivo.next();

            nombreBingo = lectorArchivo.next();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (i == 2 && j == 2) {
                        continue;
                    }
                    numerosCarton[j][i] = Integer.parseInt(lectorArchivo.next());
                }
            }
            Jugador jugador = buscaJugador(rutPropietario);
            Bingo bingo = buscaBingo(nombreBingo);
            Carton c = new Carton(id, numerosCarton, jugador, bingo);
            cartones.add(c);
        }
        lectorArchivo.close();

    }

    private void leePatrones() throws FileNotFoundException {
        File archivoPatrones = new File("patrones.txt");
        Scanner lectorArchivo = new Scanner(archivoPatrones);

        lectorArchivo.useDelimiter(",|-|\\r\n|[\\[\\]]");

        String nombrePatron, tipo;
        int numeroPosiciones, x, y;
        while (lectorArchivo.hasNext()) {
            int[][] posiciones = new int[5][5];
            nombrePatron = lectorArchivo.next();
            tipo = lectorArchivo.next();
            numeroPosiciones = Integer.parseInt(lectorArchivo.next());
            for (int i = 0; i < numeroPosiciones; i++) {
                //Revisar delimitador para caracteres []
                lectorArchivo.next(); //Skip para el caracter [ 
                x = Integer.parseInt(lectorArchivo.next());
                y = Integer.parseInt(lectorArchivo.next());
                posiciones[x][y] = 1;
                if (lectorArchivo.hasNext()) { //Centinela para ultimo caracter ]
                    lectorArchivo.next(); //Skip para el caracter ]
                }

            }
            PatronJuego patron = new PatronJuego(nombrePatron, tipo, posiciones);
            patrones.add(patron);
        }
        lectorArchivo.close();
    }

    private void leeBingos() throws FileNotFoundException {
        File archivoBingos = new File("bingos.txt");
        Scanner lectorArchivo = new Scanner(archivoBingos);
        lectorArchivo.useDelimiter(",|-|\\r\n");
        String dia, mes, anio;

        while (lectorArchivo.hasNext()) {

            dia = lectorArchivo.next();
            mes = lectorArchivo.next();
            anio = lectorArchivo.next();

            LocalDate fecha = LocalDate.parse(anio + "-" + mes + "-" + dia);
            String nombre = lectorArchivo.next();
            Bingo bingo = new Bingo(fecha, nombre);
            bingos.add(bingo);
        }
        lectorArchivo.close();
    }

    private void leeJuegos() throws FileNotFoundException {
        File archivoJuegos = new File("juegos.txt");
        Scanner lectorArchivo = new Scanner(archivoJuegos);
        lectorArchivo.useDelimiter(",|\\r\n");

        while (lectorArchivo.hasNext()) {
            String premio = lectorArchivo.next();
            int numJuego = Integer.parseInt(lectorArchivo.next());
            Bingo bingo = buscaBingo(lectorArchivo.next());
            PatronJuego patron = buscaPatron(lectorArchivo.next());

            Juego juego = new Juego(premio, numJuego, bingo, patron);
            juegos.add(juego);

        }

        lectorArchivo.close();
    }

    
    /*
    * Busca el jugador cuyo rut se pasa por parametro.
    * Retorna el jugador encontrado, o null en caso de no encontrarlo.
    */
    private Jugador buscaJugador(String rut) {
        for (Jugador jugador : jugadores) {
            if (jugador.getRut().equals(rut)) {
                return jugador;
            }
        }
        return null;
    }

    /*
    * Busca el bingo cuyo nombre se pasa por parametro.
    * Retorna el bingo encontrado, o null en caso de no encontrarlo.
    */
    private Bingo buscaBingo(String nombreEvento) {
        for (Bingo bingo : bingos) {
            if (bingo.getNombreEvento().equals(nombreEvento)) {
                return bingo;
            }
        }
        return null;
    }

    /*
    * Busca el patron cuyo nombre se pasa por parametro.
    * Retorna el patron encontrado, o null en caso de no encontrarlo.
    */
    private PatronJuego buscaPatron(String nombre) {
        for (PatronJuego patron : patrones) {
            if (patron.getNombre().equals(nombre)) {
                return patron;
            }
        }
        return null;
    }
    
    /*
    * Busca el carton cuya id se pasa por parametro.
    * Retorna el carton encontrado, o null en caso de no encontrarlo.
    */
    private Carton buscaCarton(int id) {
        for (Carton carton : cartones) {
            if (carton.getId() == id) {
                return carton;
            }
        }
        return null;
    }

    /*
    * Busca el juego cuyo numero se pasa por parametro.
    * Retorna el juego encontrado, o null en caso de no encontrarlo.
    */
    private Juego buscaJuego(int numero) {
        for (Juego juego : juegos) {
            if (juego.getNumero() == numero) {
                return juego;
            }
        }
        return null;
    }
}