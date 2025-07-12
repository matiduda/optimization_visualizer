package com.algs.gui;

import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.algs.Plotter;
import com.algs.functions.Function2D;

public class Frame extends JFrame {

    private ButtonGroup algsGroup = new ButtonGroup();
    private ButtonGroup funcGroup = new ButtonGroup();

    JRadioButton DEbutton = new JRadioButton("Differential evolution algorithm");
    JRadioButton PSbutton = new JRadioButton("Particle swarm algorithm");

    private ArrayList<RadioFunction> buttons = new ArrayList<RadioFunction>();

    private Plotter plotter;

    public Frame(Plotter plotter, ArrayList<Function2D> functions) {

        this.plotter = plotter;
        this.setTitle("Optimization algorithms");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(getClass().getResource("/logo.png"));
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setLocation(0, 0);
        this.setResizable(false);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        JPanel alghoritms = new JPanel();
        JLabel label = new JLabel("Choose the alghoritm");
        Font f = label.getFont();
        label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        alghoritms.add(label);
        label.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        alghoritms.setLayout(new BoxLayout(alghoritms,
                BoxLayout.Y_AXIS));

        alghoritms.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 165));

        DEbutton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        PSbutton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        algsGroup.add(DEbutton);
        algsGroup.add(PSbutton);

        alghoritms.add(DEbutton);
        alghoritms.add(PSbutton);

        main.add(alghoritms);

        label = new JLabel("Choose function to evaluate");
        label.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        f = label.getFont();
        label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        main.add(label);
        main.setSize(1000, 20);

        for (Function2D function2d : functions) {
            RadioFunction functionButton = new RadioButtonStandard(function2d);
            funcGroup.add(functionButton.getButton());
            buttons.add(functionButton);
            main.add(functionButton.getButton());
        }

        RadioButtonCustom customFunction = new RadioButtonCustom("Custom function");

        funcGroup.add(customFunction.getButton());
        buttons.add(customFunction);
        main.add(customFunction);

        // 'Generate' button
        JButton button = new JButton();

        button.setText("Generate");
        JPanel buttonArea = new JPanel();

        buttonArea.setLayout(new BoxLayout(buttonArea, BoxLayout.Y_AXIS));
        buttonArea.setBorder(BorderFactory.createEmptyBorder(20, 70, 10, 0));
        buttonArea.add(button);

        main.add(buttonArea);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonPressed(e);
            }
        });

        JPanel imageFrame = new JPanel();
        this.add(imageFrame);
        this.add(main);
        this.pack();
        this.setVisible(true);
    }

    private void handleButtonPressed(ActionEvent e) {
        if (funcGroup.getSelection() == null || algsGroup.getSelection() == null) {
            return;
        }
        for (RadioFunction rFunction : buttons) {
            if (funcGroup.getSelection().equals(rFunction.getButton().getModel())) {
                if (algsGroup.getSelection().equals(DEbutton.getModel())) {
                    plotter.plotDE(rFunction.getFunction());
                }
                if (algsGroup.getSelection().equals(PSbutton.getModel())) {
                    plotter.plotPS(rFunction.getFunction());
                }
            }
        }

    }
}
