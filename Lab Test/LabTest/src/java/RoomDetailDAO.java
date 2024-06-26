import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDetailDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/labtest";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    private static final String INSERT_ROOMS_SQL = "INSERT INTO room_details (room_id, room_no, room_picture, room_type, room_price) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_ROOM_BY_ID = "SELECT * FROM room_details WHERE room_id = ?;";
    private static final String SELECT_ALL_ROOMS = "SELECT * FROM room_details;";
    private static final String DELETE_ROOMS_SQL = "DELETE FROM room_details WHERE room_id = ?;";
    private static final String UPDATE_ROOMS_SQL = "UPDATE room_details SET room_no = ?, room_picture = ?, room_type = ?, room_price = ? WHERE room_id = ?;";

    // Constructor
    public RoomDetailDAO() {}

    // Method to establish a database connection
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

    // Method to add a room detail to the database
    public void addRoomDetail(RoomDetail roomDetail) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROOMS_SQL)) {
            preparedStatement.setString(1, roomDetail.getRoomId());
            preparedStatement.setInt(2, roomDetail.getRoomNo());
            preparedStatement.setBytes(3, roomDetail.getRoompicture());
            preparedStatement.setString(4, roomDetail.getRoomType());
            preparedStatement.setDouble(5, roomDetail.getRoomPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    // Method to get a room detail by room ID
    public RoomDetail getRoomDetail(String roomId) {
        RoomDetail roomDetail = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROOM_BY_ID)) {
            preparedStatement.setString(1, roomId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int roomNo = rs.getInt("room_no");
                byte[] roomPicture = rs.getBytes("room_picture");
                String roomType = rs.getString("room_type");
                double roomPrice = rs.getDouble("room_price");
                roomDetail = new RoomDetail();
                roomDetail.setRoomId(roomId);
                roomDetail.setRoomNo(roomNo);
                roomDetail.setRoompicture(roomPicture);
                roomDetail.setRoomType(roomType);
                roomDetail.setRoomPrice(roomPrice);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return roomDetail;
    }

    // Method to get all room details from the database
    public List<RoomDetail> getAllRoomDetails() {
        List<RoomDetail> roomDetails = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROOMS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String roomId = rs.getString("room_id");
                int roomNo = rs.getInt("room_no");
                byte[] roomPicture = rs.getBytes("room_picture");
                String roomType = rs.getString("room_type");
                double roomPrice = rs.getDouble("room_price");
                RoomDetail roomDetail = new RoomDetail();
                roomDetail.setRoomId(roomId);
                roomDetail.setRoomNo(roomNo);
                roomDetail.setRoompicture(roomPicture);
                roomDetail.setRoomType(roomType);
                roomDetail.setRoomPrice(roomPrice);
                roomDetails.add(roomDetail);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return roomDetails;
    }

    // Method to update a room detail in the database
    public boolean updateRoomDetail(RoomDetail roomDetail) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROOMS_SQL)) {
            preparedStatement.setInt(1, roomDetail.getRoomNo());
            preparedStatement.setBytes(2, roomDetail.getRoompicture());
            preparedStatement.setString(3, roomDetail.getRoomType());
            preparedStatement.setDouble(4, roomDetail.getRoomPrice());
            preparedStatement.setString(5, roomDetail.getRoomId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    // Method to delete a room detail from the database
    public boolean deleteRoomDetail(String roomId) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROOMS_SQL)) {
            preparedStatement.setString(1, roomId);
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
