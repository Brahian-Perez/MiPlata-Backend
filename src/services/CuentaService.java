package services;


import domain.model.Cliente;
import domain.model.Cuenta;

public interface CuentaService {
    void transferir(Cliente cliente, String origen, String destino, double monto);

    void crearCuenta(Cliente cliente, Cuenta cuenta);

    void consignar(Cliente cliente, String numero, double monto);

    void retirar(Cliente cliente, String numero, double monto);
}