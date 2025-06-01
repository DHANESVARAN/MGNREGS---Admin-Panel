package com.dhanesvaranindustries.mgnregs_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceTracker extends JFrame implements ActionListener {
    JTextField tfIdentifier;
    JButton workerInBtn, workerOutBtn;

    AttendanceTracker() {
        setTitle("Worker Attendance Tracker");
        setLayout(null);

        JLabel lblIdentifier = new JLabel("Enter Worker ID or Aadhar:");
        lblIdentifier.setBounds(40, 20, 200, 30);
        add(lblIdentifier);

        tfIdentifier = new JTextField();
        tfIdentifier.setBounds(250, 20, 150, 30);
        add(tfIdentifier);

        workerInBtn = new JButton("Worker In");
        workerInBtn.setBounds(50, 70, 150, 40);
        workerInBtn.addActionListener(this);
        add(workerInBtn);

        workerOutBtn = new JButton("Worker Out");
        workerOutBtn.setBounds(220, 70, 150, 40);
        workerOutBtn.addActionListener(this);
        add(workerOutBtn);

        setSize(450, 200);
        setLocation(450, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Connection conn = DBConnection.getConnection();
            String identifier = tfIdentifier.getText();
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            if (ae.getSource() == workerInBtn) {
                PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO attendance (worker_id, aadhar_number, work_date, check_in_time, verified) " +
                    "SELECT worker_id, aadhar_number, ?, ?, 'No' FROM workers WHERE worker_id = ? OR aadhar_number = ? " +
                    "ON DUPLICATE KEY UPDATE check_in_time = ?"
                );
                stmt.setString(1, currentDate.toString());
                stmt.setString(2, currentTime.toString());
                stmt.setString(3, identifier);
                stmt.setString(4, identifier);
                stmt.setString(5, currentTime.toString());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "✅ Worker Checked In!");
            } else if (ae.getSource() == workerOutBtn) {
                PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE attendance SET check_out_time = ?, verified = 'Yes' " +
                    "WHERE worker_id = (SELECT worker_id FROM workers WHERE worker_id = ? OR aadhar_number = ?) " +
                    "AND work_date = ? AND check_out_time IS NULL"
                );
                stmt.setString(1, currentTime.toString());
                stmt.setString(2, identifier);
                stmt.setString(3, identifier);
                stmt.setString(4, currentDate.toString());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "✅ Worker Checked Out!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AttendanceTracker();
    }
}