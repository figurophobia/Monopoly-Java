package monopoly;
import partida.*;

public class Hotel extends Edificacion{

    public Hotel(Jugador propietario, Casilla casilla, float precio) {
        super(propietario, casilla, precio);
    }
    
    @Override 
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("""
                {
                id: hotel - """+id+"""

                propietario: """+propietario.getNombre()+"""

                casilla: """+casilla.getNombre()+"""

                grupo: """+grupo.getColorGrupo()+"###"+Valor.RESET+"""

                coste: """+precio+"""

                }
                """);
        
        return str.toString();
    }   
}