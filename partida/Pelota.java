package partida;


import java.util.ArrayList;

import Excepciones.MalUsoComando.AvanzarSinPoder;
import monopoly.*;


public class Pelota extends Avatar {
    private int valorTotalTirada = 0; //Valor total de la tirada de la pelota
    private int[] ArrayMovPelota = new int[5]; //Movimientos de la pelota
    private boolean primerMovPelota = true; //Indica si es el primer movimiento de la pelota

    public Pelota(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("Pelota", jugador, lugar, avCreados);
    }

    public int getValorTotalTirada() {
        return valorTotalTirada;
    }

    public void setValorTotalTirada(int valorTotalTirada) {
        this.valorTotalTirada = valorTotalTirada;
    }

    public void limpiarMovPelota(){
        for (int i = 0; i < ArrayMovPelota.length; i++) {
            ArrayMovPelota[i] = 0;
        }
        this.primerMovPelota = true;
    }
    
    public boolean puedeAvanzar() throws AvanzarSinPoder{
        if(this.getJugador().isEnDeuda()||this.getJugador().isEnCarcel()){
            throw new AvanzarSinPoder("No puedes avanzar, estás en deuda o en la cárcel");
        }
        if (!this.esMovAvanzado()){
            throw new AvanzarSinPoder("No puedes avanzar, el modo avanzado no está activado");
        }
        if (!this.getTipo().equals("Pelota")){
            throw new AvanzarSinPoder("No puedes avanzar, no eres una pelota");
        }
        if (this.nextPelota(false)==0){
            limpiarMovPelota();
            throw new AvanzarSinPoder("No puedes avanzar, no te quedan movimientos");

        }
        return true;
    }

    //Devolvemos el siguiente movimiento de la pelota, y lo eliminamos del array si borrar es true
    public int nextPelota(boolean borrar){
        for (int i = 0; i < ArrayMovPelota.length; i++) {
            if (ArrayMovPelota[i]!=0){
                int mov = ArrayMovPelota[i];
                if (borrar) ArrayMovPelota[i] = 0;
                return mov;
            }
        }
        return 0;
    }

    @Override
    public int moverEnAvanzado(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        
        this.setCuartaVuelta(false);
        int posicion = this.getLugar().getPosicion();
        int newposition = 0;
        Casilla casillaActual = this.getLugar();
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
            this.getJugador().sumarVueltas();
            this.getJugador().sumarFortuna(Valor.SUMA_VUELTA);
            this.getJugador().setPasarPorCasillaDeSalida(this.getJugador().getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);

            if (this.getJugador().getVueltas() % 4 == 0 && this.getJugador().getVueltas() != 0) {
                this.setCuartaVuelta(true);
            }
        }
        if (!dir && (posicion - valorTirada -1< 0) && this.getJugador().getVueltas()>=1) {
            this.getJugador().DevolverVuelta();
            Juego.consola.imprimir("Pasas por Salida al reves, pierdes "+Valor.SUMA_VUELTA+" € te quedan: "+this.getJugador().getFortuna());
            this.setCuartaVuelta(false);
        }
        
        if (dir) { // Si se avanza
            newposition = (posicion + valorTirada - 1) % 40;
            Casilla newCasilla = casillas.get(newposition / 10).get(newposition % 10);
            newCasilla.anhadirAvatar(this);
            this.setLugar(newCasilla);
            return newposition;
        } else { // Si se retrocede
            Juego.consola.imprimir("Retrocediendo");
            newposition = (posicion - valorTirada - 1);
            newposition = newposition < 0 ? (40 + newposition) % 40 : newposition % 40;
            Casilla newCasilla = casillas.get(newposition / 10).get(newposition % 10);
            newCasilla.anhadirAvatar(this);
            this.setLugar(newCasilla);
            return newposition;
            
        }
    }


}
