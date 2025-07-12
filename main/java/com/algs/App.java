package com.algs;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.algs.gui.UserInterface;

public class App {
    public static void main(String[] args) throws IOException {
        Files.createDirectories(Paths.get("tmp"));
        final Plotter plotter = new Plotter();
        new UserInterface(plotter);
    }
}