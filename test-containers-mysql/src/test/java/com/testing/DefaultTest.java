package com.testing;

import com.testing.utils.MySQLCache;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DefaultTest {

    @ClassRule
    public static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:5.7")
            .withInitScript("mysqlinit.sql")
            .withUsername("sorin2")
            .withPassword("P@55w0rd!2")
            .withDatabaseName("Test2");

    private static MySQLCache mySQLCache;


    @Autowired
    MockMvc mockMvc;

    @BeforeClass
    public static void setUp() {
        mySQLContainer.start();
        mySQLCache = new MySQLCache(mySQLContainer.getJdbcUrl(), mySQLContainer.getUsername(), mySQLContainer.getPassword());
    }

    @AfterClass
    public static void tearDown() {
        mySQLContainer.stop();
        mySQLContainer.close();
    }

    @Test
    public void checkAddProductEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/add-product")
                .content("{" +
                        "\"productName\":\"frigider\"," +
                        "\"price\":\"125.25\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Record was inserted!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/get-product?productName=frigider"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("ProductName = frigider, Price = 125.25")));
    }

    @Test
    public void checkGetProductEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/get-product?productName=masina"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("There are no records available!"));

        mySQLCache.insert("Product", "ProductName", "masina", "Price", "5000");
        mockMvc.perform(MockMvcRequestBuilders.get("/get-product?productName=masina"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("ProductName = masina, Price = 5000")));
    }

    @Test
    public void checkUpdateProductEndpoint() throws Exception {
        mySQLCache.insert("Product", "ProductName", "televizor", "Price", "255.25");
        mockMvc.perform(MockMvcRequestBuilders.put("/update-product")
                .content("{" +
                        "\"productName\":\"televizor\"," +
                        "\"price\":\"300.75\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Record was updated!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/get-product?productName=televizor"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("ProductName = televizor, Price = 300.75")));
    }

    @Test
    public void checkDeleteProductEndpoint() throws Exception {
        mySQLCache.insert("Product", "ProductName", "radio", "Price", "25.25");
        mockMvc.perform(MockMvcRequestBuilders.delete("/remove-product?productName=radio"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Record was removed!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/get-product?productName=radio"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("There are no records available!"));
    }
}