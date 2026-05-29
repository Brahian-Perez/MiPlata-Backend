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
        // Validar que los datos no sean nulos o vacíos
        if (cliente.getIdentificacion() == null || cliente.getIdentificacion().isEmpty() ||
            cliente.getNombreCompleto() == null || cliente.getNombreCompleto().isEmpty() ||
            cliente.getCelular() == null || cliente.getCelular().isEmpty() ||
            cliente.getUsuario() == null || cliente.getUsuario().isEmpty() ||
            cliente.getContrasena() == null || cliente.getContrasena().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        //validación para cliente con el mismo usuario
        if (buscarPorUsuario(cliente.getUsuario()) != null) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        String sql = "INSERT INTO clientes (identificacion, nombre_completo, celular, usuario, contrasena) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getIdentificacion());
            stmt.setString(2, cliente.getNombreCompleto());
            stmt.setString(3, cliente.getCelular());
            stmt.setString(4, cliente.getUsuario());
            stmt.setString(5, cliente.getContrasena());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error al guardar los datos");
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
    public void actualizar(String usuario, String nuevoNombre, String nuevoCelular, String nuevaContrasena) {
        String sql = "UPDATE clientes SET nombre_completo = ?, celular = ?, contrasena = ? WHERE usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevoCelular);
            stmt.setString(3, nuevaContrasena);
            stmt.setString(4, usuario);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Cliente actualizado en base de datos correctamente.");
            } else {
                System.out.println("No se encontró el usuario para actualizar.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar los datos en la base de datos.");
        }
    }
}
