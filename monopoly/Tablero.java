package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

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
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("Salida", "Especial", 1, banca));
        lado.add(new Casilla("Solar1", "Solar", 2, Valor.GRUPO_BLACK, banca));
        lado.add(new Casilla("Caja", "Comunidad", 3, banca));
        lado.add(new Casilla("Solar2", "Solar", 4, Valor.GRUPO_BLACK, banca));
        lado.add(new Casilla("Imp1", 5, banca,Valor.SUMA_VUELTA));
        lado.add(new Casilla("Trans1", "Transporte", 6, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Solar3", "Solar", 7, Valor.GRUPO_CYAN, banca));
        lado.add(new Casilla("Suerte", "Suerte", 8, banca));
        lado.add(new Casilla("Solar4", "Solar", 9, Valor.GRUPO_CYAN, banca));
        lado.add(new Casilla("Solar5", "Solar", 10, Valor.GRUPO_CYAN, banca));
        grupos.put("black",new Grupo(lado.get(1),lado.get(3),Valor.BLACK));
        grupos.put("cyan",new Grupo(lado.get(6),lado.get(8),lado.get(9),Valor.CYAN));

        posiciones.add(lado);
    }
    //Casillas del 11-20
    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("Carcel", "Especial", 11, banca));
        lado.add(new Casilla("Solar6", "Solar", 12, Valor.GRUPO_PURPLE, banca));
        lado.add(new Casilla("Serv1", "Serv", 13, Valor.SUMA_VUELTA*0.75f, banca));
        lado.add(new Casilla("Solar7", "Solar", 14, Valor.GRUPO_PURPLE, banca));
        lado.add(new Casilla("Solar8", "Solar", 15, Valor.GRUPO_PURPLE, banca));
        lado.add(new Casilla("Trans2", "Transporte", 16, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Solar9", "Solar", 17, Valor.GRUPO_YELLOW, banca));
        lado.add(new Casilla("Caja", "Comunidad", 18, banca));
        lado.add(new Casilla("Solar10", "Solar", 19, Valor.GRUPO_YELLOW, banca));
        lado.add(new Casilla("Solar11", "Solar", 20, Valor.GRUPO_YELLOW, banca));
        grupos.put("purple", new Grupo(lado.get(1),lado.get(3),lado.get(4),Valor.PURPLE));
        grupos.put("yellow", new Grupo(lado.get(6),lado.get(8),lado.get(9),Valor.YELLOW));

        posiciones.add(lado);
    }

    //Método para insertar las casillas del lado norte.
    //Casillas del 21-30
    private void insertarLadoNorte() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("Parking", "Especial", 21, banca));
        lado.add(new Casilla("Solar12", "Solar", 22, Valor.GRUPO_RED, banca));
        lado.add(new Casilla("Suerte2", "Suerte", 23, banca));
        lado.add(new Casilla("Solar13", "Solar", 24, Valor.GRUPO_RED, banca));
        lado.add(new Casilla("Solar14", "Solar", 25, Valor.GRUPO_RED, banca));
        lado.add(new Casilla("Trans3", "Transporte", 26, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Solar15", "Solar", 27, Valor.GRUPO_BROWN, banca));
        lado.add(new Casilla("Solar16", "Solar", 28, Valor.GRUPO_BROWN, banca));
        lado.add(new Casilla("Serv2", "Serv", 29, Valor.SUMA_VUELTA * 0.75f, banca));
        lado.add(new Casilla("Solar17", "Solar",30, Valor.GRUPO_BROWN, banca));

        grupos.put("red", new Grupo(lado.get(1), lado.get(3),lado.get(4), Valor.RED));
        grupos.put("brown", new Grupo(lado.get(6), lado.get(7),lado.get(9), Valor.BROWN));
        this.posiciones.add(lado);
    }
    
    
    //Casillas del 31-40
    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("IrCarcel", "Especial", 31, banca));
        lado.add(new Casilla("Solar18", "Solar", 32, Valor.GRUPO_GREEN, banca));
        lado.add(new Casilla("Solar19", "Solar", 33, Valor.GRUPO_GREEN, banca));
        lado.add(new Casilla("Caja", "Comunidad", 34, banca));
        lado.add(new Casilla("Solar20", "Solar", 35, Valor.GRUPO_GREEN, banca));
        lado.add(new Casilla("Trans4", "Transporte", 36, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Suerte3", "Suerte", 37, banca));
        lado.add(new Casilla("Solar21", "Solar", 38, Valor.GRUPO_BLUE, banca));
        lado.add(new Casilla("Imp2", 39, banca, (Valor.SUMA_VUELTA)/2));
        lado.add(new Casilla("Solar22", "Solar", 40, Valor.GRUPO_BLUE, banca));
        grupos.put("green", new Grupo(lado.get(1),lado.get(2),lado.get(4),Valor.GREEN));
        grupos.put("blue",new Grupo(lado.get(7),lado.get(9),Valor.BLUE));

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
    
    
    

    public Casilla getCasilla(int posicion){
        
        return this.posiciones.get((int) posicion/10).get(posicion%10);
    }
    
    //Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre) {
        for (ArrayList<Casilla> i : posiciones) { // Recorre cada lado del tablero
            for (Casilla cas : i) { // Recorre cada casilla en el lado
                if (cas.getNombre().equals(nombre)) { // Si el nombre coincide
                    return cas; // Retorna la casilla encontrada
                }
            }
        }
        return null; // Retorna null si no se encontró ninguna casilla con el nombre dado
    }
    
}
