package model;

import exception.ExpiredGoodException;
import util.DateUtil;
import java.math.BigDecimal;
import java.time.LocalDate;


public abstract class Good {
    private String id;
    private String name;
    private BigDecimal unitDeliveryPrice;
    private String category;
    private LocalDate expirationDate;
    private int quantity;

    public enum Category {
        FOOD, NON_FOOD;
    }

    public Good(String id, String name, BigDecimal unitDeliveryPrice, String category, LocalDate expirationDate, int quantity) {
        this.id = id;
        this.name = name;
        this.unitDeliveryPrice = unitDeliveryPrice;
        this.category = category;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitDeliveryPrice() {
        return unitDeliveryPrice;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity(int amount) throws InsufficientQuantityException {
        if (this.quantity < amount) {
            throw new InsufficientQuantityException(this.name, amount - this.quantity);
        }
        this.quantity -= amount;
    }

    public abstract BigDecimal getSellingPrice(int daysUntilExpiration);

    public boolean isExpired(LocalDate currentDate) {
        return expirationDate.isBefore(currentDate) || expirationDate.isEqual(currentDate);
    }
}
