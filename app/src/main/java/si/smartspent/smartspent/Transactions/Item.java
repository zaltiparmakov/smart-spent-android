package si.smartspent.smartspent.Transactions;

public class Item {
    private int id;
    private String name;
    private int pieces;
    private double price;

    public Item(int id, String name, int pieces, double price) {
        this.id = id;
        this.name = name;
        this.pieces = pieces;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
