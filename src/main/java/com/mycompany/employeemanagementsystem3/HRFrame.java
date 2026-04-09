/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.employeemanagementsystem3;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HRFrame extends JFrame implements ActionListener {
    
    private DefaultTableModel model;
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete;
    
    public HRFrame() {
        setTitle("StaffSync - HR");
        setSize(1000, 1000);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel sideNav = new JPanel();
        sideNav.setBounds(0, 0, 250, 1000);
        sideNav.setBackground(new Color(34, 47, 62)); 
        sideNav.setLayout(null);

        btnAdd = createBtn("Add Employee", 100, new Color(52, 152, 219));
        btnEdit = createBtn("Edit Employee", 160, new Color(52, 152, 219));
        btnDelete = createBtn("Delete Record", 220, new Color(231, 76, 60));

        
        btnAdd.addActionListener(this);
        btnEdit.addActionListener(this);
        btnDelete.addActionListener(this);

        sideNav.add(btnAdd);
        sideNav.add(btnEdit);
        sideNav.add(btnDelete);

        
        JPanel content = new JPanel();
        content.setBounds(250, 0, 750, 700);
        content.setLayout(null);
        content.setBackground(new Color(245, 246, 250));

        
        String[] cols = {"ID", "First Name", "Last Name", "Salary"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(30, 100, 680, 500);
        content.add(pane);

        
        model.addRow(new Object[]{"001", "John", "Doe", "50000"});

        add(sideNav);
        add(content);
        
        setVisible(true);
    }

     @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            handleForm("ADD", -1);
        } 
        else if (e.getSource() == btnEdit) {
            int row = table.getSelectedRow();
            if (row != -1) handleForm("EDIT", row);
            else JOptionPane.showMessageDialog(this, "Select a row to edit!");
        } 
        else if (e.getSource() == btnDelete) {
            int row = table.getSelectedRow();
            if (row != -1) model.removeRow(row);
            else JOptionPane.showMessageDialog(this, "Select a row to delete!");
        }
    }

    private void handleForm(String type, int row) {
        JTextField id = new JTextField(type.equals("EDIT") ? model.getValueAt(row, 0).toString() : "");
        JTextField fn = new JTextField(type.equals("EDIT") ? model.getValueAt(row, 1).toString() : "");
        JTextField ln = new JTextField(type.equals("EDIT") ? model.getValueAt(row, 2).toString() : "");
        JTextField sl = new JTextField(type.equals("EDIT") ? model.getValueAt(row, 3).toString() : "");

        Object[] inputFields = {"ID:", id, "First Name:", fn, "Last Name:", ln, "Salary:", sl};
        int result = JOptionPane.showConfirmDialog(null, inputFields, type + " Employee", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Object[] data = {id.getText(), fn.getText(), ln.getText(), sl.getText()};
            if (type.equals("EDIT")) {
                for(int i=0; i<4; i++) model.setValueAt(data[i], row, i);
            } else {
                model.addRow(data);
            }
        }
    }

    
    private JButton createBtn(String text, int y, Color color) {
        JButton b = new JButton(text);
        b.setBounds(25, y, 200, 40);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false); 
        return b;
        
    } 
     
}
