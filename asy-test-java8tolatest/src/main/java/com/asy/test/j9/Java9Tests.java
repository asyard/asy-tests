package com.asy.test.j9;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Java9Tests {

    public static void main(String[] args) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(new File("nonexistingfile.txt")))) {
            System.out.println((dis.readLong() + 5000L));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
