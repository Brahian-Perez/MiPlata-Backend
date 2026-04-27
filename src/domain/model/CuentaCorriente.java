package domain.model;

public class CuentaCorriente extends Cuenta {

    public CuentaCorriente(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    @Override
    public void retirar(double monto) {
        try {
            double limite = saldo * 1.2;

            if (monto <= limite) {
                saldo -= monto;
                registrarMovimiento(domain.enums.TipoMovimiento.RETIRO, monto);
            } else {
                System.out.println("Excede el límite de sobregiro (20%)");
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
        return true;
    }

    @Override
    protected double saldoDisponible() {
        return saldo * 1.2;
    }
}