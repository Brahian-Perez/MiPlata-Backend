package userinterface;

import domain.model.*;
import services.ClienteService;
import services.CuentaService;

import java.util.Scanner;

public class MenuApp {

    private Scanner sc = new Scanner(System.in);

    private ClienteService clienteService;
    private CuentaService cuentaService;

    private static int contadorClientes = 1;

    public MenuApp(ClienteService clienteService, CuentaService cuentaService) {
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
    }

    public void iniciar() {

        int opcion;

        do {
            System.out.println("\n===== BANCO MI PLATA =====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Login");
            System.out.println("3. Administración de clientes");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> registrarCliente();
                case 2 -> login();
                case 3 -> menuAdministracion();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 0);
    }

    // =========================
    // REGISTRO CLIENTE
    // =========================
    private void registrarCliente() {

        System.out.print("Identificación: ");
        String id = sc.nextLine();

        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine();

        System.out.print("Celular: ");
        String celular = sc.nextLine();

        System.out.print("Usuario: ");
        String usuario = sc.nextLine();

        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        Cliente cliente = new Cliente(
                contadorClientes++,
                id,
                nombre,
                celular,
                usuario,
                pass
        );

        clienteService.registrar(cliente);
    }

    // =========================
    // LOGIN
    // =========================
    private void login() {

        System.out.print("Usuario: ");
        String usuario = sc.nextLine();

        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        Cliente cliente = clienteService.login(usuario, pass);

        if (cliente != null) {
            menuCliente(cliente);
        }
    }

    // =========================
    // MENÚ CLIENTE
    // =========================
    private void menuCliente(Cliente cliente) {

        int opcion;

        do {
            System.out.println("\n===== MENÚ CLIENTE =====");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Ver cuentas");
            System.out.println("3. Consignar");
            System.out.println("4. Retirar");
            System.out.println("5. Ver movimientos");
            System.out.println("6. Transferir");
            System.out.println("7. Comprar con tarjeta de crédito");
            System.out.println("8. Pagar tarjeta de crédito");
            System.out.println("9. Consultar tarjeta de crédito");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1 -> crearCuenta(cliente);
                case 2 -> cliente.listarCuentas();
                case 3 -> consignar(cliente);
                case 4 -> retirar(cliente);
                case 5 -> verMovimientos(cliente);
                case 6 -> transferir(cliente);

                case 7 -> comprarTarjeta(cliente);
                case 8 -> pagarTarjeta(cliente);
                case 9 -> consultarTarjeta(cliente);

                case 0 -> cliente.cerrarSesion();
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 0);
    }

    // =========================
    // ADMIN CLIENTES
    // =========================
    private void menuAdministracion() {

        int op;

        do {
            System.out.println("\n--- ADMIN CLIENTES ---");
            System.out.println("1. Listar clientes");
            System.out.println("2. Editar cliente");
            System.out.println("3. Eliminar cliente");
            System.out.println("0. Volver");

            op = sc.nextInt();
            sc.nextLine();

            switch (op) {

                case 1 -> listarClientes();
                case 2 -> editarCliente();
                case 3 -> eliminarCliente();
            }

        } while (op != 0);
    }

    private void listarClientes() {

        for (Cliente c : clienteService.listar()) {
            System.out.println(
                    c.getUsuario() + " | " +
                            c.getNombreCompleto() + " | " +
                            c.getCelular()
            );
        }
    }

    private void editarCliente() {

        System.out.print("Usuario: ");
        String usuario = sc.nextLine();

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nuevo celular: ");
        String celular = sc.nextLine();

        clienteService.actualizar(usuario, nombre, celular);
    }

    private void eliminarCliente() {

        System.out.print("Usuario a eliminar: ");
        String usuario = sc.nextLine();

        System.out.print("Confirmar (si/no): ");
        String conf = sc.nextLine();

        if (conf.equalsIgnoreCase("si")) {
            clienteService.eliminar(usuario);
        }
    }

    // =========================
    // CUENTAS
    // =========================
    private void crearCuenta(Cliente cliente) {

        System.out.println("Tipo de cuenta:");
        System.out.println("1. Ahorros");
        System.out.println("2. Corriente");
        System.out.println("3. Tarjeta de crédito");

        int tipo = sc.nextInt();
        sc.nextLine();

        System.out.print("Número de cuenta: ");
        String numero = sc.nextLine();

        Cuenta cuenta = null;

        switch (tipo) {
            case 1 -> cuenta = new CuentaAhorros(numero, 0);
            case 2 -> cuenta = new CuentaCorriente(numero, 0);
            case 3 -> cuenta = new TarjetaCredito(numero, 1000000);
            default -> System.out.println("Tipo inválido");
        }

        if (cuenta != null) {
            cuentaService.crearCuenta(cliente, cuenta);
        }
    }

    private void consignar(Cliente cliente) {

        System.out.print("Número de cuenta: ");
        String numero = sc.nextLine();

        System.out.print("Monto: ");
        double monto = sc.nextDouble();
        sc.nextLine();

        cuentaService.consignar(cliente, numero, monto);
    }

    private void retirar(Cliente cliente) {

        System.out.print("Número de cuenta: ");
        String numero = sc.nextLine();

        System.out.print("Monto: ");
        double monto = sc.nextDouble();
        sc.nextLine();

        cuentaService.retirar(cliente, numero, monto);
    }

    private void transferir(Cliente cliente) {

        System.out.print("Cuenta origen: ");
        String origen = sc.nextLine();

        System.out.print("Cuenta destino: ");
        String destino = sc.nextLine();

        System.out.print("Monto: ");
        double monto = sc.nextDouble();
        sc.nextLine();

        cuentaService.transferir(cliente, origen, destino, monto);
    }

    private void verMovimientos(Cliente cliente) {

        System.out.print("Número de cuenta: ");
        String numero = sc.nextLine();

        Cuenta cuenta = cliente.buscarCuenta(numero);

        if (cuenta != null) {
            cuenta.mostrarMovimientos();
        } else {
            System.out.println("Cuenta no encontrada.");
        }
    }

    // =========================
    // TARJETA DE CRÉDITO
    // =========================
    private void comprarTarjeta(Cliente cliente) {

        System.out.print("Número de tarjeta: ");
        String numero = sc.nextLine();

        System.out.print("Monto compra: ");
        double monto = sc.nextDouble();

        System.out.print("Cuotas: ");
        int cuotas = sc.nextInt();
        sc.nextLine();

        cuentaService.comprarConTarjeta(cliente, numero, monto, cuotas);
    }

    private void pagarTarjeta(Cliente cliente) {

        System.out.print("Número de tarjeta: ");
        String numero = sc.nextLine();

        System.out.print("Monto a pagar: ");
        double monto = sc.nextDouble();
        sc.nextLine();

        cuentaService.pagarTarjeta(cliente, numero, monto);
    }

    private void consultarTarjeta(Cliente cliente) {

        System.out.print("Número de tarjeta: ");
        String numero = sc.nextLine();

        Cuenta cuenta = cliente.buscarCuenta(numero);

        if (cuenta instanceof TarjetaCredito tarjeta) {

            System.out.println("Deuda: " + tarjeta.getDeuda());
            System.out.println("Cupo disponible: " + tarjeta.getCupoDisponible());

        } else {
            System.out.println("No es una tarjeta válida");
        }
    }
}