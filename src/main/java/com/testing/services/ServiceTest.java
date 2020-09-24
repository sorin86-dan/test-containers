package com.testing.services;

import com.testing.utils.RedisCache;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServiceTest {

    private RedisCache redis = new RedisCache("localhost", 6379);

    public String helloService(String service)
    {
        String message = redis.get("message");

        return message + ", " + service + "!";
    }

    public String helloJSONService(String service) {
        String message = redis.get("message");

        return "{\"message\":\"" + message + ", " + service + "!\"}";
    }

    public ResponseEntity setHelloService(String header, String message) {
        redis.put("message", message);
        HttpHeaders headers = new HttpHeaders();
        headers.add("header", "test-header");

        return new ResponseEntity( "Message set!", headers, HttpStatus.OK);
    }

}
