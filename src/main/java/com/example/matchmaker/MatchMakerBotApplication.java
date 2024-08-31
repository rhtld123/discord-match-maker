package com.example.matchmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MatchMakerBotApplication {

  public static void main(String[] args) {
    SpringApplication.run(MatchMakerBotApplication.class, args);
  }

}
