package services;

import domain.model.Cliente;
import domain.model.Cuenta;
import domain.model.Movimiento;
import repository.IClienteRepository;
import repository.ICuentaRepository;
import repository.IMovimientoRepository;

import java.util.ArrayList;

public class ClienteServiceImpl implements ClienteService {

    private IClienteRepository repo;
    private ICuentaRepository cuentaRepo;
    private IMovimientoRepository movimientoRepo;

    public ClienteServiceImpl(IClienteRepository repo, ICuentaRepository cuentaRepo, IMovimientoRepository movimientoRepo) {
        this.repo = repo;
        this.cuentaRepo = cuentaRepo;
        this.movimientoRepo = movimientoRepo;
    }

    @Override
    public void registrar(Cliente cliente) {
        try {
            repo.guardar(cliente);
            System.out.println("Cliente registrado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente login(String usuario, String contrasena) {
        try {
            Cliente c = repo.buscarPorUsuario(usuario);
            if (c != null && c.autenticar(usuario, contrasena)) {
                ArrayList<Cuenta> cuentas = cuentaRepo.listarPorCliente(c.getId());
                for (Cuenta cuenta : cuentas) {
                    ArrayList<Movimiento> movs = movimientoRepo.listarPorCuenta(cuenta.getId());
                    cuenta.cargarMovimientos(movs);
                }
                c.setCuentas(cuentas);
                return c;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void actualizar(String usuario, String nuevoNombre, String nuevoCelular, String nuevaContrasena) {
        try {
            repo.actualizar(usuario, nuevoNombre, nuevoCelular,nuevaContrasena);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(String usuario) {
        try {
            repo.eliminar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Cliente> listar() {
        try {
            return repo.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
