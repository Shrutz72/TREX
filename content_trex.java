import java.io.*;
import java.util.*;

public class Trex {
    private static final String EXPENSES_FILE = "expenses.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n----TREX-----");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1: addExpense(sc); break;
                case 2: viewExpenses(); break;
                case 3: deleteExpense(sc); break;
                case 4: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
        
        scanner.close();
    }

    private static void addExpense(Scanner sc) {
        System.out.print("Enter expense description: ");
        String description = sc.nextLine();
        System.out.print("Enter expense amount: ");
        double amount = sc.nextDouble();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXPENSES_FILE, true))) {
            writer.write(description + "|" + amount);
            writer.newLine();
            System.out.println("Expense added.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void viewExpenses() {
        System.out.println("\n--- Expenses ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(EXPENSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                System.out.println("Description: " + parts[0] + ", Amount: $" + parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    private static void deleteExpense(Scanner sc) {
        System.out.print("Enter the description of the expense to delete: ");
        String descriptionToDelete = sc.nextLine();
        File file = new File(EXPENSES_FILE);
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(descriptionToDelete + "|")) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

            if (found) {
                System.out.println("Expense deleted.");
            } else {
                System.out.println("Expense not found.");
            }
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
        }

        if (file.delete()) {
            tempFile.renameTo(file);
        } else {
            System.out.println("Could not delete original file.");
        }
    }
}

