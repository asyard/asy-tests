package com.asy.test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Java9Tests {

    public static void main(String[] args) {

        List<String> oldStyleImmutableList = Collections.unmodifiableList(Arrays.asList("A", "B", "C", "D"));

        List<String> list1 = List.of("A", "B", "C", "D", "D");
        //Set<String> set1 = Set.of("A", "B", "C", "D", "D"); //IllegalArgumentException : duplicate element: D
        Set<String> set1 = Set.of("A", "B", "C", "D");
        //Map.of("1", "A", "2", "B", "3", "C", "4", "D", "4", "E");//duplicate key: 4
        Map<String, String> map1 = Map.of("1", "A", "2", "B", "3", "C", "4", "D");
        System.out.println(map1);

        //list1.add("E"); //UnsupportedOperationException
        //list1.sort(Comparator.reverseOrder()); //UnsupportedOperationException

        ArrayList<String> strings =new ArrayList<>(list1);
        strings.add("test_newadded");
        System.out.println(strings);

        //List.copyOf(list1).add("oh!"); // UnsupportedOperationException for ImmutableCollections since copyOf creates unmodifiable collection

        ArrayList<String> strings2 =new ArrayList<>(List.copyOf(list1));
        strings2.add("newlist_newadded");
        System.out.println(strings2);


        try (DataInputStream dis = new DataInputStream(new FileInputStream(new File("nonexistingfile.txt")))) {
            System.out.println((dis.readLong() + 5000L));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }


}
