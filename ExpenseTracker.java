import java.io.*;
import java.util.*;

public class ExpenseTracker {
    static List<Transaction> transactions = new ArrayList<>();
    static double balance = 0;
    static String title = "Untitled";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Always ask title every time program starts
        System.out.print("Enter the title for this data: ");
        title = scanner.nextLine();

        loadData();  // Load transactions if present

        while (true) {
            System.out.println("\n===== Expense Tracker =====");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Balance");
            System.out.println("4. View Transactions");
            System.out.println("5. Save & Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addTransaction(scanner, "Income");
                case 2 -> addTransaction(scanner, "Expense");
                case 3 -> System.out.println("Current Balance: " + balance + "/-");
                case 4 -> showTransactions();
                case 5 -> {
                    saveData();
                    System.out.println("Saved. Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static void addTransaction(Scanner scanner, String type) {
        System.out.print("Enter description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amt = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        if (type.equals("Expense")) amt = -amt;

        Transaction t = new Transaction(type, desc, Math.abs(amt));
        transactions.add(t);
        balance += amt;

        System.out.println(type + " added successfully!");
    }

    static void showTransactions() {
        System.out.println("\nTitle of this data: " + title);
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("\n--- Transactions ---");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    static void saveData() {
        try (PrintWriter writer = new PrintWriter("data.txt")) {
            writer.println(title);
            writer.println(balance);
            for (Transaction t : transactions) {
                writer.println(t.type + ";" + t.description + ";" + t.amount);
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    static void loadData() {
        File file = new File("data.txt");
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine(); // skip old title

            if (fileScanner.hasNextLine())
                balance = Double.parseDouble(fileScanner.nextLine());

            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(";");
                Transaction t = new Transaction(parts[0], parts[1], Double.parseDouble(parts[2]));
                transactions.add(t);
            }
        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }
}
