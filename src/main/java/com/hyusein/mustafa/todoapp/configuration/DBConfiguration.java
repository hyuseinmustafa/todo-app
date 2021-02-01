package com.hyusein.mustafa.todoapp.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

//@Configuration
public class DBConfiguration {

/*    @Bean
    @Profile("development")
    public DataSource h2DataBaseConfig(){
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName("org.h2.Driver");
        dataSource.url("jdbc:h2:mem:testdb");
        dataSource.username("SA");
        dataSource.password("");
        return dataSource.build();
    }

    @Bean
    @Profile("production")
    public DataSource mySqlDataBaseConfig(){
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.url("jdbc:mysql://localhost/todoapp");
        dataSource.username("todoapp");
        dataSource.password("test");
        return dataSource.build();
    }*/
}
