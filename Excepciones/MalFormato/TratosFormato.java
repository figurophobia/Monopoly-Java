package Excepciones.MalFormato;

public class TratosFormato extends MalFormatoBase {
    public TratosFormato(){
        //Dice el formato y los diferentes casos posibles de tratos:
        /*
         * (SolarX, SolarY)
         * (SolarX, dinero)
         * (dinero, SolarY)
         * (SolarX, SolarY, dinero)
         * (SolarX, dinero, SolarY)
         */
        super("trato [nombre jugador] cambiar [caso sin parentesis ni comas]\n" +
              "Casos:\n" +
              "  - SolarX, SolarY\n" +
              "  - SolarX, dinero\n" +
              "  - dinero, SolarY\n" +
              "  - SolarX, SolarY, dinero\n" +
              "  - SolarX, dinero, SolarY");    }   
}
