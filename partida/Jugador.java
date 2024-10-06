package partida;

import java.util.ArrayList;
import monopoly.*;


public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.

    //getters y setters: aun queda especificar los setters
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public float getFortuna() {
        return fortuna;
    }

    public void setFortuna(float fortuna) {
        this.fortuna = fortuna;
    }

    public float getGastos() {
        return gastos;
    }

    public void setGastos(float gastos) {
        this.gastos = gastos;
    }

    public boolean isEnCarcel() {
        return enCarcel;
    }

    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }

    public int getTiradasCarcel() {
        return tiradasCarcel;
    }

    public void setTiradasCarcel(int tiradasCarcel) {
        if (tiradasCarcel >= 0) this.tiradasCarcel = tiradasCarcel;
    }

    public int getVueltas() {
        return vueltas;
    }

    public void setVueltas(int vueltas) {
        if (vueltas >= 0) this.vueltas = vueltas;
    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(ArrayList<Casilla> propiedades) {
        this.propiedades = propiedades;
    }

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre = "banca";
        this.fortuna = Valor.FORTUNA_BANCA;
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
    */

    public Jugador(String nombre, String tipo, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;
        this.avatar = new Avatar(tipo, this, lugar, avCreados);
        this.fortuna = Valor.FORTUNA_INICIAL;
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
    }
    //Otros métodos:
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        if (!this.propiedades.contains(casilla)) {
            this.propiedades.add(casilla);
            
        }
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if (this.propiedades.contains(casilla)) {
            this.propiedades.remove(casilla);
            
        }
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    //Método para sumar numero de vueltas en 1
    public void sumarVueltas() {
        this.vueltas++;
    }

    //Método para describir a un jugador (sobreescribir toString), se mostraŕan todas las características de un jugador;
    /* Al estilo
     * $> describir jugador Maria
        {
        nombre: Maria,
        avatar: M,
        fortuna: 4000000,
        propiedades: [Valencia, Barcelona, Terrassa]
        hipotecas: [Palencia, Badajoz]
        edificios: [casa-1, casa-2, hotel-4]
        }
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("{\n");
        str.append("nombre: " + this.nombre + ",\n");
        str.append("avatar: " + this.avatar.getId() + ",\n");
        str.append("fortuna: " + this.fortuna + ",\n");
        str.append("propiedades: " + this.propiedades + ",\n");
        str.append("hipotecas: " + "PROVISIONAL" + ",\n");
        str.append("edificios: " + "PROVISIONAL" + "\n");
        str.append("}\n");
        return str.toString();
    }

    //Método para describir las propiedades de un jugador.
    public String describirPropiedades() {
        StringBuilder str = new StringBuilder();
        str.append("Propiedades de " + this.nombre + ":\n");
        for (Casilla casilla : this.propiedades) {
            str.append(casilla.getNombre() + "\n");
        }
        return str.toString();
    }

    /*Método para establecer al jugador en la cárcel. 
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        this.enCarcel = true;
        this.tiradasCarcel = 0;
        this.avatar.setLugar(pos.get(1).get(0));
    }


}
