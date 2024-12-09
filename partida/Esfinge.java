package partida;

import java.util.ArrayList;

import monopoly.*;

public class Esfinge extends Avatar {
    public Esfinge(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super("Esfinge", jugador, lugar, avCreados);
    }    

    @Override
    public int moverEnAvanzado(ArrayList<ArrayList<Casilla>> casillas, int valorTirada){
        return moverEnAvanzado(casillas, valorTirada);
    }
}

