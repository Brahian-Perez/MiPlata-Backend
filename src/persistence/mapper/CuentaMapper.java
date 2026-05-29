package persistence.mapper;

import domain.enums.EstadoCuenta;
import domain.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CuentaMapper {

    public static CuentaAhorros toAhorros(ResultSet rs) throws SQLException {
        CuentaAhorros cuenta = new CuentaAhorros(
                rs.getString("numero_cuenta"),
                rs.getDouble("saldo")
        );
        cuenta.setNumeroCuenta("CAH-" + cuenta.getNumeroCuenta());
        cuenta.setId(rs.getInt("id"));
        cuenta.setEstado(EstadoCuenta.valueOf(rs.getString("estado")));
        cuenta.setFechaApertura(rs.getTimestamp("fecha_apertura").toLocalDateTime());
        cuenta.setTasaInteres(rs.getDouble("tasa_interes"));
        return cuenta;
    }

    public static CuentaCorriente toCorriente(ResultSet rs) throws SQLException {
        CuentaCorriente cuenta = new CuentaCorriente(
                rs.getString("numero_cuenta"),
                rs.getDouble("saldo")
        );
        cuenta.setNumeroCuenta("CCO-" + cuenta.getNumeroCuenta());
        cuenta.setId(rs.getInt("id"));
        cuenta.setEstado(EstadoCuenta.valueOf(rs.getString("estado")));
        cuenta.setFechaApertura(rs.getTimestamp("fecha_apertura").toLocalDateTime());
        cuenta.setPorcentajeSobregiro(rs.getDouble("porcentaje_sobregiro"));
        return cuenta;
    }

    public static TarjetaCredito toTarjeta(ResultSet rs) throws SQLException {
        TarjetaCredito cuenta = new TarjetaCredito(
                rs.getString("numero_cuenta"),
                rs.getDouble("cupo")
        );
        cuenta.setNumeroCuenta("TCR-" + cuenta.getNumeroCuenta());
        cuenta.setId(rs.getInt("id"));
        cuenta.setEstado(EstadoCuenta.valueOf(rs.getString("estado")));
        cuenta.setFechaApertura(rs.getTimestamp("fecha_apertura").toLocalDateTime());
        cuenta.setDeuda(rs.getDouble("deuda"));
        cuenta.setNumeroCuotas(rs.getInt("numero_cuotas"));
        return cuenta;
    }
}
