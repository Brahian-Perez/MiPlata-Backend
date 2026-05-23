package repository;

import domain.model.Movimiento;
import java.util.ArrayList;

public interface IMovimientoRepository {
    void guardar(Movimiento movimiento, int cuentaId);
    ArrayList<Movimiento> listarPorCuenta(int cuentaId);
}
