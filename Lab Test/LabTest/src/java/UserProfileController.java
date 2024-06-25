import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserProfileDAO userProfileDAO;

    public void init() {
        userProfileDAO = new UserProfileDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
                case "list":
                    listUser(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<UserProfile> listUser = userProfileDAO.getAllUserProfiles();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");
        UserProfile existingUser = userProfileDAO.getUserProfile(userId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String gender = request.getParameter("gender");
        String contactNo = request.getParameter("contactNo");
        String id = request.getParameter("id");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");

        UserProfile newUser = new UserProfile(userId, userName, gender, contactNo, id, age, address);
        userProfileDAO.addUserProfile(newUser);
        response.sendRedirect("user?action=list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String gender = request.getParameter("gender");
        String contactNo = request.getParameter("contactNo");
        String id = request.getParameter("id");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");

        UserProfile user = new UserProfile(userId, userName, gender, contactNo, id, age, address);
        userProfileDAO.updateUserProfile(user);
        response.sendRedirect("user?action=list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userId = request.getParameter("id");
        userProfileDAO.deleteUserProfile(userId);
        response.sendRedirect("user?action=list");
    }
}
