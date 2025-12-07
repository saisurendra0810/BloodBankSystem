import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;

// Donor Class
class Donor {
    String name;
    int age;
    String bloodGroup;

    Donor(String name, int age, String bloodGroup) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
    }
}

// Main System Class
public class BloodBankSystem {

    static Scanner sc = new Scanner(System.in);

    // Donor list
    static ArrayList<Donor> donors = new ArrayList<>();

    // Blood stock (Units)
    static HashMap<String, Integer> bloodStock = new HashMap<>();

    // Initialize blood groups
    static {
        bloodStock.put("A+", 10);
        bloodStock.put("A-", 10);
        bloodStock.put("B+", 10);
        bloodStock.put("B-", 10);
        bloodStock.put("O+", 10);
        bloodStock.put("O-", 10);
        bloodStock.put("AB+", 10);
        bloodStock.put("AB-", 10);
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== BLOOD BANK INVENTORY SYSTEM =====");
            System.out.println("1. Add Donor");
            System.out.println("2. View Donors");
            System.out.println("3. Add Blood Units to Stock");
            System.out.println("4. Remove Blood Units (Donation to Patient)");
            System.out.println("5. View Blood Stock");
            System.out.println("6. Exit");
            System.out.println("7. Report");

            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1: addDonor(); break;
                case 2: viewDonors(); break;
                case 3: addBloodUnits(); break;
                case 4: removeBloodUnits(); break;
                case 5: viewBloodStock(); break;
                case 6: System.out.println("Exiting system..."); return;
                case 7: reportOption(); break;
                default: System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // Add donor details
    public static void addDonor() {
        System.out.print("Enter donor name: ");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();

        System.out.print("Enter blood group (A+, A-, B+, O+, AB+, ...): ");
        sc.nextLine();
        String bg = sc.nextLine().toUpperCase();

        donors.add(new Donor(name, age, bg));
        System.out.println("Donor added successfully!");
    }

    // Show all donors
    public static void viewDonors() {
        if (donors.isEmpty()) {
            System.out.println("No donors available.");
            return;
        }

        System.out.println("\n--- Donor List ---");
        for (Donor d : donors) {
            System.out.println("Name: " + d.name + " | Age: " + d.age + " | Blood Group: " + d.bloodGroup);
        }
    }

    // Add blood units
    public static void addBloodUnits() {
        System.out.print("Enter blood group: ");
        sc.nextLine();
        String bg = sc.nextLine().toUpperCase();

        if (!bloodStock.containsKey(bg)) {
            System.out.println("Invalid blood group!");
            return;
        }

        System.out.print("Enter number of units to add: ");
        int units = sc.nextInt();

        bloodStock.put(bg, bloodStock.get(bg) + units);
        System.out.println("Units added successfully!");
    }

    // Remove blood units for patients
    public static void removeBloodUnits() {
        System.out.print("Enter blood group: ");
        sc.nextLine();
        String bg = sc.nextLine().toUpperCase();

        if (!bloodStock.containsKey(bg)) {
            System.out.println("Invalid blood group!");
            return;
        }

        System.out.print("Enter number of units needed: ");
        int units = sc.nextInt();

        int available = bloodStock.get(bg);

        if (units > available) {
            System.out.println("Not enough units available!");
        } else {
            bloodStock.put(bg, available - units);
            System.out.println("Units deducted successfully!");
        }
    }

    // Show stock
    public static void viewBloodStock() {
        System.out.println("\n--- Blood Stock ---");
        for (String bg : bloodStock.keySet()) {
            System.out.println(bg + " : " + bloodStock.get(bg) + " units");
        }
    }

    // REPORT OPTION
    public static void reportOption() {
        System.out.print("Do you want the report? (yes/no): ");
        sc.nextLine();
        String ans = sc.nextLine().toLowerCase();

        if (ans.equals("yes")) {
            generateReport();
        } else {
            System.out.println("Report cancelled.");
        }
    }

    // FULL REPORT (Automatically saved to file)
    public static void generateReport() {
        try {
            FileWriter fw = new FileWriter("report.txt");  
            PrintWriter pw = new PrintWriter(fw);

            pw.println("===== BLOOD BANK REPORT =====");

            pw.println("\n--- Donors ---");
            if (donors.isEmpty()) {
                pw.println("No donors available.");
            } else {
                for (Donor d : donors) {
                    pw.println("Name: " + d.name +
                               " | Age: " + d.age +
                               " | Blood Group: " + d.bloodGroup);
                }
            }

            pw.println("\n--- Blood Stock ---");
            for (String bg : bloodStock.keySet()) {
                pw.println(bg + " : " + bloodStock.get(bg) + " units");
            }

            pw.println("\n===== END OF REPORT =====");

            pw.close();
            System.out.println("Report generated and saved to report.txt");

        } catch (Exception e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }

}  // END OF CLASS