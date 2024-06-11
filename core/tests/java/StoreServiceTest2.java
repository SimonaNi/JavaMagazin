package test;

import model.*;
import service.StoreService;
import exception.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoreServiceTest2 {
    private StoreService storeService;
    private Store store;

    @BeforeEach
    public void setUp() {
        store = new Store();
        storeService = new StoreService(store);
    }

    @Test
    public void testSellGoodsWithDiscount() throws InsufficientQuantityException, ExpiredGoodException {
        Good bread = new FoodGood("2", "Bread", 1.0, LocalDate.now().plusDays(2), 50);
        storeService.addGood(bread);
        Cashier cashier = new Cashier("2", "Jane Doe", 3200);
        storeService.addCashier(cashier);

        List<Good> goodsToSell = new ArrayList<>();
        goodsToSell.add(new FoodGood("2", "Bread", 1.0, LocalDate.now().plusDays(2), 5));

        Receipt receipt = storeService.sellGoods(cashier, goodsToSell);
        assertNotNull(receipt);
        assertEquals(5, store.getGoods().get(0).getQuantity());
        assertTrue(receipt.getTotalPrice() < 5 * 1.2); // Check if the price is discounted
    }

    @Test
    public void testTotalReceiptsAfterMultipleSales() throws InsufficientQuantityException, ExpiredGoodException {
        Good milk = new FoodGood("3", "Milk", 0.8, LocalDate.now().plusDays(7), 30);
        storeService.addGood(milk);
        Cashier cashier = new Cashier("3", "Alice Johnson", 2800);
        storeService.addCashier(cashier);

        List<Good> firstSaleGoods = new ArrayList<>();
        firstSaleGoods.add(new FoodGood("3", "Milk", 0.8, LocalDate.now().plusDays(7), 10));
        storeService.sellGoods(cashier, firstSaleGoods);

        List<Good> secondSaleGoods = new ArrayList<>();
        secondSaleGoods.add(new FoodGood("3", "Milk", 0.8, LocalDate.now().plusDays(7), 5));
        storeService.sellGoods(cashier, secondSaleGoods);

        assertEquals(2, store.getTotalReceipts());
    }
}
