package vendingmachine.dto;

public class Item {

    private String name;
    private String amount;
    private String price;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getPrice() {
        return price;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}

