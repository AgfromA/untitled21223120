package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            // Create a statement
            Statement statement = connection.createStatement();

            // Execute the SQL query to create the users table
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "age INT" +
                    ")";
            statement.executeUpdate(sql);

            System.out.println("Users table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected != 1) {
                throw new AssertionError("User был некорректно добавлен в базу данных");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AssertionError("Ошибка при сохранении пользователя в базу данных");
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        public List<User> getAllUsers() {
            List<User> users = new ArrayList<>();
            String sql = "SELECT * FROM users";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastName");
                    byte age = resultSet.getByte("age");

                    User user = new User(name, lastName, age);
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return users;
        }


    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

