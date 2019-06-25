package com.asy.test.hawk;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharStreams;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.wealdtech.DataError;
import com.wealdtech.ServerError;
import com.wealdtech.hawk.Hawk;
import com.wealdtech.hawk.HawkCredentials;
import com.wealdtech.hawk.HawkServer;
import com.wealdtech.hawk.HawkServerConfiguration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Simple HTTP server that does nothing other than check the Hawk authentication
 * parameters and return any empty response with appropriate response code.
 */
public class SimpleHttpServer
{
    private final HttpServer server;
    public SimpleHttpServer(final HawkCredentials credentials, final HawkServerConfiguration configuration) throws Exception
    {

        InetSocketAddress addr = new InetSocketAddress(6600);
        this.server = HttpServer.create(addr, 0);

        this.server.createContext("/", new AuthenticateHandler(credentials, configuration));
        this.server.setExecutor(Executors.newCachedThreadPool());
        this.server.start();
    }

    public void stop()
    {
        this.server.stop(0);
    }

    // A handler that does nothing other than authentication
    private class AuthenticateHandler implements HttpHandler
    {
        private transient final HawkServer server;
        private transient final HawkCredentials credentials;


        public AuthenticateHandler(final HawkCredentials credentials, final HawkServerConfiguration configuration)
        {
            this.credentials = credentials;
            this.server = new HawkServer.Builder().configuration(configuration).build();
        }

        @Override
        public void handle(final HttpExchange exchange) throws IOException
        {
            if (exchange.getRequestURI().toString().contains("bewit="))
            {
                handleAuthenticationBewit(exchange);
            }
            else
            {
                handleAuthenticationHeader(exchange);
            }
        }

        private void handleAuthenticationBewit(final HttpExchange exchange) throws IOException
        {
            System.out.println("Handling bewit");
            if (!exchange.getRequestMethod().equals("GET"))
            {
                System.out.println("Not authenticated: HTTP method " + exchange.getRequestMethod() + " not supported with bewit");
                addAuthenticateHeader(exchange);
                exchange.sendResponseHeaders(401, 0);
            }

            URI uri = null;
            try
            {
                uri = new URI(exchange.getRequestURI().getScheme() + "://" + exchange.getRequestHeaders().getFirst("Host") + exchange.getRequestURI());
            }
            catch (URISyntaxException use)
            {
                System.out.println("Not authenticated: " + use.getLocalizedMessage());
                addAuthenticateHeader(exchange);
                exchange.sendResponseHeaders(401, 0);
            }

            try
            {
                server.authenticate(this.credentials, uri);
                System.out.println("Authenticated by bewit");
                exchange.sendResponseHeaders(200, 0);
            }
            catch (DataError de)
            {
                System.out.println("Not authenticated: " + de.getLocalizedMessage());
                addAuthenticateHeader(exchange);
                exchange.sendResponseHeaders(401, 0);
            }
            catch (ServerError se)
            {
                System.out.println("Not authenticated: " + se.getLocalizedMessage());
                addAuthenticateHeader(exchange);
                exchange.sendResponseHeaders(500, 0);
            }

        }

        private void handleAuthenticationHeader(final HttpExchange exchange) throws IOException
        {
            System.out.println("Handling Hawk");
            // Obtain parameters available from the request
            URI uri = null;
            try
            {
                //TODO gerial
                String scheme = exchange.getRequestURI().getScheme();
                if (scheme == null) {
                    scheme = "http";
                }

                uri = new URI( scheme+ "://" + exchange.getRequestHeaders().getFirst("Host") + exchange.getRequestURI());
            }
            catch (URISyntaxException use)
            {
                System.out.println("Not authenticated: " + use.getLocalizedMessage());
                addAuthenticateHeader(exchange);
                exchange.sendResponseHeaders(401, 0);
            }

            ImmutableMap<String, String> authorizationHeaders = ImmutableMap.of();
            try
            {
                authorizationHeaders = this.server.splitAuthorizationHeader(exchange.getRequestHeaders().getFirst("Authorization"));
            }
            catch (DataError de)
            {
                System.out.println("Not authenticated: " + de.getLocalizedMessage());
                addAuthenticateHeader(exchange);
                exchange.sendResponseHeaders(401, 0);
            }

            String hash = null;
            if (authorizationHeaders.get("hash") != null)
            {
                // Need to calculate hash of the body
                hash = Hawk.calculateMac(this.credentials, CharStreams.toString(new InputStreamReader(exchange.getRequestBody(), "UTF-8")));
            }

            // Need to know if there is a body present
            // TODO what happens if the client fakes this information
            boolean hasBody = exchange.getRequestHeaders().getFirst("Content-Length") != null ? true : false;
            try
            {
                server.authenticate(this.credentials, uri, exchange.getRequestMethod(), authorizationHeaders, hash, hasBody);
                System.out.println("Authenticated by header");
                exchange.sendResponseHeaders(200, 0);
            }
            catch (DataError de)
            {
                System.out.println("Not authenticated: " + de.getLocalizedMessage());
                addAuthenticateHeader(exchange);
                exchange.sendResponseHeaders(401, 0);
            }
            catch (ServerError se)
            {
                System.out.println("Not authenticated: " + se.getLocalizedMessage());
                addAuthenticateHeader(exchange);
                exchange.sendResponseHeaders(500, 0);
            }



            OutputStream os = exchange.getResponseBody();
            os.write("ANS".getBytes());
            os.close();

        }

        private void addAuthenticateHeader(final HttpExchange exchange)
        {
            Map<String, List<String>> responseheaders = exchange.getResponseHeaders();
            String authenticate = server.generateAuthenticateHeader();
            List<String> authenticateheader = new ArrayList<>();
            authenticateheader.add(authenticate);
            responseheaders.put("WWW-Authenticate", authenticateheader);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            HawkCredentials credentials = new HawkCredentials.Builder()
                    .keyId("hawkKeyId")
                    .key("hawkKey1231123123123123")
                    .algorithm(HawkCredentials.Algorithm.SHA256)
                    .build();

            HawkServerConfiguration.Builder builder = new HawkServerConfiguration.Builder();
            builder.timestampSkew(5L);
            builder.bewitAllowed(false);
            HawkServerConfiguration conf = builder.build();

            new SimpleHttpServer(credentials, conf);
            while (true)
            {
                Thread.sleep(60000);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // Shutdown
        }
    }
}