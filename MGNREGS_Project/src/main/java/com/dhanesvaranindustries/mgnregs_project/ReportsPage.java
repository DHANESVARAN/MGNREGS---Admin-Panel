package com.dhanesvaranindustries.mgnregs_project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;

public class ReportsPage extends JFrame implements ActionListener {
    JTextField dateField, workerSearchField;
    JButton viewWorkersBtn, todayAttendanceBtn, filterAttendanceBtn, wageStatusBtn, individualWageBtn, discrepanciesBtn, downloadWagesBtn;
    JTable table;
    DefaultTableModel model;
    TableRowSorter<DefaultTableModel> sorter;

    ReportsPage() {
        setTitle("Reports Dashboard");
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        // View all workers
        viewWorkersBtn = new JButton("View All Workers");
        viewWorkersBtn.addActionListener(this);
        panel.add(viewWorkersBtn);

        // Today's attendance
        todayAttendanceBtn = new JButton("Today's Attendance");
        todayAttendanceBtn.addActionListener(this);
        panel.add(todayAttendanceBtn);

        // Filter attendance by date
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField(10);
        panel.add(dateField);
        filterAttendanceBtn = new JButton("Filter Attendance");
        filterAttendanceBtn.addActionListener(this);
        panel.add(filterAttendanceBtn);

        // Wage status
        wageStatusBtn = new JButton("View Wage Status");
        wageStatusBtn.addActionListener(this);
        panel.add(wageStatusBtn);

        // Individual worker's wage record
        panel.add(new JLabel("Worker ID/Aadhar:"));
        workerSearchField = new JTextField(10);
        panel.add(workerSearchField);
        individualWageBtn = new JButton("View Worker Wage");
        individualWageBtn.addActionListener(this);
        panel.add(individualWageBtn);

        // Discrepancy report
        discrepanciesBtn = new JButton("Show Discrepancies");
        discrepanciesBtn.addActionListener(this);
        panel.add(discrepanciesBtn);

        // Download wage report (TXT)
        downloadWagesBtn = new JButton("Export");
        downloadWagesBtn.addActionListener(this);
        panel.add(downloadWagesBtn);

        add(panel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewWorkersBtn) {
            viewAllWorkers();
        } else if (ae.getSource() == todayAttendanceBtn) {
            viewTodayAttendance();
        } else if (ae.getSource() == filterAttendanceBtn) {
            viewAttendanceByDate();
        } else if (ae.getSource() == wageStatusBtn) {
            viewWageStatus();
        } else if (ae.getSource() == individualWageBtn) {
            viewIndividualWage();
        } else if (ae.getSource() == discrepanciesBtn) {
            displayDiscrepancies();
        } else if (ae.getSource() == downloadWagesBtn) {
            generateWageReceiptReportAsTXT();
        }
    }

    // 📋 View All Workers
    public void viewAllWorkers() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT worker_id, name, aadhar_number, phone, work_type FROM workers");
            ResultSet rs = stmt.executeQuery();
            updateTable(rs, new String[]{"Worker ID", "Name", "Aadhar Number", "Phone", "Work Type"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🗓 View Today's Attendance
    public void viewTodayAttendance() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT worker_id, aadhar_number, work_date, check_in_time, check_out_time FROM attendance WHERE work_date = CURDATE()");
            ResultSet rs = stmt.executeQuery();
            updateTable(rs, new String[]{"Worker ID", "Aadhar Number", "Date", "Check-In", "Check-Out"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 📅 Filter Attendance by Date
    public void viewAttendanceByDate() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT worker_id, aadhar_number, work_date, check_in_time, check_out_time FROM attendance WHERE work_date = ?");
            stmt.setString(1, dateField.getText());
            ResultSet rs = stmt.executeQuery();
            updateTable(rs, new String[]{"Worker ID", "Aadhar Number", "Date", "Check-In", "Check-Out"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 💰 View Wage Status
    public void viewWageStatus() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT worker_id, aadhar_number, verified_work_days, amount, payment_status FROM payments");
            ResultSet rs = stmt.executeQuery();
            updateTable(rs, new String[]{"Worker ID", "Aadhar Number", "Verified Work Days", "Amount", "Payment Status"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔍 View Individual Worker Wage Record
    public void viewIndividualWage() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT worker_id, aadhar_number, verified_work_days, amount FROM payments WHERE worker_id = ? OR aadhar_number = ?");
            stmt.setString(1, workerSearchField.getText());
            stmt.setString(2, workerSearchField.getText());
            ResultSet rs = stmt.executeQuery();
            updateTable(rs, new String[]{"Worker ID", "Aadhar Number", "Verified Work Days", "Amount"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🚨 Show Discrepancies
    public void displayDiscrepancies() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT worker_id, COUNT(attendance_id) AS unverified_days FROM attendance WHERE verified = 'No' GROUP BY worker_id");
            ResultSet rs = stmt.executeQuery();
            updateTable(rs, new String[]{"Worker ID", "Unverified Attendance Days"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTable(ResultSet rs, String[] columnNames) throws SQLException {
        model.setRowCount(0);
        model.setColumnIdentifiers(columnNames);
        while (rs.next()) {
            Object[] rowData = new Object[columnNames.length];
            for (int i = 0; i < columnNames.length; i++) {
                rowData[i] = rs.getObject(i + 1);
            }
            model.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        new ReportsPage();
    }

    private void generateWageReceiptReportAsTXT() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}