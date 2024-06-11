package model;

import util.DateUtil;

public class FoodGood extends Good {
    private static final double MARKUP_PERCENTAGE = 0.20;
    private static final int DISCOUNT_DAYS = 5;
    private static final double DISCOUNT_PERCENTAGE = 0.10;

    public FoodGood(String id, String name, double unitDeliveryPrice, LocalDate expirationDate, int quantity) {
        super(id, name, unitDeliveryPrice, "Food", expirationDate, quantity);
    }

    @Override
    public double getSellingPrice(int daysUntilExpiration) {
        double sellingPrice = getUnitDeliveryPrice() * (1 + MARKUP_PERCENTAGE);
        if (daysUntilExpiration < DISCOUNT_DAYS) {
            sellingPrice *= (1 - DISCOUNT_PERCENTAGE);
        }
        return sellingPrice;
    }
}
