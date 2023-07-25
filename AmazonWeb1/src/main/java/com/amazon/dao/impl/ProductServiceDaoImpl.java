package com.amazon.dao.impl;

import com.amazon.dao.ProductServiceDao;
import com.amazon.exception.DBException;
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
 * Implements the {@link ProductServiceDao} and provide {@link Product} service
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class ProductServiceDaoImpl implements ProductServiceDao {

    private static final ProductServiceDao PRODUCT_SERVICE_DAO = new ProductServiceDaoImpl();
    private final DBConnection dbConnection;

    private ProductServiceDaoImpl() {
        dbConnection = DBConnection.getInstance();
    }

    /**
     * <p>
     * Represents the object of {@link ProductServiceDaoImpl} class can be created for only one time
     * </p>
     *
     * @return Represents {@link ProductServiceDao}
     */
    public static ProductServiceDao getInstance() {
        return PRODUCT_SERVICE_DAO;
    }

    /**
     * <p>
     * Add product to the product list
     * </p>
     *
     * @param product Product object
     * @return Boolean true is the {@link Product} added successfully in the product list otherwise return false
     *@throws DBException Represents any error occur while executing a query
     */
    public boolean add(final Product product) {
        try (final Connection connection = dbConnection.get()) {
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
            dbConnection.release(connection);

            return true;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Provide the collection view of the products value
     * </p>
     *
     * @return Collection view of {@link Product}
     * @throws DBException Represents any error occur while executing a query
     */
    public Collection<Product> getAllProducts() {
        try (final Connection connection = dbConnection.get()) {
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
            dbConnection.release(connection);

            return productList;

        } catch (SQLException | InterruptedException exception) {;
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * Represents the product details that the user create
     *
     * @param userId Represents admin id
     * @return Represents {@link Product} list created by the user
     * @throws DBException Represents any error occur while executing a query
     */
    public Map<Long, Product> getUserProduct(final Long userId) {
        final Map<Long, Product> productList = new HashMap<>();

        try (final Connection connection = dbConnection.get()) {
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
            dbConnection.release(connection);
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
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
     * @throws DBException Represents any error occur while executing a query
     */
    public Product get(final Long productId) {
        try (final Connection connection = dbConnection.get()) {
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
                dbConnection.release(connection);

                return product;
            }
        } catch (SQLException | InterruptedException exception) {
            System.out.println(exception.getMessage());
            throw new DBException(exception.getMessage());
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
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean update(final Long id, final Product product) {
        try (final Connection connection = dbConnection.get()) {
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
            dbConnection.release(connection);

            return true;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Deletes the product object from the product list
     * </p>
     *
     * @param id id of the product object
     * @return True if the {@link Product} deleted successfully in the product list otherwise return false
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean delete(final Long id) {
        try (final Connection connection = dbConnection.get()) {
            final String query = "DELETE FROM PRODUCT WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, id);
            statement.executeUpdate();
            dbConnection.release(connection);

            return true;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     *<p>
     *     Represents the adding a product to the cart
     *</p>
     * @param cart Represents {@link Cart}
     * @return True if product added to cart successfully
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean addToCart(final Cart cart) {
        try (final Connection connection = dbConnection.get()) {
            final String query = "INSERT INTO CART (PRODUCT_ID, QUANTITY, PRICE, USER_ID, NAME) VALUES (?,?,?,?,?)";
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cart.getProductId());
            statement.setLong(2, cart.getQuantity());
            statement.setDouble(3, cart.getPrice());
            statement.setLong(4, cart.getUserId());
            statement.setString(5, cart.getProductName());
            statement.execute();
            dbConnection.release(connection);

            return true;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Represents the product details from the cart for a particular user
     * </p>
     * @param id Represents the id of {@link User}
     * @return Represents the list of products from the cart
     * @throws DBException Represents any error occur while executing a query
     */
    public List<Cart> getCartList(final Long id) {
        try (final Connection connection = dbConnection.get()) {
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
            dbConnection.release(connection);

            return cartList;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Represents the product details from the cart for a particular user
     * </p>
     * @param id Represents the id of {@link User}
     * @return Represents the list of products from the cart
     * @throws DBException Represents any error occur while executing a query
     */
    public Cart getCart(final Long id) {
        try (final Connection connection = dbConnection.get()) {
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
                dbConnection.release(connection);

                return cart;
            }

            return null;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Represents removing a product from the cart
     * </p>
     * @param cartId Represents the cart id for remove
     * @return true if the product is removed successfully from the cart
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean removeCart(final Long cartId) {
        try (final Connection connection = dbConnection.get()) {
            final String query = "DELETE FROM CART WHERE ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, cartId);
            statement.execute();
            dbConnection.release(connection);

            return true;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Represents getting all the product id from the cart list
     * </p>
     * @param userId Represents the id of the {@link User}
     * @return List of product id from the cart
     * @throws DBException Represents any error occur while executing a query
     */
    public List<Long> getCartProductIds(final Long userId) {
        try (final Connection connection = dbConnection.get()) {
            final List<Long> productIds = new LinkedList<>();
            final String query = "SELECT PRODUCT_ID FROM CART WHERE USER_ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, userId);
            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                productIds.add(result.getLong("PRODUCT_ID"));
            }
            dbConnection.release(connection);

            return productIds;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Represents updating the quantity of product in {@link Cart}
     * </p>
     *
     * @param quantity Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the quantity updated successfully
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean updateQuantityInCart(final Long quantity, final Long productId) {
        try (final Connection connection = dbConnection.get()) {
            final String query = "UPDATE CART SET QUANTITY = QUANTITY + ? , PRICE = PRICE + WHERE PRODUCT_ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, quantity);
            statement.setLong(2, productId);
            statement.execute();
            dbConnection.release(connection);

            return true;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Represents updating the quantity of product in {@link Product}
     * </p>
     *
     * @param quantity Quantity need to add with available products
     * @param productId Represents the id of the product need to update the quantity
     * @return True if the quantity updated successfully
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean updateQuantityInProduct(final Long quantity, final Long productId) {
        try (final Connection connection = dbConnection.get()) {
            final String query = "UPDATE PRODUCT SET AVAILABLE = AVAILABLE + ? WHERE PRODUCT_ID = ?";
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setLong(1, quantity);
            statement.setLong(2, productId);
            statement.execute();
            dbConnection.release(connection);

            return true;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     *     Represents the order of {@link Product}
     * </p>
     *
     * @param order Represents {@link Order}
     * @return True if the order is added to the order list
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean order(final Order order) {
        try (final Connection connection = dbConnection.get()) {
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
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * <p>
     *      Retrieve the List of {@link User} order
     * </p>
     *
     * @param userId Represents id of {@link User}
     * @return Represents collection of {@link Order}
     * @throws DBException Represents any error occur while executing a query
     */
    public List<Order> getOrderList(final Long userId) {
        try (final Connection connection = dbConnection.get()) {
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
            dbConnection.release(connection);

            return orderList;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * Represents the order details of the particular order id
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     * @throws DBException Represents any error occur while executing a query
     */
    public Order getOrder(final Long orderId) {
        try (final Connection connection = dbConnection.get()) {
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
            dbConnection.release(connection);

            return order;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }

    /**
     * Represents the cancelling the order of the particular order id
     * @param orderId Represents the id of the {@link Product}
     * @return Represents {@link Order}
     * @throws DBException Represents any error occur while executing a query
     */
    public boolean cancelOrder(final Long orderId) {
        try (final Connection connection = dbConnection.get()) {
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
            dbConnection.release(connection);

            return true;
        } catch (SQLException | InterruptedException exception) {
            throw new DBException(exception.getMessage());
        }
    }
}
