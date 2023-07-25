package com.amazon.service.Impl2;

import com.amazon.dao.ProductServiceDao;
import com.amazon.model.Cart;
import com.amazon.model.Order;
import com.amazon.model.Product;
import com.amazon.model.User;
import com.amazon.service.ProductService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Implements the {@link ProductServiceDao} to provide services for {@link  Product}
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class ProductServiceImpl2 implements ProductService {

    private static final ProductService PRODUCT_SERVICE = new ProductServiceImpl2();
    private final ProductServiceDao productServiceDao;

    private ProductServiceImpl2() {
        productServiceDao = ProductServiceDao.getInstance();
    }

    /**
     * <p>
     * Represents the object of {@link ProductService} class can be created for only one time
     * </p>
     *
     * @return Represents object of {@link ProductService}
     */
    public static ProductService getInstance() {
        return PRODUCT_SERVICE;
    }

    /**
     * <p>
     * Add product to the product list
     * </p>
     *
     * @param product Product object
     * @return Boolean true is the {@link Product} added successfully in the product list otherwise return false
     */
    public boolean add(Product product) {
        return productServiceDao.add(product);
    }

    /**
     * <p>
     * Provide the collection view of the products value
     * </p>
     *
     * @return Collection view of {@link Product}
     */
    public Collection<Product> getAllProducts() {
        return productServiceDao.getAllProducts();
    }

    /**
     * Represents the product details that the user create
     *
     * @param userId Represents admin id
     * @return Represents {@link Product} list created by the user
     */
    public Map<Long, Product> getUserProduct(final Long userId) {
        return productServiceDao.getUserProduct(userId);
    }

    /**
     * <p>
     * Gets the product from product list using productId
     * </p>
     *
     * @param productId product id of the product object
     * @return Represent {@link Product} in product list
     */
    public Product get(final Long productId) {
        return productServiceDao.get(productId);
    }

    /**
     * <p>
     * Updates product object in product list
     * </p>
     *
     * @param id      Product id of the product
     * @param product Represent {@link Product}
     * @return True if the {@link Product} is updated successfully in the product list otherwise return false
     */
    public boolean update(final Long id, final Product product) {
        return productServiceDao.update(id, product);
    }

    /**
     * <p>
     * Deletes the product object from the product list
     * </p>
     *
     * @param id id of the product object
     * @return True if the {@link Product} deleted successfully in the product list otherwise return false
     */
    public boolean delete(final Long id) {
        return productServiceDao.delete(id);
    }

    /**
     * Represents the order of {@link Product}
     *
     * @param order Represents {@link Order}
     * @return True if the order is added to the order list
     */
    public boolean order(final Order order) {
        return productServiceDao.order(order);
    }

    /**
     * Retrieve the List of {@link Order}
     *
     * @param userId Represent the id of user
     * @return Represents collection of {@link Order}
     */
    public List<Order> getOrderList(final Long userId) {
        return productServiceDao.getOrderList(userId);
    }

    /**
     * Represents the order details of the particular order id
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     */
    public Order getOrder(final Long orderId) {
        return productServiceDao.getOrder(orderId);
    }

    /**
     * Represents the cancelling the order of the particular order id
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     */
    public boolean cancelOrder(final Long orderId) {
        return productServiceDao.cancelOrder(orderId);
    }

    /**
     * Represents adding the product to cart list
     * @param cart Represents {@link Cart}
     * @return True if the product is added to cart successfully
     */
    public boolean addToCart(final Cart cart) {
        return productServiceDao.addToCart(cart);
    }

    /**
     * Represents the Product details from the cart for a particular user
     * @param userId Represents the id of {@link User}
     * @return Collection of products from the cart
     */
    public List<Cart> getCartList(final Long userId) {
        return productServiceDao.getCartList(userId);
    }

    /**
     * Represents the particular id details of entered cart id
     * @param id Represents the id of the cart
     * @return Represents {@link Cart}
     */
    public Cart getCart(final Long id) {
        return productServiceDao.getCart(id);
    }

    /**
     * Represents the product id's of the user created product
     * @param userId Represents the id of the {@link User}
     * @return List of product id's
     */
    public List<Long> getCartProductIds(final Long userId) {
        return productServiceDao.getCartProductIds(userId);
    }

    /**
     * Represents the removal of product for the particular cart id
     * @param cartId Represents the id of the cart
     * @return True if the Product is removed successfully
     */
    public boolean removeCart(final Long cartId) {
        return productServiceDao.removeCart(cartId);
    }

    /**
     * Represents updating the quantity of product in cart
     * @param quantity Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    public boolean updateQuantityInCart(final Long quantity, final Long productId) {
        return productServiceDao.updateQuantityInCart(quantity, productId);
    }

    /**
     * Represents updating the quantity of product in {@link Product}
     *
     * @param quantity  Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    public boolean updateQuantityInProduct(final Long quantity, final Long productId) {
        return productServiceDao.updateQuantityInProduct(quantity, productId);
    }
}
