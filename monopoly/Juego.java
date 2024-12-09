package monopoly;

import java.util.ArrayList;
import java.util.Map;

import Excepciones.Ejecucion.*;
import Excepciones.MalUsoComando.*;
import partida.*;

public class Juego implements Comando{

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
    private boolean partida_OFF; //Booleano para comprobar si la partida ha finalizado.
    private CartaSuerte CartasSuerte = new CartaSuerte();
    private CartaCajaComunidad CartasComunidad = new CartaCajaComunidad();
    public static Consola consola = new ConsolaNormal(); //Objeto de la clase ConsolaNormal


//////---CONSTRUCTOR JUEGO---

    public Juego() {
        this.turno = 1;
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
        consola.imprimir("\n\nCreamos los 2 jugadores mínimos para jugar...");
        anadirjugador();
        anadirjugador();
        consola.imprimir("Si desea añadir más jugadores, introduzca 'crear jugador'...");

    }
//////---METODO AÑADIR JUGADOR---
    @Override
    public void anadirjugador(){
        String nombre = Juego.consola.leer("Indica el nombre del jugador ("+jugadores.size()+"): ");
        if (nombreRepetido(nombre)){
            while (nombreRepetido(nombre)) {
                consola.imprimir("Nombre repetido, introduce otro nombre.");
                nombre = Juego.consola.leer("Indica el nombre del jugador ("+jugadores.size()+"): ");
            }
        }

        String tipo = Juego.consola.leer("Indica el tipo de avatar (Sombrero, Esfinge, Pelota, Coche): ");
        if (Avatar.TipoValido(tipo)) {
            crearJugador(nombre, tipo);
        } else {
            while (!Avatar.TipoValido(tipo)) {
                consola.imprimir("Tipo de avatar no válido, introduce un tipo válido.");
                tipo = Juego.consola.leer("Indica el tipo de avatar (Sombrero, Esfinge, Pelota, Coche): ");
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
            consola.imprimir("Jugador " + nombre + " creado con éxito.");
            tablero.getCasilla(0).anhadirAvatar(avatar);
        } else {
            consola.imprimir("No se pueden crear más jugadores");
        }
        
    }

//////---Metodo Jugador:
///             case "jugador" -> {
// Jugador jugadorActual = jugadores.get(turno);
//consola.imprimir("\n{\nnombre: " + jugadorActual.getNombre() + ",\n" +
//        "avatar: " + jugadorActual.getAvatar().getId() + "\n" +
//                "}");
//}
    @Override
    public void jugadorInfo(){
        Jugador jugadorActual = jugadores.get(turno);
        consola.imprimir("\n{\nnombre: " + jugadorActual.getNombre() + ",\n" +
                "avatar: " + jugadorActual.getAvatar().getId() + "\n" +
                        "}");
    }

//////---Metodo Edificar:
/*
 * 
 *                 if (partes.length == 2) {
                    jugadores.get(turno).getAvatar().getLugar().edificar(partes[1]);
                    }else consola.imprimir("Comando no reconocido");
                }
 */
    @Override
    public void edificar(String tipo){
        try{
            jugadores.get(turno).getAvatar().getLugar().edificar(tipo);
        } catch (Exception e){
            System.out.println(e);
        }
    }


 //////---Metodo para acabar el turno:
 /*
  *             case "acabar" -> {
                if (partes.length == 2 && partes[1].equals("turno")) {
                    if (jugadores.get(turno).getFortuna()<0) {
                        consola.imprimir("No puedes acabar turno. Debes declarar bancarrota, hipotecar propiedades o vender edificaciones.");
                    } else{
                        acabarTurno();
                    }
                }else if(partes.length == 3 && partes[1].equals("turno") && partes[2].equals("force")){
                    acabarTurnoForce();
                } else {
                    consola.imprimir("Comando no reconocido");
                }
  */
    @Override
    public void acabar() throws AcabarTurno{
        if (jugadores.get(turno).getFortuna()<0) {
            throw new AcabarTurno("Debes declarar bancarrota, hipotecar propiedades o vender edificaciones.");
        } else{
            acabarTurno();
        }
    }

    //////---Metodo para cambiar modo de movimiento:
    @Override
    public void cambiarModo(){
        if (!tirado) {
            avatares.get(turno).cambiarModo();
        }
        else consola.imprimir("Debes acabar el turno antes de cambiar el modo de movimiento.");
    }

    ////// ----Metodo para declarar bancarrota:
    @Override
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
    @Override
    public void hipotecar(String casilla) throws HipotecaSinTener{
        hipotecar(casilla, jugadores.get(turno));
    }

    //////---Metodo para deshipotecar propiedades:
    @Override
    public void deshipotecar(String casilla){
        deshipotecar(casilla, jugadores.get(turno));
    }


//////---METODO DESCRIBE JUGADOR---
    @Override
    public void descJugador(String nombre)  throws JugadorNoEncontrado{
        for (Jugador player : jugadores) {
            if (player!=null && player.getNombre().equals(nombre)) {
                consola.imprimir(player.toString());
                break;
            }
        }
        throw new JugadorNoEncontrado(nombre);
    }
//////---METODO DESCRIBE AVATAR---
    @Override
    public void descAvatar(String ID) throws AvatarNoEncontrado {
        for (Avatar av : avatares) {
            if (av!=null && av.getId().equals(ID)) {
                consola.imprimir(av.toString());
                break;
            }
        }
        throw new AvatarNoEncontrado(ID);
    }
//////---METODO DESCRIBE CASILLA---
    @Override
    public void descCasilla(String nombre) throws CasillaNoEncontrada {
        Casilla casilla = this.tablero.casillaByName(nombre);
        if (casilla != null) {
            consola.imprimir(casilla.infoCasilla());
        }else throw new CasillaNoEncontrada(nombre);
    }
//////---METODO SIMULA LANZAMIENTO DADOS---

    private void cargando(int milliseconds, int ratio) {

        consola.imprimirlinea("|"); 

        int count = 0;
        while (count < milliseconds) {

            sleep(ratio);
            count += ratio;
            consola.imprimirlinea("\b/");
            sleep(ratio);
            count += ratio;
            consola.imprimirlinea("\b—");
            sleep(ratio);
            count += ratio;
            consola.imprimirlinea("\b\\");
            sleep(ratio);
            count += ratio;
            consola.imprimirlinea("\b|");

        }

        consola.imprimirlinea("\b" + Valor.RESET);

    }



    public void tiradados(int dado1, int dado2){

        String B = Valor.BLUE;
        String RE = Valor.RESET;


        consola.imprimirlinea("Lanzando dados... " + B);
        cargando(2000, 100);
        Avatar avatarActual = avatares.get(turno);
        avatarActual.getJugador().setVecesDados(avatarActual.getJugador().getVecesDados()+1);
        // Verificar si el avatar tiene movimiento avanzado y es de tipo "Coche"
        if (avatarActual.esMovAvanzado() && avatarActual.getTipo().equals("Coche")) {
            // Verificar si es necesario cambiar la tirada
            
            if ((avatarActual instanceof Coche coche && coche.getUltimoTiroFueCoche()) || avatarActual.getJugador().isEnCarcel()) {
                tirado = true;
                lanzamientos++;
            }
        } else {
            // Para cualquier otro caso, incrementar lanzamientos
            lanzamientos++;
            tirado=true;
        }

        consola.imprimir("Han salido "+ B + dado1 + RE + " y " + B + dado2 + RE + "!");
    }


//////---METODO LANZA DADOS ALEATORIOS---
    @Override
    public void lanzarDados() throws AcabarTurno,EdificarSinPoder, DineroError, InstanciaIncorrecta  {
        dado1 = new Dado();
        dado2 = new Dado();
        Avatar avatarActual = avatares.get(turno);
        if (tirado) {
            consola.imprimir("Ya has lanzado los dados en este turno.");
            return;
        }
        if (avatarActual instanceof Coche coche) {
            if (coche.isCocheParado()) {
                consola.imprimir("El coche está parado, no puede lanzar los dados en " + coche.getTurnosParado() + " turnos consecutivos.");
                return;
            }
            if (coche.getTiros_extra() == 4) {
                consola.imprimir("Se han acabado tus tiros extra");
                tirado = true;
                return;
            }
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
    public void manejarCarcel(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual) throws AcabarTurno,EdificarSinPoder, DineroError, InstanciaIncorrecta  {
        String G = Valor.GREEN;
        String RE = Valor.RESET;

        if (jugadorActual.getTiradasCarcel() >= 3) {
            consola.imprimir("Has pasado 3 turnos en la cárcel, sales! Tira los dados!");
            salirCarcel();
        } else {
            consola.imprimir("Estás en la cárcel, debes sacar dobles para salir.");
            int resultado1 = dado1.hacerTirada();
            int resultado2 = dado2.hacerTirada();
            tiradados(resultado1, resultado2);
    
            if (resultado1 == resultado2) {
                consola.imprimir(G + "¡Dobles!" + RE + " Sales de la cárcel.");
                jugadorActual.sacarCarcel();
                sleep(1000);
                moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2,false);
            } else {
                consola.imprimir("No has sacado dobles, sigues en la cárcel.");
                jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                acabarTurno();
            }
        }
    }
//////---METODO LANZAMIENTOS DADOS ALEATORIOS---
    public void manejarTiradaNormal(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual)  throws AcabarTurno,EdificarSinPoder, DineroError, InstanciaIncorrecta  {
        int resultado1 = dado1.hacerTirada();
        int resultado2 = dado2.hacerTirada();

        String B = Valor.BLUE;
        String R = Valor.RED;
        String RE = Valor.RESET;

        tiradados(resultado1, resultado2);
    
        if (resultado1 == resultado2) {
            if (lanzamientos == 3) {
                consola.imprimir(R + "¡Dobles! " + RE + "Has sacado tres veces dobles, vas a la cárcel.");
                jugadorActual.encarcelar(tablero.getPosiciones());
                verTablero();
                acabarTurno();
                return;
            } else {
                consola.imprimir(B + "¡Dobles!" + RE + " Puedes tirar otra vez.");
                consola.imprimir("Entra a dobles una vez");
                tirado = false;
            }
        }
    
        sleep(1000);
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2,false);
    }
//////---METODO MOVER AVATAR AL LANZAR DADOS---
    public void moverYVerTablero(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual, int total, boolean avanzar) throws EdificarSinPoder, DineroError, InstanciaIncorrecta  {
        Casilla nuevaCasilla;
        int newposition = moverJugador(total);
        nuevaCasilla = tablero.getCasilla(newposition);

        consola.imprimir("El avatar " + Valor.BLUE + avatarActual.getId()+ Valor.RESET + " avanzó " +Valor.BLUE + total + Valor.RESET +" posiciones. Desde " + 
        Valor.RED+posicionActual.getNombre()+ Valor.RESET + " hasta " +Valor.GREEN+ nuevaCasilla.getNombre()+Valor.RESET);
        verTablero();
        // Verificar si el jugador debe ser encarcelado
        if (nuevaCasilla == tablero.getPosiciones().get(3).get(0)) {
            jugadorActual.encarcelar(tablero.getPosiciones());
        }


        if (nuevaCasilla instanceof AccionComunidad){
            CartasComunidad.gestionCartas(avatarActual, tablero, jugadores);
        }

        if (nuevaCasilla instanceof AccionSuerte) {
            CartasSuerte.gestionCartas(avatarActual, tablero, jugadores);
        }
        /*/
        if (tipo.equals("Accion")) {
            cartas.gestionCartas(avatarActual, tablero, jugadores);
        }*/
        if (avanzar){
            // Verificar si el jugador puede pagar sus deudas
            

            if (avatarActual instanceof Pelota p){
                if (!nuevaCasilla.evaluarCasilla(jugadorActual, banca, p.getValorTotalTirada())) {
                    return;
                }
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
            if (avatares.get(turno).getTipo().equals("Coche")) {
                if (lanzamientos<2 && posiciones>4){
                    consola.imprimir("Tienes tirada extra, puedes seguir lanzando los dados.");
                }
                return avatares.get(turno).moverEnAvanzado(tablero.getPosiciones(), posiciones);
                
            }else{
                return avatares.get(turno).moverEnAvanzado(tablero.getPosiciones(), posiciones);
            }
            
        }
        else {
            return this.avatares.get(turno).moverEnBasico(this.tablero.getPosiciones(), posiciones);
        }
    }
    
    @Override
    public void comprar(String nombre) throws CompraSinPoder, InstanciaIncorrecta {
        Jugador jugador = jugadores.get(turno);
        Casilla casilla = jugador.getAvatar().getLugar();

        if (!(casilla instanceof Propiedad)){
            throw new InstanciaIncorrecta("Propiedad");
        }

        if (!casilla.getNombre().equals(nombre)){
            throw new CompraSinPoder("El usuario no se encuentra en la casilla que solicita");
        }

        Propiedad propiedad = (Propiedad) casilla;

        if (!propiedad.esComprable(jugador, banca)){
            throw new CompraSinPoder("El usuario no puede comprar la casilla que solicita");
        }

        propiedad.comprarCasilla(jugador, banca);// Tira las excepciones de las comprobaciones restantes

    }
//////---METODO SALIR CARCEL---
    @Override
    public void salirCarcel() {
        if (lanzamientos==0) { //al inicio del turno en el que esta en la carcel, puede pagar
            if(!jugadores.get(turno).pagarSalidaCarcel()){
            }
        }else{ //si no ha salido en el turno anterior, sale de la carcel
            consola.imprimir("Solo puedes pagar la multa al inicio de un turno en el que no hayas tirado los dados.");
        }
    }
//////---METODO LISTA PROPIEDADES EN VENTA---
    @Override
    public void listarVenta()  {
        for (int i = 0; i < 40; i++) {
            Casilla c = tablero.getCasilla(i);
            if (!(c instanceof Propiedad))
                continue;
            Propiedad propiedad = (Propiedad) c;

            if (propiedad.getDuenho() == banca ) {
                System.out.println(propiedad.casEnVenta());
                
            }
        }
    }
//////---METODO LISTA JUGADORES---
    @Override
    public void listarJugadores() {
        for (Jugador jugador: jugadores){
            if (jugador.getAvatar()!=null) {
                consola.imprimir(jugador.toString());
            }
    }}
//////---METODO LISTA AVATARES---
    @Override
    public void listarAvatares() {
        for (Avatar avatar: avatares){
            if (avatar!=null) {
                consola.imprimir(avatar.toString());
            }
        }
    }
//////---METODO LISTAR EDIFICIOS---
    @Override
    public void listarEdificios()  {
        for (int i = 0; i < 40; i++) {

            Casilla c = tablero.getCasilla(i);

            if (!(c instanceof Solar solar))
                continue;

            solar.mostrarEdificaciones();
        }
    }

    @Override
    public void listarGrupo(String grupo)  {
        try{
            tablero.getGrupos().get(grupo).mostrarEdificaciones();
        } catch (Exception e) {
            consola.imprimir("No se ha encontrado ese grupo");
            consola.imprimir("Los grupos posibles son (en orden): black, cyan, purple, yellow, red, brown, green, blue.");
            //consola.imprimir(e);
        }
    }
//////---METODO ACABAR TURNO---
    public void acabarTurno() throws AcabarTurno{
        Avatar avatarActual = avatares.get(turno);
        if (avatarActual instanceof Coche car){
            if (car.isCocheParado()){
                car.reducirTurnosParado();
                tirado = true;
                car.setUltimoTiroFueCoche(false);
                if (car.getTurnosParado()==0) {
                    car.setCocheParado(false);
                }
            }
            car.setTiros_extra(0);
            car.setUltimoTiroFueCoche(false);
            car.getJugador().setCochePuedeComprar(true);
        }
        if (tirado) {
            turno++;
            if (turno>(jugadores.size()-1)) {
                turno = 1;
            }
            lanzamientos = 0;
            tirado = false;
            consola.imprimir("Turno de "+ jugadores.get(turno).getNombre());}
        else throw new AcabarTurno("Debes lanzar los dados antes de acabar el turno.");
    }


    //////---METODO ACABAR TURNO---
    @Override
    public void acabarTurnoForce() {
        Avatar avatarActual = avatares.get(turno);
        if (avatarActual instanceof Coche car){
            if (car.isCocheParado()){
                car.reducirTurnosParado();
                tirado = true;
                car.setUltimoTiroFueCoche(false);
                if (car.getTurnosParado()==0) {
                    car.setCocheParado(false);
                }
            }
            car.setTiros_extra(0);
            car.setUltimoTiroFueCoche(false);
            car.getJugador().setCochePuedeComprar(true);
        }
        turno++;
        if (turno>(jugadores.size()-1)) {
            turno = 1;
        }
        lanzamientos = 0;
        tirado = false;
        consola.imprimir("Turno de "+ jugadores.get(turno).getNombre());
        }

    public void acabarTurnoBancarrota(){
        turno++;
        if (turno>(jugadores.size()-1)) {
            turno = 1;
        }
        lanzamientos = 0;
        tirado = false;
        consola.imprimir("Turno de "+ jugadores.get(turno).getNombre());
    }

//////---METODO VER TABLERO---
    @Override
    public void verTablero() {
        consola.imprimir(tablero.toString());
    
    }
//////---METODO FINALIZAR PARTIDA---
    @Override
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
            consola.imprimir("El hilo fue interrumpido.");
        }
    }
//////---METODO COMPRUEBA SI JUGADOR POSEE CASILLA
    public boolean tieneCasilla(Jugador jugador){

        Casilla casilla = jugador.getAvatar().getLugar();

        if (!(casilla instanceof Propiedad propiedad))
            return false;
        
        return propiedad.getDuenho().equals(jugador);
    }
//////---METODO VENDER EDIFICIO
    @Override
    public void vender_edificio(String tipo, String casilla,String num) throws VenderSinTener, CasillaNoEncontrada, InstanciaIncorrecta, EdificioNotFound {

        try{

            int numero = Integer.parseInt(num);
            Casilla c = buscar_Casilla(casilla);

            if(c == null){
                //Lo consideramos error de ejecución
                throw new CasillaNoEncontrada(casilla); 
            }

            if (!(c instanceof Solar solar))
                throw new InstanciaIncorrecta("Solar");
            
            Jugador jugadorActual = jugadores.get(turno);
            
            if (solar.getDuenho() != jugadorActual){
                throw new VenderSinTener("No te pertenece esa casilla");
            }

            solar.vender_edificio(tipo,numero); //Tira las excepciones de edificio no encontrado si no existe el edificio del tipo, o no hay suficientes

            boolean isDeudaPagada = (jugadorActual.isEnDeuda() && jugadorActual.getFortuna()>0);
            Jugador deudor = jugadorActual.getDeudor();

            if (isDeudaPagada && deudor != null){
                jugadorActual.setEnDeuda(false);
                deudor.sumarFortuna(jugadorActual.getCantidadDeuda());
                consola.imprimirMensaje("Deuda pagada");
            }

            if (isDeudaPagada){
                jugadorActual.setEnDeuda(false);
                banca.sumarFortuna(jugadorActual.getCantidadDeuda());
                consola.imprimirMensaje("Deuda pagada");
            }

        }catch (NumberFormatException e){
            throw new VenderSinTener("El número introducido no es válido");
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
        consola.imprimir("\n" + //
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
    @Override
    public void lanzarDados(String dado1, String dado2) throws LanzarDado, AcabarTurno,EdificarSinPoder, DineroError, InstanciaIncorrecta  {
        int dadoint1= Integer.parseInt(dado1);
        int dadoint2= Integer.parseInt(dado2);
        Avatar avatarActual = avatares.get(turno);
        if ((avatarActual instanceof Coche car) && car.isCocheParado()) {
            throw new LanzarDado("\nEl coche está parado, no puede lanzar los dados en "+car.getTurnosParado() +"turnos consecutivos.");
        }
        if ((dadoint1>6)||(dadoint2>6)) consola.imprimir("Valor de tirada no válida");    
        
        if (avatarActual.getJugador().getFortuna()<0) {
            throw new LanzarDado("\nDebes declarar bancarrota, hipotecar propiedades o vender edificaciones.");
        }

        if ((avatarActual instanceof Coche car) && car.getTiros_extra() == 4){
            throw new LanzarDado("\nSe han acabado tus tiros extra de coche");

        }
        if (tirado) {
            throw new LanzarDado("\nYa has lanzado los dados en este turno.");
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
    public void manejarCarcel(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual, int dado1, int dado2) throws AcabarTurno,EdificarSinPoder, DineroError, InstanciaIncorrecta  {
        String G = Valor.GREEN;
        String RE = Valor.RESET;
        if (jugadorActual.getTiradasCarcel() >= 3) {
            consola.imprimir("Has pasado 3 turnos en la cárcel, sales! Tira los dados!");
            salirCarcel();
        } else {
            consola.imprimir("Estás en la cárcel, debes sacar dobles para salir.");
            int resultado1 = dado1;
            int resultado2 = dado2;
            tiradados(resultado1, resultado2);

            if (resultado1 == resultado2) {
                consola.imprimir(G + "¡Dobles! " + RE + "Sales de la cárcel.");
                jugadorActual.sacarCarcel();
                sleep(1000);
                moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2,false);
            } else {
                consola.imprimir("No has sacado dobles, sigues en la cárcel.");
                jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                acabarTurno();
            }
        }
    }
//////---METODO LANZAMIENTOS DADOS VALORES ESPECIFICOS---
    public void manejarTiradaNormal(Jugador jugadorActual, Avatar avatarActual, Casilla posicionActual, int dado1, int dado2) throws AcabarTurno,EdificarSinPoder, DineroError, InstanciaIncorrecta  {

        String B = Valor.BLUE;
        String R = Valor.RED;
        String RE = Valor.RESET;

        int resultado1 = dado1;
        int resultado2 = dado2;
        tiradados(resultado1, resultado2);

        if (resultado1 == resultado2) {
            if (lanzamientos == 3) {
                consola.imprimir(R + "¡Dobles! " + RE + "Has sacado tres veces dobles, vas a la cárcel.");
                jugadorActual.encarcelar(tablero.getPosiciones());
                verTablero();
                acabarTurno();
                return;
            } else {
                consola.imprimir(B + "¡Dobles! " + RE + "Puedes tirar otra vez.");
                tirado = false;
            }
        }

        sleep(1000);
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, resultado1 + resultado2,false);
    }

    @Override
    public void estadisticas(String nombre) throws JugadorNoEncontrado {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                jugador.estadisticas();
            }
        }
        throw new JugadorNoEncontrado(nombre);
    }


    public float FortunaYValorPropEdif(Jugador jugador) {
        float pasta = jugador.getFortuna();

        for (Casilla c : jugador.getPropiedades()) {

            if (!(c instanceof Propiedad propiedad))
                continue;


            pasta += propiedad.getValor();

            if (!(propiedad instanceof Solar solar))
                continue;

            if (!solar.getEdificaciones().isEmpty() && solar.getEdificaciones() != null)
                for (Map.Entry<String, ArrayList<Edificacion>> entry : solar.getEdificaciones().entrySet())
                    for (Edificacion e : entry.getValue()) 
                        pasta += e.getPrecio();                     
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

    @Override
    public void estadisticasjuego() {
        Jugador jugadorMasVueltas = jugadores.get(1);
        Jugador jugadorMasVecesDados = jugadores.get(1);
        Jugador jugadorEnCabeza = jugadores.get(1);
        Propiedad casillaMasRentable = null;
        if (tablero.getCasilla(1) instanceof Propiedad propiedad)
            casillaMasRentable = propiedad;
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

            if (!(c instanceof Propiedad propiedad))
                continue;
            
            if (propiedad.getDineroGenerado() > casillaMasRentable.getDineroGenerado())
                casillaMasRentable = propiedad;
        }

        for (Grupo grupo : tablero.getGrupos().values()) {
            if (grupo.getDineroGenerado() > grupoMasRentable.getDineroGenerado()) {
                grupoMasRentable = grupo;
            }
        }
    
        consola.imprimir("{\n" +
                "casillaMasRentable: " + casillaMasRentable.getNombre() + ",\n" +
                "grupoMasRentable: " + grupoMasRentable.getColorGrupo() + "####" + Valor.RESET + ",\n" +
                "casillaMasFrecuentada: " + casillaMasFrecuentada.getNombre() + ",\n" +
                "jugadorMasVueltas: " + jugadorMasVueltas.getNombre() + ",\n" +
                "jugadorMasVecesDados: " + jugadorMasVecesDados.getNombre() + ",\n" +
                "jugadorEnCabeza: " + jugadorEnCabeza.getNombre() + "\n" +
                "}");
    }

    public  void bancarrota(Jugador pobre, Jugador duenho) {

        float pasta = pobre.getFortuna();

        ArrayList<Casilla> propiedades = pobre.getPropiedades();

        float recuperado=0;

        for (Casilla c : propiedades) {

            if (!(c instanceof Propiedad propiedad))
                continue;
            
            if (propiedad instanceof Solar solar)
                solar.getEdificaciones().clear();

            propiedad.setDuenho(duenho);
            duenho.anhadirPropiedad(propiedad);
        }
        if (duenho==banca){
            consola.imprimir("Las propiedades de " + pobre.getNombre() + " pueden ser compradas de nuevo.");
        }
        if (pasta<0){
            recuperado = pobre.getDineroPreDeuda();
            duenho.sumarFortuna(pobre.getDineroPreDeuda()); //Sumamos el dinero que tenía antes de la deuda
        } else if (pasta>0){
            recuperado =pasta;
            duenho.sumarFortuna(pasta); //Sumamos el dinero actual
        }
        consola.imprimir("El jugador "+duenho.getNombre()+" ha ganado por la deuda"+recuperado+" € ahora tiene "+duenho.getFortuna()+" €");
        turno--;
        if(turno<1){
            turno=jugadores.size()-1;
        }
        pobre.getAvatar().getLugar().eliminarAvatar(pobre.getAvatar());
        avatares.remove(pobre.getAvatar());
        jugadores.remove(pobre);
        acabarTurnoBancarrota(); //Checkear que se haga bien1
    }

    public void hipotecar(String nombreCasilla,Jugador actual) throws HipotecaSinTener{

        Casilla c = tablero.casillaByName(nombreCasilla);

        if (c != null && c instanceof Propiedad propiedad){
            propiedad.hipotecar(actual);
            if (actual.isEnDeuda() && actual.getFortuna()>0){
                if (actual.getDeudor()!=null){
                    actual.getDeudor().sumarFortuna(actual.getCantidadDeuda());
                    consola.imprimir("Deuda pagada");
                    actual.setEnDeuda(false);
                }
                else{
                    banca.sumarFortuna(actual.getCantidadDeuda());
                    consola.imprimir("Deuda pagada");
                    actual.setEnDeuda(false);
                }
                
            }
        }else consola.imprimir("Casilla no encontrada");
    }

    public void deshipotecar(String nombreCasilla,Jugador actual){

        Casilla c = tablero.casillaByName(nombreCasilla);

        if (c!=null && c instanceof Propiedad propiedad){
            propiedad.deshipotecar(actual);
        }else System.out.println("Casilla no encontrada");
    }

    @Override
    public void avanzar() throws AvanzarSinPoder,EdificarSinPoder, DineroError, InstanciaIncorrecta {
        Avatar avatarActual = avatares.get(turno);
        if (!(avatarActual instanceof Pelota p)){
            throw new InstanciaIncorrecta("Pelota");
        }
        if (p.puedeAvanzar()){
            Jugador jugadorActual = jugadores.get(turno);
            Casilla posicionActual = avatarActual.getLugar();
            int valor = p.nextPelota(true);
            consola.imprimir("Avanzamos "+valor+" posiciones");

            moverYVerTablero(jugadorActual, avatarActual, posicionActual, valor,true);
            if(p.nextPelota(false)==0){
                p.limpiarMovPelota();
                //consola.imprimir("Limpiando movimientos de pelota");
            }
        }
    }

    @Override
    public void verTratos() {
        Jugador jugadorActual = jugadores.get(turno);
        consola.imprimir("Tratos propuestos:");
        if (jugadorActual.getTratosPropuestos().isEmpty()) {
            consola.imprimir("No tienes tratos propuestos.");
        } else {
            for (Trato trato : jugadorActual.getTratosPropuestos()) {
                consola.imprimir(trato.toString());
            }
        }
    
        consola.imprimir("Tratos recibidos:");
        if (jugadorActual.getTratosRecibidos().isEmpty()) {
            consola.imprimir("No tienes tratos recibidos.");
        } else {
            for (Trato trato : jugadorActual.getTratosRecibidos()) {
                consola.imprimir(trato.toString());
            }
        }
    }



    @Override
    public void crearTrato(String[] partes) throws TratoIncompatible, JugadorNoEncontrado, CasillaNoEncontrada{
        Jugador jugador1 = jugadores.get(turno);
        Jugador jugador2 = nombreJugador(partes[1]);
        Trato trato=null;
        if (jugador2==null){
            throw new JugadorNoEncontrado(partes[1]);
        }
        int campos=0;
        if (partes.length==5){
            campos=2;
        }
        if (partes.length==7){
            campos=3;
        }
        float dinero=0;
        Propiedad SolarX=null;
        Propiedad SolarY=null;
        try{
            dinero = Float.parseFloat(partes[3]);
        }catch (NumberFormatException e){ //Si el primero no es numero, es un Solar
            SolarX = (Propiedad) tablero.casillaByName(partes[3]);
            if (SolarX==null){
                throw new CasillaNoEncontrada(partes[3]);
            }
        }

        if (campos==2){
            try{
                dinero = Float.parseFloat(partes[4]); //Trato SolarX dinero
                if (SolarX!=null){
                    trato = new Trato(jugador1,jugador2,SolarX,dinero);
                }
            }catch (NumberFormatException e){ //Si el segundo no es numero, es SolarY
                SolarY = (Propiedad) tablero.casillaByName(partes[4]);
                if (dinero!=0){ //Trato SolarX dinero
                    if (SolarY==null){
                        throw new CasillaNoEncontrada(partes[4]);
                    }
                    trato = new Trato(jugador1,jugador2,dinero,SolarY);
                }
                if (SolarX!=null && SolarY!=null){ //Trato SolarX SolarY
                    trato = new Trato(jugador1,jugador2,SolarX,SolarY);
                }
                

            }
        }else if(campos==3){
            if(partes[4].equals("y")){
                SolarX = (Propiedad) tablero.casillaByName(partes[3]);
                if (SolarX==null){
                    throw new CasillaNoEncontrada(partes[3]);
                }
                SolarY = (Propiedad) tablero.casillaByName(partes[6]);
                if (SolarY==null){
                    throw new CasillaNoEncontrada(partes[6]);
                }
                dinero = Float.parseFloat(partes[5]);
                trato = new Trato(jugador1,jugador2,SolarX,dinero,SolarY); //Trato SolarX y dinero por SolarY
            }
            else if (partes[5].equals("y")){
                SolarX = (Propiedad) tablero.casillaByName(partes[3]);
                if (SolarX==null){
                    throw new CasillaNoEncontrada(partes[3]);
                }
                SolarY = (Propiedad) tablero.casillaByName(partes[4]);
                if (SolarY==null){
                    throw new CasillaNoEncontrada(partes[4]);
                }
                dinero = Float.parseFloat(partes[6]);
                trato = new Trato(jugador1,jugador2,SolarX,SolarY,dinero); //Trato SolarX por SolarY y dinero
            }

        }

        if (trato==null){
            throw new TratoIncompatible("Haces un trato de tipo no contemplado");
        }
        if (trato.valido(true)){
            jugador1.getTratosPropuestos().add(trato);
            jugador2.getTratosRecibidos().add(trato);
            consola.imprimir("Trato creado con éxito");
            consola.imprimir(trato.toString());
        }
    }

    @Override
    public void aceptarTrato(String id) throws TratoIncompatible, TratoNoEncontrado, DineroError{
        Jugador jugador1 = jugadores.get(turno);
        int ident = Integer.parseInt(id);
        ArrayList<Trato> tratos=   jugador1.getTratosRecibidos();
        for (Trato trato : tratos) {
            if (trato.getid() == ident){
                trato.aceptarTrato();// Si el trato no es valido en el momento de aceptar, o hay error de ejecucion por no tener dinero, se lanza excepcion
                tratos.remove(trato);
                return;
            }
        }//Si salimos del bucle, es que no se ha encontrado el trato
        throw new TratoNoEncontrado(id);

    }

    //Eliminamos el trato de los dos jugadores, tanto de los tratos propuestos del jugador1, como de los recibidos del jugador2
    @Override
    public void eliminarTrato(String id) throws TratoNoEncontrado{
        Jugador jugador1 = jugadores.get(turno);
        int ident = Integer.parseInt(id);
        ArrayList<Trato> tratos=   jugador1.getTratosPropuestos();
        Jugador jugador2 =null;
        for (Trato trato : tratos) {
            if (trato.getid() == ident){
                jugador2 = trato.getjugadorAcepta();
                tratos.remove(trato);
                break;
            }
        }
        if (jugador2!=null){
            ArrayList<Trato> tratos2 = jugador2.getTratosRecibidos();
            for (Trato trato : tratos2) {
                if (trato.getid() == ident){
                    tratos2.remove(trato);
                    return;
                }
            }
        }
        throw new TratoNoEncontrado(id);

    }


    /* 
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Casilla now) {
        // Incrementar el valor en 1 punto para la casilla actual
        actual.getNumeroVisitas().put(now, actual.getNumeroVisitas().getOrDefault(now, 0) + 1);
        // debug consola.imprimir("Entrando en evaluar casilla");
        if (now.esComprable(actual, banca)) {
            Scanner sc = new Scanner(System.in);
            consola.imprimir("Puedes comprar la casilla " + now.getNombre() + " por " + now.getValor() + " ¿Quieres comprarla? (s/n) ");
            String respuesta = sc.nextLine();
            if (respuesta.equals("s")) {
                now.comprarCasilla(actual, banca);
            }
        } else if (now.getDuenho() != actual && now.getDuenho() != banca) {
            consola.imprimir("Has pagado " + Valor.RED + now.calcularPago(tirada) + "€" + Valor.RESET + " al jugador " + Valor.BLUE + now.getDuenho().getNombre() + Valor.RESET + " por caer en la casilla " + Valor.BLUE + now.getNombre() + Valor.RESET);
            now.setDineroGenerado(now.getDineroGenerado() + now.calcularPago(tirada));
            now.getGrupo().setDineroGenerado(now.getGrupo().getDineroGenerado() + now.calcularPago(tirada));
            return actual.pagarAJugador(now.getDuenho(), now.calcularPago(tirada));
        } else if (actual.puedeEdificar(now)) {
            consola.imprimir("Has caido en la casilla " + Valor.BLUE + now.getNombre() + Valor.RESET + "." + Valor.RED + "  ¡Puedes edificar!" + Valor.RESET);
        } else if (now.getTipo().equals("Impuesto")) {
            consola.imprimir("Has pagado " + now.getImpuesto() + " a la banca por caer en la casilla " + now.getNombre());
            consola.imprimir("Tu fortuna es de " + actual.getFortuna());
            return actual.pagarImpuesto(now.getImpuesto(), banca);
        } else if (now.getNombre().equals("Parking")) {
            actual.setPremiosInversionesOBote(actual.getPremiosInversionesOBote() + actual.getBote());
            actual.recibirBote(banca);
            consola.imprimir("Bote puesto a " + Valor.RED + banca.getBote() + "€" + Valor.RESET);
            return true;
        } else if (now.getNombre().equals("Ir a Cárcel")) {
            consola.imprimir(Valor.RED + "Has sido enviado a la cárcel" + Valor.RESET);
        } else if (now.getNombre().equals("Carcel")) {
            consola.imprimir("Estás en la " + Valor.BLUE + "cárcel" + Valor.RESET + ", pero de VISITA");
        }
        // debug consola.imprimir("Saliendo de evaluar casilla");
        return true;
    }
        */

    //OBTENER JUGADOR A PARTIR DE NOMBRE
    private Jugador nombreJugador(String nombre){
        for (Jugador player : jugadores) {
            if (player!=null && player.getNombre().equals(nombre)) {
                return player;
            }
        }
        return null;
    }

//////---METODO QUE LANZA DADOS UN VALOR??
   @Override
    public void lanzarDados(int tiradaTotal) throws EdificarSinPoder, DineroError, InstanciaIncorrecta {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = avatares.get(turno);
        Casilla posicionActual = avatarActual.getLugar();
        moverYVerTablero(jugadorActual, avatarActual, posicionActual, tiradaTotal,false);
    }
//////---METODO CAMBIA FORTUNA
    @Override
    public void fortunaManual(float cantidad){
        jugadores.get(turno).setFortuna(cantidad);
        consola.imprimir("Fortuna de "+jugadores.get(turno).getNombre()+" actualizada a "+cantidad);
    }
}