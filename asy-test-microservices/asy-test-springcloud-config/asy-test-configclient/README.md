**Spring Boot Config Client Example**

This client retrieves configuration values from a config server application.

After adding necessary dependencies, we need to create a bootstrap.properties file.

```
spring.cloud.config.uri=http://serverip:port/asy-config-server
```

We defined a Rest Controller (`/getit`), which returns a dummy string, but the string value is configured in config server.

Before starting client application, we need to start config server. After, we can start client application with a profile.

```
java -jar asy-config-client.jar --spring.profiles.active=development
```

Config server will determine which profile to use, and return the propertySources for requested profile.

Take note that config server's `application-{client.name}.properties` files have to match client's application name defined in application.properties file. In this example, client's application name is asy-config-client so under config server's configuration folder, we defined files named `application-asy-config-client.properties` for each profile folder.

When client bootstraps, it will send a request to obtain necessary resources by config-server's uri similar to below.

```
http://serverip:port/asy-config-server/{client.name}/{profile}
```


**Reload Client Application Properties via Actuator**

When a client configuration change occurs in server's config files, our spring beans in client application can be reloaded with the help of spring boot actuator by calling the following endpoint (using **POST**):

    http://clientip:port/actuator/refresh

Actuator will trigger a rest call to retrieve config information by calling ConfigServicePropertySourceLocator.class provided by spring.

2 types of beans can be refreshed : Beans which have attributes annotated with `@Value` and beans which are annotated with `@ConfigurationProperties`.
Beans which have attributes annotated with `@Value` will only be refreshed if the class is annotated with `@RefreshScope`.

We also have application.properties file in addition to bootstrap.properties file. Which is retrieved after bootstrap.properties file is loaded.

In any of the properties file, we have to set actuator endpoint available for calling since actuator endpoints are not exposed by default. In this example we used * to expose all actuator endpoints.

```
management.endpoints.web.exposure.include=*
```

Keep in mind that spring.application.name property should be placed in bootstrap.properties file instead of application.properties file since triggered actuator refresh operation will look at this file for app name.


**Reload Client Application Properties via Spring Cloud Bus**

Instead of calling actuator /refresh endpoint, we can also use spring cloud bus to reload client application properties. This will be helpful when we have multiple clients needing to be refreshed.

We used rabbitmq as a message broker in this example. Install docker image for rabbitmq and start it.
We  used rabbitmq-management, so we can use it to check message queue from browser via http://localhost:15672/
Default username is guest and password is guest.

```
docker pull rabbitmq:management

docker run -d --name my-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:management
```

Add maven dependency for clients:

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

In clients' application.properties file, we need to set the following properties:
```
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
spring.cloud.bus.enabled=true
spring.cloud.bus.refresh.enabled=true
```




