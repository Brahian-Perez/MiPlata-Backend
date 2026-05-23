package persistence.repository;

import domain.model.*;
import persistence.database.DatabaseConnection;
import persistence.mapper.CuentaMapper;
import repository.ICuentaRepository;

import java.sql.*;
import java.util.ArrayList;

public class CuentaRepositoryImpl implements ICuentaRepository {

    @Override
    public void guardar(Cuenta cuenta, int clienteId) {
        String sql = "INSERT INTO cuentas (numero_cuenta, tipo, saldo, estado, cliente_id) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cuenta.getNumeroCuenta());
            stmt.setString(2, getTipo(cuenta));
            stmt.setDouble(3, cuenta.consultarSaldo());
            stmt.setString(4, cuenta.getEstado().name());
            stmt.setInt(5, clienteId);
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int cuentaId = keys.getInt(1);
                cuenta.setId(cuentaId);
                guardarSubtipo(conn, cuenta, cuentaId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarSubtipo(Connection conn, Cuenta cuenta, int cuentaId) throws SQLException {
        if (cuenta instanceof CuentaAhorros ca) {
            String sql = "INSERT INTO cuentas_ahorros (cuenta_id, tasa_interes) VALUES (?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, cuentaId);
                stmt.setDouble(2, ca.getTasaInteres());
                stmt.executeUpdate();
            }
        } else if (cuenta instanceof CuentaCorriente cc) {
            String sql = "INSERT INTO cuentas_corriente (cuenta_id, porcentaje_sobregiro) VALUES (?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, cuentaId);
                stmt.setDouble(2, cc.getPorcentajeSobregiro());
                stmt.executeUpdate();
            }
        } else if (cuenta instanceof TarjetaCredito tc) {
            String sql = "INSERT INTO tarjetas_credito (cuenta_id, cupo, deuda, numero_cuotas) VALUES (?,?,?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, cuentaId);
                stmt.setDouble(2, tc.getCupo());
                stmt.setDouble(3, tc.getDeuda());
                stmt.setInt(4, tc.getNumeroCuotas());
                stmt.executeUpdate();
            }
        }
    }

    private String getTipo(Cuenta cuenta) {
        if (cuenta instanceof CuentaAhorros) return "AHORROS";
        if (cuenta instanceof CuentaCorriente) return "CORRIENTE";
        if (cuenta instanceof TarjetaCredito) return "TARJETA_CREDITO";
        return "";
    }

    @Override
    public void actualizarSaldo(int cuentaId, double nuevoSaldo) {
        String sql = "UPDATE cuentas SET saldo = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, nuevoSaldo);
            stmt.setInt(2, cuentaId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarTarjeta(int cuentaId, double deuda, int numeroCuotas) {
        String sql = "UPDATE tarjetas_credito SET deuda = ?, numero_cuotas = ? WHERE cuenta_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, deuda);
            stmt.setInt(2, numeroCuotas);
            stmt.setInt(3, cuentaId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Cuenta> listarPorCliente(int clienteId) {
        ArrayList<Cuenta> lista = new ArrayList<>();
        lista.addAll(listarAhorros(clienteId));
        lista.addAll(listarCorriente(clienteId));
        lista.addAll(listarTarjetas(clienteId));
        return lista;
    }

    private ArrayList<CuentaAhorros> listarAhorros(int clienteId) {
        ArrayList<CuentaAhorros> lista = new ArrayList<>();
        String sql = "SELECT c.*, ca.tasa_interes FROM cuentas c " +
                "JOIN cuentas_ahorros ca ON c.id = ca.cuenta_id " +
                "WHERE c.cliente_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(CuentaMapper.toAhorros(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private ArrayList<CuentaCorriente> listarCorriente(int clienteId) {
        ArrayList<CuentaCorriente> lista = new ArrayList<>();
        String sql = "SELECT c.*, cc.porcentaje_sobregiro FROM cuentas c " +
                "JOIN cuentas_corriente cc ON c.id = cc.cuenta_id " +
                "WHERE c.cliente_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(CuentaMapper.toCorriente(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Cuenta buscarPorNumero(String numeroCuenta) {
        // Busca en los 3 tipos
        Cuenta c = buscarAhorrosPorNumero(numeroCuenta);
        if (c != null) return c;
        c = buscarCorrientePorNumero(numeroCuenta);
        if (c != null) return c;
        return buscarTarjetaPorNumero(numeroCuenta);
    }

    private CuentaAhorros buscarAhorrosPorNumero(String numeroCuenta) {
        String sql = "SELECT c.*, ca.tasa_interes FROM cuentas c " +
                "JOIN cuentas_ahorros ca ON c.id = ca.cuenta_id " +
                "WHERE c.numero_cuenta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroCuenta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return CuentaMapper.toAhorros(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private CuentaCorriente buscarCorrientePorNumero(String numeroCuenta) {
        String sql = "SELECT c.*, cc.porcentaje_sobregiro FROM cuentas c " +
                "JOIN cuentas_corriente cc ON c.id = cc.cuenta_id " +
                "WHERE c.numero_cuenta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroCuenta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return CuentaMapper.toCorriente(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private TarjetaCredito buscarTarjetaPorNumero(String numeroCuenta) {
        String sql = "SELECT c.*, tc.cupo, tc.deuda, tc.numero_cuotas FROM cuentas c " +
                "JOIN tarjetas_credito tc ON c.id = tc.cuenta_id " +
                "WHERE c.numero_cuenta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroCuenta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return CuentaMapper.toTarjeta(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private ArrayList<TarjetaCredito> listarTarjetas(int clienteId) {
        ArrayList<TarjetaCredito> lista = new ArrayList<>();
        String sql = "SELECT c.*, tc.cupo, tc.deuda, tc.numero_cuotas FROM cuentas c " +
                "JOIN tarjetas_credito tc ON c.id = tc.cuenta_id " +
                "WHERE c.cliente_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(CuentaMapper.toTarjeta(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
