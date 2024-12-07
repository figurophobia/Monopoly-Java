package Excepciones.Ejecucion;
import monopoly.Valor;
public class EdificioNotFound  extends EjecucionBase {
    public EdificioNotFound(String edificioname){
        super("Edificio no encontrado:\n"+Valor.RED+edificioname+Valor.RESET);
    }
    
}
