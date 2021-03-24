package com.testing.controllers;

import com.testing.services.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class ControllerTest {

    @Autowired
    ServiceTest serviceTest;

    @PostMapping(value = "/add-product")
    public ResponseEntity addProduct(@RequestBody String product) throws SQLException {
        return serviceTest.addProductDB(product);
    }

    @GetMapping(value = "/get-product")
    public ResponseEntity getProduct(@RequestParam(name = "productName") String productName) throws SQLException {
        return serviceTest.getProductDB(productName);
    }

    @PutMapping(value = "/update-product")
    public ResponseEntity updateProduct(@RequestBody String product) throws SQLException {
        return serviceTest.updateProductDB(product);
    }

    @DeleteMapping(value = "/remove-product")
    public ResponseEntity removeProduct(@RequestParam(name = "productName") String productName) throws SQLException {
        return serviceTest.removeProductDB(productName);
    }

}
