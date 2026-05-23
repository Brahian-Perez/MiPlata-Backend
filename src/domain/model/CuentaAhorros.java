package domain.model;

import domain.enums.TipoMovimiento;

public class CuentaAhorros extends Cuenta {

    private double tasaInteres;

    public CuentaAhorros(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
        this.tasaInteres = 0.005;
    }

    @Override
    public void retirar(double monto) {
        if (monto <= 0) return;
        if (saldo >= monto) {
            saldo -= monto;
            registrarMovimiento(TipoMovimiento.RETIRO, monto, "Retiro");
        } else {
            System.out.println("Saldo insuficiente");
        }
    }

    public void aplicarIntereses() {
        double intereses = calcularIntereses();
        saldo += intereses;
        registrarMovimiento(TipoMovimiento.CONSIGNACION, intereses, "Intereses aplicados");
        System.out.println("Intereses aplicados: $" + intereses);
    }

    public double calcularIntereses() {
        return saldo * tasaInteres;
    }

    @Override
    public boolean validarDestino(Cuenta c) {
        return c != null && !(c instanceof TarjetaCredito);
    }

    public double getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(double tasaInteres) { this.tasaInteres = tasaInteres; }
}
