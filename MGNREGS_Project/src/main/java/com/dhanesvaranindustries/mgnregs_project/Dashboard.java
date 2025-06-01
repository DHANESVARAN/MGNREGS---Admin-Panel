package com.dhanesvaranindustries.mgnregs_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.dhanesvaranindustries.mgnregs_project.RegisterWorker;
import com.dhanesvaranindustries.mgnregs_project.AttendanceTracker;
import com.dhanesvaranindustries.mgnregs_project.PaymentProcessing;
import com.dhanesvaranindustries.mgnregs_project.ReportsPage;
import com.dhanesvaranindustries.mgnregs_project.AdminPanel;
import com.dhanesvaranindustries.mgnregs_project.WorkAllocation;
import com.dhanesvaranindustries.mgnregs_project.Utils;

public class Dashboard extends JFrame implements ActionListener {
    
    JButton registerWorkers, markAttendance, processPayments, generateReports, adminPanel;

    Dashboard() {
        setLayout(null);
        
        JLabel heading = new JLabel("MGNREGS Dashboard");
        heading.setBounds(190, 20, 600, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 30));
        add(heading);

        registerWorkers = new JButton("Register Worker");
        registerWorkers.setBounds(250, 80, 200, 40);
        registerWorkers.addActionListener(this);
        add(registerWorkers);

        markAttendance = new JButton("Mark Attendance");
        markAttendance.setBounds(250, 140, 200, 40);
        markAttendance.addActionListener(this);
        add(markAttendance);

        processPayments = new JButton("Process Payments");
        processPayments.setBounds(250, 200, 200, 40);
        processPayments.addActionListener(this);
        add(processPayments);

        generateReports = new JButton("Generate Reports");
        generateReports.setBounds(250, 260, 200, 40);
        generateReports.addActionListener(this);
        add(generateReports);

        adminPanel = new JButton("Admin Panel");
        adminPanel.setBounds(250, 320, 200, 40);
        adminPanel.addActionListener(this);
        add(adminPanel);

       

        

        setSize(700, 550);
        setLocation(400, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == registerWorkers) {
            new RegisterWorker();
        } else if (ae.getSource() == markAttendance) {
            new AttendanceTracker();
        } else if (ae.getSource() == processPayments) {
            PaymentProcessing.processPayment();
        } else if (ae.getSource() == generateReports) {
            new ReportsPage();
        } else if (ae.getSource() == adminPanel) {
            new AdminPanel();
        
        
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}