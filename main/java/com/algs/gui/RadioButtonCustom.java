package com.algs.gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.algs.functions.CustomFunction;
import com.algs.functions.Function2D;

public class RadioButtonCustom extends RadioFunction {

    JRadioButton button;
    JTextField textField;

    JSpinner lowerX;
    JSpinner upperX;

    JSpinner lowerY;
    JSpinner upperY;

    RadioButtonCustom(String name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel radioPanel = new JPanel();

        button = new JRadioButton(name);
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        textField = new JTextField(7);
        textField.setMaximumSize(textField.getPreferredSize());

        radioPanel.add(button);
        radioPanel.add(textField);

        // X Limits Spinners

        JPanel valuesX = new JPanel();

        SpinnerModel lowerXmodel = new SpinnerNumberModel(-2, -100, 100, 1);
        lowerX = new JSpinner(lowerXmodel);
        valuesX.add(new JLabel("X min: "));
        valuesX.add(lowerX);

        SpinnerModel upperXmodel = new SpinnerNumberModel(2, -100, 100, 1);
        upperX = new JSpinner(upperXmodel);
        valuesX.add(new JLabel("X max: "));
        valuesX.add(upperX);

        // Y Limits Spinners

        JPanel valuesY = new JPanel();

        SpinnerModel lowerYmodel = new SpinnerNumberModel(-1, -100, 100, 1);
        lowerY = new JSpinner(lowerYmodel);
        valuesY.add(new JLabel("Y min: "));
        valuesY.add(lowerY);

        SpinnerModel upperYmodel = new SpinnerNumberModel(3, -100, 100, 1);
        upperY = new JSpinner(upperYmodel);
        valuesY.add(new JLabel("Y max: "));
        valuesY.add(upperY);

        // errorLabel = new JPanel();

        add(radioPanel);
        add(valuesX);
        add(valuesY);
    }

    @Override
    public JRadioButton getButton() {
        return button;
    }

    @Override
    public Function2D getFunction() {
        // Generate custom function from user input

        int minX = (Integer) lowerX.getValue();
        int maxX = (Integer) upperX.getValue();
        int minY = (Integer) lowerY.getValue();
        int maxY = (Integer) upperY.getValue();

        if (!(maxX > minX) || !(maxY > minY)) {

        }

        return new CustomFunction(textField.getText(), minX, maxX, minY, maxY);
    }

}