import persistence.repository.ClienteRepositoryImpl;
import repository.IClienteRepository;
import services.ClienteService;
import services.ClienteServiceImpl;
import services.CuentaService;
import services.CuentaServiceImpl;
import userinterface.MenuApp;

public class Main {
    public static void main(String[] args) {

        // 1. REPOSITORY (JDBC)
        IClienteRepository clienteRepository = new ClienteRepositoryImpl();

        // 2. SERVICES
        ClienteService clienteService = new ClienteServiceImpl(clienteRepository);
        CuentaService cuentaService = new CuentaServiceImpl(clienteService);

        // 3. UI
        MenuApp menu = new MenuApp(clienteService, cuentaService);
        menu.iniciar();
    }
}
