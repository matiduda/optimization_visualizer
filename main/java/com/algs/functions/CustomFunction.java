package com.algs.functions;

import org.mariuszgromada.math.mxparser.Function;

public class CustomFunction extends Function2D {
    private final String expression;
    private final Function function;

    public CustomFunction(String expression, int minX, int maxX, int minY, int maxY) {
        this.expression = expression;
        function = new Function("f(x, y)=" + expression);

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public double calculate(double x, double y) {
        return function.calculate(x, y);
    }

    @Override
    public String getFunctionName() {
        return this.expression;
    }
}
