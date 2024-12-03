package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import partida.*;

public class Tratos {

    private Casilla casilla1;
    private Casilla casilla2;
    private float dinero1;
    //lista de tratos
    private ArrayList<String> tratos = new ArrayList<>();
    private int idCounter = 0;
    private Tablero tablero;

    public Tratos(Tablero tablero){
        this.tablero = tablero;
    }
    

    public void verTratos(){
        if(tratos.isEmpty()){
            System.out.println("No hay tratos activos");
        }
        else{
            for (String trato : tratos) {
                System.out.println(trato);
            }
        }
    }

    public void anadirTrato(String trato, Jugador jugadorPropone, Jugador jugadorAcepta) {
        String tratoString = String.format("{\nid: trato%d \njugadorPropone: %s \njugadorAcepta: %s\ntrato: %s\n}\n", 
                                            idCounter++, 
                                            jugadorPropone.getNombre(), 
                                            jugadorAcepta.getNombre(), 
                                            trato);
        tratos.add(tratoString);
    }

    public void proponerTrato(Jugador jugador1, Jugador jugador2){
        
        System.out.println("""
                            \nIntroduce el trato que quieres proponer (tipos disponibles):
                            cambiar (SolarX, SolarY)
                            cambiar (SolarX, cantidad)
                            cambiar (SolarX, SolarY y cantidad) 
                            """);
        //leer trato
        Scanner sc = new Scanner(System.in);
        String trato = sc.nextLine();

        //procesar campos del trato
        ArrayList<String> campos = procesarLinea(trato);

        //comprobar que tipo de trato es y asignar valores
        //antes de añadir, hay que comprobar que el trato es válido
        //el trato es válido si los jugadores tienen las casillas
        //el trato se mantiene aunque no tengan el dinero (ya que podrian conseguirlo en un futuro)
        if (campos != null) {
            if (campos.size() == 2) {
                // cambiar (SolarX, SolarY) o cambiar (SolarX, cantidad)
                casilla1 = tablero.casillaByName(campos.get(0));
                try {
                    dinero1 = Float.parseFloat(campos.get(1));
                    // cambiar (SolarX, cantidad)
                    System.out.println("TRATO 1");
                    for (Casilla casilla : jugador1.getPropiedades()) {
                        if(casilla.getNombre().equals(casilla1.getNombre())) anadirTrato(trato, jugador1, jugador2);
                    }
                    //if (jugador1.getPropiedades().contains(casilla1)) {
                    //    anadirTrato(trato, jugador1, jugador2);
                    //
                } catch (NumberFormatException e) {
                    // cambiar (SolarX, SolarY)ç
                    System.out.println("TRATO 2");
                    casilla2 = tablero.casillaByName(campos.get(1));
                    for(Casilla casillaX : jugador1.getPropiedades()){
                        for(Casilla casillaY : jugador2.getPropiedades()){
                            if(casillaX.getNombre().equals(casilla1.getNombre()) && casillaY.getNombre().equals(casilla2.getNombre())){
                                anadirTrato(trato, jugador1, jugador2);
                            }
                        }
                    }
                    /*
                    if (jugador1.getPropiedades().contains(casilla1) && jugador2.getPropiedades().contains(casilla2)) {
                        anadirTrato(trato, jugador1, jugador2);
                    } else {
                        System.out.println("El trato no es válido, los jugadores deben tener esas casillas\n");
                    }*/
                }
            } else if (campos.size() == 3) {
                // cambiar (SolarX, SolarY y cantidad)
                System.out.println("TRATO 3");
                casilla1 = tablero.casillaByName(campos.get(0));
                casilla2 = tablero.casillaByName(campos.get(1));
                dinero1 = Float.parseFloat(campos.get(2));
                for(Casilla casillaX : jugador1.getPropiedades()){
                    for(Casilla casillaY : jugador2.getPropiedades()){
                        if(casillaX.getNombre().equals(casilla1.getNombre()) && casillaY.getNombre().equals(casilla2.getNombre())){
                            anadirTrato(trato, jugador1, jugador2);
                        }
                    }
                }
                /*
                if (jugador1.getPropiedades().contains(casilla1) && jugador2.getPropiedades().contains(casilla2)) {
                    anadirTrato(trato, jugador1, jugador2);
                } else {
                    System.out.println("El trato no es válido, los jugadores deben tener esas casillas\n");
                }*/
            } else {
                System.out.println("El trato no es válido, formato incorrecto\n");
            }
        } else {
            System.out.println("El trato no es válido, formato incorrecto\n");
        }
    }

    public void aceptarTrato(String tratoID, Jugador jugador2, ArrayList<Jugador> jugadores){
        //identificar trato en arraylist de tratos
        String trato = null;
        for (String t : tratos) {
            if (t.contains(tratoID)) {
                trato = t;
                break;
            }
        }
        if (trato == null) {
            System.out.println("Trato no encontrado");
            return;
        }
        Jugador jugador1 = null;
        
        //obtener nombre de jugador que propone el trato, y el trato
        String[] tratoSplit = trato.split("\n");
        String nombreJugador1 = tratoSplit[2].split(": ")[1].trim();
        trato = tratoSplit[4].split(": ")[1].trim();

        //obtener jugador que propone el trato
        for (Jugador player : jugadores) {
            if (player.getNombre().equals(nombreJugador1)) {
                jugador1 = player;
                break;
            }
        }
        if (jugador1 == null) {
            System.out.println("Jugador no encontrado");
            return;
        }
        //procesar campos del trato
        ArrayList<String> campos = procesarLinea(trato);
        if (campos != null) {
            if (campos.size() == 2) {
                casilla1 = tablero.casillaByName(campos.get(0));
                try {
                    dinero1 = Float.parseFloat(campos.get(1));
                    // cambiar (SolarX, cantidad)
                    for(Casilla casilla : jugador1.getPropiedades()){
                        if(casilla.getNombre().equals(casilla1.getNombre()) && jugador2.getFortuna() >= dinero1){
                            jugador1.setFortuna(jugador1.getFortuna() + dinero1);
                            jugador2.setFortuna(jugador2.getFortuna() - dinero1);
                            jugador1.eliminarPropiedad((Propiedad)casilla1);
                            jugador2.anhadirPropiedad(casilla1);
                            tratos.remove(trato);
                            System.out.println("Trato aceptado");
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    // cambiar (SolarX, SolarY)
                    casilla2 = tablero.casillaByName(campos.get(1));
                    for(Casilla casilla : jugador1.getPropiedades()){
                        for(Casilla casillaY : jugador2.getPropiedades()){
                            if(casilla.getNombre().equals(casilla1.getNombre()) && casillaY.getNombre().equals(casilla2.getNombre())){
                                jugador1.eliminarPropiedad((Propiedad)casilla1);
                                jugador2.eliminarPropiedad((Propiedad)casilla2);
                                jugador1.anhadirPropiedad(casilla2);
                                jugador2.anhadirPropiedad(casilla1);
                                tratos.remove(trato);
                                System.out.println("Trato aceptado");
                                break;
                            }
                        }
                    }
                }
            } else if (campos.size() == 3) {
                // cambiar (SolarX, SolarY y cantidad)
                casilla1 = tablero.casillaByName(campos.get(0));
                casilla2 = tablero.casillaByName(campos.get(1));
                dinero1 = Float.parseFloat(campos.get(2));
                for(Casilla casilla : jugador1.getPropiedades()){
                    for(Casilla casillaY : jugador2.getPropiedades()){
                        if(casilla.getNombre().equals(casilla1.getNombre()) && casillaY.getNombre().equals(casilla2.getNombre()) && jugador2.getFortuna() >= dinero1){
                            jugador1.setFortuna(jugador1.getFortuna() + dinero1);
                            jugador2.setFortuna(jugador2.getFortuna() - dinero1);
                            jugador1.eliminarPropiedad((Propiedad)casilla1);
                            jugador2.eliminarPropiedad((Propiedad)casilla2);
                            jugador1.anhadirPropiedad(casilla2);
                            jugador2.anhadirPropiedad(casilla1);
                            tratos.remove(trato);
                            System.out.println("Trato aceptado");
                            break;
                        }
                    }
                }
            } else {
                System.out.println("El trato no es válido, formato incorrecto\n");
            }
        }
        
    }
    
    public ArrayList<String> procesarLinea(String linea) {
        ArrayList<String> campos = new ArrayList<>();
        // Patrón para las tres variantes
        Pattern pattern = Pattern.compile(
            "cambiar \\(([^,]+),\\s*([^,\\)]+)(?:\\s*y\\s*([^\\)]+))?\\)");

        Matcher matcher = pattern.matcher(linea);

        if (matcher.find()) {
            String solarX = matcher.group(1).trim();
            String segundoCampo = matcher.group(2).trim();
            String tercerCampo = matcher.group(3) != null ? matcher.group(3).trim() : null;

            campos.add(solarX);
            campos.add(segundoCampo);
            if (tercerCampo != null) {
                campos.add(tercerCampo);
            }
        } else {
            System.out.println("Formato incorrecto");
        }
        System.out.println("numero de campos " + campos.size());
        return campos;
    }

    //OBTENER JUGADOR A PARTIR DE NOMBRE
    private Jugador nombreJugador(String nombre, ArrayList<Jugador> jugadores){
        for (Jugador player : jugadores) {
            if (player!=null && player.getNombre().equals(nombre)) {
                return player;
            }
        }
        return null;
    }
}