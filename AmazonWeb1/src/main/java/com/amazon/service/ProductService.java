package com.amazon.service;

import com.amazon.model.Cart;
import com.amazon.model.Order;
import com.amazon.model.Product;
import com.amazon.model.User;
import com.amazon.service.impl.ProductServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Provides services for {@link Product}
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public interface ProductService {

    /**
     * <p>
     * Method to provide access to the single instance for accessing
     * </p>
     *
     * @return Represents the object of {@link ProductServiceImpl}
     */
    static ProductService getInstance() {
        return ProductServiceImpl.getInstance();
    }

    /**
     * <p>
     * This method is used to add a new product to the database
     * </p>
     *
     * @param product Represents {@link Product}
     * @return True if the {@link Product} is added successfully in the product list otherwise return false
     */
    boolean create(final Product product);

    /**
     * <p>
     * Retrieves a list of all products from the database
     * </p>
     *
     * @return Collection view of products from the viewProduct method
     */
    Collection<Product> getProducts();

    /**
     * Defines the product details that the user create
     *
     * @param userId Represents admin id
     * @return Represents {@link Product} list created by the user
     */
    Map<Long, Product> getUserProduct(final Long userId);

    /**
     * <p>
     * Retrieve product object from the database
     * </p>
     *
     * @param productId id of the product
     * @return Represents {@link Product}
     */
    Product get(final Long productId);

    /**
     * <p>
     * Updates product object in database
     * </p>
     *
     * @param id      Product id of the product
     * @param product Represent {@link Product}
     * @return True if the {@link Product} is updated successfully in the product list otherwise return false
     */
    boolean update(final Long id, final Product product);

    /**
     * <p>
     * Deletes the product object from the database
     * </p>
     *
     * @param id id of the product object
     * @return True if the {@link Product} deleted successfully in the product list otherwise return false
     */
    boolean delete(final Long id);

    /**
     * Describes the order of {@link Product}
     *
     * @param order Represents {@link Order}
     * @return True if the order is added to the order list
     */
    boolean order(final Order order);

    /**
     * Describes the List of {@link Order}
     *
     * @return Represents collection of {@link Order}
     */
    List<Order> getOrderList(final Long userId);

    /**
     * Retrieves the order details of the particular order id
     *
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     */
    Order getOrder(final Long orderId);

    /**
     * Describes the cancelling the order of the particular order id
     *
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     */
    boolean cancelOrder(final Long orderId);

    /**
     * Describes adding the product to cart list
     *
     * @param cart Represents {@link Cart}
     * @return True if the product is added to cart successfully
     */
    boolean addToCart(final Cart cart);

    /**
     * Describes  the Product details from the cart for a particular user
     *
     * @param userId Represents the id of {@link User}
     * @return Collection of products from the cart
     */
    List<Cart> getCartList(final Long userId);

    /**
     * Retrieves the particular id details of entered cart id
     *
     * @param id Represents the id of the cart
     * @return Represents {@link Cart}
     */
    Cart getCart(final Long id);

    /**
     * Retrieves the product id's of the user created product
     *
     * @param userId Represents the id of the {@link User}
     * @return List of product id's
     */
    List<Long> getCartProductIds(final Long userId);

    /**
     * Describes  the removal of product for the particular cart id
     *
     * @param cartId Represents the id of the cart
     * @return True if the Product is removed successfully
     */
    boolean removeFromCart(final Long cartId);

    /**
     * describes updating the quantity of product in cart
     *
     * @param quantity  Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    boolean updateQuantityInCart(final Long quantity, final Long productId);

    /**
     * describes updating the quantity of product in {@link Product}
     *
     * @param quantity  Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    boolean updateQuantityInProduct(final Long quantity, final Long productId);
}