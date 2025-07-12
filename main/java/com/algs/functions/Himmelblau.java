package com.algs.functions;

import org.mariuszgromada.math.mxparser.Function;

public class Himmelblau extends Function2D {
    private final String functionName = "Himmelblau's function";

    private final String expression = "(x^2+y-11)^2+(x+y^2-7)^2";

    private final Function function;

    public Himmelblau() {
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