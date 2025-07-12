package com.algs.functions;

import org.mariuszgromada.math.mxparser.Function;

public class Schaffer extends Function2D {
    private final String functionName = "Schaffer function N. 4";

    private final String expression = "(sin(x^2-y^2)sin(x^2-y^2)-0.5)/((1+0.001*(x^2+y^2))^2)";

    private final Function function;

    public Schaffer() {
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