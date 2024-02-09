package org.zot.chai.beerservice;

import org.springframework.boot.SpringApplication;

public class TestBeerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.from(BeerServiceApplication::main)
                .with(TestEnvironmentConfiguration.class)
                .run(args);
    }
}
