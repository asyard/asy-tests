**Spring Boot Config Client Example**

This client retrieves configuration values from a config server application.

After adding necessary dependencies, we need to create a bootstrap.properties file.

```
spring.cloud.config.uri=http://serverip:port/asy-config-server
```

We defined a Rest Controller (`/getit`), which returns a dummy string, but the string value is configured in config server.

```
@Value("${test.value}")
private String val;
```

Before starting client application, we need to start config server. After, we can start client application with a profile.

```
java -jar asy-config-client.jar --spring.profiles.active=development
```

Config server will determine which profile to use, and return the value of the property.

Take note that config server's `application-{client.name}.properties` files have to match client's application name defined in application.properties file. In this example, client's application name is asy-config-client so under config server's configuration folder, we defined files named `application-asy-config-client.properties` for each profile folder.


**Reload Client Application Properties**

When a client configuration change occurs in server's config files, our spring beans in client application which have `@RefreshScope` annotation, can be reloaded with the help of spring boot actuator by calling the following endpoint (using **POST**):

    http://clientip:port/actuator/refresh


We also have application.properties file in addition to bootstrap.properties file. Which is retrieved after bootstrap.properties file is loaded.

In this properties file, we have to set actuator endpoint available for calling since actuator endpoints are not exposed by default. In this example we used * to expose all actuator endpoints.

```
management.endpoints.web.exposure.include=*
```


