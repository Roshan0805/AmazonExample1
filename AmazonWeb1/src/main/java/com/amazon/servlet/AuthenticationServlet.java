package com.amazon.servlet;

import com.amazon.controller.AuthenticationController;
import com.amazon.exception.ServletException;
import com.amazon.model.User;
import com.amazon.view.validation.UserValidation;
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
    private final UserValidation userValidation;
    final StringBuffer buffer;
    private String value;

    public AuthenticationServlet() {
        this.authenticationController = AuthenticationController.getInstance();
        this.userValidation = UserValidation.getInstance();
        this.buffer = new StringBuffer();
    }

    /**
     * <p>
     *    The doPost method is typically used for operations that modify server-side data
     * </p>
     * @param request Describe the request object from the api
     * @param response Describe the response object sent from this servlet method
     */
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) {

        try {
            while ((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String requestValue = buffer.toString();
            final JSONObject jsonObject = new JSONObject(requestValue);
            final User user = new User();
            final JSONObject responseData = new JSONObject();

            if (userValidation.validateUserName(jsonObject.getString("name"))) {
                user.setName(jsonObject.getString("name"));

                if (userValidation.validateEmail(jsonObject.getString("email"))) {
                    user.setName(jsonObject.getString("email"));

                    if (userValidation.validatePassword(jsonObject.getString("password"))) {
                        user.setName(jsonObject.getString("password"));

                        if (userValidation.validateAddress(jsonObject.getString("address"))) {
                            user.setName(jsonObject.getString("address"));

                            if (userValidation.validatePhone(jsonObject.getString("phoneNumber"))) {
                                user.setName(jsonObject.getString("phoneNumber"));

                                if (authenticationController.signUp(user)) {
                                    responseData.put("Message", "Sign up successfully");
                                }
                            }
                        }
                    }
                }
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
     * @param request  Describes the request object from the api
     * @param response Describes the response object sent from this servlet method
     */
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) {

        try {
            while ((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String json = buffer.toString();
            final JSONObject user = new JSONObject(json);

            final JSONObject responseData = new JSONObject();
            response.setContentType("application/json");

            if (userValidation.validateEmail(user.getString("email"))) {

                if (userValidation.validatePassword(user.getString("password"))) {

                    responseData.put("Message", (authenticationController.signIn(user.getString("email"), user.getString("password"))) ?
                            "Sign in successfully" : "Sign In unsuccessful");
                }
            }

            final PrintWriter writer = response.getWriter();
            writer.println(responseData);

        } catch (IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }
}
