**Spring Boot Config Server Example**

After adding necessary libs, first we need to add :

```java
@EnableConfigServer
```
annotation to our main class. Then we need to determine config property location. In this example file system is used.

```java
spring.cloud.config.server.native.search-locations=...
```

Under provided location, we create folders for each profile. In this example we have two profiles: `development` and `production`.

In each folder, we create *.properties files for specific client configurations: application-{client.name}.properties.

When client bootstraps, it will send a request to this server with args including client app name and profile. Request url will be similar to:

```
http://localhost:8888/asy-config-server/{client.name}/{profile}
```

Server will look at path  `{config.location}/{profile}/application-{client.name}.properties` file and return information inside this file. If no matching profile is provided by client, default profile will be used, which is `/application-{client.name}.properties`.



