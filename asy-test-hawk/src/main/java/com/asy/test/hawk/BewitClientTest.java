package com.asy.test.hawk;

import com.wealdtech.hawk.Hawk;
import com.wealdtech.hawk.HawkCredentials;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class BewitClientTest {

    private static String apiUrl = "http://localhost:6600/";


    public static void main(String[] args) throws URISyntaxException, IOException {
        HawkCredentials hawkCredentials = new HawkCredentials.Builder()
                .keyId("hawkKeyId")
                .key("hawkKey1231123123123123")
                .algorithm(HawkCredentials.Algorithm.SHA256)
                .build();

        String bewit = Hawk.generateBewit(hawkCredentials, new URI(apiUrl), 3600L, "");

        String reqUrl = apiUrl + "&bewit=" + bewit;
        System.out.println(reqUrl);

        URL obj = new URL(reqUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        /*BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());*/

        System.exit(0);



    }
}
