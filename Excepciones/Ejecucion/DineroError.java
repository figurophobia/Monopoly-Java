package Excepciones.Ejecucion;
import monopoly.Valor;

public class DineroError extends EjecucionBase {
    public DineroError(float fortunaActual){ //Informa de dinero insuficiente para la operacion, da la cantidad actual y la necesaria
        super("Dinero insuficiente.\n Actual:"+Valor.RED+fortunaActual+Valor.RESET);
    }
}
