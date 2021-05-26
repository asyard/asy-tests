package com.asy.test;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class HttpClientTests {

    public static void main(String[] args) throws Exception {
        testHttpClient();
    }

    private static void testHttpClient() throws Exception {
        // default:http/2. downgraded to http/1.1  if target does not support
        // can be used both for async and sync ops

        System.out.println("--- sync ---");
        HttpClient httpClient1 = HttpClient.newHttpClient();

        HttpRequest httpRequest1 = HttpRequest
                .newBuilder()
                //.proxy(ProxySelector.of(new InetSocketAddress("192.168.1.10", 8088)))
                .uri(new URI("https://httpbin.org/delay/7"))
                .build();

        System.out.println("--- sync --- sending");
        HttpResponse<String> httpResponse1 = httpClient1.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse1.statusCode());
        System.out.println("--- sync --- rcv ok");


        System.out.println("--- async ---");
        //CompletableFuture<HttpResponse<String>> httpResponse1Async =
        System.out.println("--- async --- sending");
        CompletableFuture<Void> completableFuture1 = httpClient1.sendAsync(httpRequest1, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenAccept(System.out::println);
        System.out.println("--- async --- sent. waiting.");
        completableFuture1.get(10, TimeUnit.SECONDS);
        System.out.println("--- async --- rcv ok");

        System.out.println("--- with basic-auth ---");
        HttpClient httpClient2 = HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "admin1",
                                "password1".toCharArray());
                    }
                })
                .build();

        HttpRequest httpRequest2 = HttpRequest.newBuilder(new URI("https://httpbin.org/basic-auth/admin1/password1")).build();
        HttpResponse<String> httpResponse2 = httpClient2.send(httpRequest2, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse2.body());



        System.out.println("--- with connect timeout ---");
        HttpClient httpClient3 = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(4))
                .build();

        HttpRequest httpRequest3 = HttpRequest.newBuilder(new URI("http://127.0.0.1:8888")).build();
        HttpResponse<String> httpResponse3 = httpClient3.send(httpRequest3, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse3.body());

    }
}
