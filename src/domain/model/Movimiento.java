package domain.model;

import domain.enums.TipoMovimiento;
import java.time.LocalDateTime;

public class Movimiento {

    private int id;
    private LocalDateTime fechaHora;
    private TipoMovimiento tipo;
    private double valor;
    private double saldoPosterior;
    private String descripcion;

    // Nuevo movimiento — fechaHora = ahora
    public Movimiento(int id, TipoMovimiento tipo, double valor, double saldoPosterior, String descripcion) {
        this.id = id;
        this.fechaHora = LocalDateTime.now();
        this.tipo = tipo;
        this.valor = valor;
        this.saldoPosterior = saldoPosterior;
        this.descripcion = descripcion;
    }

    // Cargado desde BD
    public Movimiento(int id, LocalDateTime fechaHora, TipoMovimiento tipo, double valor, double saldoPosterior, String descripcion) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.valor = valor;
        this.saldoPosterior = saldoPosterior;
        this.descripcion = descripcion;
    }

    public void mostrar() {
        System.out.println(id + " | " + fechaHora + " | " + tipo + " | $" + valor +
                " | Saldo: $" + saldoPosterior + " | " + descripcion);
    }

    public int getId() { return id; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public TipoMovimiento getTipo() { return tipo; }
    public double getValor() { return valor; }
    public double getSaldoPosterior() { return saldoPosterior; }
    public String getDescripcion() { return descripcion; }
}
