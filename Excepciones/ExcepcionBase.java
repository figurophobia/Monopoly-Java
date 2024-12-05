package Excepciones;


// Excepción base para el proyecto, tendrá 3 niveles de herencia
// De 3 tipos de excepciones
// Excepciones de formato incorrecto de los comandos 
// (las cuales imprimirán un mensaje de error, con la sintaxis del comando en cuestión)
// Excepciones mal uso de comandos 
// (las cuales imprimirán un mensaje de error, con el motivo jugable por lo que no puedes usar ese comando)
// Excepciones de error en la ejecución de los comandos 
// (las cuales imprimirán un mensaje de error, con el motivo técnico por lo que no puedes usar ese comando)
// Cada uno de estos tipos tendrá su excepción base que heredará de esta clase, y a su vez para cada tipo de excepción, 
// habrá una excepción concreta que heredará de la excepción base de su tipo
public class ExcepcionBase  extends Exception{
    public ExcepcionBase(String mensaje){
        super(mensaje);
    }

}
