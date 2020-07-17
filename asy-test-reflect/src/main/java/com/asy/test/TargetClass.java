package com.asy.test;

public class TargetClass {

    private static final int staticfinalint = 5;
    private static final boolean staticfinalboolean = false;

    public static boolean isStaticfinalboolean() {
        return staticfinalboolean;
    }

    public static int getStaticfinalint() {
        return staticfinalint;
    }

    private int a;
    private String aStr;

    public int b;
    public String bStr;

    private static int c;
    private static String cStr;

    public static int d;
    public static String dStr;

    private static final int e = 5;
    private static final String eStr = "E";

    public TargetClass(int a, String aStr, int b, String bStr, int pc, String pcStr, int pd, String pdStr) {
        this.a = a;
        this.aStr = aStr;
        this.b = b;
        this.bStr = bStr;
        c = pc;
        cStr = pcStr;

    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getaStr() {
        return aStr;
    }

    public void setaStr(String aStr) {
        this.aStr = aStr;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getbStr() {
        return bStr;
    }

    public void setbStr(String bStr) {
        this.bStr = bStr;
    }

    public static int getC() {
        return c;
    }

    public static void setC(int c) {
        TargetClass.c = c;
    }

    public static String getcStr() {
        return cStr;
    }

    public static void setcStr(String cStr) {
        TargetClass.cStr = cStr;
    }

    public static int getD() {
        return d;
    }

    public static void setD(int d) {
        TargetClass.d = d;
    }

    public static String getdStr() {
        return dStr;
    }

    public static void setdStr(String dStr) {
        TargetClass.dStr = dStr;
    }

    public static int getE() {
        return e;
    }

    public static String geteStr() {
        return eStr;
    }
}
