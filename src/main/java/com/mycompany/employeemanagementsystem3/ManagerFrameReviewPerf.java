
package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import java.sql.*; // NEW: For database connection
import java.util.ArrayList; // NEW: To store radio button groups
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ManagerFrameReviewPerf extends JFrame implements ActionListener {

    private JTextArea txtFeedback;
    private JButton btnSubmit, btnBack, btnSignOut;
    private JButton btnEmpRecords, btnEmpRequests;
    private JPanel sideBar, mainContent, cardPanel;
    
    private String employeeName; 
    private String employeeId; // NEW: We need this for the database Foreign Key

    // NEW: Lists to keep track of the radio button groups for each category so we can average them later
    private List<ButtonGroup> behaviorGroups = new ArrayList<>();
    private List<ButtonGroup> commsGroups = new ArrayList<>();
    private List<ButtonGroup> mgmtGroups = new ArrayList<>();
    private List<ButtonGroup> devGroups = new ArrayList<>();

    // NEW: Updated constructor to accept employeeId
    public ManagerFrameReviewPerf(String employeeId, String name, String position) {
        this.employeeId = employeeId;
        this.employeeName = name;
        
        setTitle("StaffSync - Manager - Employee Review: " + name);
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // --- SIDEBAR NAVIGATION ---
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

        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setBounds(66, 185, 128, 128);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        sideBar.add(lblLogo);

        btnSubmit = createStyledBtn("💾 Submit Review", 340, new Color(52, 152, 219));
        btnBack = createStyledBtn("← Back to List", 400, new Color(127, 140, 141));
        sideBar.add(btnSubmit);
        sideBar.add(btnBack);

        btnSignOut = new JButton("Sign out →");
        btnSignOut.setBounds(35, 890, 180, 50);
        btnSignOut.setBackground(Color.RED);
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.setFocusPainted(false);
        btnSignOut.setBorderPainted(false);
        btnSignOut.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSignOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSignOut.addActionListener(this);
        sideBar.add(btnSignOut);

        // --- MAIN CONTENT AREA ---
        mainContent = new JPanel();
        mainContent.setBackground(new Color(245, 245, 245));
        mainContent.setLayout(null);
        mainContent.setBounds(250, 0, 750, 1000);

        Color sidebarDarkGray = new Color(33, 47, 61);

        btnEmpRecords = new JButton("Employee Records");
        btnEmpRecords.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnEmpRecords.setBackground(sidebarDarkGray);
        btnEmpRecords.setForeground(Color.WHITE);
        btnEmpRecords.setBounds(30, 30, 240, 45);
        btnEmpRecords.setFocusPainted(false);
        btnEmpRecords.setBorderPainted(false);
        btnEmpRecords.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEmpRecords.addActionListener(this);
        mainContent.add(btnEmpRecords);

        btnEmpRequests = new JButton("Employee Review");
        btnEmpRequests.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnEmpRequests.setBackground(sidebarDarkGray);
        btnEmpRequests.setForeground(Color.WHITE);
        btnEmpRequests.setBounds(285, 30, 240, 45);
        btnEmpRequests.setFocusPainted(false);
        btnEmpRequests.setBorderPainted(false);
        btnEmpRequests.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEmpRequests.addActionListener(this);
        mainContent.add(btnEmpRequests);

        // --- CARD INTERIOR CONTENT PANEL ---
        cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBounds(100, 120, 550, 730);
        cardPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        mainContent.add(cardPanel);

        JLabel title = new JLabel("Performance Evaluation");
        title.setBounds(50, 35, 450, 40);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(33, 47, 61));
        cardPanel.add(title);

        JLabel lblName = new JLabel("Employee: " + name);
        lblName.setBounds(50, 95, 400, 25);
        lblName.setFont(new Font("SansSerif", Font.BOLD, 15));
        cardPanel.add(lblName);

        JLabel lblPosition = new JLabel("Position: " + position);
        lblPosition.setBounds(50, 120, 400, 25);
        lblPosition.setFont(new Font("SansSerif", Font.ITALIC, 14));
        lblPosition.setForeground(Color.GRAY);
        cardPanel.add(lblPosition);

        JLabel lblRatings = new JLabel("Poor (1) Average (2) Good (3) Very Good (4) Excellent (5)");
        lblRatings.setBounds(50, 145, 400, 25);
        lblRatings.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblRatings.setForeground(Color.DARK_GRAY);
        cardPanel.add(lblRatings);

        JPanel tableContainer = new JPanel();
        tableContainer.setLayout(null);
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        JPanel tableHeader = new JPanel();
        tableHeader.setBackground(new Color(15, 55, 95));
        tableHeader.setBounds(0, 0, 450, 30);
        tableHeader.setLayout(null);

        JLabel lblHeaderCriteria = new JLabel("Evaluation Criteria", SwingConstants.CENTER);
        lblHeaderCriteria.setForeground(Color.WHITE);
        lblHeaderCriteria.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblHeaderCriteria.setBounds(0, 0, 240, 30);
        tableHeader.add(lblHeaderCriteria);

        JLabel lblHeaderRating = new JLabel("Rating", SwingConstants.CENTER);
        lblHeaderRating.setForeground(Color.WHITE);
        lblHeaderRating.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblHeaderRating.setBounds(240, 0, 210, 30);
        tableHeader.add(lblHeaderRating);
        tableContainer.add(tableHeader);

        int currentY = 30;

        // NEW: Passed the specific category lists to the addRatingRow method
        currentY = addCategoryHeader(tableContainer, "A. Professional Behavior", currentY);
        addRatingRow(tableContainer, "1. Maintains punctual attendance and adheres to company work-hour policies.", currentY, behaviorGroups);
        currentY += 55;
        addRatingRow(tableContainer, "2. Consistently treats colleagues, clients, and partners with courtesy and professionalism.", currentY, behaviorGroups);
        currentY += 55;
        addRatingRow(tableContainer, "3. Takes responsibility for actions, decisions, and deadlines.", currentY, behaviorGroups);
        currentY += 55;

        currentY = addCategoryHeader(tableContainer, "B. Communication Skills", currentY);
        addRatingRow(tableContainer, "1. Communicates ideas, information, and updates clearly and concisely.", currentY, commsGroups);
        currentY += 55;
        addRatingRow(tableContainer, "2. Effectively listens to others, provides constructive and specific feedback.", currentY, commsGroups);
        currentY += 55;

        currentY = addCategoryHeader(tableContainer, "C. Task and Project Management", currentY);
        addRatingRow(tableContainer, "1. Manages workload efficiently and completes assigned tasks within timelines.", currentY, mgmtGroups);
        currentY += 55;
        addRatingRow(tableContainer, "2. Proactively identifies and implements process improvements.", currentY, mgmtGroups);
        currentY += 55;

        currentY = addCategoryHeader(tableContainer, "D. Continuous Development", currentY);
        addRatingRow(tableContainer, "1. Demonstrates initiative and takes positive steps for development and growth.", currentY, devGroups);
        currentY += 55;

        JScrollPane tableScroll = new JScrollPane(tableContainer);
        tableScroll.setBounds(50, 180, 450, 230);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());
        tableScroll.getVerticalScrollBar().setUnitIncrement(12);
        cardPanel.add(tableScroll);

        JLabel lblFeedback = new JLabel("Manager's Detailed Feedback:");
        lblFeedback.setBounds(50, 430, 300, 25);
        lblFeedback.setFont(new Font("SansSerif", Font.BOLD, 15));
        cardPanel.add(lblFeedback);

        txtFeedback = new JTextArea();
        txtFeedback.setLineWrap(true);
        txtFeedback.setWrapStyleWord(true);
        txtFeedback.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(txtFeedback);
        scroll.setBounds(50, 465, 450, 160);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        cardPanel.add(scroll);

        add(sideBar);
        add(mainContent);

        setVisible(true);

        SwingUtilities.invokeLater(() -> {
            tableScroll.getVerticalScrollBar().setValue(0);
        });
    }

    private JButton createStyledBtn(String text, int y, Color color) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        b.setBounds(25, y, 200, 45); 
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addActionListener(this);
        return b;
    }

    private int addCategoryHeader(JPanel parent, String title, int y) {
        JPanel catPanel = new JPanel();
        catPanel.setBackground(new Color(230, 235, 245));
        catPanel.setBounds(0, y, 450, 25);
        catPanel.setLayout(null);

        JLabel lblCat = new JLabel(" " + title);
        lblCat.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblCat.setBounds(5, 0, 440, 25);
        catPanel.add(lblCat);

        parent.add(catPanel);
        parent.setPreferredSize(new Dimension(430, y + 35));
        return y + 25;
    }

    // NEW: Added the list parameter so the group gets saved
    private void addRatingRow(JPanel parent, String criteriaText, int y, List<ButtonGroup> categoryList) {
        JTextArea lblCriteria = new JTextArea(criteriaText);
        lblCriteria.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblCriteria.setLineWrap(true);
        lblCriteria.setWrapStyleWord(true);
        lblCriteria.setEditable(false);
        lblCriteria.setBackground(Color.WHITE);
        lblCriteria.setBounds(5, y + 6, 230, 42);
        parent.add(lblCriteria);

        JPanel rbPanel = new JPanel();
        rbPanel.setLayout(null);
        rbPanel.setBackground(Color.WHITE);
        rbPanel.setBounds(240, y, 210, 50);

        ButtonGroup group = new ButtonGroup();

        JRadioButton rb1 = new JRadioButton("1");
        JRadioButton rb2 = new JRadioButton("2");
        JRadioButton rb3 = new JRadioButton("3");
        JRadioButton rb4 = new JRadioButton("4");
        JRadioButton rb5 = new JRadioButton("5");

        // NEW: Set Action Commands so we can easily retrieve the number value later
        rb1.setActionCommand("1");
        rb2.setActionCommand("2");
        rb3.setActionCommand("3");
        rb4.setActionCommand("4");
        rb5.setActionCommand("5");

        rb1.setFont(new Font("SansSerif", Font.PLAIN, 10)); rb1.setBackground(Color.WHITE); rb1.setFocusPainted(false);
        rb2.setFont(new Font("SansSerif", Font.PLAIN, 10)); rb2.setBackground(Color.WHITE); rb2.setFocusPainted(false);
        rb3.setFont(new Font("SansSerif", Font.PLAIN, 10)); rb3.setBackground(Color.WHITE); rb3.setFocusPainted(false);
        rb4.setFont(new Font("SansSerif", Font.PLAIN, 10)); rb4.setBackground(Color.WHITE); rb4.setFocusPainted(false);
        rb5.setFont(new Font("SansSerif", Font.PLAIN, 10)); rb5.setBackground(Color.WHITE); rb5.setFocusPainted(false);

        rb1.setBounds(4,   10, 38, 30);
        rb2.setBounds(42,  10, 38, 30);
        rb3.setBounds(80,  10, 38, 30);
        rb4.setBounds(118, 10, 38, 30);
        rb5.setBounds(156, 10, 38, 30);

        group.add(rb1); group.add(rb2); group.add(rb3); group.add(rb4); group.add(rb5);

        rbPanel.add(rb1); rbPanel.add(rb2); rbPanel.add(rb3); rbPanel.add(rb4); rbPanel.add(rb5);
        parent.add(rbPanel);

        // NEW: Save the ButtonGroup into our tracking list
        categoryList.add(group);

        JPanel line = new JPanel();
        line.setBackground(new Color(220, 220, 220));
        line.setBounds(0, y + 54, 450, 1);
        parent.add(line);

        parent.setPreferredSize(new Dimension(430, y + 55));
    }

    // NEW: Helper method to calculate the average of a specific category
    private double calculateAverage(List<ButtonGroup> groups) throws Exception {
        double sum = 0;
        for (ButtonGroup group : groups) {
            if (group.getSelection() == null) {
                // If a row doesn't have a selection, throw an error to prevent DB submission
                throw new Exception("Incomplete"); 
            }
            sum += Integer.parseInt(group.getSelection().getActionCommand());
        }
        return sum / groups.size();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            
            // NEW: Database Insertion Logic
            try {
                // 1. Calculate Averages
                double avgBehavior = calculateAverage(behaviorGroups);
                double avgComms = calculateAverage(commsGroups);
                double avgMgmt = calculateAverage(mgmtGroups);
                double avgDev = calculateAverage(devGroups);
                String feedback = txtFeedback.getText();

                // 2. Connect to XAMPP MySQL Database
                String url = "jdbc:mysql://localhost:3306/db_employee_management";
                String user = "root";
                String password = ""; 

                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    // 3. Prepare the SQL Statement
                    String sql = "INSERT INTO employee_reviews (employee_id, behavior, communication, management, development, details) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    
                    pstmt.setString(1, this.employeeId);
                    pstmt.setDouble(2, avgBehavior);
                    pstmt.setDouble(3, avgComms);
                    pstmt.setDouble(4, avgMgmt);
                    pstmt.setDouble(5, avgDev);
                    pstmt.setString(6, feedback);

                    // 4. Execute and show success
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Review for " + employeeName + " has been successfully submitted and saved to the database.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Return to previous frame
                    new ManagerFrameReview(); // Assuming you will pass necessary args here if needed
                    dispose();
                }

            } catch (Exception ex) {
                if (ex.getMessage().equals("Incomplete")) {
                    JOptionPane.showMessageDialog(this, "Please select a rating for all criteria before submitting.", "Incomplete Review", JOptionPane.WARNING_MESSAGE);
                } else {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else if (e.getSource() == btnBack) {
            new ManagerFrameReview();
            dispose();
        } else if (e.getSource() == btnSignOut) {
            dispose();
            new LoginFrame();
        } else if (e.getSource() == btnEmpRecords) {
            dispose();
            new HRFrame();
        }
    }
}
