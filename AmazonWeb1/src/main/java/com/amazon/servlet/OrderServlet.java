package com.amazon.servlet;

import com.amazon.controller.ProductController;
import com.amazon.exception.DBException;
import com.amazon.exception.ServletException;
import com.amazon.exception.UnavailableQuantityException;
import com.amazon.model.Order;
import com.amazon.model.Product;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 *     Represents the servlet request and response for {@link OrderServlet}
 * </p>
 * @author Roshan B
 * @version 1.0
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private final ProductController productController;

    public OrderServlet() {
        productController = ProductController.getInstance();
    }

    /**
     * <p>
     *     Represent the post method in {@link HttpServlet} to order the product
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

            final String json = buffer.toString();
            final JSONObject orderJson = new JSONObject(json);

            final Product product = productController.get(orderJson.getLong("productId"));

            if (product.getAvailable() < orderJson.getLong("quantity")) {
                throw new UnavailableQuantityException("Un available quantity");
            }
            final Order order = new Order();

            order.setProductId(product.getId());
            order.setProductName(product.getName());
            order.setQuantity(orderJson.getLong("quantity"));
            order.setUserId(orderJson.getLong("userId"));
            order.setPrice(product.getPrice());
            order.setPaymentType(orderJson.getEnum(Order.Payment.class, "paymentType".toUpperCase()));

            final JSONObject responseData = new JSONObject();

            if (productController.order(order)) {
                responseData.put("Message", "Product added successfully");
            } else {
                responseData.put("Message", "Product added unsuccessful");
            }
            response.setContentType("application/json");
            final PrintWriter writer = response.getWriter();

            writer.println(responseData);
        } catch (IOException exception) {
            throw new ServletException(exception.getMessage());
        } catch (UnavailableQuantityException exception) {
            throw new DBException("Unavailable quantity");
        }
    }

    /**
     * <p>
     *     Represent the get method in {@link HttpServlet} to order
     * </p>
     * @param request Represents the request object from the api
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
            final JSONObject orderJson = new JSONObject(json);

            final Order order = productController.getOrder(orderJson.getLong("id"));
            final JSONObject responseData = new JSONObject();

            if (order == null) {
                responseData.put("Invalid id", "Enter a valid id");
            } else {
                responseData.put("id", order.getId());
                responseData.put("name", order.getProductName());
                responseData.put("productId", order.getProductId());
                responseData.put("quantity", order.getQuantity());
                responseData.put("payment type", order.getPaymentType());
                responseData.put("userId", order.getUserId());
                responseData.put("price", order.getPrice() * order.getQuantity());
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
     *     Represent the post method in {@link HttpServlet} to order the product
     * </p>
     * @param request Represents the request object from the api
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
            final JSONObject orderJson = new JSONObject(json);

            final Order order = productController.getOrder(orderJson.getLong("id"));
            final JSONObject responseData = new JSONObject();

            if (order == null) {
                responseData.put("Message", "Enter a valid order id");
            }

            if (productController.cancelOrder(orderJson.getLong("id"))) {
                responseData.put("Message", "Product added successfully");
            }
            response.setContentType("application/json");
            final PrintWriter writer = response.getWriter();

            writer.println(responseData);
        } catch (IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }
}
