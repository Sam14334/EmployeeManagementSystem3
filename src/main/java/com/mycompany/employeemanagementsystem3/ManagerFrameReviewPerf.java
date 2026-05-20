package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ManagerFrameReviewPerf extends JFrame
{

    private JTextArea txtFeedback;
private JButton btnSubmit, btnBack;
private JPanel sideBar, mainContent, cardPanel;

public ManagerFrameReviewPerf(String name, String address, String contact, String position)
{
    setTitle("Employee Review - " + name);
    setSize(1000, 1000);
    setLayout(null);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
    lblLogo.setForeground(Color.GRAY);
    lblLogo.setBounds(66, 185, 128, 128);
    lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
    sideBar.add(lblLogo);

    btnSubmit = new JButton("Submit Review");
    btnSubmit.setBounds(35, 360, 180, 45);
    styleButton(btnSubmit, new Color(52, 152, 219));

    btnBack = new JButton("Back");
    btnBack.setBounds(35, 425, 180, 45);
    btnBack.setBackground(Color.RED);
    btnBack.setForeground(Color.WHITE);
    btnBack.setFont(new Font("SansSerif", Font.BOLD, 15));
    btnBack.setFocusPainted(false);
    btnBack.setBorderPainted(false);
    btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

    sideBar.add(btnSubmit);
    sideBar.add(btnBack);

    mainContent = new JPanel();
    mainContent.setBackground(new Color(245, 245, 245));
    mainContent.setBounds(250, 0, 750, 1000);
    mainContent.setLayout(null);

    cardPanel = new JPanel();
    cardPanel.setLayout(null);
    cardPanel.setBackground(Color.WHITE);
    cardPanel.setBounds(100, 100, 550, 750);
    cardPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
    mainContent.add(cardPanel);

    JLabel title = new JLabel("Performance Evaluation");
    title.setBounds(50, 40, 450, 40);
    title.setFont(new Font("SansSerif", Font.BOLD, 28));
    title.setForeground(new Color(33, 47, 61));
    cardPanel.add(title);

    JLabel lblName = new JLabel("Employee: " + name);
    lblName.setBounds(50, 115, 400, 25);
    lblName.setFont(new Font("SansSerif", Font.PLAIN, 16));
    cardPanel.add(lblName);

    JLabel lblPosition = new JLabel("Position: " + position);
    lblPosition.setBounds(50, 139, 400, 25);
    lblPosition.setFont(new Font("SansSerif", Font.ITALIC, 15));
    lblPosition.setForeground(Color.GRAY);
    cardPanel.add(lblPosition);

    JLabel lblRatings = new JLabel("Poor (1) Average (2) Good (3) Very Good (4) Excellent (5)");
    lblRatings.setBounds(50, 160, 400, 25);
    lblRatings.setFont(new Font("SansSerif", Font.PLAIN, 12));
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
    tableScroll.setBounds(50, 190, 450, 221);
    tableScroll.setBorder(BorderFactory.createEmptyBorder());
    tableScroll.getVerticalScrollBar().setUnitIncrement(12);
    cardPanel.add(tableScroll);

    JLabel lblFeedback = new JLabel("Manager's Detailed Feedback:");
    lblFeedback.setBounds(50, 440, 300, 25);
    lblFeedback.setFont(new Font("SansSerif", Font.BOLD, 16));
    cardPanel.add(lblFeedback);

    txtFeedback = new JTextArea();
    txtFeedback.setLineWrap(true);
    txtFeedback.setWrapStyleWord(true);
    txtFeedback.setFont(new Font("SansSerif", Font.PLAIN, 14));

    JScrollPane scroll = new JScrollPane(txtFeedback);
    scroll.setBounds(50, 478, 450, 150);
    scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
    cardPanel.add(scroll);

    add(sideBar);
    add(mainContent);

    btnSubmit.addActionListener(e-> {
        JOptionPane.showMessageDialog(this, "Review for " + name + " has been successfully submitted.", "Success", JOptionPane.INFORMATION_MESSAGE);
        new ManagerFrameReview();
        dispose();
    });

    btnBack.addActionListener(e-> {
        new ManagerFrameReview();
        dispose();
    });

    setVisible(true);

    SwingUtilities.invokeLater(()-> {
        tableScroll.getVerticalScrollBar().setValue(0);
    });
}

private int addCategoryHeader(JPanel parent, String title, int y)
{
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

private void addRatingRow(JPanel parent, String criteriaText, int y)
{
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
    String[] labels = { "1", "2", "3", "4", "5" };

    int startX = 4;
    int buttonWidth = 38;
    int buttonHeight = 30;
    int startY = 10;

    for (int i = 0; i < labels.length; i++)
    {
        JRadioButton rb = new JRadioButton(labels[i]);
        rb.setFont(new Font("SansSerif", Font.PLAIN, 10));
        rb.setBackground(Color.WHITE);
        rb.setFocusPainted(false);

        rb.setBounds(startX + (i * buttonWidth), startY, buttonWidth, buttonHeight);

        group.add(rb);
        rbPanel.add(rb);
    }
    parent.add(rbPanel);

    JPanel line = new JPanel();
    line.setBackground(new Color(220, 220, 220));
    line.setBounds(0, y + 54, 450, 1);
    parent.add(line);

    parent.setPreferredSize(new Dimension(430, y + 55));
}

private void styleButton(JButton btn, Color bg)
{
    btn.setBackground(bg);
    btn.setForeground(Color.WHITE);
    btn.setFocusPainted(false);
    btn.setBorderPainted(false);
    btn.setFont(new Font("SansSerif", Font.BOLD, 15));
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
}
}
