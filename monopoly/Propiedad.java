package monopoly;
import partida.*;

public class Propiedad extends CasillaNew{
    private float valor;
    private final float VALOR_INICIAL;
    private Jugador duenho;
    private Grupo grupo;
    private float precioAlquiler;
    private float valorHipoteca;
    private boolean esHipotecada = false;
    private float dineroGenerado = 0;

    public float getValor() { return valor; }
    public void setValor(float valor) { this.valor = valor; }

    public float getVALOR_INICIAL() { return VALOR_INICIAL; }

    public Jugador getDuenho() {return duenho;}
    public void setDuenho(Jugador duenho) {this.duenho = duenho;}

    public Grupo getGrupo() {return grupo;}
    public void setGrupo(Grupo grupo) {this.grupo = grupo;}

    public float getPrecioAlquiler() {return precioAlquiler;}
    public void setPrecioAlquiler(float precioAlquiler) {this.precioAlquiler = precioAlquiler;}

    public float getValorHipoteca() {return valorHipoteca;}
    public void setValorHipoteca(float valorHipoteca) {this.valorHipoteca = valorHipoteca;}

    public boolean isEsHipotecada() {return esHipotecada;}
    public void setEsHipotecada(boolean esHipotecada) {this.esHipotecada = esHipotecada;}

    public float getDineroGenerado() {return dineroGenerado;}
    public void setDineroGenerado(float dineroGenerado) {this.dineroGenerado = dineroGenerado;}

    
}
