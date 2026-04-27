package services;

import domain.model.Cliente;
import domain.model.Cuenta;
import domain.model.TarjetaCredito;

public class CuentaServiceImpl implements CuentaService {


    private ClienteService clienteService;

    public CuentaServiceImpl(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public void crearCuenta(Cliente cliente, Cuenta cuenta) {
        try {
            cliente.agregarCuenta(cuenta);

            System.out.println("Cuenta creada: " + cuenta.getNumeroCuenta());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consignar(Cliente cliente, String numero, double monto) {
        try {
            Cuenta cuenta = cliente.buscarCuenta(numero);

            if (cuenta != null) {
                cuenta.consignar(monto);
                System.out.println("Consignación exitosa.");
            } else {
                System.out.println("Cuenta no encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retirar(Cliente cliente, String numero, double monto) {
        try {
            Cuenta cuenta = cliente.buscarCuenta(numero);

            if (cuenta != null) {
                cuenta.retirar(monto);
            } else {
                System.out.println("Cuenta no encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transferir(Cliente cliente, String origen, String destino, double monto) {
        try {
            Cuenta cuentaOrigen = cliente.buscarCuenta(origen);

            // brahian, hacer la busqueda de la cuenta de destino en el array de clientes
            Cuenta cuentaDestino = null;
            for (Cliente c : clienteService.listar()) {
                cuentaDestino = c.buscarCuenta(destino);
                if (cuentaDestino != null) break;
            }

            if (cuentaOrigen instanceof TarjetaCredito) {
                System.out.println("Tarjeta de crédito no puede transferir");
                return;
            }

            if (cuentaOrigen == null || cuentaDestino == null) {
                System.out.println("Cuenta origen o destino no encontrada");
                return;
            }

            cuentaOrigen.transferir(cuentaDestino, monto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}