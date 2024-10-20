package monopoly;

import partida.*;

public class Edificacion {
    private String tipo;
    private float impuesto;
    private int id;
    private Jugador propietario;
    private Casilla casilla;
    private float ganancias;

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
     * @return float return the impuesto
     */
    public float getImpuesto() {
        return impuesto;
    }
    /**
     * @param impuesto the impuesto to set
     */
    public void setImpuesto(float impuesto) {
        this.impuesto = impuesto;
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
    public void edificar(String tipo){
        
    }
}
