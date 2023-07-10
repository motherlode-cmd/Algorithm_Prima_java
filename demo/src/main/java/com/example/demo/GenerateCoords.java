package com.example.demo;

public class GenerateCoords {
    private double centerX = 0;
    private double centerY = 0;

    private int n;
    private double r = 10;
    public GenerateCoords(double centerX, double centerY, double r, int n) {
        this.centerY = centerY;
        this.centerX = centerX;
        this.r = r;
        this.n = n;
    }
    public double getX(int i) {
        return r * Math.cos(2 * 3.14159 * i / (double) n) + centerX;
    }
    public double getY(int i) {
        return r * Math.sin(2 * 3.14159 * i / (double) n) + centerY;
    }
}
