package hospital.modelo;

import java.util.ArrayList;

public abstract class Paciente {
    private String nombre;
    private String codigo;
    private int edad;

    public Paciente(String nombre, String codigo, int edad) {
        if (edad < 0 || edad > 120) {
            throw new IllegalArgumentException("Edad inválida: " + edad);
        }
        this.nombre = nombre;
        this.codigo = codigo;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getEdad() {
        return edad;
    }

    public static Paciente buscarPorCodigo(ArrayList<Paciente> lista, String codigo) {
        for (Paciente p : lista) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setEdad(int edad) {
        if (edad < 0 || edad > 120) {
            throw new IllegalArgumentException("Edad inválida: " + edad);
        }
        this.edad = edad;
    }

    public abstract String obtenerInfo();

}
