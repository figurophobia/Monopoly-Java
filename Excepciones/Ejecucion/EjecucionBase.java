package Excepciones.Ejecucion;

import Excepciones.ExcepcionBase;

public class EjecucionBase extends ExcepcionBase {
    public EjecucionBase(String mensaje){
        super("Fallo en la ejecución debido a:\n"+mensaje);
    }
    
}
