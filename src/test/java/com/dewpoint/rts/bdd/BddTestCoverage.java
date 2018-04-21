package com.dewpoint.rts.bdd;

import com.zaxxer.hikari.HikariDataSource;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.junit.Cucumber;
import lombok.Getter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.dewpoint.rts.bdd.steps"},
        features = {"classpath:bdd/features"},
        tags = {"@RTSTests"},
        snippets = SnippetType.CAMELCASE,
        format = {
                "html:target/cucumber/rts/html",
                "json:target/cucumber/rts/json"
        }
)

public class BddTestCoverage {

        @Getter
        private static Connection connection;
        @BeforeClass
        public static void dbConnection() throws SQLException{

            HikariDataSource ds = new HikariDataSource ( );
            ds.setMaximumPoolSize (10);
            ds.setDataSourceClassName ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            ds.addDataSourceProperty ("url", "jdbc:mysql://localhost:3306/rts");
            ds.addDataSourceProperty ("user", "root");
            ds.addDataSourceProperty ("password", "Welcome1");
            if(connection==null){
                connection = ds.getConnection ( );
            }
        }

        @AfterClass
        public static void closeDbConnection() throws SQLException{
                connection.close ();
        }
}