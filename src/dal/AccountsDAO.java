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
            String sql = "INSERT INTO ACCOUNTS(username, userpass, firstname, lastname) VALUES (?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserPass());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.execute();

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new User(id, user.getUserName(), user.getUserPass(), user.getFirstName(), user.getLastName());
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
                    int userID = resultSet.getInt("userid");
                    String userName = resultSet.getString("username");
                    String userPass = resultSet.getString("userpass");
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");

                    User user = new User(userID, userName, userPass, firstName, lastName);
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
            String sql = "DELETE FROM ACCOUNTS WHERE userid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateUser(User user) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE ACCOUNTS SET username = COALESCE(?, username), userpass = COALESCE(?, userpass), firstname = COALESCE(?, firstname), lastname = COALESCE(?, lastname) " + "WHERE userid = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserPass());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, user.getUserID());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public User loginUser(String userName, String userPass) {
        User user = null;
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM ACCOUNTS WHERE username = " + userName + " AND userpass = " + userPass + "";

            PreparedStatement statement = connection.prepareStatement(sql);

            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();

                int userID = resultSet.getInt("userid");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");

                user = new User(userID, userName, userPass, firstName, lastName);

            }

        } catch (SQLException e){
            throw new NoSuchElementException(e);
        }
        return user;
    }
}
