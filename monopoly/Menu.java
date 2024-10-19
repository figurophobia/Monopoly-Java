package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
import partida.*;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>(); //Jugadores de la partida.
    private ArrayList<Avatar> avatares = new ArrayList<Avatar>(); //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    private boolean partida_ON; //Booleano para comprobar si la partida sigue en curso.
    private boolean partida_OFF; //Booleano para comprobar si la partida ha finalizado.
    

    public Menu() {
        iniciarPartida();
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
        this.turno = 1;
        this.partida_ON = true;
        Scanner sc = new Scanner(System.in);
        this.banca = new Jugador();
        this.avatares.add(null); //Para que el índice coincida con el número de avatar.
        this.jugadores.add(banca); //Para que el índice coincida con el número de jugador.
        this.tablero = new Tablero(banca);
        System.out.println("\n\nCreamos los 2 jugadores mínimos para jugar...");
        anadirjugador();
        anadirjugador();
        System.out.println("Si desea añadir más jugadores, introduzca 'crear jugador'...");
        while (!partida_OFF) {
            // checkear la casilla actual
            System.out.println("\n('help' para ver los comandos disponibles | 'lanzar dados' para tirar los dados | 'acabar turno' para terminar el turno | 'end' para finalizar la partida)");
            System.out.print("Introduce un comando: ");
            String comando = sc.nextLine();
            analizarComando(comando);
        }
        sc.close();
        endGame();

    }
    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
        String[] partes = comando.split(" ");
        switch (partes[0]) {
            case "help":
                System.out.println("Comandos disponibles:\n" +
                "[+] crear jugador\n" +
                "[+] jugador\n" +
                "[+] listar jugadores\n" +
                "[+] listar avatares\n" +
                "[+] lanzar dados\n" +
                "[+] acabar turno\n" +
                "[+] ir carcel\n" +
                "[+] describir (nombre_casilla)\n" +
                "[+] dados (valor1) (valor2)\n" +
                "[+] describir jugador/avatar (nombre/id)\n" +
                "[+] comprar (nombre propiedad)\n" +
                "[+] listar enventa\n" +
                "[+] ver tablero");                
                break;
            case "crear":
                if (partes.length == 2 && partes[1].equals("jugador")) {
                    anadirjugador();
                } else {
                    System.out.println("Comando no reconocido");
                }
                break;
            case "jugador":
                Jugador jugadorActual = jugadores.get(turno);
                System.out.println("\n" +
                                "{\n" +
                                "nombre: " + jugadorActual.getNombre() + ",\n" +
                                "avatar: " + jugadorActual.getAvatar().getId() + "\n" +
                                "}");          
                break;  
            case "edificar":
                if (partes.length == 2) {
                    if(partes[1].equals("casa")){
                        //edificar casa
                    }else if (partes[1].equals("hotel")){
                        //edificar hotel
                    }else if (partes[1].equals("piscina")){
                        //edificar piscina
                    }else if (partes[1].equals("pista")){
                        //edificar pista
                }}
                else{
                    System.out.println("Comando no reconocido");
                }
                break;
            case "listar":
                if (partes.length==2) {
                    if (partes[1].equals("jugadores")) {
                        listarJugadores();
                    } else if (partes[1].equals("avatares")) {
                        listarAvatares();
                    } else if (partes[1].equals("enventa")) {
                        listarVenta();
                    } else {
                        System.out.println("Comando no reconocido");
                    }
                } else {
                    System.out.println("Comando no reconocido");
                    
                }
                break;
            case "lanzar":
                if (partes.length == 2) {
                    lanzarDados();
                } else {
                    System.out.println("Comando no reconocido");
                }
                break;
            case "comprar":
                if (partes.length == 2){
                    comprar(partes[1]);
                } else {
                    System.out.println("Comando no reconocido");
                }
                break;
            case "dados":
                if (partes.length==3){
                    lanzarDados(partes[1],partes[2]);
                }
                break;
            case "ir":
                if (partes.length == 2) {
                    System.out.println("Jugador enviado a la cárcel.");
                    jugadores.get(turno).encarcelar(tablero.getPosiciones());
                } else {
                    System.out.println("Comando no reconocido");
                    
                }
                break;
            case "salir":
                if (partes.length == 2 && partes[1].equals("carcel")) {
                    salirCarcel();
                } else {
                    System.out.println("Comando no reconocido");
                }
                break;
            case "acabar":
                acabarTurno();
                break;
            case "describir":
                if (partes.length == 3 && partes[1].equals("jugador")) {
                    descJugador(partes[2]);
                }else if (partes.length == 3 && partes[1].equals("avatar")) {
                    descAvatar(partes[2]);
                } else if (partes.length == 2 ) {
                    descCasilla(partes[1]);
                } else {
                    System.out.println("Comando no reconocido");
                }
                break;
            case "ver":
                if (partes.length == 2 && partes[1].equals("tablero")) {
                    verTablero();
                } else {
                    System.out.println("Comando no reconocido");
                }
                break;
            /////Comandos debug
            case "mover":
                if (partes.length == 2) {
                    lanzarDados(Integer.parseInt(partes[1]));
                } else {
                    System.out.println("Comando no reconocido");
                }
                break;
            case "fortuna":
                if (partes.length == 2) {
                    fortunaManual(Float.parseFloat(partes[1]));
                } else {
                    System.out.println("Comando no reconocido");
                }
                break;
            /////Fin comandos debug
            case "end":
                endGame();
                break;
            default:
                System.out.println("Comando no reconocido");
        }
    }

    /*Método que realiza las acciones asociadas al comando 'crear jugador'.
    * Parámetro: cadena de caracteres con el nombre del jugador, y cadena con el tipo de avatar.
    */
    private void anadirjugador(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Indica el nombre del jugador ("+jugadores.size()+"): ");
        String nombre = sc.nextLine();
        if (nombreRepetido(nombre)){
            while (nombreRepetido(nombre)) {
                System.out.print("Nombre repetido, introduce otro nombre: ");
                nombre = sc.nextLine();
            }
        }
        System.out.print("Indica el tipo de avatar (Sombrero, Esfinge, Pelota, Coche): ");
        String tipo = sc.nextLine();
        if (Avatar.TipoValido(tipo)) {
            crearJugador(nombre, tipo);
        } else {
            while (!Avatar.TipoValido(tipo)) {
                System.out.println("Tipo de avatar no válido, introduce uno de los siguientes: Sombrero, Esfinge, Pelota, Coche.");
                tipo = sc.nextLine();
            }
            crearJugador(nombre, tipo);
        }
    }
    private boolean nombreRepetido(String nombre) {
        for (Jugador player : jugadores) {
            if (player!=null && player.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }


    private void crearJugador(String nombre, String tipo) {
        if (jugadores.size() < 7) {
            Jugador jugador = new Jugador(nombre, tipo, tablero.getCasilla(0), avatares);
            Avatar avatar = jugador.getAvatar();
            jugadores.add(jugador);
            avatares.add(avatar);
            System.out.println("Jugador " + nombre + " creado con éxito.");
            tablero.getCasilla(0).anhadirAvatar(avatar);
        } else {
            System.out.println("No se pueden crear más jugadores");
        }
        
    }
    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: nombre del jugador introducido
     */
    private void descJugador(String nombre) {
        for (Jugador player : jugadores) {
            if (player!=null && player.getNombre().equals(nombre)) {
                System.out.println(player);
                
            }
        }
    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {
        for (Avatar av : avatares) {
            if (av!=null && av.getId().equals(ID)) {
                System.out.println(av);
                
            }
        }
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    private void descCasilla(String nombre) {
        Casilla casilla = this.tablero.casillaByName(nombre);
        if (casilla != null) {
            System.err.println(casilla.getNombre());
            System.out.println(casilla.infoCasilla());
        } else {
            System.out.println("Casilla no encontrada con ese nombre.");
        }
    }
    
    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void tiradados(int dado1, int dado2){
        System.out.print("Lanzando dados");sleep(600);System.out.print(".");sleep(600);System.out.print(".");sleep(600);System.out.println(".");sleep(400);
        tirado = true;
        lanzamientos++;
        System.out.println("Han salido "+dado1+" y "+dado2+"!");
    }
    private void lanzarDados(String dado1, String dado2) {
        int dadoint1= Integer.parseInt(dado1);
        int dadoint2= Integer.parseInt(dado2);
        if ((dadoint1>6)||(dadoint2>6)) System.out.println("Valor de tirada no válida");
        if (tirado) {
            System.out.println("Ya has lanzado los dados en este turno.");
            return;
        }
    
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = avatares.get(turno);
        Casilla posicionActual = avatarActual.getLugar();
    
        if (jugadorActual.isEnCarcel()) {
            manejarCarcel(jugadorActual, avatarActual, posicionActual,dadoint1,dadoint2);
        } else {
            manejarTiradaNormal(jugadorActual, avatarActual, posicionActual,dadoint1,dadoint2);
        }
    }
    private void lanzarDados() {
        dado1 = new Dado();
        dado2 = new Dado();
        
        if (tirado) {
            System.out.println("Ya has lanzado los dados en este turno.");
            return;
        }
    
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = avatares.get(turno);
        Casilla posicionActual = avatarActual.getLugar();
    
        if (jugadorActual.isEnCarcel()) {
            manejarCarcel(jugadorActual, avatarActual, posicionActual);
        } else {
            manejarTiradaNormal(jugadorActual, avatarActual, posicionActual);
        }
    }
    private void manejarCarcel(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual, int dado1, int dado2) {
        if (jugadorActual.getTiradasCarcel() >= 3) {
            System.out.println("Has pasado 3 turnos en la cárcel, sales! Tira los dados!");
            salirCarcel();
        } else {
            System.out.println("Estás en la cárcel, debes sacar dobles para salir.");
            int resultado1 = dado1;
            int resultado2 = dado2;
            tiradados(resultado1, resultado2);
    
            if (resultado1 == resultado2) {
                System.out.println("¡Dobles! Sales de la cárcel.");
                jugadorActual.sacarCarcel();
                sleep(1000);
                moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2);
            } else {
                System.out.println("No has sacado dobles, sigues en la cárcel.");
                jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                acabarTurno();
            }
        }
    }

    private void manejarCarcel(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual) {
        if (jugadorActual.getTiradasCarcel() >= 3) {
            System.out.println("Has pasado 3 turnos en la cárcel, sales! Tira los dados!");
            salirCarcel();
        } else {
            System.out.println("Estás en la cárcel, debes sacar dobles para salir.");
            int resultado1 = dado1.hacerTirada();
            int resultado2 = dado2.hacerTirada();
            tiradados(resultado1, resultado2);
    
            if (resultado1 == resultado2) {
                System.out.println("¡Dobles! Sales de la cárcel.");
                jugadorActual.sacarCarcel();
                sleep(1000);
                moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2);
            } else {
                System.out.println("No has sacado dobles, sigues en la cárcel.");
                jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                acabarTurno();
            }
        }
    }
    private void manejarTiradaNormal(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual, int dado1, int dado2) {
        int resultado1 = dado1;
        int resultado2 = dado2;
        tiradados(resultado1, resultado2);
    
        if (resultado1 == resultado2) {
            if (lanzamientos == 3) {
                System.out.println("¡Dobles! Has sacado tres veces dobles, vas a la cárcel.");
                jugadorActual.encarcelar(tablero.getPosiciones());
                verTablero();
                acabarTurno();
                return;
            } else {
                System.out.println("¡Dobles! Puedes tirar otra vez.");
                tirado = false;
            }
        }
    
        sleep(1000);
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2);
    }

    private void manejarTiradaNormal(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual) {
        int resultado1 = dado1.hacerTirada();
        int resultado2 = dado2.hacerTirada();
        tiradados(resultado1, resultado2);
    
        if (resultado1 == resultado2) {
            if (lanzamientos == 3) {
                System.out.println("¡Dobles! Has sacado tres veces dobles, vas a la cárcel.");
                jugadorActual.encarcelar(tablero.getPosiciones());
                verTablero();
                acabarTurno();
                return;
            } else {
                System.out.println("¡Dobles! Puedes tirar otra vez.");
                tirado = false;
            }
        }
    
        sleep(1000);
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2);
    }
    
    private void moverYVerTablero(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual, int total) {
        Casilla nuevaCasilla = tablero.getCasilla((total - 1 + posicionActual.getPosicion()) % 40);
        System.out.println("El avatar " + avatarActual.getId() + " avanzará " + total + " posiciones. Desde " + 
            posicionActual.getNombre() + " hasta " + nuevaCasilla.getNombre());
    
        moverJugador(total);
    
        // Verificar si el jugador debe ser encarcelado
        if (nuevaCasilla == tablero.getPosiciones().get(3).get(0)) {
            jugadorActual.encarcelar(tablero.getPosiciones());
        }
    
        // Verificar si el jugador puede pagar sus deudas
        if (!nuevaCasilla.evaluarCasilla(jugadorActual, banca, total)) {
            System.out.println("El jugador " + jugadorActual.getNombre() + " no tiene dinero para pagar, acaba el juego en esta primera version!");
            partida_OFF = true;
        }

        if (avatarActual.DarCuartaVuelta()) {
            boolean todosHanDado4Vueltas= true;
            for (int i=1; i<jugadores.size(); i++) { //Empezamos el bucle en 1 para no comparar con la banca
                if (jugadores.get(i).getVueltas()  < jugadorActual.getVueltas()){
                    todosHanDado4Vueltas = false; //Si tienen menos vueltas que el jugador que acaba de dar la cuarta vuelta, no todos han dado 4 vueltas
                    break;
                }
            }
            if (todosHanDado4Vueltas) {
                tablero.subirPrecio4Vueltas();
            }
            
        }
    
        verTablero();
    }
    
    //Método de prueba que mueve un jugador n posiciones.
    private void moverJugador(int posiciones) {
        this.avatares.get(turno).moverAvatar(this.tablero.getPosiciones(), posiciones);
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        Jugador jugador = jugadores.get(turno);
        Casilla c = jugador.getAvatar().getLugar();
        if (c.getNombre().equals(nombre)) {
            if (c.esComprable(jugador, banca)) {
                c.comprarCasilla(jugador, banca);
            }
            else {
                System.out.println("No puedes comprar esta casilla.");
            }
        } else {
            System.out.println("No estás en esa casilla.");
            
        }
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {
        if (lanzamientos==0) { //al inicio del turno en el que esta en la carcel, puede pagar
            jugadores.get(turno).pagarSalidaCarcel();
        }else{ //si no ha salido en el turno anterior, sale de la carcel
            System.out.println("Solo puedes pagar la multa al inicio de un turno en el que no hayas tirado los dados.");
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    //Recorremos todas las casillas del tablero y si son de tipo Solar, Transporte o Servicio y no tienen dueño, las imprimimos.
    private void listarVenta() {
        for (int i = 0; i < 40; i++) {
            Casilla c=this.tablero.getCasilla(i);
            if (c.getDuenho()==banca && (c.getTipo().equals("Solar") || c.getTipo().equals("Transporte") || c.getTipo().equals("Servicio"))) {
                System.out.println(c.casEnVenta());
                
            }
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        for (Jugador jugador: jugadores){
            if (jugador.getAvatar()!=null) {
                System.out.println(jugador);
            }
    }}

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        for (Avatar avatar: avatares){
            if (avatar!=null) {
                System.out.println(avatar);
            }
        }
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        if (tirado) {
            turno++;
            if (turno>(jugadores.size()-1)) {
                turno = 1;
            }
            lanzamientos = 0;
            tirado = false;
            System.out.println("Turno de "+ jugadores.get(turno).getNombre());}
        else System.out.println("Debes lanzar los dados antes de acabar el turno.");
    }
    
    //Método que imprime el tablero.
    private void verTablero() {
        System.out.println(tablero);
    
    }

    //Método que finaliza la partida.
    private void endGame() {
        partida_OFF = true;
        System.exit(0);
    }
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El hilo fue interrumpido.");
        }
    }

    ////////////////////////////////////////////
    //Comandos debug

    public void lanzarDados(int tiradaTotal){
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = avatares.get(turno);
        Casilla posicionActual = avatarActual.getLugar();
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, tiradaTotal);
    }

    public void fortunaManual(float cantidad){
        jugadores.get(turno).setFortuna(cantidad);
        System.out.println("Fortuna de "+jugadores.get(turno).getNombre()+" actualizada a "+cantidad);
    }
    
    public boolean tieneCasilla(Jugador jugador){
        if (jugador.getAvatar().getLugar().getDuenho().equals(jugador)){
            return true;
        }else{
            return false;
        }
    }

    ////////////////////////////////////////////
}
