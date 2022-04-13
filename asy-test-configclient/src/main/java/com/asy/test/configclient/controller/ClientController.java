package com.asy.test.configclient.controller;

import com.asy.test.configclient.ClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    private ClientConfiguration clientConfiguration;

    @GetMapping("/getit")
    public ResponseEntity<String> getResponse() {
        return ResponseEntity.ok("Hello " + clientConfiguration.getValue());
    }

}
