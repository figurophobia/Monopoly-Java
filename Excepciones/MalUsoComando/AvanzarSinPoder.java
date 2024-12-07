package Excepciones.MalUsoComando;
import monopoly.Valor;

public class AvanzarSinPoder extends MalUsoComando {
    public AvanzarSinPoder(String mensaje){
        super(Valor.RED+" Avanzar"+Valor.RESET+", porque:\n"+ mensaje);
    }
    
}
