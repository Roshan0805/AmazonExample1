package com.amazon.model;

/**
 * <p> Represents the Order </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class Order {

    private Long id;
    private Long productId;
    private String productName;
    private Long quantity;
    private Double price;
    private Long userId;
    private Payment paymentType;

    public Order() {}

    public Order(final Cart cart, final Payment paymentType) {
        this.productId = cart.getProductId();
        this.productName = cart.getProductName();
        this.quantity = cart.getQuantity();
        this.price = cart.getPrice();
        this.userId = cart.getUserId();
        this.paymentType = paymentType;
    }

    public enum Payment {
        CASH_ON_DELIVERY(1), CREDIT_OR_DEBIT_CART(2), NET_BANKING(3), OTHER_UPI_PAYMENTS(4);

        private final int index;

        Payment(final int index) {
            this.index = index;
        }

        public static Payment getPayment(final int index) {

            switch (index) {
                case 1:
                    return CASH_ON_DELIVERY;
                case 2:
                    return CREDIT_OR_DEBIT_CART;
                case 3:
                    return NET_BANKING;
                case 4:
                    return OTHER_UPI_PAYMENTS;
                default:
                    return null;
            }
        }
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getUserId() {
        return userId;
    }

    public Payment getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Payment paymentType) {
        this.paymentType = paymentType;
    }

    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setQuantity(final Long count) {
        this.quantity = count;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public String toString() {
        return String.format("Order id :%d\nproduct name : %s\nproduct quantity : %d\ntotal amount : %f",
               id, productName, quantity, price * quantity);
    }
}
