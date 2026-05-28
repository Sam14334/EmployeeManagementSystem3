package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ManagerFrameReview extends JFrame implements ActionListener {

    private JPanel sideBar, mainContent;
    private JTable employeeTable;
    private JButton btnSignOut;
    private JButton btnEmpRecords, btnEmpRequests;
    private JTextField txtSearch;
    private TableRowSorter<DefaultTableModel> tableSorter;

    public ManagerFrameReview() {

        setTitle("StaffSync - Manager - Employee List");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

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

        btnSignOut = new JButton("Sign out →");
        btnSignOut.setBounds(35,890,180,50);
        btnSignOut.setBackground(Color.RED);
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.setFocusPainted(false);
        btnSignOut.setBorderPainted(false);
        btnSignOut.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSignOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSignOut.addActionListener(this);

        sideBar.add(btnSignOut);

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

        btnEmpRequests = new JButton("Employee Requests");
        btnEmpRequests.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnEmpRequests.setBackground(sidebarDarkGray);
        btnEmpRequests.setForeground(Color.WHITE);
        btnEmpRequests.setBounds(285, 30, 240, 45);
        btnEmpRequests.setFocusPainted(false);
        btnEmpRequests.setBorderPainted(false);
        btnEmpRequests.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEmpRequests.addActionListener(this);

        mainContent.add(btnEmpRequests);

        txtSearch = new JTextField(" Search records...");
        txtSearch.setBounds(440, 95, 280, 32);
        txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        txtSearch.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals(" Search records...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setText(" Search records...");
                    txtSearch.setForeground(Color.GRAY);
                }

            }

        });

        mainContent.add(txtSearch);

        String[] columns = {"ID", "First Name", "Last Name", "Department", "Position", "Salary", "Action"};

        String[][] data = {
            {"001", "Jomar N.", "Pangilinan", "Management", "Manager", "50,000", "Edit / Review"},
            {"002", "Karlo", "Alatiit", "Operations", "Supervisor", "40,000", "Edit / Review"},
            {"003", "Rich Jasper", "Federio", "Technical", "Staff", "30,000", "Edit / Review"},
            {"004", "Alice", "Guo", "Finance", "Accountant", "35,000", "Edit / Review"},
            {"005", "Bob", "Marley", "Logistics", "Driver", "25,000", "Edit / Review"}

        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(model);
        employeeTable.setRowHeight(45);
        tableSorter = new TableRowSorter<>(model);
        employeeTable.setRowSorter(tableSorter);
        txtSearch.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                String targetText = txtSearch.getText();
                if (targetText.equals(" Search records...") || targetText.trim().isEmpty()) {
                    tableSorter.setRowFilter(null);
                } else {
                    tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + targetText.trim()));
                }

            }

        });

        employeeTable.getTableHeader().setBackground(new Color(33, 47, 61));
        employeeTable.getTableHeader().setForeground(Color.WHITE);
        employeeTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        employeeTable.setSelectionBackground(new Color(52, 152, 219, 40));
        employeeTable.setShowVerticalLines(false);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBounds(30, 140, 690, 710);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));

        mainContent.add(scrollPane);

        employeeTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int viewRow = employeeTable.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = employeeTable.convertRowIndexToModel(viewRow);
                    String name = employeeTable.getModel().getValueAt(modelRow, 1).toString() + " " + employeeTable.getModel().getValueAt(modelRow, 2).toString();
                    String pos = employeeTable.getModel().getValueAt(modelRow, 4).toString();
                    new ManagerFrameReviewPerf(name, "N/A", "N/A", pos);
                    dispose();

                }

            }

        });

        add(sideBar);
        add(mainContent);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSignOut) {
            dispose();
            new LoginFrame();
        } else if (e.getSource() == btnEmpRecords) {
        } else if (e.getSource() == btnEmpRequests) {
            dispose();

            new ManagerFrameRequests();
        }

    }

}
