package model;

import util.DateUtil;
import java.math.BigDecimal;
import java.time.LocalDate;

public class NonFoodGood extends Good {
    private static final BigDecimal MARKUP_PERCENTAGE = 0.30;

    public NonFoodGood(String id, String name, BigDecimal unitDeliveryPrice, LocalDate expirationDate, int quantity) {
        super(id, name, unitDeliveryPrice, "Non-Food", expirationDate, quantity);
    }

    @Override
    public BigDecimal getSellingPrice(int daysUntilExpiration) {
        BigDecimal markupPrice = getUnitDeliveryPrice().multiply(BigDecimal.valueOf(1 + MARKUP_PERCENTAGE));
        if (daysUntilExpiration < DISCOUNT_DAYS) {
            markupPrice = markupPrice.multiply(BigDecimal.valueOf(1 - DISCOUNT_PERCENTAGE));
        }
        return markupPrice;

}
