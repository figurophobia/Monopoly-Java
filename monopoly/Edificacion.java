package monopoly;

import partida.*;

public class Edificacion {
    private static int idCounter = 0;
    private String tipo;
    private float precio;
    private int id;
    private Jugador propietario;
    private Casilla casilla;
    private float ganancias;
    private Grupo grupo;

    public Edificacion(String tipo,Jugador propietario, Casilla casilla, float precio){
        this.id = ++idCounter;
        this.tipo = tipo;
        this.propietario = propietario;
        this.casilla = casilla;
        this.ganancias = 0;
        this.grupo = casilla.getGrupo();
        this.precio = precio;
    

    }
    /**
     * @return String return the tipo
     */
    public String getTipo() {
        return tipo;
    }
    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return int return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return Jugador return the propietario
     */
    public Jugador getPropietario() {
        return propietario;
    }
    /**
     * @param propietario the propietario to set
     */
    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }
    /**
     * @return Casilla return the casilla
     */
    public Casilla getCasilla() {
        return casilla;
    }
    /**
     * @param casilla the casilla to set
     */
    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }
    /**
     * @return float return the ganancias
     */
    public float getGanancias() {
        return ganancias;
    }
    /**
     * @param ganancias the ganancias to set
     */
    public void setGanancias(float ganancias) {
        this.ganancias = ganancias;
    }

////////////////METODOS///////////////
    @Override 
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("""
                {
                id: """+tipo+" - "+id+"""

                propietario: """+propietario.getNombre()+"""

                casilla: """+casilla.getNombre()+"""

                grupo: """+grupo.getColorGrupo()+"###"+Valor.RESET+"""

                coste: """+precio+"""

                }
                """);
        
        return str.toString();
    }    

    /**
     * @return float return the precio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

}
