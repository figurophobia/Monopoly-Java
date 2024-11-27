package monopoly;
import partida.*;

public class Especial extends Casilla{

    public Especial(String nombre, int posicion){
        super(nombre, posicion);
    }

    Consola consola = new ConsolaNormal();

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {

        if (this.nombre.equals("Parking")){
            jugador.setPremiosInversionesOBote(jugador.getPremiosInversionesOBote()+jugador.getBote());
            jugador.recibirBote(banca);
            System.out.println("Bote puesto a "+Valor.RED+banca.getBote()+"€"+Valor.RESET);
        }

        if (this.nombre.equals("IrCarcel"))
            consola.imprimirAdvertencia("Has sido enviado a la cárcel ");

        if (this.nombre.equals("Carcel"))
            consola.imprimirMensaje("Estás en la cárcel, de visita ");

        return true;
    }
    


}

