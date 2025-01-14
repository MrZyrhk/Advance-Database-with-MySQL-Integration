/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Advance_Database;

/**
 *
 * @author ambet
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAO {
    // Add Patient (Existing Code)
    public void addPatient(String firstName, String lastName, String dob, String gender, String contact, String address, String medicalHistory) {
        String sql = "INSERT INTO patients (first_name, last_name, dob, gender, contact, address, medical_history) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (connection == null) {
                System.err.println("Error: No database connection available.");
                return;
            }

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, dob);
            statement.setString(4, gender);
            statement.setString(5, contact);
            statement.setString(6, address);
            statement.setString(7, medicalHistory);

            statement.executeUpdate();
            System.out.println("Patient added successfully.");
        } catch (SQLException e) {
            System.err.println("Error: Unable to add patient to the database.");
            e.printStackTrace();
        }
    }

    // Fetch All Patients
    public ResultSet getAllPatients() throws SQLException {
        String sql = "SELECT * FROM patients";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            throw new SQLException("No database connection available.");
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        return statement.executeQuery(); // Return ResultSet
    }
}




