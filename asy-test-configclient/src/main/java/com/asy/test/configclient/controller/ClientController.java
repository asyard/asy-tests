package com.asy.test.configclient.controller;

import com.asy.test.configclient.ClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //we need to add this annotation to refresh the configuration annotated with @value
public class ClientController {

    @Autowired
    private ClientConfiguration clientConfiguration;

    @Value("${test.value}")
    private String testProperty;

    @GetMapping("/getit")
    public ResponseEntity<String> getResponse() {
        System.out.println(testProperty);
        System.out.println(clientConfiguration.getValue());

        return ResponseEntity.ok("Hello " + clientConfiguration.getValue());
    }

}
