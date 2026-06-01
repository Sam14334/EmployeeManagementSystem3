package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class AddEmployeeFrame extends JFrame implements ActionListener {

    private JTextField txtId, txtUser, txtPass, txtFn, txtLn, txtEmail, txtPhone, txtDept, txtRole, txtStatus, txtSalary;
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

        // Creating and positioning the components
        txtId = createFormRow("Employee ID:", labelX, startY, fieldX, width, height); startY += spacing;
        txtUser = createFormRow("Username:", labelX, startY, fieldX, width, height); startY += spacing;
        txtPass = createFormRow("Password:", labelX, startY, fieldX, width, height); startY += spacing;
        txtFn = createFormRow("First Name:", labelX, startY, fieldX, width, height); startY += spacing;
        txtLn = createFormRow("Last Name:", labelX, startY, fieldX, width, height); startY += spacing;
        txtEmail = createFormRow("Email:", labelX, startY, fieldX, width, height); startY += spacing;
        txtPhone = createFormRow("Phone Number:", labelX, startY, fieldX, width, height); startY += spacing;
        txtDept = createFormRow("Department:", labelX, startY, fieldX, width, height); startY += spacing;
        txtRole = createFormRow("Role:", labelX, startY, fieldX, width, height); startY += spacing;
        txtStatus = createFormRow("Employment Status:", labelX, startY, fieldX, width, height); startY += spacing;
        txtSalary = createFormRow("Salary:", labelX, startY, fieldX, width, height);

        // Save Button (Styled with Accent Blue)
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

        // Cancel Button (Styled with Flat Dark Gray)
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            // Validation step to make sure required inputs aren't blank
            if (txtId.getText().trim().isEmpty() || txtUser.getText().trim().isEmpty() || txtFn.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please populate required identification fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Gather all the textfield attributes into an array
            Object[] newEmployeeData = {
                txtId.getText().trim(), txtUser.getText().trim(), txtPass.getText().trim(),
                txtFn.getText().trim(), txtLn.getText().trim(), txtEmail.getText().trim(),
                txtPhone.getText().trim(), txtDept.getText().trim(), txtRole.getText().trim(),
                txtStatus.getText().trim(), txtSalary.getText().trim()
            };

            // Call the data injection helper method on the open parent dashboard
            parentFrame.addEmployeeRow(newEmployeeData);
            dispose(); 
        } else if (e.getSource() == btnCancel) {
            dispose();
        }
    }
}