public class Transaction {
    String type;
    String description;
    double amount;

    public Transaction(String type, String description, double amount) {
        this.type = type;
        this.description = description;
        this.amount = amount;
    }

    public String toString() {
        return type + " | " + description + " | " + amount + "/-";
    }
}
