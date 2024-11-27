package monopoly;
import partida.*;

public class Propiedad extends Casilla{
    protected float valor;
    protected final float VALOR_INICIAL;
    protected Jugador duenho;
    protected float precioAlquiler = 0; // "impuesto"
    protected float valorHipoteca = 0; // "valor_hipoteca"
    protected float hipotecaInicial = 0; // "hipoteca"
    protected boolean esHipotecada = false; // "hipotecada"
    protected float dineroGenerado = 0;

    public float getValor() { return valor; }
    public void setValor(float valor) { this.valor = valor; }

    public float getVALOR_INICIAL() { return VALOR_INICIAL; }

    public Jugador getDuenho() {return duenho;}
    public void setDuenho(Jugador duenho) {this.duenho = duenho;}

    public float getPrecioAlquiler() {return precioAlquiler;}
    public void setPrecioAlquiler(float precioAlquiler) {this.precioAlquiler = precioAlquiler;}
    
    public float getHipotecaInicial() {return hipotecaInicial;}
    public void setHipotecaInicial(float precioAlquiler) {this.hipotecaInicial = precioAlquiler;}

    public float getValorHipoteca() {return valorHipoteca;}
    public void setValorHipoteca(float valorHipoteca) {this.valorHipoteca = valorHipoteca;}

    public boolean isEsHipotecada() {return esHipotecada;}
    public void setEsHipotecada(boolean esHipotecada) {this.esHipotecada = esHipotecada;}

    public float getDineroGenerado() {return dineroGenerado;}
    public void setDineroGenerado(float dineroGenerado) {this.dineroGenerado = dineroGenerado;}

    public Propiedad(String nombre, int posicion, float valor, Jugador duenho){
        super(nombre, posicion);

        this.valor = valor;
        VALOR_INICIAL = valor;
        this.duenho = duenho;
    }

    public boolean esComprable(Jugador comprador, Jugador banca){
        return (this.duenho == banca && comprador.getFortuna() >= this.valor);
    }

    public boolean esComprableCoche(Jugador jugador){
        Avatar avatar = jugador.getAvatar();
        return !(jugador.getCochePuedeComprar() && avatar.esMovAvanzado() && avatar.getTipo().equals("Coche"));
    }

    public void comprarCasilla(Jugador comprador, Jugador banca) throws excepcionPropiedad{

    }

    public boolean sePuedeHipotecar(Jugador actual){
        if (esHipotecada){
            System.out.println("La casilla ya está hipotecada...");
            return false;
        }
        else if(this.duenho!=actual){
            System.out.println("No eres el dueño de la casilla...");
            return false;
        }
        else{return true;}
    }

    public boolean sePuedeDeshipotecar(Jugador actual){
        if (!esHipotecada){
            System.out.println("La casilla no está hipotecada...");
            return false;
        }
        else if(this.duenho!=actual){
            System.out.println("No eres el dueño de la casilla...");
            return false;
        }
        else if (actual.getFortuna() < valorHipoteca * 1.1f){
            System.out.println("No tienes suficiente dinero para deshipotecar la casilla...");
            return false;
        }
        else{return true;}
    }

    public void hipotecar(Jugador actual){
        if (sePuedeHipotecar(actual)){
            actual.setFortuna(actual.getFortuna() + hipotecaInicial);
            //actual.setDineroInvertido(actual.getDineroInvertido()-hipoteca);
            valorHipoteca = hipotecaInicial;
            esHipotecada=true;
            System.out.println("Has hipotecado la casilla "+nombre+" por "+hipotecaInicial+"€, te quedan "+actual.getFortuna()+"€.");
        }
    }

    public void deshipotecar(Jugador actual){
        if (sePuedeDeshipotecar(actual)){
            actual.setFortuna(actual.getFortuna()-valorHipoteca*1.1f);
            //actual.setDineroInvertido(actual.getDineroInvertido()+valor_hipoteca*1.1f);
            esHipotecada=false;
            System.out.println("Has deshipotecado la casilla "+nombre+" por "+valorHipoteca*1.1f+"€, te quedan "+actual.getFortuna()+"€.");
        }
    }

    
}

class excepcionPropiedad extends Exception{
    public excepcionPropiedad(String mensaje) {
        super(mensaje);
    }
}

class dineroInsuficiente extends excepcionPropiedad{
    public dineroInsuficiente(String mensaje) {
        super(mensaje);
    }
}

class casillaIncorrecta extends excepcionPropiedad{
    public casillaIncorrecta(String mensaje) {
        super(mensaje);
    }
}

class compraCocheNoDisponible extends excepcionPropiedad{
    public compraCocheNoDisponible(String mensaje) {
        super(mensaje);
    }
}