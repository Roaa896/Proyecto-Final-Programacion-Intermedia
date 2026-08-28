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

        int dia = Integer.parseInt(fecha.substring(0, 2));
        int mes = Integer.parseInt(fecha.substring(3, 5));

        if (mes < 1 || mes > 12) {
            throw new FechaInvalidaException("El mes debe estar entre 01 y 12.");
        }
        if (dia < 1 || dia > 31) {
            throw new FechaInvalidaException("El día debe estar entre 01 y 31.");
        }
    }
}
