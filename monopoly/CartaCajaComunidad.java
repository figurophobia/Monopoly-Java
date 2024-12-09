package monopoly;

import java.util.ArrayList;

import Excepciones.Ejecucion.DineroError;
import Excepciones.Ejecucion.InstanciaIncorrecta;
import Excepciones.MalUsoComando.EdificarSinPoder;
import partida.*;

public class CartaCajaComunidad extends Cartas{

    private ArrayList<String> acciones = new ArrayList<>();

    public CartaCajaComunidad() {

        acciones.add("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.");
        acciones.add("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
        acciones.add("Colócate en la casilla de Salida. Cobra la cantidad habitual.");
        acciones.add("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.");
        acciones.add("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.");
        acciones.add("Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador.");

    }

    @Override
    public void gestionCartas(Avatar actual, Tablero tablero, ArrayList<Jugador> jugadores) throws EdificarSinPoder, DineroError, InstanciaIncorrecta {

        String index = Juego.consola.leer("Elige una carta de comunidad [1-6]");
        //Pasamos el string a int
        int indice = Integer.parseInt(index);

        //Realizar acción
        realizarAccion(indice, actual, tablero, jugadores);

    }

    public void realizarAccion(int indice, Avatar actual, Tablero tablero, ArrayList<Jugador> jugadores) throws EdificarSinPoder, DineroError, InstanciaIncorrecta {
    
        Jugador jactual = actual.getJugador();
    
        Juego.consola.imprimir("Accion: "+acciones.get(indice-1));

        switch (acciones.get(indice-1)) {
            case "Paga 500000€ por un fin de semana en un balneario de 5 estrellas." -> {
                //pagar 500000€
                jactual.sumarFortuna(-500000);
                jactual.setPagoTasasEImpuestos(jactual.getPagoTasasEImpuestos()+500000);
                if (jactual.getFortuna()<0){
                    jactual.setEnDeuda(true);
                    jactual.setDineroPreDeuda(jactual.getFortuna()+500000);
                    jactual.setDeudor(null);
                    jactual.setCantidadDeuda(500000);
                }   Juego.consola.imprimir("El jugador "+jactual.getNombre()+" ha pagado 500000€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            case "Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual." -> //mover al jugador a la cárcel
                jactual.encarcelar(tablero.getPosiciones());
            case "Colócate en la casilla de Salida. Cobra la cantidad habitual." -> {
                //mover al jugador a la casilla de salida
                //cobrar la cantidad habitual
                //actual.setLugar(tablero.getCasilla(0));
                moverEspecial(tablero, 0, actual, jugadores.get(0));
                jactual.sumarFortuna(Valor.SUMA_VUELTA);
                jactual.sumarVueltas();
                jactual.setPasarPorCasillaDeSalida(jactual.getPasarPorCasillaDeSalida()+Valor.SUMA_VUELTA);
                if (jactual.getVueltas()%4==0 && jactual.getVueltas()!=0){
                    jactual.getAvatar().setCuartaVuelta(true);
                }   Juego.consola.imprimir("El jugador "+jactual.getNombre()+" ha cobrado "+Valor.SUMA_VUELTA+"€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            case "Tu compañía de Internet obtiene beneficios. Recibe 2000000€." -> {
                //cobrar 2000000€
                jactual.sumarFortuna(2000000);
                jactual.setPremiosInversionesOBote(jactual.getPremiosInversionesOBote()+2000000);
                Juego.consola.imprimir("El jugador "+jactual.getNombre()+" ha cobrado 2000000€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            case "Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14." -> {
                //pagar 1000000€
                jactual.sumarFortuna(-1000000);
                jactual.setPagoTasasEImpuestos(jactual.getPagoTasasEImpuestos()+1000000);
                if (jactual.getFortuna()<0){
                    jactual.setEnDeuda(true);
                    jactual.setDineroPreDeuda(jactual.getFortuna()+1000000);
                    jactual.setDeudor(null);
                    jactual.setCantidadDeuda(1000000);
                }   Juego.consola.imprimir("El jugador "+jactual.getNombre()+" ha pagado 1000000€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            case "Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador." -> {
                //paga 200000€ a cada jugador
                int jugadoresAPagar = jugadores.size()-2;
                float dineroAPagar = jugadoresAPagar*200000;
                jactual.sumarFortuna(-dineroAPagar);
                jactual.setPagoTasasEImpuestos(jactual.getPagoTasasEImpuestos()+dineroAPagar);
                if (jactual.getFortuna()<0){
                    jactual.setEnDeuda(true);
                    jactual.setDineroPreDeuda(jactual.getFortuna()+dineroAPagar);
                    jactual.setDeudor(null);
                    jactual.setCantidadDeuda(dineroAPagar);
                    return;
                }   for (Jugador jugador : jugadores) {
                    if(jugador != jactual && jugador!=jugadores.get(0)){
                        jugador.sumarFortuna(200000);
                    }
                }   Juego.consola.imprimir("El jugador "+jactual.getNombre()+" ha pagado 200000€ a cada jugador"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            default -> {
            }
        }
    }
    
}
