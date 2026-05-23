package persistence.repository;

import domain.model.Movimiento;
import persistence.database.DatabaseConnection;
import persistence.mapper.MovimientoMapper;
import repository.IMovimientoRepository;

import java.sql.*;
import java.util.ArrayList;

public class MovimientoRepositoryImpl implements IMovimientoRepository {

    @Override
    public void guardar(Movimiento movimiento, int cuentaId) {
        String sql = "INSERT INTO movimientos (cuenta_id, fecha_hora, tipo, valor, saldo_posterior, descripcion) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cuentaId);
            stmt.setTimestamp(2, Timestamp.valueOf(movimiento.getFechaHora()));
            stmt.setString(3, movimiento.getTipo().name());
            stmt.setDouble(4, movimiento.getValor());
            stmt.setDouble(5, movimiento.getSaldoPosterior());
            stmt.setString(6, movimiento.getDescripcion());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Movimiento> listarPorCuenta(int cuentaId) {
        ArrayList<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimientos WHERE cuenta_id = ? ORDER BY fecha_hora ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cuentaId);
            ResultSet rs = stmt.executeQuery();
            lista.addAll(MovimientoMapper.toEntityList(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
