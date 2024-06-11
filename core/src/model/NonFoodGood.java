package model;

import util.DateUtil;

public class NonFoodGood extends Good {
    private static final double MARKUP_PERCENTAGE = 0.30;

    public NonFoodGood(String id, String name, double unitDeliveryPrice, LocalDate expirationDate, int quantity) {
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
