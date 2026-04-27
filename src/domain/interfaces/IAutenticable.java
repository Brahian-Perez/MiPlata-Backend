package domain.interfaces;

public interface IAutenticable {
    boolean autenticar(String usuario, String contrasena);
    void cambiarContrasena(String actual, String nueva);
}