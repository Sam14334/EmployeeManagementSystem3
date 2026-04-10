package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeFrame extends JFrame implements ActionListener {
    
    private JLabel lblERS, lblEmpID, lblfirstName, lbllastName, lblrequestType, lbldescription;
    private JTextField txtEmpID, txtfirstName, txtlastName;
    private JComboBox<String> cbrequest;
    private JTextArea txtDescription;
    private JButton btnSubmit, btnDelete, btnUpdate;
    private JTable table;
    private JScrollPane scroll, descScroll;
    private DefaultTableModel tableModel;

    public EmployeeFrame(){
        
        setTitle("FrameEmployee");
        setSize(1000,1000);
        setLayout(null);
        setLocationRelativeTo(null);
        
        
        lblERS = new JLabel("Employee Request System");
        lblERS.setBounds(430, 20, 300, 30);
        add(lblERS);
        
        
        lblEmpID = new JLabel("Employee ID:");
        lblEmpID.setBounds(50, 60, 100, 25);
        add(lblEmpID);

        txtEmpID = new JTextField();
        txtEmpID.setBounds(180, 60, 150, 25);
        add(txtEmpID);

        lblfirstName = new JLabel("First Name:");
        lblfirstName.setBounds(50, 100, 100, 25);
        add(lblfirstName);

        txtfirstName = new JTextField();
        txtfirstName.setBounds(180, 100, 150, 25);
        add(txtfirstName);

        lbllastName = new JLabel("Last Name:");
        lbllastName.setBounds(50, 140, 100, 25);
        add(lbllastName);

        txtlastName = new JTextField();
        txtlastName.setBounds(180, 140, 150, 25);
        add(txtlastName);
        
        lblrequestType = new JLabel("Request Type:");
        lblrequestType.setBounds(50, 180, 100, 30);
        add(lblrequestType);
        
        String[] types = {"", "Leave", "Overtime", "Expenses"};
        cbrequest = new JComboBox<>(types);
        cbrequest.setBounds(180, 180, 150, 30);
        add(cbrequest);
        
        lbldescription = new JLabel("Description:");
        lbldescription.setBounds(50, 220, 100, 30);
        add(lbldescription);
        
        txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);

        descScroll = new JScrollPane(txtDescription);
        descScroll.setBounds(180, 220, 480, 200);
        add(descScroll);
       
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(180, 430, 120, 30);
        add(btnSubmit);
    
        btnUpdate = new JButton("Update Selected");
        btnUpdate.setBounds(330, 430, 150, 30);
        add(btnUpdate);
        
        btnDelete = new JButton("Delete Selected");
        btnDelete.setBounds(510, 430, 150, 30);
        add(btnDelete);
        
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
            "Employee ID", "First Name", "Last Name", "Type", "Description", "Status"
        });

        table = new JTable(tableModel);
        scroll = new JScrollPane(table);
        scroll.setBounds(50,470,880,250);
        add(scroll);
        
        
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