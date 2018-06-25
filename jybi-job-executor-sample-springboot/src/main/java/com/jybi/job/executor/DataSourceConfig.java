package com.jybi.job.executor;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by js_gg on 2017/9/22.
 */
@Configuration
public class DataSourceConfig {


    @Bean(name = "source_dataSource" , destroyMethod = "close", initMethod = "init")
    //@Qualifier("source_dataSource")
    @ConfigurationProperties(prefix="spring.datasource.source")
    public DataSource mainDataSource() {
        DruidDataSource ds = new DruidDataSource();
        return ds;
    }

    @Bean(name = "goal_dataSource" , destroyMethod = "close", initMethod = "init")
   // @Qualifier("goal_dataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.goal")
    public DataSource privateDataSource() {
        DruidDataSource ds = new DruidDataSource();
        return ds;
    }


    @Bean(name = "source_jdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("source_dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "goal_jdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(
            @Qualifier("goal_dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
