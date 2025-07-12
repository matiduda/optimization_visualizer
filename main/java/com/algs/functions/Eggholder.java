package com.algs.functions;

import org.mariuszgromada.math.mxparser.Function;

public class Eggholder extends Function2D {
    private final String functionName = "Eggholder function";

    private final String expression = "-(y+47)sin(sqrt(abs(x/2+(y+47))))-xsin(sqrt(abs(x-(y+47))))";

    private final Function function;

    public Eggholder() {
        function = new Function("f(x, y)=" + expression);

        this.minX = -512;
        this.maxX = 512;
        this.minY = -512;
        this.maxY = 512;
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