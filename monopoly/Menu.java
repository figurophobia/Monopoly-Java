package monopoly;

import Excepciones.ExcepcionBase;
import Excepciones.MalFormato.*;
import java.util.ArrayList;
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
        while (!juego.partidaApagada()) {
            ArrayList<Jugador> jugadores = juego.getJugadores();            
            if (jugadores.size() < 3) {
                Juego.consola.imprimir("Ganador: " + jugadores.get(1).getNombre() + "!!!!");
                juego.endGame();
            } else if (jugadores.get(juego.getTurno()).getFortuna() < 0) {
                Juego.consola.imprimir("Tienes " + jugadores.get(juego.getTurno()).getFortuna() + " € Debes declarar bancarrota, hipotecar propiedades o vender edificaciones.");
            }
            ayudaComandos();
            
            String comando = Juego.consola.leer("");
            //Hay que meter el try catch aqui, una vez implementado
            try{analizarComando(comando);}
            catch(ExcepcionBase e){
                Juego.consola.imprimirAdvertencia(e.getMessage());
            }
        }
    }


    private void ayudaComandos(){
        Juego.consola.imprimir("\n('help' para ver los comandos disponibles | 'lanzar dados' para tirar los dados | 'acabar turno' para terminar el turno | 'end' para finalizar la partida)");
        Juego.consola.imprimir("Introduce un comando: ");
    }
//////---METODO ANALIZA CADA COMANDO INTRODUCIDO---
    private void analizarComando(String comando) throws ExcepcionBase{
        String[] partes = comando.split(" ");
        switch (partes[0]) {
            // Ayuda
            case "help" -> Juego.consola.imprimir("""
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
                                              [+] avanzar
                                              [+] fortuna [cantidad]
                                              [+] hipotecar [nombre propiedad]
                                              [+] deshipotecar [nombre propiedad]
                                              [+] bancarrota
                                              [+] trato [nombreJugador1] cambiar SolarX SolarY dinero (insertar en orden)
                                              [+] aceptar trato [idTrato]
                                              [+] eliminar trato [idTrato]
                                              [+] end
                                              [!] DEBUG commands:
                                              [+] ir carcel
                                              [+] salir carcel
                                              [+] dados [valor1] [valor2] """);

            // Crear jugador
            case "crear" -> {
                // Si 'crear jugador'
                if (partes.length == 2 && partes[1].equals("jugador"))
                    juego.anadirjugador();
                else
                    Juego.consola.imprimir(Valor.RED+"Comando no reconocido,"+Valor.RESET+" prueba con 'crear jugador'");
            
            }
            case "jugador" -> {
                juego.jugadorInfo();
            }
            case "edificar" -> {
                if (partes.length == 2) {
                    juego.edificar(partes[1]);
                }else {
                    throw new EdificarFormato();
                }
            }
            
            case "listar" -> {
                if (partes.length==2) {
                    switch (partes[1]) {
                        case "jugadores" -> juego.listarJugadores();
                        case "avatares" -> juego.listarAvatares();
                        case "enventa" -> juego.listarVenta();
                        case "edificios" -> juego.listarEdificios();
                        default -> throw new Listar();
                    }
                }else if(partes.length==3 && "edificios".equals(partes[1])){
                    juego.listarGrupo(partes[2]);
                } 
                else {
                    throw new Listar();
                    
                }
            }
            case "lanzar" -> {
                if (partes.length == 2) {
                    juego.lanzarDados();
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }
            case "comprar" -> {
                if (partes.length == 2){
                    juego.comprar(partes[1]);
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }

            /*case "ir" -> {
                if (partes.length == 2) {
                    Juego.consola.imprimir("Jugador enviado a la cárcel.");
                    jugadores.get(turno).encarcelar(tablero.getPosiciones());
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                    
                }
            }
            */
            case "salir" -> {
                if (partes.length == 2 && partes[1].equals("carcel")) {
                    juego.salirCarcel();
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }
            case "acabar" -> {
                if (partes.length == 2 && partes[1].equals("turno")) {
                    juego.acabar();
                }else if(partes.length == 3 && partes[1].equals("turno") && partes[2].equals("force")){
                    juego.acabarTurnoForce();
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
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
                    throw new Describir();
                }
            }
            case "vender" ->{
                if(partes.length==4)
                    juego.vender_edificio(partes[2],partes[1],partes[3]);
                else{
                    throw new VenderFormato();
                }
            }
            case "ver" -> {
                if (partes.length == 2 && partes[1].equals("tablero")) {
                    juego.verTablero();
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }
            case "end" -> juego.endGame();
            case "dados" -> {
                if (partes.length==3){
                    juego.lanzarDados(partes[1],partes[2]);
                } else{ throw new DadosTrucados();}
            }
            case "mover" -> {
                if (partes.length == 2) {
                    juego.lanzarDados(Integer.parseInt(partes[1]));
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }
            case "fortuna" -> {
                if (partes.length == 2) {
                    juego.fortunaManual(Float.parseFloat(partes[1]));
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }
            case "cambiar" -> {
                if (partes.length == 2 && partes[1].equals("modo")) {
                    juego.cambiarModo();
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }
            case "bancarrota" -> {
                if (partes.length == 1) {
                    juego.bancarrota();
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }
            case "hipotecar" -> {
                if (partes.length == 2) {
                    juego.hipotecar(partes[1]);
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }
            case "deshipotecar" -> {
                if (partes.length == 2) {
                    juego.deshipotecar(partes[1]);
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }
            case "avanzar" -> {
                if (partes.length == 1) {
                    juego.avanzar();
                } else {
                    Juego.consola.imprimir("Comando no reconocido");
                }
            }
            case "tratos" -> {
                if (partes.length == 1) juego.verTratos();
                else Juego.consola.imprimir("Comando no reconocido");;
            }
            case "trato" -> {
                if (partes.length == 5||partes.length == 7) {
                    juego.crearTrato(partes);
                } else throw new TratosFormato();
            }
            case "aceptar" -> {
                if (partes.length == 3) {
                    juego.aceptarTrato(partes[2]);
                } else throw new AceptarTratos();
            }
            case "eliminar" -> {
                if (partes.length == 3) {
                    juego.eliminarTrato(partes[2]);
                } else throw new EliminarTratos();
            }
            case "estadisticas" -> {
            switch (partes.length) {
                case 2 -> juego.estadisticas(partes[1]);
                case 1 -> juego.estadisticasjuego();
                default -> Juego.consola.imprimir("Comando no reconocido");
            }
            }
            default -> Juego.consola.imprimir("Comando no reconocido");
        }
        //---COMANDOS DEBUG---
            }
        }