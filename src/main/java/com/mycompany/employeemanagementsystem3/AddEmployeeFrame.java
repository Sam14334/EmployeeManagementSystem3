package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddEmployeeFrame extends JFrame implements ActionListener {

    private JTextField txtId, txtUser, txtPass, txtFn, txtLn, txtEmail, txtPhone, txtSalary;
    private JComboBox<String> cbDept, cbRole, cbStatus; 
    private JButton btnSave, btnCancel;
    private HRFrame parentFrame;

    public AddEmployeeFrame(HRFrame parent) {
        this.parentFrame = parent;

        setTitle("StaffSync - Add New Employee Record");
        setSize(500, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        // Header Panel matching the system's dark scheme
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(33, 47, 61));
        headerPanel.setBounds(0, 0, 500, 70);
        headerPanel.setLayout(null);

        JLabel lblTitle = new JLabel("Add Employee Record", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(0, 20, 500, 30);
        headerPanel.add(lblTitle);
        add(headerPanel);

        // Form Coordinates Configuration Base
        int startY = 100;
        int labelX = 40;
        int fieldX = 180;
        int width = 260;
        int height = 32;
        int spacing = 45;

        // Creating and positioning components
        txtId = createFormRow("Employee ID:", labelX, startY, fieldX, width, height); startY += spacing;
        txtUser = createFormRow("Username:", labelX, startY, fieldX, width, height); startY += spacing;
        txtPass = createFormRow("Password:", labelX, startY, fieldX, width, height); startY += spacing;
        txtFn = createFormRow("First Name:", labelX, startY, fieldX, width, height); startY += spacing;
        txtLn = createFormRow("Last Name:", labelX, startY, fieldX, width, height); startY += spacing;
        txtEmail = createFormRow("Email:", labelX, startY, fieldX, width, height); startY += spacing;
        txtPhone = createFormRow("Phone Number:", labelX, startY, fieldX, width, height); startY += spacing;
        
        cbDept = createDropdownRow("Department:", labelX, startY, fieldX, width, height, null); startY += spacing;
        
        String[] defaultRoles = {"HR Staff", "Manager", "Employee"};
        cbRole = createDropdownRow("Role:", labelX, startY, fieldX, width, height, defaultRoles); startY += spacing;
        
        cbStatus = createDropdownRow("Employment Status:", labelX, startY, fieldX, width, height, null); startY += spacing;
        
        txtSalary = createFormRow("Salary:", labelX, startY, fieldX, width, height);

        // Save Button
        btnSave = new JButton("Save Record");
        btnSave.setBounds(100, 630, 140, 40);
        btnSave.setBackground(new Color(52, 152, 219));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(this);
        add(btnSave);

        // Cancel Button
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(260, 630, 140, 40);
        btnCancel.setBackground(new Color(127, 140, 141));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(this);
        add(btnCancel);

        // Safely fetch lookups from your local database server instance
        populateDropdowns();

        setVisible(true);
    }

    private JTextField createFormRow(String labelText, int lx, int y, int fx, int w, int h) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(44, 62, 80));
        label.setBounds(lx, y, fx - lx - 10, h);
        add(label);

        JTextField field = new JTextField();
        field.setBounds(fx, y, w, h);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));
        add(field);
        return field;
    }

    private JComboBox<String> createDropdownRow(String labelText, int lx, int y, int fx, int w, int h, String[] defaultItems) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(44, 62, 80));
        label.setBounds(lx, y, fx - lx - 10, h);
        add(label);

        JComboBox<String> comboBox = (defaultItems != null) ? new JComboBox<>(defaultItems) : new JComboBox<>();
        comboBox.setBounds(fx, y, w, h);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboBox.setBackground(Color.WHITE);
        add(comboBox);
        return comboBox;
    }

    private void populateDropdowns() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                throw new SQLException("Database connection returned null.");
            }

            // Load Departments
            Statement stmtDept = conn.createStatement();
            ResultSet rsDept = stmtDept.executeQuery("SELECT dept_name FROM departments ORDER BY dept_id ASC");
            while (rsDept.next()) {
                cbDept.addItem(rsDept.getString("dept_name"));
            }

            // Load Employment Statuses
            Statement stmtStatus = conn.createStatement();
            ResultSet rsStatus = stmtStatus.executeQuery("SELECT status_name FROM employment_statuses ORDER BY status_id ASC");
            while (rsStatus.next()) {
                cbStatus.addItem(rsStatus.getString("status_name"));
            }

            // Enforce safe system defaults if structural records were successfully read
            if (cbDept.getItemCount() > 0) cbDept.setSelectedItem("Operations");
            if (cbStatus.getItemCount() > 0) cbStatus.setSelectedItem("Contractual");
            cbRole.setSelectedItem("HR Staff");

        } catch (SQLException ex) {
            ex.printStackTrace();
            btnSave.setEnabled(false); // Lock the transactional interface to prevent illegal schema writes
            JOptionPane.showMessageDialog(this, 
                "Communications link failure: Could not reach database server.\n" +
                "Please make sure XAMPP / MySQL is actively running.\n\nDetails: " + ex.getMessage(), 
                "Critical Database Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected initialization error occurred: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            
            // 1. Text Field Blank & Whitespace Verification
            if (txtId.getText().trim().isEmpty() || txtUser.getText().trim().isEmpty() || 
                txtPass.getText().trim().isEmpty() || txtFn.getText().trim().isEmpty() || 
                txtLn.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All core credential and name fields must be filled.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. ComboBox Selection Integrity Validation
            if (cbDept.getSelectedItem() == null || cbStatus.getSelectedItem() == null || cbRole.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Relational options (Department/Role/Status) cannot be blank.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Clean numeric tracking arguments 
            double parsedSalary = 0.00;
            try {
                String cleanSalaryText = txtSalary.getText().trim().replace(",", "");
                if (!cleanSalaryText.isEmpty()) {
                    parsedSalary = Double.parseDouble(cleanSalaryText);
                    if (parsedSalary < 0) {
                        JOptionPane.showMessageDialog(this, "Salary cannot be a negative value.", "Input Formatting Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric figure for Salary (e.g., 25000 or 32500.50).", "Input Formatting Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedDept = cbDept.getSelectedItem().toString();
            String selectedRole = cbRole.getSelectedItem().toString();
            String selectedStatus = cbStatus.getSelectedItem().toString();

            String query = "INSERT INTO employees (employee_id, username, password, first_name, last_name, email, phone_number, dept_id, role, status_id, salary) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, "
                         + "(SELECT dept_id FROM departments WHERE dept_name = ? LIMIT 1), ?, "
                         + "(SELECT status_id FROM employment_statuses WHERE status_name = ? LIMIT 1), ?)";

            // 4. Try-With-Resources Transaction Handler
            try (Connection conn = DBConnection.getConnection()) {
                if (conn == null) {
                    throw new SQLException("Failed to establish a live connection to database storage.");
                }
                
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, txtId.getText().trim());
                    pstmt.setString(2, txtUser.getText().trim());
                    pstmt.setString(3, txtPass.getText().trim());
                    pstmt.setString(4, txtFn.getText().trim());
                    pstmt.setString(5, txtLn.getText().trim());
                    pstmt.setString(6, txtEmail.getText().trim());
                    pstmt.setString(7, txtPhone.getText().trim());
                    pstmt.setString(8, selectedDept);
                    pstmt.setString(9, selectedRole);
                    pstmt.setString(10, selectedStatus);
                    pstmt.setDouble(11, parsedSalary);

                    int inserted = pstmt.executeUpdate();
                    if (inserted > 0) {
                        JOptionPane.showMessageDialog(this, "Employee record saved to database successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Safety fallback wrapper for refresh trigger communication across frame objects
                        if (parentFrame != null) {
                            parentFrame.loadDatabaseData(""); 
                        }
                        dispose();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Check if the crash is caused by duplicating primary keys or unique field allocations (MySQL Error 1062)
                if (ex.getErrorCode() == 1062) {
                    JOptionPane.showMessageDialog(this, 
                        "Data Conflict: The Employee ID or Username you entered already exists in the system.", 
                        "Database Collision Error", 
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Database Write Interrupted: " + ex.getMessage(), 
                        "Database Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An unhandled runtime error occurred: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnCancel) {
            dispose();
        }
    }
}