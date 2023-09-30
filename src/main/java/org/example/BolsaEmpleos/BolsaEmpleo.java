package org.example.BolsaEmpleos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.example.aspirantes.Aspirante;

public class BolsaEmpleo {
    private List<Aspirante> aspirantes = new ArrayList<>();

    public static void main(String[] args) {
        BolsaEmpleo bolsa = new BolsaEmpleo();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenú de opciones:");
            System.out.println("1. Agregar nuevo aspirante");
            System.out.println("2. Mostrar cédulas de aspirantes");
            System.out.println("3. Mostrar información detallada de un aspirante");
            System.out.println("4. Buscar aspirante por nombre");
            System.out.println("5. Ordenar aspirantes por experiencia");
            System.out.println("6. Ordenar aspirantes por edad");
            System.out.println("7. Ordenar aspirantes por profesión");
            System.out.println("8. Contratar un aspirante");
            System.out.println("9. Eliminar aspirantes con menos experiencia");
            System.out.println("10. Promedio de edad de los aspirantes");
            System.out.println("0. Salir");
            System.out.print("Ingrese su opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consume la nueva línea después del número

            switch (opcion) {
                case 1:
                    bolsa.agregarAspirante(scanner);
                    break;
                case 2:
                    bolsa.mostrarCedulas();
                    break;
                case 3:
                    bolsa.mostrarInformacionDetallada(scanner);
                    break;
                case 4:
                    bolsa.buscarPorNombre(scanner);
                    break;
                case 5:
                    bolsa.ordenarPorExperiencia();
                    break;
                case 6:
                    bolsa.ordenarPorEdad();
                    break;
                case 7:
                    bolsa.ordenarPorProfesion();
                    break;
                case 8:
                    bolsa.contratarAspirante(scanner);
                    break;
                case 9:
                    bolsa.eliminarPorExperiencia(scanner);
                    break;
                case 10:
                    bolsa.promedioEdadAspirantes();
                    break;
                case 0:
                    System.out.println("¡Adiós!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida, por favor ingrese un número válido.");
                    break;
            }
        }
    }

    public void agregarAspirante(Scanner scanner) {
        System.out.print("Ingrese la cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Ingrese el nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la experiencia en años: ");
        int experiencia = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la profesión: ");
        String profesion = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();

        Aspirante aspirante = new Aspirante(cedula, nombre, edad, experiencia, profesion, telefono);
        aspirantes.add(aspirante);

        System.out.println("Aspirante agregado exitosamente.");
    }

    public void mostrarCedulas() {
        System.out.println("Cédulas de aspirantes:");
        for (Aspirante aspirante : aspirantes) {
            System.out.println(aspirante.getCedula());
        }
    }

    public void mostrarInformacionDetallada(Scanner scanner) {
        System.out.print("Ingrese la cédula del aspirante: ");
        String cedula = scanner.nextLine();
        for (Aspirante aspirante : aspirantes) {
            if (aspirante.getCedula().equals(cedula)) {
                System.out.println("Información detallada del aspirante:");
                System.out.println("Cédula: " + aspirante.getCedula());
                System.out.println("Nombre: " + aspirante.getNombre());
                System.out.println("Edad: " + aspirante.getEdad());
                System.out.println("Experiencia en años: " + aspirante.getExperiencia());
                System.out.println("Profesión: " + aspirante.getProfesion());
                System.out.println("Teléfono: " + aspirante.getTelefono());
                return;
            }
        }
        System.out.println("No se encontró ningún aspirante con esa cédula.");
    }

    public void buscarPorNombre(Scanner scanner) {
        System.out.print("Ingrese el nombre del aspirante: ");
        String nombre = scanner.nextLine();
        boolean encontrado = false;
        for (Aspirante aspirante : aspirantes) {
            if (aspirante.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Cédula: " + aspirante.getCedula());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró ningún aspirante con ese nombre.");
        }
    }

    public void ordenarPorExperiencia() {
        Collections.sort(aspirantes, Comparator.comparingInt(Aspirante::getExperiencia));
        System.out.println("Aspirantes ordenados por experiencia.");
    }

    public void ordenarPorEdad() {
        Collections.sort(aspirantes, Comparator.comparingInt(Aspirante::getEdad));
        System.out.println("Aspirantes ordenados por edad.");
    }

    public void ordenarPorProfesion() {
        Collections.sort(aspirantes, Comparator.comparing(Aspirante::getProfesion));
        System.out.println("Aspirantes ordenados por profesión.");
    }

    public void contratarAspirante(Scanner scanner) {
        System.out.print("Ingrese la cédula del aspirante que desea contratar: ");
        String cedula = scanner.nextLine();
        for (int i = 0; i < aspirantes.size(); i++) {
            if (aspirantes.get(i).getCedula().equals(cedula)) {
                aspirantes.remove(i);
                System.out.println("Aspirante contratado y eliminado de la lista.");
                return;
            }
        }
        System.out.println("No se encontró ningún aspirante con esa cédula.");
    }

    public void eliminarPorExperiencia(Scanner scanner) {
        System.out.print("Ingrese la cantidad mínima de años de experiencia: ");
        int minExperiencia = scanner.nextInt();
        scanner.nextLine();
        aspirantes.removeIf(aspirante -> aspirante.getExperiencia() < minExperiencia);
        System.out.println("Aspirantes con menos experiencia eliminados.");
    }

    public void promedioEdadAspirantes() {
        if (aspirantes.isEmpty()) {
            System.out.println("No hay aspirantes en la lista.");
            return;
        }
        int sumaEdades = 0;
        for (Aspirante aspirante : aspirantes) {
            sumaEdades += aspirante.getEdad();
        }
        double promedio = (double) sumaEdades / aspirantes.size();
        System.out.println("Promedio de edad de los aspirantes: " + promedio);
    }
}
