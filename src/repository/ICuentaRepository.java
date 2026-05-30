package repository;

import domain.model.Cuenta;
import java.util.ArrayList;

public interface ICuentaRepository {
    void guardar(Cuenta cuenta, int clienteId);
    void actualizarSaldo(int cuentaId, double nuevoSaldo);
    void actualizarTarjeta(int cuentaId, double deuda, int numeroCuotas);
    void eliminar(int cuentaId);
    ArrayList<Cuenta> listarPorCliente(int clienteId);
    Cuenta buscarPorNumero(String numeroCuenta);

}
