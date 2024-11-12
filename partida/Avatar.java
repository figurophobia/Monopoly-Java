package partida;

import java.util.ArrayList;
import java.util.Random;
import monopoly.*;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.
    private boolean CuartaVuelta; //Indica si el jugador acaba de hacer una vuelta completa multiplo de 4.
    private boolean movAvanzado; //Indica si el jugador ha activado el modo avanzado de movimiento
    private boolean modoCambiado = false; //Indica si el jugador ha cambiado el modo de movimiento
    private boolean compradoCoche = false; //Indica si el jugador ha comprado en su turno de ser coche
    private boolean cocheParado = false; //Indica si se detiene el movimiento del coche por 2 turnos
    private int turnosParado = 0; //Contador de turnos que lleva el coche parado
    private boolean ultimoTiroFueCoche = false; //Indica si es el ultimo tiro de los extras de coche
    private int tiros_extra= 0; //Contador de tiros de coche

    public int getTiros_extra() {
        return tiros_extra;
    }

    public void setTiros_extra(int tiros_extra) {
        this.tiros_extra = tiros_extra;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        if (id.matches("[A-Z]")) this.id=id;
        else System.out.println("Id inválido");
    }

    public boolean isCompradoCoche() {
        return compradoCoche;
    }

    public void setCompradoCoche(boolean compradoCoche) {
        this.compradoCoche = compradoCoche;
    }

    public boolean getUltimoTiroFueCoche() {
        return ultimoTiroFueCoche;
    }

    public void setUltimoTiroFueCoche(boolean ultimoTiroFueCoche) {
        this.ultimoTiroFueCoche = ultimoTiroFueCoche;
    }

    public int getTurnosParado() {
        return turnosParado;
    }

    public void reducirTurnosParado() {
        if (turnosParado>0) turnosParado--;
        if (turnosParado==0) cocheParado=false;
    }
    public boolean isCocheParado() {
        return cocheParado;
    }

    public void setCocheParado(boolean cocheParado) {
        this.cocheParado = cocheParado;
    }

    public void pararCoche(){
        this.cocheParado = true;
        turnosParado = 3;
    }

    public boolean esMovAvanzado() {
        return this.movAvanzado;
    }

    public void setMovAvanzado(boolean movAvanzado) {
        this.movAvanzado = movAvanzado;
    }

    public String getTipo() {
        return tipo;
    }
    //Método que comprueba si el tipo de avatar es válido. Es static porque no necesita acceder a los atributos de la clase
    //y asi lo podemos llamar desde el main sin problemas.
    public static boolean TipoValido(String tipo){
        if("Sombrero".equals(tipo) || "Esfinge".equals(tipo) || 
            "Pelota".equals(tipo) || "Coche".equals(tipo)) return true;
        else {
            //System.out.println("Tipo de avatar no válido, usa Sombrero, Esfinge, Pelota o Coche");
            return false;
        }
    }
    public void setTipo(String tipo) {
        if (TipoValido(tipo)) {
            this.tipo = tipo; // Asigna el valor si es válido
        }    
        else System.out.println("Tipo no válido");
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Casilla getLugar() {
        return lugar;
    }

    public void setLugar(Casilla lugar) {
        this.lugar = lugar;
    }
    
    //Constructor vacío
    public Avatar() {
    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        generarId(avCreados);
    }


    public void cambiarModo(){
        if (this.movAvanzado){
            if (!this.modoCambiado) {
                this.movAvanzado=false;
                System.out.println("Modo avanzado desactivado");               
            }
            else System.out.println("Ya has cambiado el modo de movimiento");
        }
        else{
            this.movAvanzado=true;
            this.modoCambiado=true;
            System.out.println("Modo avanzado activado");

        }
    }

    // Método para mover un avatar. Recibe un valor de tirada y un arraylist de casillas.
    public int moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        this.CuartaVuelta = false; //Al moverse, se reinicia el contador de cuarta vuelta, para no añadir valor cada vez que se juega en la cuarta vuelta


        int posicionactual=this.lugar.getPosicion();
        this.lugar.eliminarAvatar(this);
        int newposition = posicionactual+valorTirada;
        if (newposition>40) {
            this.jugador.sumarVueltas();
            this.jugador.sumarFortuna(Valor.SUMA_VUELTA);
            this.jugador.setPasarPorCasillaDeSalida(this.jugador.getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);
            System.out.println("Has dado una vuelta completa, recibes "+Valor.SUMA_VUELTA);
            if (this.jugador.getVueltas()%4==0) {
                this.CuartaVuelta=true;
            } 
        }
        newposition = (newposition-1)%40;
        Casilla newCasilla = casillas.get(newposition/10).get(newposition%10);
        newCasilla.anhadirAvatar(this);
        this.lugar = newCasilla;
        return newposition;
    }

    public int moverAvatarPelota(ArrayList<ArrayList<Casilla>> casillas, int valorTirada, Jugador banca) {
        CuartaVuelta = false;
        int posicion = lugar.getPosicion();
        int newposition = 0;
    
        // Determinar dirección de movimiento
        boolean dir = valorTirada > 4; // Si el valor de la tirada es mayor a 4, avanzar, sino retroceder
    
        // Si se cruza la salida
        if (dir && (posicion + valorTirada > 40)) {
            System.out.println("has dado una vuelta completa, recibes " + Valor.SUMA_VUELTA + ".");
            this.jugador.sumarVueltas();
            this.jugador.sumarFortuna(Valor.SUMA_VUELTA);
            this.jugador.setPasarPorCasillaDeSalida(this.jugador.getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);

            if (jugador.getVueltas() % 4 == 0) {
                CuartaVuelta = true;
            }
        }
    
        if (dir) { // Si se avanza
            for (int i = 5; i <= valorTirada; i += 2) {
                newposition = (posicion + i - 1) % 40;
                detenerse(newposition, banca, valorTirada, this.getLugar(), casillas);
                if (casillas.get(newposition / 10).get(newposition % 10).getNombre().equals("IrCarcel") || this.jugador.isEnBancarrota()) {
                    return newposition;
                }
            }
            if (valorTirada % 2 == 0) {
                newposition = (posicion + valorTirada - 1) % 40;
                detenerse(newposition, banca, valorTirada, this.getLugar(), casillas);
                if (casillas.get(newposition / 10).get(newposition % 10).getNombre().equals("IrCarcel") || this.jugador.isEnBancarrota()) {
                    return newposition;
                }
            }
        } else { // Si se retrocede
            for (int i = 1; i <= valorTirada; i += 2) {
                newposition = (posicion - i - 1);
                newposition = newposition < 0 ? (40 + newposition) % 40 : newposition % 40;
                detenerse(newposition, banca, valorTirada, this.getLugar(), casillas);
                if (casillas.get(newposition / 10).get(newposition % 10).getNombre().equals("IrCarcel") || this.jugador.isEnBancarrota()) {
                    return newposition;
                }
            }
            if (valorTirada % 2 == 0) {
                newposition = (posicion - valorTirada - 1);
                newposition = newposition < 0 ? (40 + newposition) % 40 : newposition % 40;
                detenerse(newposition, banca, valorTirada, this.getLugar(), casillas);
                if (casillas.get(newposition / 10).get(newposition % 10).getNombre().equals("IrCarcel") || this.jugador.isEnBancarrota()) {
                    return newposition;
                }
            }
        }
        return newposition;
    }

    public int moverAvatarCoche(ArrayList<ArrayList<Casilla>> casillas, int valorTirada){
        this.CuartaVuelta = false; //Al moverse, se reinicia el contador de cuarta vuelta, para no añadir valor cada vez que se juega en la cuarta vuelta


        int posicionactual=this.lugar.getPosicion();
        this.lugar.eliminarAvatar(this);
        int newposition;
        if (valorTirada>4) { //Si es mayor que 4, se mueve hacia adelante normalmente
        
            newposition = posicionactual+valorTirada;
            if (newposition>40) {
                this.jugador.sumarVueltas();
                this.jugador.sumarFortuna(Valor.SUMA_VUELTA);
                this.jugador.setPasarPorCasillaDeSalida(this.jugador.getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);
                System.out.println("Has dado una vuelta completa, recibes "+Valor.SUMA_VUELTA);
                if (this.jugador.getVueltas()%4==0) {
                    this.CuartaVuelta=true;
                } 
            }
            newposition = (newposition-1)%40;
            //Sumar movimiento extra: No cuentan dobles
            System.out.println("Tiros extra: "+tiros_extra);
            tiros_extra++;
            if (tiros_extra == 3){
                System.out.println("En el siguiente tiro se aplican los dobles");
                ultimoTiroFueCoche = true;
            }

        }
        else{ // Si es menor que 4 retrocede
            newposition = posicionactual-valorTirada-1;
            newposition = newposition < 0 ? (40 + newposition)%40 : newposition %40;
            //Paramos el coche por 2 turnos
            System.out.println("El coche se ha detenido por 2 turnos");
            pararCoche();
            ultimoTiroFueCoche = true;
            tiros_extra = 0;
            
        }
        Casilla newCasilla = casillas.get(newposition/10).get(newposition%10);
        newCasilla.anhadirAvatar(this);
        this.lugar = newCasilla;
        return newposition;

    }

    //Detenerse en casilla
    public void detenerse(int posicion, Jugador banca, int valorTirada, Casilla casillaActual,ArrayList<ArrayList<Casilla>> casillas) {
        casillaActual.eliminarAvatar(this);
        Casilla casillaFinal = casillas.get(posicion / 10).get(posicion % 10);
        System.out.println("El avatar " + this.getId() + " para en la casilla " + casillaFinal.getNombre());

        // Evaluar la casilla para posibles interacciones
        if (!casillaFinal.evaluarCasilla(jugador, banca, valorTirada)) {
            System.out.println("El jugador " + jugador.getNombre() + " no puede pagar sus deudas!");
            jugador.quedarBancarrota(casillaFinal.getDuenho());
            return;
        }

        // Si la casilla es "Ir a Cárcel", mover al avatar a la cárcel y terminar el movimiento
        if (casillaFinal.getPosicion() == 31) {
            System.out.println("El jugador " + jugador.getNombre() + " ha sido enviado a la cárcel.");
            jugador.encarcelar(casillas);
            return;
        }
    

    // Al finalizar, actualizar la posición y registrar la visita
    casillaFinal.anhadirAvatar(this);
    this.lugar = casillaFinal;
    }
    



    public boolean DarCuartaVuelta(){
        return this.CuartaVuelta;
    }

    /*Método que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        Random rnd = new Random();
        String letra;
        boolean idUnico;
    
        do {
            letra = String.valueOf((char) ('A' + rnd.nextInt(26)));
            idUnico = true;
            for (Avatar A : avCreados) {
                if (A != null && A.id.equals(letra)) {
                    idUnico = false;
                    break;
                }
            }
        } while (!idUnico);
    
        this.id = letra;
    }
    //Método para describir un avatar (sobreescribir toString), se mostrarán todas las características de un avatar:
    /*Estilo:
     * $> describir avatar M
    *{
    *id: M,
    *tipo: sombrero,
    *casilla: Cartagena,
    *jugador: Maria
    *}
     */
    @Override
    public String toString() {
        return "{\n" +
                "id: " + id + ",\n" +
                "tipo: " + tipo + ",\n" +
                "casilla: " + lugar.getNombre() + ",\n" +
                "jugador: " + jugador.getNombre() + "\n" +
                "}";
    }
}
