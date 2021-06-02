package com.testing.services;

import com.testing.utils.MySQLWrapper;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class ServiceTest {

    private String url = "jdbc:mysql://localhost:3306/Test";
    private String user = "sorin";
    private String pass = "P@55w0rd!";
    private MySQLWrapper mySQL = new MySQLWrapper(url, user, pass);


    public ResponseEntity addProductDB(String body) throws SQLException {
        JSONObject jsonBody = new JSONObject(body);
        String productName = (String) jsonBody.get("productName");
        String price = (String) jsonBody.get("price");

        if(mySQL.insert("Product", "ProductName", productName, "Price", price) > 0) {
            return new ResponseEntity("Record was inserted!", HttpStatus.OK);
        }
        return new ResponseEntity("Error in inserting record!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity getProductDB(String productName) throws SQLException {
        ResultSet records = mySQL.get("Product", "ProductName", productName);
        String response = "";
        while (records.next()) {
            response += "ProductId = " + records.getInt("ProductId") + ", ProductName = " + records.getString("ProductName") + ", Price = " + records.getFloat("Price") + "\n";
        }
        if(response.isEmpty()) {
            return new ResponseEntity("There are no records available!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    public ResponseEntity updateProductDB(String body) throws SQLException {
        JSONObject jsonBody = new JSONObject(body);
        String productName = (String) jsonBody.get("productName");
        String price = (String) jsonBody.get("price");

        if(mySQL.update("Product", "Price", price, "ProductName", productName) > 0) {
            return new ResponseEntity("Record was updated!", HttpStatus.OK);
        }
        return new ResponseEntity("Error in updating record!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity removeProductDB(String productName) throws SQLException {
        if(mySQL.delete("Product", "ProductName", productName) > 0) {
            return new ResponseEntity("Record was removed!", HttpStatus.OK);
        }
        return new ResponseEntity("Error in removing record!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
