package monopoly;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import partida.*;

public class Cartas {
    private ArrayList<String> accionesSuerte = new ArrayList<>(); //Acciones que se pueden realizar en la casilla
    private ArrayList<String> accionesCaja = new ArrayList<>(); //Acciones que se pueden realizar en la casilla
    
    
    public Cartas(){
        accionesSuerte.add("Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        accionesSuerte.add("Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
        accionesSuerte.add("Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€.");
        accionesSuerte.add("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        accionesSuerte.add("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
        accionesSuerte.add("¡Has ganado el bote de la lotería! Recibe 1000000€.");
        
        accionesCaja.add("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.");
        accionesCaja.add("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
        accionesCaja.add("Colócate en la casilla de Salida. Cobra la cantidad habitual.");
        accionesCaja.add("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.");
        accionesCaja.add("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.");
        accionesCaja.add("Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador.");
    }

    public void gestionCartas(Avatar actual, Tablero tablero, ArrayList<Jugador> jugadores){

        if (actual.getLugar().getTipo().equals("Suerte")){
            //Barajar las cartas
            //barajarCartas(accionesSuerte);
    
            //Elegir carta
            System.out.println("Elige una carta de suerte [1-6]");
            Scanner scanner = new Scanner(System.in);
            int indice = scanner.nextInt();
    
            //Realizar acción
            realizarAccion(indice, actual, tablero, jugadores);
        }
        else if(actual.getLugar().getNombre().equals("Caja")){
            //Barajar las cartas
            //barajarCartas(accionesCaja);
    
            //Elegir carta
            System.out.println("Elige una carta de comunidad [1-6]");
            Scanner scanner = new Scanner(System.in);
            int indice = scanner.nextInt();
    
            //Realizar acción
            realizarAccion(indice, actual, tablero, jugadores);
        }
    }
    
    public void barajarCartas(ArrayList<String> acciones){
        //desordenar vector de acciones
        Random rand = new Random();
        for (int i = 0; i < acciones.size(); i++) {                 //barajar las cartas
            int randomIndexToSwap = rand.nextInt(acciones.size());  //generar un número aleatorio
            String temp = acciones.get(randomIndexToSwap);          //intercambiar las cartas
            acciones.set(randomIndexToSwap, acciones.get(i));       
            acciones.set(i, temp);                          
        }
    }
    
    public void realizarAccion(int indice, Avatar actual, Tablero tablero, ArrayList<Jugador> jugadores){
    
        Jugador jactual = actual.getJugador();

        if((actual.getLugar().getTipo().equals("Suerte"))){
            System.out.println("Accion: "+accionesSuerte.get(indice-1));
            if(accionesSuerte.get(indice-1).equals("Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.")){
                //mover al jugador a la casilla de Transportes1
                if(actual.getLugar().getPosicion()>5){
                    jactual.sumarFortuna(Valor.SUMA_VUELTA);
                    jactual.sumarVueltas();
                    if (jactual.getVueltas()%4==0 && jactual.getVueltas()!=0){
                        jactual.getAvatar().setCuartaVuelta(true);
                    }

                    //si pasa por la casilla de salida, cobra la cantidad habitual
                }
                System.out.println("El jugador "+jactual.getNombre()+" ha cobrado "+Valor.SUMA_VUELTA+"€"+ " y ahora tiene "+jactual.getFortuna()+"€");
                //actual.setLugar(tablero.getCasilla(6));
                moverEspecial(tablero, 5, actual, jugadores.get(0));
            }
            else if(accionesSuerte.get(indice-1).equals("Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.")){
                //mover al jugador a la casilla de Solar15
                //actual.setLugar(tablero.getCasilla(27));
                moverEspecial(tablero, 26, actual, jugadores.get(0));
            }
            else if(accionesSuerte.get(indice-1).equals("Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€.")){
                //cobrar 500000€
                jactual.sumarFortuna(500000);
                jactual.setPremiosInversionesOBote(jactual.getPremiosInversionesOBote()+500000);
                System.out.println("El jugador "+jactual.getNombre()+" ha cobrado 500000€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            else if(accionesSuerte.get(indice-1).equals("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.")){
                //mover al jugador a la casilla de Solar3
                //si pasa por la casilla de salida, cobra la cantidad habitual
                if(actual.getLugar().getPosicion()>7){
                    jactual.sumarFortuna(Valor.SUMA_VUELTA);
                    jactual.sumarVueltas();
                    if (jactual.getVueltas()%4==0 && jactual.getVueltas()!=0){
                        jactual.getAvatar().setCuartaVuelta(true);
                    }
                    System.out.println("El jugador "+jactual.getNombre()+"da una vuelta, ha cobrado "+Valor.SUMA_VUELTA+"€"+ " y ahora tiene "+jactual.getFortuna()+"€");
                    //si pasa por la casilla de salida, cobra la cantidad habitual
                }
                moverEspecial(tablero, 6, actual, jugadores.get(0));
            }
            else if(accionesSuerte.get(indice-1).equals("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.")){
                //mover al jugador a la cárcel
                jactual.encarcelar(tablero.getPosiciones());
                System.out.println("El jugador "+jactual.getNombre()+" ha sido encarcelado");
            }
            else if(accionesSuerte.get(indice-1).equals("¡Has ganado el bote de la lotería! Recibe 1000000€.")){
                //cobrar 1000000€
                jactual.sumarFortuna(1000000);
                jactual.setPremiosInversionesOBote(jactual.getPremiosInversionesOBote()+1000000);
                System.out.println("El jugador "+jactual.getNombre()+" ha cobrado 1000000€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
        }
    
        else if(actual.getLugar().getNombre().equals("Caja")){
            System.out.println("Accion: "+accionesCaja.get(indice-1));

            if(accionesCaja.get(indice-1).equals("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.")){
                //pagar 500000€
                jactual.sumarFortuna(-500000);
                jactual.setPagoTasasEImpuestos(jactual.getPagoTasasEImpuestos()+500000);
                if (jactual.getFortuna()<0){
                    jactual.setEnDeuda(true);
                    jactual.setDineroPreDeuda(jactual.getFortuna()+500000);
                    jactual.setDeudor(null);
                    jactual.setCantidadDeuda(500000);
                }
                System.out.println("El jugador "+jactual.getNombre()+" ha pagado 500000€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            else if(accionesCaja.get(indice-1).equals("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.")){
                //mover al jugador a la cárcel
                jactual.encarcelar(tablero.getPosiciones());
            }
            else if(accionesCaja.get(indice-1).equals("Colócate en la casilla de Salida. Cobra la cantidad habitual.")){
                //mover al jugador a la casilla de salida
                //cobrar la cantidad habitual
                //actual.setLugar(tablero.getCasilla(0));
                moverEspecial(tablero, 0, actual, jugadores.get(0));
                jactual.sumarFortuna(Valor.SUMA_VUELTA);
                jactual.sumarVueltas();
                if (jactual.getVueltas()%4==0 && jactual.getVueltas()!=0){
                    jactual.getAvatar().setCuartaVuelta(true);
                }
                System.out.println("El jugador "+jactual.getNombre()+" ha cobrado "+Valor.SUMA_VUELTA+"€"+ " y ahora tiene "+jactual.getFortuna()+"€");


            }
            else if(accionesCaja.get(indice-1).equals("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.")){
                //cobrar 2000000€
                jactual.sumarFortuna(2000000);
                jactual.setPremiosInversionesOBote(jactual.getPremiosInversionesOBote()+2000000);
                System.out.println("El jugador "+jactual.getNombre()+" ha cobrado 2000000€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            else if(accionesCaja.get(indice-1).equals("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.")){
                //pagar 1000000€
                jactual.sumarFortuna(-1000000);
                jactual.setPagoTasasEImpuestos(jactual.getPagoTasasEImpuestos()+1000000);
                if (jactual.getFortuna()<0){
                    jactual.setEnDeuda(true);
                    jactual.setDineroPreDeuda(jactual.getFortuna()+1000000);
                    jactual.setDeudor(null);
                    jactual.setCantidadDeuda(1000000);
                }
                System.out.println("El jugador "+jactual.getNombre()+" ha pagado 1000000€"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
            else if(accionesCaja.get(indice-1).equals("Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador.")){
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
                }
                for (Jugador jugador : jugadores) {
                    if(jugador != jactual && jugador!=jugadores.get(0)){
                        jugador.sumarFortuna(200000);
                    }
                }
                System.out.println("El jugador "+jactual.getNombre()+" ha pagado 200000€ a cada jugador"+ " y ahora tiene "+jactual.getFortuna()+"€");
            }
        }
    }
    public void moverEspecial(Tablero tablero, int i, Avatar actual,Jugador banca){
        actual.getLugar().eliminarAvatar(actual);
        Casilla casilla = tablero.getCasilla(i);
        actual.setLugar(casilla);
        actual.getLugar().anhadirAvatar(actual);
        actual.getLugar().evaluarCasilla(actual.getJugador(), banca, 0);
    }

}
