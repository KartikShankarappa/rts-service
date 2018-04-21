package com.dewpoint.rts.bdd.steps.helpers;

import com.dewpoint.rts.bdd.BddTestCoverage;
import com.zaxxer.hikari.HikariDataSource;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DropDataHook {
    @Before
    @After
    public void dropTestData() throws SQLException {
        Connection connection = BddTestCoverage.getConnection ();
        Statement statement = connection.createStatement ();
        statement.executeUpdate ("delete from candidate where email like '%@dewtest.com'");
        statement.executeUpdate ("delete from user where user_id ='testuser'");
        statement.close ();
    }
}
