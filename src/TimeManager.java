
package iseProject;
import java.io.*;
import java.util.*;

public class TimeManager {

    public static class ScheduleEntry {
        public String title;
        public String startTime;
        public String endTime;
        public String date; // Added date field

        public ScheduleEntry(String title, String startTime, String endTime, String date) {
            this.title = title;
            this.startTime = startTime;
            this.endTime = endTime;
            this.date = date;
        }
    }

    // Inner class for Reminder entries
    public static class ReminderEntry {
        public String description;
        public String time;
        public String date; // Added date field

        public ReminderEntry(String description, String time, String date) {
            this.description = description;
            this.time = time;
            this.date = date;
        }
    }

    public TimeManager() {
    }

    public boolean addSchedule(String username, String title, String startTime, String endTime, String date) {
        String filename = username + "_schedule.txt";

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("title=" + title + "|start=" + startTime + "|end=" + endTime + "|date=" + date + "\n");
            return true;
        } catch (IOException e) {
            System.out.println("Error: Unable to save schedule.");
            return false;
        }
    }

    public ArrayList<ScheduleEntry> getAllSchedules(String username) {
        ArrayList<ScheduleEntry> schedules = new ArrayList<>();
        String filename = username + "_schedule.txt";

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                String title = extractValue(line, "title");
                String startTime = extractValue(line, "start");
                String endTime = extractValue(line, "end");
                String date = extractValue(line, "date");

                schedules.add(new ScheduleEntry(title, startTime, endTime, date));
            }
        } catch (FileNotFoundException e) {
            // No schedules yet, return empty list
        }

        return schedules;
    }

    public boolean deleteSchedule(String username, String titleToDelete) {
        String filename = username + "_schedule.txt";
        File inputFile = new File(filename);
        File tempFile = new File("temp_schedule.txt");

        boolean found = false;

        try (Scanner fileScanner = new Scanner(inputFile);
             FileWriter writer = new FileWriter(tempFile)) {

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String title = extractValue(line, "title");

                if (!title.equals(titleToDelete)) {
                    writer.write(line + "\n");
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
            return true;
        } else {
            tempFile.delete();
            return false;
        }
    }

    public boolean setReminder(String username, String description, String time, String date) {
        String filename = username + "_reminders.txt";

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("desc=" + description + "|time=" + time + "|date=" + date + "\n");
            return true;
        } catch (IOException e) {
            System.out.println("Error saving reminder.");
            return false;
        }
    }


    public ArrayList<ReminderEntry> getAllReminders(String username) {
        ArrayList<ReminderEntry> reminders = new ArrayList<>();
        String filename = username + "_reminders.txt";

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                // Parse: desc=xxx|time=HH:MM|date=DD/MM/YYYY
                String description = extractValue(line, "desc");
                String time = extractValue(line, "time");
                String date = extractValue(line, "date");

                reminders.add(new ReminderEntry(description, time, date));
            }
        } catch (FileNotFoundException e) {
            // No reminders yet, return empty list
        }

        return reminders;
    }

    public boolean deleteReminder(String username, String descToDelete) {
        String filename = username + "_reminders.txt";
        File inputFile = new File(filename);
        File tempFile = new File("temp_reminder.txt");

        boolean found = false;

        try (Scanner fileScanner = new Scanner(inputFile);
             FileWriter writer = new FileWriter(tempFile)) {

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String desc = extractValue(line, "desc");

                if (!desc.equals(descToDelete)) {
                    writer.write(line + "\n");
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
            return true;
        } else {
            tempFile.delete();
            return false;
        }
    }
    private String extractValue(String line, String key) {
        String[] parts = line.split("\\|");
        for (String part : parts) {
            if (part.startsWith(key + "=")) {
                return part.substring(key.length() + 1);
            }
        }
        return "";
    }






}