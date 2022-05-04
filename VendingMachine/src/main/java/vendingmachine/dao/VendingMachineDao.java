package vendingmachine.dao;

import vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineDao {
    BigDecimal addCoin(String coinType, int amount) throws VendingMachinePersistenceException;
    BigDecimal getTotal() throws VendingMachinePersistenceException;
    List<Item> getItemList() throws VendingMachinePersistenceException;
    Item vendItem(Item item) throws VendingMachinePersistenceException;
    List<BigDecimal> getChange(Item item)throws VendingMachinePersistenceException;
}