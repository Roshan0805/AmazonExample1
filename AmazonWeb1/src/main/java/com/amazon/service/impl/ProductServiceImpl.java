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

    private final Map<Long, Product> productList;
    private final Map<Long, Order> orderList;
    private final Map<Long, Cart> cartList;
    private Long productId;
    private Long cartId;
    private Long orderId;
    private static final ProductServiceImpl AMAZON_PRODUCT_SERVICE = new ProductServiceImpl();

    private ProductServiceImpl() {
        productList = new LinkedHashMap<>();
        orderList = new HashMap<>();
        cartList = new HashMap<>();
        productId = 1L;
        cartId = 1L;
        orderId = 1L;
    }

    /**
     * <p>
     * Represents the object of {@link ProductServiceImpl} class can be created for only one time
     * </p>
     *
     * @return Represents {@link ProductServiceImpl}
     */
    public static ProductService getInstance() {
        return AMAZON_PRODUCT_SERVICE;
    }

    /**
     * {@inheritDoc}
     *
     * @param product Represents {@link Product}
     * @return True if the product is added successfully
     */
    public boolean add(final Product product) {
        if (null == product) {
            return false;
        }

        try {
            final Long id = generateId("product");
            product.setId(id);
            productList.put(id, product);

            return true;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return Collection view of product's
     */
    public Collection<Product> getAllProducts() {
        return productList.values();
    }

    /**
     * Represents the product details that the user create
     *
     * @param userId Represents admin id
     * @return Represents {@link Product} list created by the user
     */
    public Map<Long, Product> getUserProduct(final Long userId) {
        final Map<Long, Product> products = new HashMap<>();

        for (final Product product : productList.values()) {

            if (product.getUserId().equals(userId)) {
                products.put(product.getId(), product);
            }
        }
        return products;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link Product} from the product list
     */
    public Product get(final Long productId) {
        return productList.get(productId);
    }

    /**
     * {@inheritDoc}
     *
     * @param id      Represents the id of the product
     * @param product Represents {@link Product}
     * @return True if the product name is updated successfully
     */
    public boolean update(Long id, Product product) {
        if (null == product) {
            return false;
        }
        product.setUpdatedTime(Timestamp.from(Instant.now()));
        productList.put(id, product);

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param id Represents id for deleting product
     * @return True if the product is deleted successfully
     */
    public boolean delete(final Long id) {
        if (getIds().contains(id)) {
            productList.remove(id);

            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return Set of product's id
     */
    public Collection<Long> getIds() {
        final Collection<Long> productsId = new HashSet<>();

        for (final Product product : productList.values()) {
            final long productId = product.getId();

            productsId.add(productId);
        }

        return productsId;
    }

    /**
     * Represents the order of {@link Product}
     *
     * @param order Represents {@link Order}
     * @return True if the order is added to the order list
     */
    public boolean order(final Order order) {
        try {
            order.setId(generateId("order"));
            orderList.put(order.getId(), order);
            final Product product = get(order.getProductId());

            product.setAvailable(product.getAvailable() - order.getQuantity());
            productList.put(order.getProductId(), product);

            return true;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    /**
     * Represents the List of {@link Order}
     *
     * @return Represents collection of {@link Order}
     */
    public List<Order> getOrderList(final Long userId) {
        final List<Order> orderList = new ArrayList<>();

        for (Order order : this.orderList.values()) {

            if (order.getUserId() == userId) {
                orderList.add(order);
            }
        }
        return orderList;
    }

    /**
     * Represents the order details of the particular order id
     *
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     */
    public Order getOrder(Long orderId) {
        return orderList.get(orderId);
    }

    /**
     * Represents the removal of {@link Order}
     *
     * @return True if removed successfully
     */
    public boolean cancelOrder(final Long orderId) {
        try {
            orderList.remove(orderId);
            final Order order = getOrder(orderId);
            final Product product = get(order.getProductId());

            product.setAvailable(product.getAvailable() + order.getQuantity());
            productList.put(order.getProductId(), product);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Represents adding the product to cart list
     *
     * @param cart Represents {@link Cart}
     * @return True if the product is added to cart successfully
     */
    public boolean addToCart(final Cart cart) {
        try {
            cart.setId(generateId("cart"));
            cartList.put(cart.getId(), cart);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Represents the Product details from the cart for a particular user
     *
     * @param userId Represents the id of {@link User}
     * @return Collection of products from the cart
     */
    public List<Cart> getCartList(final Long userId) {
        final List<Cart> cartList = new LinkedList<>();

        for (Cart cart : this.cartList.values()) {

            if (cart.getUserId() == userId) {
                cartList.add(cart);
            }
        }
        return cartList;
    }

    /**
     * Represents the particular id details of entered cart id
     *
     * @param id Represents the id of the cart
     * @return Represents {@link Cart}
     */
    public Cart getCart(Long id) {
        for (Cart cart : cartList.values()) {

            if (cart.getId() == id) {
                return cart;
            }
        }
        return null;
    }

    /**
     * Represents the product id's of the user created product
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
     * Represents the removal of product for the particular cart id
     *
     * @param cartId Represents the id of the cart
     * @return True if the Product is removed successfully
     */
    public boolean removeCart(final Long cartId) {
        try {
            cartList.remove(cartId);
            return true;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    /**
     * Represents updating the quantity of product in cart
     *
     * @param quantity  Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    public boolean updateQuantityInCart(Long quantity, Long productId) {
        for (Cart cart : cartList.values()) {
            if (cart.getProductId() == productId) {
                final Double price = cart.getPrice()/ cartList.size();
                System.out.println(price);
                cart.setQuantity(cart.getQuantity() + quantity);
                cart.setPrice(cart.getPrice() + (quantity * price));

                return true;
            }
        }
        return false;
    }

    /**
     * Represents updating the quantity of product in {@link Product}
     *
     * @param quantity  Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the product quantity updated successfully
     */
    public boolean updateQuantityInProduct(Long quantity, Long productId) {
        for (Product product : productList.values()) {
            if (product.getId() == productId) {
                product.setAvailable(product.getAvailable() + quantity);

                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Represent the id creation for product that is added to the product list
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
                return cartId ++;
            default:
                return null;
        }
    }
}