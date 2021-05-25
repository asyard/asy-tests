package com.asy.test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HttpClientTests {

    public static void main(String[] args) throws Exception {
        testHttpClient();
    }

    private static void testHttpClient() throws Exception {
        // default:http/2. downgraded to http/1.1  if target does not support
        // can be used both for async and sync ops

        HttpClient httpClient1 = HttpClient.newHttpClient();
        HttpRequest httpRequest1 = HttpRequest
                .newBuilder()
                .uri(new URI("https://www.google.com"))
                .build();

        HttpResponse<String> httpResponse1 = httpClient1
                .send(httpRequest1, HttpResponse.BodyHandlers.ofString());

        System.out.println(httpResponse1.body());
        System.out.println("--- async ---");

        //CompletableFuture<HttpResponse<String>> httpResponse1Async =
        CompletableFuture<Void> completableFuture1 = httpClient1.sendAsync(httpRequest1, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);
        completableFuture1.get();


        //HttpClient httpClient2 = HttpClient.newBuilder().build();

    }
}
