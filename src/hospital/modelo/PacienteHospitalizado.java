package hospital.modelo;

// Clase final: no puede heredarse
public final class PacienteHospitalizado extends Paciente {
    private int numeroCama;
    private int diasHospitalizado;

    public PacienteHospitalizado(String nombre, String codigo, int edad, int numeroCama, int diasHospitalizado) {
        super(nombre, codigo, edad);
        if (numeroCama <= 0) {
            throw new IllegalArgumentException("Número de cama inválido: ");
        }
        this.numeroCama = numeroCama;
        this.diasHospitalizado = diasHospitalizado;
    }

    // Sobreescritura (polimorfismo dinamico)
    @Override
    public String obtenerInfo() {
        return "Paciente hospitalizado: " + getNombre() +
               " | Código: " + getCodigo() +
               " | Edad: " + getEdad() +
               " | Cama: " + numeroCama +
               " | Días: " + diasHospitalizado;
    }

    public int getDiasHospitalizado() {
        return diasHospitalizado;
    }
}
