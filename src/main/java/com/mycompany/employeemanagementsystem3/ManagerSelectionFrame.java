package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

public class ManagerSelectionFrame extends JFrame implements ActionListener {


    private final Color BG_MAIN = new Color(30, 30, 30);
    private final Color ACCENT = new Color(100, 149, 237);
    private final Color TEXT_PRIMARY = Color.WHITE;
    private final Color TEXT_LIGHT = Color.LIGHT_GRAY;
    private final Color CLEAR_BTN = Color.LIGHT_GRAY;
    private final Color BUTTON_TEXT = Color.WHITE;

    private JButton btnReview, btnRequests, btnBack;

    public ManagerSelectionFrame() {
        setTitle("StaffSync - Manager Selection");
        setSize(800, 430); 
        setLayout(null);   
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_MAIN);
        setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());

    
        JLabel leftPanel = new JLabel(new ImageIcon("src\\main\\java\\images\\bgimage2blur.png"));
        leftPanel.setBounds(0, 0, 400, 400);
        leftPanel.setLayout(null);
        add(leftPanel);

        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setBounds(136, 70, 128, 128);
        leftPanel.add(lblLogo);

        JLabel lblBrand = new JLabel("StaffSync", SwingConstants.CENTER);
        lblBrand.setBounds(115, 200, 170, 40);
        lblBrand.setForeground(ACCENT);
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 36));
        leftPanel.add(lblBrand);

        JLabel lblSubtitle = new JLabel("Design. Create. Manage.", SwingConstants.CENTER);
        lblSubtitle.setBounds(115, 240, 170, 20);
        lblSubtitle.setForeground(TEXT_LIGHT);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        leftPanel.add(lblSubtitle);

        JLabel lblTitle = new JLabel("Manager Portal");
        lblTitle.setBounds(475, 50, 250, 30);
        lblTitle.setForeground(TEXT_PRIMARY);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(lblTitle);

        JLabel lblInstruction = new JLabel("Please select an operation to continue:");
        lblInstruction.setBounds(475, 90, 300, 20);
        lblInstruction.setForeground(TEXT_LIGHT);
        lblInstruction.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(lblInstruction);


        btnReview = new JButton("Review Employees");
        styleButton(btnReview, 150, ACCENT, BUTTON_TEXT);
        add(btnReview);


        btnRequests = new JButton("Process Requests");
        styleButton(btnRequests, 210, ACCENT, BUTTON_TEXT);
        add(btnRequests);


        btnBack = new JButton("Logout");
        styleButton(btnBack, 280, CLEAR_BTN, Color.BLACK);
        add(btnBack);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void styleButton(JButton btn, int y, Color bg, Color fg) {
        btn.setBounds(475, y, 250, 40);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnReview) {
            new ManagerFrameReview();
            dispose();
        } else if (e.getSource() == btnRequests) {
            new ManagerFrameRequests();
            dispose();
        } else if (e.getSource() == btnBack) {
            new LoginFrame();
            dispose();
        }
    }
}