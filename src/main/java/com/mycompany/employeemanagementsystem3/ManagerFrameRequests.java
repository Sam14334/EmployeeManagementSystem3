/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.employeemanagementsystem3;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ManagerFrameRequests extends JFrame implements ActionListener {

    private final Color SIDEBAR_BG = new Color(34, 45, 57);
    private final Color CONTENT_BG = new Color(240, 242, 245);
    private final Color SUCCESS_GREEN = new Color(40, 167, 69);
    private final Color DANGER_RED = new Color(231, 76, 60);
    private final Color TEXT_DARK = new Color(44, 62, 80);
    private final Color CARD_BG = Color.WHITE;

    JTable table;
    DefaultTableModel model;
    JButton btnAccept, btnDeny;

    public ManagerFrameRequests() {
        setTitle("StaffSync - Manager - Process Requests");
        setSize(1000, 1000);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(CONTENT_BG);

        //SIDEBAR
        JPanel sideNav = new JPanel(null);
        sideNav.setBackground(SIDEBAR_BG);
        sideNav.setBounds(0, 0, 260, 1000);
        add(sideNav);

        JLabel lblUser = new JLabel("Manager Panel", SwingConstants.CENTER);
        lblUser.setForeground(Color.LIGHT_GRAY);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblUser.setBounds(30, 100, 200, 30);
        sideNav.add(lblUser);

        //EXIT BUTTON 
        JButton btnExit = createStyledBtn("⏻ Exit", 0, DANGER_RED);

        int sidebarHeight = sideNav.getHeight(); // 1000
        int btnHeight = 45;
        int bottomMargin = 80;

        btnExit.setBounds(30, sidebarHeight - btnHeight - bottomMargin, 200, btnHeight);

        sideNav.add(btnExit);
        btnExit.addActionListener(e -> System.exit(0));

        //TITLE
        JLabel lblTitle = new JLabel("Employee Requests");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_DARK);
        lblTitle.setBounds(300, 30, 400, 40);
        add(lblTitle);

        //TABLE CARD
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_BG);
        tableCard.setBounds(300, 100, 650, 400);
        tableCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(tableCard);

        String[] cols = {"ID", "Employee Name", "Request Type", "Date", "Status", "Note"};
        model = new DefaultTableModel(cols, 0);

        model.addRow(new Object[]{"1", "Juan Dela Cruz", "Leave", "2026-04-01", "Pending", ""});
        model.addRow(new Object[]{"2", "Maria Santos", "Overtime", "2026-04-02", "Approved", ""});
        model.addRow(new Object[]{"3", "Pedro Reyes", "Leave", "2026-04-03", "Pending", ""});

        table = new JTable(model) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        styleTable(table);

       
        table.getColumnModel().getColumn(5).setPreferredWidth(200);

        JScrollPane pane = new JScrollPane(table);
        pane.getViewport().setBackground(Color.WHITE);
        pane.setBorder(new LineBorder(new Color(240, 240, 240)));

        tableCard.add(pane, BorderLayout.CENTER);

        //BUTTONS UNDER TABLE
        btnAccept = createStyledBtn("✔ Approve Request", 0, SUCCESS_GREEN);
        btnAccept.setBounds(320, 520, 200, 45);
        add(btnAccept);

        btnDeny = createStyledBtn("✖ Deny Request", 0, DANGER_RED);
        btnDeny.setBounds(580, 520, 200, 45);
        add(btnDeny);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //BUTTON STYLE
    private JButton createStyledBtn(String text, int y, Color color) {
        JButton b = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
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

    //TABLE STYLE
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
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
    }

    //LOGIC
    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a request first.");
            return;
        }

        String status = model.getValueAt(row, 4).toString();

        if (!status.equals("Pending")) {
            JOptionPane.showMessageDialog(this, "Already processed.");
            return;
        }

        JTextArea noteArea = new JTextArea(5, 20);
        JScrollPane scroll = new JScrollPane(noteArea);

        int confirm;

        // APPROVE
        if (e.getSource() == btnAccept) {
            confirm = JOptionPane.showConfirmDialog(this, scroll,
                    "Enter approval note (required)", JOptionPane.OK_CANCEL_OPTION);

            if (confirm == JOptionPane.OK_OPTION) {
                String note = noteArea.getText().trim();

                if (note.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Note is required!");
                    return;
                }

                model.setValueAt("Approved", row, 4);
                model.setValueAt(note, row, 5);
            }
        }

        // DENY
        if (e.getSource() == btnDeny) {
            confirm = JOptionPane.showConfirmDialog(this, scroll,
                    "Enter reason for denial (required)", JOptionPane.OK_CANCEL_OPTION);

            if (confirm == JOptionPane.OK_OPTION) {
                String note = noteArea.getText().trim();

                if (note.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Note is required!");
                    return;
                }

                model.setValueAt("Denied", row, 4);
                model.setValueAt(note, row, 5);
            }
        }

    }
 
}
