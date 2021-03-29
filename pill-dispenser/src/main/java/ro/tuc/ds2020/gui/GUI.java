package ro.tuc.ds2020.gui;

import javax.swing.*;

public class GUI {

    private Login loginGui;
    private PillDispenserGui pillDispenserGui;

    public GUI() {
    }

    public void start(){

        loginGui = new Login();

        JFrame mainFrame = new JFrame("Login");
        mainFrame.setContentPane(loginGui.mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
