package com.mycompany.employeemanagementsystem3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class EmployeeFrame extends JFrame implements ActionListener {
    
    private final Color ACCENT_BLUE = new Color(52, 152, 219);
    private final Color DANGER_RED = new Color(231, 76, 60);
    private JLabel lblERS, lblEmployeeInfo, lblEmpID, lblEmpName, lblDepartment, lblPosition, lblrequestType, lbldescription;
    private JTextField txtEmpID, txtEmpName, txtPosition;
    private JComboBox<String> cbDepartment;
    private JComboBox<String> cbrequest;
    private JTextArea txtDescription;
    private JButton btnSubmit, btnDelete, btnUpdate, btnSignout, btnViewDetails;
    private JTable table;
    private JScrollPane scroll, descScroll;
    private DefaultTableModel tableModel;
    private JPanel sideNav;
    private final Color SIDEBAR_BG = new Color(34, 45, 57);

    public EmployeeFrame(){
        
        setTitle("StaffSync - Employee Request Dashboard");
        setSize(1000,1000);
        setLayout(null);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src\\main\\java\\images\\StaffSyncLogo16.png").getImage());
       
        sideNav = new JPanel();
        sideNav.setSize(260,1000);
        sideNav.setBackground(SIDEBAR_BG);
        sideNav.setLayout(null);
        add(sideNav);
        
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
        
        lblERS = new JLabel("Employee Request System");
        lblERS.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblERS.setBounds(350, 20, 400, 40);
        add(lblERS);
        
        lblEmployeeInfo = new JLabel("Employee Information");
        lblEmployeeInfo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblEmployeeInfo.setBounds(300, 70, 300, 30);
        add(lblEmployeeInfo);
        
        lblEmpID = new JLabel("Employee ID:");
        lblEmpID.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEmpID.setBounds(300, 120, 150, 30);
        add(lblEmpID);

        txtEmpID = new JTextField();
        txtEmpID.setBounds(430, 120, 200, 30);
        add(txtEmpID);
        
        lblEmpName = new JLabel("Employee Name:");
        lblEmpName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEmpName.setBounds(300, 170, 150, 30);
        add(lblEmpName);

        txtEmpName = new JTextField();
        txtEmpName.setBounds(430, 170, 200, 30);
        add(txtEmpName);
        
        lblDepartment = new JLabel("Department:");
        lblDepartment.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDepartment.setBounds(300, 220, 150, 30);
        add(lblDepartment);

        String[] departments = {"", "IT", "HR", "Finance", "Marketing"};

        cbDepartment = new JComboBox<>(departments);
        cbDepartment.setBounds(430, 220, 200, 30);
        add(cbDepartment);
        
        lblPosition = new JLabel("Position:");
        lblPosition.setBounds(300, 270, 150, 30);
        lblPosition.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblPosition);

        txtPosition = new JTextField();
        txtPosition.setBounds(430, 270, 200, 30);
        add(txtPosition);
        
        lblrequestType = new JLabel("Request Type:");
        lblrequestType.setBounds(300, 340, 150, 30);
        lblrequestType.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblrequestType);
        
        String[] types = {"", "Leave", "Overtime", "Expenses"};
        cbrequest = new JComboBox<>(types);
        cbrequest.setBounds(430, 340, 200, 30);
        add(cbrequest);
        
        lbldescription = new JLabel("Description:");
        lbldescription.setBounds(300, 390, 150, 30);
        lbldescription.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lbldescription);
        
        txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDescription.setBackground(Color.WHITE);
        txtDescription.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        descScroll = new JScrollPane(txtDescription);
        descScroll.setBounds(430, 390, 350, 110);
        add(descScroll);
       
        btnSubmit = new JButton("Submit Request");
        btnSubmit.setBounds(30, 330, 200, 50);
        btnSubmit.setBackground(ACCENT_BLUE);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sideNav.add(btnSubmit);
    
        btnUpdate = new JButton("Update Request");
        btnUpdate.setBounds(30, 400, 200, 50);
        btnUpdate.setBackground(ACCENT_BLUE);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sideNav.add(btnUpdate);
        
        btnDelete = new JButton("Delete Request");
        btnDelete.setBounds(30, 540, 200, 50); 
        btnDelete.setBackground(DANGER_RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sideNav.add(btnDelete);
        
        btnViewDetails = new JButton("View Details");
        btnViewDetails.setBounds(30, 470, 200, 50);   
        btnViewDetails.setBackground(new Color(52, 152, 219));
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sideNav.add(btnViewDetails);
        
       btnSignout = new JButton("Sign out →");
        btnSignout.setBounds(35,890,180,50);
        btnSignout.setBackground(Color.RED);
        btnSignout.setForeground(Color.WHITE);
        btnSignout.setFocusPainted(false);
        btnSignout.setBorderPainted(false);
        btnSignout.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSignout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSignout.addActionListener(this);
        sideNav.add(btnSignout);
        
        tableModel = new DefaultTableModel();
        
        tableModel.setColumnIdentifiers(new String[]{
            "Employee ID",
            "Employee Name",
            "Department",
            "Position",
            "Request Type",
            "Description",
            "Status"
        });
        
        table = new JTable(tableModel);

        table.setRowHeight(55);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setGridColor(new Color(240, 240, 240));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(10, 10));

        table.setOpaque(true);
        table.setBackground(Color.WHITE);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(new Color(100, 100, 100));
        table.getTableHeader().setPreferredSize(new Dimension(100, 55));
        table.getTableHeader().setBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230))
        );

        scroll = new JScrollPane(table);
        scroll.setBounds(280, 540, 650, 350);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        scroll.getViewport().setBackground(Color.WHITE);

        add(scroll);
      
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(250);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        
        btnSubmit.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnSignout.addActionListener(this);
        btnViewDetails.addActionListener(this);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSubmit){
            handleSubmit();
        }
        else if(e.getSource() == btnUpdate){
            handleUpdate();
        }
        else if(e.getSource() == btnDelete){
            handleDelete();
        }
        else if(e.getSource() == btnSignout){
            dispose();
            new LoginFrame();
        }
        else if(e.getSource() == btnViewDetails){
        handleViewDetails();
        }
        
    }
            private void handleSubmit(){

            String empID = txtEmpID.getText().trim();
            String empName = txtEmpName.getText().trim();
            String type = cbrequest.getSelectedItem().toString();
            String desc = txtDescription.getText().trim();
            String department = cbDepartment.getSelectedItem().toString();
            String position = txtPosition.getText().trim();

            if(empID.isEmpty() || 
               empName.isEmpty() || 
               department.equals("") || 
               position.equals("") || 
               type.equals("") || 
               desc.isEmpty()){
                
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            tableModel.addRow(new Object[]{
                empID,
                empName,
                cbDepartment.getSelectedItem().toString(),
                txtPosition.getText().trim(),
                type,
                desc,
                "Pending"
            });

            clearFields();
        }

           private void handleUpdate(){

            int row = table.getSelectedRow();

            if(row == -1){
                JOptionPane.showMessageDialog(this, "Select a row first.");
                return;
            }
          
            String empID = tableModel.getValueAt(row, 0).toString();
            String empName = tableModel.getValueAt(row, 1).toString();
            String department = tableModel.getValueAt(row, 2).toString();
            String position = tableModel.getValueAt(row, 3).toString();
            String type = tableModel.getValueAt(row, 4).toString();
            String desc = tableModel.getValueAt(row, 5).toString();
            String status = tableModel.getValueAt(row, 6).toString();
          
            JTextField empIDField = new JTextField(empID);
            JTextField empNameField = new JTextField(empName);
            JComboBox<String> deptBox = new JComboBox<>(
            new String[]{"IT", "HR", "Finance", "Marketing"}
            );
            deptBox.setSelectedItem(department);
            JTextField positionField = new JTextField(position);

            JComboBox<String> typeBox = new JComboBox<>(new String[]{"Leave", "Overtime", "Expenses"});
            typeBox.setSelectedItem(type);

            JTextArea descArea = new JTextArea(desc);
            descArea.setLineWrap(true);
            descArea.setWrapStyleWord(true);

            JScrollPane descScroll = new JScrollPane(descArea);
            descScroll.setPreferredSize(new Dimension(200, 80));

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setPreferredSize(new Dimension(400, 420));

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

            JLabel lblDept = new JLabel("Department:");
            lblDept.setBounds(20, 100, 120, 25);
            panel.add(lblDept);

            deptBox.setBounds(150, 100, 200, 25);
            panel.add(deptBox);
            
            JLabel lblPosition = new JLabel("Position:");
            lblPosition.setBounds(20, 140, 120, 25);
            panel.add(lblPosition);

            positionField.setBounds(150, 140, 200, 25);
            panel.add(positionField);

            JLabel lblType = new JLabel("Request Type:");
            lblType.setBounds(20, 180, 120, 25);
            panel.add(lblType);

            typeBox.setBounds(150, 180, 200, 25);
            panel.add(typeBox);

            JLabel lblDesc = new JLabel("Description:");
            lblDesc.setBounds(20, 220, 120, 25);
            panel.add(lblDesc);

            descScroll.setBounds(150, 220, 200, 80);
            panel.add(descScroll);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Edit Employee Request",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if(result == JOptionPane.OK_OPTION){

            String newEmpID = empIDField.getText().trim();
            String newEmpName = empNameField.getText().trim();
            String newDept = deptBox.getSelectedItem().toString();
            String newPosition = positionField.getText().trim();
            String newDesc = descArea.getText().trim();
          
            if(newEmpID.isEmpty() ||
               newEmpName.isEmpty() ||
               newDept.isEmpty() ||
               newPosition.isEmpty() ||
               newDesc.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please complete all fields before saving.");

                return;
            }
         
            tableModel.setValueAt(newEmpID, row, 0);
            tableModel.setValueAt(newEmpName, row, 1);
            tableModel.setValueAt(newDept, row, 2);
            tableModel.setValueAt(newPosition, row, 3);
            tableModel.setValueAt(typeBox.getSelectedItem().toString(), row, 4);
            tableModel.setValueAt(newDesc, row, 5);

            JOptionPane.showMessageDialog(this, "Updated successfully!");
            
            }
        }
            
        private void handleDelete(){
            int row = table.getSelectedRow();

            if(row == -1){
                JOptionPane.showMessageDialog(this, "Select a row first.");
                return;
            }

            tableModel.removeRow(row);
        }

        private void handleViewDetails(){

        int row = table.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this, "Select a request first.");
            return;
        }

        String empID = tableModel.getValueAt(row, 0).toString();
        String empName = tableModel.getValueAt(row, 1).toString();
        String department = tableModel.getValueAt(row, 2).toString();
        String position = tableModel.getValueAt(row, 3).toString();
        String type = tableModel.getValueAt(row, 4).toString();
        String desc = tableModel.getValueAt(row, 5).toString();
        String status = tableModel.getValueAt(row, 6).toString();

        String comment = "No comment yet";

        String message =
                "EMPLOYEE DETAILS\n\n" +
                "ID: " + empID + "\n" +
                "Name: " + empName + "\n" +
                "Department: " + department + "\n" +
                "Position: " + position + "\n\n" +

                "REQUEST DETAILS\n" +
                "Type: " + type + "\n" +
                "Description: " + desc + "\n" +
                "Status: " + status + "\n\n" +

                "MANAGER COMMENT\n" +
                comment;

        JOptionPane.showMessageDialog(this, message, "Request Details", JOptionPane.INFORMATION_MESSAGE);
        }

        private void clearFields(){

            txtEmpID.setText("");
            txtEmpName.setText("");
            txtPosition.setText("");
            cbDepartment.setSelectedIndex(0);
            cbrequest.setSelectedIndex(0);
            txtDescription.setText("");
         
        }
    }