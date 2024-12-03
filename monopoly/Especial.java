package monopoly;
import partida.*;

public class Especial extends Casilla{

    Jugador banca;

    public Especial(String nombre, int posicion, Jugador banca){
        super(nombre, posicion);
        this.banca = banca;
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

    @Override
    public String infoCasilla() { 
        StringBuilder info = new StringBuilder();
        info.append("{\n");
        info.append("tipo: ").append(this.getClass().getSimpleName()).append(",\n");
        
        switch (this.nombre) {
            case "Carcel" -> {
                info.append("nombre: ").append(this.nombre).append(",\n");
                info.append("salir:").append(Valor.PAGO_CARCEL).append("\n");
            }
            case "Parking" -> info.append("nombre: ").append(this.nombre).append(",\n")
                        .append("bote: ").append(banca.getBote()).append(",\n");
            case "Salida" -> info.append("nombre: ").append(this.nombre).append("\n");
            case "Ir a Cárcel" -> info.append("nombre: ").append(this.nombre).append("\n");
            default -> {
            }
        }
 
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

