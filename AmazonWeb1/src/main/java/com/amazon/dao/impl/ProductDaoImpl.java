package com.amazon.dao.impl;

import com.amazon.dao.ProductDao;
import com.amazon.exception.DatabaseException;
import com.amazon.model.Cart;
import com.amazon.model.Order;
import com.amazon.model.Product;
import com.amazon.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Implements the {@link ProductDao} and provide {@link Product} service
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class ProductDaoImpl implements ProductDao {

    private static final ProductDao PRODUCT_SERVICE_DAO = new ProductDaoImpl();
    private final DatabaseConnector database;

    private ProductDaoImpl() {
        this.database = DatabaseConnector.getInstance();
    }

    /**
     * <p>
     * Method to provide access to the single instance for accessing
     * </p>
     *
     * @return Represents {@link ProductDao}
     */
    public static ProductDao getInstance() {
        return PRODUCT_SERVICE_DAO;
    }

    /**
     /**
     * <p>
     * This method is used to add a new product to the database
     * </p>
     *
     * @param product Represents {@link Product}
     * @return True if the {@link Product} is added successfully in the product list otherwise return false
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean create(final Product product) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "INSERT INTO PRODUCT (NAME, DESCRIPTION, AVAILABLE, PRICE, CATEGORY, UPDATED_TIME, USER_ID) values (?,?,?,?,?::product_category,?,?)";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setLong(3,product.getAvailable());
            statement.setDouble(4, product.getPrice());
            statement.setString(5, product.getCategory().toString());
            statement.setTimestamp(6, product.getUpdatedTime());
            statement.setLong(7, product.getUserId());
            statement.executeUpdate();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Provide the collection view of the products value
     * </p>
     *
     * @return Collection view of {@link Product}
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public Collection<Product> getProducts() {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final Collection<Product> productList = new ArrayList<>();
            final String query = "SELECT * FROM PRODUCT";
            final PreparedStatement statement = connection.prepareStatement(query);
            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final Product product = new Product();

                product.setId(result.getLong("ID"));
                product.setName(result.getString("NAME"));
                product.setDescription(result.getString("DESCRIPTION"));
                product.setAvailable(result.getLong("AVAILABLE"));
                product.setPrice(result.getDouble("PRICE"));
                product.setCategory(Product.Category.valueOf(result.getString("CATEGORY")));
                product.setUpdatedTime(result.getTimestamp("UPDATED_TIME"));
                product.setUserId(result.getLong("USER_ID"));
                productList.add(product);
            }

            return productList;

        } catch (SQLException exception) {;
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * Represents the product details that the user create
     *
     * @param userId Represents admin id
     * @return Represents {@link Product} list created by the user
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public Map<Long, Product> getUserProduct(final Long userId) {
        final Map<Long, Product> productList = new HashMap<>();

        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "SELECT * FROM PRODUCT WHERE USER_ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, userId);
            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final Product product = new Product();

                product.setId(result.getLong("ID"));
                product.setName(result.getString("NAME"));
                product.setDescription(result.getString("DESCRIPTION"));
                product.setAvailable(result.getLong("AVAILABLE"));
                product.setPrice(result.getDouble("PRICE"));
                product.setCategory(Product.Category.valueOf(result.getString("CATEGORY")));
                product.setUpdatedTime(result.getTimestamp("UPDATED_TIME"));
                product.setUserId(result.getLong("USER_ID"));
                productList.put(product.getId(), product);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }

        return productList;
    }

    /**
     * <p>
     * Gets the product from product list using productId
     * </p>
     *
     * @param productId product id of the product object
     * @return Represent {@link Product} in product list
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public Product get(final Long productId) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "SELECT * FROM PRODUCT WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, productId);
            final ResultSet result = statement.executeQuery();

            if (result.next()) {
                final Product product = new Product();

                product.setId(result.getLong("ID"));
                product.setName(result.getString("NAME"));
                product.setDescription(result.getString("DESCRIPTION"));
                product.setAvailable(result.getLong("AVAILABLE"));
                product.setPrice(result.getDouble("PRICE"));
                product.setCategory(Product.Category.valueOf(result.getString("CATEGORY")));
                product.setUpdatedTime(result.getTimestamp("UPDATED_TIME"));
                product.setUserId(result.getLong("USER_ID"));

                return product;
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
        return null;
    }

    /**
     * <p>
     * Updates product object in product list
     * </p>
     *
     * @param id      Product id of the product
     * @param product Represent {@link Product}
     * @return True if the {@link Product} is updated successfully in the product list otherwise return false
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean update(final Long id, final Product product) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "UPDATE PRODUCT SET NAME = ?, DESCRIPTION = ?, AVAILABLE = ?, PRICE = ?, CATEGORY = ?::PRODUCT_CATEGORY, UPDATED_TIME = ?, USER_ID = ? WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setLong(3, product.getAvailable());
            statement.setDouble(4, product.getPrice());
            statement.setString(5, product.getCategory().name());
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(7, product.getUserId());
            statement.setLong(8, id);
            statement.execute();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Deletes the product object from the product list
     * </p>
     *
     * @param id id of the product object
     * @return True if the {@link Product} deleted successfully in the product list otherwise return false
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean delete(final Long id) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "DELETE FROM PRODUCT WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, id);
            statement.executeUpdate();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     *<p>
     *     Describes the adding a product to the cart
     *</p>
     * @param cart Represents {@link Cart}
     * @return True if product added to cart successfully
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean addToCart(final Cart cart) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "INSERT INTO CART (PRODUCT_ID, QUANTITY, PRICE, USER_ID, NAME) VALUES (?,?,?,?,?)";
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cart.getProductId());
            statement.setLong(2, cart.getQuantity());
            statement.setDouble(3, cart.getPrice());
            statement.setLong(4, cart.getUserId());
            statement.setString(5, cart.getProductName());
            statement.execute();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Describes the product details from the cart for a particular user
     * </p>
     * @param id Represents the id of {@link User}
     * @return Represents the list of products from the cart
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public List<Cart> getCartList(final Long id) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final List<Cart> cartList = new LinkedList<>();
            final String query = "SELECT * FROM CART WHERE USER_ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, id);
            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final Cart cart = new Cart();

                cart.setId(result.getLong("ID"));
                cart.setProductId(result.getLong("PRODUCT_ID"));
                cart.setProductName(result.getString("NAME"));
                cart.setQuantity(result.getLong("QUANTITY"));
                cart.setPrice(result.getDouble("PRICE"));
                cart.setUserId(result.getLong("USER_ID"));
                cartList.add(cart);
            }

            return cartList;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Defines the product details from the cart for a particular user
     * </p>
     * @param id Represents the id of {@link User}
     * @return Represents the list of products from the cart
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public Cart getCart(final Long id) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "SELECT * FROM CART WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, id);
            final ResultSet result = statement.executeQuery();

            if (result.next()) {
                final Cart cart = new Cart();

                cart.setId(result.getLong("ID"));
                cart.setProductId(result.getLong("PRODUCT_ID"));
                cart.setProductName(result.getString("PRODUCT_NAME"));
                cart.setQuantity(result.getLong("QUANTITY"));
                cart.setPrice(result.getDouble("TOTAL_PRICE"));
                cart.setUserId(result.getLong("USER_ID"));

                return cart;
            }

            return null;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Defines removing a product from the cart
     * </p>
     * @param cartId Represents the cart id for remove
     * @return true if the product is removed successfully from the cart
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean removeFromCart(final Long cartId) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "DELETE FROM CART WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, cartId);
            statement.execute();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Describes getting all the product id from the cart list
     * </p>
     * @param userId Represents the id of the {@link User}
     * @return List of product id from the cart
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public List<Long> getCartProductIds(final Long userId) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final List<Long> productIds = new LinkedList<>();
            final String query = "SELECT PRODUCT_ID FROM CART WHERE USER_ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, userId);
            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                productIds.add(result.getLong("PRODUCT_ID"));
            }

            return productIds;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Describes updating the quantity of product in {@link Cart}
     * </p>
     *
     * @param quantity Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the quantity updated successfully
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean updateQuantityInCart(final Long quantity, final Long productId) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "UPDATE CART SET QUANTITY = QUANTITY + ? WHERE PRODUCT_ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, quantity);
            statement.setLong(2, productId);
            statement.execute();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Describes updating the quantity of product in {@link Product}
     * </p>
     *
     * @param quantity Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the quantity updated successfully
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean updateQuantityInProduct(final Long quantity, final Long productId) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "UPDATE PRODUCT SET AVAILABLE = AVAILABLE + ? WHERE PRODUCT_ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, quantity);
            statement.setLong(2, productId);
            statement.execute();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     processes the order of {@link Product}
     * </p>
     *
     * @param order Represents {@link Order}
     * @return True if the order is added to the order list
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean order(final Order order) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String orderQuery = "INSERT INTO ORDERS (PRODUCT_ID, QUANTITY, PRICE, PRODUCT_NAME, USER_ID, PAYMENT_TYPE) VALUES (?,?,?,?,?,?::payment_types)";
            final PreparedStatement statement = connection.prepareStatement(orderQuery);

            statement.setLong(1, order.getProductId());
            statement.setLong(2, order.getQuantity());
            statement.setDouble(3, order.getPrice());
            statement.setString(4, order.getProductName());
            statement.setLong(5, order.getUserId());
            statement.setString(6, String.valueOf(order.getPaymentType()));
            statement.execute();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     *      Describes the collection of order for the user from the database
     * </p>
     *
     * @param userId Represents id of {@link User}
     * @return Represents collection of {@link Order}
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public List<Order> getOrderList(final Long userId) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final List<Order> orderList = new ArrayList<>();
            final String query = "SELECT * FROM ORDERS WHERE USER_ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, userId);
            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final Order order = new Order();

                order.setId(result.getLong("ID"));
                order.setProductId(result.getLong("PRODUCT_ID"));
                order.setQuantity(result.getLong("QUANTITY"));
                order.setProductName(result.getString("PRODUCT_NAME"));
                order.setPrice(result.getDouble("PRICE"));
                order.setUserId(result.getLong("USER_ID"));
                order.setPaymentType(Order.Payment.valueOf(result.getString("PAYMENT_TYPE")));
                orderList.add(order);
            }

            return orderList;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * Describes the order details of the particular order id
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public Order getOrder(final Long orderId) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "SELECT * FROM ORDERS WHERE USER_ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, orderId);
            final ResultSet result = statement.executeQuery();
            final Order order = new Order();

            if (result.next()) {
                order.setId(result.getLong("ID"));
                order.setProductId(result.getLong("PRODUCT_ID"));
                order.setQuantity(result.getLong("PRODUCT_COUNT"));
                order.setPrice(result.getDouble("PRICE"));
                order.setUserId(result.getLong("USER_ID"));
                order.setPaymentType(Order.Payment.valueOf(result.getString("PAYMENT_TYPE")));
            }

            return order;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * Describes the cancelling the order of the particular order id
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     * @throws DatabaseException can be thrown when there is a failure to database-related operation.
     */
    public boolean cancelOrder(final Long orderId) {
        try (final Connection connection = database.getDataSource().getConnection()) {
            final String query = "DELETE FROM ORDERS WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, orderId);
            statement.execute();

            final Order order = getOrder(orderId);
            final String productQuery = "UPDATE PRODUCT SET AVAILABLE = AVAILABLE + ? WHERE ID = ? ";
            final PreparedStatement statement1 = connection.prepareStatement(productQuery);

            statement1.setLong(1, order.getQuantity());
            statement1.setLong(2, order.getProductId());
            statement1.execute();

            return true;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }
}
