package com.renysdelacruz.practica2.services;

import java.sql.*;

public class H2JdbcService {
    static final String JDBC_DRIVER     = "org.h2.Driver";
    static final String DB_URL          = "jdbc:h2:~/test";
    static final String USER            = "sa";
    static final String PASS            = "";
    static final String TEST_USERNAME   = "admin";
    static final String TEST_PASSWORD   = "admin";

    public static void main(String[] args) {
        H2JdbcService service = new H2JdbcService();
        String password = service.getPasswordByUsername(TEST_USERNAME);
        System.out.println(password);
    }

    public H2JdbcService() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        initTestTableAndUser();
    }

    public void initTestTableAndUser() {
        executeUpdate("DROP TABLE IF EXISTS user");
        executeUpdate("CREATE TABLE user (" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(255), " +
                "password VARCHAR(255)" +
                ")");
        executeUpdate(String.format("INSERT INTO user (username, password) VALUES('%s', '%s')", TEST_USERNAME, TEST_PASSWORD));
    }

    private void executeUpdate(String sql) {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPasswordByUsername(String username) {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT password FROM user WHERE(username=?)")) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString(1);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
