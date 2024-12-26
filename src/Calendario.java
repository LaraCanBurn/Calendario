import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Clase para representar un Evento
class Evento {
    String nombre;
    LocalDate fecha;
    String horaComienzo;
    String horaFinalizacion;
    String ubicacion;
    int publico;
    String url;

    // Constructor para inicializar los atributos del evento
    Evento(String nombre, String fecha, String horaComienzo, String horaFinalizacion, String ubicacion, int publico,
            String url) {
        this.nombre = nombre;
        this.fecha = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.horaComienzo = horaComienzo;
        this.horaFinalizacion = horaFinalizacion;
        this.ubicacion = ubicacion;
        this.publico = publico;
        this.url = url;
    }

    // Método para mostrar la información del evento
    public void mostrarEvento() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha: " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Hora de comienzo: " + horaComienzo);
        System.out.println("Hora de finalización: " + horaFinalizacion);
        System.out.println("Ubicación: " + ubicacion);
        System.out.println("Público: "
                + (publico == 1 ? "Estudiantes" : (publico == 2 ? "Profesorado" : "Comunidad Universitaria")));
        System.out.println("URL de registro: " + url);
    }
}

// Clase principal del calendario
public class Calendario {
    static Evento[] eventos = new Evento[100]; // Array de tamaño fijo para los eventos
    static int eventoCount = 0; // Contador de eventos
    static final String RUTA_CSV = "data/Eventos.csv"; // Ruta del archivo .csv

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        // Bucle principal para el menú del gestor de eventos
        do {
            System.out.println("\n=== MENÚ GESTOR DE EVENTOS - ESCUELA STEAM ===");
            System.out.println("1. Crear evento");
            System.out.println("2. Mostrar eventos");
            System.out.println("3. Actualizar evento");
            System.out.println("4. Eliminar evento");
            System.out.println("5. Estadísticas de eventos");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            // Validación para asegurar que se introduce un entero válido
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número válido.");
                System.out.print("Elija una opción: ");
                scanner.next();
            }
            opcion = scanner.nextInt();

            // Procesar las opciones del menú
            switch (opcion) {
                case 1:
                    crearEvento();
                    break;
                case 2:
                    mostrarEventos();
                    break;
                case 3:
                    actualizarEvento();
                    break;
                case 4:
                    eliminarEvento();
                    break;
                case 5:
                    mostrarEstadisticas();
                    break;
                case 0:
                    System.out.println("Has elegido salir del programa. ¡Vuelve pronto!");
                    break;
                default:
                    System.out.println("Tienes que elegir una opción válida.");
                    break;
            }
        } while (opcion != 0);

        // Cerrar el escáner al finalizar
        scanner.close();
    }

    // Método para crear un nuevo evento
    public static void crearEvento() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del evento: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la fecha (dd/MM/yyyy): ");
        String fecha = scanner.nextLine();
        System.out.print("Ingrese la hora de inicio (HH:mm): ");
        String horaComienzo = scanner.nextLine();
        System.out.print("Ingrese la hora de finalización (HH:mm): ");
        String horaFinalizacion = scanner.nextLine();
        System.out.print("Ingrese el lugar: ");
        String ubicacion = scanner.nextLine();
        System.out.print("Ingrese el público objetivo (1: estudiantes, 2: profesorado, 3: comunidad universitaria): ");
        int publico = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese la URL de registro: ");
        String url = scanner.nextLine();

        // Crear un nuevo evento y agregarlo al array
        Evento evento = new Evento(nombre, fecha, horaComienzo, horaFinalizacion, ubicacion, publico, url);
        eventos[eventoCount] = evento;
        eventoCount++;

        // Guardar los eventos en el archivo CSV 
        guardarEventos();
    }

    // Método para mostrar los eventos
    public static void mostrarEventos() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el modo de visualización:");
        System.out.println("1. Día");
        System.out.println("2. Semana");
        System.out.println("3. Mes");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese la fecha de referencia (dd/MM/yyyy): ");
        String fechaReferencia = scanner.nextLine();
        LocalDate fechaRef = LocalDate.parse(fechaReferencia, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Mostrar los eventos según la opción seleccionada
        switch (opcion) {
            case 1:
                mostrarEventosDia(fechaRef);
                break;
            case 2:
                mostrarEventosSemana(fechaRef);
                break;
            case 3:
                mostrarEventosMes(fechaRef);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    // Método para mostrar eventos de un día específico
    public static void mostrarEventosDia(LocalDate fecha) {
        for (int i = 0; i < eventoCount; i++) {
            if (eventos[i].fecha.equals(fecha)) {
                eventos[i].mostrarEvento();
            }
        }
    }

    // Método para mostrar eventos de una semana específica
    public static void mostrarEventosSemana(LocalDate fecha) {
        LocalDate startOfWeek = fecha.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = fecha.with(java.time.DayOfWeek.SUNDAY);
        for (int i = 0; i < eventoCount; i++) {
            if (!eventos[i].fecha.isBefore(startOfWeek) && !eventos[i].fecha.isAfter(endOfWeek)) {
                eventos[i].mostrarEvento();
            }
        }
    }

    // Método para mostrar eventos de un mes específico
    public static void mostrarEventosMes(LocalDate fecha) {
        for (int i = 0; i < eventoCount; i++) {
            if (eventos[i].fecha.getMonth() == fecha.getMonth() && eventos[i].fecha.getYear() == fecha.getYear()) {
                eventos[i].mostrarEvento();
            }
        }
    }

    // Método para actualizar un evento
    public static void actualizarEvento() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del evento a actualizar: ");
        String nombre = scanner.nextLine();

        // Buscar el evento por nombre
        for (int i = 0; i < eventoCount; i++) {
            if (eventos[i].nombre.equals(nombre)) {
                System.out.println("Evento encontrado. Ingrese los nuevos datos.");

                System.out.print("Ingrese la nueva fecha (dd/MM/yyyy): ");
                String fecha = scanner.nextLine();
                System.out.print("Ingrese la nueva hora de inicio (HH:mm): ");
                String horaComienzo = scanner.nextLine();
                System.out.print("Ingrese la nueva hora de finalización (HH:mm): ");
                String horaFinalizacion = scanner.nextLine();
                System.out.print("Ingrese el nuevo lugar: ");
                String ubicacion = scanner.nextLine();
                System.out.print(
                        "Ingrese el nuevo público objetivo (1: estudiantes, 2: profesorado, 3: comunidad universitaria): ");
                int publico = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                System.out.print("Ingrese la nueva URL de registro: ");
                String url = scanner.nextLine();

                // Actualizar el evento con los nuevos datos
                eventos[i].fecha = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                eventos[i].horaComienzo = horaComienzo;
                eventos[i].horaFinalizacion = horaFinalizacion;
                eventos[i].ubicacion = ubicacion;
                eventos[i].publico = publico;
                eventos[i].url = url;

                System.out.println("Evento actualizado correctamente.");
                // Guardar los eventos en el archivo CSV 
                guardarEventos();
                return;
            }
        }

        System.out.println("Evento no encontrado.");
    }

    // Método para eliminar un evento
    public static void eliminarEvento() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del evento a eliminar: ");
        String nombre = scanner.nextLine();

        // Buscar el evento por nombre y eliminarlo del array
        boolean encontrado = false;
        for (int i = 0; i < eventoCount; i++) {
            if (eventos[i].nombre.equals(nombre)) {
                encontrado = true;

                // Mover todos los eventos posteriores una posición hacia atrás
                for (int j = i; j < eventoCount - 1; j++) {
                    eventos[j] = eventos[j + 1];
                }
                eventos[eventoCount - 1] = null; // Eliminar referencia al último evento
                eventoCount--; // Disminuir el contador de eventos

                System.out.println("Evento eliminado correctamente.");
                // Guardar los eventos actualizados en el archivo CSV 
                guardarEventos();
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Evento no encontrado.");
        }

        // Guardar los eventos actualizados en el archivo CSV
        guardarEventos();
    }

    // Método para guardar los eventos en el archivo CSV
    public static void guardarEventos() {
        try {
            // Crear un BufferedWriter para escribir en el archivo CSV
            BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CSV));

            // Escribir la cabecera del archivo CSV
            bw.write("nombre,fecha,horaComienzo,horaFinalizacion,ubicacion,publico,url");
            bw.newLine();

            // Escribir cada evento en el archivo CSV
            for (int i = 0; i < eventoCount; i++) {
                Evento evento = eventos[i];
                String linea = evento.nombre + "," +
                        evento.fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "," +
                        evento.horaComienzo + "," +
                        evento.horaFinalizacion + "," +
                        evento.ubicacion + "," +
                        evento.publico + "," +
                        evento.url;
                bw.write(linea);
                bw.newLine();
            }

            // Cerrar el BufferedWriter
            bw.close();
        } catch (IOException e) {
            // Mostrar mensaje de error en caso de excepción
            System.out.println("Error al guardar eventos: " + e.getMessage());
        }
    }

    // Método para mostrar estadísticas de los eventos
    public static void mostrarEstadisticas() {
        int totalEventos = eventoCount;
        int eventosEstudiantes = 0;
        int eventosProfesorado = 0;
        int eventosComunidadUniversitaria = 0;

        // Calcular estadísticas
        for (int i = 0; i < totalEventos; i++) {
            if (eventos[i].publico == 1) {
                eventosEstudiantes++;
            } else if (eventos[i].publico == 2) {
                eventosProfesorado++;
            } else if (eventos[i].publico == 3) {
                eventosComunidadUniversitaria++;
            }
        }

        // Mostrar estadísticas
        System.out.println("Estadísticas de eventos:");
        System.out.println("Total de eventos: " + totalEventos);
        System.out.println("Eventos para estudiantes: " + eventosEstudiantes);
        System.out.println("Eventos para profesorado: " + eventosProfesorado);
        System.out.println("Eventos para comunidad universitaria: " + eventosComunidadUniversitaria);
    }
}
