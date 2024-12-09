package monopoly;
import Excepciones.MalUsoComando.CompraSinPoder;
import Excepciones.MalUsoComando.HipotecaSinTener;
import partida.*;

public abstract class Propiedad extends Casilla{
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

    public boolean esHipotecada() {return esHipotecada;}
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

    public void comprarCasilla(Jugador comprador, Jugador banca) throws  CompraSinPoder{

    }

    public void sumarValor(float suma) {
        this.valor +=suma;
    }

    

    public boolean perteneceAJugador(Jugador jugador) {
        return (duenho == jugador);
    }

    public float alquiler(int tirada) {
        return precioAlquiler;
    }

    public String casEnVenta() {
        StringBuilder info = new StringBuilder();

        String tipo = getClass().getSimpleName();

        info.append("{\ntipo: ").append(tipo).append(",\n");
        info.append("nombre: ").append(nombre).append(",\n");

        if(this instanceof Solar solar)
            info.append("grupo: ").append(solar.grupo.getColorGrupo()).append("#####" + Valor.RESET).append(",\n");
        
        info.append("valor: ").append(valor).append(",\n");
        return info.toString();
    }

    public boolean sePuedeHipotecar(Jugador actual) throws HipotecaSinTener{
        if (esHipotecada){
            throw new HipotecaSinTener("La casilla ya está hipotecada...");
        }
        else if(this.duenho!=actual){
            throw new HipotecaSinTener("No eres el dueño de la casilla...");
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

    public void hipotecar(Jugador actual) throws HipotecaSinTener{
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

    public void sumarPrecioAlquiler(float suma) {
        this.precioAlquiler += suma;
    }

    @Override
    public String infoCasilla() { 
        StringBuilder info = new StringBuilder();
        info.append("{\n");
        info.append("tipo: ").append(this.getClass().getSimpleName()).append(",\n");
        info.append("propietario: ").append(this.duenho != null ? this.duenho.getNombre() : "N/A").append(",\n")
        .append("valor: ").append(this.valor).append(",\n")
        .append("alquiler: ").append(this.precioAlquiler).append(",\n");
        info.append("}");
        
        return info.toString();
    }

    
}

