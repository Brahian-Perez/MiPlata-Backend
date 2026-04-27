package domain.interfaces;

import domain.model.Cuenta;

public interface ITransferible {
    void transferir(Cuenta destino, double monto);
}
