package com.algs.gui;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.algs.functions.Function2D;

abstract class RadioFunction extends JPanel implements InterfaceRadio {
    protected JRadioButton button;
    protected Function2D function;
}
