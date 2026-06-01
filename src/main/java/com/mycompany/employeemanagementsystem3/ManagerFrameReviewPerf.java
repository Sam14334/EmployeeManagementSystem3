package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ManagerFrameReviewPerf extends JFrame implements ActionListener {

    private JTextArea txtFeedback;
    private JButton btnSubmit, btnBack, btnSignOut;
    private JButton btnEmpRecords, btnEmpRequests;
    private JPanel sideBar, mainContent, cardPanel;
    private String employeeName; // Cached for submit actions

    public ManagerFrameReviewPerf(String name, String address, String contact, String position) {
        this.employeeName = name;
        
        setTitle("StaffSync - Manager - Employee Review: " + name);
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // --- SIDEBAR NAVIGATION (Identical Format to ManagerFrameReview) ---
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

        // Styled buttons replacing old standard styles
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

        // --- MAIN CONTENT AREA (Identical Format to ManagerFrameReview) ---
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
        cardPanel.setBounds(100, 120, 550, 730); // Adjusted layout dimensions cleanly
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

        currentY = addCategoryHeader(tableContainer, "A. Professional Behavior", currentY);
        addRatingRow(tableContainer, "1. Maintains punctual attendance and adheres to company work-hour policies.", currentY);
        currentY += 55;
        addRatingRow(tableContainer, "2. Consistently treats colleagues, clients, and partners with courtesy and professionalism.", currentY);
        currentY += 55;
        addRatingRow(tableContainer, "3. Takes responsibility for actions, decisions, and deadlines.", currentY);
        currentY += 55;

        currentY = addCategoryHeader(tableContainer, "B. Communication Skills", currentY);
        addRatingRow(tableContainer, "1. Communicates ideas, information, and updates clearly and concisely.", currentY);
        currentY += 55;
        addRatingRow(tableContainer, "2. Effectively listens to others, provides constructive and specific feedback.", currentY);
        currentY += 55;

        currentY = addCategoryHeader(tableContainer, "C. Task and Project Management", currentY);
        addRatingRow(tableContainer, "1. Manages workload efficiently and completes assigned tasks within timelines.", currentY);
        currentY += 55;
        addRatingRow(tableContainer, "2. Proactively identifies and implements process improvements.", currentY);
        currentY += 55;

        currentY = addCategoryHeader(tableContainer, "D. Continuous Development", currentY);
        addRatingRow(tableContainer, "1. Demonstrates initiative and takes positive steps for development and growth.", currentY);
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

    /**
     * Renders uniform rounded action buttons matching the layout system
     */
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

    private void addRatingRow(JPanel parent, String criteriaText, int y) {
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

        JPanel line = new JPanel();
        line.setBackground(new Color(220, 220, 220));
        line.setBounds(0, y + 54, 450, 1);
        parent.add(line);

        parent.setPreferredSize(new Dimension(430, y + 55));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            JOptionPane.showMessageDialog(this, "Review for " + employeeName + " has been successfully submitted.", "Success", JOptionPane.INFORMATION_MESSAGE);
            new ManagerFrameReview();
            dispose();
        } else if (e.getSource() == btnBack) {
            new ManagerFrameReview();
            dispose();
        } else if (e.getSource() == btnSignOut) {
            dispose();
            new LoginFrame();
        }else if (e.getSource() == btnEmpRecords) {
            dispose();
            new HRFrame();
    }
}}