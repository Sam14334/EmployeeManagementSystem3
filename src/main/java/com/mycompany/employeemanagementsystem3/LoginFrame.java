package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class LoginFrame extends JFrame implements ActionListener {
    //test repo push change

    // ================= COLOR VARIABLES =================
    Color BG_MAIN = new Color(30, 30, 30);          //design main background
    Color BG_PANEL = new Color(230, 230, 230);      //design left panel
    Color TEXT_PRIMARY = Color.WHITE;               //design main text
    Color TEXT_SECONDARY = Color.GRAY;              //design labels
    Color TEXT_LIGHT = Color.LIGHT_GRAY;            //design subtitle
    Color ACCENT = new Color(100, 149, 237);        //design accent (buttons, brand)
    Color INPUT_BG = new Color(30, 30, 30);         //design input background
    Color BORDER = Color.GRAY;                      //design underline
    Color BUTTON_TEXT = Color.WHITE;                //design button text
    Color CLEAR_BTN = Color.LIGHT_GRAY;             //design clear button

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
        getContentPane().setBackground(BG_MAIN); //design
        setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());

        // ================= LEFT PANEL =================
        JLabel leftPanel = new JLabel(new ImageIcon("src\\main\\java\\images\\bgimage2blur.png"));
        leftPanel.setBounds(0, 0, 400, 400);
        leftPanel.setBackground(BG_PANEL); //design
        leftPanel.setLayout(null);
        add(leftPanel);

        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setBounds(136, 70, 128, 128);
//        lblLogo.setBorder(new MatteBorder(1, 1, 1, 1, BORDER));
        leftPanel.add(lblLogo);
        
        JLabel lblBrand = new JLabel("StaffSync", SwingConstants.CENTER);
        lblBrand.setBounds(120, 200, 170, 40);
//        lblBrand.setBorder(new MatteBorder(1, 1, 1, 1, BORDER));
        lblBrand.setForeground(ACCENT); //design
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 36));
        leftPanel.add(lblBrand);

        JLabel lblSubtitle = new JLabel("Design. Create. Manage.", SwingConstants.CENTER);
        lblSubtitle.setBounds(120, 240, 170, 20);
//        lblSubtitle.setBorder(new MatteBorder(1, 1, 1, 1, BORDER));
        lblSubtitle.setForeground(TEXT_LIGHT); //design
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        leftPanel.add(lblSubtitle);

        // ================= RIGHT SIDE ================= 
        
        JLabel lblTitle = new JLabel("Log in");
        lblTitle.setBounds(475, 30, 200, 30);
        lblTitle.setForeground(TEXT_PRIMARY); //design
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(lblTitle);

        // Username
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(475, 90, 200, 20);
        lblUsername.setForeground(TEXT_SECONDARY); //design
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(475, 110, 250, 30);
        txtUsername.setBackground(INPUT_BG); //design
        txtUsername.setForeground(TEXT_PRIMARY); //design
        txtUsername.setBorder(new MatteBorder(0, 0, 1, 0, BORDER)); //design
        add(txtUsername);

        // Password
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(475, 150, 200, 20);
        lblPassword.setForeground(TEXT_SECONDARY); //design
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(475, 170, 250, 30);
        txtPassword.setBackground(INPUT_BG); //design
        txtPassword.setForeground(TEXT_PRIMARY); //design
        txtPassword.setBorder(new MatteBorder(0, 0, 1, 0, BORDER)); //design
        add(txtPassword);

        // Role
        JLabel lblRole = new JLabel("Role");
        lblRole.setBounds(475, 210, 200, 20);
        lblRole.setForeground(TEXT_SECONDARY); //design
        add(lblRole);

        String[] roles = {"Select Position", "HR Staff", "Manager", "Employee"};
        cbRole = new JComboBox<>(roles);
        cbRole.setBounds(475, 235, 250, 30);
        cbRole.setBackground(INPUT_BG);
        cbRole.setForeground(TEXT_PRIMARY);
        cbRole.setBorder(new MatteBorder(1, 1, 1, 1, BORDER));
        cbRole.setFocusable(false);
        cbRole.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
        add(cbRole);

        // Login Button
        btnSubmit = new JButton("Login");
        btnSubmit.setBounds(475, 285, 250, 30);
        btnSubmit.setBackground(ACCENT); //design
        btnSubmit.setForeground(BUTTON_TEXT); //design
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder());
        btnSubmit.addActionListener(this);
        add(btnSubmit);

        // Clear Button
        btnClear = new JButton("Clear");
        btnClear.setBounds(475, 330, 250, 30);
        btnClear.setBackground(CLEAR_BTN); //design
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

            JOptionPane.showMessageDialog(this, "Login successful as " + selectedRole);

            switch (selectedRole) {
                case "HR Staff":
                    new HRFrame();
                    break;
                case "Manager":
                    new ManagerFrame();
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
