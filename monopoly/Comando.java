package monopoly;

public interface Comando {
    void anadirjugador();
    void jugadorInfo();
    void edificar(String nombre);
    void listarJugadores();
    void listarAvatares();
    void listarVenta();
    void listarEdificios();
    void listarGrupo(String grupo);
    void lanzarDados();
    void lanzarDados(String dado1, String dado2);
    void lanzarDados(int valor);
    void comprar(String nombre) throws Exception;
    void salirCarcel();
    void acabar();
    void acabarTurnoForce();
    void descJugador(String nombre);
    void descAvatar(String nombre);
    void descCasilla(String nombre);
    void vender_edificio(String edificio, String nombre, String cantidad);
    void verTablero();
    void endGame();
    void fortunaManual(float cantidad);
    void cambiarModo();
    void bancarrota();
    void hipotecar(String nombre);
    void deshipotecar(String nombre);
    void avanzar();
    void verTratos();
    void crearTrato(String[] partes);
    void aceptarTrato(String id);
    void eliminarTrato(String id);
    void estadisticas(String nombre);
    void estadisticasjuego();
}