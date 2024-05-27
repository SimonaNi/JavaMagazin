package model;

import java.time.LocalDateTime;
import java.util.List;
import java.io.*;

public class Receipt {
    private static int receiptCounter = 0;
    private int receiptNumber;
    private Cashier cashier;
    private LocalDateTime dateTime;
    private List<Good> goods;
    private double totalPrice;

    public Receipt(Cashier cashier, List<Good> goods) {
        this.receiptNumber = ++receiptCounter;
        this.cashier = cashier;
        this.dateTime = LocalDateTime.now();
        this.goods = goods;
        this.totalPrice = calculateTotalPrice(goods);
    }

    private double calculateTotalPrice(List<Good> goods) {
        double total = 0;
        for (Good good : goods) {
            total += good.getSellingPrice((int) (good.getExpirationDate().toEpochDay() - LocalDate.now().toEpochDay())) * good.getQuantity();
        }
        return total;
    }

    public int getReceiptNumber(){
        return receiptNumber;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void saveReceiptToFile() {
        String fileName = "receipt_" + receiptNumber + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Receipt Number: ").append(receiptNumber).append("\n");
        sb.append("Cashier: ").append(cashier.getName( )).append("\n");
        sb.append("Date and Time: ").append(dateTime.toString()).append("\n");
        sb.append("Goods:\n");
        for (Good good : goods) {
            sb.append(good.getName()).append(" - ").append(good.getQuantity()).append(" units - $").append(good.getSellingPrice((int) (good.getExpirationDate().toEpochDay() - LocalDate.now().toEpochDay()))).append(" each \n");
        }
        sb.append("total price: $").append(totalPrice).append("\n");
        return sb.toString();
    }
}
