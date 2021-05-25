package com.asy.test;

public class SwitchTests {

    public static void main(String[] args) {
        testOldStyleSwitch();
        System.out.println("-testSwitch2-");
        testSwitch2();
        System.out.println("-testSwitch3-");
        testSwitch3();
        System.out.println("-testSwitch:yield-");
        testSwitchYield();
    }

    private static void testOldStyleSwitch() {
        String str = "D";
        switch (str) {
            case "A": {
                System.out.println("it is A!");
            }
            case "B": {
                System.out.println("it is B!");
            }
            case "C": {
                System.out.println("it is C!");
            }
            case "D": {
                System.out.println("it is D!"); // prints until it finds a break!
            }
            case "E": {
                System.out.println("it is E!");
            } case "F": {
                System.out.println("it is F!");
            } case "G": {
                System.out.println("it is G!");
                break;
            }
            case "H": {
                System.out.println("it is H!");
                break;
            }
            default:
                System.out.println("default");
        }
    }

    private static void testSwitch2() {
        String str = "D";
        switch (str) {
            case "A", "B", "C", "D": { // many cases
                System.out.println("Between A-D"); // prints until it finds a break too! So, use ->
            }
            case "E", "F", "G", "H": {
                System.out.println("Between E-H");
                break;
            }
            default:
                System.out.println("default");
        }
    }

    private static void testSwitch3() {
        String str = "D";
        switch (str) {
            case "A", "B", "C", "D" -> { // many cases
                System.out.println("Between A-D"); // prints only this since we used ->
            }
            case "E", "F", "G", "H" -> {
                System.out.println("Between E-H");
                break;
            }
            default ->
                System.out.println("default");
        }
    }

    private static void testSwitchYield() {
        String str = "D";
        String result = 
        switch (str) {
            case "A", "B", "C", "D" -> { // many cases
                yield (true ? "Between A-D" : "sorry"); //basically a return statement
            }
            case "E", "F", "G", "H" -> {
                yield (true ? "Between E-H" : "sorry");
            }
            default -> "n/a";
        };
        System.out.println(result);
    }

}
