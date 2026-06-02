
package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

public class ManagerFrameReviewView extends JFrame implements ActionListener {

    private JPanel sideBar, mainContent;
    private JTable reviewsTable;
    private JButton btnSignOut, btnBack; 
    private JButton btnEmpRecords, btnEmpRequests;
    private JTextField txtSearch;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private DefaultTableModel model;
    
    private String targetEmployeeId;
    private String targetEmployeeName;
    private String targetPosition;

    public ManagerFrameReviewView(String employeeId, String employeeName, String position) {
        this.targetEmployeeId = employeeId;
        this.targetEmployeeName = employeeName;
        this.targetPosition = position;
        
        initializeLayout();
        loadReviewsFromDatabase(); 
    }

    private void initializeLayout() {
        setTitle("StaffSync - Manager - View Employee Reviews");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

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

        btnBack = createStyledBtn("← Employee List", 340, new Color(52, 152, 219));
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

        JLabel lblHeaderMeta = new JLabel("Reviews For: " + targetEmployeeName + " (" + targetPosition + ")");
        lblHeaderMeta.setBounds(30, 95, 400, 32);
        lblHeaderMeta.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblHeaderMeta.setForeground(sidebarDarkGray);
        mainContent.add(lblHeaderMeta);

        txtSearch = new JTextField(" Search reviews...");
        txtSearch.setBounds(440, 95, 280, 32);
        txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals(" Search reviews...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setText(" Search reviews...");
                    txtSearch.setForeground(Color.GRAY);
                }
            }
        });
        mainContent.add(txtSearch);

        // --- USING HTML FOR MULTI-LINE HEADERS ---
        String[] columns = {
            "Detailed Comments", 
            "<html><center>Behavior<br>Score</center></html>", 
            "<html><center>Communication<br>Score</center></html>", 
            "<html><center>Management<br>Score</center></html>", 
            "<html><center>Development<br>Score</center></html>"
        };
        
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        reviewsTable = new JTable(model);
        
        // Increase row height to allow up to 4 lines of text in the comments
        reviewsTable.setRowHeight(85); 
        
        // Disable auto resizing so our specific column widths are respected
        reviewsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        
        tableSorter = new TableRowSorter<>(model);
        reviewsTable.setRowSorter(tableSorter);
        
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String targetText = txtSearch.getText();
                if (targetText.equals(" Search reviews...") || targetText.trim().isEmpty()) {
                    tableSorter.setRowFilter(null);
                } else {
                    tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(targetText.trim())));
                }
            }
        });

        // Set Header Styling & Height
        reviewsTable.getTableHeader().setBackground(sidebarDarkGray);
        reviewsTable.getTableHeader().setForeground(Color.WHITE);
        reviewsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        
        // Make the header taller to fit the HTML breaks
        Dimension headerSize = reviewsTable.getTableHeader().getPreferredSize();
        headerSize.height = 50; 
        reviewsTable.getTableHeader().setPreferredSize(headerSize);

        reviewsTable.setSelectionBackground(new Color(52, 152, 219, 40));
        reviewsTable.setSelectionForeground(Color.BLACK);
        reviewsTable.setShowVerticalLines(true);
        reviewsTable.setGridColor(new Color(200, 200, 200));

        // APPLY CUSTOM RENDERER FOR TEXT WRAPPING IN COLUMN 0 (Detailed Comments)
        reviewsTable.getColumnModel().getColumn(0).setCellRenderer(new MultiLineCellRenderer());

        // SET SPECIFIC COLUMN WIDTHS (Total width available inside scrollPane is ~690)
        reviewsTable.getColumnModel().getColumn(0).setPreferredWidth(320); // Much wider for comments
        reviewsTable.getColumnModel().getColumn(1).setPreferredWidth(90);
        reviewsTable.getColumnModel().getColumn(2).setPreferredWidth(110);
        reviewsTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        reviewsTable.getColumnModel().getColumn(4).setPreferredWidth(90);

        JScrollPane scrollPane = new JScrollPane(reviewsTable);
        scrollPane.setBounds(30, 140, 690, 710);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        mainContent.add(scrollPane);

        add(sideBar);
        add(mainContent);
        setVisible(true);
    }

    private void loadReviewsFromDatabase() {
        model.setRowCount(0); 
        
        String query = "SELECT review_id, behavior, communication, management, development, details "
                     + "FROM employee_reviews "
                     + "WHERE employee_id = ? "
                     + "ORDER BY review_id DESC";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                throw new SQLException("Database connection link is unestablished.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, targetEmployeeId);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getString("details"),
                            rs.getBigDecimal("behavior"),
                            rs.getBigDecimal("communication"),
                            rs.getBigDecimal("management"),
                            rs.getBigDecimal("development")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "SQL Syntax Error: Check table data bindings.\nDetails: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An unexpected data parsing exception occurred: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnSignOut) {
                dispose();
                new LoginFrame();
            } else if (e.getSource() == btnBack || e.getSource() == btnEmpRequests) {
                dispose();
                new ManagerFrameReview(); 
            } else if (e.getSource() == btnEmpRecords) {
                dispose();
                new HRFrame(); 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Navigation processing route error: " + ex.getMessage(), "System Interface Crash", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- CUSTOM RENDERER CLASS FOR TEXT WRAPPING ---
    private class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setMargin(new Insets(5, 5, 5, 5)); // Adds a little padding inside the cell
            setFont(new Font("SansSerif", Font.PLAIN, 13));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            // Handle row colors (Selected vs Normal)
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }
            
            // Set the text from the database
            setText(value != null ? value.toString() : "");
            
            return this;
        }
    }
}