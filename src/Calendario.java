//Este archivo contendrá el punto de entrada del programa, gestionará el flujo principal, como la navegación entre las opciones del menú.

//importar las librerías necesarias.
import java.util.Scanner;

//Introducir datos en el Gestor de Eventos
public class Calendario {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
// Crear el menú para utilizar el Calenario 
    int opcion; 
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
            scanner.next(); // Limpiar entrada no válida
            }
        opcion = scanner.nextInt();

        // Procesar las opciones para utilizar el Calenario
        switch (opcion) {
            case 1:
            crearEvento();
            break;

            case 2:
            mostrarEventos();
            break;

            case 3:
            filtrarEventos();
            break;

            case 4:
            eliminarEventos();
            break;

            case 5:
            mostarEstadisticas();   
            break;

            case 0:
            System.out.println("Has elegido salir del programa vuelve pronto");
            break;

            default:
            System.out.println("Tienes que elgir una opción válida");
            break;
        }

    }
    while(opcion != 0);

        //Cerrar la entrada de datos
    scanner.close();
    }

public static void crearEvento(){
    String nombre, fecha, horaComienzo, horaFinalizacion, ubicacion, url;
    int publico;
    Scanner scanner = new Scanner(System.in);

    System.out.print("Ingrese el nombre del evento: ");
    nombre = scanner.nextLine();

    System.out.print("Ingrese la fecha (dd/MM/yyyy): ");
    fecha = scanner.nextLine();

    System.out.print("Ingrese la hora de inicio (HH:mm): ");
    horaComienzo = scanner.nextLine();

    System.out.print("Ingrese la hora de finalización (HH:mm): ");
    horaFinalizacion = scanner.nextLine();

    System.out.print("Ingrese el lugar: ");
    ubicacion = scanner.nextLine();

    System.out.print("Ingrese el público objetivo (1: estudiantes, 2: profesorado, 3: comunidad universitaria): ");
    publico = scanner.nextInt();

    System.out.print("Ingrese la URL de registro: ");
    url = scanner.nextLine();

    scanner.close();
}

public static void mostrarEventos(){

}

public static void filtrarEventos(){

}

public static void actualizarEventos(){

}

public static void eliminarEventos(){

}

public static void mostarEstadisticas(){

    }
}