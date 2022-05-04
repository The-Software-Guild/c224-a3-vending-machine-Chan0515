
package vendingmachine.service;

import vendingmachine.dao.VendingMachinePersistenceException;
import vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineServiceLayer {
    
    List<Item> getItemList() throws 
            NoItemInventoryException,
            VendingMachinePersistenceException;
    List<BigDecimal> getChange(Item item) throws 
            InsufficientFundsException,
            VendingMachinePersistenceException;
    Item vendItem(Item item) throws
            NoItemInventoryException,
            InsufficientFundsException,
            VendingMachinePersistenceException;
    BigDecimal getTotal() throws VendingMachinePersistenceException;

    BigDecimal addCoin(String penny, int newPenny) throws VendingMachinePersistenceException;
}
