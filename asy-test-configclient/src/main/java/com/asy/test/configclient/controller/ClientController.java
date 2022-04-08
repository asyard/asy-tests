package com.asy.test.configclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ClientController {

    @Autowired
    private Environment environment;

    @Value("${test.value}")
    private String val;


    @GetMapping("/getit")
    public ResponseEntity<String> getResponse() {
        return ResponseEntity.ok("Hello " + val);
    }

}
