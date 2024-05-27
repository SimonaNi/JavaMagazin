import model.*;
import service.StoreService;
import exception.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();
        StoreService storeService = new StoreService(store);

        Good apple = new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 100);
        Good bread = new FoodGood("2", "Bread", 1.0, LocalDate.now().plusDays(5), 50);
        Good shampoo = new NonFoodGood("3", "Shampoo", 3.0, LocalDate.now().plusDays(365), 30);

        storeService.addGood(apple);
        storeService.addGood(bread);
        storeService.addGood(shampoo);

        Cashier cashier = new Cashier("1", "John Doe", 3000);
        storeService.addCashier(cashier);

        List<Good> goodsToSell = new ArrayList<>();
        goodsToSell.add(new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 10));
        goodsToSell.add(new FoodGood("2", "Bread", 1.0, LocalDate.now().plusDays(5), 5));
        goodsToSell.add(new NonFoodGood("3", "Shampoo", 3.0, LocalDate.now().plusDays(365), 2));

        try {
            Receipt receipt = storeService.sellGoods(cashier, goodsToSell);
            System.out.println("Receipt issued: ");
            System.out.println(receipt);

            System.out.println("Total receipts issued: " + storeService.getTotalReceipts());

            double profit = storeService.calculateProfit();
            System.out.println("Total profit: " + profit);
        } catch (InsufficientQuantityException | ExpiredGoodException e) {
            System.err.println(e.getMessage());
        }
    }
}
