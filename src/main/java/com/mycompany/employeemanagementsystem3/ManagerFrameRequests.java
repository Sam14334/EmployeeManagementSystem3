package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;


public class ManagerFrameRequests extends JFrame implements ActionListener {

    JLabel lblFrame, lblReason;
    JTable table;
    JButton btnAccept, btnDeny, btnSignOut;
    JTextArea txtReason;


    public ManagerFrameRequests() {
       // Force consistent UI
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("StaffSync - Manager - Process Requests");
        setSize(1000, 1000); // ✅ BACK TO ORIGINAL
        setLayout(null);
        setLocationRelativeTo(null);

        // Background
        getContentPane().setBackground(new Color(248, 250, 252));

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setBounds(0, 0, 180, 1000); // ✅ FULL HEIGHT
        sidebar.setBackground(new Color(30, 41, 59));
        sidebar.setLayout(null);
        add(sidebar);

        JLabel lblMenu = new JLabel("MENU");
        lblMenu.setBounds(50, 30, 100, 30);
        lblMenu.setForeground(Color.WHITE);
        lblMenu.setFont(new Font("Arial", Font.BOLD, 16));
        sidebar.add(lblMenu);

        btnSignOut = new JButton("Sign Out");
        btnSignOut.setBounds(20, 900, 140, 40); // ✅ MOVED DOWN
        btnSignOut.setBackground(new Color(239, 68, 68));
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.setFocusPainted(false);
        btnSignOut.setBorderPainted(false);
        btnSignOut.setOpaque(true);
        btnSignOut.setContentAreaFilled(true);
        btnSignOut.addActionListener(this);
        sidebar.add(btnSignOut);

        // ===== TITLE =====
        lblFrame = new JLabel("Employee Requests", JLabel.CENTER);
        lblFrame.setBounds(350, 20, 300, 30);
        lblFrame.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblFrame);

        // ===== TABLE =====
        String[] columnNames = {"ID", "Employee Name", "Request Type", "Date", "Status"};

        Object[][] data = {
            {"1", "Juan Dela Cruz", "Leave", "2026-04-01", "Pending"},
            {"2", "Maria Santos", "Overtime", "2026-04-02", "Approved"},
            {"3", "Pedro Reyes", "Leave", "2026-04-03", "Pending"}
        };

        table = new JTable(new DefaultTableModel(data, columnNames){
            public boolean isCellEditable(int r, int c){ return false; }
        });

        table.setRowHeight(25);
        table.setBackground(Color.WHITE);

        // Header styling
        table.getTableHeader().setBackground(new Color(30, 41, 59));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setOpaque(true);

        // Center text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(200, 80, 750, 300);
        add(scrollPane);

        // ===== REASON =====
        lblReason = new JLabel("Reason:");
        lblReason.setBounds(200, 400, 100, 25);
        add(lblReason);

        txtReason = new JTextArea();
        txtReason.setLineWrap(true);
        txtReason.setWrapStyleWord(true);

        JScrollPane reasonScroll = new JScrollPane(txtReason);
        reasonScroll.setBounds(200, 430, 750, 120);
        add(reasonScroll);

        // ===== BUTTONS =====
        btnAccept = new JButton("Accept");
        btnAccept.setBounds(300, 600, 150, 40); // ✅ LOWERED
        btnAccept.setBackground(new Color(34, 197, 94));
        btnAccept.setForeground(Color.WHITE);
        btnAccept.setFocusPainted(false);
        btnAccept.setBorderPainted(false);
        btnAccept.setOpaque(true);
        btnAccept.setContentAreaFilled(true);
        btnAccept.addActionListener(this);
        add(btnAccept);

        btnDeny = new JButton("Deny");
        btnDeny.setBounds(550, 600, 150, 40); // ✅ LOWERED
        btnDeny.setBackground(new Color(239, 68, 68));
        btnDeny.setForeground(Color.WHITE);
        btnDeny.setFocusPainted(false);
        btnDeny.setBorderPainted(false);
        btnDeny.setOpaque(true);
        btnDeny.setContentAreaFilled(true);
        btnDeny.addActionListener(this);
        add(btnDeny);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSignOut) {
            JOptionPane.showMessageDialog(this, "Signed out!");
            dispose();
            return;
        }

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request.");
            return;
        }

        if (txtReason.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a reason.");
            return;
        }

        String status = table.getValueAt(row, 4).toString();

        if (!status.equals("Pending")) {
            JOptionPane.showMessageDialog(this, "Request already processed.");
            return;
        }

        if (e.getSource() == btnAccept) {
            table.setValueAt("Approved", row, 4);
            JOptionPane.showMessageDialog(this, "Approved!");
        }

        if (e.getSource() == btnDeny) {
            table.setValueAt("Denied", row, 4);
            JOptionPane.showMessageDialog(this, "Denied!");
        }

        txtReason.setText("");
    }
}

