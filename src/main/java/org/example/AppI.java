package org.example;

import org.example.BolsaEmpleos.BolsaEmpleo;
import org.example.aspirantes.Aspirante;
import org.example.database.H2DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppI extends JFrame implements ActionListener {
    private JTextField cedulaField, nombreField, edadField, experienciaField, profesionField, telefonoField;
    private JTextArea outputArea;
    private JButton agregarButton, mostrarCedulasButton, buscarPorNombreButton, ordenarPorExperienciaButton,
            ordenarPorEdadButton, ordenarPorProfesionButton, contratarButton, eliminarPorExperienciaButton, promedioEdadButton;

    private BolsaEmpleo bolsa;
    Connection connection = null;

    public AppI() throws ClassNotFoundException, SQLException {

        H2DB.inicializarBaseDatos();

        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:./bolsaEmpleo", "sa", "");

        bolsa = new BolsaEmpleo(connection);

        setTitle("Bolsa de Empleo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaField = new JTextField();
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField();
        JLabel edadLabel = new JLabel("Edad:");
        edadField = new JTextField();
        JLabel experienciaLabel = new JLabel("Experiencia (años):");
        experienciaField = new JTextField();
        JLabel profesionLabel = new JLabel("Profesión:");
        profesionField = new JTextField();
        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoField = new JTextField();

        inputPanel.add(cedulaLabel);
        inputPanel.add(cedulaField);
        inputPanel.add(nombreLabel);
        inputPanel.add(nombreField);
        inputPanel.add(edadLabel);
        inputPanel.add(edadField);
        inputPanel.add(experienciaLabel);
        inputPanel.add(experienciaField);
        inputPanel.add(profesionLabel);
        inputPanel.add(profesionField);
        inputPanel.add(telefonoLabel);
        inputPanel.add(telefonoField);

        agregarButton = new JButton("Agregar Aspirante");
        agregarButton.addActionListener(this);
        inputPanel.add(agregarButton);

        mostrarCedulasButton = new JButton("Mostrar Cédulas");
        mostrarCedulasButton.addActionListener(this);
        inputPanel.add(mostrarCedulasButton);

        buscarPorNombreButton = new JButton("Buscar por Nombre");
        buscarPorNombreButton.addActionListener(this);
        inputPanel.add(buscarPorNombreButton);

        ordenarPorExperienciaButton = new JButton("Ordenar por Experiencia");
        ordenarPorExperienciaButton.addActionListener(this);
        inputPanel.add(ordenarPorExperienciaButton);

        ordenarPorEdadButton = new JButton("Ordenar por Edad");
        ordenarPorEdadButton.addActionListener(this);
        inputPanel.add(ordenarPorEdadButton);

        ordenarPorProfesionButton = new JButton("Ordenar por Profesión");
        ordenarPorProfesionButton.addActionListener(this);
        inputPanel.add(ordenarPorProfesionButton);

        contratarButton = new JButton("Contratar Aspirante");
        contratarButton.addActionListener(this);
        inputPanel.add(contratarButton);

        eliminarPorExperienciaButton = new JButton("Eliminar por Experiencia");
        eliminarPorExperienciaButton.addActionListener(this);
        inputPanel.add(eliminarPorExperienciaButton);

        promedioEdadButton = new JButton("Promedio de Edad");
        promedioEdadButton.addActionListener(this);
        inputPanel.add(promedioEdadButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == agregarButton) {
            agregarAspirante();
        } else if (e.getSource() == mostrarCedulasButton) {
            mostrarCedulas();
        } else if (e.getSource() == buscarPorNombreButton) {
            buscarPorNombre();
        } else if (e.getSource() == ordenarPorExperienciaButton) {
            ordenarPorExperiencia();
        } else if (e.getSource() == ordenarPorEdadButton) {
            ordenarPorEdad();
        } else if (e.getSource() == ordenarPorProfesionButton) {
            ordenarPorProfesion();
        } else if (e.getSource() == contratarButton) {
            contratarAspirante();
        } else if (e.getSource() == eliminarPorExperienciaButton) {
            eliminarPorExperiencia();
        } else if (e.getSource() == promedioEdadButton) {
            calcularPromedioEdad();
        }
    }

    private void agregarAspirante() {
        String cedula = cedulaField.getText();
        String nombre = nombreField.getText();
        int edad = Integer.parseInt(edadField.getText());
        int experiencia = Integer.parseInt(experienciaField.getText());
        String profesion = profesionField.getText();
        String telefono = telefonoField.getText();

        Aspirante aspirante = new Aspirante(cedula, nombre, edad, experiencia, profesion, telefono);
        bolsa.agregarAspirante2(aspirante);
        outputArea.setText("Aspirante agregado exitosamente.");
    }

    private void mostrarCedulas() {
        String cedulas = bolsa.obtenerCedulasAspirantes();
        outputArea.setText(cedulas);
    }

    private void buscarPorNombre() {
        String nombre = nombreField.getText();
        String resultado = bolsa.buscarAspirantesPorNombre(nombre);
        outputArea.setText(resultado);
    }

    private void ordenarPorExperiencia() {
        String resultado = bolsa.ordenarAspirantesPorExperiencia();
        outputArea.setText(resultado);
    }

    private void ordenarPorEdad() {
        String resultado = bolsa.ordenarAspirantesPorEdad();
        outputArea.setText(resultado);
    }

    private void ordenarPorProfesion() {
        String resultado = bolsa.ordenarAspirantesPorProfesion();
        outputArea.setText(resultado);
    }

    private void contratarAspirante() {
        String cedula = cedulaField.getText();
        bolsa.contratarAspirante2(cedula);
        outputArea.setText("Aspirante contratado y eliminado de la lista.");
    }

    private void eliminarPorExperiencia() {
        int minExperiencia = Integer.parseInt(experienciaField.getText());
        bolsa.eliminarAspirantesPorExperiencia(minExperiencia);
        outputArea.setText("Aspirantes con menos experiencia eliminados.");
    }

    private void calcularPromedioEdad() {
        double promedio = bolsa.calcularPromedioEdadAspirantes();
        outputArea.setText("Promedio de edad de los aspirantes: " + promedio);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppI ui = null;
            try {
                ui = new AppI();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ui.setVisible(true);
        });
    }
}
