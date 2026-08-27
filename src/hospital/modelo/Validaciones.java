package hospital.modelo;

import hospital.excepciones.NombreInvalidoException;

public class Validaciones {

    private Validaciones() {
        // Clase de utilidades: no se instancia
    }

    public static void validarNombre(String nombre) throws NombreInvalidoException {
        if (nombre == null || nombre.trim().isEmpty() || !nombre.matches("[a-zA-ZÁÉÍÓÚÑáéíóúñ ]+")) {
            throw new NombreInvalidoException("El nombre solo puede contener letras y espacios, sin números.");
        }
    }
}