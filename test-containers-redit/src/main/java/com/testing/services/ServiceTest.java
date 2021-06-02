package com.testing.services;

import com.testing.utils.RedisWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ServiceTest {

    private RedisWrapper redisWrapper = new RedisWrapper("localhost", 6379);

    public String helloService(String service)
    {
        return getMessage() + ", " + service + "!";
    }

    public String helloJSONService(String service) {
        return "{\"message\":\"" + getMessage() + ", " + service + "!\"}";
    }

    public ResponseEntity setHelloService(String header, String message) {
        redisWrapper.put("message", message);
        HttpHeaders headers = new HttpHeaders();
        headers.add("header", "test-header");

        return new ResponseEntity( "Message set!", headers, HttpStatus.OK);
    }

    private String getMessage() {
        String message = redisWrapper.get("message");
        if (StringUtils.isEmpty(message)) {
            message = "Hello";
        }
        return message;
    }

}
