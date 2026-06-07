package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class ProcessRequestFrame extends JDialog implements ActionListener {

    private JTextArea txtNotes;
    private JButton btnConfirm, btnCancel;
    private String requestId, actionType;
    private ManagerFrameRequests parentFrame; // Secure, explicitly declared component mapping link

    public ProcessRequestFrame(ManagerFrameRequests parent, String reqId, String empName, String reqType, String targetAction) {
        super(parent, "Review Action Processing Frame", true); // True establishes strict application focus modality
        this.parentFrame = parent;
        this.requestId = reqId;
        this.actionType = targetAction;

        setSize(500, 420);
        setLayout(null);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(new Color(30, 30, 30));

        // Select accent dynamically depending on target action context route
        Color accentColor = actionType.equals("Approved") ? new Color(40, 167, 69) : new Color(231, 76, 60);

        JLabel lblTitle = new JLabel("Execute Authorization: " + actionType);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(accentColor);
        lblTitle.setBounds(30, 20, 440, 30);
        add(lblTitle);

        JLabel lblEmp = new JLabel("Employee Name: " + empName);
        lblEmp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmp.setForeground(Color.LIGHT_GRAY);
        lblEmp.setBounds(30, 60, 440, 20);
        add(lblEmp);

        JLabel lblType = new JLabel("Request Category Type: " + reqType);
        lblType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblType.setForeground(Color.LIGHT_GRAY);
        lblType.setBounds(30, 85, 440, 20);
        add(lblType);

        JLabel lblNotesHeader = new JLabel(actionType.equals("Approved") ? "Provide Approval Justification Note:" : "Provide Operational Reason for Denial:");
        lblNotesHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNotesHeader.setForeground(Color.GRAY);
        lblNotesHeader.setBounds(30, 125, 440, 20);
        add(lblNotesHeader);

        // --- ENHANCED PARAGRAPH SCROLL TEXT AREA WORKSPACE ---
        txtNotes = new JTextArea();
        txtNotes.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtNotes.setBackground(new Color(40, 40, 40));
        txtNotes.setForeground(Color.WHITE);
        txtNotes.setCaretColor(Color.WHITE);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        txtNotes.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(txtNotes);
        scrollPane.setBounds(30, 150, 425, 140);
        scrollPane.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
        add(scrollPane);

        // Action Trigger Configuration
        btnConfirm = new JButton("Confirm " + actionType);
        btnConfirm.setBounds(30, 315, 200, 40);
        btnConfirm.setBackground(accentColor);
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setBorderPainted(false);
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirm.addActionListener(this);
        add(btnConfirm);

        btnCancel = new JButton("Dismiss");
        btnCancel.setBounds(255, 315, 200, 40);
        btnCancel.setBackground(Color.LIGHT_GRAY);
        btnCancel.setForeground(Color.BLACK);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(this);
        add(btnCancel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancel) {
            dispose();
        } else if (e.getSource() == btnConfirm) {
            executeDatabaseUpdate();
        }
    }

    private void executeDatabaseUpdate() {
        String noteContent = txtNotes.getText().trim();

        if (noteContent.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide validation summary remarks inside the textbox workspace before submitting.", "Remarks Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE employee_requests SET status = ?, notes = ? WHERE request_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                throw new SQLException("Pipeline missing. Connection references returned null.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, actionType);
                pstmt.setString(2, noteContent);
                pstmt.setString(3, requestId);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Transaction status successfully designated to " + actionType + ".", "Sync Complete", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Verifies instance linking context stability before execution
                    if (parentFrame != null) {
                        parentFrame.loadRequestsFromDB(); 
                    }
                    
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Record tracking identification index mismatch context variance occurred.", "Execution Failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Relational mapping validation transmission dropped: " + ex.getMessage(), "Database Pipeline Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}