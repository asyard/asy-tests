package com.asy.test;

public class TextblockTests {
    public static void main(String[] args) {
        testTextBlock();
    }

    private static void testTextBlock() {
        String longtext1 = """
                lorem 
                ipsum
                1 2 "3"
                <a href="test">test</a>
                hello!
                """;

        System.out.println(longtext1);

        System.out.println("--- ");
        String ipsum = longtext1.substring(25);
        System.out.println(ipsum);
        System.out.println("--- ");
        System.out.println(longtext1.replace("2", """
                two
                or 
                2!
                """));
    }
}
