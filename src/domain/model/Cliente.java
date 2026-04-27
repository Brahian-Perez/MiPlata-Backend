package domain.model;

import domain.interfaces.IAutenticable;

import java.util.ArrayList;

public class Cliente implements IAutenticable {

    private int id;
    private String identificacion;
    private String nombre;
    private String celular;
    private String usuario;
    private String contrasena;

    private int intentosFallidos;
    private boolean bloqueado;

    private ArrayList<Cuenta> cuentas;

    public Cliente(int id, String identificacion, String nombre,
                   String celular, String usuario, String contrasena) {

        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.celular = celular;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.cuentas = new ArrayList<>();
        this.intentosFallidos = 0;
        this.bloqueado = false;
    }


    @Override
    public boolean autenticar(String usuario, String contrasena) {
        try {
            if (bloqueado) {
                System.out.println("Cuenta bloqueada.");
                return false;
            }

            if (this.usuario.equals(usuario) && this.contrasena.equals(contrasena)) {
                intentosFallidos = 0;
                return true;
            } else {
                intentosFallidos++;
                System.out.println("Intento fallido: " + intentosFallidos);

                if (intentosFallidos >= 3) {
                    bloqueado = true;
                    System.out.println("Cuenta bloqueada por seguridad.");
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void cambiarContrasena(String actual, String nueva) {
        try {
            if (this.contrasena.equals(actual)) {
                this.contrasena = nueva;
                System.out.println("Contraseña actualizada.");
            } else {
                System.out.println("Contraseña incorrecta.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarCuenta(Cuenta cuenta) {
        try {
            cuentas.add(cuenta);
            System.out.println(cuenta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cuenta buscarCuenta(String numero) {
        try {
            for (Cuenta c : cuentas) {
                if (c.getNumeroCuenta().equals(numero)) {
                    return c;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void listarCuentas() {
        try {
            if (cuentas.isEmpty()) {
                System.out.println("No tiene cuentas.");
                return;
            }

            for (Cuenta c : cuentas) {
                System.out.println("Cuenta: " + c.getNumeroCuenta()
                        + " | Saldo: $" + c.consultarSaldo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setNombre(String nuevoNombre) {
    }

    public void setCelular(String nuevoCelular) {
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getCelular() {
        return this.celular;
    }

    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }
}