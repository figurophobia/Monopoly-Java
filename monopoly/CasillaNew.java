package monopoly;

import java.util.ArrayList;
import partida.*;

public class CasillaNew {
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


    public CasillaNew (String nombre, int posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.cuatrovueltas = false;
    }

    public void visitarCasilla(Jugador visitante){
        visitante.getNumeroVisitas().put(this, visitante.getNumeroVisitas().getOrDefault(this, 0) + 1);
    }

}
