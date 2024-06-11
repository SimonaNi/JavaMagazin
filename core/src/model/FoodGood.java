package model;

import util.DateUtil;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FoodGood extends Good {
    private static final double MARKUP_PERCENTAGE = 0.20;
    private static final int DISCOUNT_DAYS = 5;
    private static final double DISCOUNT_PERCENTAGE = 0.10;

    public FoodGood(String id, String name, BigDecimal unitDeliveryPrice, LocalDate expirationDate, int quantity) {
        super(id, name, unitDeliveryPrice, Category.FOOD, expirationDate, quantity);
    }

    @Override
    public BigDecimal getSellingPrice(int daysUntilExpiration) {
        BigDecimal sellingPrice = getUnitDeliveryPrice().multiply(BigDecimal.valueOf(1 + MARKUP_PERCENTAGE));
        if (daysUntilExpiration < DISCOUNT_DAYS) {
            sellingPrice =  sellingPrice.multiply(BigDecimal.valueOf(1 - DISCOUNT_PERCENTAGE);
        }
        return sellingPrice;
    }
}
