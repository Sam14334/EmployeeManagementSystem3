package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class EmployeeFrame extends JFrame implements ActionListener {

    private final Color ACCENT_BLUE = new Color(52, 152, 219);
    private final Color DANGER_RED = new Color(231, 76, 60);
    private final Color SUCCESS_GREEN = new Color(46, 204, 113);
    private final Color sidebarDarkGray = new Color(33, 47, 61);
    private final Color SIDEBAR_BG = new Color(34, 45, 57);
    private final Color BACKGROUND_TEXT_COLOR = new Color(44, 62, 80);

    private JLabel lblERS, lblRequestHeading, lblrequestType, lbldescription;

    private String currentUserId;
    private String currentUserName;
    private final String CURRENT_DEPARTMENT = "Technical";
    private final String CURRENT_ROLE = "Employee";

    private JComboBox<String> cbrequest;
    private JTextArea txtDescription;
    private JButton btnSubmit, btnDelete, btnUpdate, btnSignout, btnViewDetails, btnViewReviews;
    private JTable table;
    private JScrollPane scroll, descScroll;
    private DefaultTableModel tableModel;
    private JPanel sideNav;

    public EmployeeFrame(String loggedInUserId, String loggedInUserName) {
        this.currentUserId = loggedInUserId;
        this.currentUserName = loggedInUserName;

        setTitle("StaffSync - Employee Request Dashboard");
        setSize(1000, 1000);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 245, 245));
        setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());

        sideNav = new JPanel();
        sideNav.setSize(260, 1000);
        sideNav.setBackground(SIDEBAR_BG);
        sideNav.setLayout(null);
        add(sideNav);

        ImageIcon rawIcon = new ImageIcon("src\\main\\java\\images\\pfp.png");
        Image scaledImg = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon finalAvatar = new ImageIcon(scaledImg);

        JLabel lblProfilePic = new JLabel(finalAvatar);
        lblProfilePic.setBounds(80, 30, 100, 100);
        lblProfilePic.setBorder(new LineBorder(new Color(255, 255, 255, 50), 2));
        sideNav.add(lblProfilePic);

        JLabel lblUser = new JLabel(CURRENT_ROLE + " | " + currentUserName, SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setBounds(20, 140, 220, 25);
        sideNav.add(lblUser);

        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setBounds(66, 185, 128, 128);
        sideNav.add(lblLogo);

        int buttonWidth = 200;
        int buttonHeight = 45;
        int cornerRadius = 20;

        btnSubmit = new RoundedButton("Submit Request", cornerRadius);
        btnSubmit.setBounds(30, 340, buttonWidth, buttonHeight);
        btnSubmit.setBackground(ACCENT_BLUE);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sideNav.add(btnSubmit);

        btnUpdate = new RoundedButton("Update Request", cornerRadius);
        btnUpdate.setBounds(30, 405, buttonWidth, buttonHeight);
        btnUpdate.setBackground(ACCENT_BLUE);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sideNav.add(btnUpdate);

        btnViewDetails = new RoundedButton("View Details", cornerRadius);
        btnViewDetails.setBounds(30, 470, buttonWidth, buttonHeight);
        btnViewDetails.setBackground(ACCENT_BLUE);
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sideNav.add(btnViewDetails);

        btnViewReviews = new RoundedButton("Performance Reviews", cornerRadius);
        btnViewReviews.setBounds(30, 535, buttonWidth, buttonHeight);
        btnViewReviews.setBackground(SUCCESS_GREEN);
        btnViewReviews.setForeground(Color.WHITE);
        btnViewReviews.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sideNav.add(btnViewReviews);

        btnDelete = new RoundedButton("Delete Request", cornerRadius);
        btnDelete.setBounds(30, 600, buttonWidth, buttonHeight);
        btnDelete.setBackground(DANGER_RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sideNav.add(btnDelete);

        btnSignout = new RoundedButton("Sign out →", cornerRadius);
        btnSignout.setBounds(40, 880, 180, 45);
        btnSignout.setBackground(new Color(192, 57, 43));
        btnSignout.setForeground(Color.WHITE);
        btnSignout.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sideNav.add(btnSignout);

        lblERS = new JLabel("Employee Request System");
        lblERS.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblERS.setForeground(BACKGROUND_TEXT_COLOR);
        lblERS.setBounds(300, 25, 500, 40);
        add(lblERS);

        lblRequestHeading = new JLabel("Create New Request Ticket");
        lblRequestHeading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblRequestHeading.setForeground(new Color(127, 140, 141));
        lblRequestHeading.setBounds(300, 85, 300, 30);
        add(lblRequestHeading);

        lblrequestType = new JLabel("Request Type:");
        lblrequestType.setBounds(300, 140, 120, 30);
        lblrequestType.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblrequestType.setForeground(BACKGROUND_TEXT_COLOR);
        add(lblrequestType);

        String[] types = {"", "Leave", "Overtime", "Expenses"};
        cbrequest = new JComboBox<>(types);
        cbrequest.setBounds(430, 140, 220, 32);
        cbrequest.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbrequest.setBackground(Color.WHITE);
        add(cbrequest);

        lbldescription = new JLabel("Description:");
        lbldescription.setBounds(300, 190, 120, 30);
        lbldescription.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbldescription.setForeground(BACKGROUND_TEXT_COLOR);
        add(lbldescription);

        txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescription.setBackground(Color.WHITE);
        txtDescription.setBorder(new EmptyBorder(8, 8, 8, 8));

        descScroll = new JScrollPane(txtDescription);
        descScroll.setBounds(430, 190, 480, 250);
        descScroll.setBorder(new LineBorder(new Color(215, 219, 221), 1));
        add(descScroll);

        String[] cols = {
            "Req ID", "Employee ID", "Employee Name", "Department", "Role", "Request Type", "Description", "Status", "Notes"
        };

        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 233, 235));
        table.setSelectionBackground(new Color(52, 152, 219, 30));
        table.setSelectionForeground(Color.BLACK);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setBackground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JTableHeader header = table.getTableHeader();
        header.setBackground(sidebarDarkGray);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(290, 480, 665, 450);
        scroll.setBorder(new LineBorder(new Color(215, 219, 221), 1));
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(115);
        }
        table.getColumnModel().getColumn(6).setPreferredWidth(200);
        table.getColumnModel().getColumn(8).setPreferredWidth(200);

        btnSubmit.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnSignout.addActionListener(this);
        btnViewDetails.addActionListener(this);
        btnViewReviews.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadRequestsFromDB();
        setVisible(true);
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
        if (e.getSource() == btnSubmit) {
            handleSubmit();
        } else if (e.getSource() == btnUpdate) {
            handleUpdate();
        } else if (e.getSource() == btnDelete) {
            handleDelete();
        } else if (e.getSource() == btnSignout) {
            dispose();
            new LoginFrame();
        } else if (e.getSource() == btnViewDetails) {
            handleViewDetails();
        } else if (e.getSource() == btnViewReviews) {
            dispose();
            new EmployeeFrameReviewView(currentUserId, currentUserName);
        }
    }

    private void loadRequestsFromDB() {
        tableModel.setRowCount(0);

        String query = "SELECT r.request_id, r.employee_id, e.first_name, e.last_name, "
                + "d.dept_name, e.role, r.request_type, r.description, r.status, r.notes "
                + "FROM employee_requests r "
                + "INNER JOIN employees e ON r.employee_id = e.employee_id "
                + "LEFT JOIN departments d ON e.dept_id = d.dept_id "
                + "WHERE r.employee_id = ? "
                + "ORDER BY r.request_id DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, this.currentUserId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String fullName = rs.getString("first_name") + " " + rs.getString("last_name");

                    tableModel.addRow(new Object[]{
                        rs.getInt("request_id"),
                        rs.getString("employee_id"),
                        fullName.trim(),
                        rs.getString("dept_name") != null ? rs.getString("dept_name") : "None",
                        rs.getString("role"),
                        rs.getString("request_type"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("notes") != null ? rs.getString("notes") : "No comment yet"
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not load requests: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSubmit() {
        String type = cbrequest.getSelectedItem().toString();
        String desc = txtDescription.getText().trim();

        if (type.equals("") || desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Validation Notice", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO employee_requests (employee_id, request_type, description) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, currentUserId);
            pstmt.setString(2, type);
            pstmt.setString(3, desc);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Request submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            clearFields();
            loadRequestsFromDB();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to submit request: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String currentStatus = tableModel.getValueAt(row, 7).toString();
        if (!currentStatus.equals("Pending")) {
            JOptionPane.showMessageDialog(this, "You cannot edit a request that has already been " + currentStatus + ".", "Action Denied", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String reqId = tableModel.getValueAt(row, 0).toString();
        String type = tableModel.getValueAt(row, 5).toString();
        String desc = tableModel.getValueAt(row, 6).toString();

        JTextField empIDField = new JTextField(currentUserId);
        empIDField.setEditable(false);

        JTextField empNameField = new JTextField(currentUserName);
        empNameField.setEditable(false);

        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Leave", "Overtime", "Expenses"});
        typeBox.setSelectedItem(type);

        JTextArea descArea = new JTextArea(desc);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);

        JScrollPane descScrollEdit = new JScrollPane(descArea);
        descScrollEdit.setPreferredSize(new Dimension(200, 80));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(400, 260));

        JLabel lblID = new JLabel("Employee ID:");
        lblID.setBounds(20, 20, 120, 25);
        panel.add(lblID);

        empIDField.setBounds(150, 20, 200, 25);
        panel.add(empIDField);

        JLabel lblName = new JLabel("Employee Name:");
        lblName.setBounds(20, 60, 120, 25);
        panel.add(lblName);

        empNameField.setBounds(150, 60, 200, 25);
        panel.add(empNameField);

        JLabel lblType = new JLabel("Request Type:");
        lblType.setBounds(20, 100, 120, 25);
        panel.add(lblType);

        typeBox.setBounds(150, 100, 200, 25);
        panel.add(typeBox);

        JLabel lblDesc = new JLabel("Description:");
        lblDesc.setBounds(20, 140, 120, 25);
        panel.add(lblDesc);

        descScrollEdit.setBounds(150, 140, 200, 80);
        panel.add(descScrollEdit);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Edit Employee Request #" + reqId, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String newDesc = descArea.getText().trim();

            if (newDesc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please complete the description before saving.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "UPDATE employee_requests SET request_type = ?, description = ? WHERE request_id = ?";
            try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, typeBox.getSelectedItem().toString());
                pstmt.setString(2, newDesc);
                pstmt.setString(3, reqId);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadRequestsFromDB();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleDelete() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String currentStatus = tableModel.getValueAt(row, 7).toString();
        if (!currentStatus.equals("Pending")) {
            JOptionPane.showMessageDialog(this, "You cannot delete a request that has already been " + currentStatus + ".", "Action Denied", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String reqId = tableModel.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to withdraw Request #" + reqId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM employee_requests WHERE request_id = ?";
            try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, reqId);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Request withdrawn.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadRequestsFromDB();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Deletion failed: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleViewDetails() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a request from the table first.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Extracting data exactly from table values
        String reqID = tableModel.getValueAt(row, 0).toString();
        String empID = tableModel.getValueAt(row, 1).toString();
        String empName = tableModel.getValueAt(row, 2).toString();
        String department = tableModel.getValueAt(row, 3).toString();
        String role = tableModel.getValueAt(row, 4).toString();
        String type = tableModel.getValueAt(row, 5).toString();
        String desc = tableModel.getValueAt(row, 6).toString();
        String status = tableModel.getValueAt(row, 7).toString();
        String comment = tableModel.getValueAt(row, 8).toString();

        // Building stylized modern panel
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout(15, 15));
        container.setPreferredSize(new Dimension(520, 380));
        container.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Metainfo Left Container
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(5, 1, 5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(215, 219, 221), 1), " Ticket Metainfo ", 0, 0, new Font("Segoe UI", Font.BOLD, 12), BACKGROUND_TEXT_COLOR));
        leftPanel.setPreferredSize(new Dimension(230, 200));

        String[][] profileMeta = {
            {"Ticket ID:", " #" + reqID},
            {"Staff ID:", " " + empID},
            {"Full Name:", " " + empName},
            {"Dept:", " " + department},
            {"Designation:", " " + role}
        };

        for (String[] data : profileMeta) {
            JPanel line = new JPanel(new BorderLayout());
            JLabel title = new JLabel(data[0]);
            title.setFont(new Font("Segoe UI", Font.BOLD, 12));
            title.setForeground(Color.GRAY);
            JLabel val = new JLabel(data[1]);
            val.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            val.setForeground(BACKGROUND_TEXT_COLOR);
            line.add(title, BorderLayout.WEST);
            line.add(val, BorderLayout.CENTER);
            leftPanel.add(line);
        }

        // Operational Right Container
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2, 1, 5, 5));
        rightPanel.setPreferredSize(new Dimension(230, 200));
        rightPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(215, 219, 221), 1), " Category & Status ", 0, 0, new Font("Segoe UI", Font.BOLD, 12), BACKGROUND_TEXT_COLOR));

        JPanel typeLine = new JPanel(new BorderLayout());
        JLabel lblT = new JLabel("Category: ");
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblT.setForeground(Color.GRAY);
        JLabel valT = new JLabel(type);
        valT.setFont(new Font("Segoe UI", Font.BOLD, 13));
        valT.setForeground(ACCENT_BLUE);
        typeLine.add(lblT, BorderLayout.WEST);
        typeLine.add(valT, BorderLayout.CENTER);

        JPanel statusLine = new JPanel(new BorderLayout());
        JLabel lblS = new JLabel("Status: ");
        lblS.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblS.setForeground(Color.GRAY);
        JLabel valS = new JLabel(status);
        valS.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Dynamically style status text
        if (status.equalsIgnoreCase("Approved")) {
            valS.setForeground(SUCCESS_GREEN);
        } else if (status.equalsIgnoreCase("Pending")) {
            valS.setForeground(new Color(241, 196, 15));
        } else {
            valS.setForeground(DANGER_RED);
        }
        statusLine.add(lblS, BorderLayout.WEST);
        statusLine.add(valS, BorderLayout.CENTER);

        rightPanel.add(typeLine);
        rightPanel.add(statusLine);

        // Top structural wrap split
        JPanel splitTop = new JPanel(new GridLayout(1, 2, 10, 10));
        splitTop.add(leftPanel);
        splitTop.add(rightPanel);
        container.add(splitTop, BorderLayout.NORTH);

        // Lower multi-lined components mapping
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1, 10, 10));

        // Description area
        JTextArea areaDesc = new JTextArea(desc);
        areaDesc.setEditable(false);
        areaDesc.setLineWrap(true);
        areaDesc.setWrapStyleWord(true);
        areaDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane scrollD = new JScrollPane(areaDesc);
        scrollD.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(215, 219, 221), 1), " Staff Description Details ", 0, 0, new Font("Segoe UI", Font.BOLD, 11), BACKGROUND_TEXT_COLOR));
        bottomPanel.add(scrollD);

        // Manager feedback notes
        JTextArea areaComment = new JTextArea(comment);
        areaComment.setEditable(false);
        areaComment.setLineWrap(true);
        areaComment.setWrapStyleWord(true);
        areaComment.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane scrollC = new JScrollPane(areaComment);
        scrollC.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(215, 219, 221), 1), " Management Feedback Notes ", 0, 0, new Font("Segoe UI", Font.BOLD, 11), BACKGROUND_TEXT_COLOR));
        bottomPanel.add(scrollC);

        container.add(bottomPanel, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, container, "Ticket Details - Request #" + reqID, JOptionPane.PLAIN_MESSAGE);
    }

    private void clearFields() {
        cbrequest.setSelectedIndex(0);
        txtDescription.setText("");
    }
}
