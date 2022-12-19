package com.gogonew.api.mysql.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfiguration {
    // 상위 Application에서 DB 설정정보를 활용할 수 있도록 빈으로 등록
    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .url("jdbc:mysql://localhost:3306/shopping_mall?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul")
            .username("mall")
            .password("mall")
            .build();
    }
}
