package com.project.social_media.infrastructure.Config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.project.social_media.domain.Repository.MongoDB",
        considerNestedRepositories = true
)
public class MongoDBConfig {

}
