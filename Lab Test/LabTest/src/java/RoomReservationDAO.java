import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomReservationDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/labtest";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    private static final String INSERT_RESERVATION_SQL = "INSERT INTO room_reservations (reservation_code, user_id, check_in_date, check_out_date, room_type, room_id, no_of_customers, total_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_RESERVATION_BY_CODE = "SELECT * FROM room_reservations WHERE reservation_code = ?;";
    private static final String SELECT_ALL_RESERVATIONS = "SELECT * FROM room_reservations;";
    private static final String DELETE_RESERVATION_SQL = "DELETE FROM room_reservations WHERE reservation_code = ?;";
    private static final String UPDATE_RESERVATION_SQL = "UPDATE room_reservations SET user_id = ?, check_in_date = ?, check_out_date = ?, room_type = ?, room_id = ?, no_of_customers = ?, total_price = ? WHERE reservation_code = ?;";

    public RoomReservationDAO() {}

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

    public void addRoomReservation(RoomReservation roomReservation) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESERVATION_SQL)) {
            preparedStatement.setInt(1, roomReservation.getReservationCode());
            preparedStatement.setString(2, roomReservation.getUserId());
            preparedStatement.setDate(3, Date.valueOf(roomReservation.getCheckInDate()));
            preparedStatement.setDate(4, Date.valueOf(roomReservation.getCheckOutDate()));
            preparedStatement.setString(5, roomReservation.getRoomType());
            preparedStatement.setString(6, roomReservation.getRoomId());
            preparedStatement.setInt(7, roomReservation.getNumOfCustomers());
            preparedStatement.setDouble(8, roomReservation.getTotalPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public RoomReservation getRoomReservation(int reservationCode) {
        RoomReservation roomReservation = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RESERVATION_BY_CODE)) {
            preparedStatement.setInt(1, reservationCode);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                Date checkInDate = rs.getDate("check_in_date");
                Date checkOutDate = rs.getDate("check_out_date");
                String roomType = rs.getString("room_type");
                String roomId = rs.getString("room_id");
                int noOfCustomers = rs.getInt("no_of_customers");
                double totalPrice = rs.getDouble("total_price");
                roomReservation = new RoomReservation();
                roomReservation.setReservationCode(reservationCode);
                roomReservation.setUserId(userId);
                roomReservation.setCheckInDate(checkInDate.toLocalDate());
                roomReservation.setCheckOutDate(checkOutDate.toLocalDate());
                roomReservation.setRoomType(roomType);
                roomReservation.setRoomId(roomId);
                roomReservation.setNumOfCustomers(noOfCustomers);
                roomReservation.setTotalPrice(totalPrice);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return roomReservation;
    }

    public List<RoomReservation> getAllRoomReservations() {
        List<RoomReservation> roomReservations = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RESERVATIONS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int reservationCode = rs.getInt("reservation_code");
                String userId = rs.getString("user_id");
                Date checkInDate = rs.getDate("check_in_date");
                Date checkOutDate = rs.getDate("check_out_date");
                String roomType = rs.getString("room_type");
                String roomId = rs.getString("room_id");
                int noOfCustomers = rs.getInt("no_of_customers");
                double totalPrice = rs.getDouble("total_price");
                RoomReservation roomReservation = new RoomReservation();
                roomReservation.setReservationCode(reservationCode);
                roomReservation.setUserId(userId);
                roomReservation.setCheckInDate(checkInDate.toLocalDate());
                roomReservation.setCheckOutDate(checkOutDate.toLocalDate());
                roomReservation.setRoomType(roomType);
                roomReservation.setRoomId(roomId);
                roomReservation.setNumOfCustomers(noOfCustomers);
                roomReservation.setTotalPrice(totalPrice);
                roomReservations.add(roomReservation);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return roomReservations;
    }

    public boolean updateRoomReservation(RoomReservation roomReservation) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESERVATION_SQL)) {
            preparedStatement.setString(1, roomReservation.getUserId());
            preparedStatement.setDate(2, Date.valueOf(roomReservation.getCheckInDate()));
            preparedStatement.setDate(3, Date.valueOf(roomReservation.getCheckOutDate()));
            preparedStatement.setString(4, roomReservation.getRoomType());
            preparedStatement.setString(5, roomReservation.getRoomId());
            preparedStatement.setInt(6, roomReservation.getNumOfCustomers());
            preparedStatement.setDouble(7, roomReservation.getTotalPrice());
            preparedStatement.setInt(8, roomReservation.getReservationCode());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    public boolean deleteRoomReservation(int reservationCode) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_SQL)) {
            preparedStatement.setInt(1, reservationCode);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }

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
