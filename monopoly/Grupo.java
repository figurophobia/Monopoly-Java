package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.
    private float dineroGenerado=0; //Dinero generado por el grupo.
    private HashMap<String,ArrayList<Edificacion>> edificaciones = new HashMap<>();
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
  
    public float getDineroGenerado() {
        return dineroGenerado;
    }

    public void setDineroGenerado(float dineroGenerado) {
        this.dineroGenerado = dineroGenerado;
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
        boolean esDuenho = true;
        for (Casilla miembro : this.miembros) {
            if (miembro.getDuenho()!=jugador) {
                esDuenho = false;
                break;
            }
        }
        return esDuenho; //PROVISIONAL
    }
    public float valor(){
        switch (this.colorGrupo) {
            case Valor.BLACK->{
                return Valor.GRUPO_BLACK;
            }
            case Valor.BLUE->{
                return Valor.GRUPO_BLUE;
            }
            case Valor.BROWN->{
                return Valor.GRUPO_BROWN;
            }
            case Valor.CYAN->{
                return Valor.GRUPO_CYAN;
            }
            case Valor.GREEN->{
                return Valor.GRUPO_GREEN;
            }case Valor.PURPLE->{
                return Valor.GRUPO_PURPLE;
            }
            case Valor.RED->{
                return Valor.GRUPO_RED;
            }
            case Valor.YELLOW->{
                return Valor.GRUPO_YELLOW;
            }
            default ->{
                return 0;
            }
        }
    }


    /**
     * @return HashMap<String,ArrayList<Edificacion>> return the edificaciones
     */
    public HashMap<String,ArrayList<Edificacion>> getEdificaciones() {
        return edificaciones;
    }

    /**
     * @param edificaciones the edificaciones to set
     */
    public void setEdificaciones(HashMap<String,ArrayList<Edificacion>> edificaciones) {
        this.edificaciones = edificaciones;
    }

    public void mostrarEdificaciones(){
        // Recorre todas las entradas del hashmap
        StringBuilder str = new StringBuilder();
        for (Casilla cas : miembros){
            cas.calcularImpuesto();
            str.append("{\npropiedad: ").append(cas.getNombre()).append("\n");
            int nhoteles=cas.getEdificaciones().getOrDefault("hotel", new ArrayList<>()).size();
            str.append("hoteles (").append(nhoteles).append("): ");
            for (Edificacion edificio : cas.getEdificaciones().getOrDefault("hotel", new ArrayList<>())){
                str.append(edificio.getTipo()).append(" - ").append(edificio.getId()).append(", ");
            }
            int ncasas=cas.getEdificaciones().getOrDefault("casa", new ArrayList<>()).size();
            str.append("\ncasas (").append(ncasas).append("): ");
            for (Edificacion edificio : cas.getEdificaciones().getOrDefault("casa", new ArrayList<>())){
                str.append(edificio.getTipo()).append(" - ").append(edificio.getId()).append(", ");
            }
            int npiscinas=cas.getEdificaciones().getOrDefault("piscina", new ArrayList<>()).size();
            str.append("\npiscinas (").append(npiscinas).append("): ");
            for (Edificacion edificio : cas.getEdificaciones().getOrDefault("piscina", new ArrayList<>())){
                str.append(edificio.getTipo()).append(" - ").append(edificio.getId()).append(", ");
            }
            int npistas=cas.getEdificaciones().getOrDefault("pista", new ArrayList<>()).size();
            str.append("\npistas (").append(npistas).append("): ");
            for (Edificacion edificio : cas.getEdificaciones().getOrDefault("pista", new ArrayList<>())){
                str.append(edificio.getTipo()).append(" - ").append(edificio.getId()).append(", ");
            }
            str.append("\nalquiler: ").append(cas.getImpuesto()).append("\n");
        }
    Juego.consola.imprimir(str.toString());
    }

    public boolean tieneHipotecaEnGrupo(Jugador jugador){
        for (Casilla cas : miembros){
            if (cas.isHipotecada() && cas.getDuenho()==jugador){
                return true;
            }
        }
        return false;
    }
}
