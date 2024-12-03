package monopoly;

import java.util.ArrayList;
import partida.*;

public class Casilla {
    protected String nombre;
    protected int posicion;
    protected ArrayList<Avatar> avatares = new ArrayList<>();
    private boolean cuatrovueltas;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }

    public ArrayList<Avatar> getAvatares() { return avatares; }
    public void setAvatares(ArrayList<Avatar> avatares) { this.avatares = avatares; }

    public boolean isCuatrovueltas() { return cuatrovueltas; }
    public void setCuatrovueltas(boolean cuatrovueltas) { this.cuatrovueltas = cuatrovueltas; }

    public Casilla (String nombre, int posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.cuatrovueltas = false;
    }

    Consola consola = new ConsolaNormal();

    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    public void visitarCasilla(Jugador visitante){
        visitante.getNumeroVisitas().put(this, visitante.getNumeroVisitas().getOrDefault(this, 0) + 1);
    }

    //TODO: frecuenciaVisita
    public boolean estaAvatar(Avatar avatar){
        Casilla casillaAvatar = avatar.getLugar();
        return (casillaAvatar == this);
    }

    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada){
        return true;
    }

    public String printOneCasilla(){
        String name = new String();
        name +=getNombre() +" ";
        if (!this.avatares.isEmpty()) {
            name+="&";
        }
        for (Avatar i : this.avatares) {
            name+=i.getId(); //Juntamos como texto de la casilla, el nombre y los avatares
        }
        name = String.format("%-"+Valor.width+"s", name);  // Rellena con espacios si es más corto, o lo ajusta a 16
        if (this instanceof Solar solar){
            return(Valor.SUBRAYADO+solar.grupo.getColorGrupo()+name+Valor.RESET); //Si tiene grupo que pille su color
        }
        else{
            return(Valor.SUBRAYADO+Valor.WHITE+name+Valor.RESET); //Si no que se ponga el blanco
        }
    }

    public void edificar(String tipo){
        consola.imprimirAdvertencia("No puedes edificar en esta casilla");
    }

    public String infoCasilla() { 
        StringBuilder info = new StringBuilder();
        info.append("{\n");
        info.append("tipo: ").append(this.getClass()).append(",\n");
        /*

        switch (this.tipo) {
            case "Solar":
                info.append("grupo: ").append(this.grupo.getColorGrupo()).append("#####" + Valor.RESET).append(",\n")
                .append("propietario: ").append(this.duenho != null ? this.duenho.getNombre() : "N/A").append(",\n")
                .append("valor: ").append(this.valor).append(",\n")
                .append("alquiler: ").append(this.impuesto).append(",\n")
                .append("valor hotel: ").append(this.valor * 0.6f).append(",\n")
                .append("valor casa: ").append(this.valor * 0.6f).append(",\n")
                .append("valor piscina: ").append(this.valor * 0.4f).append(",\n")
                .append("valor pista de deporte: ").append(this.valor * 1.25f).append(",\n")
                .append("alquiler una casa: ").append(this.impuesto * 5).append(",\n")
                .append("alquiler dos casas: ").append(this.impuesto * 15).append(",\n")
                .append("alquiler tres casas: ").append(this.impuesto * 35).append(",\n")
                .append("alquiler cuatro casas: ").append(this.impuesto * 50).append(",\n")
                .append("alquiler hotel: ").append(this.impuesto * 70).append(",\n")
                .append("alquiler piscina: ").append(this.impuesto * 25).append(",\n")
                .append("alquiler pista de deporte: ").append(this.impuesto * 25).append("\n");
                break;
            case "Impuesto":
                    info.append("a pagar: ").append(this.impuesto).append("\n");
                break;
            case "Especial":
                if (this.nombre.equals("Carcel")) {

                    info.append("nombre: ").append(this.nombre).append(",\n");
                    info.append("salir:").append(Valor.PAGO_CARCEL).append("\n");
                } else if (this.nombre.equals("Parking")) {
                    info.append("nombre: ").append(this.nombre).append(",\n")
                    .append("bote: ").append(getDuenho().getBote()).append(",\n");
                } else if (this.nombre.equals("Salida")) {
                    info.append("nombre: ").append(this.nombre).append("\n");
                } else if (this.nombre.equals("Ir a Cárcel")) {
                    info.append("nombre: ").append(this.nombre).append("\n");
                }
                break;
            default:
                info.append("Tipo de casilla desconocido.\n");
                break;
        }
        */
        info.append("jugadores: [");
        for (Avatar avatar : this.avatares) {
            info.append(avatar.getJugador().getNombre()).append(", ");
        }
        if (!this.avatares.isEmpty()) {
            info.setLength(info.length() - 2); // Eliminar la última coma y espacio
        }
        info.append("]\n");
        /*
        int numcasa=edificaciones.getOrDefault("casa",new ArrayList<>()).size();
        if (numcasa!=0){
            info.append("- ").append(numcasa).append(numcasa==1 ? " casa" : " casas").append("\n");
        }
        int numhotel=edificaciones.getOrDefault("hotel", new ArrayList<>()).size();
        if (numhotel!=0){
            info.append("- ").append(numhotel).append(numhotel==1 ? " hotel" : " hoteles").append("\n");
        }
        int numpiscina=edificaciones.getOrDefault("piscina",new ArrayList<>()).size();
        if (numpiscina!=0){
            info.append("- ").append(numpiscina).append(numpiscina==1 ? " piscina" : " piscinas").append("\n");
        }
        int numpista=edificaciones.getOrDefault("pista",new ArrayList<>()).size();
        if (numpista!=0){
            info.append("- ").append(numpista).append(numpista==1 ? " pista de deporte" : " pistas de deporte").append("\n");
        }
        info.append("}");
        */
        return info.toString();
    }

}
