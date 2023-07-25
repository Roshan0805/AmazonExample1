package com.amazon.servlet;

import com.amazon.controller.AuthenticationController;
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
 * Represents the servlet request and response for {@link User} SignIn
 * </p>
 *
 * @author Roshan B
 * @version 1.0
 */
@WebServlet ("/authentication")
public class AuthenticationServlet extends HttpServlet {

    private final AuthenticationController authenticationController;

    public AuthenticationServlet() {
        authenticationController = AuthenticationController.getInstance();
    }

    /**
     * <p>
     *     Represent the post method in {@link HttpServlet} to provide {@link User} sign up
     * </p>
     * @param request Represents the request object from the api
     * @param response Represents the response object sent from this servlet method
     */
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) {

        try {

            final StringBuffer buffer = new StringBuffer();
            String value;

            while ((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String requestValue = buffer.toString();
            final JSONObject jsonObject = new JSONObject(requestValue);
            final User user = new User();

            user.setEmail(jsonObject.getString("email"));
            user.setPhoneNumber(jsonObject.getString("phoneNumber"));
            user.setPassword(jsonObject.getString("password"));
            user.setName(jsonObject.getString("name"));
            user.setAddress(jsonObject.getString("address"));

            final JSONObject responseData = new JSONObject();

            if (authenticationController.signUp(user)) {
                responseData.put("Message", "Sign up successfully");
            } else {
                responseData.put("Message", "Sign up unsuccessful");
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
     * Represent the post method in {@link HttpServlet} to provide {@link User} sign in
     * </p>
     *
     * @param request  Represents the request object from the api
     * @param response Represents the response object sent from this servlet method
     */
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) {

        try {

            final StringBuffer buffer = new StringBuffer();
            String value;

            while ((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String json = buffer.toString();
            final JSONObject user = new JSONObject(json);

            final JSONObject responseData = new JSONObject();
            response.setContentType("application/json");

            if (authenticationController.signIn(user.getString("email"), user.getString("password"))) {
                responseData.put("Message", "Sign in successfully");

            } else {
                responseData.put("Message","Sign In unsuccessful");
            }

            final PrintWriter writer = response.getWriter();
            writer.println(responseData);

        } catch (IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }
}
