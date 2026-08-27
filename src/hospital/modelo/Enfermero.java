package hospital.modelo;

public class Enfermero extends Personal {
    private String turno;

    public Enfermero(String nombre, String id, String especialidad, String turno) {
        super(nombre, id, especialidad);
        this.turno = turno;
    }

    // Sobreescritura (polimorfismo dinámico)
    @Override
    public String generarReporte() {
        return "=== ENFERMERO ===" +
                "\nNombre: " + getNombre() +
                "\nID: " + getId() +
                "\nEspecialidad: " + getEspecialidad() +
                "\nTurno: " + turno;
    }
}
