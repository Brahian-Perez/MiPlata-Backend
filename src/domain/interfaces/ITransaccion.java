package domain.interfaces;

import domain.model.Cuenta;


public interface ITransaccion {
    void consignar(double monto);
    void retirar(double monto);
    double consultarSaldo();
    void transferir(Cuenta destino, double monto);
}