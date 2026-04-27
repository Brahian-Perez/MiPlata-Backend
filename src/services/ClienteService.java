package services;

import domain.model.Cliente;

import java.util.ArrayList;

public interface ClienteService {


    void registrar(Cliente cliente);

    Cliente login(String usuario, String contrasena);

    void actualizar(String usuario, String nuevoNombre, String nuevoCelular);

    void eliminar(String usuario);

    ArrayList<Cliente> listar();
}