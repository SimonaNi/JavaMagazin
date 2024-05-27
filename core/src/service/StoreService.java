package service;

import model.*;
import exception.*;
import util.DateUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StoreService {
    private Store store;
    public StoreService(Store store) {
        this.store = store;
    }
    public void addGood(Good good) {
        store.addGood(good);
    }
    public void addCashier(Cashier cashier) {
        store.addCashier(cashier);
    }

    public Receipt sellGoods(Cashier cashier, List<Good> goodsToSell) throws InsufficientQuantityException, ExpiredGoodException {
        List<Good> validGoods = new ArrayList<>();
        for (Good good : goodsToSell) {
            if (good.isExpired(LocalDate.now())) {
                throw new ExpiredGoodException(good.getName());
            }
            validGoods.add(good);
        }
        return store.sellGoods(cashier, validGoods);
    }

    public int getTotalReceipts() {
        return store.getTotalReceipts();
    }

    public double calculateProfit(){
        return store.calculateProfit( );
    }
}
