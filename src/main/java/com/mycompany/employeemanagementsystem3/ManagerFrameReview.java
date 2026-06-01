package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableColumnModel;

public class ManagerFrameReview extends JFrame implements ActionListener {

    private JPanel sideBar, mainContent;
    private JTable employeeTable;
    private JButton btnSignOut, btnReview; 
    private JButton btnEmpRecords, btnEmpRequests;
    private JTextField txtSearch;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private DefaultTableModel model;

    public ManagerFrameReview() {
        initializeLayout();
        loadLiveDatabaseRows(); 
        hideUnnecessaryColumns();
    }

    public ManagerFrameReview(HRFrame hrSource) {
        initializeLayout();
        if (hrSource != null && hrSource.getTableModel() != null) {
            DefaultTableModel hrModel = hrSource.getTableModel();
            for (int i = 0; i < hrModel.getRowCount(); i++) {
                Object[] rowData = new Object[hrModel.getColumnCount()];
                for (int col = 0; col < hrModel.getColumnCount(); col++) {
                    rowData[col] = hrModel.getValueAt(i, col);
                }
                model.addRow(rowData);
            }
        } else {
            loadLiveDatabaseRows(); // Fallback to safe DB load if source frame instance is empty
        }
        hideUnnecessaryColumns();
    }

    private void initializeLayout() {
        setTitle("StaffSync - Manager - Employee List");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // Safely set frame micro-icon asset
        try {
            setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());
        } catch (Exception ex) {
            System.err.println("Warning: Taskbar mini icon failed to load. " + ex.getMessage());
        }

        // --- SIDEBAR NAVIGATION ---
        sideBar = new JPanel();
        sideBar.setBackground(new Color(33, 47, 61));
        sideBar.setBounds(0, 0, 250, 1000);
        sideBar.setLayout(null);

        try {
            ImageIcon rawIcon = new ImageIcon("src\\main\\java\\images\\karlo.png");
            Image scaledImg = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon finalAvatar = new ImageIcon(scaledImg);

            JLabel lblProfilePic = new JLabel(finalAvatar);
            lblProfilePic.setBounds(80, 30, 100, 100);
            lblProfilePic.setBorder(new LineBorder(new Color(255, 255, 255, 50), 2));
            sideBar.add(lblProfilePic);
        } catch (Exception ex) {
            System.err.println("Warning: Sidebar avatar image missing. " + ex.getMessage());
        }

        JLabel lblUser = new JLabel("Review Manager | Karlo", SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setBounds(30, 140, 200, 25);
        sideBar.add(lblUser);

        try {
            JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
            lblLogo.setBounds(66, 185, 128, 128);
            lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
            sideBar.add(lblLogo);
        } catch (Exception ex) {
            System.err.println("Warning: Branding logo failed to initialize. " + ex.getMessage());
        }

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
                    // Safe injection regex escape parsing filter execution
                    tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(targetText.trim())));
                }
            }
        });

        employeeTable.getTableHeader().setBackground(sidebarDarkGray);
        employeeTable.getTableHeader().setForeground(Color.WHITE);
        employeeTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        employeeTable.setSelectionBackground(new Color(52, 152, 219, 40));
        employeeTable.setSelectionForeground(Color.BLACK);
        employeeTable.setShowVerticalLines(false);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBounds(30, 140, 690, 710);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        mainContent.add(scrollPane);

        employeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    executeReviewAction();
                }
            }
        });

        add(sideBar);
        add(mainContent);
        setVisible(true);
    }

    private void loadLiveDatabaseRows() {
        model.setRowCount(0); 
        String query = "SELECT e.employee_id, e.username, e.password, e.first_name, e.last_name, "
                     + "e.email, e.phone_number, d.dept_name, e.role, s.status_name, e.salary "
                     + "FROM employees e "
                     + "LEFT JOIN departments d ON e.dept_id = d.dept_id "
                     + "LEFT JOIN employment_statuses s ON e.status_id = s.status_id "
                     + "ORDER BY e.employee_id ASC";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                throw new SQLException("Database pipeline reference configuration channel is empty.");
            }
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("employee_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("dept_name") != null ? rs.getString("dept_name") : "[No Dept]",
                        rs.getString("role"),
                        rs.getString("status_name") != null ? rs.getString("status_name") : "[No Status]",
                        String.format("%,.0f", rs.getDouble("salary"))
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Communications failure: Could not sync live tracking list.\nDetails: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An unexpected data parsing layout crash occurred: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executeReviewAction() {
        int viewRow = employeeTable.getSelectedRow();
        if (viewRow != -1) {
            try {
                // Secures model conversion to read true identity mappings after filtering searches
                int modelRow = employeeTable.convertRowIndexToModel(viewRow);
                
                Object fnObj = employeeTable.getModel().getValueAt(modelRow, 3);
                Object lnObj = employeeTable.getModel().getValueAt(modelRow, 4);
                Object posObj = employeeTable.getModel().getValueAt(modelRow, 8);
                
                String firstName = fnObj != null ? fnObj.toString() : "";
                String lastName = lnObj != null ? lnObj.toString() : "";
                String name = (firstName + " " + lastName).trim();
                String pos = posObj != null ? posObj.toString() : "Employee";
                
                if (name.isEmpty()) name = "Unknown Employee";

                dispose();
                new ManagerFrameReviewPerf(name, "N/A", "N/A", pos);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to instantiate the tracking target interface screen: " + ex.getMessage(), "Execution Failure", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an active employee entry from the table workspace list to review.", "Selection Missing", JOptionPane.WARNING_MESSAGE);
        }
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

    private void hideUnnecessaryColumns() {
        TableColumnModel colModel = employeeTable.getColumnModel();
        String[] columnsToHide = {"Username", "Password", "Email", "Phone Number", "Employment Status"};
        
        for (String targetHeader : columnsToHide) {
            try {
                // Linear verification tracking to safely isolate indices without layout offset shifts
                int index = colModel.getColumnIndex(targetHeader);
                colModel.removeColumn(colModel.getColumn(index));
            } catch (IllegalArgumentException ex) {
                // Handled gracefully: Column was already stripped or does not exist
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnSignOut) {
                dispose();
                new LoginFrame();
            } else if (e.getSource() == btnEmpRecords) {
                dispose();
                new HRFrame(); 
            } else if (e.getSource() == btnReview) {
                executeReviewAction();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Navigation processing route error: " + ex.getMessage(), "System Interface Crash", JOptionPane.ERROR_MESSAGE);
        }
    }
}