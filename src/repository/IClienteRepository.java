package repository;

import domain.model.Cliente;

import java.util.ArrayList;

public interface IClienteRepository {
    void guardar(Cliente cliente);
    ArrayList<Cliente> listar();
    Cliente buscarPorUsuario(String usuario);
    void eliminar(String usuario);
    void actualizar(String usuario, String nuevoNombre, String nuevoCelular, String nuevaContrasena);
}
