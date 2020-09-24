package com.testing.controllers;

import com.testing.services.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerTest {

    @Autowired
    ServiceTest serviceTest;

    @GetMapping(value = "/message")
    public String getMessage(@RequestParam(name = "service") String service) {
        return serviceTest.helloService(service);
    }

    @GetMapping(value = "/json-message")
    public String getJSONMessage(@RequestParam(name = "service") String service) {
        return serviceTest.helloJSONService(service);
    }

    @PostMapping(value = "/message")
    public ResponseEntity setJSONMessage(@RequestBody String jsonBody, @RequestHeader("header") String header) {
        return serviceTest.setHelloService(header, jsonBody);
    }

    @GetMapping(value = "/exception")
    public String getException() throws IllegalStateException {
        throw new IllegalStateException("Custom generated exception");
    }
}
