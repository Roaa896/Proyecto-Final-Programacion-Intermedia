package hospital.modelo;

import hospital.excepciones.FechaInvalidaException;

public class Cita {
    private String fecha;
    private String motivo;
    private Paciente paciente;
    private Medico medico;

    public Cita(String fecha, String motivo, Paciente paciente, Medico medico) {
        this.fecha = fecha;
        this.motivo = motivo;
        this.paciente = paciente;
        this.medico = medico;
    }

    public String getInfo() {
        return "Cita: " + fecha +
                " | Motivo: " + motivo +
                " | Médico: " + medico.getNombre() +
                " | Paciente: " + (paciente != null ? paciente.getNombre() : "sin asignar");
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public String getFecha() {
        return fecha;
    }

    public static void validarFormato(String fecha) throws FechaInvalidaException {
        if (fecha == null || !fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new FechaInvalidaException("La fecha debe tener el formato dd/mm/aaaa.");
        }
    }
}
