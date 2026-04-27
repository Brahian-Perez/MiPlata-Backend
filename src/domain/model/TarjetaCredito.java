package domain.model;

import domain.enums.TipoMovimiento;

public class TarjetaCredito extends Cuenta {

    private double cupoTotal;
    private double deuda;

    public TarjetaCredito(String numeroCuenta, double cupo) {
        super(numeroCuenta, cupo);

        this.cupoTotal = cupo;
        this.deuda = 0;
    }


    // COMPRA A CREDITO
    public void comprar(double monto, int cuotas) {

        if (monto <= 0) {
            System.out.println("Monto inválido");
            return;
        }

        double disponible = cupoTotal - deuda;

        if (monto > disponible) {
            System.out.println("Cupo insuficiente");
            return;
        }

        deuda += monto;

        double tasa = obtenerTasa(cuotas);
        double cuotaMensual = calcularCuota(monto, tasa, cuotas);

        registrarMovimiento(TipoMovimiento.COMPRA_TC, monto);

        System.out.println("Compra realizada correctamente");
        System.out.println("Deuda total: " + deuda);
        System.out.println("Cuota mensual: " + cuotaMensual);
    }

    // PAGO DE TARJETA
    public void pagar(double monto) {

        if (monto <= 0) {
            System.out.println("Monto inválido");
            return;
        }

        if (monto > deuda) {
            monto = deuda;
        }

        deuda -= monto;

        registrarMovimiento(TipoMovimiento.PAGO_TC, monto);

        System.out.println("Pago realizado. Deuda actual: " + deuda);
    }

    // CALCULO DE CUOTA
    private double calcularCuota(double capital, double tasa, int n) {

        if (tasa == 0) {
            return capital / n;
        }

        return (capital * tasa) /
                (1 - Math.pow(1 + tasa, -n));
    }

    // TASA SEGUN CUOTAS (REQUISITO)
    private double obtenerTasa(int cuotas) {

        if (cuotas <= 2) {
            return 0;
        } else if (cuotas <= 6) {
            return 0.019;
        } else {
            return 0.023;
        }
    }

    // GETTERS
    public double getDeuda() {
        return deuda;
    }

    public double getCupoDisponible() {
        return cupoTotal - deuda;
    }


    // CUENTA
    @Override
    public void retirar(double monto) {
        System.out.println("No se permite retiro en tarjeta de crédito");
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
        return false;
    }

    @Override
    protected double saldoDisponible() {
        return 0;
    }
}