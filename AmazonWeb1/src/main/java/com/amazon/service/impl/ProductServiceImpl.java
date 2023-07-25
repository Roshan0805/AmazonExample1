package com.amazon.service.impl;

import com.amazon.model.Cart;
import com.amazon.model.Order;
import com.amazon.model.Product;
import com.amazon.model.User;
import com.amazon.service.ProductService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Implements the {@link ProductService} to provide services for {@link  Product}
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class ProductServiceImpl implements ProductService {

    private final Map<Long, Product> products;
    private final Map<Long, Order> orders;
    private final Map<Long, Cart> carts;
    private Long productId;
    private Long cartId;
    private Long orderId;

    private static final ProductServiceImpl AMAZON_PRODUCT_SERVICE = new ProductServiceImpl();

    private ProductServiceImpl() {
        this.products = new LinkedHashMap<>();
        this.orders = new HashMap<>();
        this.carts = new HashMap<>();
        this.productId = 1L;
        this.cartId = 1L;
        this.orderId = 1L;
    }

    /**
     * <p>
     * Method to provide access to the single instance for accessing
     * </p>
     *
     * @return Represents {@link ProductServiceImpl}
     */
    public static ProductService getInstance() {
        return AMAZON_PRODUCT_SERVICE;
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
        if (null == product) {
            return false;
        }

        try {
            final Long id = generateId("product");

            product.setId(id);
            products.put(id, product);

            return true;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    /**
     * <p>
     * Retrieves a list of all products from the database
     * </p>
     *
     * @return Collection view of products from the viewProduct method
     */
    public Collection<Product> getProducts() {
        return products.values();
    }

    /**
     * Represents the user create product details from the database
     *
     * @param userId Represents admin id
     * @return Represents {@link Product} list created by the user
     */
    public Map<Long, Product> getUserProduct(final Long userId) {
        final Map<Long, Product> products = new HashMap<>();

        for (final Product product : this.products.values()) {

            if (product.getUserId().equals(userId)) {
                products.put(product.getId(), product);
            }
        }
        return products;
    }

    /**
     * <p>
     * Retrieve product object from the database
     * </p>
     *
     * @param productId id of the product
     * @return Represents {@link Product}
     */
    public Product get(final Long productId) {
        return products.get(productId);
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
    public boolean update(Long id, Product product) {
        if (null == product) {
            return false;
        }
        product.setUpdatedTime(Timestamp.from(Instant.now()));
        products.put(id, product);

        return true;
    }

    /**
     * <p>
     * Represents the product delete from the database
     * </p>
     *
     * @param id Represents id for deleting product
     * @return True if the product is deleted successfully
     */
    public boolean delete(final Long id) {
        if (getIds().contains(id)) {
            products.remove(id);

            return true;
        }

        return false;
    }

    /**
     * <p>
     * Retrieves product id from the database
     * </p>
     *
     * @return Set of product's id
     */
    public Collection<Long> getIds() {
        final Collection<Long> productsId = new HashSet<>();

        for (final Product product : products.values()) {
            final long productId = product.getId();

            productsId.add(productId);
        }

        return productsId;
    }

    /**
     * <p>
     * Represents the order of {@link Product} from database
     * </p>
     *
     * @param order Represents {@link Order}
     * @return True if the order is added to the order list
     */
    public boolean order(final Order order) {
        try {
            order.setId(generateId("order"));
            orders.put(order.getId(), order);
            final Product product = get(order.getProductId());

            product.setAvailable(product.getAvailable() - order.getQuantity());
            products.put(order.getProductId(), product);

            return true;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    /**
     * <p>
     * Retrieves the List of {@link Order} from the database
     * </p>
     *
     * @return Represents collection of {@link Order}
     */
    public List<Order> getOrderList(final Long userId) {
        final List<Order> orderList = new ArrayList<>();

        for (Order order : this.orders.values()) {

            if (order.getUserId() == userId) {
                orderList.add(order);
            }
        }
        return orderList;
    }

    /**
     * <p>
     * Retrieves the order details of the particular order id
     * </p>
     *
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     */
    public Order getOrder(Long orderId) {
        return orders.get(orderId);
    }

    /**
     * <p>
     * Describes the cancel of {@link Order}
     * </p>
     *
     * @return True if removed successfully
     */
    public boolean cancelOrder(final Long orderId) {
        try {
            orders.remove(orderId);
            final Order order = getOrder(orderId);
            final Product product = get(order.getProductId());

            product.setAvailable(product.getAvailable() + order.getQuantity());
            products.put(order.getProductId(), product);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * <p>
     * Retrieves adding the product to cart list
     * </p>
     *
     * @param cart Represents {@link Cart}
     * @return True if the product is added to cart successfully
     */
    public boolean addToCart(final Cart cart) {
        try {
            cart.setId(generateId("cart"));
            carts.put(cart.getId(), cart);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * <p>
     * Retrieves the Product details from the cart for a particular user
     * </p>
     *
     * @param userId Represents the id of {@link User}
     * @return Collection of products from the cart
     */
    public List<Cart> getCartList(final Long userId) {
        final List<Cart> cartList = new LinkedList<>();

        for (Cart cart : this.carts.values()) {

            if (cart.getUserId() == userId) {
                cartList.add(cart);
            }
        }
        return cartList;
    }

    /**
     * <p>
     * Retrieves the particular id details of entered cart id
     * </p>
     *
     * @param id Represents the id of the cart
     * @return Represents {@link Cart}
     */
    public Cart getCart(Long id) {
        for (Cart cart : carts.values()) {

            if (cart.getId() == id) {
                return cart;
            }
        }
        return null;
    }

    /**
     * <p>
     * Retrieves the product id's of the user created product
     * </p>
     *
     * @param userId Represents the id of the {@link User}
     * @return List of product id's
     */
    public List<Long> getCartProductIds(Long userId) {
        final List<Long> cartIds = new ArrayList<>();

        for (Cart cart : getCartList(userId)) {
            cartIds.add(cart.getProductId());
        }
        return cartIds;
    }

    /**
     * <p>
     * Describes the removal of product for the particular cart id
     * </p>
     *
     * @param cartId Represents the id of the cart
     * @return True if the Product is removed successfully
     */
    public boolean removeFromCart(final Long cartId) {
        try {
            carts.remove(cartId);
            return true;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    /**
     * <p>
     * Represents updating the quantity of product in cart
     * </p>
     *
     * @param quantity  Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    public boolean updateQuantityInCart(Long quantity, Long productId) {
        for (Cart cart : carts.values()) {

            if (cart.getProductId() == productId) {
                cart.setQuantity(cart.getQuantity() + quantity);
                cart.setPrice(cart.getPrice());

                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Represents updating the quantity of product in {@link Product}
     * </p>
     *
     * @param quantity  Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    public boolean updateQuantityInProduct(Long quantity, Long productId) {
        for (Product product : products.values()) {

            if (product.getId() == productId) {
                product.setAvailable(product.getAvailable() + quantity);

                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Describes the id creation for product that is added to the product list
     * </p>
     *
     * @return Represents the product id
     */
    public Long generateId(final String value) {
        switch (value) {
            case "product":
                return productId++;
            case "order":
                return orderId++;
            case "cart":
                return cartId++;
            default:
                return null;
        }
    }
}