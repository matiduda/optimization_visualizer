package com.algs.alghoritms;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;

import com.algs.functions.Function2D;

public class DE {

    List<Individual> population;

    int POPULATION_SIZE;
    private int currentEpoch = 0;

    // crossover probability [0,1]
    double CROSSOVER_PROBABILITY = 0.4;

    // differential weight [0,2]
    double DIFFERENTIAL_WEIGHT = 1.2d;

    int ITERATION_NO;

    Random random;
    PrintWriter pw;
    Function2D function;

    List<double[]> dimensionList = new LinkedList<>();

    public DE(Function2D function, int particles, int epochs) {
        this.function = function;
        this.POPULATION_SIZE = particles;
        this.ITERATION_NO = epochs;

        double[] dimension1Bounds = new double[2];
        dimension1Bounds[0] = function.getMinX();
        dimension1Bounds[1] = function.getMaxX();

        double[] dimension2Bounds = new double[2];
        dimension2Bounds[0] = function.getMinY();
        dimension2Bounds[1] = function.getMaxY();

        dimensionList.add(dimension1Bounds);
        dimensionList.add(dimension2Bounds);

        random = new Random();
        population = new LinkedList<>();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            Individual individual = new Individual(dimensionList);
            population.add(individual);
        }
    }

    public double fitFunction(Individual aCandidate) {
        return function.calculate(aCandidate.dataValue[0], aCandidate.dataValue[1]);
    }

    public void optimise(int epochStep) {
        for (int iterationCount = 0; iterationCount < epochStep; iterationCount++) {

            int loop = 0;

            while (loop < population.size()) {

                Individual original = null;
                Individual candidate = null;
                boolean boundsHappy;

                do {
                    boundsHappy = true;

                    int x = loop;
                    int a, b, c = -1;

                    do {
                        a = random.nextInt(population.size());
                    } while (x == a);
                    do {
                        b = random.nextInt(population.size());
                    } while (b == x || b == a);
                    do {
                        c = random.nextInt(population.size());
                    } while (c == x || c == a || c == b);

                    Individual individual1 = population.get(a);
                    Individual individual2 = population.get(b);
                    Individual individual3 = population.get(c);

                    Individual noisyRandomCandicate = new Individual(dimensionList);

                    for (int n = 0; n < dimensionList.size(); n++) {
                        noisyRandomCandicate.dataValue[n] = (individual1.dataValue[n]
                                + DIFFERENTIAL_WEIGHT * (individual2.dataValue[n] - individual3.dataValue[n]));
                    }

                    original = population.get(x);
                    candidate = new Individual(dimensionList);

                    for (int n = 0; n < dimensionList.size(); n++) {
                        candidate.dataValue[n] = original.dataValue[n];
                    }

                    int R = random.nextInt(dimensionList.size());

                    for (int n = 0; n < dimensionList.size(); n++) {

                        double crossoverProbability = random.nextDouble();

                        if (crossoverProbability < CROSSOVER_PROBABILITY || n == R) {
                            candidate.dataValue[n] = noisyRandomCandicate.dataValue[n];
                        }

                    }

                    for (int n = 0; n < dimensionList.size(); n++) {
                        if (candidate.dataValue[n] < dimensionList.get(n)[0]
                                || candidate.dataValue[n] > dimensionList.get(n)[1]) {
                            boundsHappy = false;
                        }
                    }

                } while (boundsHappy == false);

                if (fitFunction(original) > fitFunction(candidate)) {
                    population.remove(original);
                    population.add(candidate);
                }
                loop++;
            }

            currentEpoch++;
        }
    }

    public Particle[] getParticles() {
        Particle[] particles = new Particle[POPULATION_SIZE];

        // Create particle list
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Individual individual = population.get(i);

            if (particles[i] == null) {
                particles[i] = new Particle(function);
            }
            particles[i].setPosition(new Vector(individual.dataValue[0], individual.dataValue[1]));
        }

        return particles;
    }

    public class Individual implements Cloneable {

        public double[] dataValue;

        public Individual(List<double[]> dimensionIn) {
            int noDimension = dimensionIn.size();
            dataValue = new double[noDimension];

            for (int dimensionIndex = 0; dimensionIndex < noDimension; dimensionIndex++) {

                double dimensionLowerBound = dimensionIn.get(dimensionIndex)[0];
                double dimensionUpperBound = dimensionIn.get(dimensionIndex)[1];

                DoubleStream valueGenerator = random.doubles(dimensionLowerBound, dimensionUpperBound);

                dataValue[dimensionIndex] = valueGenerator.iterator().nextDouble();
            }
        }

        @Override
        public String toString() {

            String string = "";

            for (int i = 0; i < dataValue.length; i++) {
                string += Double.toString(dataValue[i]);

                if ((i + 1) != dataValue.length) {
                    string += ",";
                }
            }

            return string;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone(); // To change body of generated methods, choose Tools | Templates.
        }
    }

    public Object getEpoch() {
        return currentEpoch;
    }
}
