package repository;

import domain.model.Cliente;
import domain.model.Cuenta;
import services.ClienteServiceImpl;

import java.util.ArrayList;

public class ClienteRepository {
    ClienteServiceImpl clienteServiceImpl;

    private ArrayList<Cliente> clientes = new ArrayList<>();


    public void guardar(Cliente cliente) {
        clientes.add(cliente);
        System.out.println(cliente);
    }

    public ArrayList<Cliente> listar() {
        return clientes;
    }

    public Cliente buscarPorUsuario(String usuario) {

        for (Cliente c : clientes) {
            if (c.getUsuario().equals(usuario)) {
                return c;
            }
        }
        return null;
    }


    //Actualizar datos del cliente
    public void actualizar(String usuario, String nuevoNombre, String nuevoCelular, String nuevaContrasena) {
        Cliente c = buscarPorUsuario(usuario);
        if (c != null) {
            c.editarPerfil(nuevoNombre, nuevoCelular);
            c.cambiarContrasena(c.getContrasena(), nuevaContrasena);
        }
    }



    public void eliminar(String usuario) {

        Cliente c = buscarPorUsuario(usuario);

        if (c != null) {
            clientes.remove(c);
        }
    }


    public Cuenta buscarPorNumeroEnCLientes(String numero){
        ArrayList<Cliente> clientes = clienteServiceImpl.listar();
        for(Cliente client: clienteServiceImpl.listar()){
            for (Cuenta cuentas : client.getCuentas()){
                if (cuentas.getNumeroCuenta().equals(numero)) {
                    return cuentas;
                }
            }
        }
        return null;
    }
}