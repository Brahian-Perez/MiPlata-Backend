package persistence.repository;

import domain.model.Cliente;
import persistence.database.DatabaseConnection;
import persistence.mapper.UsuarioMapper;
import repository.IClienteRepository;

import java.sql.*;
import java.util.ArrayList;

public class ClienteRepositoryImpl implements IClienteRepository {

    @Override
    public void guardar(Cliente cliente) {
        String sql = "INSERT INTO clientes (identificacion, nombre, celular, usuario, contrasena) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getIdentificacion());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getCelular());
            stmt.setString(4, cliente.getUsuario());
            stmt.setString(5, cliente.getContrasena());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Cliente> listar() {
        String sql = "SELECT * FROM clientes";
        ArrayList<Cliente> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            lista.addAll(UsuarioMapper.toEntityList(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Cliente buscarPorUsuario(String usuario) {
        String sql = "SELECT * FROM clientes WHERE usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return UsuarioMapper.toEntity(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void eliminar(String usuario) {
        String sql = "DELETE FROM clientes WHERE usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(String usuario, String nuevoNombre, String nuevoCelular) {
        String sql = "UPDATE clientes SET nombre = ?, celular = ? WHERE usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevoCelular);
            stmt.setString(3, usuario);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
