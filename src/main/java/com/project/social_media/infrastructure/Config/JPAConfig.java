package com.project.social_media.infrastructure.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.project.social_media.domain.Repository.JPA")
@EntityScan(basePackages = "com.project.social_media.domain.Model.JPA")
@EnableTransactionManagement
public class JPAConfig {

}
