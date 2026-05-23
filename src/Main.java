import persistence.repository.ClienteRepositoryImpl;
import persistence.repository.CuentaRepositoryImpl;
import persistence.repository.MovimientoRepositoryImpl;
import repository.IClienteRepository;
import repository.ICuentaRepository;
import repository.IMovimientoRepository;
import services.ClienteService;
import services.ClienteServiceImpl;
import services.CuentaService;
import services.CuentaServiceImpl;
import userinterface.MenuApp;

public class Main {
    public static void main(String[] args) {

        // 1. REPOSITORIES (JDBC)
        IClienteRepository clienteRepository = new ClienteRepositoryImpl();
        ICuentaRepository cuentaRepository = new CuentaRepositoryImpl();
        IMovimientoRepository movimientoRepository = new MovimientoRepositoryImpl();

        // 2. SERVICES
        ClienteService clienteService = new ClienteServiceImpl(clienteRepository, cuentaRepository, movimientoRepository);
        CuentaService cuentaService = new CuentaServiceImpl(cuentaRepository, movimientoRepository);

        // 3. UI
        MenuApp menu = new MenuApp(clienteService, cuentaService);
        menu.iniciar();
    }
}
