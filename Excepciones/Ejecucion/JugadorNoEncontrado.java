package Excepciones.Ejecucion;
import monopoly.Valor;

public class JugadorNoEncontrado extends EjecucionBase {
    public JugadorNoEncontrado(String jugadorname){
        super("Jugador no encontrado:"+Valor.RED+jugadorname+Valor.RESET);
        }
}
