package repository;


import domain.model.Cliente;
import domain.model.Cuenta;
import services.ClienteServiceImpl;

import java.util.ArrayList;

public class CuentaRepository {
    ClienteServiceImpl clienteServiceImpl;

    private ArrayList<Cuenta> cuentas;

    public CuentaRepository(ClienteRepository clienteRepository) {
        this.cuentas = new ArrayList<>();
    }

    public void guardar(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    public Cuenta buscarPorNumero(String numero) {
        for (Cuenta c : cuentas) {
            if (c.getNumeroCuenta().equals(numero)) {
                return c;
            }
        }
        return null;
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

    public ArrayList<Cuenta> listar() {
        return cuentas;
    }
}