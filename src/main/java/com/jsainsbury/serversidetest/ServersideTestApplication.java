package com.jsainsbury.serversidetest;

import com.jsainsbury.serversidetest.config.UrlConfig;
import com.jsainsbury.serversidetest.model.ProductResults;
import com.jsainsbury.serversidetest.services.BerriesCherriesCurrantsService;
import com.jsainsbury.serversidetest.services.JsonMapper;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This application will parse the web page for Berries, Cherries and Currants and output a
 * JSON string detailing the product details (title, unit price, kcal per 100g and description).
 * Also includes the gross and vat total.
 */
@SpringBootApplication
public class ServersideTestApplication implements CommandLineRunner {

    @Autowired private BerriesCherriesCurrantsService service;
    @Autowired private JsonMapper<ProductResults> jsonMapper;
    @Autowired private UrlConfig uriConfig;

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
