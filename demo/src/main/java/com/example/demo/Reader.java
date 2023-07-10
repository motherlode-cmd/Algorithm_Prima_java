package com.example.demo;

public abstract class Reader {
    protected String v2 = "";
    protected double w = 0;

    protected String v1 = "";

    protected int n = 0;

    public int getN() {
        return n;
    }
    public void setV1(String v1) {
        this.v1 = v1;
    }

    public void setW(double w) {
        this.w = w;
    }

    public String getV1() {
        return v1;
    }

    public double getW() {
        return w;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public abstract void read();
}
