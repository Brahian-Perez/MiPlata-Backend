package persistence.repository;

import domain.model.Cliente;
import persistence.database.DatabaseConnection;
import persistence.mapper.UsuarioMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRepositoryImpl {

    public Cliente findById(int id){
        String sql = "SELECT * FROM CLIENTES WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return UsuarioMapper.toEntity(rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private int id;
    private String identificacion;
    private String nombre;
    private String celular;
    private String usuario;
    private String contrasena;

    public String saveCliente (Cliente cliente){
        String sql = "INSERT INTO CLIENTES (IDENTIFICACION, NOMBRE, CELULAR, USUARIO, CONTRASENA) VALUES (?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,cliente.getIdentificacion());
            stmt.setString(2,cliente.getNombre());
            stmt.setString(3,cliente.getCelular());
            stmt.setString(4,cliente.getUsuario());
            stmt.setString(5, cliente.getContrasena());
            ResultSet rs = stmt.executeQuery(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "El usuario se ha guardado con exito";
    }
}
