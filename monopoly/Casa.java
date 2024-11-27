package monopoly;
import partida.*;

public class Casa extends Edificacion{

    public Casa(Jugador propietario, Casilla casilla, float precio) {
        super(propietario, casilla, precio);
    }
    
    @Override 
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("""
                {
                id: casa - """+id+"""

                propietario: """+propietario.getNombre()+"""

                casilla: """+casilla.getNombre()+"""

                grupo: """+grupo.getColorGrupo()+"###"+Valor.RESET+"""

                coste: """+precio+"""

                }
                """);
        
        return str.toString();
    }   
}