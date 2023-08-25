package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {



    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {

        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;

        String creatUsers = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    name VARCHAR(100),\n" +
                "    lastName VARCHAR(100),\n" +
                "    age TINYINT" +
                ")";

        try {

            preparedStatement = connection.prepareStatement(creatUsers);

            preparedStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public void dropUsersTable() throws SQLException {

        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;

        String dropUsers = "DROP TABLE IF EXISTS users  ";

        try {
            preparedStatement = connection.prepareStatement(dropUsers);

            preparedStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;

        String save = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(save);


            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);

            preparedStatement.execute();

            System.out.println("User с именем " + name + " добавлен в базу");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;
        String delitID = "DELETE FROM users WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement(delitID);

            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = Util.getConnection();
        List <User> userList = new ArrayList<>();

        String getAllUsers = "SELECT ID, NAME, LASTNAME, AGE FROM users";

        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllUsers);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                System.out.println(user);

                userList.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException {

        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;

        String cleanUsers = "TRUNCATE TABLE users";

        try {
            preparedStatement = connection.prepareStatement(cleanUsers);

            preparedStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }
}
