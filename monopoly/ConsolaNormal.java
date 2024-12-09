package monopoly;
import java.util.Scanner;

public class ConsolaNormal implements Consola {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private final Scanner scanner;

    // Constructor para inicializar el Scanner
    public ConsolaNormal() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void imprimir(String cadena) {
        System.out.println(cadena);
    }
    @Override
    public void imprimirlinea(String cadena) {
        System.out.print(cadena);
    }
    @Override
    public void imprimirMensaje(String mensaje) {
        System.out.println(ANSI_BLUE + "[+] " + ANSI_RESET + mensaje);
    }
    @Override
    public void imprimirAdvertencia(String advertencia) {
        System.out.println(ANSI_RED + "[!] " + ANSI_RESET + advertencia);
    }

    @Override
    public String leer(String descripcion) {
        System.out.print(descripcion);
        return scanner.nextLine(); 
    }
}
