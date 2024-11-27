package monopoly;

import partida.*;

public class Edificacion {

    protected static int id_counter = 0;
    protected float precio;
    protected int id;
    protected float ganancias;
    protected Jugador propietario;
    protected Casilla casilla;
    protected Grupo grupo;

    public Edificacion(Jugador propietario, Casilla casilla, float precio)
    {
        this.id = ++id_counter;
        this.propietario = propietario;
        this.casilla = casilla;
        if (casilla instanceof Solar solar)
            this.grupo = solar.getGrupo();
        this.precio = precio;
        this.ganancias = 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Jugador getPropietario() { return propietario; }
    public void setPropietario(Jugador propietario) { this.propietario = propietario; }

    public Grupo getGrupo(Grupo grupo) { return grupo; }
    public void setGrupo(Grupo grupo) { this.grupo = grupo; }

    public Casilla getCasilla() { return casilla; }
    public void setCasilla(Casilla casilla) { this.casilla = casilla; }

    public float getGanancias() { return ganancias; }
    public void setGanancias(float ganancias) { this.ganancias = ganancias; }

    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

}

/*
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

}
    */
