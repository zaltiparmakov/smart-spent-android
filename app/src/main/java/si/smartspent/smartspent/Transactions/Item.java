package si.smartspent.smartspent.Transactions;

public class Item {
    private String name;
    private int pieces;
    private double price;
    private double amount;

    public Item(String name, int pieces, double price, double amount) {
        this.name = name;
        this.pieces = pieces;
        this.price = price;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
