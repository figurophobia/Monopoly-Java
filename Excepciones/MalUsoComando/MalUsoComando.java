package Excepciones.MalUsoComando;

import Excepciones.ExcepcionBase;

public class MalUsoComando extends ExcepcionBase {
    public MalUsoComando(String mensaje){
        super("Actualmente no puedes usar este comando porque: "+ mensaje);
    }
    
}
