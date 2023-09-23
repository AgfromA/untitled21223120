package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/misha"; // Здесь укажи URL базы данных
    private static final String USERNAME = "root"; // Здесь укажи имя пользователя базы данных
    private static final String PASSWORD = "root"; // Здесь укажи пароль пользователя базы данных

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка при создании соединения с базой данных");
            e.printStackTrace();
        }

        return connection;
    }
}
