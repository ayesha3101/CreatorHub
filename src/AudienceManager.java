package iseProject;

import java.io.*;
import java.util.Scanner;

public class AudienceManager {

    // Data fields
    public int totalAudience = 0;
    public int age18_24 = 0;
    public int age25_34 = 0;
    public int age35_44 = 0;
    public int age45plus = 0;
    public int maleCount = 0;
    public int femaleCount = 0;

    // Add a single audience member
    public boolean addAudienceMember(String username, int age, char gender) {
        String filename = username + "_audience.txt";

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(age + " " + gender + "\n");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void loadAudienceStats(String username) {
        totalAudience = 0;
        age18_24 = 0;
        age25_34 = 0;
        age35_44 = 0;
        age45plus = 0;
        maleCount = 0;
        femaleCount = 0;

        String filename = username + "_audience.txt";

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNext()) {
                int age = fileScanner.nextInt();
                char gender = fileScanner.next().charAt(0);
                totalAudience++;

                // Categorize age groups
                if (age >= 18 && age <= 24) age18_24++;
                else if (age >= 25 && age <= 34) age25_34++;
                else if (age >= 35 && age <= 44) age35_44++;
                else if (age >= 45) age45plus++;

                // Count genders
                if (gender == 'M' || gender == 'm') maleCount++;
                else if (gender == 'F' || gender == 'f') femaleCount++;
            }
        } catch (FileNotFoundException e) {
            // Stats remain at 0 if no file
        }
    }


//    public boolean clearAudienceData(String username) {
//        String filename = username + "_audience.txt";
//        File file = new File(filename);
//
//        if (file.exists()) {
//            return file.delete();
//        }
//        return true;
//    }
}