package view;

import controller.JavazicController;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(JavazicController controller) {

        setTitle("Javazic");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new LoginPanel(controller, this));

        setVisible(true);
    }
}