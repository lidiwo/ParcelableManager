package com.lidiwo.parcelablemanagerdemo.bean;

import android.util.SparseArray;

import com.lidiwo.parcelable.annotation.Parcelable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/9/26 10:49
 * @Company：智能程序员
 * @Description： *****************************************************
 */
@Parcelable
public class StudentBean extends DemoBean {
    private String g;
    private Integer h;
    private Map<String,TeacherBean> i;
    private Short j;
    private Long k;
    private Float l;
    private Double m;
    private Boolean n;
    private CharSequence o;
    private List<TeacherBean> p;
    private SparseArray<TeacherBean> q;
    private boolean[] r;
    private byte[] s;
    private String[] t;
    private CharSequence[] u;
    private int[] v;
    private long[] w;
    private Byte x;
    private double[] y;


    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Map<String, TeacherBean> getI() {
        return i;
    }

    public void setI(Map<String, TeacherBean> i) {
        this.i = i;
    }

    public Short getJ() {
        return j;
    }

    public void setJ(Short j) {
        this.j = j;
    }

    public Long getK() {
        return k;
    }

    public void setK(Long k) {
        this.k = k;
    }

    public Float getL() {
        return l;
    }

    public void setL(Float l) {
        this.l = l;
    }

    public Double getM() {
        return m;
    }

    public void setM(Double m) {
        this.m = m;
    }

    public Boolean getN() {
        return n;
    }

    public void setN(Boolean n) {
        this.n = n;
    }

    public CharSequence getO() {
        return o;
    }

    public void setO(CharSequence o) {
        this.o = o;
    }

    public List<TeacherBean> getP() {
        return p;
    }

    public void setP(List<TeacherBean> p) {
        this.p = p;
    }

    public SparseArray<TeacherBean> getQ() {
        return q;
    }

    public void setQ(SparseArray<TeacherBean> q) {
        this.q = q;
    }

    public boolean[] getR() {
        return r;
    }

    public void setR(boolean[] r) {
        this.r = r;
    }

    public byte[] getS() {
        return s;
    }

    public void setS(byte[] s) {
        this.s = s;
    }

    public String[] getT() {
        return t;
    }

    public void setT(String[] t) {
        this.t = t;
    }

    public CharSequence[] getU() {
        return u;
    }

    public void setU(CharSequence[] u) {
        this.u = u;
    }

    public int[] getV() {
        return v;
    }

    public void setV(int[] v) {
        this.v = v;
    }

    public long[] getW() {
        return w;
    }

    public void setW(long[] w) {
        this.w = w;
    }

    public Byte getX() {
        return x;
    }

    public void setX(Byte x) {
        this.x = x;
    }

    public double[] getY() {
        return y;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return  getStr()+ "StudentBean{" +
                "g='" + g + '\'' +
                ", h=" + h +
                ", i=" + i +
                ", j=" + j +
                ", k=" + k +
                ", l=" + l +
                ", m=" + m +
                ", n=" + n +
                ", o=" + o +
                ", p=" + p +
                ", q=" + q +
                ", r=" + Arrays.toString(r) +
                ", s=" + Arrays.toString(s) +
                ", t=" + Arrays.toString(t) +
                ", u=" + Arrays.toString(u) +
                ", v=" + Arrays.toString(v) +
                ", w=" + Arrays.toString(w) +
                ", x=" + x +
                ", y=" + Arrays.toString(y) +
                '}';
    }
}
