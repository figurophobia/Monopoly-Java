package monopoly;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import monopoly.Edificacion;
import partida.*;

public class Menu {

//////---ATRIBUTOS CLASE MENU---
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
    private int tiros_coche; //Para ver cuantos tiros lleva el coche

//////---MENU---
    public Menu() {
        try{
            iniciarPartida();
        }catch(Exception exception){
            System.out.println("Error: "+exception);
        }
    }
//////---METODO INICIA LA PARTIDA---
    private void iniciarPartida() {
        this.turno = 1; this.partida_ON = true;
        try (Scanner sc = new Scanner(System.in)) {
            banca = new Jugador(); avatares.add(null);jugadores.add(banca); tablero = new Tablero(banca);

            System.out.println("\n\nCreamos los 2 jugadores mínimos para jugar...");
            anadirjugador();
            anadirjugador();
            System.out.println("Si desea añadir más jugadores, introduzca 'crear jugador'...");

            while (!partida_OFF) {
                ayudaComandos();
                String comando = sc.nextLine();
                analizarComando(comando);
            }
        }
        endGame();

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
                                              [+] jugador
                                              [+] listar jugadores
                                              [+] listar avatares
                                              [+] listar edificios
                                              [+] listar edificios (grupo)
                                              [+] lanzar dados
                                              [+] acabar turno
                                              [+] ir carcel
                                              [+] describir (nombre_casilla)
                                              [+] dados (valor1) (valor2)
                                              [+] describir jugador/avatar (nombre/id)
                                              [+] comprar (nombre propiedad)
                                              [+] edificar (tipo_edificio)
                                              [+] vender (casilla) (tipo_edificio) (numero_ventas)
                                              [+] listar enventa
                                              [+] cambiar modo
                                              [+] salir carcel
                                              [+] estadisticas (nombre jugador)
                                              [+] estadisticas
                                              [+] edificar (tipo de edificio)
                                              [+] ver tablero""");
            // Crear jugador
            case "crear" -> {
                // Si 'crear jugador'
                if (partes.length == 2 && partes[1].equals("jugador"))
                    anadirjugador();
                else
                    System.out.println(Valor.RED+"Comando no reconocido,"+Valor.RESET+" prueba con 'crear jugador'");
            
            }
            case "jugador" -> {
                Jugador jugadorActual = jugadores.get(turno);
                System.out.println("\n{\nnombre: " + jugadorActual.getNombre() + ",\n" +
                        "avatar: " + jugadorActual.getAvatar().getId() + "\n" +
                                "}");
            }
            case "edificar" -> {
                if (partes.length == 2) {
                    jugadores.get(turno).getAvatar().getLugar().edificar(partes[1]);
                    }else System.out.println("Comando no reconocido");
                }
            
            case "listar" -> {
                if (partes.length==2) {
                    switch (partes[1]) {
                        case "jugadores" -> listarJugadores();
                        case "avatares" -> listarAvatares();
                        case "enventa" -> listarVenta();
                        case "edificios" -> listarEdificios();
                        default -> System.out.println("Comando no reconocido");
                    }
                }else if(partes.length==3 && "edificios".equals(partes[1])){
                    listarGrupo(partes[2]);
                } 
                else {
                    System.out.println("Comando no reconocido");
                    
                }
            }
            case "lanzar" -> {
                if (partes.length == 2) {
                    lanzarDados();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "comprar" -> {
                if (partes.length == 2){
                    comprar(partes[1]);
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "ir" -> {
                if (partes.length == 2) {
                    System.out.println("Jugador enviado a la cárcel.");
                    jugadores.get(turno).encarcelar(tablero.getPosiciones());
                } else {
                    System.out.println("Comando no reconocido");
                    
                }
            }
            case "salir" -> {
                if (partes.length == 2 && partes[1].equals("carcel")) {
                    salirCarcel();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "acabar" -> acabarTurno();
            case "describir" -> {
                if (partes.length == 3 && partes[1].equals("jugador")) {
                    descJugador(partes[2]);
                }else if (partes.length == 3 && partes[1].equals("avatar")) {
                    descAvatar(partes[2]);
                } else if (partes.length == 2 ) {
                    descCasilla(partes[1]);
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "vender" ->{
                if(partes.length==4)
                    vender_edificio(partes[2],partes[1],partes[3]);
            }
            case "ver" -> {
                if (partes.length == 2 && partes[1].equals("tablero")) {
                    verTablero();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "end" -> endGame();
            case "dados" -> {
                if (partes.length==3){
                    lanzarDados(partes[1],partes[2]);
                }
            }
            case "mover" -> {
                if (partes.length == 2) {
                    lanzarDados(Integer.parseInt(partes[1]));
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "fortuna" -> {
                if (partes.length == 2) {
                    fortunaManual(Float.parseFloat(partes[1]));
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "cambiar" -> {
                if (partes.length == 2 && partes[1].equals("modo")) {
                    if (!tirado) {
                        avatares.get(turno).cambiarModo();
                    }
                    else System.out.println("Debes acabar el turno antes de cambiar el modo de movimiento.");
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            case "estadisticas" -> {
                if (partes.length == 2) {
                    estadisticas(partes[1]);
                } else  if(partes.length == 1){
                    estadisticasjuego();
                } else {
                    System.out.println("Comando no reconocido");
                }
            }
            default -> System.out.println("Comando no reconocido");
        }
        //---COMANDOS DEBUG---
            }
//////---METODO AÑADIR JUGADOR---
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

//////---METODO COMPRUEBA SI NOMBRE REPETIDO---
    private boolean nombreRepetido(String nombre) {
        for (Jugador player : jugadores) {
            if (player!=null && player.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }
//////---METODO CREA JUGADOR---
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
//////---METODO DESCRIBE JUGADOR---
    private void descJugador(String nombre) {
        for (Jugador player : jugadores) {
            if (player!=null && player.getNombre().equals(nombre)) {
                System.out.println(player);
                
            }
        }
    }
//////---METODO DESCRIBE AVATAR---
    private void descAvatar(String ID) {
        for (Avatar av : avatares) {
            if (av!=null && av.getId().equals(ID)) {
                System.out.println(av);
                
            }
        }
    }
//////---METODO DESCRIBE CASILLA---
    private void descCasilla(String nombre) {
        Casilla casilla = this.tablero.casillaByName(nombre);
        if (casilla != null) {
            System.err.println(casilla.getNombre());
            System.out.println(casilla.infoCasilla());
        } else {
            System.out.println("Casilla no encontrada con ese nombre.");
        }
    }
//////---METODO SIMULA LANZAMIENTO DADOS---
    private void tiradados(int dado1, int dado2){
        System.out.print("Lanzando dados");sleep(600);System.out.print(".");sleep(600);System.out.print(".");sleep(600);System.out.println(".");sleep(400);
        Avatar avatarActual = avatares.get(turno);
        avatarActual.getJugador().setVecesDados(avatarActual.getJugador().getVecesDados()+1);
        // Verificar si el avatar tiene movimiento avanzado y es de tipo "Coche"
        if (avatarActual.esMovAvanzado() && avatarActual.getTipo().equals("Coche")) {
            // Verificar si es necesario cambiar la tirada
            if (avatarActual.getUltimoTiroFueCoche() || avatarActual.getJugador().isEnCarcel()) {
                tirado = true;
                lanzamientos++;
            }
        } else {
            // Para cualquier otro caso, incrementar lanzamientos
            lanzamientos++;
            tirado=true;
        }
        System.out.println("Han salido "+dado1+" y "+dado2+"!");
    }


//////---METODO LANZA DADOS ALEATORIOS---
    private void lanzarDados() {
        dado1 = new Dado();
        dado2 = new Dado();
        Avatar avatarActual = avatares.get(turno);
        if (avatarActual.isCocheParado()) {
            System.out.println("El coche está parado, no puede lanzar los dados en "+avatarActual.getTurnosParado() +"turnos consecutivos.");            
            return;
        }
        if (tirado) {
            System.out.println("Ya has lanzado los dados en este turno.");
            return;
        }
        if (avatarActual.getTiros_extra() == 4){
            System.out.println("Se han acabado tus tiros extra");
            tirado=true;
            return;

        }

    
        Jugador jugadorActual = jugadores.get(turno);
        Casilla posicionActual = avatarActual.getLugar();
    
        if (jugadorActual.isEnCarcel()) {
            manejarCarcel(jugadorActual, avatarActual, posicionActual);
        } else {
            manejarTiradaNormal(jugadorActual, avatarActual, posicionActual);
        }
    }
//////---METODO LANZAMIENTOS DADOS EN CARCEL ALEATORIOS---
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
//////---METODO LANZAMIENTOS DADOS ALEATORIOS---
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
                System.out.println("Entra a dobles una vez");
                tirado = false;
            }
        }
    
        sleep(1000);
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2);
    }
//////---METODO MOVER AVATAR AL LANZAR DADOS---
    private void moverYVerTablero(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual, int total) {
        Casilla nuevaCasilla;
        int newposition = moverJugador(total);
        nuevaCasilla = tablero.getCasilla(newposition);

        System.out.println("El avatar " + Valor.BLUE + avatarActual.getId()+ Valor.RESET + " avanzó " +Valor.BLUE + total + Valor.RESET +" posiciones. Desde " + 
        Valor.RED+posicionActual.getNombre()+ Valor.RESET + " hasta " +Valor.GREEN+ nuevaCasilla.getNombre()+Valor.RESET);

        //Hace la condicion el que no esté en modo avanzado o quien lo esté pero no sea Pelota
        if(!avatares.get(turno).esMovAvanzado() || !avatares.get(turno).getTipo().equals("Pelota")){
            // Verificar si el jugador debe ser encarcelado
            if (nuevaCasilla == tablero.getPosiciones().get(3).get(0)) {
                jugadorActual.encarcelar(tablero.getPosiciones());
            }
            // Verificar si el jugador puede pagar sus deudas
            if (!nuevaCasilla.evaluarCasilla(jugadorActual, banca, total)) {
                System.out.println("El jugador " + jugadorActual.getNombre() + " no tiene dinero para pagar, entra en bancarrota, acaba el juego en esta primera version!");
                partida_OFF = true;
            }
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
    }
//////---METODO MUEVE AVATAR VALOR ESPECIFICO---
    private int moverJugador(int posiciones) {
        if (avatares.get(turno).esMovAvanzado()) {
            if (avatares.get(turno).getTipo().equals("Pelota")) {
                return avatares.get(turno).moverAvatarPelota(tablero.getPosiciones(), posiciones, banca);
            }
            else if (avatares.get(turno).getTipo().equals("Coche")) {
                if (lanzamientos<2 && posiciones>4){
                    System.out.println("Tienes tirada extra, puedes seguir lanzando los dados, llevas "+lanzamientos+" lanzamientos.");
                }
                return avatares.get(turno).moverAvatarCoche(tablero.getPosiciones(), posiciones);
                
            }
            
        }
        else {
            return this.avatares.get(turno).moverAvatar(this.tablero.getPosiciones(), posiciones);
        }
        return 0;
    }
    
//////---METODO COMPRA CASILLA---
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
//////---METODO SALIR CARCEL---
    private void salirCarcel() {
        if (lanzamientos==0) { //al inicio del turno en el que esta en la carcel, puede pagar
            jugadores.get(turno).pagarSalidaCarcel();
        }else{ //si no ha salido en el turno anterior, sale de la carcel
            System.out.println("Solo puedes pagar la multa al inicio de un turno en el que no hayas tirado los dados.");
        }
    }
//////---METODO LISTA PROPIEDADES EN VENTA---
    private void listarVenta() {
        for (int i = 0; i < 40; i++) {
            Casilla c=this.tablero.getCasilla(i);
            if (c.getDuenho()==banca && (c.getTipo().equals("Solar") || c.getTipo().equals("Transporte") || c.getTipo().equals("Servicio"))) {
                System.out.println(c.casEnVenta());
                
            }
        }
    }
//////---METODO LISTA JUGADORES---
    private void listarJugadores() {
        for (Jugador jugador: jugadores){
            if (jugador.getAvatar()!=null) {
                System.out.println(jugador);
            }
    }}
//////---METODO LISTA AVATARES---
    private void listarAvatares() {
        for (Avatar avatar: avatares){
            if (avatar!=null) {
                System.out.println(avatar);
            }
        }
    }
//////---METODO LISTAR EDIFICIOS---
    private void listarEdificios() {
        for (int i = 0; i < 40; i++) {
            Casilla c=tablero.getCasilla(i);
            c.mostrarEdificaciones();
        }
    }
    private void listarGrupo(String grupo) {
        try{
            tablero.getGrupos().get(grupo).mostrarEdificaciones();
        } catch (Exception e) {
            System.out.println("No se ha encontrado ese grupo");
            System.out.println("Los grupos posibles son (en orden): black, cyan, purple, yellow, red, brown, green, blue.");
            //System.out.println(e);
        }
    }
//////---METODO ACABAR TURNO---
    private void acabarTurno() {
        Avatar avatarActual = avatares.get(turno);
        if (avatarActual.isCocheParado()){
            avatarActual.reducirTurnosParado();
            tirado = true;
            avatarActual.setUltimoTiroFueCoche(false);
            if (avatarActual.getTurnosParado()==0) {
                avatarActual.setCocheParado(false);
            }
        }
        avatarActual.setTiros_extra(0);
        tiros_coche = 0;
        avatarActual.setUltimoTiroFueCoche(false);
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
//////---METODO VER TABLERO---
    private void verTablero() {
        System.out.println(tablero);
    
    }
//////---METODO FINALIZAR PARTIDA---
    private void endGame() {
        partida_OFF = true;
        System.exit(0);
    }
//////---METODO SLEEP---
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El hilo fue interrumpido.");
        }
    }
//////---METODO COMPRUEBA SI JUGADOR POSEE CASILLA
    public boolean tieneCasilla(Jugador jugador){
        return jugador.getAvatar().getLugar().getDuenho().equals(jugador);
    }
//////---METODO VENDER EDIFICIO
    public void vender_edificio(String casilla, String tipo,String num){
        try{
            int numero= Integer.parseInt(num);
            Casilla c=buscar_Casilla(casilla);
            if(c!=null){
                Jugador jugadorActual = jugadores.get(turno);
                if (c.getDuenho()==jugadorActual)
                    c.vender_edificio(tipo,numero);
                else System.out.println("No te pertenece esa casilla!");
            }else System.out.println("Casilla no encontrada...");
        }catch (NumberFormatException e){
            System.out.println("Error: "+e);
        }
    }
    private Casilla buscar_Casilla(String casilla){
        for(int i=0;i<40;i++){
            Casilla c=tablero.getCasilla(i);
            if (c.getNombre().equals(casilla))
                return c;
        }
        return null;
    }
////////////////////////////////////DEBUG COMMANDS////////////////////////////////////
//////---METODO LANZA DADOS VALORES ESPECIFICOS---
    private void lanzarDados(String dado1, String dado2) {
        int dadoint1= Integer.parseInt(dado1);
        int dadoint2= Integer.parseInt(dado2);
        Avatar avatarActual = avatares.get(turno);
        if (avatarActual.isCocheParado()) {
            System.out.println("El coche está parado, no puede lanzar los dados en "+avatarActual.getTurnosParado() +"turnos consecutivos.");            
            return;
        }
        if ((dadoint1>6)||(dadoint2>6)) System.out.println("Valor de tirada no válida");    
        if (tirado) {
            System.out.println("Ya has lanzado los dados en este turno.");
            return;
        }
        
        if (avatarActual.getTiros_extra() == 4){
            System.out.println("Se han acabado tus tiros extra");
            tirado=true;
            return;

        }

        Jugador jugadorActual = jugadores.get(turno);
        Casilla posicionActual = avatarActual.getLugar();
    
        if (jugadorActual.isEnCarcel()) {
            manejarCarcel(jugadorActual, avatarActual, posicionActual,dadoint1,dadoint2);
        } else {
            manejarTiradaNormal(jugadorActual, avatarActual, posicionActual,dadoint1,dadoint2);
        }
    }
//////---METODO LANZAMIENTOS DADOS EN CARCEL VALORES ESPECIFICOS---
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
//////---METODO LANZAMIENTOS DADOS VALORES ESPECIFICOS---
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
    public void estadisticas(String nombre) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                jugador.estadisticas();
            }
        }
    }

    /*
     * 
     * $> estadisticas
        {
        casillaMasRentable: Solar3,
        grupoMasRentable: Verde,
        casillaMasFrecuentada: Solar14,
        jugadorMasVueltas: Pedro,
        jugadorMasVecesDados: Cristina,
        jugadorEnCabeza: Maria
        }
     */


    public void estadisticasjuego() {
        Jugador jugadorMasVueltas = jugadores.get(1);
        Jugador jugadorMasVecesDados = jugadores.get(1);
        Jugador jugadorEnCabeza = jugadores.get(1);
        Casilla casillaMasRentable = tablero.getCasilla(1);
        Grupo grupoMasRentable = tablero.getGrupos().get("black");
        Casilla casillaMasFrecuentada = tablero.getCasilla(1);
        int maxFrecuencia = 0;
        //Array de 40 enteros que guarda el número de veces que se ha visitado cada casilla
        int[] frecuenciaCasillas = new int[40];
    
        // Empezamos en 1 para omitir la banca
        for (int i = 1; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            if (jugador.getVueltas() > jugadorMasVueltas.getVueltas()) {
                jugadorMasVueltas = jugador;
            }
            if (jugador.getVecesDados() > jugadorMasVecesDados.getVecesDados()) {
                jugadorMasVecesDados = jugador;
            }
            if (jugador.getFortuna() > jugadorEnCabeza.getFortuna()) { // PROVISIONAL, hay que añadir el valor de las propiedades y de los edificios
                jugadorEnCabeza = jugador;
            }
    
            for (Map.Entry<Casilla, Integer> entry : jugador.getNumeroVisitas().entrySet()) {
                Casilla c = entry.getKey();
                int frecuencia = entry.getValue();
                frecuenciaCasillas[c.getPosicion()-1] += frecuencia;
                }
        }
        

        for (int j = 0; j < 40; j++) {
            if (frecuenciaCasillas[j] > maxFrecuencia) {
                maxFrecuencia = frecuenciaCasillas[j];
                casillaMasFrecuentada = tablero.getCasilla(j);
            }
        }
    
        for (int i=0;i<40;i++) {
            Casilla c = tablero.getCasilla(i);
            if (c.getDineroGenerado() > casillaMasRentable.getDineroGenerado()) {
                casillaMasRentable = c;
            }
        }

        for (Grupo grupo : tablero.getGrupos().values()) {
            if (grupo.getDineroGenerado() > grupoMasRentable.getDineroGenerado()) {
                grupoMasRentable = grupo;
            }
        }
    
        System.out.println("{\n" +
                "casillaMasRentable: " + casillaMasRentable.getNombre() + ",\n" +
                "grupoMasRentable: " + grupoMasRentable.getColorGrupo() + "####" + Valor.RESET + ",\n" +
                "casillaMasFrecuentada: " + casillaMasFrecuentada.getNombre() + ",\n" +
                "jugadorMasVueltas: " + jugadorMasVueltas.getNombre() + ",\n" +
                "jugadorMasVecesDados: " + jugadorMasVecesDados.getNombre() + ",\n" +
                "jugadorEnCabeza: " + jugadorEnCabeza.getNombre() + "\n" +
                "}");
    }


//////---METODO QUE LANZA DADOS UN VALOR??
    public void lanzarDados(int tiradaTotal){
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = avatares.get(turno);
        Casilla posicionActual = avatarActual.getLugar();
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, tiradaTotal);
    }
//////---METODO CAMBIA FORTUNA
    public void fortunaManual(float cantidad){
        jugadores.get(turno).setFortuna(cantidad);
        System.out.println("Fortuna de "+jugadores.get(turno).getNombre()+" actualizada a "+cantidad);
    }
}