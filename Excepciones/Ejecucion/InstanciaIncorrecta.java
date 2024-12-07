package Excepciones.Ejecucion;
import monopoly.Valor;

public class InstanciaIncorrecta  extends EjecucionBase {
    public InstanciaIncorrecta(String instanciaCorrecta){ //Marca la instancia que deberia ser
        super("Instancia incorrecta, deberia ser:"+Valor.RED+instanciaCorrecta+Valor.RESET);
    }
    
}
