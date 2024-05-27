package model;

import exception.InsufficientQuantityException;
import util.DateUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Good> goods;
    private List<Cashier> cashiers;
    private List<Receipt> receipts;
    private double totalTurnover;
    private double totalCosts;

    public Store() {
        this.goods = new ArrayList<>();
        this.cashiers = new ArrayList<>();
        this.receipts = new ArrayList<>();
        this.totalTurnover = 0;
        this.totalCosts = 0;
    }

    public void addGood(Good good) {
        goods.add(good);
        totalCosts += good.getUnitDeliveryPrice() * good.getQuantity();
    }

    public void addCashier(Cashier cashier) {
        cashiers.add(cashier);
        totalCosts += cashier.getMonthlySalary();
    }

    public Receipt sellGoods(Cashier cashier, List<Good> goodsToSell) throws InsufficientQuantityException {
        double totalPrice = 0;
        for (Good good : goodsToSell) {
            Good storeGood = findGoodById(good.getId());
            if (storeGood == null || storeGood.isExpired(LocalDate.now())) {
                throw new InsufficientQuantityException(good.getName(), good.getQuantity());
            }
            storeGood.reduceQuantity(good.getQuantity());
            totalPrice += good.getSellingPrice((int) (good.getExpirationDate().toEpochDay() - LocalDate.now().toEpochDay())) * good.getQuantity();
        }
        Receipt receipt = new Receipt(cashier, goodsToSell);
        receipts.add(receipt);
        totalTurnover += totalPrice;
        receipt.saveReceiptToFile();
        return receipt;
    }

    private Good findGoodById(String id) {
        for (Good good : goods) {
            if (good.getId().equals(id)) {
                return good;
            }
        }
        return null;
    }

    public int getTotalReceipts() {
        return receipts.size();
    }

    public double calculateProfit() {
        return totalTurnover - totalCosts;
    }
}
