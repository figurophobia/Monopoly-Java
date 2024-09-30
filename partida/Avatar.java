package partida;

import monopoly.*;

import java.util.ArrayList;
import java.util.Random;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id.matches("[A-Z]")) this.id=id;
        else System.out.println("Id inválido");
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if ("Sombrero".equals(tipo) || "Esfinge".equals(tipo) || 
            "Pelota".equals(tipo) || "Coche".equals(tipo)) {
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

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        //!hay que hacer funciones para devolver la casilla a partir de la posicion y devolver la posicion a partir de la casilla --------->
        //1º recuperar la posicion de la casilla actual para poder calcular la casilla nueva; hay que eliminar el avatar de la casilla actual y añadirlo en la nueva

        int posicionactual=this.lugar.getPosicion();
        this.lugar.eliminarAvatar(this);
        posicionactual+=valorTirada;
        this.lugar=Casilla.Casillaporpos(casillas,posicionactual);
        casillas.anhadirAvatar(this);
    }

    /*Método que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        // id de una letra
        Random idrand = new Random(); //instancai de random
        char idr;
        boolean idvalido=false; //se pone a false para que haga almenos un while se puede poner un do while pero bueno
        idr='A'; //inicializa la variable con A para que no moleste diciendo que variable no inicializada o nsq

        while(idvalido==false){
            idvalido=true; // de primeras pone que es un id valido
            idr=(char) (idrand.nextInt(26)+65); //negera un numero random de 0 a 26 (letras existentes) y le suma 65 (codigo ascii de a) lo pasa a char
            for (Avatar avatar : avCreados) { //for each que recorre la lista de avatares
                if (Character.toString(idr).equals(avatar.getId())){ //si avatar id es igual a idr idvalido false y vuelve a generar un 
                    idvalido=false;
                }
                
            }
            
        }
        this.id=Character.toString(idr);
    }
}
