package com.dhanesvaranindustries.mgnregs_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SplashScreen extends JFrame implements ActionListener {

    SplashScreen() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("MGNREGS - Admin Pannel");
        heading.setBounds(300, 30, 900, 50);
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        heading.setForeground(Color.RED);
        add(heading);

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/mgnregs_logo.jpg"));
        Image img = icon.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(img));
        image.setBounds(150, 100, 600, 300);
        add(image);

        JButton proceed = new JButton("CONTINUE TO LOGIN");
        proceed.setBounds(350, 450, 250, 50);
        proceed.setBackground(Color.BLACK);
        proceed.setForeground(Color.WHITE);
        proceed.addActionListener(this);
        add(proceed);

        setSize(900, 600);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new LoginPage();
    }

    public static void main(String[] args) {
        new SplashScreen();
    }
}