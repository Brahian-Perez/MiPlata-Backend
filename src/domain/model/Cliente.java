package domain.model;

import domain.interfaces.IAutenticable;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Cliente implements IAutenticable {

    private int id;
    private String identificacion;
    private String nombreCompleto;
    private String celular;
    private String usuario;
    private String contrasena;
    private int intentosFallidos;
    private boolean bloqueado;
    private ArrayList<Cuenta> cuentas;

    public Cliente() {}

    public Cliente(int id, String identificacion, String nombreCompleto,
                   String celular, String usuario, String contrasena) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.cuentas = new ArrayList<>();
        this.intentosFallidos = 0;
        this.bloqueado = false;
    }

    @Override
    public boolean autenticar(String usuario, String contrasena) {
        if (intentosFallidos >= 3) {
            System.out.println("Cuenta bloqueada por seguridad.");
            return false;
        }
        if (bloqueado) {
            System.out.println("Cuenta bloqueada.");
            return false;
        }
        if (usuario == null || contrasena == null)
            return false;
        if (usuario.isEmpty() || contrasena.isEmpty())
            return false;
        if (this.usuario.equals(usuario) && this.contrasena.equals(contrasena)) {
            resetearIntentos();
            return true;
        }
        System.out.println("Usuario o contraseña incorrectos.");
        incrementarIntentos();
        return false;
    }

    @Override
    public void cerrarSesion() {
        System.out.println("Sesión cerrada.");
    }

    @Override
    public void cambiarContrasena(String actual, String nueva) {
        if (this.contrasena.equals(actual)) {
            this.contrasena = nueva;
            System.out.println("Contraseña actualizada.");
        } else {
            System.out.println("Contraseña incorrecta.");
        }
    }

    public void incrementarIntentos() {
        intentosFallidos++;
        System.out.println("Intento fallido: " + intentosFallidos);
        if (intentosFallidos >= 3) {
            bloqueado = true;
            System.out.println("Cuenta bloqueada por seguridad.");
        }
    }

    public void resetearIntentos() {
        intentosFallidos = 0;
    }

    public void editarPerfil(String nuevoNombre, String nuevoCelular) {
        this.nombreCompleto = nuevoNombre;
        this.celular = nuevoCelular;
        System.out.println("Perfil actualizado.");
    }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
        System.out.println("Cuenta creada: " + cuenta.getNumeroCuenta());
    }

    public Cuenta buscarCuenta(String numero) {
        for (Cuenta c : cuentas) {
            if (c.getNumeroCuenta().equals(numero)) return c;
        }
        return null;
    }

    public void listarCuentas() {
        if (cuentas.isEmpty()) {
            System.out.println("No tiene cuentas.");
            return;
        }
        for (Cuenta c : cuentas) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (c instanceof TarjetaCredito tc)
                System.out.println("Cuenta: " + c.getNumeroCuenta()
                        + " | Fecha de apertura: " + c.getFechaApertura().format(formatter)
                        + " | Tipo: " + c.getClass().getSimpleName()
                        + "  | Cupo  disponible: $" + tc.getCupoDisponible()
                        + " | Estado: " + c.getEstado());
            else
                System.out.println("Cuenta: " + c.getNumeroCuenta()

                        + " | Fecha de apertura: " + c.getFechaApertura().format(formatter)
                        + " | Tipo: " + c.getClass().getSimpleName()
                        + " | Saldo disponible: $" + c.consultarSaldo()
                        + " | Estado: " + c.getEstado());
            }


    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }
    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }
    public ArrayList<Cuenta> getCuentas() { return cuentas; }
    public void setCuentas(ArrayList<Cuenta> cuentas) { this.cuentas = cuentas; }
}
