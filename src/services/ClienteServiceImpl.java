package services;

import domain.model.Cliente;
import repository.IClienteRepository;

import java.util.ArrayList;

public class ClienteServiceImpl implements ClienteService {

    private IClienteRepository repo;

    public ClienteServiceImpl(IClienteRepository repo) {
        this.repo = repo;
    }

    @Override
    public void registrar(Cliente cliente) {
        try {
            repo.guardar(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente login(String usuario, String contrasena) {
        try {
            Cliente c = repo.buscarPorUsuario(usuario);

            if (c != null && c.autenticar(usuario, contrasena)) {
                return c;
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void actualizar(String usuario, String nuevoNombre, String nuevoCelular) {
        try {
            repo.actualizar(usuario, nuevoNombre, nuevoCelular);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(String usuario) {
        try {
            repo.eliminar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Cliente> listar() {
        try {
            return repo.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
