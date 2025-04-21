package com.project.social_media.infrastructure.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.project.social_media.domain.Repository")
public class MongoConfig {

}
