package partida;

import java.util.ArrayList;
import java.util.Random;

import Excepciones.MalUsoComando.AvanzarSinPoder;
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
        else Juego.consola.imprimir("Id inválido");
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
            //Juego.consola.imprimir("Tipo de avatar no válido, usa Sombrero, Esfinge, Pelota o Coche");
            return false;
        }
    }
    public void setTipo(String tipo) {
        if (TipoValido(tipo)) {
            this.tipo = tipo; // Asigna el valor si es válido
        }    
        else Juego.consola.imprimir("Tipo no válido");
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
    
    public void setCuartaVuelta(boolean cuartaVuelta) {
        CuartaVuelta = cuartaVuelta;
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
            this.movAvanzado=false;
            Juego.consola.imprimir("Modo avanzado desactivado");               
        }
        else{
            this.movAvanzado=true;
            Juego.consola.imprimir("Modo avanzado activado");
        }
    }

    // Método para mover un avatar. Recibe un valor de tirada y un arraylist de casillas.
    public int moverEnBasico(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        this.CuartaVuelta = false; //Al moverse, se reinicia el contador de cuarta vuelta, para no añadir valor cada vez que se juega en la cuarta vuelta


        int posicionactual=this.lugar.getPosicion();
        this.lugar.eliminarAvatar(this);
        int newposition = posicionactual+valorTirada;
        if (newposition>40) {
            this.jugador.sumarVueltas();
            this.jugador.sumarFortuna(Valor.SUMA_VUELTA);
            this.jugador.setPasarPorCasillaDeSalida(this.jugador.getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);
            Juego.consola.imprimir("Has dado una vuelta completa, recibes "+Valor.SUMA_VUELTA);
            if (this.jugador.getVueltas()%4==0  && this.jugador.getVueltas()!=0) {
                Juego.consola.imprimir("Vuelta múltiplo de 4");
                this.CuartaVuelta=true;
            } 
        }
        newposition = (newposition-1)%40;
        Casilla newCasilla = casillas.get(newposition/10).get(newposition%10);
        newCasilla.anhadirAvatar(this);
        this.lugar = newCasilla;
        return newposition;
    }


    




    public int moverEnAvanzado(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) { //Clase a implementar en cada avatar
        return moverEnBasico(casillas, valorTirada);
    }

/*
    public int moverAvatarPelota(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        CuartaVuelta = false;
        int posicion = lugar.getPosicion();
        int newposition = 0;
        Casilla casillaActual = lugar;
        casillaActual.eliminarAvatar(this);
        // Determinar dirección de movimiento
        boolean dir = valorTirada>=0; // SI la tirada es positiva, avanzar, sino retroceder
        
        if (this.primerMovPelota) {
            int i = 0;
            this.valorTotalTirada = valorTirada; //Guardamos el valor total de la tirada, para el evaluar casilla
            if (valorTirada > 4) {
                ArrayMovPelota[i++] = 5;
                valorTirada -= 5;
                while (valorTirada > 1) {
                    ArrayMovPelota[i++] = 2;
                    valorTirada -= 2;
                }
            } else {
                ArrayMovPelota[i++] = -1;
                valorTirada = -valorTirada + 1;
                while (valorTirada < -1) {
                    ArrayMovPelota[i++] = -2;
                    valorTirada += 2;
                }
            }
        
            if (valorTirada != 0) {
                ArrayMovPelota[i] = valorTirada;
            }

            valorTirada = this.nextPelota(true);
            this.primerMovPelota = false;

        }
        valorTirada = Math.abs(valorTirada);  // Valor absoluto de la tirada

        // Si se cruza la salida
        if (dir && (posicion + valorTirada > 40)) {
            Juego.consola.imprimir("has dado una vuelta completa, recibes " + Valor.SUMA_VUELTA + ".");
            this.jugador.sumarVueltas();
            this.jugador.sumarFortuna(Valor.SUMA_VUELTA);
            this.jugador.setPasarPorCasillaDeSalida(this.jugador.getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);

            if (jugador.getVueltas() % 4 == 0 && jugador.getVueltas() != 0) {
                CuartaVuelta = true;
            }
        }
        if (!dir && (posicion - valorTirada -1< 0) && this.getJugador().getVueltas()>=1) {
            this.jugador.DevolverVuelta();
            Juego.consola.imprimir("Pasas por Salida al reves, pierdes "+Valor.SUMA_VUELTA+" € te quedan: "+jugador.getFortuna());
            this.CuartaVuelta=false;
        }
        
        if (dir) { // Si se avanza
            newposition = (posicion + valorTirada - 1) % 40;
            Casilla newCasilla = casillas.get(newposition / 10).get(newposition % 10);
            newCasilla.anhadirAvatar(this);
            this.lugar = newCasilla;
            return newposition;
        } else { // Si se retrocede
            Juego.consola.imprimir("Retrocediendo");
            newposition = (posicion - valorTirada - 1);
            newposition = newposition < 0 ? (40 + newposition) % 40 : newposition % 40;
            Casilla newCasilla = casillas.get(newposition / 10).get(newposition % 10);
            newCasilla.anhadirAvatar(this);
            this.lugar = newCasilla;
            return newposition;
            
        }
    }
*/
/*
    public int moverAvatarPelota(ArrayList<ArrayList<Casilla>> casillas, int valorTirada, Jugador banca, Cartas cartas, Tablero tablero, ArrayList<Jugador> jugadores) {
        CuartaVuelta = false;
        int posicion = lugar.getPosicion();
        int newposition = 0;
    
        // Determinar dirección de movimiento
        boolean dir = valorTirada > 4; // Si el valor de la tirada es mayor a 4, avanzar, sino retroceder
    
        // Si se cruza la salida
        if (dir && (posicion + valorTirada > 40)) {
            Juego.consola.imprimir("has dado una vuelta completa, recibes " + Valor.SUMA_VUELTA + ".");
            this.jugador.sumarVueltas();
            this.jugador.sumarFortuna(Valor.SUMA_VUELTA);
            this.jugador.setPasarPorCasillaDeSalida(this.jugador.getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);

            if (jugador.getVueltas() % 4 == 0 && jugador.getVueltas() != 0) {
                CuartaVuelta = true;
            }
        }
        if (!dir && (posicion - valorTirada -1< 0) && this.getJugador().getVueltas()>=1) {
            this.jugador.DevolverVuelta();
            Juego.consola.imprimir("Pasas por Salida al reves, pierdes "+Valor.SUMA_VUELTA+" € te quedan: "+jugador.getFortuna());
            this.CuartaVuelta=false;
        }
        
        int valor_avanzado_previo=0;
        if (dir) { // Si se avanza
            for (int i = 5; i <= valorTirada; i += 2) {
                posicion= this.getLugar().getPosicion();
                newposition = (posicion + i - valor_avanzado_previo - 1) % 40;
                detenerse(newposition, banca, valorTirada, this.getLugar(), casillas, cartas, tablero, jugadores);
                valor_avanzado_previo=i;
                if (casillas.get(newposition / 10).get(newposition % 10).getNombre().equals("IrCarcel") || this.jugador.isEnDeuda()) {
                    return newposition;
                }
            }
            if (valorTirada % 2 == 0) {
                posicion= this.getLugar().getPosicion();
                newposition = (posicion + valorTirada -valor_avanzado_previo - 1) % 40;
                detenerse(newposition, banca, valorTirada, this.getLugar(), casillas, cartas, tablero, jugadores);
                if (casillas.get(newposition / 10).get(newposition % 10).getNombre().equals("IrCarcel") || this.jugador.isEnDeuda()) {
                    return newposition;
                }
            }
        } else { // Si se retrocede
            for (int i = 1; i <= valorTirada; i += 2) {
                posicion= this.getLugar().getPosicion();
                newposition = (posicion - i + valor_avanzado_previo- 1);
                newposition = newposition < 0 ? (40 + newposition) % 40 : newposition % 40;
                detenerse(newposition, banca, valorTirada, this.getLugar(), casillas, cartas, tablero, jugadores);
                valor_avanzado_previo=i;
                if (casillas.get(newposition / 10).get(newposition % 10).getNombre().equals("IrCarcel") || this.jugador.isEnDeuda()) {
                    return newposition;
                }
            }
            if (valorTirada % 2 == 0) {
                posicion= this.getLugar().getPosicion();
                newposition = (posicion - valorTirada +valor_avanzado_previo - 1);
                newposition = newposition < 0 ? (40 + newposition) % 40 : newposition % 40;
                detenerse(newposition, banca, valorTirada, this.getLugar(), casillas, cartas, tablero, jugadores);
                if (casillas.get(newposition / 10).get(newposition % 10).getNombre().equals("IrCarcel") || this.jugador.isEnDeuda()) {
                    return newposition;
                }
            }
        }
        return newposition;
    }
    */
    /*
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
                Juego.consola.imprimir("Has dado una vuelta completa, recibes "+Valor.SUMA_VUELTA);
                if (this.jugador.getVueltas()%4==0 && this.jugador.getVueltas()!=0) {
                    this.CuartaVuelta=true;
                } 
            }
            newposition = (newposition-1)%40;
            //Sumar movimiento extra: No cuentan dobles
            Juego.consola.imprimir("Tiros extra: "+tiros_extra);
            tiros_extra++;
            if (tiros_extra == 3){
                Juego.consola.imprimir("En el siguiente tiro se aplican los dobles");
                ultimoTiroFueCoche = true;
            }

        }
        else{ // Si es menor que 4 retrocede
            newposition = posicionactual-valorTirada-1;
            if (newposition<0 && this.jugador.getVueltas()>=1) { //Si se pasa por salida al revés
                this.jugador.DevolverVuelta();
                Juego.consola.imprimir("Pasas por Salida al reves, pierdes "+Valor.SUMA_VUELTA+" € te quedan: "+jugador.getFortuna());
                this.CuartaVuelta=false;
            }
            newposition = newposition < 0 ? (40 + newposition)%40 : newposition %40;
            //Paramos el coche por 2 turnos
            Juego.consola.imprimir("El coche se ha detenido por 2 turnos");
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
    public void detenerse(int posicion, Jugador banca, int valorTirada, Casilla casillaActual,ArrayList<ArrayList<Casilla>> casillas, Cartas cartas, Tablero tablero, ArrayList<Jugador> jugadores) {
        casillaActual.eliminarAvatar(this);
        Casilla casillaFinal = casillas.get(posicion / 10).get(posicion % 10);
        Juego.consola.imprimir("El avatar " + this.getId() + " para en la casilla " + casillaFinal.getNombre());
        // Al finalizar, actualizar la posición y registrar la visita
        casillaFinal.anhadirAvatar(this);
        this.lugar = casillaFinal;
        if (casillaFinal instanceof Accion) {
            cartas.gestionCartas(this, tablero, jugadores);
        }
        
        // Evaluar la casilla para posibles interacciones
        if (!casillaFinal.evaluarCasilla(jugador, banca, valorTirada)) {
            Juego.consola.imprimir("El jugador " + jugador.getNombre() + " no puede pagar sus deudas!");
            return;
        }

        // Si la casilla es "Ir a Cárcel", mover al avatar a la cárcel y terminar el movimiento
        if (casillaFinal.getPosicion() == 31) {
            Juego.consola.imprimir("El jugador " + jugador.getNombre() + " ha sido enviado a la cárcel.");
            jugador.encarcelar(casillas);
            return;
        }
    


    }
    
    */


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
