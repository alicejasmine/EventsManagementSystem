package dal;

import be.*;

import javax.xml.transform.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AdminDAO {

    private DatabaseConnector databaseConnector;

    public AdminDAO(){databaseConnector = new DatabaseConnector();}


    public List<Admin> getAllAdmins()  {
        ArrayList<Admin> admins = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM event_admin";
            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    int userID = resultSet.getInt("userid");
                    int adminID = resultSet.getInt("adminid");

                    Admin admin = new Admin(adminID, userID);
                    admins.add(admin);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }

    public Admin createAdmin(Admin admin) {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO event_admin(userid) VALUES (?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, admin.getUserID());
            statement.execute();

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new Admin(id, admin.getUserID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
