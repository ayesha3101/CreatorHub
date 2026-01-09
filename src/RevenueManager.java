package iseProject;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RevenueManager {

    // Data fields for totals
    public float totalRevenue = 0;
    public float totalExpenses = 0;
    public float totalProfit = 0;

    // List to store individual revenue entries
    public ArrayList<RevenueEntry> revenueList = new ArrayList<>();

    // Add a single revenue entry
    public boolean addRevenue(String username, String month, int year, float revenue, float expenses, String currency) {
        String filename = username + "_revenue.txt";
        float profit = revenue - expenses;

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(month + " " + year + " " + revenue + " " + expenses + " " + profit + " " + currency + "\n");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Load all revenue data from file
    public void loadRevenueData(String username) {
        // Reset values
        totalRevenue = 0;
        totalExpenses = 0;
        totalProfit = 0;
        revenueList.clear();

        String filename = username + "_revenue.txt";

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNext()) {
                String month = fileScanner.next();
                int year = fileScanner.nextInt();
                float revenue = fileScanner.nextFloat();
                float expenses = fileScanner.nextFloat();
                float profit = fileScanner.nextFloat();
                String currency = fileScanner.hasNext() ? fileScanner.next() : "$";

                // Add to list
                revenueList.add(new RevenueEntry(month, year, revenue, expenses, profit, currency));

                // Calculate totals
                totalRevenue += revenue;
                totalExpenses += expenses;
                totalProfit += profit;
            }
        } catch (FileNotFoundException e) {
            // Empty data if no file
        }
    }

    // Delete revenue entry
    public boolean deleteRevenue(String username, String month, int year) {
        String filename = username + "_revenue.txt";
        File inputFile = new File(filename);
        File tempFile = new File("temp_revenue.txt");
        boolean found = false;

        try (Scanner fileScanner = new Scanner(inputFile);
             FileWriter writer = new FileWriter(tempFile)) {

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" ");

                if (parts.length >= 2) {
                    String entryMonth = parts[0];
                    int entryYear = Integer.parseInt(parts[1]);

                    if (entryMonth.equals(month) && entryYear == year) {
                        found = true;
                        continue; // Skip this line
                    }
                }
                writer.write(line + "\n");
            }

        } catch (IOException e) {
            return false;
        }

        if (found) {
            if (inputFile.delete() && tempFile.renameTo(inputFile)) {
                return true;
            }
        } else {
            tempFile.delete();
        }
        return false;
    }

    // Inner class to hold revenue entry data
    public static class RevenueEntry {
        public String month;
        public int year;
        public float revenue;
        public float expenses;
        public float profit;
        public String currency;

        public RevenueEntry(String month, int year, float revenue, float expenses, float profit, String currency) {
            this.month = month;
            this.year = year;
            this.revenue = revenue;
            this.expenses = expenses;
            this.profit = profit;
            this.currency = currency;
        }
    }
}