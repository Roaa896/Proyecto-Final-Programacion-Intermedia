package hospital.modelo;

import java.util.ArrayList;

import hospital.excepciones.CamaNoDisponibleException;
import hospital.excepciones.PacienteNoEncontradoException;

public class Sala {
    private String nombre;
    private int capacidad;
    private ArrayList<Paciente> pacientes;

    public Sala(String nombre, int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("Capacidad debe ser mayor a 0.");
        }
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.pacientes = new ArrayList<>();
    }

    public void agregarPaciente(Paciente paciente) throws CamaNoDisponibleException {
        if (pacientes.size() >= capacidad) {
            throw new CamaNoDisponibleException("Sala " + nombre + " sin camas disponibles.");
        }
        pacientes.add(paciente);
        System.out.println("Paciente " + paciente.getNombre() + " asignado a sala " + nombre);
    }

    public void eliminarPaciente(String codigo) throws PacienteNoEncontradoException {
        for (Paciente paciente : pacientes) {
            if (paciente.getCodigo().equals(codigo)) {
                pacientes.remove(paciente);
                System.out.println("Paciente dado de alta.");
                return;
            }
        }
        throw new PacienteNoEncontradoException(codigo);
    }

    public Paciente buscarPaciente(String codigo) throws PacienteNoEncontradoException {
        for (Paciente paciente : pacientes) {
            if (paciente.getCodigo().equals(codigo)) {
                return paciente;
            }
        }
        throw new PacienteNoEncontradoException(codigo);
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes en esta sala.");
            return;
        }
        for (Paciente paciente : pacientes) {
            System.out.println(paciente.obtenerInfo()); // polimorfismo dinámico
        }
    }
}
