package Excepciones.MalUsoComando;

import Excepciones.ExcepcionBase;

public class MalUsoComando extends ExcepcionBase {
    public MalUsoComando(String Comando){
        super("Actualmente no puedes usar:"+ Comando);
    }
    
}
