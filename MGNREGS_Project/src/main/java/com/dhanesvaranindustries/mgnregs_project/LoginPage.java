package com.dhanesvaranindustries.mgnregs_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {
    
    JTextField tfUsername, tfPassword;
    
    LoginPage() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(40, 20, 100, 30);
        add(lblUsername);
        
        tfUsername = new JTextField();
        tfUsername.setBounds(150, 20, 150, 30);
        add(tfUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(40, 70, 100, 30);
        add(lblPassword);
        
        tfPassword = new JTextField();
        tfPassword.setBounds(150, 70, 150, 30);
        add(tfPassword);

        JButton login = new JButton("LOGIN");
        login.setBounds(150, 140, 150, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        setSize(450, 250);
        setLocation(450, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String username = tfUsername.getText();
            String password = tfPassword.getText();
            
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM users WHERE username = '"+username+"' AND password = '"+password+"'";
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, " Login Successful!");
                setVisible(false);
                new Dashboard();
            } else {
                JOptionPane.showMessageDialog(null, " Invalid username or password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}