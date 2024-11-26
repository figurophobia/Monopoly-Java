import monopoly.Consola;
import monopoly.ConsolaNormal;

public class Test {
    public static Consola consola = new ConsolaNormal();

    public static void main(String[] args) {
        consola.imprimir("testing\n");
        consola.imprimirMensaje("Mensaje de prueba");
        consola.imprimirAdvertencia("Error detectado");
    }
}
