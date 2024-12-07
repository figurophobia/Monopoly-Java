package Excepciones.Ejecucion;
import monopoly.Valor;

public class CasillaNoEncontrada extends EjecucionBase {
    public CasillaNoEncontrada(String casillaname){
        super("Casilla no encontrada:"+Valor.RED+casillaname+Valor.RESET);
    }
    
}
