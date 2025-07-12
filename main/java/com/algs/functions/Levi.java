package com.algs.functions;

import org.mariuszgromada.math.mxparser.Function;

public class Levi extends Function2D {
    private final String functionName = "Levi function N. 13";

    private final String expression = "sin(3pix)*sin(2pix)+(x-1)^2(1+sin(3piy)*sin(3piy))+(y-1)^2(1+sin(2piy)*sin(2piy))";

    private final Function function;

    public Levi() {
        function = new Function("f(x, y)=" + expression);

        this.minX = -10;
        this.maxX = 10;
        this.minY = -10;
        this.maxY = 10;
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