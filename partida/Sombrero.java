package partida;

import java.util.ArrayList;

import monopoly.*;


public class Sombrero extends Avatar {
    public Sombrero(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("Sombrero", jugador, lugar, avCreados);
    }
    
    @Override
    public int moverEnAvanzado(ArrayList<ArrayList<Casilla>> casillas, int valorTirada){
        return moverEnAvanzado(casillas, valorTirada);
    }

}
