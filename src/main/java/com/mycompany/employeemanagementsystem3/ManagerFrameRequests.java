package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

public class ManagerFrameRequests extends JFrame implements ActionListener {

    private JPanel sideBar, mainContent;
    private JTable requestTable;
    private JButton btnApprove, btnDeny, btnSignOut;

    private JTextField txtSearch;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private DefaultTableModel model;

    // Fields to store session information
    private String currentUserId;
    private String currentUserName;

    public ManagerFrameRequests(String loggedInUserId, String loggedInUserName) {
        this.currentUserId = loggedInUserId;
        this.currentUserName = loggedInUserName;

        setTitle("StaffSync - Manager - Process Requests");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        sideBar = new JPanel();
        sideBar.setBackground(new Color(33, 47, 61));
        sideBar.setBounds(0, 0, 250, 1000);
        sideBar.setLayout(null);
        add(sideBar);

        ImageIcon rawIcon = new ImageIcon("src\\main\\java\\images\\pfp.png");
        Image scaledImg = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel lblProfilePic = new JLabel(new ImageIcon(scaledImg));
        lblProfilePic.setBounds(80, 30, 100, 100);
        lblProfilePic.setBorder(new LineBorder(new Color(255, 255, 255, 50), 2));
        sideBar.add(lblProfilePic);

        JLabel lblUser = new JLabel("Manager | " + currentUserName, SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setBounds(30, 140, 200, 25);
        sideBar.add(lblUser);

        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setBounds(66, 185, 128, 128);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        sideBar.add(lblLogo);

        btnApprove = new JButton("Approve Request");
        btnApprove.setBounds(35, 340, 180, 45);
        btnApprove.setBackground(new Color(40, 167, 69));
        btnApprove.setForeground(Color.WHITE);
        btnApprove.setBorderPainted(false);
        btnApprove.addActionListener(this);
        sideBar.add(btnApprove);

        btnDeny = new JButton("Deny Request");
        btnDeny.setBounds(35, 400, 180, 45);
        btnDeny.setBackground(new Color(231, 76, 60));
        btnDeny.setForeground(Color.WHITE);
        btnDeny.setBorderPainted(false);
        btnDeny.addActionListener(this);
        sideBar.add(btnDeny);

        btnSignOut = new JButton("Sign out →");
        btnSignOut.setBounds(35, 890, 180, 50);
        btnSignOut.setBackground(Color.RED);
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.setBorderPainted(false);
        btnSignOut.addActionListener(this);
        sideBar.add(btnSignOut);

        mainContent = new JPanel();
        mainContent.setBackground(new Color(245, 245, 245));
        mainContent.setLayout(null);
        mainContent.setBounds(250, 0, 750, 1000);
        add(mainContent);

        JLabel lblTitle = new JLabel("Employee Requests");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitle.setBounds(30, 90, 300, 40);
        mainContent.add(lblTitle);

        txtSearch = new JTextField(" Search requests...");
        txtSearch.setBounds(430, 100, 290, 32);
        txtSearch.setForeground(Color.GRAY);

        txtSearch.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals(" Search requests...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setText(" Search requests...");
                    txtSearch.setForeground(Color.GRAY);
                }
            }
        });

        mainContent.add(txtSearch);

        // MODIFIED: Replaced Date Submitted with Description so managers can read it!
        String[] columns = {"ID", "Employee Name", "Request Type", "Description", "Status", "Notes"};
        
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        requestTable = new JTable(model);
        requestTable.setRowHeight(45);

        tableSorter = new TableRowSorter<>(model);
        requestTable.setRowSorter(tableSorter);

        requestTable.getTableHeader().setBackground(new Color(33, 47, 61));
        requestTable.getTableHeader().setForeground(Color.WHITE);
        requestTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        requestTable.setSelectionBackground(new Color(52, 152, 219, 40));
        requestTable.setShowVerticalLines(false);
        requestTable.setShowHorizontalLines(true);
        requestTable.setBorder(null);

        // Adjust column widths for better readability
        requestTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        requestTable.getColumnModel().getColumn(1).setPreferredWidth(130);
        requestTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        requestTable.getColumnModel().getColumn(3).setPreferredWidth(180);
        requestTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        requestTable.getColumnModel().getColumn(5).setPreferredWidth(150);

        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String text = txtSearch.getText();

                if (text.equals(" Search requests...") || text.trim().isEmpty()) {
                    tableSorter.setRowFilter(null);
                } else {
                    tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text.trim()));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.setBounds(30, 165, 690, 750);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));

        mainContent.add(scrollPane);

        // Load data from DB on startup
        loadRequestsFromDB();

        setVisible(true);
    }

    // --- NEW: Load from Database ---
    private void loadRequestsFromDB() {
        model.setRowCount(0); 
        
        String query = "SELECT r.request_id, e.first_name, e.last_name, r.request_type, r.description, r.status, r.notes "
                     + "FROM employee_requests r "
                     + "INNER JOIN employees e ON r.employee_id = e.employee_id "
                     + "ORDER BY r.request_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String fullName = rs.getString("first_name") + " " + rs.getString("last_name");
                
                model.addRow(new Object[]{
                    rs.getInt("request_id"),
                    fullName.trim(),
                    rs.getString("request_type"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getString("notes") != null ? rs.getString("notes") : "-"
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not load requests: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- NEW: Update Database Status ---
    private void processRequest(String newStatus, String noteInputMsg) {
        int viewRow = requestTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a request from the table first.");
            return;
        }

        int row = requestTable.convertRowIndexToModel(viewRow);
        String currentStatus = requestTable.getModel().getValueAt(row, 4).toString();

        if (currentStatus.equals("Approved") || currentStatus.equals("Denied")) {
            JOptionPane.showMessageDialog(this, "This request has already been processed.");
            return;
        }

        String note = JOptionPane.showInputDialog(this, noteInputMsg);
        if (note != null && !note.trim().isEmpty()) {
            
            String reqId = requestTable.getModel().getValueAt(row, 0).toString();
            String sql = "UPDATE employee_requests SET status = ?, notes = ? WHERE request_id = ?";
            
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, newStatus);
                pstmt.setString(2, note);
                pstmt.setString(3, reqId);
                
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Request successfully " + newStatus + "!");
                
                loadRequestsFromDB(); // Refresh table
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database update failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnApprove) {
            processRequest("Approved", "Enter approval note:");
        }

        if (e.getSource() == btnDeny) {
            processRequest("Denied", "Enter denial reason:");
        }

        if (e.getSource() == btnSignOut) {
            dispose();
            new LoginFrame(); 
        }
    }
}