package domain.model;

import domain.enums.EstadoCuenta;
import domain.enums.TipoMovimiento;
import domain.interfaces.ITransaccion;
import domain.interfaces.ITransferible;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Cuenta implements ITransaccion, ITransferible {

    protected String numeroCuenta;
    protected double saldo;
    protected LocalDateTime fechaApertura;
    protected EstadoCuenta estado;
    protected ArrayList<Movimiento> movimientos;

    public Cuenta(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
        this.fechaApertura = LocalDateTime.now();
        this.estado = EstadoCuenta.ACTIVA;
        this.movimientos = new ArrayList<>();
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public EstadoCuenta getEstado() { return estado; }
    public LocalDateTime getFechaApertura() { return fechaApertura; }

    @Override
    public void consignar(double monto) {
        if (monto <= 0) {
            System.out.println("Monto inválido");
            return;
        }
        saldo += monto;
        registrarMovimiento(TipoMovimiento.CONSIGNACION, monto, "Consignación");
    }

    @Override
    public double consultarSaldo() {
        return saldo;
    }

    @Override
    public ArrayList<Movimiento> obtenerMovimientos() {
        return movimientos;
    }

    @Override
    public void transferir(Cuenta destino, double monto) {
        if (destino == null) {
            System.out.println("Cuenta destino inválida");
            return;
        }
        if (monto <= 0) {
            System.out.println("Monto inválido");
            return;
        }
        if (!validarDestino(destino)) {
            System.out.println("Esta cuenta no puede transferir a la cuenta destino");
            return;
        }
        if (saldo < monto) {
            System.out.println("Saldo insuficiente");
            return;
        }
        saldo -= monto;
        destino.saldo += monto;
        registrarMovimiento(TipoMovimiento.TRANSFERENCIA_OUT, monto, "Transferencia a " + destino.getNumeroCuenta());
        destino.registrarMovimiento(TipoMovimiento.TRANSFERENCIA_IN, monto, "Transferencia de " + this.numeroCuenta);
        System.out.println("Transferencia realizada correctamente");
    }

    @Override
    public abstract boolean validarDestino(Cuenta c);

    @Override
    public abstract void retirar(double monto);

    protected void registrarMovimiento(TipoMovimiento tipo, double monto, String descripcion) {
        movimientos.add(new Movimiento(movimientos.size() + 1, tipo, monto, saldo, descripcion));
    }

    public void mostrarMovimientos() {
        if (movimientos.isEmpty()) {
            System.out.println("No hay movimientos.");
            return;
        }
        for (Movimiento m : movimientos) {
            m.mostrar();
        }
    }
}
