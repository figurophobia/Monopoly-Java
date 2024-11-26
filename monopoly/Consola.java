package monopoly;
/**
 * Interfaz para manejar la entrada y salida de mensajes en consola.
 */
public interface Consola {
    /**
     * Imprime un string en la consola.
     * @param cadena La cadena que se va a imprimir.
     */
    void imprimir(String cadena);

    /**
     * Imprime un mensaje en la consola. Para mensajes de una línea.
     * @param mensaje El mensaje que se desea mostrar.
     */
    void imprimirMensaje(String mensaje);
    
    /**
     * Imprime un mensaje en la consola.
     * @param advertencia La advertencia que se desea mostrar.
     */
    void imprimirAdvertencia(String advertencia);

    /**
     * Solicita al usuario un dato mostrándole una descripción.
     * @param descripcion El mensaje que se muestra antes de leer el dato.
     * @return El dato introducido por el usuario como un String.
     */
    String leer(String descripcion);
}
