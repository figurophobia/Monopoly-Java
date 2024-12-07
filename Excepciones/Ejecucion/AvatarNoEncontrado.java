package Excepciones.Ejecucion;
import monopoly.Valor;

public class AvatarNoEncontrado extends EjecucionBase {
    public AvatarNoEncontrado(String avaname){
        super("Avatar no encontrado:"+Valor.RED+avaname+Valor.RESET);
    }
    
}
