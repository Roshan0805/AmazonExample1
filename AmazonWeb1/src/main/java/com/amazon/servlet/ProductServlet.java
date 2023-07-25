package com.amazon.servlet;

import com.amazon.controller.ProductController;
import com.amazon.exception.ServletException;
import com.amazon.model.Product;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * <p>
 * Represents the servlet request and response for {@link ProductServlet}
 * </p>
 *
 * @author Roshan B
 * @version 1.0
 */
@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    private final ProductController productController;

    public ProductServlet() {
        productController = ProductController.getInstance();
    }

    /**
     * <p>
     * Represent the post method in {@link HttpServlet} to add {@link Product}
     * </p>
     *
     * @param request  Represents the request object from the api
     * @param response Represents the response object sent from this servlet method
     */
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) {

        try {

            final StringBuffer buffer = new StringBuffer();
            String value;

            while ((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String json = buffer.toString();
            final JSONObject productJson = new JSONObject(json);

            final Product product = new Product();

            product.setCategory(productJson.getEnum(Product.Category.class, "category"));
            product.setName(productJson.getString("name"));
            product.setDescription(productJson.getString("description"));
            product.setAvailable(productJson.getLong("available"));
            product.setPrice(productJson.getDouble("price"));
            product.setUpdatedTime(Timestamp.valueOf(productJson.getString("updatedTime")));
            product.setUserId(productJson.getLong("userId"));

            final JSONObject responseData = new JSONObject();

            if (productController.add(product)) {
                responseData.put("Message", "Product added successfully");
            } else {
                responseData.put("Message", "Product added unsuccessful");
            }
            response.setContentType("application/json");
            final PrintWriter writer = response.getWriter();

            writer.println(responseData);

        } catch (NumberFormatException | IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Represent the get method in {@link HttpServlet} to get {@link Product}
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
            final JSONObject productJson = new JSONObject(json);
            final JSONObject responseData = new JSONObject();
            final Product product = productController.get(productJson.getLong("id"));

            if (product == null) {
                responseData.put("Invalid id", "Enter a valid id");
            } else {
                responseData.put("id", product.getId());
                responseData.put("name", product.getName());
                responseData.put("description", product.getDescription());
                responseData.put("available", product.getAvailable());
                responseData.put("updatedTime", product.getUpdatedTime());
                responseData.put("category", product.getCategory());
                responseData.put("userId", product.getUserId());
            }
            response.setContentType("application/json");
            final PrintWriter writer = response.getWriter();

            writer.println(responseData);

        } catch (NumberFormatException | IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Represent the put method in {@link HttpServlet} to update {@link Product}
     * </p>
     *
     * @param request  Represents the request object from the api
     * @param response Represents the response object sent from this servlet method
     */
    public void doPut(final HttpServletRequest request, final HttpServletResponse response) {

        try {

            final StringBuffer buffer = new StringBuffer();
            String value;

            while ((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String json = buffer.toString();
            final JSONObject productJson = new JSONObject(json);
            final JSONObject responseData = new JSONObject();
            final Product product = productController.get(productJson.getLong("id"));

            if (product == null) {
                responseData.put("Invalid id", "Enter a valid id");
            } else {
                product.setCategory(productJson.getEnum(Product.Category.class, "category"));
                product.setName(productJson.getString("name"));
                product.setDescription(productJson.getString("description"));
                product.setAvailable(productJson.getLong("available"));
                product.setPrice(productJson.getDouble("price"));
                product.setUpdatedTime(Timestamp.valueOf(productJson.getString("updatedTime")));
                product.setUserId(productJson.getLong("userId"));
            }

            if (productController.update(productJson.getLong("id"), product)) {
                responseData.put("Message", "Product added successfully");
            } else {
                responseData.put("Message", "Product added unsuccessful");
            }
            response.setContentType("application/json");
            final PrintWriter writer = response.getWriter();

            writer.println(responseData);

        } catch (NumberFormatException | IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Represent the delete method in {@link HttpServlet} to delete {@link Product}
     * </p>
     *
     * @param request  Represents the request object from the api
     * @param response Represents the response object sent from this servlet method
     */
    public void doDelete(final HttpServletRequest request, final HttpServletResponse response) {

        try {

            final StringBuffer buffer = new StringBuffer();
            String value;

            while ((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String json = buffer.toString();
            final JSONObject productJson = new JSONObject(json);
            final JSONObject responseData = new JSONObject();
            final Product product = productController.get(productJson.getLong("id"));

            if (product == null) {
                responseData.put("Invalid id", "Enter a valid id");
            }

            if (productController.delete(productJson.getLong("id"))) {
                responseData.put("Message", "Product deleted successfully");
            } else {
                responseData.put("Message", "Product delete unsuccessful");
            }
            response.setContentType("application/json");
            final PrintWriter writer = response.getWriter();

            writer.println(responseData);

        } catch (NumberFormatException | IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }
}
