package com.jsainsbury.serversidetest;

import com.jsainsbury.serversidetest.scrapers.BerriesCherriesCurrantsScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServersideTestApplication implements CommandLineRunner {

    @Autowired private BerriesCherriesCurrantsScraper scraper;

    public static void main(String[] args) {
        SpringApplication.run(ServersideTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        scraper.getProducts();
    }
}
