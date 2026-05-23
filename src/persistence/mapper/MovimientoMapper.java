package persistence.mapper;

import domain.enums.TipoMovimiento;
import domain.model.Movimiento;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovimientoMapper {

    public static Movimiento toEntity(ResultSet rs) throws SQLException {
        return new Movimiento(
                rs.getInt("id"),
                rs.getTimestamp("fecha_hora").toLocalDateTime(),
                TipoMovimiento.valueOf(rs.getString("tipo")),
                rs.getDouble("valor"),
                rs.getDouble("saldo_posterior"),
                rs.getString("descripcion")
        );
    }

    public static ArrayList<Movimiento> toEntityList(ResultSet rs) throws SQLException {
        ArrayList<Movimiento> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(toEntity(rs));
        }
        return lista;
    }
}
