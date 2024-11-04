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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        if (id.matches("[A-Z]")) this.id=id;
        else System.out.println("Id inválido");
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
        if (this.movAvanzado) {
            this.movAvanzado=false;
            System.out.println("Modo avanzado desactivado");
        }
        else{ 
            this.movAvanzado=true;
            System.out.println("Modo avanzado activado");
        }
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        this.CuartaVuelta = false; //Al moverse, se reinicia el contador de cuarta vuelta, para no añadir valor cada vez que se juega en la cuarta vuelta


        int posicionactual=this.lugar.getPosicion();
        this.lugar.eliminarAvatar(this);
        int newposition = posicionactual+valorTirada;
        if (newposition>40) {
            this.jugador.sumarVueltas();
            this.jugador.sumarFortuna(Valor.SUMA_VUELTA); 
            System.out.println("Has dado una vuelta completa, recibes "+Valor.SUMA_VUELTA);
            if (this.jugador.getVueltas()%4==0) {
                this.CuartaVuelta=true;
            } 
        }
        newposition = (newposition-1)%40;
        Casilla newCasilla = casillas.get(newposition/10).get(newposition%10);
        newCasilla.anhadirAvatar(this);
        this.lugar = newCasilla;
    }

    public void moverAvatarPelota(ArrayList<ArrayList<Casilla>> casillas, int valorTirada, Jugador banca) {
        CuartaVuelta = false;
        int posicion = lugar.getPosicion();
    
        // Determinar dirección de movimiento
        boolean dir = valorTirada > 4; // Si el valor de la tirada es mayor a 4, avanzar, sino retroceder
    
        // Si se cruza la salida
        if (dir &&(posicion + valorTirada > 40)) {
            System.out.println("has dado una vuelta completa, recibes" + Valor.SUMA_VUELTA + ".");
            this.jugador.sumarVueltas();
            this.jugador.sumarFortuna(Valor.SUMA_VUELTA);
            if (jugador.getVueltas() % 4 == 0) {
                CuartaVuelta = true;
            }
        }
        if (dir){ // Si se avanza
            for(int i =5; i<=valorTirada; i+=2){
                int newposition = (posicion+i-1)%40;
                detenerse(newposition, banca, valorTirada,this.getLugar() ,casillas);
            }
            if (valorTirada % 2 == 0){
                detenerse((posicion+valorTirada-1)%40, banca, valorTirada, this.getLugar(), casillas);
            }
        }else{ // Si se retrocede
            for(int i = 1; i<=valorTirada; i+=2){
                int newposition = (posicion-i-1);
                newposition = newposition < 0 ? (40 + newposition)%40 : newposition %40;
                detenerse(newposition, banca, valorTirada, this.getLugar(), casillas);
            }
            if (valorTirada % 2 == 0){
                int newposition = (posicion-valorTirada-1);
                newposition = newposition < 0 ? (40 + newposition)%40 : newposition %40;
                detenerse(newposition, banca, valorTirada, this.getLugar(), casillas);
            }

        }

    }

    //Detenerse en casilla
    public void detenerse(int posicion, Jugador banca, int valorTirada, Casilla casillaActual,ArrayList<ArrayList<Casilla>> casillas) {
        casillaActual.eliminarAvatar(this);
        Casilla casillaFinal = casillas.get(posicion / 10).get(posicion % 10);
        System.out.println("El avatar " + this.getId() + " se detiene en la casilla " + casillaFinal.getNombre());

        // Evaluar la casilla para posibles interacciones
        if (!casillaFinal.evaluarCasilla(jugador, banca, valorTirada)) {
            System.out.println("El jugador " + jugador.getNombre() + " no puede pagar sus deudas!");
            return;
        }

        // Si la casilla es "Ir a Cárcel", mover al avatar a la cárcel y terminar el movimiento
        if (casillaFinal.getPosicion() == 31) {
            jugador.encarcelar(casillas);
            return;
        }
    

    // Al finalizar, actualizar la posición y registrar la visita
    casillaFinal.anhadirAvatar(this);
    //vecesCaidasCasilla[posicion]++;
    this.lugar = casillaFinal;
    }
    

    public void moverAvatarCoche(ArrayList<ArrayList<Casilla>> casillas, int valorTirada){

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
