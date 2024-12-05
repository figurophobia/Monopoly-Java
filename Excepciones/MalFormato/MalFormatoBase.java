package Excepciones.MalFormato;

import Excepciones.ExcepcionBase;

public class MalFormatoBase extends ExcepcionBase {
    public MalFormatoBase(String mensaje){
        super("El formato del comando es: "+mensaje);
    }
    
}
