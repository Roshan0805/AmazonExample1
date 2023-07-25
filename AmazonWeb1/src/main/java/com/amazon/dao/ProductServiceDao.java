package com.amazon.dao;

import com.amazon.dao.impl.ProductServiceDaoImpl;
import com.amazon.model.Cart;
import com.amazon.model.Order;
import com.amazon.model.Product;

import com.amazon.model.User;
import com.amazon.service.ProductService;
import com.amazon.service.impl.ProductServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Represents the {@link Product} service
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public interface ProductServiceDao {

    /**
     * <p>
     * Represents the {@link ProductService} interface implemented class object can be created for only one time
     * </p>
     *
     * @return Represents the object of {@link ProductServiceImpl}
     */
    static ProductServiceDao getInstance() {
           return ProductServiceDaoImpl.getInstance();
    }

    /**
     * <p>
     * Add product to the product list
     * </p>
     *
     * @param product Product object
     * @return Boolean true is the {@link Product} added successfully in the product list otherwise return false
     */
    boolean add(final Product product);

    /**
     * <p>
     * Provide the collection view of the products value
     * </p>
     *
     * @return Collection view of {@link Product}
     */
    Collection<Product> getAllProducts();

    /**
     * <p>
     *     Represents the product details that the user create
     * </p>
     *
     * @param userId Represents admin id
     * @return Represents {@link Product} list created by the user
     */
    Map<Long, Product> getUserProduct(final Long userId);

    /**
     * <p>
     * Gets the product from product list using productId
     * </p>
     *
     * @param productId product id of the product object
     * @return Represent {@link Product} in product list
     */
    Product get(final Long productId);

    /**
     * <p>
     * Updates product object in product list
     * </p>
     *
     * @param id      Product id of the product
     * @param product Represent {@link Product}
     * @return True if the {@link Product} is updated successfully in the product list otherwise return false
     */
    boolean update(final Long id, final Product product);

    /**
     * <p>
     * Deletes the product object from the product list
     * </p>
     *
     * @param id id of the product object
     * @return True if the {@link Product} deleted successfully in the product list otherwise return false
     */
    boolean delete(final Long id);

    /**
     * Represents adding the product to cart list
     * @param cart Represents {@link Cart}
     * @return True if the product is added to cart successfully
     */
    boolean addToCart(final Cart cart) ;

    /**
     * Represents the Product details from the cart for a particular user
     * @param userId Represents the id of {@link User}
     * @return Collection of products from the cart
     */
    List<Cart> getCartList(final Long userId);

    /**
     * Represents the particular id details of entered cart id
     * @param userId Represents the id of the {@link User}
     * @return Represents {@link Cart}
     */
    Cart getCart(final Long userId);


    /**
     * Represents the removal of product for the particular cart id
     * @param cartId Represents the id of the cart
     * @return True if the Product is removed successfully
     */
    boolean removeCart(final Long cartId);

    /**
     * Represents the product id's of the user created product
     * @param userId Represents the id of the {@link User}
     * @return List of product id's
     */
    List<Long> getCartProductIds(final Long userId);

    /**
     * Represents updating the quantity of product in {@link Cart}
     * @param quantity Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    boolean updateQuantityInCart(final Long quantity, final Long productId);

    /**
     * Represents updating the quantity of product in {@link Product}
     * @param quantity Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    boolean updateQuantityInProduct(final Long quantity, final Long productId);

    /**
     * Represents the order of {@link Product}
     *
     * @param order Represents {@link Order}
     * @return True if the order is added to the order list
     */
    boolean order(final Order order);

    /**
     * Retrieve the List of {@link Order}
     *
     * @return Represents collection of {@link Order}
     */
    List<Order> getOrderList(final Long userId);

    /**
     * Represents the order details of the particular order id
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     */
    Order getOrder(final Long orderId);

    /**
     * Represents the cancelling the order of the particular order id
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     */
    boolean cancelOrder(final Long orderId);
}
