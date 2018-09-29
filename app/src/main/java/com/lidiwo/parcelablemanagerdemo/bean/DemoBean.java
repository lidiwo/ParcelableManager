package com.lidiwo.parcelablemanagerdemo.bean;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/9/27 15:47
 * @Company：智能程序员
 * @Description： *****************************************************
 */
public class DemoBean {
    private int a;
    private byte b;
    private float c;
    private double d;
    private long e;
    private boolean f;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public float getC() {
        return c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public long getE() {
        return e;
    }

    public void setE(long e) {
        this.e = e;
    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }



    protected String getStr(){
        return "DemoBean{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", e=" + e +
                ", f=" + f +
                '}';
    }
}
