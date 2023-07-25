package com.amazon.controller;

import com.amazon.model.Cart;
import com.amazon.model.Order;
import com.amazon.model.Product;
import com.amazon.model.User;
import com.amazon.service.Impl2.ProductServiceImpl2;
import com.amazon.service.ProductService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Acts as a Control between service and the view to {@link Product}
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class ProductController {

    private static final ProductController PRODUCT_CONTROLLER = new ProductController();
    private final ProductService productService;

    private ProductController() {
        productService = ProductServiceImpl2.getInstance();
    }

    /**
     * <p>
     * Method to provide access to the single instance
     * </p>
     *
     * @return Represents {@link ProductController}
     */
    public static ProductController getInstance() {
        return PRODUCT_CONTROLLER;
    }

    /**
     * <p>
     * This method is used to add a new product to the database
     * </p>
     *
     * @param product Represents {@link Product}
     * @return True if the {@link Product} is added successfully in the product list otherwise return false
     */
    public boolean create(final Product product) {
        return productService.create(product);
    }

    /**
     * <p>
     * Retrieves a list of all products from the database
     * </p>
     *
     * @return Collection view of products from the viewProduct method
     */
    public Collection<Product> getProducts() {
        return productService.getProducts();
    }

    /**
     * <p>
     * Describe the product details that are created by the user
     * </p>
     *
     * @param userId Represents admin id
     * @return Represents {@link Product} list created by the user
     */
    public Map<Long, Product> getUserProducts(final Long userId) {
        return productService.getUserProduct(userId);
    }

    /**
     * <p>
     * Retrieve product object from the database corresponding to the product id
     * </p>
     *
     * @param productId describe id of the product
     * @return Represents {@link Product}
     */
    public Product get(final Long productId) {
        return productService.get(productId);
    }

    /**
     * <p>
     * Updates product information in the database
     * </p>
     *
     * @param id      id of the product
     * @param product Represents {@link Product}
     * @return True if the {@link Product} is updated successfully in the product list otherwise return false
     */
    public boolean update(final Long id, final Product product) {
        return productService.update(id, product);
    }

    /**
     * <p>
     * Deletes the product from database
     * </p>
     *
     * @param id id of the product
     * @return True if the {@link Product} is deleted successfully in the product list otherwise return false
     */
    public boolean delete(final Long id) {
        return productService.delete(id);
    }

    /**
     * <p>
     *     Processes a product to order for a customer
     * </p>
     *
     * @param order Represents {@link Order}
     * @return True if the order is added to the order list otherwise it returns false
     */
    public boolean order(final Order order) {
        return productService.order(order);
    }

    /**
     * <p>
     *     Retrieves a list of all {@link Order} from the database
     * </p>
     *
     * @return Represents collection of {@link Order}
     */
    public List<Order> getOrdersList(final Long userId) {
        return productService.getOrderList(userId);
    }

    /**
     * <p>
     *     Describes the order details of the particular order id from the database
     * </p>
     *
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     */
    public Order getOrder(final Long orderId) {
        return productService.getOrder(orderId);
    }

    /**
     * <p>
     *     Describes the cancelling the order of the particular order id
     * </p>
     *
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     */
    public boolean cancelOrder(final Long orderId) {
        return productService.cancelOrder(orderId);
    }

    /**
     * <p>
     *     Describes adding a product to the cart
     * </p>
     *
     * @param cart Represents the {@link Cart}
     * @return True if the Product is added successfully in cart otherwise return false
     */
    public boolean addToCart(final Cart cart) {
        return productService.addToCart(cart);
    }

    /**
     * <p>
     *     Describes the Product details from the cart for a particular user
     * </p>
     *
     * @param userId Represents the id of {@link User}
     * @return Collection of products from the cart
     */
    public Collection<Cart> getCartList(final Long userId) {
        return productService.getCartList(userId);
    }

    /**
     * <p>
     *     Describes the particular id details of entered cart id
     * </p>
     *
     * @param id Represents the id of the cart
     * @return Represents {@link Cart}
     */
    public Cart getCart(final Long id) {
        return productService.getCart(id);
    }

    /**
     * <p>
     *     describes the removal of product for the particular cart id
     * </p>
     *
     * @param cartId Represents the id of the cart
     * @return True if the Product is removed successfully otherwise return false
     */
    public boolean removeCart(final Long cartId) {
        return productService.removeFromCart(cartId);
    }

    /**
     * <p>
     *     describes the product id's of the user created product
     * </p>
     *
     * @param userId Represents the id of the {@link User}
     * @return List of product id retrieved from database
     */
    public List<Long> getCartProductIds(final Long userId) {
        return productService.getCartProductIds(userId);
    }

    /**
     * <p>
     *     Describes updating the quantity of product in cart
     * </p>
     *
     * @param quantity  Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully otherwise return false
     */
    public boolean updateQuantityInCart(final Long quantity, final Long productId) {
        return productService.updateQuantityInCart(quantity, productId);
    }

    /**
     * <p>
     *     Describes updating the quantity of product in {@link Product}
     * </p>
     *
     * @param quantity  Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    public boolean updateQuantityInProduct(final Long quantity, final Long productId) {
        return productService.updateQuantityInProduct(quantity, productId);
    }
}