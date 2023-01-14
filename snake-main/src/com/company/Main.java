package com.company;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame f = new JFrame("Snake");
        JButton bEasyMode = new JButton("Poziom prosty");
        JButton bHardMode = new JButton("Poziom trudny");

        bEasyMode.setBackground(new Color(64, 145, 108));
        bEasyMode.setForeground(Color.WHITE);
        bEasyMode.setFont(new Font("Serif", Font.ITALIC, 40));
        bEasyMode.setBounds(300, 400,400,100);

        bHardMode.setBackground(new Color(27, 67, 50));
        bHardMode.setForeground(Color.WHITE);
        bHardMode.setFont(new Font("Serif", Font.ITALIC, 40));
        bHardMode.setBounds(300, 600,400,100);

        f.add(bEasyMode);
        f.add(bHardMode);

        CardLayout cardLayout = new CardLayout();
        EasyMode game = new EasyMode();
        HardMode game2 = new HardMode();

        JPanel mainPanel = new JPanel(cardLayout);
        mainPanel.add(game, "easy mode");
        mainPanel.add(game2, "hard mode");

        f.add(mainPanel);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.pack();
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        bEasyMode.addActionListener(arg -> {
            cardLayout.show(mainPanel, "easy mode");
            bEasyMode.setVisible(false);
            bHardMode.setVisible(false);
            game.start();
        });

        bHardMode.addActionListener(arg -> {
            cardLayout.show(mainPanel, "hard mode");
            bEasyMode.setVisible(false);
            bHardMode.setVisible(false);
            game2.start();
        });

        f.setVisible(true);
    }
}

