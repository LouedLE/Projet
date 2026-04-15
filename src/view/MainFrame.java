package view;

import controller.JavazicController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(JavazicController controller) {

        setTitle("Javazic");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(245, 247, 250));

        setContentPane(new LoginPanel(controller, this));

        setVisible(true);
    }
}