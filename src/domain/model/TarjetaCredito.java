package domain.model;

import domain.enums.TipoMovimiento;

public class TarjetaCredito extends Cuenta {

    private double cupo;
    private double deuda;
    private int numeroCuotas;

    public TarjetaCredito(String numeroCuenta, double cupo) {
        super(numeroCuenta, cupo);
        this.cupo = cupo;
        this.deuda = 0;
        this.numeroCuotas = 0;
    }

    public void comprar(double monto, int cuotas) {
        if (monto <= 0) {
            System.out.println("Monto inválido");
            return;
        }
        if (monto > (cupo - deuda)) {
            System.out.println("Cupo insuficiente");
            return;
        }
        this.numeroCuotas = cuotas;
        deuda += monto;
        registrarMovimiento(TipoMovimiento.COMPRA_TC, monto, "Compra a " + cuotas + " cuotas");
        System.out.println("Compra realizada. Deuda: $" + deuda + " | Cuota mensual: $" + calcularCuotaMensual());
    }

    public void pagar(double monto) {
        if (monto <= 0) {
            System.out.println("Monto inválido");
            return;
        }
        if (monto > deuda) monto = deuda;
        deuda -= monto;
        registrarMovimiento(TipoMovimiento.PAGO_TC, monto, "Pago tarjeta");
        System.out.println("Pago realizado. Deuda actual: $" + deuda);
    }

    public double calcularTasa(int cuotas) {
        if (cuotas <= 2) return 0;
        if (cuotas <= 6) return 0.019;
        return 0.023;
    }

    public double calcularCuotaMensual() {
        if (numeroCuotas == 0 || deuda == 0) return 0;
        double tasa = calcularTasa(numeroCuotas);
        if (tasa == 0) return deuda / numeroCuotas;
        return (deuda * tasa) / (1 - Math.pow(1 + tasa, -numeroCuotas));
    }

    @Override
    public void retirar(double monto) {
        System.out.println("No se permite retiro en tarjeta de crédito");
    }

    @Override
    public boolean validarDestino(Cuenta c) {
        return false;
    }

    public double getDeuda() { return deuda; }
    public double getCupoDisponible() { return cupo - deuda; }
    public double getCupo() { return cupo; }
    public int getNumeroCuotas() { return numeroCuotas; }
}
