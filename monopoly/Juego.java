package monopoly;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import monopoly.Edificacion;
import partida.*;

public class Juego {

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
    private Cartas cartas = new Cartas(); //Objeto de la clase Cartas

//////---CONSTRUCTOR JUEGO---

    public Juego() {
        this.turno = 1;
        this.partida_ON = true;
        this.partida_OFF = false;
    }

    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }

    public boolean partidaApagada(){
        return partida_OFF;
    }

    public int getTurno(){
        return turno;
    }


    public void iniciarPartida(){
        banca = new Jugador();
        avatares.add(null);
        jugadores.add(banca);
        tablero = new Tablero(banca);
        print_intro();
        System.out.println("\n\nCreamos los 2 jugadores mínimos para jugar...");
        anadirjugador();
        anadirjugador();
        System.out.println("Si desea añadir más jugadores, introduzca 'crear jugador'...");

    }
//////---METODO AÑADIR JUGADOR---
    public void anadirjugador(){
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
    public boolean nombreRepetido(String nombre) {
        for (Jugador player : jugadores) {
            if (player!=null && player.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }
//////---METODO CREA JUGADOR---
    public void crearJugador(String nombre, String tipo) {
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

//////---Metodo Jugador:
///             case "jugador" -> {
// Jugador jugadorActual = jugadores.get(turno);
//System.out.println("\n{\nnombre: " + jugadorActual.getNombre() + ",\n" +
//        "avatar: " + jugadorActual.getAvatar().getId() + "\n" +
//                "}");
//}

    public void jugadorInfo(){
        Jugador jugadorActual = jugadores.get(turno);
        System.out.println("\n{\nnombre: " + jugadorActual.getNombre() + ",\n" +
                "avatar: " + jugadorActual.getAvatar().getId() + "\n" +
                        "}");
    }

//////---Metodo Edificar:
/*
 * 
 *                 if (partes.length == 2) {
                    jugadores.get(turno).getAvatar().getLugar().edificar(partes[1]);
                    }else System.out.println("Comando no reconocido");
                }
 */

    public void edificar(String tipo){
        jugadores.get(turno).getAvatar().getLugar().edificar(tipo);
    }


 //////---Metodo para acabar el turno:
 /*
  *             case "acabar" -> {
                if (partes.length == 2 && partes[1].equals("turno")) {
                    if (jugadores.get(turno).getFortuna()<0) {
                        System.out.println("No puedes acabar turno. Debes declarar bancarrota, hipotecar propiedades o vender edificaciones.");
                    } else{
                        acabarTurno();
                    }
                }else if(partes.length == 3 && partes[1].equals("turno") && partes[2].equals("force")){
                    acabarTurnoForce();
                } else {
                    System.out.println("Comando no reconocido");
                }
  */

    public void acabar(){
        if (jugadores.get(turno).getFortuna()<0) {
            System.out.println("No puedes acabar turno. Debes declarar bancarrota, hipotecar propiedades o vender edificaciones.");
        } else{
            acabarTurno();
        }
    }

    //////---Metodo para cambiar modo de movimiento:
    public void cambiarModo(){
        if (!tirado) {
            avatares.get(turno).cambiarModo();
        }
        else System.out.println("Debes acabar el turno antes de cambiar el modo de movimiento.");
    }

    ////// ----Metodo para declarar bancarrota:
    public void bancarrota(){
        Jugador deudor = jugadores.get(turno).getDeudor();
        if (deudor != null) {
            bancarrota(jugadores.get(turno), jugadores.get(turno).getDeudor());
        }
        else{
            bancarrota(jugadores.get(turno), banca);
        }
    }


    //////---Metodo para hipotecar propiedades:
    public void hipotecar(String casilla){
        hipotecar(casilla, jugadores.get(turno));
    }

    //////---Metodo para deshipotecar propiedades:
    public void deshipotecar(String casilla){
        deshipotecar(casilla, jugadores.get(turno));
    }


//////---METODO DESCRIBE JUGADOR---
    public void descJugador(String nombre) {
        for (Jugador player : jugadores) {
            if (player!=null && player.getNombre().equals(nombre)) {
                System.out.println(player);
                
            }
        }
    }
//////---METODO DESCRIBE AVATAR---
    public void descAvatar(String ID) {
        for (Avatar av : avatares) {
            if (av!=null && av.getId().equals(ID)) {
                System.out.println(av);
                
            }
        }
    }
//////---METODO DESCRIBE CASILLA---
    public void descCasilla(String nombre) {
        Casilla casilla = this.tablero.casillaByName(nombre);
        if (casilla != null) {
            System.err.println(casilla.getNombre());
            System.out.println(casilla.infoCasilla());
        } else {
            System.out.println("Casilla no encontrada con ese nombre.");
        }
    }
//////---METODO SIMULA LANZAMIENTO DADOS---
    public void tiradados(int dado1, int dado2){
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
    public void lanzarDados() {
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
    public void manejarCarcel(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual) {
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
                moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2,false);
            } else {
                System.out.println("No has sacado dobles, sigues en la cárcel.");
                jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                acabarTurno();
            }
        }
    }
//////---METODO LANZAMIENTOS DADOS ALEATORIOS---
    public void manejarTiradaNormal(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual) {
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
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2,false);
    }
//////---METODO MOVER AVATAR AL LANZAR DADOS---
    public void moverYVerTablero(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual, int total, boolean avanzar) {
        Casilla nuevaCasilla;
        int newposition = moverJugador(total);
        nuevaCasilla = tablero.getCasilla(newposition);

        System.out.println("El avatar " + Valor.BLUE + avatarActual.getId()+ Valor.RESET + " avanzó " +Valor.BLUE + total + Valor.RESET +" posiciones. Desde " + 
        Valor.RED+posicionActual.getNombre()+ Valor.RESET + " hasta " +Valor.GREEN+ nuevaCasilla.getNombre()+Valor.RESET);
        verTablero();
        // Verificar si el jugador debe ser encarcelado
        if (nuevaCasilla == tablero.getPosiciones().get(3).get(0)) {
            jugadorActual.encarcelar(tablero.getPosiciones());
        }
        if (nuevaCasilla.getTipo().equals("Comunidad") || nuevaCasilla.getTipo().equals("Suerte")) {
            cartas.gestionCartas(avatarActual, tablero, jugadores);
        }
        if (avanzar){
            // Verificar si el jugador puede pagar sus deudas
            if (!nuevaCasilla.evaluarCasilla(jugadorActual, banca, avatarActual.getValorTotalTirada())) {
                return;
            }
        }else{
            if (!nuevaCasilla.evaluarCasilla(jugadorActual, banca, total)) {
                return;
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
    public int moverJugador(int posiciones) {
        if (avatares.get(turno).esMovAvanzado()) {
            if (avatares.get(turno).getTipo().equals("Pelota")) {
                return avatares.get(turno).moverAvatarPelota(tablero.getPosiciones(), posiciones);
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
    public void comprar(String nombre) {
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
    public void salirCarcel() {
        if (lanzamientos==0) { //al inicio del turno en el que esta en la carcel, puede pagar
            if(!jugadores.get(turno).pagarSalidaCarcel()){
            }
        }else{ //si no ha salido en el turno anterior, sale de la carcel
            System.out.println("Solo puedes pagar la multa al inicio de un turno en el que no hayas tirado los dados.");
        }
    }
//////---METODO LISTA PROPIEDADES EN VENTA---
    public void listarVenta() {
        for (int i = 0; i < 40; i++) {
            Casilla c=this.tablero.getCasilla(i);
            if (c.getDuenho()==banca && (c.getTipo().equals("Solar") || c.getTipo().equals("Transporte") || c.getTipo().equals("Servicio"))) {
                System.out.println(c.casEnVenta());
                
            }
        }
    }
//////---METODO LISTA JUGADORES---
    public void listarJugadores() {
        for (Jugador jugador: jugadores){
            if (jugador.getAvatar()!=null) {
                System.out.println(jugador);
            }
    }}
//////---METODO LISTA AVATARES---
    public void listarAvatares() {
        for (Avatar avatar: avatares){
            if (avatar!=null) {
                System.out.println(avatar);
            }
        }
    }
//////---METODO LISTAR EDIFICIOS---
    public void listarEdificios() {
        for (int i = 0; i < 40; i++) {
            Casilla c=tablero.getCasilla(i);
            c.mostrarEdificaciones();
        }
    }
    public void listarGrupo(String grupo) {
        try{
            tablero.getGrupos().get(grupo).mostrarEdificaciones();
        } catch (Exception e) {
            System.out.println("No se ha encontrado ese grupo");
            System.out.println("Los grupos posibles son (en orden): black, cyan, purple, yellow, red, brown, green, blue.");
            //System.out.println(e);
        }
    }
//////---METODO ACABAR TURNO---
    public void acabarTurno() {
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
        avatarActual.setUltimoTiroFueCoche(false);
        avatarActual.getJugador().setCochePuedeComprar(true);
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


    //////---METODO ACABAR TURNO---
    public void acabarTurnoForce() {
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
        avatarActual.setUltimoTiroFueCoche(false);
        avatarActual.getJugador().setCochePuedeComprar(true);
        turno++;
        if (turno>(jugadores.size()-1)) {
            turno = 1;
        }
        lanzamientos = 0;
        tirado = false;
        System.out.println("Turno de "+ jugadores.get(turno).getNombre());
        }

    public void acabarTurnoBancarrota(){
        turno++;
        if (turno>(jugadores.size()-1)) {
            turno = 1;
        }
        lanzamientos = 0;
        tirado = false;
        System.out.println("Turno de "+ jugadores.get(turno).getNombre());
    }

//////---METODO VER TABLERO---
    public void verTablero() {
        System.out.println(tablero);
    
    }
//////---METODO FINALIZAR PARTIDA---
    public void endGame() {
        partida_OFF = true;
        System.exit(0);
    }
//////---METODO SLEEP---
    public void sleep(int milliseconds) {
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
                    if (jugadorActual.isEnDeuda() && jugadorActual.getFortuna()>0){
                        if (jugadorActual.getDeudor()!=null){
                            jugadorActual.getDeudor().sumarFortuna(jugadorActual.getCantidadDeuda());
                            System.out.println("Deuda pagada");
                            jugadorActual.setEnDeuda(false);
                        }
                        else{
                            banca.sumarFortuna(jugadorActual.getCantidadDeuda());
                            System.out.println("Deuda pagada");
                            jugadorActual.setEnDeuda(false);
                        }
                        
                    }
                else System.out.println("No te pertenece esa casilla!");
            }else System.out.println("Casilla no encontrada...");
        }catch (NumberFormatException e){
            System.out.println("Error: "+e);
        }
    }
    public Casilla buscar_Casilla(String casilla){
        for(int i=0;i<40;i++){
            Casilla c=tablero.getCasilla(i);
            if (c.getNombre().equals(casilla))
                return c;
        }
        return null;
    }
    public void print_intro(){
        System.out.println("\n" + //
                        "\n" + //
                        "                                                                                                                                     \n" + //
                        "                                                                                                                                     \n" + //
                        "                                                                                                                                     \n" + //
                        "    ███    █   ████   █   █   █     █   ████   █   █   █   ███       ████     █                                                      \n" + //
                        "    █  █   █   █      ██  █    █   █    █      ██  █   █   █  █     █    █    █                                                      \n" + //
                        "    ███    █   ███    █ █ █     █ █     ███    █ █ █   █   █   █   █      █   █                                                      \n" + //
                        "    █  █   █   █      █  ██     █ █     █      █  ██   █   █  █     █    █                                                           \n" + //
                        "    ███    █   ████   █   █      █      ████   █   █   █   ███       ████     █                                                      \n" + //
                        "                                                                                                                                     \n" +Valor.RED+ //
                        "   ██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████    \n" + //
                        "   ██                                                                                                                           █    \n" + //
                        "   ██  ███████████████████████████████████████████████████████████████████████████████████████████████████████████████████████  █    \n" + //
                        "   ██ █████  ███████████  ████          █████  ███████   ████           ████        █████           ████   █████   ██████   ███ █    \n" + //
                        "   ████████   █████████   ███    █████    ███    █████   ███    █████    ███   ███    ██    █████    ███   ██████    ███   ██████    \n" + //
                        "   ███████     ███████    ██   █████████   ██     ████   ██   █████████   ██   ████   █   █████████   ██   ███████    █   ███████    \n" + //
                        "   ███████      █████     █   ██████████   ██       ██   █   ██████████    █   ███        ██████████   █   █████████      ███████    \n" + //
                        "   ███████   █   ██       █   ██████████   ██   ██       █   ██████████    █         █    ██████████   █   ██████████    ████████    \n" + //
                        "   ███████   ██       █    █   ████████    ██   ████     ██   █████████   ██      █████   █████████   ██   █████████    █████████    \n" + //
                        "   ███████   ███     ███   ██    █████    ███   ██████   ██    ██████    ███   █████████    █████     ██          ██   ██████████    \n" + //
                        "   ██████    ████  █████   ████         █████   ███████  ████           ████   ██████████           ████         ██   ███████████    \n" + //
                        "   ██████████████████████████████████████████████████████████████   █████████████████████████   █████████████████████████████████    \n" + //
                        "   ██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████    \n" + //
                        "   ██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████    \n" + //
                        "                                                                                                                                     \n" + //
                        "                                                                                                                                     \n" + //
                        "\n" +Valor.RESET+ //
                        "");
    }
////////////////////////////////////DEBUG COMMANDS////////////////////////////////////
//////---METODO LANZA DADOS VALORES ESPECIFICOS---
    public void lanzarDados(String dado1, String dado2) {
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
    public void manejarCarcel(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual, int dado1, int dado2) {
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
                moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2,false);
            } else {
                System.out.println("No has sacado dobles, sigues en la cárcel.");
                jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                acabarTurno();
            }
        }
    }
//////---METODO LANZAMIENTOS DADOS VALORES ESPECIFICOS---
    public void manejarTiradaNormal(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual, int dado1, int dado2) {
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
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2,false);
    }
    public void estadisticas(String nombre) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                jugador.estadisticas();
            }
        }
    }


    public float FortunaYValorPropEdif(Jugador jugador) {
        float pasta=jugador.getFortuna();
        for (Casilla c : jugador.getPropiedades()) {
            pasta+=c.getValor();
            if (c.getTipo().equals("Solar") && c.getEdificaciones().size()>0 &&c.getEdificaciones()!=null) {
                for (Map.Entry<String, ArrayList<Edificacion>> entry : c.getEdificaciones().entrySet()) {
                    for (Edificacion e : entry.getValue()) {
                        pasta += e.getPrecio(); // Suponiendo que Edificacion tiene un método getValor() que devuelve el valor de la edificación
                    }
                }
            }
        }
        
        return pasta;
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
            if (FortunaYValorPropEdif(jugador) > FortunaYValorPropEdif(jugadorEnCabeza)) { // PROVISIONAL, hay que añadir el valor de las propiedades y de los edificios
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

    public  void bancarrota(Jugador pobre, Jugador duenho) {
        float pasta=pobre.getFortuna();
        ArrayList<Casilla> propiedades = pobre.getPropiedades();
        float recuperado=0;
        for (Casilla c : propiedades) {
            if (c.getTipo().equals("Solar") && c.getEdificaciones().size()>0 &&c.getEdificaciones()!=null) {
                c.getEdificaciones().clear();
                
            }
            c.setDuenho(duenho);
            duenho.anhadirPropiedad(c);
        }
        if (duenho==banca){
            System.out.println("Las propiedades de " + pobre.getNombre() + " pueden ser compradas de nuevo.");
        }
        if (pasta<0){
            recuperado = pobre.getDineroPreDeuda();
            duenho.sumarFortuna(pobre.getDineroPreDeuda()); //Sumamos el dinero que tenía antes de la deuda
        } else if (pasta>0){
            recuperado =pasta;
            duenho.sumarFortuna(pasta); //Sumamos el dinero actual
        }
        System.out.println("El jugador "+duenho.getNombre()+" ha ganado por la deuda"+recuperado+" € ahora tiene "+duenho.getFortuna()+" €");
        turno--;
        if(turno<1){
            turno=jugadores.size()-1;
        }
        pobre.getAvatar().getLugar().eliminarAvatar(pobre.getAvatar());
        avatares.remove(pobre.getAvatar());
        jugadores.remove(pobre);
        acabarTurnoBancarrota(); //Checkear que se haga bien1
    }

    public void hipotecar(String nombreCasilla,Jugador actual){
        Casilla c = tablero.casillaByName(nombreCasilla);
        if (c!=null){
            c.hipotecar(actual);
            if (actual.isEnDeuda() && actual.getFortuna()>0){
                if (actual.getDeudor()!=null){
                    actual.getDeudor().sumarFortuna(actual.getCantidadDeuda());
                    System.out.println("Deuda pagada");
                    actual.setEnDeuda(false);
                }
                else{
                    banca.sumarFortuna(actual.getCantidadDeuda());
                    System.out.println("Deuda pagada");
                    actual.setEnDeuda(false);
                }
                
            }
        }else System.out.println("Casilla no encontrada");
    }

    public void deshipotecar(String nombreCasilla,Jugador actual){
        Casilla c = tablero.casillaByName(nombreCasilla);
        if (c!=null){
            c.deshipotecar(actual);
        }else System.out.println("Casilla no encontrada");
    }

    public void avanzar(){
        Avatar avatarActual = avatares.get(turno);
        if (avatarActual.puedeAvanzar()){
            Jugador jugadorActual = jugadores.get(turno);
            Casilla posicionActual = avatarActual.getLugar();
            int valor = avatarActual.nextPelota(true);
            System.out.println("Avanzamos "+valor+" posiciones");

            moverYVerTablero(jugadorActual, avatarActual, posicionActual, valor,true);
            if(avatarActual.nextPelota(false)==0){
                avatarActual.limpiarMovPelota();
                System.out.println("Limpiando movimientos de pelota");
            }
        }
    }

    /* 
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Casilla now) {
        // Incrementar el valor en 1 punto para la casilla actual
        actual.getNumeroVisitas().put(now, actual.getNumeroVisitas().getOrDefault(now, 0) + 1);
        // debug System.out.println("Entrando en evaluar casilla");
        if (now.esComprable(actual, banca)) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Puedes comprar la casilla " + now.getNombre() + " por " + now.getValor() + " ¿Quieres comprarla? (s/n) ");
            String respuesta = sc.nextLine();
            if (respuesta.equals("s")) {
                now.comprarCasilla(actual, banca);
            }
        } else if (now.getDuenho() != actual && now.getDuenho() != banca) {
            System.out.println("Has pagado " + Valor.RED + now.calcularPago(tirada) + "€" + Valor.RESET + " al jugador " + Valor.BLUE + now.getDuenho().getNombre() + Valor.RESET + " por caer en la casilla " + Valor.BLUE + now.getNombre() + Valor.RESET);
            now.setDineroGenerado(now.getDineroGenerado() + now.calcularPago(tirada));
            now.getGrupo().setDineroGenerado(now.getGrupo().getDineroGenerado() + now.calcularPago(tirada));
            return actual.pagarAJugador(now.getDuenho(), now.calcularPago(tirada));
        } else if (actual.puedeEdificar(now)) {
            System.out.println("Has caido en la casilla " + Valor.BLUE + now.getNombre() + Valor.RESET + "." + Valor.RED + "  ¡Puedes edificar!" + Valor.RESET);
        } else if (now.getTipo().equals("Impuesto")) {
            System.out.println("Has pagado " + now.getImpuesto() + " a la banca por caer en la casilla " + now.getNombre());
            System.out.println("Tu fortuna es de " + actual.getFortuna());
            return actual.pagarImpuesto(now.getImpuesto(), banca);
        } else if (now.getNombre().equals("Parking")) {
            actual.setPremiosInversionesOBote(actual.getPremiosInversionesOBote() + actual.getBote());
            actual.recibirBote(banca);
            System.out.println("Bote puesto a " + Valor.RED + banca.getBote() + "€" + Valor.RESET);
            return true;
        } else if (now.getNombre().equals("Ir a Cárcel")) {
            System.out.println(Valor.RED + "Has sido enviado a la cárcel" + Valor.RESET);
        } else if (now.getNombre().equals("Carcel")) {
            System.out.println("Estás en la " + Valor.BLUE + "cárcel" + Valor.RESET + ", pero de VISITA");
        }
        // debug System.out.println("Saliendo de evaluar casilla");
        return true;
    }
        */


//////---METODO QUE LANZA DADOS UN VALOR??
    public void lanzarDados(int tiradaTotal){
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = avatares.get(turno);
        Casilla posicionActual = avatarActual.getLugar();
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, tiradaTotal,false);
    }
//////---METODO CAMBIA FORTUNA
    public void fortunaManual(float cantidad){
        jugadores.get(turno).setFortuna(cantidad);
        System.out.println("Fortuna de "+jugadores.get(turno).getNombre()+" actualizada a "+cantidad);
    }
}