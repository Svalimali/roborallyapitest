package com.restapi.configuration;
import org.example.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class GameConfig {



    @Bean
    public Game game() {
        int radius = 5;
        return new Game(radius);
    }



}
