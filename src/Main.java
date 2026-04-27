import repository.ClienteRepository;
import repository.CuentaRepository;
import services.ClienteService;
import services.ClienteServiceImpl;
import services.CuentaService;
import services.CuentaServiceImpl;
import userinterface.MenuApp;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // =========================
        // 1. REPOSITORIES
        // =========================
        ClienteRepository clienteRepository = new ClienteRepository();
        CuentaRepository cuentaRepository = new CuentaRepository(clienteRepository);  // ✅ Pasar clienteRepository

        // =========================
        // 2. SERVICES
        // =========================
        ClienteService clienteService = new ClienteServiceImpl(clienteRepository);
        CuentaService cuentaService = new CuentaServiceImpl(clienteService);  // ✅ Pasar cuentaRepository

        // =========================
        // 3. UI (MENÚ)
        // =========================
        MenuApp menu = new MenuApp(clienteService, cuentaService);

        // =========================
        // 4. INICIAR SISTEMA
        // =========================
        menu.iniciar();


    }
}