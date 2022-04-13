**Spring Boot Config Server Example**

After adding necessary libs, first we need to add :

```java
@EnableConfigServer
```
annotation to our main class. Then we need to determine config property location. In this example classpath is used insted of file system or git repo.

```java
spring.cloud.config.server.native.search-locations=...
```

Under provided location, we create folders for each profile. In this example we have two profiles: `development` and `production`.

In each folder, we create *.properties files for specific client configurations: application-{client.name}.properties.

When client bootstraps, it will use `/{profile}/application-{client.name}.properties` file to configure itself under {profile} folder. If no matching profile is provided by client, default profile will be used, which is `/application-{client.name}.properties` in this example.

