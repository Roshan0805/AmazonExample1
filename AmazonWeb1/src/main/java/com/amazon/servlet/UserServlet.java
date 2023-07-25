package com.amazon.servlet;

import com.amazon.controller.UserController;
import com.amazon.exception.ServletException;
import com.amazon.view.validation.UserValidation;
import com.amazon.model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * <p>
 * Represents the servlet request and response for {@link User} servlet
 * </p>
 *
 * @author Roshan B
 * @version 1.0
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final UserController userController;
    private final UserValidation userValidation;

    public UserServlet() {
        this.userController = UserController.getInstance();
        userValidation = UserValidation.getInstance();
    }

    /**
     * <p>
     *     Represent the get method in {@link HttpServlet} to provide {@link User} object
     * </p>
     * @param request Represents the request object for update user values
     * @param response Represents the response object created by this method
     */
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        response.setContentType("application/json");

        try {
            final StringBuffer buffer = new StringBuffer();
            String value;

            while ((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String json = buffer.toString();
            final JSONObject userJson = new JSONObject(json);
            final JSONObject responseData = new JSONObject();
            final PrintWriter writer = response.getWriter();

            if (json.isEmpty()) {
                final Collection<User> users = userController.getUsers();
                final JSONArray jsonUsers = new JSONArray();

                for (User user : users) {
                    final JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", user.getId());
                    jsonObject.put("name", user.getName());
                    jsonObject.put("email", user.getEmail());
                    jsonObject.put("address", user.getAddress());
                    jsonObject.put("phone no", user.getPhoneNumber());

                    jsonUsers.put(jsonObject);
                }
                writer.print(jsonUsers);
                return;
            } else {
                final User user = userController.getDetail(userJson.getLong("id"));

                if (user == null) {
                    responseData.put("Message", "User not found");
                } else {
                    responseData.put("id", user.getId());
                    responseData.put("name", user.getName());
                    responseData.put("email", user.getEmail());
                    responseData.put("address", user.getAddress());
                    responseData.put("phone no", user.getPhoneNumber());
                }
            }
            writer.println(responseData);
        } catch (IOException exception) {
            throw new ServletException(exception.getMessage());
        }
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
            } else {
                user.setName(userJson.getString("name"));
                user.setEmail(userJson.getString("email"));
                user.setPassword(userJson.getString("password"));
                user.setAddress(userJson.getString("address"));
                user.setPhoneNumber(userJson.getString("phoneNumber"));
            }

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
