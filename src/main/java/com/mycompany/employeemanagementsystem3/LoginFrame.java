package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class LoginFrame extends JFrame implements ActionListener {

    JTextField txtUsername;
    JPasswordField txtPassword;
    JComboBox<String> cbRole;

    JButton btnSubmit, btnClear;

    public LoginFrame() {

        setTitle("StaffSync - Login");
        setSize(800, 430);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(30, 30, 30));
        setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());

        JLabel leftPanel = new JLabel(new ImageIcon("src\\main\\java\\images\\bgimage2blur.png"));
        leftPanel.setBounds(0, 0, 400, 400);
        leftPanel.setBackground(new Color(230, 230, 230));
        leftPanel.setLayout(null);
        add(leftPanel);

        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setBounds(136, 70, 128, 128);
//        lblLogo.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
        leftPanel.add(lblLogo);

        JLabel lblBrand = new JLabel("StaffSync", SwingConstants.CENTER);
        lblBrand.setBounds(120, 200, 170, 40);
        lblBrand.setForeground(new Color(100, 149, 237));
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 36));
        leftPanel.add(lblBrand);

        JLabel lblSubtitle = new JLabel("Design. Create. Manage.", SwingConstants.CENTER);
        lblSubtitle.setBounds(120, 240, 170, 20);
        lblSubtitle.setForeground(Color.LIGHT_GRAY);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        leftPanel.add(lblSubtitle);

        JLabel lblTitle = new JLabel("Log in");
        lblTitle.setBounds(475, 30, 200, 30);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(lblTitle);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(475, 90, 200, 20);
        lblUsername.setForeground(Color.GRAY);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(475, 110, 250, 30);
        txtUsername.setBackground(new Color(30, 30, 30));
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        add(txtUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(475, 150, 200, 20);
        lblPassword.setForeground(Color.GRAY);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(475, 170, 250, 30);
        txtPassword.setBackground(new Color(30, 30, 30));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        add(txtPassword);

        JLabel lblRole = new JLabel("Role");
        lblRole.setBounds(475, 210, 200, 20);
        lblRole.setForeground(Color.GRAY);
        add(lblRole);

        String[] roles = {"Select Position", "HR Staff", "Manager", "Employee"};
        cbRole = new JComboBox<>(roles);
        cbRole.setBounds(475, 235, 250, 30);
        cbRole.setBackground(new Color(30, 30, 30));
        cbRole.setForeground(Color.WHITE);
        cbRole.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
        cbRole.setFocusable(false);
        cbRole.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
        add(cbRole);

        btnSubmit = new JButton("Login");
        btnSubmit.setBounds(475, 285, 250, 30);
        btnSubmit.setBackground(new Color(100, 149, 237));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder());
        btnSubmit.addActionListener(this);
        add(btnSubmit);

        btnClear = new JButton("Clear");
        btnClear.setBounds(475, 330, 250, 30);
        btnClear.setBackground(Color.LIGHT_GRAY);
        btnClear.setFocusPainted(false);
        btnClear.addActionListener(this);
        add(btnClear);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            handleLogin();
        } else if (e.getSource() == btnClear) {
            txtUsername.setText("");
            txtPassword.setText("");
            cbRole.setSelectedIndex(0);
        }
    }

    private void handleLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String selectedRole = (String) cbRole.getSelectedItem();

        if (selectedRole.equals("Select Position")) {
            JOptionPane.showMessageDialog(this, "Please select a role.");
            return;
        }

        if (username.equals("admin") && password.equals("123")) {

            switch (selectedRole) {
                case "HR Staff":
                    new HRFrame();
                    break;
                case "Manager":
                    new ManagerFrameReview();
                    break;
                case "Employee":
                    new EmployeeFrame();
                    break;
            }

            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
}