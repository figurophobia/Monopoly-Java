package monopoly;

import Excepciones.Ejecucion.DineroError;
import Excepciones.Ejecucion.InstanciaIncorrecta;
import Excepciones.MalUsoComando.EdificarSinPoder;
import java.util.ArrayList;
import partida.*;

public class Cartas {
    
    public Cartas(){
        
        }

    public void gestionCartas(Avatar actual, Tablero tablero, ArrayList<Jugador> jugadores) throws EdificarSinPoder, DineroError, InstanciaIncorrecta {
        
    }
    
    public void realizarAccion(int indice, Avatar actual, Tablero tablero, ArrayList<Jugador> jugadores) throws EdificarSinPoder, DineroError, InstanciaIncorrecta {
    
    }
    
    public void moverEspecial(Tablero tablero, int i, Avatar actual,Jugador banca) throws EdificarSinPoder, DineroError, InstanciaIncorrecta {
        actual.getLugar().eliminarAvatar(actual);
        Casilla casilla = tablero.getCasilla(i);
        actual.setLugar(casilla);
        actual.getLugar().anhadirAvatar(actual);
        actual.getLugar().evaluarCasilla(actual.getJugador(), banca, 0);
    }

}
