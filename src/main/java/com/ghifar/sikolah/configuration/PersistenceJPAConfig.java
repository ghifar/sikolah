package com.ghifar.sikolah.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.logging.Logger;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:persistence.properties"})
//@ComponentScan({"com.ghifar.sikolah"}) bedanya?
@ComponentScan(basePackages="com.ghifar.sikolah")
@EnableJpaRepositories(basePackages = "com.ghifar.sikolah.repository")
public class PersistenceJPAConfig {

    @Autowired
    private Environment env;

    private Logger logger= Logger.getLogger(getClass().getName());

    @Bean
    public DataSource securityDataSource() {

//		create connection pool
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

//		set jdbc driver class
        try {
            securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//		log the connection :)
        logger.info(">> jdbc.url= " + env.getProperty("jdbc.url"));
        logger.info(">> jdbc.user= " + env.getProperty("jdbc.user"));

//		set database connection properties
        securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        securityDataSource.setUser(env.getProperty("jdbc.user"));
        securityDataSource.setPassword(env.getProperty("jdbc.password"));

        return securityDataSource;
    }



}
