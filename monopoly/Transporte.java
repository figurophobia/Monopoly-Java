package monopoly;
import Excepciones.MalUsoComando.CompraSinPoder;
import partida.*;

public class Transporte extends Propiedad {

    public Transporte(String nombre, int posicion, float valor, Jugador duenho) {
        super(nombre, posicion , valor, duenho);

        hipotecaInicial = valor / 2f;
        precioAlquiler = Valor.SUMA_VUELTA;

    }

    Consola consola = new ConsolaNormal();
    
    public float calcularPago(int tirada) {
        return (this.precioAlquiler * 0.25f * this.duenho.numTransportes());
    }

    @Override
    public float alquiler(int tirada) {
        return (this.precioAlquiler * 0.25f * this.duenho.numTransportes());
    }

    @Override
    public void comprarCasilla(Jugador comprador, Jugador banca) throws CompraSinPoder{

        if (comprador.getAvatar().getLugar() != this){
            consola.imprimirAdvertencia("No estás en esa casilla");
            throw new CompraSinPoder("El usuario solicitante de la compra de la casilla no está en ella");
        }
        if (!esComprable(comprador, banca)){
            consola.imprimirAdvertencia("No tienes suficiente dinero para comprar esa casilla");
            throw new CompraSinPoder("El usuario solicitante de la compra de la casilla no tiene suficiente dinero para comprarla");
        }
        if (!esComprableCoche(comprador)){
            consola.imprimirAdvertencia("No puedes comprar esa casilla");
            throw new CompraSinPoder("El usuario solicitante de la compra de la casilla no puede comprarla, por ser un coche en movimiento avanzado");
        }

        comprador.sumarGastos(valor);
        comprador.setDineroInvertido(comprador.getDineroInvertido() + valor);
        comprador.setFortuna(comprador.getFortuna() - valor);
        banca.sumarFortuna(valor);
        duenho = comprador;
        banca.eliminarPropiedad(this);
        comprador.setCochePuedeComprar(false);
        comprador.anhadirPropiedad(this);

        String mensaje = "El jugador " + comprador.getNombre() + " ha comprado al casilla " + nombre + " por " + valor + "€. ";
        consola.imprimirMensaje(mensaje);
        
    }

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada){

        visitarCasilla(jugador);

        boolean esAjena = (duenho != jugador && duenho != banca);

        if (esComprable(jugador, banca)){
            String answer = consola.leer("Puedes comprar esta casilla de transporte por " + valor + ". ¿Quieres comprarla? (s/n): ");
            if (answer.equals("s"))
                try {
                    comprarCasilla(jugador, banca);
                } catch (Exception e) {
                }
            return true;
        }

        if (esAjena && esHipotecada){
            consola.imprimirMensaje("La casilla " + nombre + " está hipotecada, no pagas nada");
            return true;
        }

        if (!esAjena)
            return true;
        
        float pago = calcularPago(tirada);

        boolean puedePagar = jugador.getFortuna() >= pago; 

        if (puedePagar){
            consola.imprimirAdvertencia("Has pagado "+Valor.RED+pago+"€"+Valor.RESET+" al jugador "+Valor.BLUE+duenho.getNombre()+Valor.RESET+" por caer en la casilla "+ Valor.BLUE+ this.nombre+Valor.RESET);
            dineroGenerado += pago;
        }

        return jugador.pagarAJugador(duenho, pago);

    }



    

}

