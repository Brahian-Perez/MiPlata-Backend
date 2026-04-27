package domain.model;

import domain.enums.TipoMovimiento;
import domain.interfaces.ITransaccion;

import java.util.ArrayList;

public abstract class Cuenta implements ITransaccion {

    protected String numeroCuenta;
    protected double saldo;
    protected ArrayList<Movimiento> movimientos;

    public Cuenta(String numeroCuenta, double saldoInicial) {
        try {
            this.numeroCuenta = numeroCuenta;
            this.saldo = saldoInicial;
            this.movimientos = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNumeroCuenta() {
        try {
            return numeroCuenta;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void consignar(double monto) {
        try {
            if (monto <= 0) {
                System.out.println("Monto inválido");
                return;
            }

            saldo += monto;
            registrarMovimiento(TipoMovimiento.CONSIGNACION, monto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public double consultarSaldo() {
        try {
            return saldo;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    protected void registrarMovimiento(TipoMovimiento tipo, double monto) {
        try {
            movimientos.add(
                    new Movimiento(movimientos.size() + 1, tipo, monto, saldo)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarMovimientos() {
        try {
            if (movimientos.isEmpty()) {
                System.out.println("No hay movimientos.");
                return;
            }

            for (Movimiento m : movimientos) {
                m.mostrar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void retirar(double monto);

    @Override
    public void transferir(Cuenta destino, double monto) {
        try {
            if (destino == null) {
                System.out.println("Cuenta destino inválida");
                return;
            }

            if (monto <= 0) {
                System.out.println("Monto inválido");
                return;
            }

            if (!puedeTransferir(monto)) {
                System.out.println("Esta cuenta no puede realizar transferencias");
                return;
            }

            if (!tieneSaldoSuficiente(monto)) {
                System.out.println("Saldo insuficiente");
                return;
            }

            this.saldo -= monto;
            destino.saldo += monto;

            registrarMovimiento(TipoMovimiento.TRANSFERENCIA_OUT, monto);
            destino.registrarMovimiento(TipoMovimiento.TRANSFERENCIA_IN, monto);

            System.out.println("Transferencia realizada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract boolean puedeTransferir(double monto);

    protected abstract boolean tieneSaldoSuficiente(double monto);

    protected abstract boolean puedeOperar(double monto);

    protected abstract double saldoDisponible();
}