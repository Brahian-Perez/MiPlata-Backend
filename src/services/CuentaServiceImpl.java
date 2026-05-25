package services;

import domain.model.*;
import repository.ICuentaRepository;
import repository.IMovimientoRepository;

import java.util.ArrayList;

public class CuentaServiceImpl implements CuentaService {

    private ICuentaRepository cuentaRepo;
    private IMovimientoRepository movimientoRepo;

    public CuentaServiceImpl(ICuentaRepository cuentaRepo, IMovimientoRepository movimientoRepo) {
        this.cuentaRepo = cuentaRepo;
        this.movimientoRepo = movimientoRepo;
    }

    @Override
    public void crearCuenta(Cliente cliente, Cuenta cuenta) {
        try {
            cuentaRepo.guardar(cuenta, cliente.getId());
            System.out.println("Cuenta creada exitosamente.");
            cliente.agregarCuenta(cuenta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consignar(Cliente cliente, String numero, double monto) {
        try {
            Cuenta cuenta = cliente.buscarCuenta(numero);
            if (cuenta == null) { System.out.println("Cuenta no encontrada."); return; }

            cuenta.consignar(monto);
            cuentaRepo.actualizarSaldo(cuenta.getId(), cuenta.consultarSaldo());
            persistirUltimo(cuenta);
            System.out.println("Monto total:"+ (cuenta.getSaldo()));
            System.out.println("Consignación exitosa.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retirar(Cliente cliente, String numero, double monto) {
        try {
            Cuenta cuenta = cliente.buscarCuenta(numero);
            if (cuenta == null) { System.out.println("Cuenta no encontrada."); return; }

            int antes = cuenta.obtenerMovimientos().size();
            cuenta.retirar(monto);
            if (cuenta.obtenerMovimientos().size() > antes) {
                cuentaRepo.actualizarSaldo(cuenta.getId(), cuenta.consultarSaldo());
                persistirUltimo(cuenta);
                System.out.println("Retiro exitoso.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transferir(Cliente cliente, String origen, String destino, double monto) {
        try {
            Cuenta cuentaOrigen = cliente.buscarCuenta(origen);
            Cuenta cuentaDestino = cuentaRepo.buscarPorNumero(destino);

            if (cuentaOrigen == null || cuentaDestino == null) {
                System.out.println("Cuenta origen o destino no encontrada");
                return;
            }

            int antesOrigen = cuentaOrigen.obtenerMovimientos().size();
            cuentaOrigen.transferir(cuentaDestino, monto);

            if (cuentaOrigen.obtenerMovimientos().size() > antesOrigen) {
                cuentaRepo.actualizarSaldo(cuentaOrigen.getId(), cuentaOrigen.consultarSaldo());
                cuentaRepo.actualizarSaldo(cuentaDestino.getId(), cuentaDestino.consultarSaldo());
                persistirUltimo(cuentaOrigen);
                persistirUltimo(cuentaDestino);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void comprarConTarjeta(Cliente cliente, String numeroCuenta, double monto, int cuotas) {
        try {
            Cuenta cuenta = cliente.buscarCuenta(numeroCuenta);
            if (!(cuenta instanceof TarjetaCredito tarjeta)) {
                System.out.println("No es una tarjeta válida");
                return;
            }
            int antes = tarjeta.obtenerMovimientos().size();
            tarjeta.comprar(monto, cuotas);
            if (tarjeta.obtenerMovimientos().size() > antes) {
                cuentaRepo.actualizarTarjeta(tarjeta.getId(), tarjeta.getDeuda(), tarjeta.getNumeroCuotas());
                persistirUltimo(tarjeta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pagarTarjeta(Cliente cliente, String numeroCuenta, double monto) {
        try {
            Cuenta cuenta = cliente.buscarCuenta(numeroCuenta);
            if (!(cuenta instanceof TarjetaCredito tarjeta)) {
                System.out.println("No es una tarjeta válida");
                return;
            }
            tarjeta.pagar(monto);
            cuentaRepo.actualizarTarjeta(tarjeta.getId(), tarjeta.getDeuda(), tarjeta.getNumeroCuotas());
            persistirUltimo(tarjeta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void persistirUltimo(Cuenta cuenta) {
        ArrayList<Movimiento> movs = cuenta.obtenerMovimientos();
        if (!movs.isEmpty()) {
            movimientoRepo.guardar(movs.get(movs.size() - 1), cuenta.getId());
        }
    }
}
