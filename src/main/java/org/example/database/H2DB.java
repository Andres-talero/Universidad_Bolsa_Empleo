package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.aspirantes.Aspirante;

public class H2DB {
    private static final String URL = "jdbc:h2:./bolsaEmpleo";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static void inicializarBaseDatos() {
        try {
            Class.forName("org.h2.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            crearTablaAspirantes();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void crearTablaAspirantes() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS aspirantes (" +
                    "cedula VARCHAR(255) PRIMARY KEY," +
                    "nombre VARCHAR(255)," +
                    "edad INT," +
                    "experiencia INT," +
                    "profesion VARCHAR(255)," +
                    "telefono VARCHAR(255)" +
                    ")";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cerrarConexion() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertarAspirante(Aspirante aspirante) {
        try {
            String sql = "INSERT INTO aspirantes (cedula, nombre, edad, experiencia, profesion, telefono) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, aspirante.getCedula());
            statement.setString(2, aspirante.getNombre());
            statement.setInt(3, aspirante.getEdad());
            statement.setInt(4, aspirante.getExperiencia());
            statement.setString(5, aspirante.getProfesion());
            statement.setString(6, aspirante.getTelefono());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Aspirante> consultarAspirantes() {
        List<Aspirante> aspirantes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM aspirantes";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
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

    public static Aspirante consultarAspirantePorCedula(String cedula) {
        Aspirante aspirante = null;
        try {
            String sql = "SELECT * FROM aspirantes WHERE cedula = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cedula);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                int edad = resultSet.getInt("edad");
                int experiencia = resultSet.getInt("experiencia");
                String profesion = resultSet.getString("profesion");
                String telefono = resultSet.getString("telefono");
                aspirante = new Aspirante(cedula, nombre, edad, experiencia, profesion, telefono);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aspirante;
    }

    public static void eliminarAspirantePorCedula(String cedula) {
        try {
            String sql = "DELETE FROM aspirantes WHERE cedula = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cedula);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
