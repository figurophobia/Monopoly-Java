package Excepciones.Ejecucion;
import monopoly.Valor;
public class TratoNoEncontrado extends EjecucionBase {
    public TratoNoEncontrado(String tratoid){
        super("Trato no encontrado:"+Valor.RED+tratoid+Valor.RESET);
    }
}
