package com.algs.functions;

public abstract class Function2D implements Interface2D {
    protected int minX;
    protected int maxX;
    protected int minY;
    protected int maxY;

    public Function2D() {
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }
}
