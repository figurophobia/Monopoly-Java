package monopoly;

import java.time.chrono.ThaiBuddhistChronology;
import partida.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    public void setPosiciones(ArrayList<ArrayList<Casilla>> posiciones) {
        this.posiciones = posiciones;
    }


    public HashMap<String, Grupo> getGrupos() {
        return grupos;
    }


    public void setGrupos(HashMap<String, Grupo> grupos) {
        this.grupos = grupos;
    }


    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.banca = banca;
        this.posiciones = new ArrayList<ArrayList<Casilla>>();
        this.grupos = new HashMap<String, Grupo>();
        generarCasillas();
    }


    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }
    //Casillas del 1-10
    //Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> lado = new ArrayList<>();
        lado.add(new Especial("Salida", 1));
        lado.add(new Solar("Solar1", 2, Valor.GRUPO_BLACK, banca));
        lado.add(new AccionComunidad("Caja", 3));
        lado.add(new Solar("Solar2",  4, Valor.GRUPO_BLACK, banca));
        lado.add(new Impuesto("Imp1", 5, Valor.SUMA_VUELTA));
        lado.add(new Transporte("Trans1", 6, Valor.SUMA_VUELTA, banca));
        lado.add(new Solar("Solar3",  7, Valor.GRUPO_CYAN, banca));
        lado.add(new AccionSuerte("Suerte", 8));
        lado.add(new Solar("Solar4",  9, Valor.GRUPO_CYAN, banca));
        lado.add(new Solar("Solar5",  10, Valor.GRUPO_CYAN, banca));
        grupos.put("black",new Grupo((Solar) lado.get(1),(Solar) lado.get(3),Valor.BLACK));
        grupos.put("cyan",new Grupo((Solar) lado.get(6),(Solar) lado.get(8),(Solar) lado.get(9),Valor.CYAN));

        posiciones.add(lado);
    }
    //Casillas del 11-20
    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> lado = new ArrayList<>();
        lado.add(new Especial("Carcel", 11));
        lado.add(new Solar("Solar6",  12, Valor.GRUPO_PURPLE, banca));
        lado.add(new Servicio("Serv1", 13, Valor.SUMA_VUELTA*0.75f, banca));
        lado.add(new Solar("Solar7",  14, Valor.GRUPO_PURPLE, banca));
        lado.add(new Solar("Solar8",  15, Valor.GRUPO_PURPLE, banca));
        lado.add(new Transporte("Trans2", 16, Valor.SUMA_VUELTA, banca));
        lado.add(new Solar("Solar9",  17, Valor.GRUPO_YELLOW, banca));
        lado.add(new AccionComunidad("Caja", 18));
        lado.add(new Solar("Solar10",  19, Valor.GRUPO_YELLOW, banca));
        lado.add(new Solar("Solar11",  20, Valor.GRUPO_YELLOW, banca));
        grupos.put("purple", new Grupo((Solar) lado.get(1),(Solar) lado.get(3),(Solar) lado.get(4),Valor.PURPLE));
        grupos.put("yellow", new Grupo((Solar) lado.get(6),(Solar) lado.get(8),(Solar) lado.get(9),Valor.YELLOW));

        posiciones.add(lado);
    }
    //Método para insertar las casillas del lado norte.
    //Casillas del 21-30
    private void insertarLadoNorte() {
        ArrayList<Casilla> lado = new ArrayList<>();
        lado.add(new Especial("Parking", 21));
        lado.add(new Solar("Solar12",  22, Valor.GRUPO_RED, banca));
        lado.add(new AccionSuerte("Suerte2", 23));
        lado.add(new Solar("Solar13",  24, Valor.GRUPO_RED, banca));
        lado.add(new Solar("Solar14",  25, Valor.GRUPO_RED, banca));
        lado.add(new Transporte("Trans3", 26, Valor.SUMA_VUELTA, banca));
        lado.add(new Solar("Solar15",  27, Valor.GRUPO_BROWN, banca));
        lado.add(new Solar("Solar16",  28, Valor.GRUPO_BROWN, banca));
        lado.add(new Servicio("Serv2", 29, Valor.SUMA_VUELTA * 0.75f, banca));
        lado.add(new Solar("Solar17", 30, Valor.GRUPO_BROWN, banca));

        grupos.put("red", new Grupo((Solar)(Solar) lado.get(1), (Solar)(Solar) lado.get(3),(Solar)(Solar) lado.get(4), Valor.RED));
        grupos.put("brown", new Grupo((Solar) lado.get(6), (Solar) lado.get(7),(Solar) lado.get(9), Valor.BROWN));
        this.posiciones.add(lado);
    }
    
    
    //Casillas del 31-40
    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> lado = new ArrayList<>();
        lado.add(new Especial("IrCarcel", 31));
        lado.add(new Solar("Solar18",  32, Valor.GRUPO_GREEN, banca));
        lado.add(new Solar("Solar19",  33, Valor.GRUPO_GREEN, banca));
        lado.add(new AccionComunidad("Caja", 34));
        lado.add(new Solar("Solar20",  35, Valor.GRUPO_GREEN, banca));
        lado.add(new Transporte("Trans4", 36, Valor.SUMA_VUELTA, banca));
        lado.add(new AccionSuerte("Suerte3", 37));
        lado.add(new Solar("Solar21",  38, Valor.GRUPO_BLUE, banca));
        lado.add(new Impuesto("Imp2", 39, (Valor.SUMA_VUELTA)/2));
        lado.add(new Solar("Solar22",  40, Valor.GRUPO_BLUE, banca));
        grupos.put("green", new Grupo((Solar) lado.get(1),(Solar) lado.get(2),(Solar) lado.get(4),Valor.GREEN));
        grupos.put("blue",new Grupo((Solar) lado.get(7),(Solar) lado.get(9),Valor.BLUE));

        posiciones.add(lado);
    }


    @Override
    public String toString() {
        StringBuilder tableroStr = new StringBuilder();
        // Imprimir el lado norte (posiciones[2]), de izquierda a derecha
        tableroStr.append(" ");
        for (int j = 0; j < 11; j++) {
            tableroStr.append(Valor.SUBRAYADO+" ".repeat(Valor.width)+Valor.RESET+" ");

        }
        tableroStr.append("\n");
        for (int i = 0; i < 10; i++) { 
            tableroStr.append("|"+posiciones.get(2).get(i).printOneCasilla());
        }
        tableroStr.append("|"+posiciones.get(3).get(0).printOneCasilla()+"|");
        tableroStr.append("\n");
    
        // Imprimir los lados oeste y este simultáneamente
        for (int i = 9; i > 0; i--) {
            // Lado oeste (posiciones[1]): desde la cárcel hasta el inicio (de abajo hacia arriba)
            tableroStr.append("|"+posiciones.get(1).get(i).printOneCasilla()+"|"); 
            if(i==1){
                for (int j = 0; j < 8; j++) {
                    tableroStr.append(Valor.SUBRAYADO+" ".repeat(Valor.width)+Valor.RESET+" ");

                }
                tableroStr.append(Valor.SUBRAYADO+" ".repeat(Valor.width)+Valor.RESET);
            }else{tableroStr.append(" ".repeat(9*Valor.width+8));}
            // Lado este (posiciones[3]): desde el inicio (arriba) hacia abajo
            tableroStr.append("|"+posiciones.get(3).get(10-i).printOneCasilla()+"|");
    
            tableroStr.append("\n");
        }
    
        // Imprimir el lado sur (posiciones[0]), de derecha a izquierda
        tableroStr.append("|"+posiciones.get(1).get(0).printOneCasilla());
        for (int i = 9; i >= 0; i--) { 
            tableroStr.append("|"+posiciones.get(0).get(i).printOneCasilla());
        }
        tableroStr.append("|\n");
    
        return tableroStr.toString();
    }
    
    public ArrayList<ArrayList<Casilla>> getPosiciones() {
        return posiciones;
    }
    

    public Casilla getCasilla(int posicion){
        
        return this.posiciones.get((int) posicion/10).get(posicion%10);
    }
    
    //Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla casillaByName(String nombre) {
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla casilla : lado) {
                if (casilla.getNombre().equals(nombre)) {
                    return casilla;
                }
            }
        }
        return null;
    }
    /*
     * Las casillas de solar tienen un precio de compra inicial, el cual se incrementa un 5% cada vez que todos
    los avatares completen cuatro vueltas al tablero sin que hayan sido compradas por ninguno de ellos.
    No se considera una vuelta si el avatar va a la casilla de Cárcel.
     */
    public void subirPrecio4Vueltas(){

        for (ArrayList<Casilla> lado : posiciones) {

            for (Casilla casilla : lado) {
                if (!(casilla instanceof Solar solar))
                    continue;
                if (!solar.getDuenho().equals(banca))
                    continue;
                
                float valor = solar.getValor()*0.05f;
                float precioAlquiler = solar.getPrecioAlquiler()*0.05f;
                solar.sumarValor(valor);
                solar.sumarPrecioAlquiler(precioAlquiler);
                solar.setCuatrovueltas(true);

            }
        }
        Juego.consola.imprimir("Todos los jugadores han dado 4 vueltas.");
        Juego.consola.imprimir("Los precios de los solares no comprados han subido un 5%.");
    }

    
    
}
