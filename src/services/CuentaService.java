package services;

import domain.model.Cliente;
import domain.model.Cuenta;

public interface CuentaService {
    //NECESITO QUE AL CREAR LA CUENTA DE AHORROS SE APLIQUE EL CODIGO CAH-  JUSTO AL INICIO DEL NUMERO ASIGNADO POR EL CLIENTTE

    void crearCuenta(Cliente cliente, Cuenta cuenta);
    void consignar(Cliente cliente, String numero, double monto);
    void retirar(Cliente cliente, String numero, double monto);
    void transferir(Cliente cliente, String origen, String destino, double monto);
    void comprarConTarjeta(Cliente cliente, String numeroCuenta, double monto, int cuotas);
    void pagarTarjeta(Cliente cliente, String numeroCuenta, double monto);
}
