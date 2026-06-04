package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HRFrame extends JFrame implements ActionListener {

    private JPanel sideBar, mainContent;
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtSearch;
    private JButton btnAdd, btnEdit, btnDelete, btnSignOut;
    private JButton btnEmpRecords, btnEmpRequests;

    // Stored credentials to pass to other frames
    private String loggedInUserId;
    private String loggedInUsername;

    public HRFrame(String userId, String username) {
        this.loggedInUserId = userId;
        this.loggedInUsername = username;

        setTitle("StaffSync - HR Dashboard");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        
        // Wrapped icon assignment with local try-catch to prevent asset missing crashes
        try {
            setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());
        } catch (Exception ex) {
            System.err.println("Warning: System frame micro-icon asset missing. " + ex.getMessage());
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
            System.err.println("Warning: Profile picture graphic missing. " + ex.getMessage());
        }

        // Optionally displays the logged in username on the sidebar
        JLabel lblUser = new JLabel("HR Manager | " + this.loggedInUsername, SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setBounds(30, 140, 200, 25);
        sideBar.add(lblUser);

        try {
            JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
            lblLogo.setBounds(66, 185, 128, 128); 
            sideBar.add(lblLogo);
        } catch (Exception ex) {
            System.err.println("Warning: System logo asset failed to initialize. " + ex.getMessage());
        }

        btnAdd = createStyledBtn("+ Add Employee", 340, new Color(52, 152, 219));
        btnEdit = createStyledBtn("✎ Edit Employee", 400, new Color(52, 152, 219));
        btnDelete = createStyledBtn("🗑 Delete Record", 460, new Color(231, 76, 60));
        
        sideBar.add(btnAdd);
        sideBar.add(btnEdit);
        sideBar.add(btnDelete);

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
        
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String term = txtSearch.getText().trim();
                if (!term.equals("Search records...")) {
                    loadDatabaseData(term);
                }
            }
        });

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
                    loadDatabaseData(""); 
                }
            }
        });
        mainContent.add(txtSearch);

        // --- UNIFIED 11-COLUMN CONFIGURATION ---
        String[] cols = {
            "ID", "Username", "Password", "First Name", "Last Name", 
            "Email", "Phone Number", "Department", "Role", "Employment Status", "Salary"
        };
        
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setRowHeight(45);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(52, 152, 219, 40));
        table.setSelectionForeground(Color.BLACK);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 

        JTableHeader header = table.getTableHeader();
        header.setBackground(sidebarDarkGray);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 13));

        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBounds(40, 140, 680, 710);
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.getViewport().setBackground(new Color(245, 245, 245));
        mainContent.add(pane);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(110);
        }

        // Initialize table dataset
        loadDatabaseData("");

        add(sideBar);
        add(mainContent);
        setVisible(true);
    }

    public void loadDatabaseData(String keyword) {
        model.setRowCount(0); 
        String query = "SELECT e.employee_id, e.username, e.password, e.first_name, e.last_name, "
                     + "e.email, e.phone_number, d.dept_name, e.role, s.status_name, e.salary "
                     + "FROM employees e "
                     + "LEFT JOIN departments d ON e.dept_id = d.dept_id "
                     + "LEFT JOIN employment_statuses s ON e.status_id = s.status_id ";
        
        if (!keyword.isEmpty()) {
            query += "WHERE e.employee_id LIKE ? OR e.first_name LIKE ? OR e.last_name LIKE ? OR d.dept_name LIKE ?";
        }
        query += " ORDER BY e.employee_id ASC";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                throw new SQLException("Database connection configuration context tracking is offline.");
            }
            
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                if (!keyword.isEmpty()) {
                    String searchPattern = "%" + keyword + "%";
                    pstmt.setString(1, searchPattern);
                    pstmt.setString(2, searchPattern);
                    pstmt.setString(3, searchPattern);
                    pstmt.setString(4, searchPattern);
                }

                try (ResultSet rs = pstmt.executeQuery()) {
                    while(rs.next()) {
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
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showModernMsg("Communications link failure: Could not read employee entries.\n\nDetails: " + ex.getMessage(), "Database Connection Error");
        } catch (Exception ex) {
            ex.printStackTrace();
            showModernMsg("An unexpected data execution crash occurred: " + ex.getMessage(), "System Error");
        }
    }

    public DefaultTableModel getTableModel() {
        return this.model;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnEmpRequests) {
                dispose();
                // Passing the variables directly into the ManagerFrameReview instance
                new ManagerFrameReview(loggedInUserId, loggedInUsername); 
            } else if (e.getSource() == btnAdd) {
                new AddEmployeeFrame(this);
            } else if (e.getSource() == btnEdit) {
                int row = table.getSelectedRow();
                if (row != -1) handleEditForm(row);
                else showModernMsg("Please select an employee entry row from the data table to edit.", "Selection Missing");
            } else if (e.getSource() == btnDelete) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    Object idValue = model.getValueAt(row, 0);
                    if (idValue != null) {
                        String empId = idValue.toString();
                        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to permanently delete record ID: " + empId + "?", "Confirm Record Erasure", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (confirm == JOptionPane.YES_OPTION) {
                            deleteEmployeeFromDB(empId, row);
                        }
                    }
                } else showModernMsg("Please select an employee entry row from the data table to delete.", "Selection Missing");
            } else if (e.getSource() == btnSignOut) {
                dispose();
                new LoginFrame();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showModernMsg("Action navigation intercept failure: " + ex.getMessage(), "Navigation Error");
        }
    }

    private void deleteEmployeeFromDB(String empId, int viewRowIndex) {
        String query = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                throw new SQLException("Database interface endpoint connection lost.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, empId);
                pstmt.executeUpdate();
                model.removeRow(viewRowIndex);
                showModernMsg("Employee record deleted from storage successfully.", "Success");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showModernMsg("Transaction abort: Cannot delete record entry.\nDetails: " + ex.getMessage(), "Database Deletion Error");
        }
    }

    private void handleEditForm(int row) {
        // Fallback default checks for cell properties to avoid null conversions
        String empId = model.getValueAt(row, 0) != null ? model.getValueAt(row, 0).toString() : "";
        
        JTextField user = new JTextField(model.getValueAt(row, 1) != null ? model.getValueAt(row, 1).toString() : "");
        JTextField pass = new JTextField(model.getValueAt(row, 2) != null ? model.getValueAt(row, 2).toString() : "");
        JTextField fn = new JTextField(model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "");
        JTextField ln = new JTextField(model.getValueAt(row, 4) != null ? model.getValueAt(row, 4).toString() : "");
        JTextField email = new JTextField(model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "");
        JTextField phone = new JTextField(model.getValueAt(row, 6) != null ? model.getValueAt(row, 6).toString() : "");
        JTextField dept = new JTextField(model.getValueAt(row, 7) != null ? model.getValueAt(row, 7).toString() : "");
        JTextField role = new JTextField(model.getValueAt(row, 8) != null ? model.getValueAt(row, 8).toString() : "");
        JTextField status = new JTextField(model.getValueAt(row, 9) != null ? model.getValueAt(row, 9).toString() : "");
        JTextField sl = new JTextField(model.getValueAt(row, 10) != null ? model.getValueAt(row, 10).toString().replace(",", "") : "0");

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 8));
        panel.add(new JLabel("Username:")); panel.add(user);
        panel.add(new JLabel("Password:")); panel.add(pass);
        panel.add(new JLabel("First Name:")); panel.add(fn);
        panel.add(new JLabel("Last Name:")); panel.add(ln);
        panel.add(new JLabel("Email:")); panel.add(email);
        panel.add(new JLabel("Phone Number:")); panel.add(phone);
        panel.add(new JLabel("Department Name:")); panel.add(dept);
        panel.add(new JLabel("Role (HR Staff/Manager/Employee):")); panel.add(role);
        panel.add(new JLabel("Status (Regular/Contractual/Probationary):")); panel.add(status);
        panel.add(new JLabel("Salary:")); panel.add(sl);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(420, 420));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        int result = JOptionPane.showConfirmDialog(this, scrollPane, "Edit Structural Details - ID: " + empId, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            
            // 1. Text Field Input Presence Integrity Guards
            if (user.getText().trim().isEmpty() || fn.getText().trim().isEmpty() || ln.getText().trim().isEmpty()) {
                showModernMsg("Operation Cancelled: Username, First Name, and Last Name cannot be left blank.", "Input Validation Error");
                return;
            }

            // 2. Numerical Transformation Formatting Safeguard Block
            double parsedSalary = 0.00;
            try {
                parsedSalary = Double.parseDouble(sl.getText().trim().replace(",", ""));
                if (parsedSalary < 0) {
                    showModernMsg("Salary cannot evaluate to a negative metric value.", "Data Formatting Error");
                    return;
                }
            } catch (NumberFormatException ex) {
                showModernMsg("Please specify an unambiguous numeric value for the salary field (e.g., 30000).", "Input Format Warning");
                return;
            }

            String updateQuery = "UPDATE employees SET username=?, password=?, first_name=?, last_name=?, email=?, phone_number=?, "
                               + "dept_id=(SELECT dept_id FROM departments WHERE dept_name=? LIMIT 1), role=?, "
                               + "status_id=(SELECT status_id FROM employment_statuses WHERE status_name=? LIMIT 1), salary=? "
                               + "WHERE employee_id=?";
            
            // 3. Database Execution Attempt Block
            try (Connection conn = DBConnection.getConnection()) {
                if (conn == null) {
                    throw new SQLException("SQL server transaction channel could not be formed.");
                }
                
                try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                    pstmt.setString(1, user.getText().trim());
                    pstmt.setString(2, pass.getText().trim());
                    pstmt.setString(3, fn.getText().trim());
                    pstmt.setString(4, ln.getText().trim());
                    pstmt.setString(5, email.getText().trim());
                    pstmt.setString(6, phone.getText().trim());
                    pstmt.setString(7, dept.getText().trim());
                    pstmt.setString(8, role.getText().trim());
                    pstmt.setString(9, status.getText().trim());
                    pstmt.setDouble(10, parsedSalary);
                    pstmt.setString(11, empId);

                    int updatedRows = pstmt.executeUpdate();
                    if (updatedRows > 0) {
                        showModernMsg("Employee storage values modified successfully!", "Success");
                        loadDatabaseData(""); 
                    } else {
                        showModernMsg("Update dropped: Ensure your Department and Status entries perfectly match database lookups.", "Constraint Error");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Capture primary key/unique key duplicate exceptions (MySQL Code 1062)
                if (ex.getErrorCode() == 1062) {
                    showModernMsg("Data Conflict: The updated Username is already assigned to another user profile.", "Database Uniqueness Collision");
                } else {
                    showModernMsg("Database execution failed to commit structural state change: \n" + ex.getMessage(), "Database Transaction Error");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showModernMsg("An unhandled system state variation occurred: " + ex.getMessage(), "System Error");
            }
        }
    }

    private void showModernMsg(String msg, String title) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
}