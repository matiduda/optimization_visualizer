package com.algs.functions;

import org.mariuszgromada.math.mxparser.Function;

public class Ackley extends Function2D {
    private final String functionName = "Ackley function";

    private final String expression = "-20 * e^(-0.2*sqrt(0.5*(x^2+y^2))) - e^(0.5*(cos(2*pi*x)+cos(2*pi*y))) + e + 20";

    private final Function function;

    public Ackley() {
        function = new Function("f(x, y)=" + expression);

        this.minX = -5;
        this.maxX = 5;
        this.minY = -5;
        this.maxY = 5;
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