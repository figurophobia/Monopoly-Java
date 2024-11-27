package monopoly;
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

    Consola consola = new ConsolaNormal();
    
    public float calcularPago(int tirada) {
        calcularImpuesto();
        return precioAlquiler;
    }

    @Override
    public void comprarCasilla(Jugador comprador, Jugador banca) throws dineroInsuficiente, compraCocheNoDisponible, casillaIncorrecta{

        if (comprador != duenho){
            consola.imprimirAdvertencia("Esa casilla ya pertenece a " + duenho.getNombre() + "!");
            throw new casillaIncorrecta("La casilla solicitada de compra ya pertenece a otro jugador");
        }

        if (!esComprable(comprador, banca)){
            consola.imprimirAdvertencia("No tienes suficiente dinero para comprar esa casilla");
            throw new dineroInsuficiente("El usuario solicitante de la compra de la casilla no posee el dinero suficiente");
        }
        if (!esComprableCoche(comprador)){
            consola.imprimirAdvertencia("No puedes comprar esa casilla");
            throw new dineroInsuficiente("El usuario solicitante de la compra de la casilla está en un movimiento avanzado que no le permite hacer la compra");
        }
        if (comprador.getAvatar().getLugar() != this){
            consola.imprimirAdvertencia("No estás en esa casilla");
            throw new casillaIncorrecta("El usuario solicitante de la compra de la casilla no se encuentra en la casilla indicada");
        }

        comprador.sumarGastos(valor);
        comprador.setDineroInvertido(comprador.getDineroInvertido() + valor);
        comprador.setFortuna(comprador.getFortuna() - valor);
        banca.sumarFortuna(valor);
        duenho = comprador;
        banca.eliminarPropiedad(this);
        comprador.setCochePuedeComprar(false);

        String mensaje = "El jugador " + comprador.getNombre() + " ha comprado al casilla " + nombre + " por " + valor + "€. ";
        consola.imprimirMensaje(mensaje);
        
    }

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada){

        visitarCasilla(jugador);

        boolean esAjena = (duenho != jugador && duenho != banca);

        if (esComprable(jugador, banca)){
            String answer = consola.leer("Puedes comprar esta casilla de Solar por " + valor + ". ¿Quieres comprarla? (s/n)");
            if (answer.equals("s"))
                try {
                    comprarCasilla(jugador, banca);
                } catch (Exception e) {
                    e.printStackTrace();
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

    public void calcularImpuesto() {
        
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
    public void edificar(String tipo){
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
            calcularImpuesto();
        }else System.out.println(Valor.RED + "No "+ Valor.RESET+ "puedes edificar en esta casilla.");
    }

    private void comprarCasa() {
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
                System.out.println("Has comprado una" + Valor.YELLOW + " casa" + Valor.RESET + " en " + Valor.BLUE + this.nombre + Valor.RESET + " por "
                + Valor.RED + precio + "€"+Valor.RESET +", te quedan " + Valor.RED + duenho.getFortuna()+ "€"+Valor.RESET +".");
                edificaciones.get("casa").add(new Casa(duenho, this, precio));
                grupo.getEdificaciones().get("casa").add(new Casa(duenho, this, precio));
            } else {
                System.out.println("No puedes construir más casas");
            }
        } else {
            System.out.println("No tienes suficiente dinero");
        }
    }
    private void comprarHotel() {
        grupo.getEdificaciones().putIfAbsent("hotel", new ArrayList<>());
        edificaciones.putIfAbsent("hotel", new ArrayList<>());
        float precio=(0.6f*grupo.valor());
        if (duenho.getFortuna() >= precio) {
            if (edificaciones.getOrDefault("casa",new ArrayList<>()).size() == 4) {
                if (grupo.getEdificaciones().get("hotel").size() < grupo.getMiembros().size()) {
                    duenho.setFortuna(duenho.getFortuna() - precio);
                    System.out.println("Has comprado un hotel por " + precio + "€.");
                    edificaciones.get("casa").clear();
                    
                    for (int i = 0; i < 4; i++) {
                        grupo.getEdificaciones().get("casa").remove(0);
                    }
                    
                    edificaciones.get("hotel").add(new Hotel(duenho, this, precio));
                    grupo.getEdificaciones().get("hotel").add(new Hotel(duenho, this, precio));
                } else {
                    System.out.println("No puedes construir más hoteles");
                }
            } else {
                System.out.println("No tienes cuatro casas en esta casilla...");
            }
        } else {
            System.out.println("No tienes suficiente dinero");
        }
    }
    private void comprarPiscina() {
        grupo.getEdificaciones().putIfAbsent("piscina", new ArrayList<>());
        edificaciones.putIfAbsent("piscina", new ArrayList<>());
        float precio=(0.4f*grupo.valor());
        if (duenho.getFortuna() >= precio) {
            int numcasa = edificaciones.getOrDefault("casa",new ArrayList<>()).size();
            int numhotel=edificaciones.getOrDefault("hotel",new ArrayList<>()).size();
            if (((numcasa >= 2) && (numhotel>=1))||numhotel>=2){
                if (grupo.getEdificaciones().getOrDefault("piscina", new ArrayList<>()).size() < grupo.getMiembros().size()) {
                    System.out.println("Has comprado una piscina por " + precio + "€.");
                    duenho.setFortuna(duenho.getFortuna() - precio);
                    
                    edificaciones.get("piscina").add(new Piscina(duenho, this, precio));
                    grupo.getEdificaciones().get("piscina").add(new Piscina(duenho, this, precio));
                } else {
                    System.out.println("No puedes construir más piscinas");
                }
            } else {
                System.out.println("No tienes al menos dos casas y un hotel...");
            }
        } else {
            System.out.println("No tienes suficiente dinero");
        }
    }
    private void comprarPista() {
        grupo.getEdificaciones().putIfAbsent("pista", new ArrayList<>());
        edificaciones.putIfAbsent("pista", new ArrayList<>());
        float precio=(1.25f*grupo.valor());
        if (duenho.getFortuna() >= precio) {
            if (edificaciones.getOrDefault("hotel",new ArrayList<>()).size()>=2){
                if (grupo.getEdificaciones().getOrDefault("pista", new ArrayList<>()).size() < grupo.getMiembros().size()) {
                    System.out.println("Has comprado una pista de deporte por " + precio + "€.");
                    duenho.setFortuna(duenho.getFortuna() - precio);
                    
                    edificaciones.get("pista").add(new Pista(duenho, this, precio));
                    grupo.getEdificaciones().get("pista").add(new Pista(duenho, this, precio));
                } else {
                    System.out.println("No puedes construir más pistas de deporte");
                }
            } else {
                System.out.println("No tienes dos hoteles construidos...");
            }
        } else {
            System.out.println("No tienes suficiente dinero");
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

    public void vender_edificio(String tipo,int num){
        tipo=tipo.toLowerCase();
        if(!tipo.equals("casa")&&!tipo.equals("hotel")&&!tipo.equals("pista")&&!tipo.equals("piscina")){
            System.out.println("Tipo de edificación no válido...");
            return;
        }
        int num_viviendas=edificaciones.getOrDefault(tipo, new ArrayList<>()).size();
        if (num>num_viviendas){
            System.out.println("No tienes "+num+" "+tipo+((num==1) ? "":"s")+ " en esta casilla, tienes "+num_viviendas+"...");
            return;}
        try{
            int ganancias=0;
            for(int i=0;i<num;i++){
                float precio_compra=edificaciones.get(tipo).getLast().getPrecio();
                ganancias+=precio_compra/2;
                float nueva_fortuna=duenho.getFortuna()+precio_compra/2;
                edificaciones.get(tipo).removeLast();
                grupo.getEdificaciones().get(tipo).removeLast();
                duenho.setFortuna(nueva_fortuna);}
            
            int properties_left=edificaciones.getOrDefault(tipo, new ArrayList<>()).size();
            System.out.println("Has vendido con éxito "+ ((num==1) ? "tu ":"tus ") +Valor.YELLOW+num +" "+ tipo+((num==1) ? "":"s") +
            Valor.RESET+" en "+Valor.BLUE+nombre+Valor.RESET+". Recibes "+Valor.GREEN+ganancias+"€."+Valor.RESET+" En la propiedad "+
            (properties_left==1 ? "queda ":"quedan ")+Valor.RED+properties_left+" "+
            tipo+((properties_left==1) ? "":"s")+". "+Valor.RESET);
        }catch (Exception e){
            System.out.println("Error: "+e);
        }
    }

    @Override
    public boolean sePuedeHipotecar(Jugador actual){
        if (esHipotecada){
            System.out.println("La casilla ya está hipotecada...");
            return false;
        }
        else if(this.duenho!=actual){
            System.out.println("No eres el dueño de la casilla...");
            return false;
        }
        else if(!edificaciones.isEmpty()){
            System.out.println("No puedes hipotecar una casilla con edificaciones,debes venderlos primero...");
            return false;
        }
        else{return true;}
    }


    

}

