package com.mycompany.employeemanagementsystem3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class EmployeeFrame extends JFrame implements ActionListener {
    
    private final Color ACCENT_BLUE = new Color(52, 152, 219);
    
    private JLabel lblERS, lblEmpID, lblfirstName, lbllastName, lblrequestType, lbldescription;
    private JTextField txtEmpID, txtfirstName, txtlastName;
    private JComboBox<String> cbrequest;
    private JTextArea txtDescription;
    private JButton btnSubmit, btnDelete, btnUpdate, btnSignout;
    private JTable table;
    private JScrollPane scroll, descScroll;
    private DefaultTableModel tableModel;
    private JPanel sideNav;
    private final Color SIDEBAR_BG = new Color(34, 45, 57);

    public EmployeeFrame(){
        
        setTitle("FrameEmployee");
        setSize(1000,1000);
        setLayout(null);
        setLocationRelativeTo(null);
        
       
        sideNav = new JPanel();
        sideNav.setSize(260,1000);
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
        
        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setBounds(66, 185, 128, 128); 
        sideNav.add(lblLogo);
        
        lblERS = new JLabel("Employee Request System");
        lblERS.setBounds(450, 20, 300, 30);
        lblERS.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(lblERS);
        
        lblrequestType = new JLabel("Request Type:");
        lblrequestType.setBounds(300, 90, 150, 30);
        lblrequestType.setFont(new Font("Segoe UI", Font.BOLD, 15));
        add(lblrequestType);
        
        String[] types = {"", "Leave", "Overtime", "Expenses"};
        cbrequest = new JComboBox<>(types);
        cbrequest.setBounds(430, 90, 150, 30);
        add(cbrequest);
        
        lbldescription = new JLabel("Description:");
        lbldescription.setBounds(300, 150, 150, 30);
        lbldescription.setFont(new Font("Segoe UI", Font.BOLD, 15));
        add(lbldescription);
        
        txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);

        descScroll = new JScrollPane(txtDescription);
        descScroll.setBounds(430, 150, 480, 200);
        add(descScroll);
       
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(430, 380, 150, 50);
        btnSubmit.setBackground(ACCENT_BLUE);
        btnSubmit.setForeground(Color.WHITE);
        add(btnSubmit);
    
        btnUpdate = new JButton("Update Selected");
        btnUpdate.setBounds(595, 380, 150, 50);
        btnUpdate.setBackground(ACCENT_BLUE);
        btnUpdate.setForeground(Color.WHITE);
        add(btnUpdate);
        
        btnDelete = new JButton("Delete Selected");
        btnDelete.setBounds(760, 380, 150, 50);
        btnDelete.setBackground(ACCENT_BLUE);
        btnDelete.setForeground(Color.WHITE);
        add(btnDelete);
        
        btnSignout = new JButton("Sign out →");
        btnSignout.setBounds(50,900,150,50);
        btnSignout.setBackground(new Color(41, 128, 185));
        btnSignout.setForeground(Color.WHITE);
        sideNav.add(btnSignout);
        
        
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
            "Employee ID", "First Name", "Last Name", "Type", "Description", "Status"
        });

//        table = new JTable(tableModel);
//        scroll = new JScrollPane(table);
//        scroll.setBounds(50,470,880,250);
//        add(scroll);
        
        
        btnSubmit.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        
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
    }

    private void handleSubmit(){
        String empID = txtEmpID.getText().trim(); 
        String firstName = txtfirstName.getText().trim();
        String lastName = txtlastName.getText().trim();
        String type = cbrequest.getSelectedItem().toString();
        String desc = txtDescription.getText().trim();

        if(empID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || type.equals("") || desc.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        tableModel.addRow(new Object[]{empID, firstName, lastName, type, desc, "Pending"});

        clearFields();
    }

    private void handleUpdate(){
        int row = table.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this, "Select a row first.");
            return;
        }

        String empID = txtEmpID.getText().trim();
        String firstName = txtfirstName.getText().trim();
        String lastName = txtlastName.getText().trim();
        String type = cbrequest.getSelectedItem().toString();
        String desc = txtDescription.getText().trim();

        if(empID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || type.equals("") || desc.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        tableModel.setValueAt(empID, row, 0);
        tableModel.setValueAt(firstName, row, 1);
        tableModel.setValueAt(lastName, row, 2);
        tableModel.setValueAt(type, row, 3);
        tableModel.setValueAt(desc, row, 4);
        tableModel.setValueAt("Pending", row, 5);

        clearFields();
    }

    private void handleDelete(){
        int row = table.getSelectedRow();
//test
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Select a row first.");
            return;
        }

        tableModel.removeRow(row);
    }

    private void clearFields(){
        txtEmpID.setText("");
        txtfirstName.setText("");
        txtlastName.setText("");
        cbrequest.setSelectedIndex(0);
        txtDescription.setText("");
    }
}