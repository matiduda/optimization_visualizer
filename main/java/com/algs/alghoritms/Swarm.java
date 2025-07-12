package com.algs.alghoritms;

import java.util.Random;

import com.algs.functions.Function2D;

/**
 * Represents a swarm of particles from the Particle Swarm Optimization
 * algorithm.
 */
public class Swarm {

    private int numOfParticles;

    private int currentEpoch = 0;

    private double inertia, cognitiveComponent, socialComponent;
    private Vector bestPosition;
    private double bestEval;
    private Function2D function; // The function to search.

    public static final double DEFAULT_INERTIA = 0.729844;
    public static final double DEFAULT_COGNITIVE = 1.496180; // Cognitive component.
    public static final double DEFAULT_SOCIAL = 1.496180; // Social component.

    private Particle[] particles_array;

    /**
     * When Particles are created they are given a random position.
     * The random position is selected from a specified range.
     * If the begin range is 0 and the end range is 10 then the
     * value will be between 0 (inclusive) and 10 (exclusive).
     */

    /**
     * Construct the Swarm with default values.
     * 
     * @param particles the number of particles to create
     * @param epochs    the number of generations
     */
    public Swarm(Function2D function, int particles, int epochs) {
        this(function, particles, DEFAULT_INERTIA, DEFAULT_COGNITIVE, DEFAULT_SOCIAL);
    }

    /**
     * Construct the Swarm with custom values.
     * 
     * @param particles the number of particles to create
     * @param epochs    the number of generations
     * @param inertia   the particles resistance to change
     * @param cognitive the cognitive component or introversion of the particle
     * @param social    the social component or extroversion of the particle
     */
    public Swarm(Function2D function, int particles, double inertia, double cognitive, double social) {
        this.numOfParticles = particles;
        this.inertia = inertia;
        this.cognitiveComponent = cognitive;
        this.socialComponent = social;
        this.function = function;
        double infinity = Double.POSITIVE_INFINITY;
        bestPosition = new Vector(infinity, infinity);
        bestEval = Double.POSITIVE_INFINITY;

        this.particles_array = initialize();
    }

    public void runNepochs(int e) {
        for (int i = 0; i < e; i++) {
            runOneEpoch();
        }
    }

    public Particle[] getParticles() {
        return this.particles_array;
    }

    /**
     * Execute the algorithm.
     */
    public Particle[] runOneEpoch() {

        double oldEval = bestEval;

        if (bestEval < oldEval) {
            oldEval = bestEval;
        }

        for (Particle p : particles_array) {
            p.updatePersonalBest();
            updateGlobalBest(p);
        }

        for (Particle p : particles_array) {
            updateVelocity(p);
            p.updatePosition();
        }
        currentEpoch++;
        return particles_array;
    }

    public int getEpoch() {
        return this.currentEpoch;
    }

    /**
     * Create a set of particles, each with random starting positions.
     * 
     * @return an array of particles
     */
    private Particle[] initialize() {
        Particle[] particles = new Particle[numOfParticles];
        for (int i = 0; i < numOfParticles; i++) {
            Particle particle = new Particle(function);
            particles[i] = particle;
            updateGlobalBest(particle);
        }
        return particles;
    }

    /**
     * Update the global best solution if a the specified particle has
     * a better solution
     * 
     * @param particle the particle to analyze
     */
    private void updateGlobalBest(Particle particle) {
        if (particle.getBestEval() < bestEval) {
            bestPosition = particle.getBestPosition();
            bestEval = particle.getBestEval();
        }
    }

    /**
     * Update the velocity of a particle using the velocity update formula
     * 
     * @param particle the particle to update
     */
    private void updateVelocity(Particle particle) {
        Vector oldVelocity = particle.getVelocity();
        Vector pBest = particle.getBestPosition();
        Vector gBest = bestPosition.clone();
        Vector pos = particle.getPosition();

        Random random = new Random();
        double r1 = random.nextDouble();
        double r2 = random.nextDouble();

        // The first product of the formula.
        Vector newVelocity = oldVelocity.clone();
        newVelocity.mul(inertia);

        // The second product of the formula.
        pBest.sub(pos);
        pBest.mul(cognitiveComponent);
        pBest.mul(r1);
        newVelocity.add(pBest);

        // The third product of the formula.
        gBest.sub(pos);
        gBest.mul(socialComponent);
        gBest.mul(r2);
        newVelocity.add(gBest);

        particle.setVelocity(newVelocity);
    }

}
