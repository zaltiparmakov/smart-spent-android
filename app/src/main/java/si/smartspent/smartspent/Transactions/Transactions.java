package si.smartspent.smartspent.Transactions;

import java.time.LocalDateTime;
import java.util.Currency;

import si.smartspent.smartspent.Location;

public class Transactions {
    private int id;
    private String name;
    private String description;
    private Location location;
//    private Currency currency;
    private double amount;
    private LocalDateTime dateTime;
    private String type;
    private String payMethod;

    public Transactions(int id, String name, String description, Location location,
                        double amount, LocalDateTime dateTime, String type, String payMethod) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
//        this.currency = currency;
        this.amount = amount;
        this.dateTime = dateTime;
        this.type = type;
        this.payMethod = payMethod;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

//    public Currency getCurrency() {
//        return currency;
//    }

//    public void setCurrency(Currency currency) {
//        this.currency = currency;
//    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
}
