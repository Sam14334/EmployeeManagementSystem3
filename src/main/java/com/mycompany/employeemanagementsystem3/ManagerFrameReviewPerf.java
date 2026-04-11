//package com.mycompany.employeemanagementsystem3;
//
//import java.awt.*;
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//
//public class ManagerFrameReviewPerf extends JFrame {
//
//    private JTextArea txtFeedback;
//    private JComboBox<String> cbRating;
//    private JCheckBox chkPerformance, chkSkills, chkBehavior, chkGrowth;
//    private JButton btnSubmit, btnBack;
//
//    public ManagerFrameReviewPerf(String name, String address, String contact, String position) {
//        setTitle("Employee Review - " + name);
//        setSize(550, 750);
//        setLayout(new BorderLayout());
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(null);
//        mainPanel.setBackground(Color.WHITE);
//        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
//        add(mainPanel, BorderLayout.CENTER);
//
//        // Header
//        JLabel title = new JLabel("Performance Evaluation");
//        title.setBounds(50, 20, 450, 40);
//        title.setFont(new Font("SansSerif", Font.BOLD, 24));
//        title.setForeground(new Color(33, 47, 61));
//        mainPanel.add(title);
//
//        JLabel lblName = new JLabel("Employee: " + name);
//        lblName.setBounds(50, 70, 400, 25);
//        lblName.setFont(new Font("SansSerif", Font.PLAIN, 15));
//        mainPanel.add(lblName);
//
//        JLabel lblPosition = new JLabel("Position: " + position);
//        lblPosition.setBounds(50, 95, 400, 25);
//        lblPosition.setFont(new Font("SansSerif", Font.ITALIC, 14));
//        lblPosition.setForeground(Color.GRAY);
//        mainPanel.add(lblPosition);
//
//        // Checkboxes
//        JLabel lblCategories = new JLabel("Areas for Evaluation:");
//        lblCategories.setBounds(50, 140, 200, 25);
//        lblCategories.setFont(new Font("SansSerif", Font.BOLD, 15));
//        mainPanel.add(lblCategories);
//
//        chkPerformance = createStyledCheckBox("Work Performance (Technical proficiency/Goals)", 175);
//        chkSkills = createStyledCheckBox("Soft Skills & Communication (Collaboration)", 205);
//        chkBehavior = createStyledCheckBox("Behavioral Habits (Punctuality/Conduct)", 235);
//        chkGrowth = createStyledCheckBox("Growth & Leadership (Mentorship/Initiative)", 265);
//        
//        mainPanel.add(chkPerformance);
//        mainPanel.add(chkSkills);
//        mainPanel.add(chkBehavior);
//        mainPanel.add(chkGrowth);
//
//        // Feedback
//        JLabel lblFeedback = new JLabel("Manager's Detailed Feedback:");
//        lblFeedback.setBounds(50, 310, 300, 25);
//        lblFeedback.setFont(new Font("SansSerif", Font.BOLD, 15));
//        mainPanel.add(lblFeedback);
//
//        txtFeedback = new JTextArea();
//        txtFeedback.setLineWrap(true);
//        txtFeedback.setWrapStyleWord(true);
//        txtFeedback.setFont(new Font("SansSerif", Font.PLAIN, 13));
//        
//        JScrollPane scroll = new JScrollPane(txtFeedback);
//        scroll.setBounds(50, 340, 430, 120);
//        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
//        mainPanel.add(scroll);
//
//        // Rating
//        JLabel lblRating = new JLabel("Overall Rating:");
//        lblRating.setBounds(50, 480, 120, 25);
//        lblRating.setFont(new Font("SansSerif", Font.BOLD, 14));
//        mainPanel.add(lblRating);
//
//        String[] ratings = {"Excellent", "Very Good", "Good", "Average", "Poor"};
//        cbRating = new JComboBox<>(ratings);
//        cbRating.setBounds(170, 480, 150, 30);
//        cbRating.setBackground(Color.WHITE);
//        mainPanel.add(cbRating);
//
//        // Buttons
//        btnSubmit = new JButton("Submit Review");
//        btnSubmit.setBounds(100, 580, 150, 45);
//        styleButton(btnSubmit, new Color(52, 152, 219));
//
//        btnBack = new JButton("Back");
//        btnBack.setBounds(270, 580, 150, 45);
//        styleButton(btnBack, new Color(149, 165, 166)); // Gray Back Button
//
//        mainPanel.add(btnSubmit);
//        mainPanel.add(btnBack);
//
//        // Logic
//        btnSubmit.addActionListener(e -> {
//            JOptionPane.showMessageDialog(this, "Review for " + name + " has been successfully submitted.", "Success", JOptionPane.INFORMATION_MESSAGE);
//            new ManagerFrameReview();
//            dispose();
//        });
//
//        btnBack.addActionListener(e -> {
//            new ManagerFrameReview();
//            dispose();
//        });
//
//        setVisible(true);
//    }
//
//    private JCheckBox createStyledCheckBox(String text, int y) {
//        JCheckBox chk = new JCheckBox(text);
//        chk.setBounds(50, y, 420, 25);
//        chk.setBackground(Color.WHITE);
//        chk.setFont(new Font("SansSerif", Font.PLAIN, 13));
//        chk.setFocusPainted(false);
//        return chk;
//    }
//
//    private void styleButton(JButton btn, Color bg) {
//        btn.setBackground(bg);
//        btn.setForeground(Color.WHITE);
//        btn.setFocusPainted(false);
//        btn.setBorderPainted(false);
//        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
//        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//    }
//}

package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ManagerFrameReviewPerf extends JFrame {

    private JTextArea txtFeedback;
    private JComboBox<String> cbRating;
    private JCheckBox chkPerformance, chkSkills, chkBehavior, chkGrowth;
    private JButton btnSubmit, btnBack, btnSignOut;
    private JPanel sideBar, mainContent, cardPanel;

    public ManagerFrameReviewPerf(String name, String address, String contact, String position) {
        setTitle("Employee Review - " + name);
        setSize(1000, 1000); // Updated size
        setLayout(null); // Pure null layout
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- 1. SIDEBAR ---
        sideBar = new JPanel();
        sideBar.setBackground(new Color(33, 47, 61)); 
        sideBar.setBounds(0, 0, 250, 1000);
        sideBar.setLayout(null);
        
        ImageIcon rawIcon = new ImageIcon("src\\main\\java\\images\\karlo.png"); 

        
        Image scaledImg = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon finalAvatar = new ImageIcon(scaledImg);

       
        JLabel lblProfilePic = new JLabel(finalAvatar);
        lblProfilePic.setBounds(80, 30, 100, 100);

       
        lblProfilePic.setBorder(new LineBorder(new Color(255, 255, 255, 50), 2)); 

        sideBar.add(lblProfilePic);

        JLabel lblUser = new JLabel("Review Manager | Karlo", SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setBounds(30, 140, 200, 25);
        sideBar.add(lblUser);


        // Logo/User Icon Placeholder
        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png")); 
        lblLogo.setForeground(Color.GRAY);
        lblLogo.setBounds(66, 185, 128, 128); 
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        sideBar.add(lblLogo);

        // Sign Out Button (Positioned at bottom)
        btnSignOut = new JButton("Sign out →");
        btnSignOut.setBounds(25, 880, 200, 45);
        btnSignOut.setBackground(new Color(52, 152, 219));
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.setFocusPainted(false);
        btnSignOut.setBorderPainted(false);
        btnSignOut.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSignOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sideBar.add(btnSignOut);

        // --- 2. MAIN CONTENT AREA ---
        mainContent = new JPanel();
        mainContent.setBackground(new Color(245, 245, 245));
        mainContent.setBounds(250, 0, 750, 1000);
        mainContent.setLayout(null);

        // --- 3. EVALUATION CARD (Centered in Main Content) ---
        cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBounds(100, 100, 550, 750); // Centered evaluation area
        cardPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        mainContent.add(cardPanel);

        // Header
        JLabel title = new JLabel("Performance Evaluation");
        title.setBounds(50, 40, 450, 40);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(33, 47, 61));
        cardPanel.add(title);

        JLabel lblName = new JLabel("Employee: " + name);
        lblName.setBounds(50, 100, 400, 25);
        lblName.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cardPanel.add(lblName);

        JLabel lblPosition = new JLabel("Position: " + position);
        lblPosition.setBounds(50, 125, 400, 25);
        lblPosition.setFont(new Font("SansSerif", Font.ITALIC, 15));
        lblPosition.setForeground(Color.GRAY);
        cardPanel.add(lblPosition);

        // Checkboxes
        JLabel lblCategories = new JLabel("Areas for Evaluation:");
        lblCategories.setBounds(50, 180, 250, 25);
        lblCategories.setFont(new Font("SansSerif", Font.BOLD, 16));
        cardPanel.add(lblCategories);

        chkPerformance = createStyledCheckBox("Work Performance (Technical proficiency/Goals)", 220);
        chkSkills = createStyledCheckBox("Soft Skills & Communication (Collaboration)", 255);
        chkBehavior = createStyledCheckBox("Behavioral Habits (Punctuality/Conduct)", 290);
        chkGrowth = createStyledCheckBox("Growth & Leadership (Mentorship/Initiative)", 325);
        
        cardPanel.add(chkPerformance);
        cardPanel.add(chkSkills);
        cardPanel.add(chkBehavior);
        cardPanel.add(chkGrowth);

        // Feedback
        JLabel lblFeedback = new JLabel("Manager's Detailed Feedback:");
        lblFeedback.setBounds(50, 380, 300, 25);
        lblFeedback.setFont(new Font("SansSerif", Font.BOLD, 16));
        cardPanel.add(lblFeedback);

        txtFeedback = new JTextArea();
        txtFeedback.setLineWrap(true);
        txtFeedback.setWrapStyleWord(true);
        txtFeedback.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        JScrollPane scroll = new JScrollPane(txtFeedback);
        scroll.setBounds(50, 415, 450, 150);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        cardPanel.add(scroll);

        // Rating
        JLabel lblRating = new JLabel("Overall Rating:");
        lblRating.setBounds(50, 590, 150, 25);
        lblRating.setFont(new Font("SansSerif", Font.BOLD, 15));
        cardPanel.add(lblRating);

        String[] ratings = {"Excellent", "Very Good", "Good", "Average", "Poor"};
        cbRating = new JComboBox<>(ratings);
        cbRating.setBounds(180, 590, 150, 30);
        cbRating.setBackground(Color.WHITE);
        cardPanel.add(cbRating);

        // Buttons
        btnSubmit = new JButton("Submit Review");
        btnSubmit.setBounds(100, 660, 160, 45);
        styleButton(btnSubmit, new Color(52, 152, 219));

        btnBack = new JButton("Back");
        btnBack.setBounds(290, 660, 160, 45);
        styleButton(btnBack, new Color(149, 165, 166));

        cardPanel.add(btnSubmit);
        cardPanel.add(btnBack);

        // Adding Panels to Frame
        add(sideBar);
        add(mainContent);

        // --- Logic ---
        btnSubmit.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Review for " + name + " has been successfully submitted.", "Success", JOptionPane.INFORMATION_MESSAGE);
            new ManagerFrameReview();
            dispose();
        });

        btnBack.addActionListener(e -> {
            new ManagerFrameReview();
            dispose();
        });

        setVisible(true);
    }

    private JCheckBox createStyledCheckBox(String text, int y) {
        JCheckBox chk = new JCheckBox(text);
        chk.setBounds(50, y, 450, 25);
        chk.setBackground(Color.WHITE);
        chk.setFont(new Font("SansSerif", Font.PLAIN, 14));
        chk.setFocusPainted(false);
        return chk;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}