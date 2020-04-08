package com.asy.test.httpurlconnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpTest {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://www.google.com");
        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine, inputLines = "";
        while ((inputLine = bufferedReader.readLine()) != null) {
            inputLines += inputLine;
        }
        System.out.println(inputLines);
        bufferedReader.close();
    }
}
