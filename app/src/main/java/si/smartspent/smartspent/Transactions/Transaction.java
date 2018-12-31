package si.smartspent.smartspent.Transactions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import si.smartspent.smartspent.Location.Location;

public class Transaction {
    private int id;
    private String description;
    private String location;
    private double amount;
    private String date;
    private String category;

    private boolean income;
    public enum PAYMENT_METHOD {
        CASH, CARD
    }
    private PAYMENT_METHOD paymentMethod;
    private static int numOfObjects = 0;

    /**
     * Bought items related to this transaction, scanned by OCR
     */
    private ArrayList<Item> items;

    public Transaction(String description, String location, double amount, String date,
                       String category, boolean income, PAYMENT_METHOD paymentMethod) {
        this.id = numOfObjects++;
        this.description = description;
        this.location = location;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.paymentMethod = paymentMethod;
        this.income = income;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        // get latitude and longitude from the location string
        String lat = location.split(",")[0];
        String lon = location.split(",")[1];
        Location location = new Location(lat, lon);
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        Date date = new Date();
        try {
            date = new SimpleDateFormat().parse(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PAYMENT_METHOD getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PAYMENT_METHOD paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}