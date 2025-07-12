package com.algs.alghoritms;

import java.util.concurrent.ThreadLocalRandom;

import com.algs.functions.Function2D;

/**
 * Represents a particle from the Particle Swarm Optimization algorithm.
 */
public class Particle {

    private Vector position; // Current position.
    private Vector velocity;
    private Vector bestPosition; // Personal best solution.
    private double bestEval; // Personal best value.
    private Function2D function; // The evaluation function to use.

    /**
     * Construct a Particle with a random starting position.
     * 
     */
    Particle(Function2D function) {
        this.function = function;
        position = new Vector();
        velocity = new Vector();
        setRandomPosition(function);
        bestPosition = velocity.clone();
        bestEval = eval();
    }

    /**
     * The evaluation of the current position.
     * 
     * @return the evaluation
     */
    private double eval() {
        return function.calculate(position.getX(), position.getY());
    }

    private void setRandomPosition(Function2D f) {
        int x = rand(f.getMinX(), f.getMaxX());
        int y = rand(f.getMinY(), f.getMaxY());
        position.set(x, y);
    }

    /**
     * Generate a random number between a certain range.
     * 
     * @param beginRange the minimum value (inclusive)
     * @param endRange   the maximum value (exclusive)
     * @return the randomly generated value
     */
    private static int rand(int beginRange, int endRange) {
        return ThreadLocalRandom.current().nextInt(beginRange, endRange + 1);
    }

    /**
     * Update the personal best if the current evaluation is better.
     */
    void updatePersonalBest() {
        double eval = eval();
        if (eval < bestEval) {
            bestPosition = position.clone();
            bestEval = eval;
        }
    }

    /**
     * Get a copy of the position of the particle.
     * 
     * @return the x position
     */
    public Vector getPosition() {
        return position.clone();
    }

    /**
     * Get a copy of the velocity of the particle.
     * 
     * @return the velocity
     */
    Vector getVelocity() {
        return velocity.clone();
    }

    /**
     * Get a copy of the personal best solution.
     * 
     * @return the best position
     */
    Vector getBestPosition() {
        return bestPosition.clone();
    }

    /**
     * Get the value of the personal best solution.
     * 
     * @return the evaluation
     */
    double getBestEval() {
        return bestEval;
    }

    /**
     * Update the position of a particle by adding its velocity to its position.
     */
    void updatePosition() {
        this.position.add(velocity);
        // Limit position bounds
        if (this.position.getX() > function.getMaxX()) {
            this.position.setX(function.getMaxX());
        }
        if (this.position.getX() < function.getMinX()) {
            this.position.setX(function.getMinX());
        }
        if (this.position.getY() > function.getMaxY()) {
            this.position.setY(function.getMaxY());
        }
        if (this.position.getY() < function.getMinY()) {
            this.position.setY(function.getMinY());
        }
    }

    /**
     * Set the velocity of the particle.
     * 
     * @param velocity the new velocity
     */
    void setVelocity(Vector velocity) {
        this.velocity = velocity.clone();
    }

    public void setPosition(Vector position2) {
        this.position.set(position2.getX(), position2.getY());
    }
}
