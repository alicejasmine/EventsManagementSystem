package dal;

import be.*;

import javax.xml.transform.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AccountsDAO {

    /**
     * This is our data access for the ACCOUNTS table where we store the information on user accounts.
     * UserID, UserName, and UserPass are the column names for the table.
     */

    private DatabaseConnector databaseConnector;

    public AccountsDAO(){databaseConnector = new DatabaseConnector();}

    public User createUser(User user) {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO ACCOUNTS(UserName, UserPass) VALUES (?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserPass());
            statement.execute();

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new User(id, user.getUserName(), user.getUserPass());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers()  {
        ArrayList<User> users = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM ACCOUNTS";
            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    int userID = resultSet.getInt("UserID");
                    String userName = resultSet.getString("UserName");
                    String userPass = resultSet.getString("UserPass");

                    User user = new User(userID, userName, userPass);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void deleteUser(int id) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM ACCOUNTS WHERE UserID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateUser(User user) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE ACCOUNTS SET UserName = COALESCE(?, UserName), UserPass = COALESCE(?, UserPass) " + "WHERE UserID = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserPass());
            statement.setInt(3, user.getUserID());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public User loginUser(String userName, String userPass) {
        User user = null;
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM ACCOUNTS WHERE UserName = " + userName + " AND UserPass = " + userPass + "";

            PreparedStatement statement = connection.prepareStatement(sql);

            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();

                int userID = resultSet.getInt("UserID");
                user = new User(userID, userName, userPass);

            }

        } catch (SQLException e){
            throw new NoSuchElementException(e);
        }
        return user;
    }
}
