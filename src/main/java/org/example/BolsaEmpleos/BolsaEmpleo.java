package org.example.BolsaEmpleos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.example.aspirantes.Aspirante;
import org.example.database.H2DB;

public class BolsaEmpleo {
    private Connection connection;

    public BolsaEmpleo(Connection connection) {
        this.connection = connection;
        crearTablaAspirantes();
    }

    private void crearTablaAspirantes() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS aspirantes (" +
                    "cedula VARCHAR(255) PRIMARY KEY," +
                    "nombre VARCHAR(255)," +
                    "edad INT," +
                    "experiencia INT," +
                    "profesion VARCHAR(255)," +
                    "telefono VARCHAR(255)" +
                    ")";
            PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarAspirante(Scanner scanner) {
        System.out.print("Ingrese la cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Ingrese el nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consume la nueva línea después del número
        System.out.print("Ingrese la experiencia en años: ");
        int experiencia = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la profesión: ");
        String profesion = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();

        Aspirante aspirante = new Aspirante(cedula, nombre, edad, experiencia, profesion, telefono);

        H2DB.insertarAspirante(aspirante);

        System.out.println("Aspirante agregado exitosamente.");
    }

    public String agregarAspirante2(Aspirante aspirante) {
        try {
            H2DB.insertarAspirante(aspirante);
            return "Aspirante agregado exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al agregar el aspirante.";
        }
    }

    public List<Aspirante> obtenerAspirantes() {
        List<Aspirante> aspirantes = new ArrayList<>();
        try {
            String selectSQL = "SELECT * FROM aspirantes";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String cedula = resultSet.getString("cedula");
                String nombre = resultSet.getString("nombre");
                int edad = resultSet.getInt("edad");
                int experiencia = resultSet.getInt("experiencia");
                String profesion = resultSet.getString("profesion");
                String telefono = resultSet.getString("telefono");
                Aspirante aspirante = new Aspirante(cedula, nombre, edad, experiencia, profesion, telefono);
                aspirantes.add(aspirante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aspirantes;
    }

    public void mostrarCedulas() {
        List<Aspirante> aspirantes = obtenerAspirantes();
        System.out.println("Cédulas de aspirantes:");
        for (Aspirante aspirante : aspirantes) {
            System.out.println(aspirante.getCedula());
        }
    }

    public void mostrarInformacionDetallada(Scanner scanner) {
        System.out.print("Ingrese la cédula del aspirante: ");
        String cedula = scanner.nextLine();

        Aspirante aspirante = H2DB.consultarAspirantePorCedula(cedula);

        if (aspirante != null) {
            System.out.println("Información detallada del aspirante:");
            System.out.println("Cédula: " + aspirante.getCedula());
            System.out.println("Nombre: " + aspirante.getNombre());
            System.out.println("Edad: " + aspirante.getEdad());
            System.out.println("Experiencia en años: " + aspirante.getExperiencia());
            System.out.println("Profesión: " + aspirante.getProfesion());
            System.out.println("Teléfono: " + aspirante.getTelefono());
        } else {
            System.out.println("No se encontró ningún aspirante con esa cédula.");
        }
    }

    public void mostrarInformacionDetallada2(Aspirante aspirante) {
        System.out.println("Información detallada del aspirante:");
        System.out.println("Cédula: " + aspirante.getCedula());
        System.out.println("Nombre: " + aspirante.getNombre());
        System.out.println("Edad: " + aspirante.getEdad());
        System.out.println("Experiencia en años: " + aspirante.getExperiencia());
        System.out.println("Profesión: " + aspirante.getProfesion());
        System.out.println("Teléfono: " + aspirante.getTelefono());
        System.out.println("------------------------------"); // Línea divisoria
    }

    public String mostrarInformacionDetallada3(Aspirante aspirante) {
        StringBuilder info = new StringBuilder();
        info.append("Información detallada del aspirante:\n");
        info.append("Cédula: ").append(aspirante.getCedula()).append("\n");
        info.append("Nombre: ").append(aspirante.getNombre()).append("\n");
        info.append("Edad: ").append(aspirante.getEdad()).append("\n");
        info.append("Experiencia en años: ").append(aspirante.getExperiencia()).append("\n");
        info.append("Profesión: ").append(aspirante.getProfesion()).append("\n");
        info.append("Teléfono: ").append(aspirante.getTelefono()).append("\n");
        info.append("------------------------------\n"); // Línea divisoria
        return info.toString();
    }


    public void buscarPorNombre(Scanner scanner) {
        System.out.print("Ingrese el nombre del aspirante: ");
        String nombre = scanner.nextLine();
        List<Aspirante> aspirantes = obtenerAspirantes();
        boolean encontrado = false;
        for (Aspirante aspirante : aspirantes) {
            if (aspirante.getNombre().equalsIgnoreCase(nombre)) {
                mostrarInformacionDetallada2(aspirante);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró ningún aspirante con ese nombre.");
        }
    }

    public void ordenarPorExperiencia() {
        List<Aspirante> aspirantes = obtenerAspirantes();
        Collections.sort(aspirantes, Comparator.comparingInt(Aspirante::getExperiencia));
        System.out.println("Aspirantes ordenados por experiencia:");
        for (Aspirante aspirante : aspirantes) {
            mostrarInformacionDetallada2(aspirante);
        }
    }

    public void ordenarPorEdad() {
        List<Aspirante> aspirantes = obtenerAspirantes();
        Collections.sort(aspirantes, Comparator.comparingInt(Aspirante::getEdad));
        System.out.println("Aspirantes ordenados por edad:");
        for (Aspirante aspirante : aspirantes) {
            mostrarInformacionDetallada2(aspirante);
        }
    }

    public void ordenarPorProfesion() {
        List<Aspirante> aspirantes = obtenerAspirantes();
        Collections.sort(aspirantes, Comparator.comparing(Aspirante::getProfesion));
        System.out.println("Aspirantes ordenados por profesión:");
        for (Aspirante aspirante : aspirantes) {
            mostrarInformacionDetallada2(aspirante);
        }
    }

    public void contratarAspirante(Scanner scanner) {
        System.out.print("Ingrese la cédula del aspirante que desea contratar: ");
        String cedula = scanner.nextLine();
        List<Aspirante> aspirantes = obtenerAspirantes();
        for (int i = 0; i < aspirantes.size(); i++) {
            if (aspirantes.get(i).getCedula().equals(cedula)) {
                aspirantes.remove(i);
                H2DB.eliminarAspirantePorCedula(cedula);
                System.out.println("Aspirante contratado y eliminado de la lista.");
                return;
            }
        }
        System.out.println("No se encontró ningún aspirante con esa cédula.");
    }

    public String contratarAspirante2(String cedula) {
        List<Aspirante> aspirantes = obtenerAspirantes();
        for (int i = 0; i < aspirantes.size(); i++) {
            if (aspirantes.get(i).getCedula().equals(cedula)) {
                aspirantes.remove(i);
                H2DB.eliminarAspirantePorCedula(cedula);
                return "Aspirante contratado y eliminado de la lista.";
            }
        }
        return "No se encontró ningún aspirante con esa cédula.";
    }


    public void eliminarPorExperiencia(Scanner scanner) {
        System.out.print("Ingrese la cantidad mínima de años de experiencia: ");
        int minExperiencia = scanner.nextInt();
        scanner.nextLine();
        List<Aspirante> aspirantes = obtenerAspirantes();
        aspirantes.removeIf(aspirante -> aspirante.getExperiencia() < minExperiencia);
        System.out.println("Aspirantes con menos experiencia eliminados.");
    }

    public void promedioEdadAspirantes() {
        List<Aspirante> aspirantes = obtenerAspirantes();
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

    public void consultarAspiranteMasJoven() {
        List<Aspirante> aspirantes = H2DB.consultarAspirantes();

        if (aspirantes.isEmpty()) {
            System.out.println("No hay aspirantes en la lista.");
            return;
        }

        Aspirante aspiranteMasJoven = aspirantes.get(0);

        for (Aspirante aspirante : aspirantes) {
            if (aspirante.getEdad() < aspiranteMasJoven.getEdad()) {
                aspiranteMasJoven = aspirante;
            }
        }

        System.out.println("Aspirante más joven:");
        mostrarInformacionDetallada2(aspiranteMasJoven);
    }

    public String buscarAspirantesPorNombre(String nombre) {
        List<Aspirante> aspirantes = obtenerAspirantes();
        StringBuilder infoAspirantes = new StringBuilder();
        boolean encontrado = false;
        for (Aspirante aspirante : aspirantes) {
            if (aspirante.getNombre().equalsIgnoreCase(nombre)) {
                infoAspirantes.append(mostrarInformacionDetallada3(aspirante));
                encontrado = true;
            }
        }
        if (!encontrado) {
            infoAspirantes.append("No se encontró ningún aspirante con ese nombre.");
        }
        return infoAspirantes.toString();
    }

    public String ordenarAspirantesPorExperiencia() {
        List<Aspirante> aspirantes = H2DB.consultarAspirantes();
        aspirantes.sort(Comparator.comparingInt(Aspirante::getExperiencia));
        StringBuilder infoAspirantes = new StringBuilder("Aspirantes ordenados por experiencia:\n");
        for (Aspirante aspirante : aspirantes) {
            infoAspirantes.append(mostrarInformacionDetallada3(aspirante));
        }
        return infoAspirantes.toString();
    }

    public String ordenarAspirantesPorEdad() {
        List<Aspirante> aspirantes = H2DB.consultarAspirantes();
        aspirantes.sort(Comparator.comparingInt(Aspirante::getEdad));
        StringBuilder infoAspirantes = new StringBuilder("Aspirantes ordenados por edad:\n");
        for (Aspirante aspirante : aspirantes) {
            infoAspirantes.append(mostrarInformacionDetallada3(aspirante));
        }
        return infoAspirantes.toString();
    }

    public String ordenarAspirantesPorProfesion() {
        List<Aspirante> aspirantes = H2DB.consultarAspirantes();
        aspirantes.sort(Comparator.comparing(Aspirante::getProfesion));
        StringBuilder infoAspirantes = new StringBuilder("Aspirantes ordenados por profesión:\n");
        for (Aspirante aspirante : aspirantes) {
            infoAspirantes.append(mostrarInformacionDetallada3(aspirante));
        }
        return infoAspirantes.toString();
    }

    public String obtenerCedulasAspirantes() {
        List<Aspirante> aspirantes = obtenerAspirantes();
        StringBuilder cedulas = new StringBuilder("Cédulas de aspirantes:\n");
        for (Aspirante aspirante : aspirantes) {
            cedulas.append(aspirante.getCedula()).append("\n");
        }
        return cedulas.toString();
    }

    public String eliminarAspirantesPorExperiencia(int minExperiencia) {
        try {
            List<Aspirante> aspirantes = obtenerAspirantes();

            aspirantes.removeIf(aspirante -> aspirante.getExperiencia() < minExperiencia);

            StringBuilder result = new StringBuilder("Aspirantes con al menos " + minExperiencia + " años de experiencia eliminados:\n");
            for (Aspirante aspirante : aspirantes) {
                result.append(mostrarInformacionDetallada3(aspirante));
            }

            return result.toString();
        } catch (NumberFormatException e) {
            return "La experiencia mínima debe ser un número válido.";
        }
    }

    public double calcularPromedioEdadAspirantes() {
        List<Aspirante> aspirantes = obtenerAspirantes();

        if (aspirantes.isEmpty()) {
            return 0.0;
        }

        int sumaEdades = 0;
        for (Aspirante aspirante : aspirantes) {
            sumaEdades += aspirante.getEdad();
        }

        return (double) sumaEdades / aspirantes.size();
    }



}
