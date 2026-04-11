//package com.mycompany.employeemanagementsystem3;
//
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//
//public class ManagerFrameReview extends JFrame {
//
//    private JPanel sideBar, mainContent;
//    private JTable employeeTable;
//    private JButton btnAdd, btnEdit, btnDelete, btnSignOut;
//
//    public ManagerFrameReview() {
//        setTitle("StaffSync - HR Dashboard");
//        setSize(1100, 700);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        
//        // --- THE CORE RULE: Frame is now NULL ---
//        setLayout(null); 
//
//        // --- 1. SIDEBAR (Manual Positioning) ---
//        sideBar = new JPanel();
//        sideBar.setBackground(new Color(33, 47, 61)); 
//        // We manually set it to start at 0,0 and take 250 width and full height
//        sideBar.setBounds(0, 0, 250, 700); 
//        sideBar.setLayout(null);
//
//        // Logo
//        JLabel lblProfilePic = new JLabel("[User Icon]"); 
//        lblProfilePic.setForeground(Color.GRAY);
//        lblProfilePic.setBounds(75, 40, 100, 100);
//        lblProfilePic.setHorizontalAlignment(SwingConstants.CENTER);
//        sideBar.add(lblProfilePic);
//
//        btnSignOut = new JButton("Sign out →");
//        btnSignOut.setBounds(25, 580, 200, 45);
//        btnSignOut.setBackground(new Color(52, 152, 219));
//        btnSignOut.setForeground(Color.WHITE);
//        btnSignOut.setFocusPainted(false);
//        btnSignOut.setBorderPainted(false);
//        btnSignOut.setFont(new Font("SansSerif", Font.BOLD, 13));
//        sideBar.add(btnSignOut);
//
//        // --- 2. MAIN CONTENT (Manual Positioning) ---
//        mainContent = new JPanel();
//        mainContent.setBackground(new Color(245, 245, 245));
//        mainContent.setLayout(null);
//        // It starts at X=250 (where sidebar ends) and takes the remaining 850px width
//        mainContent.setBounds(250, 0, 850, 700); 
//
//        JLabel lblTitle = new JLabel("Employee Records");
//        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
//        lblTitle.setForeground(new Color(33, 47, 61));
//        lblTitle.setBounds(30, 30, 300, 40);
//        mainContent.add(lblTitle);
//
//        // --- 3. THE TABLE ---
//        String[] columns = {"ID", "First Name", "Last Name", "Department", "Position", "Salary"};
//        String[][] data = {
//            {"001", "Jomar N.", "Pangilinan", "Management", "Manager", "50,000"},
//            {"002", "Karlo", "Alatiit", "Operations", "Supervisor", "40,000"},
//            {"003", "Rich Jasper", "Federio", "Technical", "Staff", "30,000"},
//            {"004", "Alice", "Guo", "Finance", "Accountant", "35,000"},
//            {"005", "Bob", "Marley", "Logistics", "Driver", "25,000"}
//        };
//
//        DefaultTableModel model = new DefaultTableModel(data, columns) {
//            @Override
//            public boolean isCellEditable(int row, int column) { return false; }
//        };
//
//        employeeTable = new JTable(model);
//        employeeTable.setRowHeight(45);
//        
//        // Table Design
//        employeeTable.getTableHeader().setBackground(new Color(33, 47, 61));
//        employeeTable.getTableHeader().setForeground(Color.WHITE);
//        employeeTable.setSelectionBackground(new Color(52, 152, 219, 40));
//        employeeTable.setShowVerticalLines(false);
//
//        // ScrollPane must have setBounds to appear in a null layout
//        JScrollPane scrollPane = new JScrollPane(employeeTable);
//        scrollPane.setBounds(30, 100, 780, 480); 
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
//        mainContent.add(scrollPane);
//
//        // Table Click Logic
//        employeeTable.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int row = employeeTable.getSelectedRow();
//                if (row != -1) {
//                    String name = employeeTable.getValueAt(row, 1).toString() + " " + employeeTable.getValueAt(row, 2).toString();
//                    String pos = employeeTable.getValueAt(row, 4).toString();
//                    new ManagerFrameReviewPerf(name, "N/A", "N/A", pos);
//                    dispose();
//                }
//            }
//        });
//
//        // --- 4. ADD PANELS TO FRAME ---
//        // Since layout is null, the order doesn't matter, but setBounds does!
//        add(sideBar);
//        add(mainContent);
//
//        setVisible(true);
//    }
//
//    private JButton createSidebarButton(String text, int y, Color bg) {
//        JButton btn = new JButton(text);
//        btn.setBounds(25, y, 200, 45);
//        btn.setBackground(bg);
//        btn.setForeground(Color.WHITE);
//        btn.setFocusPainted(false);
//        btn.setBorderPainted(false);
//        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
//        return btn;
//    }
//
//    public static void main(String[] args) {
//        new ManagerFrameReview();
//    }
//}

package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ManagerFrameReview extends JFrame implements ActionListener {

    private JPanel sideBar, mainContent;
    private JTable employeeTable;
    private JButton btnSignOut;

    public ManagerFrameReview() {
        setTitle("StaffSync - HR Dashboard");
        setSize(1000, 1000); // Matched to ReviewPerf size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // --- NULL LAYOUT PROTOCOL ---
        setLayout(null); 

        // --- 1. SIDEBAR (Width: 250, Height: 1000) ---
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

        // Consistent Sign Out placement at the bottom
        btnSignOut = new JButton("Sign out →");
        btnSignOut.setBounds(25, 880, 200, 45); 
        btnSignOut.setBackground(new Color(52, 152, 219));
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.setFocusPainted(false);
        btnSignOut.setBorderPainted(false);
        btnSignOut.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSignOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sideBar.add(btnSignOut);

        // --- 2. MAIN CONTENT (Width: 750, Height: 1000) ---
        mainContent = new JPanel();
        mainContent.setBackground(new Color(245, 245, 245));
        mainContent.setLayout(null);
        mainContent.setBounds(250, 0, 750, 1000); 

        JLabel lblTitle = new JLabel("Employee Records");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitle.setForeground(new Color(33, 47, 61));
        lblTitle.setBounds(30, 30, 300, 40);
        mainContent.add(lblTitle);

        // --- 3. THE REFINED TABLE ---
        String[] columns = {"ID", "First Name", "Last Name", "Department", "Position", "Salary"};
        String[][] data = {
            {"001", "Jomar N.", "Pangilinan", "Management", "Manager", "50,000"},
            {"002", "Karlo", "Alatiit", "Operations", "Supervisor", "40,000"},
            {"003", "Rich Jasper", "Federio", "Technical", "Staff", "30,000"},
            {"004", "Alice", "Guo", "Finance", "Accountant", "35,000"},
            {"005", "Bob", "Marley", "Logistics", "Driver", "25,000"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        employeeTable = new JTable(model);
        employeeTable.setRowHeight(45);
        
        // Table Design (Flat UI)
        employeeTable.getTableHeader().setBackground(new Color(33, 47, 61));
        employeeTable.getTableHeader().setForeground(Color.WHITE);
        employeeTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        employeeTable.setSelectionBackground(new Color(52, 152, 219, 40));
        employeeTable.setShowVerticalLines(false);

        // Table ScrollPane
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        // Adjusted bounds to fit the new 750px wide mainContent area
        scrollPane.setBounds(30, 100, 690, 750); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainContent.add(scrollPane);

        // Table Click Logic to open ManagerFrameReviewPerf
        employeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = employeeTable.getSelectedRow();
                if (row != -1) {
                    String name = employeeTable.getValueAt(row, 1).toString() + " " + employeeTable.getValueAt(row, 2).toString();
                    String pos = employeeTable.getValueAt(row, 4).toString();
                    // Transition to the 1000x1000 Performance Frame
                    new ManagerFrameReviewPerf(name, "N/A", "N/A", pos);
                    dispose();
                }
            }
        });

        // --- 4. ASSEMBLE ---
        add(sideBar);
        add(mainContent);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
       
    }

    public static void main(String[] args) {
        // Apply System Look and Feel for cleaner components
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        new ManagerFrameReview();
    }

    
}