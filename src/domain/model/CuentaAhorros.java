package domain.model;

public class CuentaAhorros extends Cuenta {

    public CuentaAhorros(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    @Override
    public void retirar(double monto) {
        try {
            if (monto <= 0) return;

            if (saldo >= monto) {
                saldo -= monto;
                registrarMovimiento(domain.enums.TipoMovimiento.RETIRO, monto);
            } else {
                System.out.println("Saldo insuficiente");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean puedeTransferir(double monto) {
        return false;
    }

    @Override
    protected boolean tieneSaldoSuficiente(double monto) {
        return false;
    }

    @Override
    protected boolean puedeOperar(double monto) {
        return true; //depende del saldo
    }

    @Override
    protected double saldoDisponible() {
        return saldo;
    }
}