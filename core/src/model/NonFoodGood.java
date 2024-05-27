package model;

import util.DateUtil;

public class NonFoodGood extends Good {
    private static final double MARKUP_PERCENTAGE = 0.30; // 30%

    public NonFoodGood(String id, String name, double unitDeliveryPrice, LocalDate expirationDate, int quantity) {
        super(id, name, unitDeliveryPrice, "Non-Food", expirationDate, quantity);
    }

    @Override
    public double getSellingPrice(int daysUntilExpiration) {
        return getUnitDeliveryPrice() * (1 + MARKUP_PERCENTAGE);
    }
}
