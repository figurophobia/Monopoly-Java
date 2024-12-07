package Excepciones.MalUsoComando;
import monopoly.Valor;

public class LanzarDado extends MalUsoComando {
    public LanzarDado(String mensaje){
        super(Valor.RED+" Lanzar Dado"+Valor.RESET+", porque:\n"+ mensaje);
    }
    
}
