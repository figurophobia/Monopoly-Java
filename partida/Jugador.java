package partida;

import java.util.ArrayList;
import java.util.HashMap;
import monopoly.*;

public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private float bote; //Bote que se va acumulando en la partida por la banca en impuesto, comunidad...
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.
    private HashMap<Casilla,Integer> numeroVisitas; //Numero de veces que ha visitado una casilla
    private ArrayList<Edificacion> edificaciones; //Edificaciones que posee el jugador.
    private int VecesDados=0; //Veces que ha tirado los dados

    private float dineroInvertido = 0; //Dinero invertido en propiedades.
    private float pagoTasasEImpuestos = 0; //Dinero pagado en tasas e impuestos.
    private float pagoDeAlquileres=0; //Dinero pagado en alquileres.
    private float cobroDeAlquileres=0; //Dinero cobrado en alquileres.
    private float pasarPorCasillaDeSalida=0; //Veces que ha pasado por la casilla de salida.
    private float premiosInversionesOBote=0; //Dinero ganado en premios, inversiones o bote.
    private int vecesEnLaCarcel=0; //Veces que ha estado en la carcel.

    private boolean enBancarrota = false; //Bandera para saber si el jugador está en bancarrota.
    private Jugador deudor = null; //Jugador al que se le debe dinero.

    private boolean CochePuedeComprar = true; //Bandera para saber si el coche puede comprar

    /*
     * dineroInvertido: 8500000, //Compra de propiedades solares,servicios y transporte, edificaciones
        pagoTasasEImpuestos: 4500000, //Impuestos, salir de la carcel, cartas de comunidad 1,5 y 6
        pagoDeAlquileres: 3400000, //Pago de alquileres a otros jugadores
        cobroDeAlquileres: 2300000, //Cobro de alquileres a otros jugadores
        pasarPorCasillaDeSalida: 16000000, //Dinero ganado por pasar por la casilla de salida
        premiosInversionesOBote: 1000000, //Caer en Parking, carta de suerte 3 y 6, carta comunidad 4
        vecesEnLaCarcel:4 //Veces que ha estado en la carcel
     */


    //getters y setters: aun queda especificar los setters
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public float getFortuna() {
        return fortuna;
    }

    public void setFortuna(float fortuna) {
        this.fortuna = fortuna;
    }

    public float getGastos() {
        return gastos;
    }

    public void setGastos(float gastos) {
        this.gastos = gastos;
    }

    public boolean isEnCarcel() {
        return enCarcel;
    }

    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }

    public int getTiradasCarcel() {
        return tiradasCarcel;
    }

    public void setTiradasCarcel(int tiradasCarcel) {
        if (tiradasCarcel >= 0) this.tiradasCarcel = tiradasCarcel;
    }

    public int getVueltas() {
        return vueltas;
    }

    public void setVueltas(int vueltas) {
        if (vueltas >= 0) this.vueltas = vueltas;
    }
    
    public float getBote() {
        return bote;
    }

    public void sumarBote(float bote) {
        this.bote += bote;
    }

    public void ReiniciarBote() {
        this.bote = 0;
    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(ArrayList<Casilla> propiedades) {
        this.propiedades = propiedades;
    }

    public ArrayList<Edificacion> getEdificaciones() {
        return edificaciones;
    }

    public void setEdificaciones(ArrayList<Edificacion> edificaciones) {
        this.edificaciones = edificaciones;
    }

    public float getDineroInvertido() {
        return dineroInvertido;
    }
    
    public void setDineroInvertido(float dineroInvertido) {
        this.dineroInvertido = dineroInvertido;
    }
    
    public float getPagoTasasEImpuestos() {
        return this.pagoTasasEImpuestos;
    }
    
    public void setPagoTasasEImpuestos(float pagoTasasEImpuestos) {
        this.pagoTasasEImpuestos = pagoTasasEImpuestos;
    }
    
    public float getPagoDeAlquileres() {
        return pagoDeAlquileres;
    }
    
    public void setPagoDeAlquileres(float pagoDeAlquileres) {
        this.pagoDeAlquileres = pagoDeAlquileres;
    }
    
    public float getCobroDeAlquileres() {
        return cobroDeAlquileres;
    }
    
    public void setCobroDeAlquileres(float cobroDeAlquileres) {
        this.cobroDeAlquileres = cobroDeAlquileres;
    }
    
    public float getPasarPorCasillaDeSalida() {
        return pasarPorCasillaDeSalida;
    }
    
    public void setPasarPorCasillaDeSalida(float pasarPorCasillaDeSalida) {
        this.pasarPorCasillaDeSalida = pasarPorCasillaDeSalida;
    }
    
    public float getPremiosInversionesOBote() {
        return premiosInversionesOBote;
    }
    
    public void setPremiosInversionesOBote(float premiosInversionesOBote) {
        this.premiosInversionesOBote = premiosInversionesOBote;
    }

    public int getVecesEnLaCarcel() {
        return vecesEnLaCarcel;
    }

    public void setVecesEnLaCarcel(int vecesEnLaCarcel) {
        this.vecesEnLaCarcel = vecesEnLaCarcel;
    }

    public int getVecesDados() {
        return VecesDados;
    }

    public void setVecesDados(int VecesDados) {
        this.VecesDados = VecesDados;
    }

    public boolean isEnBancarrota() {
        return enBancarrota;
    }

    public Jugador getDeudor() {
        return deudor;
    }

    public void setDeudor(Jugador deudor) {
        this.deudor = deudor;
    }

    public boolean getCochePuedeComprar() {
        return CochePuedeComprar;
    }

    public void setCochePuedeComprar(boolean CochePuedeComprar) {
        this.CochePuedeComprar = CochePuedeComprar;
    }

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre = "banca";
        this.fortuna = Valor.FORTUNA_BANCA;
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
    */

    public Jugador(String nombre, String tipo, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;
        this.avatar = new Avatar(tipo, this, lugar, avCreados);
        this.fortuna = Valor.FORTUNA_INICIAL;
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
        this.numeroVisitas = new HashMap<>();
    }
    //Otros métodos:
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        if (!this.propiedades.contains(casilla)) {
            this.propiedades.add(casilla);
        }
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if (this.propiedades.contains(casilla)) {
            this.propiedades.remove(casilla);
            
        }
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    //Método para sumar numero de vueltas en 1
    public void sumarVueltas() {
        this.vueltas++;
    }

    //Método para describir a un jugador (sobreescribir toString), se mostraŕan todas las características de un jugador;
    /* Al estilo
     * $> describir jugador Maria
        {
        nombre: Maria,
        avatar: M,
        fortuna: 4000000,
        propiedades: [Valencia, Barcelona, Terrassa]
        hipotecas: [Palencia, Badajoz]
        edificios: [casa-1, casa-2, hotel-4]
        }
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("{\n");
        str.append("nombre: ").append(this.nombre).append(",\n");
        str.append("avatar: ").append(avatar != null ? avatar.getId() : "null").append(",\n");
        str.append("fortuna: ").append(this.fortuna).append(",\n");
        str.append("propiedades: " + Valor.BLUE).append(this.describirPropiedadesSinHipoteca()).append(Valor.RESET+"\n");
        str.append("hipotecas: " + Valor.BLUE).append(this.describirHipotecas()).append(Valor.RESET+"\n");

        boolean hayCasas = false; // Bandera para rastrear si hay casas
        for (Casilla casilla : propiedades) {
            int num = casilla.getEdificaciones().getOrDefault("casa", new ArrayList<>()).size();
            if (num != 0) {
                if (!hayCasas) {
                    str.append("Casas:"); // Imprimir "Casas:" solo una vez
                    hayCasas = true;
                }
                String resultado = (num == 1) ? " casa en " : " casas en ";
                str.append("\n- ").append(Valor.BLUE).append(num).append(Valor.RESET).append(resultado).append(Valor.BLUE).append(casilla.getNombre()).append(Valor.RESET);
                str.append("\n");
            }
        }
        
        boolean hayHoteles = false; // Bandera para rastrear si hay casas
        for (Casilla casilla : propiedades) {
            int num = casilla.getEdificaciones().getOrDefault("hotel", new ArrayList<>()).size();
            if (num != 0) {
                if (!hayHoteles) {
                    str.append("Hoteles:"); // Imprimir "Casas:" solo una vez
                    hayHoteles = true;
                }
                String resultado = (num == 1) ? " hotel en " : " hoteles en ";
                str.append("\n- ").append(Valor.BLUE).append(num).append(Valor.RESET).append(resultado).append(Valor.BLUE).append(casilla.getNombre()).append(Valor.RESET);
                str.append("\n");
            }
        }
        
        boolean hayPiscinas = false; // Bandera para rastrear si hay casas
        for (Casilla casilla : propiedades) {
            int num = casilla.getEdificaciones().getOrDefault("piscina", new ArrayList<>()).size();
            if (num != 0) {
                if (!hayPiscinas) {
                    str.append("Piscinas:"); // Imprimir "Casas:" solo una vez
                    hayPiscinas = true;
                }
                String resultado = (num == 1) ? " piscina en " : " piscinas en ";
                str.append("\n- ").append(Valor.BLUE).append(num).append(Valor.RESET).append(resultado).append(Valor.BLUE).append(casilla.getNombre()).append(Valor.RESET);
                str.append("\n");
            }
        }
        boolean hayPistas = false; // Bandera para rastrear si hay casas
        for (Casilla casilla : propiedades) {
            int num = casilla.getEdificaciones().getOrDefault("pista", new ArrayList<>()).size();
            if (num != 0) {
                if (!hayPistas) {
                    str.append("Pistas:"); // Imprimir "Casas:" solo una vez
                    hayPistas = true;
                }
                String resultado = (num == 1) ? " pista en " : " pistas en ";
                str.append("\n- ").append(Valor.BLUE).append(num).append(Valor.RESET).append(resultado).append(Valor.BLUE).append(casilla.getNombre()).append(Valor.RESET);
                str.append("\n");
            }
        }

        str.append("}\n");
        return str.toString();
    }

    //Método para describir las propiedades de un jugador.
    public String describirPropiedadesSinHipoteca() {
        StringBuilder str = new StringBuilder();
        for (Casilla casilla : this.propiedades) {
            if (!casilla.isHipotecada()){
                str.append(casilla.getNombre() + ", ");
            }
        }
        return str.toString();
    }

    public String describirHipotecas(){
        StringBuilder str = new StringBuilder();
        for (Casilla casilla : this.propiedades) {
            if (casilla.isHipotecada()) {
                str.append(casilla.getNombre() + ", ");
            }
        }
        return str.toString();
    }

    /*Método para establecer al jugador en la cárcel. 
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        this.avatar.getLugar().eliminarAvatar(this.avatar);
        this.enCarcel = true;
        Casilla carcel = pos.get(1).get(0);
        this.avatar.setLugar(carcel);
        this.tiradasCarcel = 0;
        this.avatar.getLugar().anhadirAvatar(this.avatar);
        this.vecesEnLaCarcel++;
    }
//this.avatares.get(turno).moverAvatar(this.tablero.getPosiciones(), total);

    //Método para recibir el bote de la banca al caer en Parking.
    public void recibirBote(Jugador banca){
        float cantidad = banca.getBote();
        this.sumarFortuna(cantidad);
        banca.ReiniciarBote();
        System.out.println("El jugador "+this.getNombre()+" ha recibido el bote de " +cantidad+"€");
    }

    //Método para saber cuántos transportes tiene un jugador.
    public int numTransportes(){
        int num = 0;
        for (Casilla casilla : this.getPropiedades()) {
            if (casilla.getTipo().equals("Transporte")) {
                num++;
            }
        }
        return num;
    }

    //Método para saber cuántos servicios tiene un jugador.
    public int numServicios(){
        int num = 0;
        for (Casilla casilla : this.getPropiedades()) {
            if (casilla.getTipo().equals("Servicio")) {
                num++;
            }
        }
        return num;
    }

    //Método para pagar a otro jugador, devuelve true si se ha podido pagar y false en otro caso.
    public boolean pagarAJugador(Jugador recibidor, float cantidad){
        if (this.getFortuna() < cantidad) {
            System.out.println("No tienes suficiente dinero para pagar");
            return false;
        }
        this.sumarFortuna(-cantidad);
        this.sumarGastos(cantidad);
        recibidor.sumarFortuna(cantidad);
        this.pagoDeAlquileres += cantidad;
        recibidor.cobroDeAlquileres += cantidad;
        return true;
    }

    //Método para pagar impuesto a la banca, devuelve true si se ha podido pagar y false en otro caso.
    public boolean pagarImpuesto(float cantidad,Jugador banca){
        if (this.getFortuna() < cantidad) {
            System.out.println("No tienes suficiente dinero para pagar");
            return false;
        }
        this.sumarFortuna(-cantidad);
        this.sumarGastos(cantidad);
        this.pagoTasasEImpuestos += cantidad;
        banca.sumarBote(cantidad);
        return true;
    }

    //Metodo para pagar la salida de la carcel, devuelve true si se ha podido pagar y false en otro caso.
    public boolean pagarSalidaCarcel(){
        if (this.getFortuna() < Valor.PAGO_CARCEL) {
            System.out.println("No tienes suficiente dinero para pagar");
            return false;
        }
        this.sumarFortuna(-Valor.PAGO_CARCEL);
        this.sumarGastos(Valor.PAGO_CARCEL);
        this.pagoTasasEImpuestos += Valor.PAGO_CARCEL;
        this.sacarCarcel();
        System.out.println("Has pagado 25% "+Valor.PAGO_CARCEL +" para salir de la carcel");
        return true;
    }

    //Metodo para poner al jugador como salido de la carcel
    public void sacarCarcel(){
        this.enCarcel = false;
        this.tiradasCarcel = 0;
    }

    public boolean puedeEdificar(Casilla actual){
        if (avatar.getLugar().getGrupo()!=null){
            return (avatar.getLugar().getGrupo().esDuenhoGrupo(this) || ((numeroVisitas.get(actual) >= 3) && (this == actual.getDuenho())));
        }else return false;
    }

    

    /**
     * @return HashMap<Casilla,Integer> return the numeroVisitas
     */
    public HashMap<Casilla,Integer> getNumeroVisitas() {
        return numeroVisitas;
    }

    /**
     * @param numeroVisitas the numeroVisitas to set
     */
    public void setNumeroVisitas(HashMap<Casilla,Integer> numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }


    //Metodo para ver las estadisticas de un jugador:
    public void estadisticas(){
        StringBuilder str = new StringBuilder();
        str.append("{\n");
        str.append("dineroInvertido: " + this.dineroInvertido + ",\n");
        str.append("pagoTasasEImpuestos: " + this.pagoTasasEImpuestos + ",\n");
        str.append("pagoDeAlquileres: " + this.pagoDeAlquileres + ",\n");
        str.append("cobroDeAlquileres: " +this.cobroDeAlquileres + ",\n");
        str.append("pasarPorCasillaDeSalida: " + this.pasarPorCasillaDeSalida + ",\n");
        str.append("premiosInversionesOBote: " + this.premiosInversionesOBote + ",\n");
        str.append("vecesEnLaCarcel: " + this.vecesEnLaCarcel + ",\n");
        str.append("}\n");
        System.out.println(str.toString());
    }

    public void quedarBancarrota(Jugador deudor){
        this.enBancarrota = true;
        this.deudor = deudor;
    }


}
