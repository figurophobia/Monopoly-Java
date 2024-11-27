package monopoly;
import partida.*;

public class Impuesto extends Casilla{

    private float impuesto;

    public Impuesto(String nombre, int posicion, float impuesto){
        super(nombre, posicion);

        this.impuesto = impuesto;
    }

    Consola consola = new ConsolaNormal();

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {

        float fortunaJugador = jugador.getFortuna();

        if (!jugador.pagarImpuesto(impuesto, banca))
            return false;
        
        consola.imprimirAdvertencia("Has pagado " + impuesto + "€ a la banca por caer en la casilla de impuesto ");
        consola.imprimirMensaje("Tu fortuna restante es: " + fortunaJugador);
        banca.sumarBote(impuesto);

        return true;
    }
    


}
