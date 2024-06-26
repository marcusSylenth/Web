import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/reservation")
public class RoomReservationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RoomReservationDAO roomReservationDAO;
    private RoomDetailDAO roomDetailDAO;
    private UserProfileDAO userProfileDAO;

    // Initialize DAOs
    public void init() {
        roomReservationDAO = new RoomReservationDAO();
        roomDetailDAO = new RoomDetailDAO();
        userProfileDAO = new UserProfileDAO();
    }

    // Handle POST requests by redirecting them to the doGet method
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // Handle GET requests and route to appropriate methods based on action parameter
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertReservation(request, response);
                    break;
                case "delete":
                    deleteReservation(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateReservation(request, response);
                    break;
                case "list":
                    listReservations(request, response);
                    break;
                default:
                    listReservations(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // List all reservations and forward to reservation-list.jsp
    private void listReservations(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<RoomReservation> listReservations = roomReservationDAO.getAllRoomReservations();
        request.setAttribute("listReservations", listReservations);
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservation-list.jsp");
        dispatcher.forward(request, response);
    }

    // Show form to create a new reservation and forward to reservation-form.jsp
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<RoomDetail> roomDetails = roomDetailDAO.getAllRoomDetails();
        request.setAttribute("roomDetails", roomDetails);
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservation-form.jsp");
        dispatcher.forward(request, response);
    }

    // Show form to edit an existing reservation and forward to reservation-form.jsp
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int reservationCode = Integer.parseInt(request.getParameter("code"));
        RoomReservation existingReservation = roomReservationDAO.getRoomReservation(reservationCode);
        List<RoomDetail> roomDetails = roomDetailDAO.getAllRoomDetails();
        request.setAttribute("reservation", existingReservation);
        request.setAttribute("roomDetails", roomDetails);
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservation-form.jsp");
        dispatcher.forward(request, response);
    }

    // Insert a new reservation into the database and redirect to the list page
    private void insertReservation(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userId = request.getParameter("userId");
        Date checkInDate = Date.valueOf(request.getParameter("checkInDate"));
        Date checkOutDate = Date.valueOf(request.getParameter("checkOutDate"));
        String roomType = request.getParameter("roomType");
        String roomId = request.getParameter("roomId");
        int noOfCustomers = Integer.parseInt(request.getParameter("noOfCustomers"));
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));

        RoomReservation newReservation = new RoomReservation();
        newReservation.setUserId(userId);
        newReservation.setCheckInDate(checkInDate.toLocalDate());
        newReservation.setCheckOutDate(checkOutDate.toLocalDate());
        newReservation.setRoomType(roomType);
        newReservation.setRoomId(roomId);
        newReservation.setNumOfCustomers(noOfCustomers);
        newReservation.setTotalPrice(totalPrice);

        roomReservationDAO.addRoomReservation(newReservation);
        response.sendRedirect("reservation?action=list");
    }

    // Update an existing reservation in the database and redirect to the list page
    private void updateReservation(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int reservationCode = Integer.parseInt(request.getParameter("reservationCode"));
        String userId = request.getParameter("userId");
        Date checkInDate = Date.valueOf(request.getParameter("checkInDate"));
        Date checkOutDate = Date.valueOf(request.getParameter("checkOutDate"));
        String roomType = request.getParameter("roomType");
        String roomId = request.getParameter("roomId");
        int noOfCustomers = Integer.parseInt(request.getParameter("noOfCustomers"));
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));

        RoomReservation reservation = new RoomReservation();
        reservation.setReservationCode(reservationCode);
        reservation.setUserId(userId);
        reservation.setCheckInDate(checkInDate.toLocalDate());
        reservation.setCheckOutDate(checkOutDate.toLocalDate());
        reservation.setRoomType(roomType);
        reservation.setRoomId(roomId);
        reservation.setNumOfCustomers(noOfCustomers);
        reservation.setTotalPrice(totalPrice);

        roomReservationDAO.updateRoomReservation(reservation);
        response.sendRedirect("reservation?action=list");
    }

    // Delete a reservation from the database and redirect to the list page
    private void deleteReservation(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int reservationCode = Integer.parseInt(request.getParameter("code"));
        roomReservationDAO.deleteRoomReservation(reservationCode);
        response.sendRedirect("reservation?action=list");
    }
}
