package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

public class EmployeeFrameReviewView extends JFrame implements ActionListener {

    private final Color ACCENT_BLUE = new Color(52, 152, 219);
    private final Color DANGER_RED = new Color(231, 76, 60);
    private final Color SIDEBAR_BG = new Color(34, 45, 57);
    private final Color sidebarDarkGray = new Color(33, 47, 61);
    private final Color BACKGROUND_TEXT_COLOR = new Color(44, 62, 80);

    private JPanel sideBar, mainContent;
    private JTable reviewsTable;
    private JButton btnSignOut, btnBack;
    private JTextField txtSearch;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private DefaultTableModel model;

    private String currentUserId;
    private String currentUserName;
    private final String CURRENT_ROLE = "Employee";

    public EmployeeFrameReviewView(String loggedInUserId, String loggedInUserName) {
        this.currentUserId = loggedInUserId;
        this.currentUserName = loggedInUserName;

        initializeLayout();
        loadReviewsFromDatabase();
    }

    private void initializeLayout() {
        setTitle("StaffSync - Employee - My Performance Reviews");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        try {
            setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());
        } catch (Exception ex) {
            System.err.println("Warning: Taskbar mini icon failed to load. " + ex.getMessage());
        }

        
        sideBar = new JPanel();
        sideBar.setBackground(SIDEBAR_BG);
        sideBar.setBounds(0, 0, 260, 1000);
        sideBar.setLayout(null);
        add(sideBar);

        try {
            ImageIcon rawIcon = new ImageIcon("src\\main\\java\\images\\pfp.png");
            Image scaledImg = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon finalAvatar = new ImageIcon(scaledImg);

            JLabel lblProfilePic = new JLabel(finalAvatar);
            lblProfilePic.setBounds(80, 30, 100, 100);
            lblProfilePic.setBorder(new LineBorder(new Color(255, 255, 255, 50), 2));
            sideBar.add(lblProfilePic);
        } catch (Exception ex) {
            System.err.println("Warning: Sidebar avatar image missing. " + ex.getMessage());
        }

        JLabel lblUser = new JLabel(CURRENT_ROLE + " | " + currentUserName, SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setBounds(20, 140, 220, 25);
        sideBar.add(lblUser);

        try {
            JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
            lblLogo.setBounds(66, 185, 128, 128);
            lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
            sideBar.add(lblLogo);
        } catch (Exception ex) {
            System.err.println("Warning: Branding logo failed to initialize. " + ex.getMessage());
        }

        int buttonWidth = 200;
        int buttonHeight = 45;
        int cornerRadius = 20;

        btnBack = new RoundedButton("← Back to Dashboard", cornerRadius);
        btnBack.setBounds(30, 340, buttonWidth, buttonHeight);
        btnBack.setBackground(ACCENT_BLUE);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.addActionListener(this);
        sideBar.add(btnBack);

        btnSignOut = new RoundedButton("Sign out →", cornerRadius);
        btnSignOut.setBounds(40, 880, 180, buttonHeight);
        btnSignOut.setBackground(new Color(192, 57, 43));
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSignOut.addActionListener(this);
        sideBar.add(btnSignOut);

        mainContent = new JPanel();
        mainContent.setBackground(new Color(245, 245, 245));
        mainContent.setLayout(null);
        mainContent.setBounds(260, 0, 740, 1000);
        add(mainContent);

        JLabel lblHeaderMeta = new JLabel("My Performance Evaluation History");
        lblHeaderMeta.setBounds(30, 35, 400, 32);
        lblHeaderMeta.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeaderMeta.setForeground(BACKGROUND_TEXT_COLOR);
        mainContent.add(lblHeaderMeta);

        txtSearch = new JTextField(" Search reviews...");
        txtSearch.setBounds(430, 35, 280, 32);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(215, 219, 221), 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));

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

        String[] columns = {
            "Manager/HR Evaluation Comments",
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
        reviewsTable.setRowHeight(85);
        reviewsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        reviewsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));

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

        reviewsTable.getTableHeader().setBackground(sidebarDarkGray);
        reviewsTable.getTableHeader().setForeground(Color.WHITE);
        reviewsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        Dimension headerSize = reviewsTable.getTableHeader().getPreferredSize();
        headerSize.height = 50;
        reviewsTable.getTableHeader().setPreferredSize(headerSize);

        reviewsTable.setSelectionBackground(new Color(52, 152, 219, 30));
        reviewsTable.setSelectionForeground(Color.BLACK);
        reviewsTable.setShowVerticalLines(false);
        reviewsTable.setGridColor(new Color(230, 233, 235));

        reviewsTable.getColumnModel().getColumn(0).setCellRenderer(new MultiLineCellRenderer());

        reviewsTable.getColumnModel().getColumn(0).setPreferredWidth(310);
        reviewsTable.getColumnModel().getColumn(1).setPreferredWidth(90);
        reviewsTable.getColumnModel().getColumn(2).setPreferredWidth(110);
        reviewsTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        reviewsTable.getColumnModel().getColumn(4).setPreferredWidth(90);

        JScrollPane scrollPane = new JScrollPane(reviewsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(30, 95, 680, 835);
        scrollPane.setBorder(new LineBorder(new Color(215, 219, 221), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainContent.add(scrollPane);

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
                pstmt.setString(1, currentUserId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getString("details"),
                            rs.getObject("behavior") != null ? rs.getObject("behavior") : "N/A",
                            rs.getObject("communication") != null ? rs.getObject("communication") : "N/A",
                            rs.getObject("management") != null ? rs.getObject("management") : "N/A",
                            rs.getObject("development") != null ? rs.getObject("development") : "N/A"
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "SQL Data Binding Error.\nDetails: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An unexpected data parsing error occurred: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g2.setColor(getBackground().brighter());
            } else {
                g2.setColor(getBackground());
            }
            
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnSignOut) {
                dispose();
                new LoginFrame();
            } else if (e.getSource() == btnBack) {
                dispose();
                new EmployeeFrame(currentUserId, currentUserName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Navigation processing route error: " + ex.getMessage(), "System Interface Crash", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setMargin(new Insets(5, 5, 5, 5));
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setBorder(new EmptyBorder(5, 5, 5, 5));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            setText(value != null ? value.toString() : "");
            return this;
        }
    }
}