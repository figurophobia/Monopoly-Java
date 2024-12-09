package monopoly;
import Excepciones.Ejecucion.DineroError;
import Excepciones.Ejecucion.EdificioNotFound;
import Excepciones.Ejecucion.InstanciaIncorrecta;
import Excepciones.MalUsoComando.CompraSinPoder;
import Excepciones.MalUsoComando.EdificarSinPoder;
import Excepciones.MalUsoComando.HipotecaSinTener;
import Excepciones.MalUsoComando.VenderSinTener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import partida.*;

public class Solar extends Propiedad {

    private HashMap<String,ArrayList<Edificacion>> edificaciones = new HashMap<>();
    protected Grupo grupo;

    public Solar(String nombre, int posicion, float valor, Jugador duenho) {
        super(nombre, posicion , valor, duenho);

        hipotecaInicial = valor / 2f;
        precioAlquiler = Valor.SUMA_VUELTA/200;

    }

    public Grupo getGrupo() {return grupo;}
    public void setGrupo(Grupo grupo) {this.grupo = grupo;}

    public HashMap<String,ArrayList<Edificacion>> getEdificaciones(){ return edificaciones; }

    Consola consola = new ConsolaNormal();
    
    public float calcularPago(int tirada) {
        calcularprecioAlquiler();
        return precioAlquiler;
    }

    @Override
    public void comprarCasilla(Jugador comprador, Jugador banca) throws CompraSinPoder{

        if (comprador != duenho && duenho != banca){
            throw new CompraSinPoder("La casilla solicitada de compra ya pertenece a otro jugador");
        }

        if (!esComprable(comprador, banca)){
            throw new CompraSinPoder("El usuario solicitante de la compra de la casilla no tiene suficiente dinero para comprarla");
        }
        if (!esComprableCoche(comprador)){
            throw new CompraSinPoder("El usuario solicitante de la compra de la casilla está en un movimiento avanzado que no le permite hacer la compra");
        }
        if (comprador.getAvatar().getLugar() != this){
            throw new CompraSinPoder("El usuario solicitante de la compra de la casilla no se encuentra en la casilla indicada");
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
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) throws EdificarSinPoder, DineroError, InstanciaIncorrecta  {

        visitarCasilla(jugador);

        boolean esAjena = (duenho != jugador && duenho != banca);

        if (esComprable(jugador, banca)){
            String answer = consola.leer("Puedes comprar esta casilla " + nombre + " por " + valor + ". ¿Quieres comprarla? (s/n): ");
            if (answer.equals("s"))
                try {
                    comprarCasilla(jugador, banca);
                } catch (Exception e) {
                }
            return true;
        }

        if (jugador.puedeEdificar(this))
            consola.imprimirMensaje("Has caido en la casilla "+ Valor.BLUE +this.nombre+Valor.RESET +"."+Valor.RED+"  ¡Puedes edificar!"+Valor.RESET);

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

    public void calcularprecioAlquiler() {
        
        float inicial = valor * 0.1f;
        precioAlquiler = inicial;
        if (grupo.esDuenhoGrupo(duenho)){
            precioAlquiler *= 2;
            
        }

        precioAlquiler += inicial * calcularMultiplicadorAlquiler(edificaciones.getOrDefault("casa", new ArrayList<>()).size());
        precioAlquiler += inicial * edificaciones.getOrDefault("hotel", new ArrayList<>()).size() * 70;
        precioAlquiler += inicial * edificaciones.getOrDefault("piscina", new ArrayList<>()).size() * 25;
        precioAlquiler += inicial * edificaciones.getOrDefault("pista", new ArrayList<>()).size() * 25;
    }

    @Override
    public float alquiler(int tirada) {

        float inicial = valor * 0.1f;
        precioAlquiler = inicial;
        if (grupo.esDuenhoGrupo(duenho))
            precioAlquiler *= 2;


        precioAlquiler += inicial * calcularMultiplicadorAlquiler(edificaciones.getOrDefault("casa", new ArrayList<>()).size());
        precioAlquiler += inicial * edificaciones.getOrDefault("hotel", new ArrayList<>()).size() * 70;
        precioAlquiler += inicial * edificaciones.getOrDefault("piscina", new ArrayList<>()).size() * 25;
        precioAlquiler += inicial * edificaciones.getOrDefault("pista", new ArrayList<>()).size() * 25;

        return precioAlquiler;
    }
    

    private float calcularMultiplicadorAlquiler(int numCasas) {
        return switch (numCasas) {
            case 1 -> 5.0f;
            case 2 -> 15.0f;
            case 3 -> 35.0f;
            case 4 -> 50.0f;
            default -> 0.0f;
        }; 
    }
    @Override
    public void edificar(String tipo) throws EdificarSinPoder, DineroError, InstanciaIncorrecta{
        if(duenho.puedeEdificar(this)){
            switch (tipo) {
                case "casa"-> {
                    comprarCasa();
                }
                case "hotel"-> {
                    comprarHotel();
                }
                case "piscina"-> {
                    comprarPiscina();
                }
                case "pista"-> {
                    comprarPista();
                }
                default->{
                    System.out.println("Tipo de edificación no reconocido...");
                }
            }
            calcularprecioAlquiler();
        }else System.out.println(Valor.RED + "No "+ Valor.RESET+ "puedes edificar en esta casilla.");
    }

    private void comprarCasa() throws EdificarSinPoder, DineroError{
        String B = Valor.BLUE;
        String R = Valor.RED;
        String G = Valor.GREEN;
        String RE = Valor.RESET;
        String Y = Valor.YELLOW;
        int maxcasa;
        grupo.getEdificaciones().putIfAbsent("casa", new ArrayList<>());
        edificaciones.putIfAbsent("casa", new ArrayList<>());
        float precio=(0.6f*grupo.valor());
        if (duenho.getFortuna() >= precio) {
            if (grupo.getEdificaciones().getOrDefault("hotel", new ArrayList<>()).size() < grupo.getMiembros().size()) {
                maxcasa = grupo.getMiembros().size() * 4;
            } else {
                maxcasa = grupo.getMiembros().size();
            }
            duenho.setDineroInvertido(duenho.getDineroInvertido()+precio);
    
            boolean puedeConstruirCasa = edificaciones.getOrDefault("casa", new ArrayList<>()).size() < 4 &&
            grupo.getEdificaciones().getOrDefault("casa", new ArrayList<>()).size() < maxcasa;
    
            if (puedeConstruirCasa) {
                duenho.setFortuna(duenho.getFortuna() - precio);
                System.out.println("Has comprado una" + B + " casa" + RE + " en " + Y + this.nombre + RE + " por "
                + R + precio + "€"+RE +", te quedan " + R + duenho.getFortuna()+ "€"+RE +".");
                
                Edificacion casa = new Casa(duenho, this, precio);
                edificaciones.get("casa").add(casa);
                grupo.getEdificaciones().get("casa").add(new Casa(duenho, this, precio));
            } else {
                throw new EdificarSinPoder(R + "No puedes construir más casas..." + RE);
            }
        } else {
            throw new DineroError(duenho.getFortuna());
        }
    }
    private void comprarHotel() throws EdificarSinPoder, DineroError{
        String B = Valor.BLUE;
        String R = Valor.RED;
        String G = Valor.GREEN;
        String RE = Valor.RESET;
        String Y = Valor.YELLOW;

        grupo.getEdificaciones().putIfAbsent("hotel", new ArrayList<>());
        edificaciones.putIfAbsent("hotel", new ArrayList<>());
        float precio=(0.6f*grupo.valor());
        if (duenho.getFortuna() >= precio) {
            if (edificaciones.getOrDefault("casa",new ArrayList<>()).size() == 4) {
                if (grupo.getEdificaciones().get("hotel").size() < grupo.getMiembros().size()) {
                    duenho.setFortuna(duenho.getFortuna() - precio);
                    System.out.println("Has comprado un " + B + "hotel " + RE + " en " + Y + this.nombre + RE +  " por " + R + precio + "€" + RE + ".");
                    edificaciones.get("casa").clear();
                    
                    for (int i = 0; i < 4; i++) {
                        grupo.getEdificaciones().get("casa").remove(0);
                    }
                    
                    edificaciones.get("hotel").add(new Hotel(duenho, this, precio));
                    grupo.getEdificaciones().get("hotel").add(new Hotel(duenho, this, precio));
                } else {
                    throw new EdificarSinPoder(R + "No puedes construir más hoteles..." + RE);
                }
            } else {
                throw new EdificarSinPoder(R + "No tienes cuatro casas construidas..." + RE);
            }
        } else {
            throw new DineroError(duenho.getFortuna());
        }
    }
    private void comprarPiscina() throws EdificarSinPoder, DineroError{
        String B = Valor.BLUE;
        String R = Valor.RED;
        String G = Valor.GREEN;
        String RE = Valor.RESET;
        String Y = Valor.YELLOW;
        grupo.getEdificaciones().putIfAbsent("piscina", new ArrayList<>());
        edificaciones.putIfAbsent("piscina", new ArrayList<>());
        float precio=(0.4f*grupo.valor());
        if (duenho.getFortuna() >= precio) {
            int numcasa = edificaciones.getOrDefault("casa",new ArrayList<>()).size();
            int numhotel=edificaciones.getOrDefault("hotel",new ArrayList<>()).size();
            if (((numcasa >= 2) && (numhotel>=1))||numhotel>=2){
                if (grupo.getEdificaciones().getOrDefault("piscina", new ArrayList<>()).size() < grupo.getMiembros().size()) {
                    System.out.println("Has comprado una " + B + "piscina " + RE + " en " + Y + this.nombre + RE + " por " + R + precio + "€" + RE + ".");
                    duenho.setFortuna(duenho.getFortuna() - precio);
                    
                    edificaciones.get("piscina").add(new Piscina(duenho, this, precio));
                    grupo.getEdificaciones().get("piscina").add(new Piscina(duenho, this, precio));
                } else {
                    throw new EdificarSinPoder(R + "No puedes construir más piscinas..." + RE);
                }
            } else {
                throw new EdificarSinPoder(R + "No tienes al menos dos casas y un hotel..." + RE);
            }
        } else {
            throw new DineroError(duenho.getFortuna());
        }
    }
    private void comprarPista() throws EdificarSinPoder, DineroError {
        String B = Valor.BLUE;
        String R = Valor.RED;
        String G = Valor.GREEN;
        String RE = Valor.RESET;
        String Y = Valor.YELLOW;
        grupo.getEdificaciones().putIfAbsent("pista", new ArrayList<>());
        edificaciones.putIfAbsent("pista", new ArrayList<>());
        float precio=(1.25f*grupo.valor());
        if (duenho.getFortuna() >= precio) {
            if (edificaciones.getOrDefault("hotel",new ArrayList<>()).size()>=2){
                if (grupo.getEdificaciones().getOrDefault("pista", new ArrayList<>()).size() < grupo.getMiembros().size()) {
                    System.out.println("Has comprado una " + B + "pista de deporte" + RE + " en " + Y + this.nombre + RE + " por " + R + precio + "€" + RE + ".");
                    duenho.setFortuna(duenho.getFortuna() - precio);
                    
                    edificaciones.get("pista").add(new Pista(duenho, this, precio));
                    grupo.getEdificaciones().get("pista").add(new Pista(duenho, this, precio));
                } else {
                    throw new EdificarSinPoder(R + "No puedes construir más pistas de deporte..." + RE);
                }
            } else {
                throw new EdificarSinPoder(R + "No tienes al menos dos hoteles..."+ RE);
            }
        } else {
            throw new DineroError(duenho.getFortuna());
        }
    }

    public void mostrarEdificaciones(){
        // Recorre todas las entradas del hashmap
        for (Map.Entry<String, ArrayList<Edificacion>> entry : edificaciones.entrySet()) {
            // Coge el valor de cada entrada
            ArrayList<Edificacion> listaEdificios = entry.getValue();
            // Y la recorre imprimiendo el edificio
            for (Edificacion edificio : listaEdificios) {
                //FIXME:
                System.out.print(edificio);
            }
        }
    }

    public void vender_edificio(String tipo,int num) throws VenderSinTener, EdificioNotFound{
        tipo=tipo.toLowerCase();
        if(!tipo.equals("casa")&&!tipo.equals("hotel")&&!tipo.equals("pista")&&!tipo.equals("piscina")){
            throw new VenderSinTener("Tipo de edificación no válido...");
        }
        int num_viviendas=edificaciones.getOrDefault(tipo, new ArrayList<>()).size();
        if (num>num_viviendas){
            //Lo consideramos error de ejecución

            throw new EdificioNotFound("No tienes "+num+" "+tipo+((num==1) ? "":"s")+ " en esta casilla, tienes "+num_viviendas+"...");
            }
        try{
            int ganancias=0;
            for(int i=0;i<num;i++){
                float precio_compra=edificaciones.get(tipo).get(edificaciones.get(tipo).size() - 1).getPrecio();
                ganancias+=precio_compra/2;
                float nueva_fortuna=duenho.getFortuna()+precio_compra/2;
                edificaciones.get(tipo).remove(edificaciones.get(tipo).size() - 1);
                grupo.getEdificaciones().get(tipo).remove(grupo.getEdificaciones().get(tipo).size() - 1);
                duenho.setFortuna(nueva_fortuna);}
            
            int properties_left=edificaciones.getOrDefault(tipo, new ArrayList<>()).size();
            System.out.println("Has vendido con éxito "+ ((num==1) ? "tu ":"tus ") +Valor.YELLOW+num +" "+ tipo+((num==1) ? "":"s") +
            Valor.RESET+" en "+Valor.BLUE+nombre+Valor.RESET+". Recibes "+Valor.GREEN+ganancias+"€."+Valor.RESET+" En la propiedad "+
            (properties_left==1 ? "queda ":"quedan ")+Valor.RED+properties_left+" "+
            tipo+((properties_left==1) ? "":"s")+". "+Valor.RESET);
        }catch (Exception e){
        }
    }

    @Override
    public boolean sePuedeHipotecar(Jugador actual) throws HipotecaSinTener{
        if (esHipotecada){
            throw new HipotecaSinTener("La casilla ya está hipotecada...");
        }
        else if(this.duenho!=actual){
            throw new HipotecaSinTener("No eres el dueño de la casilla...");
        }
        else if(!edificaciones.isEmpty()){
            throw new HipotecaSinTener("No puedes hipotecar una casilla con edificaciones,debes venderlos primero...");
        }
        else{return true;}
    }

    @Override
    public String infoCasilla() { 
        float alquiler = valor*0.1f;
        StringBuilder info = new StringBuilder();
        info.append("{\n");
        info.append("tipo: ").append(this.getClass().getSimpleName()).append(",\n");
        info.append("grupo: ").append(this.grupo.getColorGrupo()).append("#####" + Valor.RESET).append(",\n")
        .append("propietario: ").append(this.duenho != null ? this.duenho.getNombre() : "N/A").append(",\n")
        .append("valor: ").append(this.valor).append(",\n")
        .append("alquiler: ").append(alquiler).append(",\n")
        .append("valor hotel: ").append(this.valor * 0.6f).append(",\n")
        .append("valor casa: ").append(this.valor * 0.6f).append(",\n")
        .append("valor piscina: ").append(this.valor * 0.4f).append(",\n")
        .append("valor pista de deporte: ").append(this.valor * 1.25f).append(",\n")
        .append("alquiler una casa: ").append(alquiler * 5).append(",\n")
        .append("alquiler dos casas: ").append(alquiler * 15).append(",\n")
        .append("alquiler tres casas: ").append(alquiler * 35).append(",\n")
        .append("alquiler cuatro casas: ").append(alquiler * 50).append(",\n")
        .append("alquiler hotel: ").append(alquiler * 70).append(",\n")
        .append("alquiler piscina: ").append(alquiler * 25).append(",\n")
        .append("alquiler pista de deporte: ").append(alquiler * 25).append("\n");
        info.append("jugadores: [");
        for (Avatar avatar : this.avatares) {
            info.append(avatar.getJugador().getNombre()).append(", ");
        }
        if (!this.avatares.isEmpty()) {
            info.setLength(info.length() - 2); // Eliminar la última coma y espacio
        }
        info.append("]\n");
        
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
        
        return info.toString();
    }


    

}

