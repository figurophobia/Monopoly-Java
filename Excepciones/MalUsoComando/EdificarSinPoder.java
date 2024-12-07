package Excepciones.MalUsoComando;
import monopoly.Valor;

public class EdificarSinPoder extends MalUsoComando{
    public EdificarSinPoder(String porque){
        super(Valor.RED+" Edificar"+Valor.RESET+", porque\n"+porque);
    }
    
}
