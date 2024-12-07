package Excepciones.MalUsoComando;
import monopoly.Valor;

public class CompraSinPoder extends MalUsoComando {
    public CompraSinPoder(String mensaje){
        super(Valor.RED+" Compra"+Valor.RESET+", porque:\n"+ mensaje);
    }
    
}
