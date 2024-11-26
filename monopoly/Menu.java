package monopoly;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import monopoly.Edificacion;
import partida.*;

public class Menu {

//////---ATRIBUTOS CLASE MENU---
    private Juego juego; // Partida actual

    //instanciar consola en menu
    public Consola consola = new ConsolaNormal();


//////---MENU---
    public Menu() {
        juego = new Juego();
        juego.iniciarPartida();
        bucle();
    }


    ///---Bucle principal de la partida---
    private void bucle() {
        try (Scanner sc = new Scanner(System.in)) {
            while (!juego.partidaApagada()) {
                ArrayList<Jugador> jugadores = juego.getJugadores();            
                if (jugadores.size() < 3) {
                    System.out.println("Ganador: " + jugadores.get(1).getNombre() + "!!!!");
                    juego.endGame();
                } else if (jugadores.get(juego.getTurno()).getFortuna() < 0) {
                    System.out.println("Tienes " + jugadores.get(juego.getTurno()).getFortuna() + " € Debes declarar bancarrota, hipotecar propiedades o vender edificaciones.");
                }
                ayudaComandos();
                String comando = sc.nextLine();
                analizarComando(comando);
            }
        }
    }


    private void ayudaComandos(){
        System.out.println("\n('help' para ver los comandos disponibles | 'lanzar dados' para tirar los dados | 'acabar turno' para terminar el turno | 'end' para finalizar la partida)");
        System.out.print("Introduce un comando: ");
    }
//////---METODO ANALIZA CADA COMANDO INTRODUCIDO---
    private void analizarComando(String comando) {
        String[] partes = comando.split(" ");
        switch (partes[0]) {
            // Ayuda
            case "help" -> System.out.println("""
                                              Comandos disponibles:
                                              [+] crear jugador
                                              [+] lanzar dados
                                              [+] acabar turno
                                              [+] cambiar modo
                                              [+] comprar [nombre propiedad]
                                              [+] edificar [tipo_edificio]
                                              [+] vender [casilla] [tipo_edificio] [numero_ventas]
                                              [+] jugador
                                              [+] describir [nombre_casilla]
                                              [+] describir jugador/avatar [nombre/id]
                                              [+] listar jugadores
                                              [+] listar avatares
                                              [+] listar enventa
                                              [+] listar edificios
                                              [+] listar edificios [grupo]
                                              [+] estadisticas [nombre jugador]
                                              [+] estadisticas
                                              [+] ver tablero
                                              [!] DEBUG commands:
                                              [+] ir carcel
                                              [+] salir carcel
                                              [+] dados [valor1] [valor2]""");

            // Crear jugador
            case "crear" -> {
                // Si 'crear jugador'
                if (partes.length == 2 && partes[1].equals("jugador"))
                    juego.anadirjugador();
                else
                    System.out.println(Valor.RED+"Comando no reconocido,"+Valor.RESET+" prueba con 'crear jugador'");
            
            }
            case "jugador" -> {
                juego.jugadorInfo();
            }
            case "edificar" -> {
                if (partes.length == 2) {
                    juego.edificar(partes[1]);
                }else System.out.println("Comando no reconocido");
                }
            
            case "listar" -> {
                if (partes.length==2) {
                    switch (partes[1]) {
                        case "jugadores" -> juego.listarJugadores();
                        case "avatares" -> juego.listarAvatares();
                        case "enventa" -> juego.listarVenta();
                        case "edificios" -> juego.listarEdificios();
                        default -> System.out.println("Comando no reconocido");
                    }
                }else if(partes.length==3 && "edificios".equals(partes[1])){
                    juego.listarGrupo(partes[2]);
                } 
                else {
                    System.out.println("Comando no reconocido");
                    
                }
            }
            case "lanzar" -> {
                if (partes.length == 2) {
                    juego.lanzarDados();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "comprar" -> {
                if (partes.length == 2){
                    juego.comprar(partes[1]);
                } else {
                    System.out.println("Comando no reconocido");
                }
            }

            /*case "ir" -> {
                if (partes.length == 2) {
                    System.out.println("Jugador enviado a la cárcel.");
                    jugadores.get(turno).encarcelar(tablero.getPosiciones());
                } else {
                    System.out.println("Comando no reconocido");
                    
                }
            }
            */
            case "salir" -> {
                if (partes.length == 2 && partes[1].equals("carcel")) {
                    juego.salirCarcel();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "acabar" -> {
                if (partes.length == 2 && partes[1].equals("turno")) {
                    juego.acabar();
                }else if(partes.length == 3 && partes[1].equals("turno") && partes[2].equals("force")){
                    juego.acabarTurnoForce();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "describir" -> {
                if (partes.length == 3 && partes[1].equals("jugador")) {
                    juego.descJugador(partes[2]);
                }else if (partes.length == 3 && partes[1].equals("avatar")) {
                    juego.descAvatar(partes[2]);
                } else if (partes.length == 2 ) {
                    juego.descCasilla(partes[1]);
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "vender" ->{
                if(partes.length==4)
                    juego.vender_edificio(partes[2],partes[1],partes[3]);
            }
            case "ver" -> {
                if (partes.length == 2 && partes[1].equals("tablero")) {
                    juego.verTablero();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "end" -> juego.endGame();
            case "dados" -> {
                if (partes.length==3){
                    juego.lanzarDados(partes[1],partes[2]);
                }
            }
            case "mover" -> {
                if (partes.length == 2) {
                    juego.lanzarDados(Integer.parseInt(partes[1]));
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "fortuna" -> {
                if (partes.length == 2) {
                    juego.fortunaManual(Float.parseFloat(partes[1]));
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "cambiar" -> {
                if (partes.length == 2 && partes[1].equals("modo")) {
                    juego.cambiarModo();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "bancarrota" -> {
                if (partes.length == 1) {
                    juego.bancarrota();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "hipotecar" -> {
                if (partes.length == 2) {
                    juego.hipotecar(partes[1]);
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "deshipotecar" -> {
                if (partes.length == 2) {
                    juego.deshipotecar(partes[1]);
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "avanzar" -> {
                if (partes.length == 1) {
                    juego.avanzar();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "estadisticas" -> {
            switch (partes.length) {
                case 2 -> juego.estadisticas(partes[1]);
                case 1 -> juego.estadisticasjuego();
                default -> System.out.println("Comando no reconocido");
            }
            }
            default -> System.out.println("Comando no reconocido");
        }
        //---COMANDOS DEBUG---
            }
        }