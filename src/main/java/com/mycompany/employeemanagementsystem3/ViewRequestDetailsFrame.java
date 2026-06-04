package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class ViewRequestDetailsFrame extends JDialog {

    public ViewRequestDetailsFrame(JFrame parent, String reqId, String empName, String reqType, String desc, String status, String notes) {
        super(parent, "Transaction Summary Ledger Log Details", true);
        
        setSize(520, 540);
        setLayout(null);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(new Color(30, 30, 30));

        // Color coordinate based on historical execution status entries
        Color statusColor = Color.LIGHT_GRAY;
        if (status.equalsIgnoreCase("Approved")) statusColor = new Color(40, 167, 69);
        else if (status.equalsIgnoreCase("Denied")) statusColor = new Color(231, 76, 60);
        else if (status.equalsIgnoreCase("Pending")) statusColor = new Color(241, 196, 15);

        JLabel lblTitle = new JLabel("Request Tracking Log Details (ID: " + reqId + ")");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(52, 152, 219));
        lblTitle.setBounds(30, 20, 460, 30);
        add(lblTitle);

        JLabel lblEmp = new JLabel("Employee Name: " + empName);
        lblEmp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmp.setForeground(Color.LIGHT_GRAY);
        lblEmp.setBounds(30, 65, 460, 20);
        add(lblEmp);

        JLabel lblType = new JLabel("Request Category Type: " + reqType);
        lblType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblType.setForeground(Color.LIGHT_GRAY);
        lblType.setBounds(30, 90, 460, 20);
        add(lblType);

        JLabel lblStatus = new JLabel("Current Authorization Status: ");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblStatus.setForeground(Color.LIGHT_GRAY);
        lblStatus.setBounds(30, 115, 200, 20);
        add(lblStatus);

        JLabel lblStatusVal = new JLabel(status.toUpperCase());
        lblStatusVal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStatusVal.setForeground(statusColor);
        lblStatusVal.setBounds(225, 115, 200, 20);
        add(lblStatusVal);

        // --- DESCRIPTION TEXT AREA SCROLL CONTAINER ---
        JLabel lblDescTitle = new JLabel("Employee Description Statement:");
        lblDescTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDescTitle.setForeground(Color.GRAY);
        lblDescTitle.setBounds(30, 155, 460, 20);
        add(lblDescTitle);

        JTextArea txtDesc = new JTextArea(desc);
        txtDesc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtDesc.setBackground(new Color(45, 45, 45));
        txtDesc.setForeground(Color.WHITE);
        txtDesc.setEditable(false);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        JScrollPane scrollDesc = new JScrollPane(txtDesc);
        scrollDesc.setBounds(30, 180, 445, 110);
        scrollDesc.setBorder(new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        add(scrollDesc);

        // --- MANAGEMENT REMARKS/NOTES TEXT AREA SCROLL CONTAINER ---
        JLabel lblNotesTitle = new JLabel("Management Resolution Evaluation Notes:");
        lblNotesTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNotesTitle.setForeground(Color.GRAY);
        lblNotesTitle.setBounds(30, 310, 460, 20);
        add(lblNotesTitle);

        JTextArea txtNotes = new JTextArea(notes);
        txtNotes.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtNotes.setBackground(new Color(45, 45, 45));
        txtNotes.setForeground(Color.WHITE);
        txtNotes.setEditable(false);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        txtNotes.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        JScrollPane scrollNotes = new JScrollPane(txtNotes);
        scrollNotes.setBounds(30, 335, 445, 110);
        scrollNotes.setBorder(new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        add(scrollNotes);

       
        setVisible(true);
    }
}