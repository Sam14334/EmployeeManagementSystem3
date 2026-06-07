package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;  

public class LoginFrame extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRole;
    private JButton btnSubmit, btnClear;
 
    private LinkedList<Long> failedAttempts = new LinkedList<>();
    private static final int MAX_ATTEMPTS = 3; 
    private static final long LOCKOUT_TIME_MS = 30000;   

    public LoginFrame() {
        setTitle("StaffSync - Login");
        setSize(800, 430);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(30, 30, 30));
 
        try {
            setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());
        } catch (Exception ex) {
            System.err.println("Warning: Login window taskbar icon failed to load. " + ex.getMessage());
        }
 
        JLabel leftPanel = new JLabel();
        try {
            leftPanel.setIcon(new ImageIcon("src\\main\\java\\images\\bgimage2blur.png"));
        } catch (Exception ex) {
            System.err.println("Warning: Background blur asset missing. Falling back to plain color.");
        }
        leftPanel.setBounds(0, 0, 400, 400);
        leftPanel.setBackground(new Color(44, 62, 80));
        leftPanel.setOpaque(true);
        leftPanel.setLayout(null);
        add(leftPanel);

        try {
            JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
            lblLogo.setBounds(136, 70, 128, 128);
            leftPanel.add(lblLogo);
        } catch (Exception ex) {
            System.err.println("Warning: Main logo asset missing inside visual brand container.");
        }

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
        txtUsername.setCaretColor(Color.WHITE);
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
        txtPassword.setCaretColor(Color.WHITE);
        txtPassword.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        add(txtPassword);

        JLabel lblRole = new JLabel("Role");
        lblRole.setBounds(475, 210, 200, 20);
        lblRole.setForeground(Color.GRAY);
        add(lblRole);

        String[] roles = {"Employee", "HR Staff", "Manager"};
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
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder());
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(this);
        add(btnSubmit);

        btnClear = new JButton("Clear");
        btnClear.setBounds(475, 330, 250, 30);
        btnClear.setBackground(Color.LIGHT_GRAY);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnClear.setFocusPainted(false);
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.addActionListener(this);
        add(btnClear);

        getRootPane().setDefaultButton(btnSubmit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnSubmit) {
                handleLogin();
            } else if (e.getSource() == btnClear) {
                txtUsername.setText("");
                txtPassword.setText("");
                cbRole.setSelectedIndex(0); 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An unexpected interactive runtime error occurred: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogin() {
        
        if (failedAttempts.size() >= MAX_ATTEMPTS) {
            long oldestAttempt = failedAttempts.getFirst(); 
            long timeSinceOldestFail = System.currentTimeMillis() - oldestAttempt;

            if (timeSinceOldestFail < LOCKOUT_TIME_MS) {
                long secondsLeft = (LOCKOUT_TIME_MS - timeSinceOldestFail) / 1000;
                JOptionPane.showMessageDialog(this, 
                    "Too many failed login attempts.\nPlease wait " + secondsLeft + " seconds before trying again.", 
                    "Security Lock", 
                    JOptionPane.WARNING_MESSAGE);
                return; 
            } else { 
                failedAttempts.clear();
            }
        } 

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String selectedRole = (String) cbRole.getSelectedItem();

        if (selectedRole == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fulfill all required username and password arguments.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "SELECT employee_id, first_name, last_name, role FROM employees WHERE username = ? AND password = ? AND role = ?";

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                throw new SQLException("Database connection endpoint returned a null handler sequence.");
            }

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, selectedRole);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                         
                        failedAttempts.clear();  

                        String loggedInUserId = rs.getString("employee_id");
                        String fullName = rs.getString("first_name") + " " + rs.getString("last_name");

                        try {
                            dispose(); 
                            
                            switch (selectedRole) {
                                case "HR Staff":
                                    new HRFrame(loggedInUserId, fullName);
                                    break;
                                case "Manager":
                                    new ManagerFrameRequests(loggedInUserId, fullName); 
                                    break;
                                case "Employee":
                                    new EmployeeFrame(loggedInUserId, fullName);
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(this, "The specified identity routing configuration rules are invalid.", "Routing Error", JOptionPane.ERROR_MESSAGE);
                                    setVisible(true); 
                                    break;
                            }
                        } catch (Exception targetEx) {
                            targetEx.printStackTrace();
                            JOptionPane.showMessageDialog(null, 
                                "Target Interface Failure: Could not load the workspace window.\n" +
                                "Verify that your implementation class exists and is error-free.\n\nDetails: " + targetEx.getMessage(), 
                                "Workspace Frame Crash", 
                                JOptionPane.ERROR_MESSAGE);
                            setVisible(true);
                        }
                    } else {
                         
                        failedAttempts.addLast(System.currentTimeMillis());
                         
                        if(failedAttempts.size() > MAX_ATTEMPTS) {
                            failedAttempts.removeFirst();
                        } 
                        
                        JOptionPane.showMessageDialog(this, "Invalid credentials or unauthorized system authority mapping requested.", "Login Denied", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(this, 
                "Communications link failure: Could not verify authorization profile.\n" +
                "Please verify that XAMPP / MySQL services are fully active.\n\nDetails: " + sqlException.getMessage(), 
                "Database Link Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An unhandled background transaction variance has occurred: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}