package com.algs.gui;

import javax.swing.BorderFactory;
import javax.swing.JRadioButton;

import com.algs.functions.Function2D;

public class RadioButtonStandard extends RadioFunction {

    RadioButtonStandard(Function2D function) {
        button = new JRadioButton(function.getFunctionName());
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.function = function;
    }

    @Override
    public JRadioButton getButton() {
        return this.button;
    }

    @Override
    public Function2D getFunction() {
        return this.function;
    }
}