package monopoly;
import Excepciones.Ejecucion.*;
import Excepciones.MalUsoComando.*;

public interface Comando {
    void anadirjugador();
    void jugadorInfo();
    void edificar(String tipo) throws EdificarSinPoder, DineroError, InstanciaIncorrecta;
    void listarJugadores();
    void listarAvatares();
    void listarVenta();
    void listarEdificios() ;
    void listarGrupo(String grupo);
    void lanzarDados() throws AcabarTurno,EdificarSinPoder, DineroError, InstanciaIncorrecta;
    void lanzarDados(String dado1, String dado2) throws LanzarDado, AcabarTurno, EdificarSinPoder, DineroError, InstanciaIncorrecta ;
    void lanzarDados(int valor) throws EdificarSinPoder, DineroError, InstanciaIncorrecta ;
    void comprar(String nombre) throws Exception;
    void salirCarcel();
    void acabar() throws AcabarTurno;
    void acabarTurnoForce();
    void descJugador(String nombre) throws JugadorNoEncontrado;
    void descAvatar(String nombre) throws AvatarNoEncontrado;
    void descCasilla(String nombre) throws CasillaNoEncontrada;
    void vender_edificio(String tipo, String casilla,String num) throws VenderSinTener, CasillaNoEncontrada, InstanciaIncorrecta, EdificioNotFound;
    void verTablero();
    void endGame();
    void fortunaManual(float cantidad);
    void cambiarModo();
    void bancarrota();
    void hipotecar(String nombre) throws HipotecaSinTener;
    void deshipotecar(String nombre);
    void avanzar() throws AvanzarSinPoder, EdificarSinPoder, DineroError, InstanciaIncorrecta ;
    void verTratos();
    void crearTrato(String[] partes) throws TratoIncompatible, JugadorNoEncontrado, CasillaNoEncontrada;
    void aceptarTrato(String id) throws TratoIncompatible, TratoNoEncontrado, DineroError;
    void eliminarTrato(String id) throws TratoNoEncontrado;
    void estadisticas(String nombre) throws JugadorNoEncontrado;
    void estadisticasjuego();
}