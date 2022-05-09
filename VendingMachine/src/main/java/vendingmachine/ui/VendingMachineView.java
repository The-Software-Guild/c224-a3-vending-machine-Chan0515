package vendingmachine.ui;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendingMachineView {

    //di
    private UserIO io;
    @Autowired
    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Enter Coins");
        io.print("2. Purchase an item");
        io.print("3. Exit");

        return io.readInt("Please select from the above choices.", 1, 3);
    }


    public int getNewPennyInfo() {
        int pennies = io.readInt("How many pennies would you like to insert?");
        return pennies;
    }


    public int getNewNickelInfo() {
        int nickels = io.readInt("How many nickels would you like to insert?");
        return nickels;
    }


    public int getNewDimeInfo() {
        int dimes = io.readInt("How many dimes would you like to insert?");
        return dimes;
    }


    public int getNewQuarterInfo() {
        int quarters = io.readInt("How many quarters would you like to insert?");
        return quarters;
    }

    public void displayAddCoinBanner() {
        io.print("=== Adding Coins ===");
    }

    public void displayAddSuccessBanner() {
        io.readString("Coins successfully added. Please hit enter to continue");
    }

    public void displayTotal(BigDecimal total) {
        io.print("$" + total.toString());
        io.readString("Please hit enter to continue.");
    }

    public void displayChange(List<BigDecimal> total) {
        io.print("Quarters: " + total.get(0));
        io.print("Dimes: " + total.get(1));
        io.print("Nickels: " + total.get(2));
        io.print("Pennies: " + total.get(3));
        io.print("Your change is $" + total.get(4));
        io.readString("Please hit enter to continue.");
    }

    public void displayTotalBanner() {
        io.print("=== Display Money ===");
    }

    public void displayPurchaseBanner() {
        io.print("=== Buying Item ===");
    }

    public List<Item> displayItems(List<Item> itemList) {
        int counter = 1;

        List<Item> inStock = itemList.stream()
                .filter((item) -> Integer.parseInt(item.getAmount()) >= 1)
                .collect(Collectors.toList());
        for (Item currentItem : inStock) {
            String itemInfo = String.format("#" + counter + " %s : $%s",
                    currentItem.getName(),
                    currentItem.getPrice());
            io.print(itemInfo);
            counter++;
        }
        return inStock;


    }

    public Item chooseItem(List<Item> list) {
        if(!list.isEmpty()) {
            int choice = io.readInt("Please select from the above choices according to it's number on the list.", 1, list.size());
            Item item = list.get(choice-1);
            return item;
        }
        else {
            return null;
        }
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayNotEnoughMoney() {
        io.print("=== NOT ENOUGH MONEY ===");
        io.print("=== Display Money ===");
    }



}