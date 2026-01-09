package iseProject;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;


public class ContentManager {

    // Add content to file
    public boolean addContent(String username, String title, String status, String date, String type) {
        String filename = username + "_content.txt";

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(title + "||" + status + "||" + date + "||" + type + "\n");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Delete content from file
    public boolean deleteContent(String username, String titleToDelete) {
        String filename = username + "_content.txt";
        File inputFile = new File(filename);
        File tempFile = new File("temp.txt");
        boolean found = false;

        try (Scanner fileScanner = new Scanner(inputFile);
             FileWriter writer = new FileWriter(tempFile)) {

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (!line.contains(titleToDelete)) {
                    writer.write(line + "\n");
                } else {
                    found = true;
                }
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

    // Get all content as ArrayList
    public ArrayList<String[]> getAllContent(String username) {
        ArrayList<String[]> contentList = new ArrayList<>();
        String filename = username + "_content.txt";

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|\\|");
                if (parts.length >= 4) {
                    contentList.add(parts); // [title, status, date, type]
                }
            }
        } catch (FileNotFoundException e) {
            // Return empty list if no file
        }

        return contentList;
    }

}