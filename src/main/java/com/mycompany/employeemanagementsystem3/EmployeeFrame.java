package com.mycompany.employeemanagementsystem3;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class EmployeeFrame extends JFrame implements ActionListener {
    
    private final Color ACCENT_BLUE = new Color(52, 152, 219);
    private final Color DANGER_RED = new Color(231, 76, 60);
    private final Color sidebarDarkGray = new Color(33, 47, 61); 
    
    private JLabel lblERS, lblRequestHeading, lblrequestType, lbldescription;
    
    // User Session Data
    private final String CURRENT_EMP_ID = "EMP-2026-001";
    private final String CURRENT_EMP_NAME = "Karlo Alatiit";
    private final String CURRENT_DEPARTMENT = "HR";
    private final String CURRENT_ROLE = "HR Manager";

    private JComboBox<String> cbrequest;
    private JTextArea txtDescription;
    private JButton btnSubmit, btnDelete, btnUpdate, btnSignout, btnViewDetails;
    private JTable table;
    private JScrollPane scroll, descScroll;
    private DefaultTableModel tableModel;
    private JPanel sideNav;
    private final Color SIDEBAR_BG = new Color(34, 45, 57);

    public EmployeeFrame(){
        
        setTitle("StaffSync - Employee Request Dashboard");
        setSize(1000, 1000);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());
       
        // --- SIDE NAVIGATION SIDEBAR ---
        sideNav = new JPanel();
        sideNav.setSize(260, 1000);
        sideNav.setBackground(SIDEBAR_BG);
        sideNav.setLayout(null);
        add(sideNav);
        
        ImageIcon rawIcon = new ImageIcon("src\\main\\java\\images\\karlo.png"); 
        Image scaledImg = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon finalAvatar = new ImageIcon(scaledImg);
          
        JLabel lblProfilePic = new JLabel(finalAvatar);
        lblProfilePic.setBounds(80, 30, 100, 100);
        lblProfilePic.setBorder(new LineBorder(new Color(255, 255, 255, 50), 2)); 
        sideNav.add(lblProfilePic);
        
        JLabel lblUser = new JLabel(CURRENT_ROLE + " | " + CURRENT_EMP_NAME, SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setBounds(30, 140, 200, 25);
        sideNav.add(lblUser);
        
        JLabel lblUserDept = new JLabel("Dept: " + CURRENT_DEPARTMENT, SwingConstants.CENTER);
        lblUserDept.setForeground(Color.GRAY);
        lblUserDept.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUserDept.setBounds(30, 165, 200, 20);
        sideNav.add(lblUserDept);
        
        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setBounds(66, 200, 128, 128); 
        sideNav.add(lblLogo);
        
        // --- TOP HEADINGS ---
        lblERS = new JLabel("Employee Request System");
        lblERS.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblERS.setBounds(350, 20, 400, 40);
        add(lblERS);
        
        lblRequestHeading = new JLabel("Create New Request");
        lblRequestHeading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblRequestHeading.setBounds(300, 80, 300, 30);
        add(lblRequestHeading);
        
        // Request Type
        lblrequestType = new JLabel("Request Type:");
        lblrequestType.setBounds(300, 140, 150, 30);
        lblrequestType.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblrequestType);
        
        String[] types = {"", "Leave", "Overtime", "Expenses"};
        cbrequest = new JComboBox<>(types);
        cbrequest.setBounds(430, 140, 200, 30);
        add(cbrequest);
        
        // ADJUSTED: Description Label ay inangat pa natin para hindi nito matakpan ang text field
        lbldescription = new JLabel("Description:");
        lbldescription.setBounds(300, 180, 150, 30);
        lbldescription.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lbldescription);
        
        txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDescription.setBackground(Color.WHITE);
        txtDescription.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ADJUSTED: Inakyat sa y=340 ang simula at pinalaki ang height sa 130!
        // Swak na swak ang taas nito pa-itaas nang hindi naaapektuhan ang table sa ibaba.
        descScroll = new JScrollPane(txtDescription);
        descScroll.setBounds(430, 190, 350, 300); 
        add(descScroll);
       
        // --- SIDEBAR ACTIONS / BUTTONS ---
        btnSubmit = new JButton("Submit Request");
        btnSubmit.setBounds(30, 350, 200, 50); 
        btnSubmit.setBackground(ACCENT_BLUE);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sideNav.add(btnSubmit);
    
        btnUpdate = new JButton("Update Request");
        btnUpdate.setBounds(30, 420, 200, 50); 
        btnUpdate.setBackground(ACCENT_BLUE);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sideNav.add(btnUpdate);
        
        btnViewDetails = new JButton("View Details");
        btnViewDetails.setBounds(30, 490, 200, 50);   
        btnViewDetails.setBackground(ACCENT_BLUE);
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sideNav.add(btnViewDetails);

        btnDelete = new JButton("Delete Request");
        btnDelete.setBounds(30, 560, 200, 50); 
        btnDelete.setBackground(DANGER_RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sideNav.add(btnDelete);
        
        btnSignout = new JButton("Sign out →");
        btnSignout.setBounds(35, 890, 180, 50);
        btnSignout.setBackground(Color.RED);
        btnSignout.setForeground(Color.WHITE);
        btnSignout.setFocusPainted(false);
        btnSignout.setBorderPainted(false);
        btnSignout.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSignout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sideNav.add(btnSignout);
        
        // --- MATCHED TABLE SYSTEM SETUP ---
        String[] cols = {
            "Employee ID", "Employee Name", "Department", "Role", "Request Type", "Description", "Status"
        };
        
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(45); 
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(52, 152, 219, 40)); 
        table.setSelectionForeground(Color.BLACK);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        table.setBackground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setBackground(sidebarDarkGray);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 13));

        // Nananatili sa y=500 para permanenteng may magandang agwat pababa
        scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(280, 500, 650, 440); 
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(new Color(245, 245, 245));
        add(scroll);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(110);
        }

        table.getColumnModel().getColumn(5).setPreferredWidth(180); 
        
        btnSubmit.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnSignout.addActionListener(this);
        btnViewDetails.addActionListener(this);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSubmit){
            handleSubmit();
        }
        else if(e.getSource() == btnUpdate){
            handleUpdate();
        }
        else if(e.getSource() == btnDelete){
            handleDelete();
        }
        else if(e.getSource() == btnSignout){
            dispose();
            new LoginFrame();
        }
        else if(e.getSource() == btnViewDetails){
            handleViewDetails();
        }
    }
    
    private void handleSubmit(){
        String type = cbrequest.getSelectedItem().toString();
        String desc = txtDescription.getText().trim();

        if(type.equals("") || desc.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        tableModel.addRow(new Object[]{
            CURRENT_EMP_ID,
            CURRENT_EMP_NAME,
            CURRENT_DEPARTMENT,
            CURRENT_ROLE,
            type,
            desc,
            "Pending"
        });

        clearFields();
    }

    private void handleUpdate(){
        int row = table.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this, "Select a row first.");
            return;
        }
      
        String type = tableModel.getValueAt(row, 4).toString();
        String desc = tableModel.getValueAt(row, 5).toString();
      
        JTextField empIDField = new JTextField(CURRENT_EMP_ID);
        empIDField.setEditable(false); 
        
        JTextField empNameField = new JTextField(CURRENT_EMP_NAME);
        empNameField.setEditable(false); 

        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Leave", "Overtime", "Expenses"});
        typeBox.setSelectedItem(type);

        JTextArea descArea = new JTextArea(desc);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);

        JScrollPane descScrollEdit = new JScrollPane(descArea);
        descScrollEdit.setPreferredSize(new Dimension(200, 80));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(400, 260));

        JLabel lblID = new JLabel("Employee ID:");
        lblID.setBounds(20, 20, 120, 25);
        panel.add(lblID);

        empIDField.setBounds(150, 20, 200, 25);
        panel.add(empIDField);

        JLabel lblName = new JLabel("Employee Name:");
        lblName.setBounds(20, 60, 120, 25);
        panel.add(lblName);

        empNameField.setBounds(150, 60, 200, 25);
        panel.add(empNameField);

        JLabel lblType = new JLabel("Request Type:");
        lblType.setBounds(20, 100, 120, 25);
        panel.add(lblType);

        typeBox.setBounds(150, 100, 200, 25);
        panel.add(typeBox);

        JLabel lblDesc = new JLabel("Description:");
        lblDesc.setBounds(20, 140, 120, 25);
        panel.add(lblDesc);

        descScrollEdit.setBounds(150, 140, 200, 80);
        panel.add(descScrollEdit);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Edit Employee Request", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if(result == JOptionPane.OK_OPTION){
            String newDesc = descArea.getText().trim();
          
            if(newDesc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please complete the description before saving.");
                return;
            }
         
            tableModel.setValueAt(typeBox.getSelectedItem().toString(), row, 4);
            tableModel.setValueAt(newDesc, row, 5);

            JOptionPane.showMessageDialog(this, "Updated successfully!");
        }
    }
        
    private void handleDelete(){
        int row = table.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this, "Select a row first.");
            return;
        }

        tableModel.removeRow(row);
    }

    private void handleViewDetails(){
        int row = table.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this, "Select a request first.");
            return;
        }

        String empID = tableModel.getValueAt(row, 0).toString();
        String empName = tableModel.getValueAt(row, 1).toString();
        String department = tableModel.getValueAt(row, 2).toString();
        String role = tableModel.getValueAt(row, 3).toString(); 
        String type = tableModel.getValueAt(row, 4).toString();
        String desc = tableModel.getValueAt(row, 5).toString();
        String status = tableModel.getValueAt(row, 6).toString();

        String comment = "No comment yet";

        String message =
                "EMPLOYEE DETAILS\n\n" +
                "ID: " + empID + "\n" +
                "Name: " + empName + "\n" +
                "Department: " + department + "\n" +
                "Role: " + role + "\n\n" + 

                "REQUEST DETAILS\n" +
                "Type: " + type + "\n" +
                "Description: " + desc + "\n" +
                "Status: " + status + "\n\n" +

                "MANAGER COMMENT\n" +
                comment;

        JOptionPane.showMessageDialog(this, message, "Request Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields(){
        cbrequest.setSelectedIndex(0);
        txtDescription.setText("");
    }
}