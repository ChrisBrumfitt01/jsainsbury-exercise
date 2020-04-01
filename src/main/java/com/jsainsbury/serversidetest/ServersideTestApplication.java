package com.jsainsbury.serversidetest;

import com.jsainsbury.serversidetest.services.BerriesCherriesCurrantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServersideTestApplication implements CommandLineRunner {

    @Autowired private BerriesCherriesCurrantsService service;

    public static void main(String[] args) {
        SpringApplication.run(ServersideTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        service.getProductDetails();
    }
}
