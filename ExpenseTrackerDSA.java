import java.util.*;

// Expense Node (Linked List)
class Expense {
    String description;
    double amount;
    String type; // Income or Expense
    Expense next;

    Expense(String desc, double amt, String type) {
        this.description = desc;
        this.amount = amt;
        this.type = type;
        this.next = null;
    }
}

// Singly Linked List (List ADT)
class ExpenseList {
    Expense head;

    void add(Expense e) {
        if (head == null) {
            head = e;
            return;
        }
        Expense temp = head;
        while (temp.next != null)
            temp = temp.next;
        temp.next = e;
    }

    void display() {
        Expense temp = head;
        if (temp == null) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("\nDescription\tType\tAmount");
        while (temp != null) {
            System.out.println(temp.description + "\t" + temp.type + "\t₹" + temp.amount);
            temp = temp.next;
        }
    }

    // Linear Search
    void search(String key) {
        Expense temp = head;
        boolean found = false;
        while (temp != null) {
            if (temp.description.equalsIgnoreCase(key)) {
                System.out.println("Found: ₹" + temp.amount + " (" + temp.type + ")");
                found = true;
            }
            temp = temp.next;
        }
        if (!found)
            System.out.println("Expense not found.");
    }

    // Bubble Sort by Amount
    void bubbleSort() {
        if (head == null) return;

        boolean swapped;
        do {
            swapped = false;
            Expense curr = head;
            while (curr.next != null) {
                if (curr.amount > curr.next.amount) {
                    double tempAmt = curr.amount;
                    String tempDesc = curr.description;
                    String tempType = curr.type;

                    curr.amount = curr.next.amount;
                    curr.description = curr.next.description;
                    curr.type = curr.next.type;

                    curr.next.amount = tempAmt;
                    curr.next.description = tempDesc;
                    curr.next.type = tempType;

                    swapped = true;
                }
                curr = curr.next;
            }
        } while (swapped);

        System.out.println("Expenses sorted using Bubble Sort.");
    }

    double totalIncome() {
        double sum = 0;
        Expense temp = head;
        while (temp != null) {
            if (temp.type.equals("Income"))
                sum += temp.amount;
            temp = temp.next;
        }
        return sum;
    }

    double totalExpense() {
        double sum = 0;
        Expense temp = head;
        while (temp != null) {
            if (temp.type.equals("Expense"))
                sum += temp.amount;
            temp = temp.next;
        }
        return sum;
    }
}

// MAIN CLASS
public class ExpenseTrackerDSA {

    static Scanner sc = new Scanner(System.in);

    // Hashing: Username → Password
    static HashMap<String, String> users = new HashMap<>();

    // Stack for Undo
    static Stack<Expense> undoStack = new Stack<>();

    // Queue for History
    static Queue<Expense> historyQueue = new LinkedList<>();

    static ExpenseList list = new ExpenseList();

    public static void main(String[] args) {

        System.out.println("==== Personal Expense Tracker (DSA Console Project) ====\n");

        // Login
        System.out.print("Enter Username: ");
        String user = sc.nextLine();

        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        users.put(user, pass); // Hashing
        System.out.println("Login successful!\n");

        int choice;

        do {
            System.out.println("\n1. Add Transaction");
            System.out.println("2. View Summary");
            System.out.println("3. View Transactions");
            System.out.println("4. Search Expense");
            System.out.println("5. Sort Expenses");
            System.out.println("6. Undo Last Transaction");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addTransaction();
                case 2 -> viewSummary();
                case 3 -> list.display();
                case 4 -> searchExpense();
                case 5 -> list.bubbleSort();
                case 6 -> undoTransaction();
                case 7 -> System.out.println("Thank you!");
                default -> System.out.println("Invalid choice");
            }

        } while (choice != 7);
    }

    // ADD TRANSACTION (UPDATED)
    static void addTransaction() {

        System.out.print("Description: ");
        String desc = sc.nextLine();

        System.out.print("Amount: ");
        double amt = sc.nextDouble();
        sc.nextLine();

        System.out.print("Type (Income/Expense): ");
        String type = sc.nextLine();

        Expense e = new Expense(desc, amt, type);
        list.add(e);

        undoStack.push(e);   // Stack
        historyQueue.add(e); // Queue

        System.out.println("Transaction added.");

        // Show balance immediately
        double income = list.totalIncome();
        double expense = list.totalExpense();

        System.out.println("Current Balance: ₹" + (income - expense));
    }

    // VIEW SUMMARY
 
    static void viewSummary() {
        double income = list.totalIncome();
        double expense = list.totalExpense();

        System.out.println("\nTotal Income : ₹" + income);
        System.out.println("Total Expense: ₹" + expense);
        System.out.println("Balance      : ₹" + (income - expense));
    }

    static void searchExpense() {
        System.out.print("Enter description to search: ");
        String key = sc.nextLine();
        list.search(key);
    }

    static void undoTransaction() {

        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        undoStack.pop();
        list.head = list.head.next; // simplified removal

        System.out.println("Last transaction undone.");
    }
}