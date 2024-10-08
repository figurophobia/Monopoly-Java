package monopoly;

import java.util.ArrayList;
import partida.*;


public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares = new ArrayList<>(); //Avatares que están situados en la casilla.

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; // no se puede proteger ya que hay 40 nombres diferente + los nombres con el jugador en la casilla, no vale la pena
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if ("Solar".equals(tipo) || "Especial".equals(tipo) || 
        "Transporte".equals(tipo) || "Servicios".equals(tipo) || "Comunidad".equals(tipo) || "Suerte".equals(tipo)||
        "Impuesto".equals(tipo)) {
        this.tipo = tipo; // Asigna el valor si es válido
        }
        else System.out.println("Tipo de casilla no válido");
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        if (valor>0) this.valor = valor; //comprueba que el valor introducido sea positivo
        else System.out.println("Valor no válido");
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        if(posicion>=1&&posicion<=40) this.posicion=posicion;
        else System.out.println("Posición no válida");
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public float getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(float impuesto) {
        if (impuesto>=0) this.impuesto = impuesto;
        else System.out.println("Impuesto no disponible");
    }

    public float getHipoteca() {
        return hipoteca;
    }

    public void setHipoteca(float hipoteca) {
        if (hipoteca>=0) this.hipoteca = hipoteca;
        else System.out.println("Hipoteca no disponible");
    }

    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }

    //Constructores:
    public Casilla() {
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */

    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.impuesto = valor/10; //Valor por defecto para impuesto en casillas de servicios
        this.posicion = posicion;
        this.duenho = duenho;
    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
    * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    
    public Casilla(String nombre, int posicion, Jugador duenho, float impuesto) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.tipo = "Impuesto";
        this.duenho = duenho;
        this.impuesto = impuesto;
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
    * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */

    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
    }

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        //Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto
        if(actual.avatar.lugar.tipo == "Solar" && actual.avatar.lugar.duenho != banca){
            if(actual.fortuna < actual.avatar.lugar.impuesto) return false;  //TIENE QUE HIPOTECAR
            else return true;
        }
        if(actual.avatar.lugar.tipo == "Impuesto"){
            if(actual.fortuna < actual.avatar.lugar.impuesto) return false;  //TIENE QUE HIPOTECAR
            else return true;
        }      
        if(actual.avatar.lugar.tipo == "Servicios" && actual.avatar.lugar.duenho != banca){
            if(actual.fortuna < actual.avatar.lugar.impuesto) return false;  //TIENE QUE HIPOTECAR
            else return true;
        }
    }

    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        this.duenho = solicitante;
        anhadirPropiedad(solicitante.avatar.lugar);
        solicitante.fortuna = solicitante.fortuna - solicitante.avatar.lugar.valor;
    }

    /*Método para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
    * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        this.valor +=suma;
    }

    /*Método para imprimir cada casilla del tablero
     * 
     * 
     */
    public String printOneCasilla(){
        String name = new String();
        name +=getNombre() +" ";
        for (Avatar i : this.avatares) {
            name+="&"+i.getId(); //Juntamos como texto de la casilla, el nombre y los avatares
        }
        name = String.format("%-"+Valor.width+"s", name);  // Rellena con espacios si es más corto, o lo ajusta a 16
        if (this.grupo!=null){
            return(Valor.SUBRAYADO+this.grupo.getColorGrupo()+name+Valor.RESET); //Si tiene grupo que pille su color
        }
        else{
            return(Valor.SUBRAYADO+Valor.WHITE+name+Valor.RESET); //Si no que se ponga el blanco
        }
    }
    /*Método para mostrar información sobre una casilla.
    * Devuelve una cadena con información específica de cada tipo de casilla.*/
        public String infoCasilla() { 
        StringBuilder info = new StringBuilder();
        
        switch (this.tipo) {
            case "Solar":
                info.append("{\n")
                    .append("tipo: ").append(this.tipo).append(",\n")
                    .append("grupo: ").append(this.grupo.getColorGrupo() + "Color" + Valor.RESET).append(",\n")
                    .append("propietario: ").append(this.duenho != null ? this.duenho.getNombre() : "N/A").append(",\n")
                    .append("valor: ").append(this.valor).append(",\n")
                    .append("alquiler: ").append(this.valor * 0.1f).append(",\n")
                    .append("valor hotel: ").append(this.valor * 0.6f).append(",\n")
                    .append("valor casa: ").append(this.valor * 0.6f).append(",\n")
                    .append("valor piscina: ").append(this.valor * 0.4f).append(",\n")
                    .append("valor pista de deporte: ").append(this.valor * 1.25f).append(",\n")
                    .append("alquiler una casa: ").append(this.valor * 0.1f * 5).append(",\n")
                    .append("alquiler dos casas: ").append(this.valor * 0.1f * 15).append(",\n")
                    .append("alquiler tres casas: ").append(this.valor * 0.1f * 35).append(",\n")
                    .append("alquiler cuatro casas: ").append(this.valor * 0.1f * 50).append(",\n")
                    .append("alquiler hotel: ").append(this.valor * 0.1f * 70).append(",\n")
                    .append("alquiler piscina: ").append(this.valor * 0.1f * 25).append(",\n")
                    .append("alquiler pista de deporte: ").append(this.valor * 0.1f * 25).append("\n")
                    .append("}");
                break;
            case "Impuesto":
                info.append("{\n")
                    .append("tipo: ").append(this.tipo).append(",\n")
                    .append("apagar: ").append(this.impuesto).append("\n")
                    .append("}");
                break;
            case "Especial":
                if (this.nombre.equals("Carcel")) {
                    info.append("{\n")
                        .append("tipo: ").append(this.tipo).append(",\n")
                        .append("nombre: ").append(this.nombre).append(",\n")
                        .append("jugadores: [");
                    for (Avatar avatar : this.avatares) {
                        info.append(avatar.getJugador().getNombre()).append(", ");
                    }
                    if (!this.avatares.isEmpty()) {
                        info.setLength(info.length() - 2); // Eliminar la última coma y espacio
                    }
                    info.append("]\n")
                        .append("}");
                } else if (this.nombre.equals("Parking")) {
                    info.append("{\n")
                        .append("tipo: ").append(this.tipo).append(",\n")
                        .append("nombre: ").append(this.nombre).append(",\n")
                        .append("bote: ").append("Poner bote IMPLEMENTAR").append(",\n")
                        .append("jugadores: [");
                    for (Avatar avatar : this.avatares) {
                        info.append(avatar.getJugador().getNombre()).append(", ");
                    }
                    if (!this.avatares.isEmpty()) {
                        info.setLength(info.length() - 2); // Eliminar la última coma y espacio
                    }
                    info.append("]\n")
                        .append("}");
                } else if (this.nombre.equals("Salida")) {
                    info.append("{\n")
                        .append("tipo: ").append(this.tipo).append(",\n")
                        .append("nombre: ").append(this.nombre).append("\n")
                        .append("}");
                } else if (this.nombre.equals("Ir a Cárcel")) {
                    info.append("{\n")
                        .append("tipo: ").append(this.tipo).append(",\n")
                        .append("nombre: ").append(this.nombre).append("\n")
                        .append("}");
                }
                break;
            default:
                info.append("Tipo de casilla desconocido.");
                break;
        }
        
        return info.toString();
    }
    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        StringBuilder info = new StringBuilder();
        
    }


}
