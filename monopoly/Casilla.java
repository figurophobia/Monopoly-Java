package monopoly;

import java.util.ArrayList;
import partida.*;

public class Casilla {
    protected String nombre;
    protected int posicion;
    protected ArrayList<Avatar> avatares = new ArrayList<>();
    private boolean cuatrovueltas;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }

    public ArrayList<Avatar> getAvatares() { return avatares; }
    public void setAvatares(ArrayList<Avatar> avatares) { this.avatares = avatares; }

    public boolean isCuatrovueltas() { return cuatrovueltas; }
    public void setCuatrovueltas(boolean cuatrovueltas) { this.cuatrovueltas = cuatrovueltas; }

    public Casilla (String nombre, int posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.cuatrovueltas = false;
    }

    Consola consola = new ConsolaNormal();

    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    public void visitarCasilla(Jugador visitante){
        visitante.getNumeroVisitas().put(this, visitante.getNumeroVisitas().getOrDefault(this, 0) + 1);
    }

    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada){
        return true;
    }

    public void edificar(String tipo){
        consola.imprimirAdvertencia("No puedes edificar en esta casilla");
    }

    public String infoCasilla(){
        return ("Aún en desarrollo");
    }


}

