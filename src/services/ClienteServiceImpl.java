package services;

import domain.model.Cliente;
import repository.ClienteRepository;

import java.util.ArrayList;

public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository repo;


    public ClienteServiceImpl(ClienteRepository repo) {
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
            System.out.println("Error: "+e);
            return null;
        }
    }

    @Override
    public void actualizar(String usuario, String nuevoNombre, String nuevoCelular) {
        try {
            Cliente c = repo.buscarPorUsuario(usuario);

            if (c != null) {
                c.setNombre(nuevoNombre);
                c.setCelular(nuevoCelular);
            }
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