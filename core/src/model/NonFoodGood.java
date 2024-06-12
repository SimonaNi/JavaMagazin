package model;

import util.DateUtil;
import java.math.BigDecimal;
import java.time.LocalDate;

public class NonFoodGood extends Good {
    private static final double MARKUP_PERCENTAGE = 0.30;

    public NonFoodGood(String id, String name, BigDecimal unitDeliveryPrice, LocalDate expirationDate, int quantity) {
        super(id, name, unitDeliveryPrice, Category.NON_FOOD, expirationDate, quantity);
    }

    @Override
    public BigDecimal getSellingPrice(int daysUntilExpiration) {
        return getUnitDeliveryPrice().multiply(BigDecimal.valueOf(1 + MARKUP_PERCENTAGE));
    }
}
