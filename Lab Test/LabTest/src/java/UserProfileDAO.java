import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserProfileDAO {
    // Database connection parameters
    private String jdbcURL = "jdbc:mysql://localhost:3306/labtest";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    // SQL queries
    private static final String INSERT_USERS_SQL = "INSERT INTO user_profiles (user_id, user_name, gender, contact_no, id, age, address) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user_profiles WHERE user_id = ?;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM user_profiles;";
    private static final String DELETE_USERS_SQL = "DELETE FROM user_profiles WHERE user_id = ?;";
    private static final String UPDATE_USERS_SQL = "UPDATE user_profiles SET user_name = ?, gender = ?, contact_no = ?, id = ?, age = ?, address = ? WHERE user_id = ?;";

    public UserProfileDAO() {}

    // Method to establish database connection
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Method to add a new user profile to the database
    public void addUserProfile(UserProfile userProfile) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, userProfile.getUserId());
            preparedStatement.setString(2, userProfile.getUserName());
            preparedStatement.setString(3, userProfile.getGender());
            preparedStatement.setString(4, userProfile.getContactNo());
            preparedStatement.setString(5, userProfile.getId());
            preparedStatement.setInt(6, userProfile.getAge());
            preparedStatement.setString(7, userProfile.getAddress());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    // Method to get a user profile by user ID
    public UserProfile getUserProfile(String userId) {
        UserProfile userProfile = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setString(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("user_name");
                String gender = rs.getString("gender");
                String contactNo = rs.getString("contact_no");
                String id = rs.getString("id");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                userProfile = new UserProfile();
                userProfile.setUserId(userId);
                userProfile.setUserName(userName);
                userProfile.setGender(gender);
                userProfile.setContactNo(contactNo);
                userProfile.setId(id);
                userProfile.setAge(age);
                userProfile.setAddress(address);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return userProfile;
    }

    // Method to get all user profiles
    public List<UserProfile> getAllUserProfiles() {
        List<UserProfile> userProfiles = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String userName = rs.getString("user_name");
                String gender = rs.getString("gender");
                String contactNo = rs.getString("contact_no");
                String id = rs.getString("id");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(userId);
                userProfile.setUserName(userName);
                userProfile.setGender(gender);
                userProfile.setContactNo(contactNo);
                userProfile.setId(id);
                userProfile.setAge(age);
                userProfile.setAddress(address);
                userProfiles.add(userProfile);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return userProfiles;
    }

    // Method to update an existing user profile
    public boolean updateUserProfile(UserProfile userProfile) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            preparedStatement.setString(1, userProfile.getUserName());
            preparedStatement.setString(2, userProfile.getGender());
            preparedStatement.setString(3, userProfile.getContactNo());
            preparedStatement.setString(4, userProfile.getId());
            preparedStatement.setInt(5, userProfile.getAge());
            preparedStatement.setString(6, userProfile.getAddress());
            preparedStatement.setString(7, userProfile.getUserId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    // Method to delete a user profile
    public boolean deleteUserProfile(String userId) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)) {
            preparedStatement.setString(1, userId);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }

    // Method to print SQL exceptions
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
