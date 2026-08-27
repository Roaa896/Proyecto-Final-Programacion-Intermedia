package hospital.modelo;

import java.util.ArrayList;
import hospital.excepciones.CitaInvalidaException;

public class Medico extends Personal {
    private ArrayList<Cita> citas;

    public Medico(String nombre, String id, String especialidad) {
        super(nombre, id, especialidad);
        this.citas = new ArrayList<>();
    }

    // Sobregarga (polimorfismo estatico) - version 1
    public void agendarCita(String fecha) throws CitaInvalidaException {
        if (fecha == null || fecha.isEmpty()) {
            throw new CitaInvalidaException("La fecha no puede estar vacía.");
        }
        // Logica para agendar la cita
        Cita cita = new Cita(fecha, "Consulta general", null, this);
        citas.add(cita);
        System.out.println("Cita agendada para el " + fecha);
    }

    // Sobregarga (polimorfismo estatico) - version 2
    public void agendarCita(String fecha, String motivo) throws CitaInvalidaException {
        if (fecha == null || fecha.isEmpty()) {
            throw new CitaInvalidaException("La fecha no puede estar vacía.");
        }
        if (motivo == null || motivo.isEmpty()) {
            throw new CitaInvalidaException("El motivo no puede estar vacío.");
        }
        // Logica para agendar la cita
        Cita cita = new Cita(fecha, motivo, null, this);
        citas.add(cita);
        System.out.println("Cita agendada para el " + fecha + " con motivo: " + motivo);
    }

    @Override
    public String generarReporte() {
        return "=== MÉDICO ===" +
                "\nNombre: " + getNombre() +
                "\nID: " + getId() +
                "\nEspecialidad: " + getEspecialidad() +
                "\nCitas agendadas: " + citas.size();
    }

    public ArrayList<Cita> getCitas() {
        return citas;
    }

    public static Medico buscarPorId(ArrayList<Medico> lista, String id) {
        for (Medico m : lista) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }
}
