package monopoly;

import Excepciones.MalUsoComando.TratoIncompatible;
import partida.*;

public class Trato {
    static int numTratos = 0; //Static para poder llevar la cuenta de diferentes instancias de tratos
    private int id = -1; // Valor inicial, hasta que se haga comprobarTratoValido()
    private Jugador jugadorPropone; // Jugador que propone el trato
    private Jugador jugadorAcepta; // Jugador que recibe el trato
    private Propiedad SolarX;
    private Propiedad SolarY;
    private String info; // Información adicional del trato
    private float dinero;
    private int type;

    //Cambiar (SolarX, SolarY) Tipo1
    public Trato(Jugador jugadorPropone, Jugador jugadorAcepta, Propiedad SolarX, Propiedad SolarY) {
        this.jugadorPropone = jugadorPropone;
        this.jugadorAcepta = jugadorAcepta;
        this.SolarX = SolarX;
        this.SolarY = SolarY;
        this.dinero = 0;
        this.type = 1;
        this.info = Valor.RED + jugadorPropone.getNombre() + Valor.RESET + " ofrece " + Valor.BLUE + SolarX.getNombre() + Valor.RESET
                    + " a cambio de " + Valor.RED + SolarY.getNombre() + Valor.RESET + " de " + Valor.BLUE + jugadorAcepta.getNombre() + Valor.RESET;
    }

    //Cambiar (SolarX, dinero) Tipo2
    public Trato(Jugador jugadorPropone, Jugador jugadorAcepta, Propiedad SolarX, float dinero) {
        this.jugadorPropone = jugadorPropone;
        this.jugadorAcepta = jugadorAcepta;
        this.dinero = dinero;
        this.SolarX = SolarX;
        this.SolarY = null;
        this.type = 2;
        this.info = Valor.RED + jugadorPropone.getNombre() + Valor.RESET + " ofrece " + Valor.BLUE + SolarX.getNombre() + Valor.RESET
                    + " a cambio de " + Valor.YELLOW + dinero + "€" + Valor.RESET + " de " + Valor.RED + jugadorAcepta.getNombre() + Valor.RESET;
    }

    //Cambiar (dinero, SolarX) Tipo3
    public Trato(Jugador jugadorPropone, Jugador jugadorAcepta, float dinero, Propiedad SolarY) {
        this.jugadorPropone = jugadorPropone;
        this.jugadorAcepta = jugadorAcepta;
        this.dinero = dinero;
        this.SolarX = null;
        this.SolarY = SolarY;
        this.type = 3;
        this.info = Valor.RED + jugadorPropone.getNombre() + Valor.RESET + " ofrece " + Valor.YELLOW + dinero + "€" + Valor.RESET
                    + " a cambio de " + Valor.BLUE + SolarY.getNombre() + Valor.RESET + " de " + Valor.RED + jugadorAcepta.getNombre() + Valor.RESET;
    }


    //Cambiar (SolarX, SolarY y dinero) Tipo4
    public Trato(Jugador jugadorPropone, Jugador jugadorAcepta, Propiedad SolarX, Propiedad SolarY, float dinero) {
        this.jugadorPropone = jugadorPropone;
        this.jugadorAcepta = jugadorAcepta;
        this.dinero = dinero;
        this.SolarX = SolarX;
        this.SolarY = SolarY;
        this.type = 4;
        this.info = Valor.RED + jugadorPropone.getNombre() + Valor.RESET + " ofrece " + Valor.BLUE + SolarX.getNombre() + Valor.RESET
                    + " a cambio de " + Valor.RED + SolarY.getNombre() + Valor.RESET + " y " + Valor.YELLOW + dinero + "€" + Valor.RESET + " de " + Valor.BLUE + jugadorAcepta.getNombre() + Valor.RESET;}

    //Cambiar (SolarX y dinero, SolarY) Tipo5
    public Trato(Jugador jugadorPropone, Jugador jugadorAcepta, Propiedad SolarX, float dinero, Propiedad SolarY) {
        this.jugadorPropone = jugadorPropone;
        this.jugadorAcepta = jugadorAcepta;
        this.dinero = dinero;
        this.SolarX = SolarX;
        this.SolarY = SolarY;
        this.type = 5;
        this.info = Valor.RED + jugadorPropone.getNombre() + Valor.RESET + " ofrece " + Valor.BLUE + SolarX.getNombre() + Valor.RESET
                    + " y " + Valor.YELLOW + dinero + "€" + Valor.RESET + " a cambio de " + Valor.RED + SolarY.getNombre() + Valor.RESET + " de " + Valor.BLUE + jugadorAcepta.getNombre() + Valor.RESET;
    }

    //Comprueba si el trato es válido   
    public boolean valido(boolean changeid) throws TratoIncompatible {
        if (SolarX != null && SolarX.getDuenho() != jugadorPropone) {
            throw new TratoIncompatible("El jugador " + jugadorPropone.getNombre() + " no tiene la Propiedad " + SolarX.getNombre());
        }
        if (SolarY != null && SolarY.getDuenho() != jugadorAcepta) {
            throw new TratoIncompatible("El jugador " + jugadorAcepta.getNombre() + " no tiene la Propiedad " + SolarY.getNombre());
        }
        if(changeid) this.id = ++numTratos; // se le asigna el id solo al comprobar que es válido
        return true;
    }

    public void aceptarTrato() throws TratoIncompatible {
        if (this.valido(false)) {
            //Los tomaremos como fallos de ejecución estos, ya que se miran una vez comprobado si el trato era valido
            //Ademas se dejan hacer tratos independientemente de si hay el dinero o no
            if ((type == 2 || type == 5) && jugadorPropone.getFortuna() < dinero) {
                Juego.consola.imprimir("El jugador " + jugadorPropone.getNombre() + " no tiene suficiente dinero.");
                return;
            }
            if ((type == 3 || type == 4) && jugadorAcepta.getFortuna() < dinero) {
                Juego.consola.imprimir("El jugador " + jugadorAcepta.getNombre() + " no tiene suficiente dinero.");
                return;
            }

            //Recordar que si alguna propiedad está hipotecada hay que hacer una confirmación
            if(this.SolarX != null && this.SolarX.esHipotecada()){
                String respuesta =Juego.consola.leer("La propiedad "+this.SolarX.getNombre()+" está hipotecada, ¿quieres continuar con el trato? (s/n)");
                if (!respuesta.equals("s")) return;{
                    
                }
            }
    
            switch (type) {
                case 1:
                    jugadorPropone.cambiarPropiedad(SolarX, jugadorAcepta);
                    jugadorAcepta.cambiarPropiedad(SolarY, jugadorPropone);
                    break;
    
                case 2:
                    jugadorPropone.cambiarPropiedad(SolarX, jugadorAcepta);
                    jugadorAcepta.setFortuna(jugadorAcepta.getFortuna() - dinero);
                    break;
    
                case 3:
                    jugadorPropone.setFortuna(jugadorPropone.getFortuna() - dinero);
                    jugadorAcepta.cambiarPropiedad(SolarX, jugadorPropone);
                    break;
    
                case 4:
                    jugadorPropone.cambiarPropiedad(SolarX, jugadorAcepta);
                    jugadorAcepta.cambiarPropiedad(SolarY, jugadorPropone);
                    jugadorPropone.setFortuna(jugadorPropone.getFortuna() - dinero);
                    break;
    
                case 5:
                    jugadorPropone.cambiarPropiedad(SolarX, jugadorAcepta);
                    jugadorAcepta.cambiarPropiedad(SolarY, jugadorPropone);
                    jugadorPropone.setFortuna(jugadorPropone.getFortuna() - dinero);
                    break;
    
                default:
                    Juego.consola.imprimir("Tipo de trato no válido."); // No debería llegar aquí, pero por si acaso
                    break;
            }
            Juego.consola.imprimir("Trato aceptado: "+ this.info);
        } else{
            Juego.consola.imprimir("Trato no válido, alguna de las propiedades no pertenece al jugador correspondiente."); //Tampoco deberia, tira una excepcion antes
        }
    }

    /*
     * Formato:
     * {
            id: trato24
            jugadorAcepta: Manuel,
            jugadorPropone: Manuel,
            trato: info
            },
     * 
     */

    @Override
    public String toString() {
        return """
               {
               id: """ + id + ",\n" + "jugadorAcepta: " + jugadorAcepta.getNombre() + ",\n"
                + "jugadorPropone: " + jugadorPropone.getNombre() + ",\n" + "trato: " + info + "\n}";

    }

    public Jugador getjugadorPropone() {
        return jugadorPropone;
    }

    public int getid() {
        return id;
    }

    public Jugador getjugadorAcepta() {
        return jugadorAcepta;
    }

}
