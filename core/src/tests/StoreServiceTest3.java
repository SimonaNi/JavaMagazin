package test;

import model.*;
import service.StoreService;
import exception.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoreServiceTest3 {
    private StoreService storeService;
    private Store store;

    @BeforeEach
    public void setUp() {
        store = new Store();
        storeService = new StoreService(store);
    }

    @Test
    public void testSellGoodsWithMultipleItems() throws InsufficientQuantityException, ExpiredGoodException {
        Good apple = new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 100);
        Good shampoo = new NonFoodGood("4", "Shampoo", 3.0, LocalDate.now().plusDays(365), 50);
        storeService.addGood(apple);
        storeService.addGood(shampoo);
        Cashier cashier = new Cashier("4", "Bob Smith", 3100);
        storeService.addCashier(cashier);

        List<Good> goodsToSell = new ArrayList<>();
        goodsToSell.add(new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 10));
        goodsToSell.add(new NonFoodGood("4", "Shampoo", 3.0, LocalDate.now().plusDays(365), 2));

        Receipt receipt = storeService.sellGoods(cashier, goodsToSell);
        assertNotNull(receipt);
        assertEquals(10, store.getGoods().get(0).getQuantity());
        assertEquals(48, store.getGoods().get(1).getQuantity());
    }

    @Test
    public void testProfitCalculationAfterSales() throws InsufficientQuantityException, ExpiredGoodException {
        Good apple = new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 100);
        Good shampoo = new NonFoodGood("4", "Shampoo", 3.0, LocalDate.now().plusDays(365), 50);
        storeService.addGood(apple);
        storeService.addGood(shampoo);
        Cashier cashier = new Cashier("4", "Bob Smith", 3100);
        storeService.addCashier(cashier);

        List<Good> goodsToSell = new ArrayList<>();
        goodsToSell.add(new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 10));
        goodsToSell.add(new NonFoodGood("4", "Shampoo", 3.0, LocalDate.now().plusDays(365), 2));
        storeService.sellGoods(cashier, goodsToSell);

        double profit = storeService.calculateProfit();
        assertTrue(profit > 0);
    }
}
