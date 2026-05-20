package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ManagerFrameRequests extends JFrame implements ActionListener {

    private JPanel sideBar, mainContent;
    private JTable requestTable;
    private JButton btnApprove, btnDeny, btnBack;

    public ManagerFrameRequests() {
        setTitle("StaffSync - Manager - Process Requests");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

    
        sideBar = new JPanel();
        sideBar.setBackground(new Color(33, 47, 61));
        sideBar.setBounds(0, 0, 250, 1000);
        sideBar.setLayout(null);
        add(sideBar);

      
        ImageIcon rawIcon = new ImageIcon("src\\main\\java\\images\\karlo.png");
        Image scaledImg = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon finalAvatar = new ImageIcon(scaledImg);

        JLabel lblProfilePic = new JLabel(finalAvatar);
        lblProfilePic.setBounds(80, 30, 100, 100);
        lblProfilePic.setBorder(new LineBorder(new Color(255, 255, 255, 50), 2));
        sideBar.add(lblProfilePic);

      
        JLabel lblUser = new JLabel("Manager | Karlo", SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setBounds(30, 140, 200, 25);
        sideBar.add(lblUser);

     
        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setForeground(Color.GRAY);
        lblLogo.setBounds(66, 185, 128, 128);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        sideBar.add(lblLogo);

        
        btnApprove = new JButton("Approve Request");
        btnApprove.setBounds(35, 340, 180, 45);
        btnApprove.setBackground(new Color(40, 167, 69));
        btnApprove.setForeground(Color.WHITE);
        btnApprove.setFocusPainted(false);
        btnApprove.setBorderPainted(false);
        btnApprove.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnApprove.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnApprove.addActionListener(this);
        sideBar.add(btnApprove);

        btnDeny = new JButton("Deny Request");
        btnDeny.setBounds(35, 400, 180, 45);
        btnDeny.setBackground(new Color(231, 76, 60));
        btnDeny.setForeground(Color.WHITE);
        btnDeny.setFocusPainted(false);
        btnDeny.setBorderPainted(false);
        btnDeny.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnDeny.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDeny.addActionListener(this);
        sideBar.add(btnDeny);

        btnBack = new JButton("Back →");
        btnBack.setBounds(35, 460, 180, 45);
        btnBack.setBackground(Color.GRAY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(this);
        sideBar.add(btnBack);

        
        mainContent = new JPanel();
        mainContent.setBackground(new Color(245, 245, 245));
        mainContent.setLayout(null);
        mainContent.setBounds(250, 0, 750, 1000);
        add(mainContent);

        JLabel lblTitle = new JLabel("Employee Requests");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitle.setForeground(new Color(33, 47, 61));
        lblTitle.setBounds(30, 30, 300, 40);
        mainContent.add(lblTitle);

        
        String[] columns = {"ID", "Employee Name", "Request Type", "Date Submitted", "Status", "Notes"};
        String[][] data = {
            {"REQ-001", "Jomar N. Pangilinan", "Leave", "2026-04-01", "Pending", "-"},
            {"REQ-002", "Micheal P. Samia", "Overtime", "2026-04-02", "Approved", "Approved by HR"},
            {"REQ-003", "Karlo H. Alatiit", "Leave", "2026-04-03", "Pending", "-"},
            {"REQ-004", "Ezekiel Parao", "Transfer", "2026-04-04", "Pending", "-"},
            {"REQ-005", "Rich Jasper C. Federio", "Resignation", "2026-04-05", "Denied", "Incomplete papers"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        requestTable = new JTable(model);
        requestTable.setRowHeight(45);
        requestTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        
        requestTable.getTableHeader().setBackground(new Color(33, 47, 61));
        requestTable.getTableHeader().setForeground(Color.WHITE);
        requestTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        requestTable.setSelectionBackground(new Color(52, 152, 219, 40));
        requestTable.setShowVerticalLines(false);
        requestTable.setGridColor(new Color(230, 230, 230));

    
        requestTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        requestTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        requestTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        requestTable.getColumnModel().getColumn(3).setPreferredWidth(110);
        requestTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        requestTable.getColumnModel().getColumn(5).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.setBounds(30, 100, 690, 750);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        mainContent.add(scrollPane);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        if (e.getSource() == btnApprove) {
            int row = requestTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a request to approve.");
                return;
            }
            
            String status = requestTable.getValueAt(row, 4).toString();
            if (status.equals("Approved") || status.equals("Denied")) {
                JOptionPane.showMessageDialog(this, "Request already processed.");
                return;
            }
            
          
            String note = JOptionPane.showInputDialog(this, "Enter approval note:", "Approved by Manager");
            
            if (note != null && !note.trim().isEmpty()) {
                requestTable.setValueAt("Approved", row, 4);
                requestTable.setValueAt(note, row, 5);
                JOptionPane.showMessageDialog(this, "Request approved successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Note is required.");
            }
        }
        
       
        if (e.getSource() == btnDeny) {
            int row = requestTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a request to deny.");
                return;
            }
            
            String status = requestTable.getValueAt(row, 4).toString();
            if (status.equals("Approved") || status.equals("Denied")) {
                JOptionPane.showMessageDialog(this, "Request already processed.");
                return;
            }
            
       
            String reason = JOptionPane.showInputDialog(this, "Enter denial reason:", "Denied");
            
            if (reason != null && !reason.trim().isEmpty()) {
                requestTable.setValueAt("Denied", row, 4);
                requestTable.setValueAt(reason, row, 5);
                JOptionPane.showMessageDialog(this, "Request denied.");
            } else {
                JOptionPane.showMessageDialog(this, "Reason is required.");
            }
        }
        
       
        if (e.getSource() == btnBack) {
            dispose();
            new ManagerFrameReview();
        }
    }
}
