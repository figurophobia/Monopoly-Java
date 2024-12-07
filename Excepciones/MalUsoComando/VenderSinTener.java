package Excepciones.MalUsoComando;
import monopoly.Valor;

public class VenderSinTener extends MalUsoComando {
    public VenderSinTener(String mensaje){
        super(Valor.RED+" Vender"+Valor.RESET+", porque:\n"+ mensaje);
    }
    
}
