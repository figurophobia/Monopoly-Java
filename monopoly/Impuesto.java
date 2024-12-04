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
    @Override
    public String infoCasilla() { 
        StringBuilder info = new StringBuilder();
        info.append("{\n");
        info.append("tipo: ").append(this.getClass()).append(",\n");
        info.append("a pagar: ").append(this.impuesto).append("\n");
        info.append("jugadores: [");
        for (Avatar avatar : this.avatares) {
            info.append(avatar.getJugador().getNombre()).append(", ");
        }
        if (!this.avatares.isEmpty()) {
            info.setLength(info.length() - 2); // Eliminar la última coma y espacio
        }
        info.append("]\n");
        return info.toString();
    }

    


}
