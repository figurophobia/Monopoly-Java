package Excepciones.MalUsoComando;
import monopoly.Valor;

public class TratoIncompatible extends MalUsoComando {
    public TratoIncompatible(String mensaje){
        super(Valor.RED+" Comando de trato"+Valor.RESET+", porque:\n"+ mensaje);
    }
    
}
