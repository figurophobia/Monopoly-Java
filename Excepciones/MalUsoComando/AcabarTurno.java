package Excepciones.MalUsoComando;
import monopoly.Valor;

public class AcabarTurno  extends MalUsoComando {
    public AcabarTurno(String mensaje){ //El mensaje es el motivo de no poder acabar turno
        super(Valor.RED+" Acabar Turno"+Valor.RESET+", porque:\n"+ mensaje);
    }
    
}
