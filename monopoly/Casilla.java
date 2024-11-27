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

    //TODO: frecuenciaVisita
    public boolean estaAvatar(Avatar avatar){
        Casilla casillaAvatar = avatar.getLugar();
        return (casillaAvatar == this);
    }

    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada){
        return true;
    }

    public String printOneCasilla(){
        String name = new String();
        name +=getNombre() +" ";
        if (!this.avatares.isEmpty()) {
            name+="&";
        }
        for (Avatar i : this.avatares) {
            name+=i.getId(); //Juntamos como texto de la casilla, el nombre y los avatares
        }
        name = String.format("%-"+Valor.width+"s", name);  // Rellena con espacios si es más corto, o lo ajusta a 16
        if (this instanceof Solar solar){
            return(Valor.SUBRAYADO+solar.grupo.getColorGrupo()+name+Valor.RESET); //Si tiene grupo que pille su color
        }
        else{
            return(Valor.SUBRAYADO+Valor.WHITE+name+Valor.RESET); //Si no que se ponga el blanco
        }
    }

    public void edificar(String tipo){
        consola.imprimirAdvertencia("No puedes edificar en esta casilla");
    }

    public String infoCasilla(){
        return ("Aún en desarrollo");
    }

    //TODO: String toString


}
