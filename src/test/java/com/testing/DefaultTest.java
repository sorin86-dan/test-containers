package com.testing;

import com.testing.utils.RedisCache;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DefaultTest {

    @Rule
    public GenericContainer redis = new GenericContainer("redis:6.0.1-alpine").withExposedPorts(6379);

    @Autowired
    MockMvc mockMvc;

    private RedisCache redisCache;

    @Before
    public void setUp() {
        String address = redis.getHost();
        Integer port = redis.getFirstMappedPort();

        // Now we have an address and port for Redis, no matter where it is running
        redisCache = new RedisCache(address, port);
    }
    @Test
    public void checkExceptionEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/exception"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Custom Exception"));
    }

    @Test
    public void checkServiceMessageEndpoint() throws Exception {
        redisCache.put("message", "Hello");
        System.out.println(redisCache.get("message"));

        mockMvc.perform(MockMvcRequestBuilders.get("/message?service=TestServiceX"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Hello, TestServiceX!"));
    }

    @Test
    public void checkJSONServiceMessageEndpoint() throws Exception {
        redisCache.put("message", "Hello");

        mockMvc.perform(MockMvcRequestBuilders.get("/json-message?service=TestServiceJSONX"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.message").value("Hello, TestServiceJSONX!"));
    }

    @Test
    public void checkSetMessageEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/message")
                    .content("Salut")
                    .header("header", "test-header")
                    .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("header", "test-header"))
                .andExpect(content().string("Message set!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/json-message?service=TestServiceJSONX"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.message").value("Salut, TestServiceJSONX!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/message?service=TestService"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Salut, TestService!"));
    }

    @Test
    public void checkInvalidEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mssage?service=TestServiceX"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(""));
    }

    @Test
    public void checkServiceMessageEndpointInvalidParameter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/message?serviceX=TestServiceX"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(""));
    }

}