package com.mycompany.employeemanagementsystem3;

import java.awt.Font;
import javax.swing.*;

public class ManagerFrameReviewPerf extends JFrame {

    private JTextArea txtFeedback;
    private JComboBox<String> cbRating;
    // New Checkboxes for the Subject Matter Categories
    private JCheckBox chkPerformance, chkSkills, chkBehavior, chkGrowth;
    private JButton btnSubmit, btnBack;

    public ManagerFrameReviewPerf(String name, String address, String contact, String position) {

        setTitle("Employee Review - " + name);
        setSize(500, 700); // Increased height to fit new categories
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Employee Info Section ---
        JLabel title = new JLabel("Performance Evaluation");
        title.setBounds(100, 10, 300, 40);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        JLabel lblName = new JLabel("Name: " + name);
        lblName.setBounds(50, 60, 400, 25);
        add(lblName);

        JLabel lblPosition = new JLabel("Position: " + position);
        lblPosition.setBounds(50, 85, 400, 25);
        add(lblPosition);

        // --- 2. Categories by Subject Matter ---
        JLabel lblCategories = new JLabel("Areas for Evaluation:");
        lblCategories.setBounds(50, 120, 200, 25);
        lblCategories.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblCategories);

        chkPerformance = new JCheckBox("Work Performance (Technical proficiency/Goals)");
        chkPerformance.setBounds(50, 150, 400, 25);
        add(chkPerformance);

        chkSkills = new JCheckBox("Soft Skills & Communication (Collaboration)");
        chkSkills.setBounds(50, 180, 400, 25);
        add(chkSkills);

        chkBehavior = new JCheckBox("Behavioral Habits (Punctuality/Conduct)");
        chkBehavior.setBounds(50, 210, 400, 25);
        add(chkBehavior);

        chkGrowth = new JCheckBox("Growth & Leadership (Mentorship/Initiative)");
        chkGrowth.setBounds(50, 240, 400, 25);
        add(chkGrowth);

        // --- Feedback & Final Rating ---
        JLabel lblFeedback = new JLabel("Manager's Detailed Feedback:");
        lblFeedback.setBounds(50, 280, 200, 25);
        add(lblFeedback);

        txtFeedback = new JTextArea();
        txtFeedback.setLineWrap(true);
        txtFeedback.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtFeedback);
        scroll.setBounds(50, 310, 380, 100);
        add(scroll);

        JLabel lblRating = new JLabel("Overall Rating:");
        lblRating.setBounds(50, 430, 100, 25);
        add(lblRating);

        String[] ratings = {"Excellent", "Very Good", "Good", "Average"};
        cbRating = new JComboBox<>(ratings);
        cbRating.setBounds(150, 430, 150, 25);
        add(cbRating);

        // --- Buttons ---
        btnSubmit = new JButton("Submit Review");
        btnSubmit.setBounds(100, 500, 130, 40);
        add(btnSubmit);

        btnBack = new JButton("Back");
        btnBack.setBounds(250, 500, 130, 40);
        add(btnBack);

        // --- Action Logic ---
        btnSubmit.addActionListener(e -> {
            String summary = "Review for: " + name + "\n" +
                             "Overall: " + cbRating.getSelectedItem() + "\n\n" +
                             "Criteria Met:\n" +
                             "- Performance: " + (chkPerformance.isSelected() ? "Yes" : "No") + "\n" +
                             "- Soft Skills: " + (chkSkills.isSelected() ? "Yes" : "No") + "\n" +
                             "- Behavior: " + (chkBehavior.isSelected() ? "Yes" : "No") + "\n" +
                             "- Growth: " + (chkGrowth.isSelected() ? "Yes" : "No") + "\n\n" +
                             "Comments: " + txtFeedback.getText();

            JOptionPane.showMessageDialog(this, summary, "Review Submitted", JOptionPane.INFORMATION_MESSAGE);
        });

        btnBack.addActionListener(e -> {
            new ManagerFrameReview();
            dispose();
        });

        setVisible(true);
    }
}