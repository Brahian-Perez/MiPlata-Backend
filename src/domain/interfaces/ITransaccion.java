package domain.interfaces;

import domain.model.Movimiento;
import java.util.ArrayList;

public interface ITransaccion {
    void consignar(double monto);
    void retirar(double monto);
    double consultarSaldo();
    ArrayList<Movimiento> obtenerMovimientos();
}
