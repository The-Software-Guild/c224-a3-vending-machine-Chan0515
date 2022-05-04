package vendingmachine.dao;

import vendingmachine.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class VendingMachineDaoImpl implements VendingMachineDao {

    enum CoinType {
        PENNY,NICKEL,DIME,QUARTER;
    }

    private CoinType coinType;

    private BigDecimal total = new BigDecimal("0.00");

    private final Map<String, Item> itemList = new HashMap<>();

    public final String INVENTORY_FILE;
    public static final String DELIMITER = "::";

    public VendingMachineDaoImpl() {
        INVENTORY_FILE = "inventory.txt";
    }


    @Override
    public BigDecimal addCoin(String coinType, int amount) {
        this.coinType = this.coinType.valueOf(coinType);
        int value = 0;
        switch(this.coinType) {
            case PENNY:
                total = total.add(new BigDecimal(1*amount));
                break;
            case NICKEL:
                total = total.add(new BigDecimal(5 * amount));
                break;
            case DIME:
                total = total.add(new BigDecimal(10 * amount));
                break;
            case QUARTER:
                total = total.add(new BigDecimal(25 * amount));
                break;
        }
        return total;
    }

    @Override
    public BigDecimal getTotal() {
        BigDecimal convertToMoney = new BigDecimal(100);
        BigDecimal convertedTotal = total;
        convertedTotal = convertedTotal.divide(convertToMoney);
        convertedTotal = convertedTotal.setScale(2, RoundingMode.DOWN);
        return convertedTotal;
    }

    //sl
    @Override
    public List<Item> getItemList() throws VendingMachinePersistenceException {
        loadInventory();
        return new ArrayList(itemList.values());
    }

    //sl
    @Override
    public Item vendItem(Item item) throws VendingMachinePersistenceException {
        loadInventory();
        if (itemList.isEmpty()) {
            throw new VendingMachinePersistenceException("VENDING MACHINE EMPTY");
        }

        Item removedItem = itemList.remove(item.getName()); //remove item
        BigDecimal temp = new BigDecimal(removedItem.getPrice());

        if (temp.compareTo(getTotal()) > 0) {
            itemList.put(item.getName(), removedItem); //put back

        }
        else {
            int amount = Integer.parseInt(removedItem.getAmount()) -1;
            removedItem.setAmount(Integer.toString(amount));
            itemList.put(item.getName(), removedItem);
            writeInventory();
        }
        return removedItem;
    }
    @Override
    public List<BigDecimal> getChange(Item item)  {
        BigDecimal itemPrice = new BigDecimal(item.getPrice());
        BigDecimal convert = new BigDecimal(100);
        itemPrice = itemPrice.multiply(convert);
        total = total.subtract(itemPrice);

        BigDecimal q = new BigDecimal(".25");
        BigDecimal d = new BigDecimal(".1");
        BigDecimal n = new BigDecimal(".05");
        BigDecimal p = new BigDecimal(".01");
        BigDecimal temp = getTotal();

        List<BigDecimal> totalList = new ArrayList<>();

        BigDecimal qChange = temp.divide(q);
        qChange = qChange.setScale(0, RoundingMode.DOWN);
        totalList.add(qChange);
        temp = temp.remainder(q);

        BigDecimal dChange = temp.divide(d);
        dChange = dChange.setScale(0, RoundingMode.DOWN);
        totalList.add(dChange);
        temp = temp.remainder(d);

        BigDecimal nChange = temp.divide(n);
        nChange = nChange.setScale(0, RoundingMode.DOWN);
        totalList.add(nChange);
        temp = temp.remainder(n);

        BigDecimal pChange = temp.divide(p);
        totalList.add(pChange);

        totalList.add(getTotal());
        return totalList;
    }

    private Item unmarshallItem(String itemAsText){
        String[] itemTokens = itemAsText.split(DELIMITER);
        String itemTitle = itemTokens[0];
        Item itemFromFile = new Item(itemTitle);
        itemFromFile.setPrice(itemTokens[1]);
        itemFromFile.setAmount(itemTokens[2]);
        return itemFromFile;
    }

    private String marshallItem(Item aItem){
        String itemAsText = aItem.getName() + DELIMITER;
        itemAsText += aItem.getPrice()+ DELIMITER;
        itemAsText += aItem.getAmount();
        return itemAsText;
    }

    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("-_- Could not load inventory data into memory.", e);
        }

        String currentLine;

        Item currentItem;

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();

            currentItem = unmarshallItem(currentLine);

            itemList.put(currentItem.getName(), currentItem);
        }

        scanner.close();
    }

    private void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not save inventory data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getItemList();
        for (Item currentItem : itemList) {

            itemAsText = marshallItem(currentItem);

            out.println(itemAsText);

            out.flush();
        }

        out.close();
    }
}
