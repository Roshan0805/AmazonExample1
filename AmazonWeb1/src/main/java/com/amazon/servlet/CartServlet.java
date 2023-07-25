package com.amazon.servlet;

import com.amazon.controller.ProductController;
import com.amazon.exception.ServletException;
import com.amazon.model.Cart;

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
 *     Represents the servlet request and response for {@link CartServlet}
 * </p>
 * @author Roshan B
 * @version 1.0
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private final ProductController productController;

    public CartServlet() {
        productController = ProductController.getInstance();
    }

    /**
     * <p>
     *     Represent the post method in {@link HttpServlet} add product to the cart
     * </p>
     * @param request Represents the request object from the api
     * @param response Represents the response object sent from this servlet method
     */
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) {

        try {
            final StringBuffer buffer = new StringBuffer();
            String value;

            while((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String json = buffer.toString();
            final JSONObject cartJson = new JSONObject(json);
            final Cart cart = new Cart();
            final Product product = productController.get(cartJson.getLong("productId"));

            cart.setProductId(product.getId());
            cart.setProductName(product.getName());
            cart.setQuantity(cartJson.getLong("quantity"));
            cart.setUserId(cartJson.getLong("userId"));
            cart.setPrice(product.getPrice());


            final JSONObject responseData = new JSONObject();

            if (productController.addToCart(cart)) {
                responseData.put("Message", "Product added to cart successfully");
            } else {
                responseData.put("Message","Product added to cart unsuccessful");
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
     *     Represent the get method in {@link HttpServlet} to cart
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

            final Cart cart = productController.getCart(orderJson.getLong("id"));
            final JSONObject responseData = new JSONObject();

            if (cart == null) {
                responseData.put("Invalid id", "Enter a valid id");
            } else {
                responseData.put("id", cart.getId());
                responseData.put("name", cart.getProductName());
                responseData.put("productId", cart.getProductId());
                responseData.put("quantity", cart.getQuantity());
                responseData.put("userId", cart.getUserId());
                responseData.put("price", cart.getPrice() * cart.getQuantity());
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
     *     Represent the post method in {@link HttpServlet} add product to the cart
     * </p>
     * @param request Represents the request object from the api
     * @param response Represents the response object sent from this servlet method
     */
    public void doDelete(final HttpServletRequest request, final HttpServletResponse response) {

        try  {
            final StringBuffer buffer = new StringBuffer();
            String value;

            while((value = request.getReader().readLine()) != null) {
                buffer.append(value);
            }

            final String json = buffer.toString();
            final JSONObject cartJson = new JSONObject(json);
            final Cart cart = productController.getCart(cartJson.getLong("productId"));
            final JSONObject responseData = new JSONObject();

            if (cart == null) {
                responseData.put("Message", "Enter a valid cart id");
            }

            if (productController.removeCart(cartJson.getLong("productId"))) {
                responseData.put("Message", "Product delete from cart successfully");
            }
            response.setContentType("application/json");
            final PrintWriter writer = response.getWriter();

            writer.println(responseData);

        } catch (IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }
}
