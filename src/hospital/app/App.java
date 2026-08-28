package hospital.app;

import java.util.Scanner;
import java.util.ArrayList;

import hospital.modelo.Cita;
import hospital.modelo.Enfermero;
import hospital.modelo.Hospital;
import hospital.modelo.Medico;
import hospital.modelo.Paciente;
import hospital.modelo.PacienteAmbulatorio;
import hospital.modelo.PacienteHospitalizado;
import hospital.modelo.Sala;
import hospital.modelo.Validaciones;

import hospital.excepciones.CamaNoDisponibleException;
import hospital.excepciones.CitaInvalidaException;
import hospital.excepciones.NombreInvalidoException;
import hospital.excepciones.PacienteNoEncontradoException;
import hospital.excepciones.RespuestaInvalidaException;
import hospital.excepciones.TipoPacienteInvalidoException;
import hospital.excepciones.ValorNegativoException;
import hospital.excepciones.FechaInvalidaException;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hospital hospital = new Hospital("Hospital Regional San Juan");

        Medico m1 = new Medico("Dra. Laura Gomez", "M001", "Cardiologia");
        Medico m2 = new Medico("Dr. Pedro Ruiz", "M002", "Pediatria");
        Enfermero e1 = new Enfermero("Ana Torres", "E001", "Cuidados intensivos", "Mañana");

        hospital.agregarPersonal(m1);
        hospital.agregarPersonal(m2);
        hospital.agregarPersonal(e1);

        ArrayList<Medico> medicos = new ArrayList<>();
        medicos.add(m1);
        medicos.add(m2);

        // Pacientes ya creados pero aún sin asignar a una sala
        ArrayList<Paciente> pacientesTemporales = new ArrayList<>();

        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN HOSPITALARIA ===");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Asignar paciente a sala");
            System.out.println("3. Agendar cita con médico");
            System.out.println("4. Ver pacientes de una sala");
            System.out.println("5. Ver agenda de un médico");
            System.out.println("6. Dar de alta a paciente");
            System.out.println("7. Buscar paciente por código");
            System.out.println("8. Reporte general del hospital");
            System.out.println("9. Crear nueva sala");
            System.out.println("10. Registrar nuevo médico");
            System.out.println("11. Registrar nuevo enfermero");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = -1;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Error: debes ingresar un número.");
                continue;
            }

            switch (opcion) {

                case 1: {
                    try {
                        System.out.print("Tipo (1=Ambulatorio, 2=Hospitalizado): ");
                        int tipo = Integer.parseInt(sc.nextLine());

                        if (tipo != 1 && tipo != 2) {
                            throw new TipoPacienteInvalidoException("Selecciona la opción 1 o 2.");
                        }

                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        Validaciones.validarNombre(nombre);

                        System.out.print("Código: ");
                        String codigo = sc.nextLine();

                        System.out.print("Edad: ");
                        int edad = Integer.parseInt(sc.nextLine());

                        if (edad < 0) {
                            throw new ValorNegativoException("La edad no puede ser negativa.");
                        }

                        if (tipo == 1) {
                            System.out.print("Próxima cita: ");
                            String proximaCita = sc.nextLine();
                            Cita.validarFormato(proximaCita);
                            Paciente p = new PacienteAmbulatorio(nombre, codigo, edad, proximaCita);
                            pacientesTemporales.add(p);
                            System.out.println("Paciente ambulatorio registrado.");
                        } else {
                            System.out.print("Número de cama: ");
                            int numeroCama = Integer.parseInt(sc.nextLine());

                            if (numeroCama < 0) {
                                throw new ValorNegativoException("El número de cama no puede ser negativo.");
                            }

                            System.out.print("Días hospitalizado: ");
                            int diasHospitalizado = Integer.parseInt(sc.nextLine());

                            if (diasHospitalizado < 0) {
                                throw new ValorNegativoException("Los días hospitalizado no pueden ser negativos.");
                            }

                            Paciente p = new PacienteHospitalizado(nombre, codigo, edad, numeroCama, diasHospitalizado);
                            pacientesTemporales.add(p);
                            System.out.println("Paciente hospitalizado registrado.");
                        }
                    } catch (TipoPacienteInvalidoException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } catch (NombreInvalidoException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } catch (ValorNegativoException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: debes ingresar un número válido.");
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } catch (FechaInvalidaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                }

                case 2: {
                    System.out.println("\nSalas disponibles:");
                    for (Sala sala : hospital.getSalas()) {
                        System.out.println("- " + sala.getNombre() +
                                " | Ocupación: " + sala.getPacientes().size() + "/" + sala.getCapacidad());
                    }
                    System.out.print("Nombre de sala: ");
                    String nombreSala = sc.nextLine();
                    Sala sala = hospital.buscarSala(nombreSala);

                    if (sala == null) {
                        System.out.println("Error: sala no encontrada.");
                        break;
                    }

                    System.out.print("Código del paciente a asignar: ");
                    String codigo = sc.nextLine();
                    Paciente paciente = Paciente.buscarPorCodigo(pacientesTemporales, codigo);

                    if (paciente == null) {
                        System.out.println("Error: paciente no encontrado en la lista temporal.");
                        break;
                    }

                    try {
                        sala.agregarPaciente(paciente);
                    } catch (CamaNoDisponibleException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                }

                case 3: {
                    System.out.println("\nMédicos disponibles:");
                    for (Medico m : medicos) {
                        System.out.println("- " + m.getId() + " | " + m.getNombre());
                    }
                    System.out.print("ID del médico: ");
                    String idMedico = sc.nextLine();
                    Medico medico = Medico.buscarPorId(medicos, idMedico);

                    if (medico == null) {
                        System.out.println("Error: médico no encontrado.");
                        break;
                    }

                    System.out.print("¿Agregar motivo? (s/n): ");
                    String conMotivo = sc.nextLine();

                    try {
                        if (!conMotivo.equalsIgnoreCase("s") && !conMotivo.equalsIgnoreCase("n")) {
                            throw new RespuestaInvalidaException("Debes responder 's' o 'n'.");
                        }

                        System.out.print("Fecha: ");
                        String fecha = sc.nextLine();
                        Cita.validarFormato(fecha);

                        if (conMotivo.equalsIgnoreCase("s")) {
                            System.out.print("Motivo: ");
                            String motivo = sc.nextLine();
                            medico.agendarCita(fecha, motivo);
                        } else {
                            medico.agendarCita(fecha);
                        }
                    } catch (RespuestaInvalidaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } catch (CitaInvalidaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } catch (FechaInvalidaException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                }

                case 4: {
                    System.out.print("Nombre de sala: ");
                    String nombreSala = sc.nextLine();
                    Sala sala = hospital.buscarSala(nombreSala);

                    if (sala == null) {
                        System.out.println("Error: sala no encontrada.");
                        break;
                    }
                    sala.listarPacientes();
                    break;
                }

                case 5: {
                    System.out.print("ID del médico: ");
                    String idMedico = sc.nextLine();
                    Medico medico = Medico.buscarPorId(medicos, idMedico);
                    if (medico == null) {
                        System.out.println("Error: médico no encontrado.");
                        break;
                    }
                    for (Cita cita : medico.getCitas()) {
                        System.out.println(cita.getInfo());
                    }
                    break;
                }

                case 6: {
                    System.out.print("Nombre de sala: ");
                    String nombreSala = sc.nextLine();
                    Sala sala = hospital.buscarSala(nombreSala);

                    if (sala == null) {
                        System.out.println("Error: sala no encontrada.");
                        break;
                    }

                    System.out.print("Código del paciente: ");
                    String codigo = sc.nextLine();

                    try {
                        sala.eliminarPaciente(codigo);
                    } catch (PacienteNoEncontradoException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                }

                case 7: {
                    System.out.print("Código: ");
                    String codigo = sc.nextLine();

                    try {
                        Paciente p = hospital.buscarPaciente(codigo);
                        System.out.println(p.obtenerInfo());
                    } catch (PacienteNoEncontradoException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                }

                case 8:
                    hospital.generarReporteGeneral();
                    break;

                case 9: {
                    try {
                        System.out.print("Nombre de la nueva sala: ");
                        String nombreSalaNueva = sc.nextLine();
                        Validaciones.validarNombre(nombreSalaNueva);

                        System.out.print("Capacidad: ");
                        int capacidad = Integer.parseInt(sc.nextLine());

                        if (capacidad < 0) {
                            throw new ValorNegativoException("La capacidad no puede ser negativa.");
                        }

                        // Composición: la Sala se crea aquí y pasa a pertenecer al Hospital
                        Sala nuevaSala = new Sala(nombreSalaNueva, capacidad);
                        hospital.agregarSala(nuevaSala);
                        System.out.println("Sala '" + nombreSalaNueva + "' creada y agregada al hospital.");
                    } catch (NombreInvalidoException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } catch (ValorNegativoException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: debes ingresar un número válido.");
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                }

                case 10: {
                    try {
                        System.out.print("Nombre del médico: ");
                        String nombreMedico = sc.nextLine();
                        Validaciones.validarNombre(nombreMedico);

                        System.out.print("ID: ");
                        String idMedico = sc.nextLine();

                        System.out.print("Especialidad: ");
                        String especialidadMedico = sc.nextLine();
                        Validaciones.validarNombre(especialidadMedico);

                        Medico nuevoMedico = new Medico(nombreMedico, idMedico, especialidadMedico);
                        medicos.add(nuevoMedico);
                        hospital.agregarPersonal(nuevoMedico);
                        System.out.println("Médico registrado correctamente.");
                    } catch (NombreInvalidoException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                }

                case 11: {
                    try {
                        System.out.print("Nombre del enfermero: ");
                        String nombreEnfermero = sc.nextLine();
                        Validaciones.validarNombre(nombreEnfermero);
                        System.out.print("ID: ");
                        String idEnfermero = sc.nextLine();

                        System.out.print("Especialidad: ");
                        String especialidadEnfermero = sc.nextLine();
                        Validaciones.validarNombre(especialidadEnfermero);

                        System.out.print("Turno: ");
                        String turno = sc.nextLine();

                        Enfermero nuevoEnfermero = new Enfermero(nombreEnfermero, idEnfermero, especialidadEnfermero,
                                turno);
                        hospital.agregarPersonal(nuevoEnfermero);
                        System.out.println("Enfermero registrado correctamente.");
                    } catch (NombreInvalidoException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                }

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}
