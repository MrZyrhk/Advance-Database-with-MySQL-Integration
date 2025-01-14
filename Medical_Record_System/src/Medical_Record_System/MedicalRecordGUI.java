/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Medical_Record_System;

/**
 *
 * @author ambet
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class MedicalRecordGUI {

    // Main Frame and Panels
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Table Models
    private DefaultTableModel patientTableModel;
    private DefaultTableModel appointmentTableModel;

    public MedicalRecordGUI() {
        initialize();
    }

    private void initialize() {
        // Main Frame
        frame = new JFrame("Medical Record System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null); // Center the window

        // Add a logo to the top
        URL logoUrl = getClass().getClassLoader().getResource("Medical_Record_System/img/icon.png");
        if (logoUrl != null) {
            ImageIcon icon = new ImageIcon(logoUrl);
            frame.setIconImage(icon.getImage()); // Set the frame icon using the loaded image
        } else {
            System.out.println("Logo image not found.");
        }
        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // Patient Management Menu
        JMenu patientMenu = new JMenu("Patient Management");
        JMenuItem addPatientMenuItem = new JMenuItem("Add Patient Record");
        JMenuItem viewPatientMenuItem = new JMenuItem("View Patient Records");
        patientMenu.add(addPatientMenuItem);
        patientMenu.add(viewPatientMenuItem);

        // Appointment Management Menu
        JMenu appointmentMenu = new JMenu("Appointment Management");
        JMenuItem recordAppointmentMenuItem = new JMenuItem("Record Appointment");
        JMenuItem viewAppointmentMenuItem = new JMenuItem("View Appointment Records");
        appointmentMenu.add(recordAppointmentMenuItem);
        appointmentMenu.add(viewAppointmentMenuItem);

        // Add Menus to Menu Bar
        menuBar.add(patientMenu);
        menuBar.add(appointmentMenu);

        // Set Menu Bar
        frame.setJMenuBar(menuBar);

        // Main Panel with CardLayout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Add Panels to CardLayout
        mainPanel.add(createAddPatientPanel(), "AddPatient");
        mainPanel.add(createViewPatientPanel(), "ViewPatient");
        mainPanel.add(createRecordAppointmentPanel(), "RecordAppointment");
        mainPanel.add(createViewAppointmentPanel(), "ViewAppointment");

        // Add Action Listeners for Menu Items
        addPatientMenuItem.addActionListener(e -> cardLayout.show(mainPanel, "AddPatient"));
        viewPatientMenuItem.addActionListener(e -> {
            populatePatientTable(); // Fetch and display patients
            cardLayout.show(mainPanel, "ViewPatient");
        });
        recordAppointmentMenuItem.addActionListener(e -> cardLayout.show(mainPanel, "RecordAppointment"));
        viewAppointmentMenuItem.addActionListener(e -> {
            populateAppointmentTable(); // Fetch and display appointments
            cardLayout.show(mainPanel, "ViewAppointment");
        });

        // Add Main Panel to Frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Panel for Adding Patient Record
    private JPanel createAddPatientPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the edges

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("First Name:"), gbc);

        gbc.gridx = 1;
        JTextField firstNameText = new JTextField();
        firstNameText.setPreferredSize(new Dimension(300, 25)); // Reduced height
        panel.add(firstNameText, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        JTextField lastNameText = new JTextField();
        lastNameText.setPreferredSize(new Dimension(300, 25)); // Reduced height
        panel.add(lastNameText, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Gender:"), gbc);

        gbc.gridx = 1;
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderDropdown = new JComboBox<>(genders);
        genderDropdown.setPreferredSize(new Dimension(300, 25)); // Reduced height
        panel.add(genderDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Contact:"), gbc);

        gbc.gridx = 1;
        JTextField contactText = new JTextField();
        contactText.setPreferredSize(new Dimension(300, 25)); // Reduced height
        panel.add(contactText, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Address:"), gbc);

        gbc.gridx = 1;
        JTextField addressText = new JTextField();
        addressText.setPreferredSize(new Dimension(300, 25)); // Reduced height
        panel.add(addressText, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Medical History:"), gbc);

        gbc.gridx = 1;
        JTextArea medicalHistoryText = new JTextArea();
        medicalHistoryText.setPreferredSize(new Dimension(300, 100)); // Same width, increased height
        panel.add(new JScrollPane(medicalHistoryText), gbc);

        // Save Button
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveClientButton = new JButton("Save Patient Record");
        panel.add(saveClientButton , gbc);

        saveClientButton .addActionListener(e -> {
            try {
                // Input validation
                if (firstNameText.getText().trim().isEmpty() || lastNameText.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "First Name and Last Name are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Save to database
                PatientDAO patientDAO = new PatientDAO();
                patientDAO.addPatient(
                        firstNameText.getText().trim(),
                        lastNameText.getText().trim(),
                        "1990-01-01", // Replace with DOB input if needed
                        (String) genderDropdown.getSelectedItem(),
                        contactText.getText().trim(),
                        addressText.getText().trim(),
                        medicalHistoryText.getText().trim()
                );
                JOptionPane.showMessageDialog(frame, "Patient record saved successfully!");

                // Clear fields
                firstNameText.setText("");
                lastNameText.setText("");
                contactText.setText("");
                addressText.setText("");
                medicalHistoryText.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error saving patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        return panel;
    }

    // Other Panels (No layout changes needed for now)
    private JPanel createViewPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        patientTableModel = new DefaultTableModel();
        JTable patientTable = new JTable(patientTableModel);

        // Add columns to the patient table
        patientTableModel.addColumn("ID");
        patientTableModel.addColumn("First Name");
        patientTableModel.addColumn("Last Name");
        patientTableModel.addColumn("DOB");
        patientTableModel.addColumn("Gender");
        patientTableModel.addColumn("Contact");
        patientTableModel.addColumn("Address");
        patientTableModel.addColumn("Medical History");

        panel.add(new JScrollPane(patientTable), BorderLayout.CENTER);
        return panel;
    }

    // Panel for Recording Appointments
    private JPanel createRecordAppointmentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the edges

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Patient ID:"), gbc);

        gbc.gridx = 1;
        JTextField patientIdText = new JTextField();
        patientIdText.setPreferredSize(new Dimension(300, 25)); // Reduced height
        panel.add(patientIdText, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Appointment Date (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        JTextField appointmentDateText = new JTextField();
        appointmentDateText.setPreferredSize(new Dimension(300, 25)); // Reduced height
        panel.add(appointmentDateText, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        JTextArea descriptionText = new JTextArea();
        descriptionText.setPreferredSize(new Dimension(300, 100)); // Same width, increased height
        panel.add(new JScrollPane(descriptionText), gbc);

        // Save Button
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveAppointmentButton = new JButton("Save Appointment");
        panel.add(saveAppointmentButton, gbc);

        // Add Action Listener for Save Button
        saveAppointmentButton.addActionListener(e -> {
            try {
                // Validate input fields
                if (patientIdText.getText().isEmpty() || appointmentDateText.getText().isEmpty() || descriptionText.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get input values
                int patientId = Integer.parseInt(patientIdText.getText());
                String appointmentDate = appointmentDateText.getText();
                String description = descriptionText.getText();

                // Add appointment to database
                AppointmentDAO appointmentDAO = new AppointmentDAO();
                appointmentDAO.addAppointment(patientId, appointmentDate, description);

                // Success message
                JOptionPane.showMessageDialog(panel, "Appointment saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields after saving
                patientIdText.setText("");
                appointmentDateText.setText("");
                descriptionText.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid Patient ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "Error saving appointment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        return panel;
    }

    // Panel for Viewing Appointment Table
    private JPanel createViewAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        appointmentTableModel = new DefaultTableModel();
        JTable appointmentTable = new JTable(appointmentTableModel);

        // Add columns to the appointment table
        appointmentTableModel.addColumn("ID");
        appointmentTableModel.addColumn("Patient ID");
        appointmentTableModel.addColumn("Date");
        appointmentTableModel.addColumn("Description");

        panel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);
        return panel;
    }

    // Table Population Methods
    private void populatePatientTable() {
        try {
            patientTableModel.setRowCount(0); // Clear existing rows
            PatientDAO patientDAO = new PatientDAO();
            ResultSet resultSet = patientDAO.getAllPatients();

            while (resultSet.next()) {
                patientTableModel.addRow(new Object[]{
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getDate("dob"),
                    resultSet.getString("gender"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("medical_history")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error fetching patients: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void populateAppointmentTable() {
        try {
            appointmentTableModel.setRowCount(0); // Clear existing rows
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            ResultSet resultSet = appointmentDAO.getAllAppointments();

            while (resultSet.next()) {
                appointmentTableModel.addRow(new Object[]{
                    resultSet.getInt("id"),
                    resultSet.getInt("patient_id"),
                    resultSet.getDate("date"),
                    resultSet.getString("description")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error fetching appointments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MedicalRecordGUI::new);
    }
}
