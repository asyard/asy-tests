package com.asy.test.httpurlconnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpTest {
    public static void main(String[] args) throws Exception {

        String connectionUrl = "https://www.google.com";
        if (args != null && args.length > 0 && !args[0].isEmpty()) {
            connectionUrl = args[0];
        }
        HttpURLConnection connection = null;
        try {
            URL url = new URL(connectionUrl);
            connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine, inputLines = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                inputLines += inputLine;
            }
            System.out.println(inputLines);
            bufferedReader.close();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }


    }
}
