package services;

import domain.model.Cliente;
import domain.model.Cuenta;

public interface CuentaService {
    void crearCuenta(Cliente cliente, Cuenta cuenta);
    void consignar(Cliente cliente, String numero, double monto);
    void retirar(Cliente cliente, String numero, double monto);
    void transferir(Cliente cliente, String origen, String destino, double monto);
    void comprarConTarjeta(Cliente cliente, String numeroCuenta, double monto, int cuotas);
    void pagarTarjeta(Cliente cliente, String numeroCuenta, double monto);
    void eliminarCuenta(Cliente cliente, String numeroCuenta);
}
