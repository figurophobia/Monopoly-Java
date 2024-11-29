package partida;

import java.util.ArrayList;

import monopoly.Casilla;
import monopoly.Juego;
import monopoly.Valor;

public class Coche extends Avatar {
    private boolean compradoCoche = false; //Indica si el jugador ha comprado en su turno de ser coche
    private boolean cocheParado = false; //Indica si se detiene el movimiento del coche por 2 turnos
    private int turnosParado = 0; //Contador de turnos que lleva el coche parado
    private boolean ultimoTiroFueCoche = false; //Indica si es el ultimo tiro de los extras de coche
    private int tiros_extra= 0; //Contador de tiros de coche

    public Coche(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("Coche", jugador, lugar, avCreados);
    }

    public int getTiros_extra() {
        return tiros_extra;
    }

    public void setTiros_extra(int tiros_extra) {
        this.tiros_extra = tiros_extra;
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

        public int moverAvatarCoche(ArrayList<ArrayList<Casilla>> casillas, int valorTirada){
        this.setCuartaVuelta(false); //Al moverse, se reinicia el contador de cuarta vuelta, para no añadir valor cada vez que se juega en la cuarta vuelta


        int posicionactual=this.getLugar().getPosicion();
        this.getLugar().eliminarAvatar(this);
        int newposition;
        if (valorTirada>4) { //Si es mayor que 4, se mueve hacia adelante normalmente
        
            newposition = posicionactual+valorTirada;
            if (newposition>40) {
                this.getJugador().sumarVueltas();
                this.getJugador().sumarFortuna(Valor.SUMA_VUELTA);
                this.getJugador().setPasarPorCasillaDeSalida(this.getJugador().getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);
                Juego.consola.imprimir("Has dado una vuelta completa, recibes "+Valor.SUMA_VUELTA);
                if (this.getJugador().getVueltas()%4==0 && this.getJugador().getVueltas()!=0) {
                    this.setCuartaVuelta(true);
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
            if (newposition<0 && this.getJugador().getVueltas()>=1) { //Si se pasa por salida al revés
                this.getJugador().DevolverVuelta();
                Juego.consola.imprimir("Pasas por Salida al reves, pierdes "+Valor.SUMA_VUELTA+" € te quedan: "+this.getJugador().getFortuna());
                this.setCuartaVuelta(false);
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
        this.setLugar(newCasilla);
        return newposition;

    }
}
