package domain.model;

import domain.enums.TipoMovimiento;

public class CuentaCorriente extends Cuenta {

    private double porcentajeSobregiro;
    private double limiteSobregiro;

    public CuentaCorriente(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
        this.porcentajeSobregiro = 0.20;
        this.limiteSobregiro = calcularLimiteSobregiro();
    }

    @Override
    public void retirar(double monto) {
        double limiteTotal = saldo + calcularLimiteSobregiro();
        if (monto <= limiteTotal) {
            saldo -= monto;
            registrarMovimiento(TipoMovimiento.RETIRO, monto, "Retiro");
        } else {
            System.out.println("Excede el límite de sobregiro (20%)");
        }
    }

    public double calcularLimiteSobregiro() {
        return saldo * porcentajeSobregiro;
    }

    @Override
    public boolean validarDestino(Cuenta c) {
        return c != null && !(c instanceof TarjetaCredito);
    }

    public double getPorcentajeSobregiro() { return porcentajeSobregiro; }
    public double getLimiteSobregiro() { return calcularLimiteSobregiro(); }
}
