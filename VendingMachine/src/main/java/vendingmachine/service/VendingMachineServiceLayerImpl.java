
package vendingmachine.service;

import vendingmachine.dao.VendingMachineAuditDao;
import vendingmachine.dao.VendingMachineDao;
import vendingmachine.dao.VendingMachinePersistenceException;
import vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;


public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineAuditDao auditDao;
    private VendingMachineDao dao;
    
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    private void hasEnoughMoney(Item item) throws InsufficientFundsException, VendingMachinePersistenceException, VendingMachinePersistenceException {
        BigDecimal temp = new BigDecimal(item.getPrice());
        if (dao.getTotal().compareTo(temp) < 0) {
            throw new InsufficientFundsException("INSUFFICIENT FUNDS");
        }
    }

    @Override
    public List<Item> getItemList() throws NoItemInventoryException, VendingMachinePersistenceException {
        if (dao.getItemList().isEmpty()) {
            throw new NoItemInventoryException("VENDING MACHINE EMPTY");
        }
        return dao.getItemList();
    }

    @Override
    public List<BigDecimal> getChange(Item item) throws InsufficientFundsException, VendingMachinePersistenceException {
        hasEnoughMoney(item);
        return dao.getChange(item);
    }

    @Override
    public Item vendItem(Item item) throws NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException {
        auditDao.writeAuditEntry("Item: " + item.getName() + " VENDED.");
        if (dao.getItemList().isEmpty()) {
            throw new NoItemInventoryException("VENDING MACHINE EMPTY");
        }
        
        BigDecimal temp = new BigDecimal(item.getPrice());

        hasEnoughMoney(item);
        return dao.vendItem(item);
    }
    @Override
    public BigDecimal getTotal() throws VendingMachinePersistenceException {
        return dao.getTotal();
    }

    @Override
    public BigDecimal addCoin(String coinType, int amount) throws VendingMachinePersistenceException {
        return dao.addCoin(coinType, amount);
    }

}
