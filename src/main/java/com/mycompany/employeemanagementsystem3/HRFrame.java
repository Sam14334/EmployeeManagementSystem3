package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class HRFrame extends JFrame implements ActionListener {

    
    private final Color SIDEBAR_BG = new Color(34, 45, 57);
    private final Color CONTENT_BG = new Color(240, 242, 245);
    private final Color ACCENT_BLUE = new Color(52, 152, 219);
    private final Color DANGER_RED = new Color(231, 76, 60);
    private final Color TEXT_DARK = new Color(44, 62, 80);
    private final Color CARD_BG = Color.WHITE;

    private DefaultTableModel model;
    private JTable table;
    private JTextField txtSearch;
    private JButton btnAdd, btnEdit, btnDelete, btnSignOut;

    public HRFrame() {
        setTitle("StaffSync - HR Dashboard");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());
        
        JPanel container = new JPanel(new BorderLayout());
        setContentPane(container);

        
        JPanel sideNav = new JPanel();
        sideNav.setPreferredSize(new Dimension(260, 1000));
        sideNav.setBackground(SIDEBAR_BG);
        sideNav.setLayout(null);

        
        ImageIcon rawIcon = new ImageIcon("src\\main\\java\\images\\karlo.png"); 

        
        Image scaledImg = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon finalAvatar = new ImageIcon(scaledImg);

       
        JLabel lblProfilePic = new JLabel(finalAvatar);
        lblProfilePic.setBounds(80, 30, 100, 100);

       
        lblProfilePic.setBorder(new LineBorder(new Color(255, 255, 255, 50), 2)); 

        sideNav.add(lblProfilePic);

        JLabel lblUser = new JLabel("HR Manager | Karlo", SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setBounds(30, 140, 200, 25);
        sideNav.add(lblUser);

       
        JLabel lblLogo = new JLabel(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo128.png"));
        lblLogo.setBounds(66, 185, 128, 128); 
        sideNav.add(lblLogo);

       
        btnAdd = createStyledBtn("+ Add Employee", 340, ACCENT_BLUE);
        btnEdit = createStyledBtn("✎ Edit Employee", 400, ACCENT_BLUE);
        btnDelete = createStyledBtn("🗑 Delete Record", 460, DANGER_RED);
        
        sideNav.add(btnAdd);
        sideNav.add(btnEdit);
        sideNav.add(btnDelete);

       
        JPanel mainContent = new JPanel();
        mainContent.setBackground(CONTENT_BG);
        mainContent.setLayout(null);

        JLabel lblTitle = new JLabel("Employee Records");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_DARK);
        lblTitle.setBounds(40, 30, 300, 40);
        mainContent.add(lblTitle);

        txtSearch = new JTextField(" Search...");
        txtSearch.setBounds(300, 35, 230, 35);
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        mainContent.add(txtSearch);

        JPanel tableCard = new JPanel();
        tableCard.setBackground(CARD_BG);
        tableCard.setBounds(40, 90, 700, 480);
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] cols = {"ID", "First Name", "Last Name", "Department", "Position", "Salary"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        styleTable(table);
        
        JScrollPane pane = new JScrollPane(table);
        pane.getViewport().setBackground(Color.WHITE);
        pane.setBorder(new LineBorder(new Color(240, 240, 240)));
        tableCard.add(pane, BorderLayout.CENTER);
        mainContent.add(tableCard);

        btnSignOut = new JButton("Sign out →");
        btnSignOut.setBounds(500, 590, 150, 40);
        btnSignOut.setBackground(new Color(41, 128, 185));
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.setFocusPainted(false);
        btnSignOut.setBorderPainted(false);
        btnSignOut.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSignOut.addActionListener(this);
        mainContent.add(btnSignOut);

        model.addRow(new Object[]{"001", "John", "Doe", "Marketing", "Specialist", "50,000"});
        model.addRow(new Object[]{"002", "Sarah", "Connor", "IT", "System Admin", "65,000"});
        model.addRow(new Object[]{"003", "Michael", "Jordan", "Exec", "Director", "120,000"});

        container.add(sideNav, BorderLayout.WEST);
        container.add(mainContent, BorderLayout.CENTER);
        setVisible(true);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(245, 245, 245));
        table.setSelectionBackground(new Color(235, 245, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(new Color(100, 100, 100));
        header.setPreferredSize(new Dimension(100, 45));
        header.setBorder(new MatteBorder(0, 0, 2, 0, new Color(240, 240, 240)));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
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
        b.setBounds(30, y, 200, 45);
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
        if (e.getSource() == btnAdd) {
            handleForm("ADD", -1);
        } else if (e.getSource() == btnEdit) {
            int row = table.getSelectedRow();
            if (row != -1) handleForm("EDIT", row);
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

    private void handleForm(String type, int row) {
        JTextField id = new JTextField(type.equals("EDIT") ? model.getValueAt(row, 0).toString() : "");
        JTextField fn = new JTextField(type.equals("EDIT") ? model.getValueAt(row, 1).toString() : "");
        JTextField ln = new JTextField(type.equals("EDIT") ? model.getValueAt(row, 2).toString() : "");
        JTextField dept = new JTextField(type.equals("EDIT") ? model.getValueAt(row, 3).toString() : "");
        JTextField pos = new JTextField(type.equals("EDIT") ? model.getValueAt(row, 4).toString() : "");
        JTextField sl = new JTextField(type.equals("EDIT") ? model.getValueAt(row, 5).toString() : "");

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("ID:")); panel.add(id);
        panel.add(new JLabel("First Name:")); panel.add(fn);
        panel.add(new JLabel("Last Name:")); panel.add(ln);
        panel.add(new JLabel("Department:")); panel.add(dept);
        panel.add(new JLabel("Position:")); panel.add(pos);
        panel.add(new JLabel("Salary:")); panel.add(sl);

        int result = JOptionPane.showConfirmDialog(this, panel, type + " Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Object[] data = {id.getText(), fn.getText(), ln.getText(), dept.getText(), pos.getText(), sl.getText()};
            if (type.equals("EDIT")) {
                for(int i=0; i<6; i++) model.setValueAt(data[i], row, i);
            } else model.addRow(data);
        }
    }

    private void showModernMsg(String msg, String title) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new HRFrame());
    }
}
