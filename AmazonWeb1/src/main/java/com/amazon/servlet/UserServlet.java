package com.amazon.servlet;

import com.amazon.controller.UserController;
import com.amazon.exception.ServletException;
import com.amazon.model.User;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * Represents the {@link User} servlet
 * </p>
 *
 * @author Roshan B
 * @version 1.0
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final UserController userController;

    public UserServlet() {
        userController = UserController.getInstance();
    }

    /**
     * <p>
     *     Represent the post method in {@link HttpServlet} to provide {@link User} update
     * </p>
     * @param request Represents the request object for update user values
     * @param response Represents the response object created by this method
     */
    public void doPut(final HttpServletRequest request, final HttpServletResponse response) {

        try {
            final StringBuffer buffer = new StringBuffer();
            String value;

            while ((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String json = buffer.toString();
            final JSONObject userJson = new JSONObject(json);
            final JSONObject responseData = new JSONObject();
            final User user = userController.getDetail(userJson.getLong("id"));

            if (user == null) {
                responseData.put("Message", "User not found");
            }
            user.setName(userJson.getString("name"));
            user.setEmail(userJson.getString("email"));
            user.setPassword(userJson.getString("password"));
            user.setAddress(userJson.getString("address"));
            user.setPhoneNumber(userJson.getString("phoneNumber"));

            if (userController.update(user, userJson.getLong("id"))) {
                responseData.put("Message", "Updated successfully");
            } else {
                responseData.put("Message", "User not found updated unsuccessful");
            }
            response.setContentType("application/json");
            final PrintWriter writer = response.getWriter();

            writer.println(responseData);
        } catch (IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Represent the post method in {@link HttpServlet} to provide {@link User} delete
     * </p>
     * @param request Represents the request object for update user values
     * @param response Represents the response object created by this method
     */
    public void doDelete(final HttpServletRequest request, final HttpServletResponse response) {

        try  {
            final StringBuffer buffer = new StringBuffer();
            String value;

            while ((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String json = buffer.toString();
            final JSONObject userJson = new JSONObject(json);
            final JSONObject responseData = new JSONObject();

            if (userController.delete(userJson.getLong("id"))) {
                responseData.put("Message", "User deleted successfully");
            } else {
                responseData.put("Message", "User not found");
            }
            final PrintWriter writer = response.getWriter();

            writer.println(responseData);
        } catch (IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }
}
