package monopoly;
import partida.*;

public class Pista extends Edificacion{

    public Pista(Jugador propietario, Casilla casilla, float precio) {
        super(propietario, casilla, precio);
    }
    
    @Override 
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("""
                {
                id: pista - """+id+"""

                propietario: """+propietario.getNombre()+"""

                casilla: """+casilla.getNombre()+"""

                grupo: """+grupo.getColorGrupo()+"###"+Valor.RESET+"""

                coste: """+precio+"""

                }
                """);
        
        return str.toString();
    }   
}