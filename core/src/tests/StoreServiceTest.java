// src/test/StoreServiceTest.java
package test;

import model.*;
import service.StoreService;
import exception.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoreServiceTest {
    private StoreService storeService;
    private Store store;

    @BeforeEach
    public void setUp() {
        store = new Store();
        storeService = new StoreService(store);
    }

    @Test
    public void testAddGood() {
        Good apple = new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 100);
        storeService.addGood(apple);
        assertEquals(1, store.getGoods().size());
    }

    @Test
    public void testAddCashier() {
        Cashier cashier = new Cashier("1", "John Doe", 3000);
        storeService.addCashier(cashier);
        assertEquals(1, store.getCashiers().size());
    }

    @Test
    public void testSellGoods() throws InsufficientQuantityException, ExpiredGoodException {
        Good apple = new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 100);
        storeService.addGood(apple);
        Cashier cashier = new Cashier("1", "John Doe", 3000);
        storeService.addCashier(cashier);

        List<Good> goodsToSell = new ArrayList<>();
        goodsToSell.add(new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 10));

        Receipt receipt = storeService.sellGoods(cashier, goodsToSell);
        assertNotNull(receipt);
        assertEquals(1, store.getTotalReceipts());
        assertEquals(90, store.getGoods().get(0).getQuantity());
    }

    @Test
    public void testSellExpiredGoods() {
        Good apple = new FoodGood("1", "Apple", 0.5, LocalDate.now().minusDays(1), 100);
        storeService.addGood(apple);
        Cashier cashier = new Cashier("1", "John Doe", 3000);
        storeService.addCashier(cashier);

        List<Good> goodsToSell = new ArrayList<>();
        goodsToSell.add(new FoodGood("1", "Apple", 0.5, LocalDate.now().minusDays(1), 10));

        assertThrows(ExpiredGoodException.class, () -> {
            storeService.sellGoods(cashier, goodsToSell);
        });
    }

    @Test
    public void testInsufficientQuantity() {
        Good apple = new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 100);
        storeService.addGood(apple);
        Cashier cashier = new Cashier("1", "John Doe", 3000);
        storeService.addCashier(cashier);

        List<Good> goodsToSell = new ArrayList<>();
        goodsToSell.add(new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 150));

        assertThrows(InsufficientQuantityException.class, () -> {
            storeService.sellGoods(cashier, goodsToSell);
        });
    }

    @Test
    public void testCalculateProfit() throws InsufficientQuantityException, ExpiredGoodException {
        Good apple = new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 100);
        storeService.addGood(apple);
        Cashier cashier = new Cashier("1", "John Doe", 3000);
        storeService.addCashier(cashier);

        List<Good> goodsToSell = new ArrayList<>();
        goodsToSell.add(new FoodGood("1", "Apple", 0.5, LocalDate.now().plusDays(10), 10));
        storeService.sellGoods(cashier, goodsToSell);

        double profit = storeService.calculateProfit();
        assertTrue(profit > 0);
    }
}
