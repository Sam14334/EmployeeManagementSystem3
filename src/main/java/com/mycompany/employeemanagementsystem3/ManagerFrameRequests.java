

package com.mycompany.employeemanagementsystem3;

import java.awt.*;
import java.awt.event.*;
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

    public ManagerFrameRequests() {
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

        ImageIcon rawIcon = new ImageIcon("src\\main\\java\\images\\karlo.png");
        Image scaledImg = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel lblProfilePic = new JLabel(new ImageIcon(scaledImg));
        lblProfilePic.setBounds(80, 30, 100, 100);
        lblProfilePic.setBorder(new LineBorder(new Color(255, 255, 255, 50), 2));
        sideBar.add(lblProfilePic);

        JLabel lblUser = new JLabel("Manager | Karlo", SwingConstants.CENTER);
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

      
        String[] columns = {"ID", "Employee Name", "Request Type", "Date Submitted", "Status", "Notes"};
        String[][] data = {
            {"REQ-001", "Jomar N. Pangilinan", "Leave", "2026-04-01", "Pending", "-"},
            {"REQ-002", "Micheal P. Samia", "Overtime", "2026-04-02", "Approved", "Approved by HR"},
            {"REQ-003", "Karlo H. Alatiit", "Leave", "2026-04-03", "Pending", "-"},
            {"REQ-004", "Ezekiel Parao", "Transfer", "2026-04-04", "Pending", "-"},
            {"REQ-005", "Rich Jasper C. Federio", "Resignation", "2026-04-05", "Denied", "Incomplete papers"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
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

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

      
        if (e.getSource() == btnApprove) {
            int viewRow = requestTable.getSelectedRow();
            if (viewRow == -1) {
                JOptionPane.showMessageDialog(this, "Select a request.");
                return;
            }

            int row = requestTable.convertRowIndexToModel(viewRow);

            String status = requestTable.getModel().getValueAt(row, 4).toString();

            if (status.equals("Approved") || status.equals("Denied")) {
                JOptionPane.showMessageDialog(this, "Already processed.");
                return;
            }

            String note = JOptionPane.showInputDialog(this, "Enter approval note:");

            if (note != null && !note.trim().isEmpty()) {
                requestTable.getModel().setValueAt("Approved", row, 4);
                requestTable.getModel().setValueAt(note, row, 5);
            }
        }

        
        if (e.getSource() == btnDeny) {
            int viewRow = requestTable.getSelectedRow();
            if (viewRow == -1) {
                JOptionPane.showMessageDialog(this, "Select a request.");
                return;
            }

            int row = requestTable.convertRowIndexToModel(viewRow);

            String status = requestTable.getModel().getValueAt(row, 4).toString();

            if (status.equals("Approved") || status.equals("Denied")) {
                JOptionPane.showMessageDialog(this, "Already processed.");
                return;
            }

            String reason = JOptionPane.showInputDialog(this, "Enter denial reason:");

            if (reason != null && !reason.trim().isEmpty()) {
                requestTable.getModel().setValueAt("Denied", row, 4);
                requestTable.getModel().setValueAt(reason, row, 5);
            }
        }

        // SIGN OUT
        if (e.getSource() == btnSignOut) {
            dispose();
             new LoginFrame(); 
        }
    }
}