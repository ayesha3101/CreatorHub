package iseProject;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class GrowthManager {
    private Scanner scanner;
    private static final int MAX_MONTHS = 120; // 10 years of data

    public int totalSubscribers = 0;
    public int totalViews = 0;
    public ArrayList<GrowthEntry> growthList = new ArrayList<>();

    public static class GrowthEntry {
        public String month;
        public int year;
        public int subscribers;
        public int views;
        public int cumulativeSubscribers;
        public int cumulativeViews;
        public Date date; // For date comparison

        public GrowthEntry(String month, int year, int subscribers, int views) {
            this.month = month;
            this.year = year;
            this.subscribers = subscribers;
            this.views = views;

            // Create date object for sorting
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
                this.date = sdf.parse(month + " " + year);
            } catch (ParseException e) {
                this.date = new Date();
            }
        }
    }

    public GrowthManager() {
        this.scanner = new Scanner(System.in);
    }
    public boolean addGrowthData(String username, String month, int year, int subscribers, int views) {
        String filename = username + "_channel_growth.txt";

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(month + " " + year + " " + subscribers + " " + views + "\n");
            return true;
        } catch (IOException e) {
            System.out.println("Error saving growth data.");
            return false;
        }
    }

    public void loadGrowthData(String username) {
        String filename = username + "_channel_growth.txt";
        growthList.clear();
        totalSubscribers = 0;
        totalViews = 0;

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNext()) {
                String month = fileScanner.next();
                int year = fileScanner.nextInt();
                int subscribers = fileScanner.nextInt();
                int views = fileScanner.nextInt();

                GrowthEntry entry = new GrowthEntry(month, year, subscribers, views);
                growthList.add(entry);
            }

            // Sort by date
            growthList.sort((e1, e2) -> e1.date.compareTo(e2.date));

            // Calculate cumulative values
            int cumSubs = 0;
            int cumViews = 0;

            for (GrowthEntry entry : growthList) {
                cumSubs += entry.subscribers;
                cumViews += entry.views;
                entry.cumulativeSubscribers = cumSubs;
                entry.cumulativeViews = cumViews;
            }

            totalSubscribers = cumSubs;
            totalViews = cumViews;

        } catch (FileNotFoundException e) {
            // No data yet
        }
    }
    public ArrayList<GrowthEntry> getAllGrowthData(String username) {
        if (growthList.isEmpty()) {
            loadGrowthData(username);
        }
        return growthList;
    }

    public ArrayList<GrowthEntry> getGrowthDataBetweenDates(String username, String startMonth, int startYear,
                                                            String endMonth, int endYear) {
        if (growthList.isEmpty()) {
            loadGrowthData(username);
        }

        ArrayList<GrowthEntry> filtered = new ArrayList<>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
            Date startDate = sdf.parse(startMonth + " " + startYear);
            Date endDate = sdf.parse(endMonth + " " + endYear);

            for (GrowthEntry entry : growthList) {
                if ((entry.date.equals(startDate) || entry.date.after(startDate)) &&
                        (entry.date.equals(endDate) || entry.date.before(endDate))) {
                    filtered.add(entry);
                }
            }

            // Recalculate cumulative for filtered data
            int cumSubs = 0;
            int cumViews = 0;
            for (GrowthEntry entry : filtered) {
                cumSubs += entry.subscribers;
                cumViews += entry.views;
                entry.cumulativeSubscribers = cumSubs;
                entry.cumulativeViews = cumViews;
            }

        } catch (ParseException e) {
            System.out.println("Error parsing dates.");
        }

        return filtered;
    }


    public ArrayList<String> getAvailableMonthYears(String username) {
        if (growthList.isEmpty()) {
            loadGrowthData(username);
        }

        ArrayList<String> monthYears = new ArrayList<>();
        for (GrowthEntry entry : growthList) {
            monthYears.add(entry.month + " " + entry.year);
        }

        return monthYears;
    }

    public boolean deleteGrowthData(String username, String month, int year) {
        String filename = username + "_channel_growth.txt";
        File inputFile = new File(filename);
        File tempFile = new File("temp_growth.txt");

        boolean found = false;

        try (Scanner fileScanner = new Scanner(inputFile);
             FileWriter writer = new FileWriter(tempFile)) {

            while (fileScanner.hasNext()) {
                String m = fileScanner.next();
                int y = fileScanner.nextInt();
                int subs = fileScanner.nextInt();
                int views = fileScanner.nextInt();

                if (!(m.equals(month) && y == year)) {
                    writer.write(m + " " + y + " " + subs + " " + views + "\n");
                } else {
                    found = true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error processing files.");
            return false;
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            // Reload data
            loadGrowthData(username);
            return true;
        } else {
            tempFile.delete();
            return false;
        }
    }

}

