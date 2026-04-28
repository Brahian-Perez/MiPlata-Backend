package domain.model;

import domain.enums.TipoMovimiento;

import java.time.LocalDateTime;


public class Movimiento {


    private int id;
    private LocalDateTime fecha;
    private TipoMovimiento tipo;
    private double valor;
    private double saldoPosterior;

    public Movimiento(int id, TipoMovimiento tipo,

                      double valor, double saldoPosterior) {

        this.id = id;
        this.fecha = LocalDateTime.now();
        this.tipo = tipo;
        this.valor = valor;
        this.saldoPosterior = saldoPosterior;
    }


    public void mostrar() {
        System.out.println(id + " | " +
                        fecha + " | " +
                        tipo + " | $" +
                        valor + " | Saldo: $" +
                        saldoPosterior
        );
    }
}