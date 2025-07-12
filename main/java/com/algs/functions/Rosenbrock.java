package com.algs.functions;

import org.mariuszgromada.math.mxparser.Function;

public class Rosenbrock extends Function2D {
    private final String functionName = "Rosenbrock function";

    private final int a = 1;
    private final int b = 100;

    private final String expression = String.format("(%d - x)^2 + %d * (y - x^2)^2", a, b);

    private final Function function;

    public Rosenbrock() {
        function = new Function("f(x, y)=" + expression);

        this.minX = -2;
        this.maxX = 2;
        this.minY = -1;
        this.maxY = 3;
    }

    @Override
    public double calculate(double x, double y) {
        return function.calculate(x, y);
    }

    @Override
    public String getFunctionName() {
        return this.functionName;
    }
}