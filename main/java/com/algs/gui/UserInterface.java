package com.algs.gui;

import java.util.ArrayList;

import javax.swing.UIManager;

import com.algs.Plotter;
import com.algs.functions.Ackley;
import com.algs.functions.Eggholder;
import com.algs.functions.Function2D;
import com.algs.functions.Himmelblau;
import com.algs.functions.Levi;
import com.algs.functions.Rosenbrock;
import com.algs.functions.Schaffer;
import com.formdev.flatlaf.FlatDarkLaf;

public class UserInterface {

    private ArrayList<Function2D> functions = new ArrayList<Function2D>();

    /**
     * @param plotter
     */
    public UserInterface(Plotter plotter) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // Add functions
        this.addFunction(new Rosenbrock());
        this.addFunction(new Ackley());
        this.addFunction(new Levi());
        this.addFunction(new Himmelblau());
        this.addFunction(new Eggholder());
        this.addFunction(new Schaffer());

        new Frame(plotter, functions);
    }

    public void addFunction(Function2D funciton) {
        functions.add(funciton);
    }
}