package com.tudor.service;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.tudor")
@EntityScan(basePackages = "com.tudor.model")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.tudor.repository")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("user");
        dataSource.setPassword("password");
        dataSource.setUrl("jdbc:mysql://localhost:3306/bank?useSSL=false&allowPublicKeyRetrieval=true");

        return dataSource;
    }

}
