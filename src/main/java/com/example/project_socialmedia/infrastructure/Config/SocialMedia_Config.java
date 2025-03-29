package com.example.project_socialmedia.infrastructure.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialMedia_Config {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
