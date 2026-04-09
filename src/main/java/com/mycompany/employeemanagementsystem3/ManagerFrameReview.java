package com.mycompany.employeemanagementsystem3;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ManagerFrameReview extends JFrame implements ActionListener {   

    private JLabel lbl1; 
    private JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnBack;

    public ManagerFrameReview() {

        setTitle("StaffSync - Manager - Review Employees");
        setSize(1000, 1000);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title Label
        lbl1 = new JLabel("Review Employees");
        lbl1.setBounds(300, 100, 400, 50);
        lbl1.setFont(new Font("Arial", Font.BOLD, 30));
        lbl1.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl1);

        // Employee Buttons
        btn1 = new JButton("Jomar N. Pangilinan");
        btn1.setBounds(20, 250, 300, 50);
        add(btn1);

        btn2 = new JButton("Karlo Alatiit");
        btn2.setBounds(20, 350, 300, 50);
        add(btn2);

        btn3 = new JButton("Rich Jasper Federio");
        btn3.setBounds(20, 450, 300, 50);
        add(btn3);
        
        btn4 = new JButton("Jomar N. Pangilinan");
        btn4.setBounds(350, 250, 300, 50);
        add(btn4);

        btn5 = new JButton("Karlo Alatiit");
        btn5.setBounds(350, 350, 300, 50);
        add(btn5);

        btn6 = new JButton("Rich Jasper Federio");
        btn6.setBounds(350, 450, 300, 50);
        add(btn6);
        
        btn7 = new JButton("Jomar N. Pangilinan");
        btn7.setBounds(680, 250, 300, 50);
        add(btn7);

        btn8 = new JButton("Karlo Alatiit");
        btn8.setBounds(680, 350, 300, 50);
        add(btn8);

        btn9 = new JButton("Rich Jasper Federio");
        btn9.setBounds(680, 450, 300, 50);
        add(btn9);
        
        // Back Button
        btnBack = new JButton("Back to Dashboard");
        btnBack.setBounds(330, 800, 300, 50);
        add(btnBack);

        // Action Listeners
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
        btn6.addActionListener(this);
        btn7.addActionListener(this);
        btn8.addActionListener(this);
        btn9.addActionListener(this);
        btnBack.addActionListener(this);

        setVisible(true);
    }

    /**
     * Helper method to launch the details frame. 
     * The feedback textfield and ratings are handled inside EmployeeDetailsFrame.
     */
    private void openDetails(String name, String address, String contact, String position) {
        new ManagerFrameReviewPerf(name, address, contact, position);
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            openDetails("Jomar N. Pangilinan", "Laguna, Philippines", "09123456789", "Manager");
        } 
        else if (e.getSource() == btn2) {
            openDetails("Karlo Alatiit", "Manila, Philippines", "09987654321", "Supervisor");
        } 
        else if (e.getSource() == btn3) {
            openDetails("Rich Jasper Federio", "Cebu, Philippines", "09112223333", "Staff");
        }
        else if (e.getSource() == btn4) {
            openDetails("Karlo Alatiit", "Manila, Philippines", "09987654321", "Supervisor");
        } 
        else if (e.getSource() == btn5) {
            openDetails("Rich Jasper Federio", "Cebu, Philippines", "09112223333", "Staff");
        }
        else if (e.getSource() == btn6) {
            openDetails("Rich Jasper Federio", "Cebu, Philippines", "09112223333", "Staff");
        }
        else if (e.getSource() == btn4) {
            openDetails("Karlo Alatiit", "Manila, Philippines", "09987654321", "Supervisor");
        } 
        else if (e.getSource() == btn5) {
            openDetails("Rich Jasper Federio", "Cebu, Philippines", "09112223333", "Staff");
        }
        else if (e.getSource() == btn6) {
            openDetails("Rich Jasper Federio", "Cebu, Philippines", "09112223333", "Staff");
        }
        else if (e.getSource() == btnBack) {
            // Replace with your actual Dashboard class if needed
            // new MainDashboard(); 
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new ManagerFrameReview();
    }
}
