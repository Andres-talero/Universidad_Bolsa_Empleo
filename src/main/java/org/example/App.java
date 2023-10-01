package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.example.BolsaEmpleos.BolsaEmpleo;
import org.example.database.H2DB;

public class App {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./bolsaEmpleo", "sa", "");
            H2DB.inicializarBaseDatos();

            BolsaEmpleo bolsa = new BolsaEmpleo(connection);
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
                System.out.println("11. Aspirante más joven");
                System.out.println("0. Salir");
                System.out.print("Ingrese su opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

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
                    case 11:
                        bolsa.consultarAspiranteMasJoven();
                        break;
                    case 0:
                        System.out.println("¡Adiós!");
                        scanner.close();
                        H2DB.cerrarConexion();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida, por favor ingrese un número válido.");
                        break;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
