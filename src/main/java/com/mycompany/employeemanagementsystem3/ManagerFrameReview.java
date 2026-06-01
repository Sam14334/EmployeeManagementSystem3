package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableColumnModel;

public class ManagerFrameReview extends JFrame implements ActionListener {

    private JPanel sideBar, mainContent;
    private JTable employeeTable;
    private JButton btnSignOut, btnReview; // Added btnReview reference
    private JButton btnEmpRecords, btnEmpRequests;
    private JTextField txtSearch;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private DefaultTableModel model;

    public ManagerFrameReview() {
        initializeLayout();
        loadDefaultMasterRows();
        hideUnnecessaryColumns();
    }

    // Transfers running data arrays natively across frames with matching schemas
    public ManagerFrameReview(HRFrame hrSource) {
        initializeLayout();
        DefaultTableModel hrModel = hrSource.getTableModel();
        for (int i = 0; i < hrModel.getRowCount(); i++) {
            Object[] rowData = new Object[hrModel.getColumnCount()];
            for (int col = 0; col < hrModel.getColumnCount(); col++) {
                rowData[col] = hrModel.getValueAt(i, col);
            }
            model.addRow(rowData);
        }
        hideUnnecessaryColumns();
    }

    private void initializeLayout() {
        setTitle("StaffSync - Manager - Employee List");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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

        // --- ADDED SIDEBAR ACTION BUTTON ---
        btnReview = createStyledBtn("🔍 Review Employee", 340, new Color(52, 152, 219));
        sideBar.add(btnReview);

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

        // --- UNIFIED 11-COLUMN CONFIGURATION ---
        String[] columns = {
            "ID", "Username", "Password", "First Name", "Last Name", 
            "Email", "Phone Number", "Department", "Role", "Employment Status", "Salary"
        };
        
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(model);
        employeeTable.setRowHeight(45);
        
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 
        
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

        employeeTable.getTableHeader().setBackground(sidebarDarkGray);
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
                if (e.getClickCount() == 2) { // Optional Double Click support to trigger review
                    executeReviewAction();
                }
            }
        });

        add(sideBar);
        add(mainContent);
        setVisible(true);
    }

    /**
     * Helper to retrieve row selections safely and route to performance evaluations 
     */
    private void executeReviewAction() {
        int viewRow = employeeTable.getSelectedRow();
        if (viewRow != -1) {
            int modelRow = employeeTable.convertRowIndexToModel(viewRow);
            
            String name = employeeTable.getModel().getValueAt(modelRow, 3).toString() + " " + 
                          employeeTable.getModel().getValueAt(modelRow, 4).toString();
            String pos = employeeTable.getModel().getValueAt(modelRow, 8).toString();
            
            new ManagerFrameReviewPerf(name, "N/A", "N/A", pos);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee from the table list to review.", "No Selection Made", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Custom renderer method matching HR UI style conventions for buttons
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

    /**
     * Hides specific columns from the view while leaving them intact in the model.
     * This keeps the layout perfectly aligned but clean for easy selection.
     */
    private void hideUnnecessaryColumns() {
        TableColumnModel colModel = employeeTable.getColumnModel();
        String[] columnsToHide = {"Username", "Password", "Email", "Phone Number", "Employment Status"};
        
        for (String targetHeader : columnsToHide) {
            try {
                int index = colModel.getColumnIndex(targetHeader);
                colModel.removeColumn(colModel.getColumn(index));
            } catch (IllegalArgumentException ex) {
                // Column already hidden or not found
            }
        }
    }

    private void loadDefaultMasterRows() {
        model.addRow(new Object[]{"001", "jomar_p", "pass1", "Jomar N.", "Pangilinan", "jomar@staffsync.com", "09123456789", "Management", "Manager", "Regular", "50,000"});
        model.addRow(new Object[]{"002", "karlo_a", "pass2", "Karlo", "Alatiit", "karlo@staffsync.com", "09234567890", "Operations", "Supervisor", "Regular", "40,000"});
        model.addRow(new Object[]{"003", "rich_j", "pass3", "Rich Jasper", "Federio", "rich@staffsync.com", "09345678901", "Technical", "Staff", "Regular", "30,000"});
        model.addRow(new Object[]{"004", "alice_g", "pass4", "Alice", "Guo", "alice@staffsync.com", "09456789012", "Finance", "Accountant", "Regular", "35,000"});
        model.addRow(new Object[]{"005", "bob_m", "pass5", "Bob", "Marley", "bob@staffsync.com", "09567890123", "Logistics", "Driver", "Regular", "25,000"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSignOut) {
            dispose();
            new LoginFrame();
        } else if (e.getSource() == btnEmpRecords) {
            dispose();
            new HRFrame(); 
        } else if (e.getSource() == btnReview) {
            executeReviewAction();
        }
    }
}