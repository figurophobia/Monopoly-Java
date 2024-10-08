package monopoly;

import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.

    //Constructor vacío.
    public Grupo() {
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        this.miembros = new ArrayList<Casilla>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        cas1.setGrupo(this);
        cas2.setGrupo(this);
        this.colorGrupo = colorGrupo;
        this.numCasillas =2;

    }

    
    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
        this.miembros = new ArrayList<Casilla>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.miembros.add(cas3);
        cas1.setGrupo(this);
        cas2.setGrupo(this);
        cas3.setGrupo(this);
        this.colorGrupo = colorGrupo;
        this.numCasillas =3;
    }
    
    public ArrayList<Casilla> getMiembros() {
        return miembros;
    }

    public void setMiembros(ArrayList<Casilla> miembros) {
        this.miembros = miembros;
    }

    public String getColorGrupo() {
        return colorGrupo;
    }

    public void setColorGrupo(String colorGrupo) {
        this.colorGrupo = colorGrupo;
    }

    public int getNumCasillas() {
        return numCasillas;
    }

    public void setNumCasillas(int numCasillas) {
        this.numCasillas = numCasillas;
    }

    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {
        this.miembros.add(miembro);
        this.numCasillas++;
    }

    /*Método que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        int i=0;
        if(numCasillas==2){
            for (Casilla casilla : jugador.propiedades()){
                if(casilla == miembro[0]) i++;
                if(casilla == miembro[1]) i++;
            }
            if(i==2) return true;
            else return false;
        }
        if(numCasillas==3){
            for (Casilla casilla : jugador.propiedades()){
                if(casilla == miembro[0]) i++;
                if(casilla == miembro[1]) i++;
                if(casilla == miembro[2]) i++;
            }
            if(i==3) return true;
            else return false;
        }
    }

}
