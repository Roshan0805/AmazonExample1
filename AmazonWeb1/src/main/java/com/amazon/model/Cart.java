package com.amazon.model;

/**
 * <p> Represents the cart </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class Cart {

    private Long id;
    private Long productId;
    private String productName;
    private Long quantity;
    private Double price;
    private Long userId;

    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(final Long count) {
        this.quantity = count;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public String toString() {
        return String.format("Cart id : %d\nproduct name : %s product quantity : %d\ntotal amount : %f\n",
                id, productName, quantity, price * quantity);
    }
}
