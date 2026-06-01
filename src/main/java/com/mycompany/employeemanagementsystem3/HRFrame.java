package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class HRFrame extends JFrame implements ActionListener {

    private JPanel sideBar, mainContent;
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtSearch;
    private JButton btnAdd, btnEdit, btnDelete, btnSignOut;
    private JButton btnEmpRecords, btnEmpRequests;

    public HRFrame() {
        setTitle("StaffSync - HR Dashboard");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());

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

        JLabel lblUser = new JLabel("HR Manager | Karlo", SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setBounds(30, 140, 200, 25);
        sideBar.add(lblUser);

        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setBounds(66, 185, 128, 128); 
        sideBar.add(lblLogo);

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

        // Aligned Unified Master Records
        model.addRow(new Object[]{"001", "jomar_p", "pass1", "Jomar N.", "Pangilinan", "jomar@staffsync.com", "09123456789", "Management", "Manager", "Regular", "50,000"});
        model.addRow(new Object[]{"002", "karlo_a", "pass2", "Karlo", "Alatiit", "karlo@staffsync.com", "09234567890", "Operations", "Supervisor", "Regular", "40,000"});
        model.addRow(new Object[]{"003", "rich_j", "pass3", "Rich Jasper", "Federio", "rich@staffsync.com", "09345678901", "Technical", "Staff", "Regular", "30,000"});
        model.addRow(new Object[]{"004", "alice_g", "pass4", "Alice", "Guo", "alice@staffsync.com", "09456789012", "Finance", "Accountant", "Regular", "35,000"});
        model.addRow(new Object[]{"005", "bob_m", "pass5", "Bob", "Marley", "bob@staffsync.com", "09567890123", "Logistics", "Driver", "Regular", "25,000"});

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(110);
        }

        add(sideBar);
        add(mainContent);
        setVisible(true);
    }

    public DefaultTableModel getTableModel() {
        return this.model;
    }

    public void addEmployeeRow(Object[] dataRow) {
        model.addRow(dataRow);
        JOptionPane.showMessageDialog(this, "Employee record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        if (e.getSource() == btnEmpRequests) {
            dispose();
            new ManagerFrameReview(this); // Transitions seamlessly passing identical layouts
        } else if (e.getSource() == btnAdd) {
            new AddEmployeeFrame(this);
        } else if (e.getSource() == btnEdit) {
            int row = table.getSelectedRow();
            if (row != -1) handleEditForm(row);
            else showModernMsg("Please select a row to edit!", "No Selection");
        } else if (e.getSource() == btnDelete) {
            int row = table.getSelectedRow();
            if (row != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete record?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) model.removeRow(row);
            } else showModernMsg("Please select a row to delete!", "No Selection");
        } else if (e.getSource() == btnSignOut) {
            dispose();
            new LoginFrame();
        }
    }

    private void handleEditForm(int row) {
        JTextField id = new JTextField(model.getValueAt(row, 0).toString());
        JTextField user = new JTextField(model.getValueAt(row, 1).toString());
        JTextField pass = new JTextField(model.getValueAt(row, 2).toString());
        JTextField fn = new JTextField(model.getValueAt(row, 3).toString());
        JTextField ln = new JTextField(model.getValueAt(row, 4).toString());
        JTextField email = new JTextField(model.getValueAt(row, 5).toString());
        JTextField phone = new JTextField(model.getValueAt(row, 6).toString());
        JTextField dept = new JTextField(model.getValueAt(row, 7).toString());
        JTextField role = new JTextField(model.getValueAt(row, 8).toString());
        JTextField status = new JTextField(model.getValueAt(row, 9).toString());
        JTextField sl = new JTextField(model.getValueAt(row, 10).toString());

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 8));
        panel.add(new JLabel("Employee ID:")); panel.add(id);
        panel.add(new JLabel("Username:")); panel.add(user);
        panel.add(new JLabel("Password:")); panel.add(pass);
        panel.add(new JLabel("First Name:")); panel.add(fn);
        panel.add(new JLabel("Last Name:")); panel.add(ln);
        panel.add(new JLabel("Email:")); panel.add(email);
        panel.add(new JLabel("Phone Number:")); panel.add(phone);
        panel.add(new JLabel("Department:")); panel.add(dept);
        panel.add(new JLabel("Role:")); panel.add(role);
        panel.add(new JLabel("Employment Status:")); panel.add(status);
        panel.add(new JLabel("Salary:")); panel.add(sl);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(380, 400));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        int result = JOptionPane.showConfirmDialog(this, scrollPane, "Edit Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Object[] data = {
                id.getText(), user.getText(), pass.getText(), fn.getText(), ln.getText(), 
                email.getText(), phone.getText(), dept.getText(), role.getText(), status.getText(), sl.getText()
            };
            for(int i = 0; i < 11; i++) model.setValueAt(data[i], row, i);
        }
    }

    private void showModernMsg(String msg, String title) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
}