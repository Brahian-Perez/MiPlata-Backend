package persistence.mapper;

import domain.model.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioMapper {

    public static Cliente toEntity (ResultSet rs)throws SQLException{
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setIdentificacion(rs.getString("identificacion"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setCelular(rs.getString("celular"));
        cliente.setUsuario(rs.getString("usuario"));
        cliente.setContrasena(rs.getString("contrasena"));

        return cliente;
    }

    public static List<Cliente> toEntityList(ResultSet rs) throws SQLException {
        List<Cliente> cliente = new ArrayList<>();
        while (rs.next()) {
            cliente.add(toEntity(rs));
        }
        return cliente;
    }
}
