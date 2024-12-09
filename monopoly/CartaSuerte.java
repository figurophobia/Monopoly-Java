package monopoly;

import java.util.ArrayList;

import Excepciones.Ejecucion.DineroError;
import Excepciones.Ejecucion.InstanciaIncorrecta;
import Excepciones.MalUsoComando.EdificarSinPoder;
import partida.*;

public class CartaSuerte extends Cartas {

    private ArrayList<String> acciones = new ArrayList<>();

    public CartaSuerte() {

        acciones.add("Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        acciones.add("Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
        acciones.add("Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€.");
        acciones.add("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        acciones.add("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
        acciones.add("¡Has ganado el bote de la lotería! Recibe 1000000€.");

    }

    @Override
    public void gestionCartas(Avatar actual, Tablero tablero, ArrayList<Jugador> jugadores) throws EdificarSinPoder, DineroError, InstanciaIncorrecta {

        String index = Juego.consola.leer("Elige una carta de suerte [1-6]");
        //Pasamos el string a int
        int indice = Integer.parseInt(index);

        //Realizar acción
        realizarAccion(indice, actual, tablero, jugadores);

    }

    @Override
    public void realizarAccion(int indice, Avatar actual, Tablero tablero, ArrayList<Jugador> jugadores) throws EdificarSinPoder, DineroError, InstanciaIncorrecta {
    
        Jugador jactual = actual.getJugador();


        System.out.println("Accion: "+acciones.get(indice-1));

        switch (acciones.get(indice-1)) {
            case "Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual." -> {
                //mover al jugador a la casilla de Transportes1
                if(actual.getLugar().getPosicion()>5){
                    jactual.sumarFortuna(Valor.SUMA_VUELTA);
                    jactual.sumarVueltas();
                    jactual.setPasarPorCasillaDeSalida(jactual.getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);
                    if (jactual.getVueltas()%4==0 && jactual.getVueltas()!=0){
                        jactual.getAvatar().setCuartaVuelta(true);
                    }
                    
                    //si pasa por la casilla de salida, cobra la cantidad habitual
                }   Juego.consola.imprimir("El jugador "+jactual.getNombre()+" ha cobrado "+Valor.SUMA_VUELTA+"€"+ " y ahora tiene "+jactual.getFortuna()+"€");
                //actual.setLugar(tablero.getCasilla(6));
                moverEspecial(tablero, 5, actual, jugadores.get(0));
            }
            case "Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual." -> //mover al jugador a la casilla de Solar15
                //actual.setLugar(tablero.getCasilla(27));
                moverEspecial(tablero, 26, actual, jugadores.get(0));
            case "Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€." -> {
                //cobrar 500000€
                jactual.sumarFortuna(500000);
                jactual.setPremiosInversionesOBote(jactual.getPremiosInversionesOBote()+500000);
                Juego.consola.imprimir("El jugador "+jactual.getNombre()+" ha cobrado 500000€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            case "Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual." -> {
                //mover al jugador a la casilla de Solar3
                //si pasa por la casilla de salida, cobra la cantidad habitual
                if(actual.getLugar().getPosicion()>7){
                    jactual.sumarFortuna(Valor.SUMA_VUELTA);
                    jactual.sumarVueltas();
                    jactual.setPasarPorCasillaDeSalida(jactual.getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);
                    if (jactual.getVueltas()%4==0 && jactual.getVueltas()!=0){
                        jactual.getAvatar().setCuartaVuelta(true);
                    }
                    Juego.consola.imprimir("El jugador "+jactual.getNombre()+"da una vuelta, ha cobrado "+Valor.SUMA_VUELTA+"€"+ " y ahora tiene "+jactual.getFortuna()+"€");
                    //si pasa por la casilla de salida, cobra la cantidad habitual
                }   moverEspecial(tablero, 6, actual, jugadores.get(0));
            }
            case "Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual." -> {
                //mover al jugador a la cárcel
                jactual.encarcelar(tablero.getPosiciones());
                Juego.consola.imprimir("El jugador "+jactual.getNombre()+" ha sido encarcelado");
            }
            case "¡Has ganado el bote de la lotería! Recibe 1000000€." -> {
                //cobrar 1000000€
                jactual.sumarFortuna(1000000);
                jactual.setPremiosInversionesOBote(jactual.getPremiosInversionesOBote()+1000000);
                Juego.consola.imprimir("El jugador "+jactual.getNombre()+" ha cobrado 1000000€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            default -> {
            }
        }
        
    }
    
}
