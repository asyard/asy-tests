package com.asy.test.hawk;

import com.wealdtech.hawk.HawkClient;
import com.wealdtech.hawk.HawkCredentials;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HawkClientTest {

    private static String uri = "http://localhost:6600/";

    public static void main(String[] args) throws IOException, URISyntaxException {
        HawkCredentials hawkCredentials = new HawkCredentials.Builder()
                .keyId("") // ???
                .key("hawkKey1231123123123123")
                .algorithm(HawkCredentials.Algorithm.SHA256)
                .build();

        HawkClient hawkClient = new HawkClient.Builder().credentials(hawkCredentials).build();
        String authorizationHeader = hawkClient.generateAuthorizationHeader(new URI(uri), "GET", null, null, null, null);


        URL obj = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        //con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization", authorizationHeader);

        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        System.exit(0);

    }
}
