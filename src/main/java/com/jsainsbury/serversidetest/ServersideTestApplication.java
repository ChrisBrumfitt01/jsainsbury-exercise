package com.jsainsbury.serversidetest;

import com.jsainsbury.serversidetest.model.ProductResults;
import com.jsainsbury.serversidetest.services.BerriesCherriesCurrantsService;
import com.jsainsbury.serversidetest.services.JsonMapper;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServersideTestApplication implements CommandLineRunner {

    @Autowired private BerriesCherriesCurrantsService service;
    @Autowired private JsonMapper<ProductResults> jsonMapper;

    public static void main(String[] args) {
        SpringApplication.run(ServersideTestApplication.class, args);
    }

    @Override
    public void run(String... args) {
        ProductResults results = service.getProductDetails();
        String json = jsonMapper.convertToJson(results);
        System.out.println(json);
    }
}
