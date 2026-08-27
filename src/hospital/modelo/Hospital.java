package hospital.modelo;

import java.util.ArrayList;

import hospital.excepciones.PacienteNoEncontradoException;

public class Hospital {

    private String nombre;
    private ArrayList<Sala> salas;
    private ArrayList<Personal> personal;

    //Constructor
    public Hospital(String nombre) {
        this.nombre = nombre;
        this.salas = new ArrayList<>();
        this.personal = new ArrayList<>();

        // Composición: las salas se crean internamente y viven mientras vive el hospital
        salas.add(new Sala("Urgencias", 10));
        salas.add(new Sala("Pediatria", 8));
        salas.add(new Sala("Cirugia", 6));
    }

    public void agregarSala(Sala sala) {
        salas.add(sala);
    }

    // Metodos
    // Composición: el personal se crea internamente y vive mientras vive el hospital
    public void agregarPersonal(Personal personal) {
        this.personal.add(personal);
        System.out.println(personal.getNombre() + " registrado en el hospital.");
    }

    public Sala buscarSala(String nombreSala) {
        for (Sala sala : salas) {
            if (sala.getNombre().equals(nombreSala)) {
                return sala;
            }
        }
        return null;
    }

    public Paciente buscarPaciente(String codigo) throws PacienteNoEncontradoException {
        for (Sala sala : salas) {
            try {
                return sala.buscarPaciente(codigo);
            } catch (PacienteNoEncontradoException e) {
                // no está en esta sala, seguir buscando
            }
        }
        throw new PacienteNoEncontradoException(codigo);
    }

    public void generarReporteGeneral() {
        System.out.println("=== REPORTE GENERAL: " + nombre + " ===");
        for (Sala sala : salas) {
            System.out.println("Sala: " + sala.getNombre() +
                    " | Ocupación: " + sala.getPacientes().size() +
                    "/" + sala.getCapacidad());
        }
        System.out.println("Personal registrado: " + personal.size());
        for (Personal p : personal) {
            System.out.println(p.generarReporte()); // polimorfismo dinámico
        }
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public ArrayList<Personal> getPersonal() {
        return personal;
    }
}
