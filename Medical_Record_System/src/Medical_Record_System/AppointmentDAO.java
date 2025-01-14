/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Medical_Record_System;

/**
 *
 * @author ambet
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAO {
    // Add Appointment
    public void addAppointment(int patientId, String date, String description) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, date, description) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);
            statement.setString(2, date);
            statement.setString(3, description);
            statement.executeUpdate();
        }
    }

    // Get All Appointments
    public ResultSet getAllAppointments() throws SQLException {
        String sql = "SELECT * FROM appointments";
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        return statement.executeQuery();
    }
}

