package com.algs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;

import com.algs.alghoritms.DE;
import com.algs.alghoritms.Particle;
import com.algs.alghoritms.Swarm;
import com.algs.functions.Function2D;
import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Plotter {

    Plot plt;

    private final int plotDPI = 100;
    private final int plotSteps = 200;

    Swarm swarm;
    DE de;

    int epochsStep = 5;

    int maxEpochs = 10;

    private final int gifDelayMs = 1500;

    private Function2D funciton;

    List<Double> x;
    List<Double> y;
    NumpyUtils.Grid<Double> grid;

    List<List<Double>> zCalced;

    public Plotter() {
    }

    public void plotPS(Function2D f) {

        JFrame frame = new JFrame("Calculating, please wait...");
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        this.funciton = f;

        int particles;

        particles = 50;

        this.swarm = new Swarm(f, particles, epochsStep);

        genetareData();

        for (int i = 0; i < maxEpochs; i++) {

            plt = Plot.create();
            plotFunciton();
            plotParticles();

            plt.savefig(String.format("tmp/plot%d.png", i)).dpi(plotDPI);

            try {
                plt.executeSilently();
            } catch (IOException | PythonExecutionException e1) {
                e1.printStackTrace();
            }

            swarm.runNepochs(epochsStep);
            frame.setTitle(getProgressBar(i));
        }

        try {
            makeAnimationFromSavedFrames();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Output the gif to the frame
        frame.setTitle(f.getFunctionName());
        ImageIcon imgIcon = new ImageIcon("tmp/output.gif");
        JLabel labelImage = new JLabel(imgIcon);
        frame.add(labelImage);
        frame.pack();
    }

    public void plotDE(Function2D f) {

        JFrame frame = new JFrame("Calculating, please wait...");
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        this.funciton = f;
        int particles;
        particles = 50;
        this.de = new DE(f, particles, epochsStep);

        genetareData();

        for (int i = 0; i < maxEpochs; i++) {

            plt = Plot.create();
            plotFunciton();
            plotParticlesDE(de.getParticles());
            de.optimise(epochsStep);

            plt.savefig(String.format("tmp/plot%d.png", i)).dpi(plotDPI);

            try {
                plt.executeSilently();
            } catch (IOException | PythonExecutionException e1) {
                e1.printStackTrace();
            }

            frame.setTitle(getProgressBar(i));
        }

        try {
            makeAnimationFromSavedFrames();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Output the gif to the frame
        frame.setTitle(f.getFunctionName());
        ImageIcon imgIcon = new ImageIcon("tmp/output.gif");
        JLabel labelImage = new JLabel(imgIcon);
        frame.add(labelImage);
        frame.pack();
    }

    private String getProgressBar(int currentEpoch) {
        String bar = "";

        for (int i = 0; i < currentEpoch; i++) {
            bar += "⬛";
        }

        for (int i = 0; i < maxEpochs - currentEpoch; i++) {
            bar += "⬜";
        }

        bar += String.format(" %d %%", (100 * currentEpoch) / maxEpochs);
        return bar;
    }

    private void genetareData() {
        x = NumpyUtils.linspace(funciton.getMinX(), funciton.getMaxX(), plotSteps);
        y = NumpyUtils.linspace(funciton.getMinY(), funciton.getMaxY(), plotSteps);
        grid = NumpyUtils.meshgrid(x, y);
        zCalced = grid.calcZ((xi, yj) -> funciton.calculate(xi, yj));
    }

    private void plotFunciton() {

        plt.pcolor().add(x, y, zCalced).alpha(0.8);

        Double maxZ = zCalced.get(0).get(0);
        Double minZ = zCalced.get(0).get(0);

        for (List<Double> list : zCalced) {
            for (Double d : list) {
                if (d > maxZ) {
                    maxZ = d;
                }
                if (d < minZ) {
                    minZ = d;
                }
            }
        }
    }

    private void plotParticles() {
        Particle[] particles_arr = this.swarm.getParticles();

        // Draw particles
        List<Double> xs = new ArrayList<Double>();
        List<Double> ys = new ArrayList<Double>();

        for (Particle p : particles_arr) {
            xs.add(p.getPosition().getX());
            ys.add(p.getPosition().getY());
        }

        plt.plot()
                .add(xs, ys, "+y")
                .label(String.format("Particle swarm, epoch %d", swarm.getEpoch()));
        plt.legend().loc("upper right");

    }

    private void plotParticlesDE(Particle[] particles_arr) {
        // Draw particles
        List<Double> xs = new ArrayList<Double>();
        List<Double> ys = new ArrayList<Double>();

        for (Particle p : particles_arr) {
            xs.add(p.getPosition().getX());
            ys.add(p.getPosition().getY());
        }

        plt.plot()
                .add(xs, ys, "+y")
                .label(String.format("Diff. evolution, epoch: %d", de.getEpoch()));
        plt.legend().loc("upper right");
    }

    public void makeAnimationFromSavedFrames() throws IOException {

        final int argslen = this.maxEpochs;

        File first = new File("tmp/plot0.png");
        BufferedImage firstImage = ImageIO.read(first);
        first.delete();

        ImageOutputStream output = new FileImageOutputStream(new File("tmp/output.gif"));

        GifSequenceWriter writer = new GifSequenceWriter(output, firstImage.getType(), gifDelayMs, true);

        writer.writeToSequence(firstImage);
        for (int i = 1; i < argslen; i++) {
            File tmp = new File("tmp/plot" + i + ".png");
            BufferedImage nextImage = ImageIO.read(tmp);
            writer.writeToSequence(nextImage);
            tmp.delete();
        }
        writer.close();
        output.close();
    }
}
