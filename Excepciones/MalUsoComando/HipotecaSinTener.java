package Excepciones.MalUsoComando;
import monopoly.Valor;

public class HipotecaSinTener  extends MalUsoComando {
    public HipotecaSinTener(String mensaje){
        super(Valor.RED+" Hipotecar"+Valor.RESET+", porque:\n"+ mensaje);
    }
    
}
