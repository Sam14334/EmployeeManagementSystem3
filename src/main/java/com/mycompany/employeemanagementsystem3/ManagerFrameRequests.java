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
    private JButton btnViewDetails, btnApprove, btnDeny, btnSignOut, btnViewMyReviews;

    private JTextField txtSearch;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private DefaultTableModel model;

    private String currentUserId;
    private String currentUserName;

    public ManagerFrameRequests(String loggedInUserId, String loggedInUserName) {
        this.currentUserId = loggedInUserId;
        this.currentUserName = loggedInUserName;

        setTitle("StaffSync - Manager - Process Requests");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        try {
            setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());
        } catch (Exception ex) {
            System.err.println("Warning: Taskbar icon failed to initialize.");
        }

        sideBar = new JPanel();
        sideBar.setBackground(new Color(33, 47, 61));
        sideBar.setBounds(0, 0, 250, 1000);
        sideBar.setLayout(null);
        add(sideBar);

        try {
            ImageIcon rawIcon = new ImageIcon("src\\main\\java\\images\\pfp.png");
            Image scaledImg = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel lblProfilePic = new JLabel(new ImageIcon(scaledImg));
            lblProfilePic.setBounds(80, 30, 100, 100);
            lblProfilePic.setBorder(new LineBorder(new Color(255, 255, 255, 50), 2));
            sideBar.add(lblProfilePic);
        } catch (Exception ex) {
            System.err.println("Warning: Sidebar avatar missing.");
        }

        JLabel lblUser = new JLabel("Manager | " + currentUserName, SwingConstants.CENTER);
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
            System.err.println("Warning: Secondary branding asset missing.");
        }

         
        btnViewDetails = createStyledBtn("View Details", 340, new Color(52, 152, 219));  
        sideBar.add(btnViewDetails);

        btnApprove = createStyledBtn("Approve Request", 400, new Color(40, 167, 69));  
        sideBar.add(btnApprove);

        btnDeny = createStyledBtn("Deny Request", 460, new Color(231, 76, 60));  
        sideBar.add(btnDeny);
        
        btnViewMyReviews = createStyledBtn("View My Reviews", 520, new Color(52, 152, 219));  
        sideBar.add(btnViewMyReviews);
         

        btnSignOut = new JButton("Sign out →");
        btnSignOut.setBounds(35, 890, 180, 50);
        btnSignOut.setBackground(Color.RED);
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSignOut.setFocusPainted(false);
        btnSignOut.setBorderPainted(false);
        btnSignOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

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

        String[] columns = {"ID", "Employee Name", "Request Type", "Description", "Status", "Notes"};

        model = new DefaultTableModel(columns, 0) {
            @Override
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
        requestTable.setSelectionForeground(Color.BLACK);
        requestTable.setShowVerticalLines(false);
        requestTable.setShowHorizontalLines(true);

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
                    tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(text.trim())));
                }
            }
        });

        requestTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    executeViewAction();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.setBounds(30, 165, 690, 750);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        mainContent.add(scrollPane);

        loadRequestsFromDB();
        setVisible(true);
    }

    public void loadRequestsFromDB() {
        model.setRowCount(0);
        String query = "SELECT r.request_id, e.first_name, e.last_name, r.request_type, r.description, r.status, r.notes "
                + "FROM employee_requests r "
                + "INNER JOIN employees e ON r.employee_id = e.employee_id "
                + "ORDER BY r.request_id DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

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
            JOptionPane.showMessageDialog(this, "Could not load data entries: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executeViewAction() {
        int viewRow = requestTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request entry from the table workspace to view.", "Selection Missing", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = requestTable.convertRowIndexToModel(viewRow);

        String reqId = requestTable.getModel().getValueAt(modelRow, 0).toString();
        String empName = requestTable.getModel().getValueAt(modelRow, 1).toString();
        String reqType = requestTable.getModel().getValueAt(modelRow, 2).toString();
        String desc = requestTable.getModel().getValueAt(modelRow, 3).toString();
        String status = requestTable.getModel().getValueAt(modelRow, 4).toString();
        String notes = requestTable.getModel().getValueAt(modelRow, 5).toString();

        new ViewRequestDetailsFrame(this, reqId, empName, reqType, desc, status, notes);
    }

    private void prepareProcessWindow(String actionType) {
        int viewRow = requestTable.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an active record entry from the list workspace template.", "Selection Missing", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = requestTable.convertRowIndexToModel(viewRow);
        String currentStatus = requestTable.getModel().getValueAt(modelRow, 4).toString();

        if (currentStatus.equals("Approved") || currentStatus.equals("Denied")) {
            JOptionPane.showMessageDialog(this, "This transaction tracking record has already completed processing authorization routines.", "Action Prohibited", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String requestId = requestTable.getModel().getValueAt(modelRow, 0).toString();
        String empName = requestTable.getModel().getValueAt(modelRow, 1).toString();
        String type = requestTable.getModel().getValueAt(modelRow, 2).toString();

        new ProcessRequestFrame(this, requestId, empName, type, actionType);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnViewDetails) {
            executeViewAction();
        } else if (e.getSource() == btnApprove) {
            prepareProcessWindow("Approved");
        } else if (e.getSource() == btnDeny) {
            prepareProcessWindow("Denied");
        } else if (e.getSource() == btnSignOut) {
            dispose();
            new LoginFrame();
        } else if (e.getSource() == btnViewMyReviews) {
            dispose();
             new ManagerFrameRequestsView(currentUserId, currentUserName);
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
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);  
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addActionListener(this);
        return b;
    }
}