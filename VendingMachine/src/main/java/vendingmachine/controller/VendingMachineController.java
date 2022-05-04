package vendingmachine.controller;

import vendingmachine.dao.VendingMachineDao;
import vendingmachine.dao.VendingMachinePersistenceException;
import vendingmachine.dto.Item;
import vendingmachine.service.InsufficientFundsException;
import vendingmachine.service.NoItemInventoryException;
import vendingmachine.service.VendingMachineServiceLayer;
import vendingmachine.ui.UserIO;
import vendingmachine.ui.UserIOConsoleImpl;
import vendingmachine.ui.VendingMachineView;
import vendingmachine.service.VendingMachineServiceLayerImpl;
import java.math.BigDecimal;
import java.util.List;

public class VendingMachineController {

    private UserIO io = new UserIOConsoleImpl();
    //di
    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {

            while(keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        addCoins();
                        getTotalMoney();
                        break;
                    case 2:
                        if(service.getTotal().compareTo(BigDecimal.ZERO) == 0) {
                            io.print("No money");
                            break;
                        }
                        listItems();
                        break;
                    case 3:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }

        } catch (VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException e) {
            view.displayErrorMessage(e.getMessage());
        }
        exitMessage();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void addCoins() throws VendingMachinePersistenceException {
        view.displayAddCoinBanner();

        int newPenny = view.getNewPennyInfo();
        service.addCoin("PENNY", newPenny);

        int newNickel = view.getNewNickelInfo();
        service.addCoin("NICKEL", newNickel);

        int newDime = view.getNewDimeInfo();
        service.addCoin("DIME", newDime);

        int newQuarter = view.getNewQuarterInfo();
        service.addCoin("QUARTER", newQuarter);

        view.displayAddSuccessBanner();
    }


    private void getTotalMoney()throws VendingMachinePersistenceException {
        view.displayTotalBanner();
        view.displayTotal(service.getTotal());
    }

    private void listItems() throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        view.displayPurchaseBanner();
        List<Item> itemList = service.getItemList();
        List<Item> availableItems = view.displayItems(itemList);
        Item chosenItem = view.chooseItem(availableItems);
        Item removedItem = service.vendItem(chosenItem);

        if (chosenItem.getAmount().equals(removedItem.getAmount())) {
            view.displayNotEnoughMoney();
            view.displayTotal(service.getTotal());
        }
        else {
            view.displayChange(service.getChange(chosenItem));
        }
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }


}
