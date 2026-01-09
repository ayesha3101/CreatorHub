package iseProject;
import java.io.*;
import java.util.*;
public class AdminManager {

    public final String ADMIN_FILE = "admin_data.txt";

    public static class CreatorInfo {
        public String username;
        public String email;
        public String hashedPassword;

        public CreatorInfo(String username, String email, String hashedPassword) {
            this.username = username;
            this.email = email;
            this.hashedPassword = hashedPassword;
        }
    }

    public AdminManager(UserManager userManager) {
        initializeAdminFile();
    }

    private void initializeAdminFile() {
        File adminFile = new File(ADMIN_FILE);
        if (!adminFile.exists()) {
            try (FileWriter writer = new FileWriter(adminFile)) {
                String defaultUsername = "admin";
                String defaultEmail = "ayesha.naveed3101@gmail.com";
                String defaultPassword = "admin123";

                String hashedPassword = UserManager.SimpleHasher.hash(defaultPassword);
                writer.write(defaultUsername + " " + defaultEmail + " " + hashedPassword + " not active\n");
                System.out.println("Admin file created with default admin credentials.");
            } catch (IOException e) {
                System.out.println("Error creating admin file: " + e.getMessage());
            }
        }
    }

    public static boolean updateAdminStatus(String status) {
        File inputFile = new File("admin_data.txt");
        File tempFile = new File("temp_admin_status.txt");

        try (Scanner fileScanner = new Scanner(inputFile);
             FileWriter writer = new FileWriter(tempFile)) {

            if (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" ");

                if (parts.length >= 3) {
                    writer.write(parts[0] + " " + parts[1] + " " + parts[2] + " " + status + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);
        return true;
    }

    public boolean adminLogin(String password) {
        try (Scanner fileScanner = new Scanner(new File(ADMIN_FILE))) {
            if (!fileScanner.hasNextLine()) {
                System.out.println("Admin file is empty or missing.");
                return false;
            }

            String[] data = fileScanner.nextLine().split(" ");
            String storedHashedPass = data[2];
            String enteredHash = UserManager.SimpleHasher.hash(password);

            return enteredHash.equals(storedHashedPass);

        } catch (Exception e) {
            System.out.println("Error reading admin_data.txt");
            return false;
        }
    }

    public ArrayList<CreatorInfo> getAllCreators() {
        ArrayList<CreatorInfo> creators = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(new File("creator.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] userData = fileScanner.nextLine().split("  ");
                if (userData.length >= 3) {
                    creators.add(new CreatorInfo(userData[0], userData[1], userData[2]));
                }
            }
        } catch (FileNotFoundException e) {
            // No creators yet
        }

        return creators;
    }


    public int getCreatorCount() {
        return getAllCreators().size();
    }

    public ArrayList<CreatorInfo> searchCreators(String query) {
        ArrayList<CreatorInfo> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        try (Scanner fileScanner = new Scanner(new File("creator.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] userData = fileScanner.nextLine().split("  ");
                if (userData.length >= 3) {
                    if (userData[0].toLowerCase().contains(lowerQuery) ||
                            userData[1].toLowerCase().contains(lowerQuery)) {
                        results.add(new CreatorInfo(userData[0], userData[1], userData[2]));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // No file
        }

        return results;
    }

    public boolean deleteCreatorByEmail(String email) {
        File inputFile = new File("creator.txt");
        File tempFile = new File("temp.txt");

        boolean found = false;

        try (Scanner fileScanner = new Scanner(inputFile);
             FileWriter writer = new FileWriter(tempFile)) {

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split("  ");

                if (userData.length >= 2 && userData[1].equals(email)) {
                    found = true;
                } else {
                    writer.write(line + "\n");
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




    public boolean updateAdminPassword(String oldPassword, String newPassword) {
        if (!adminLogin(oldPassword)) {
            return false;
        }
        try (Scanner fileScanner = new Scanner(new File(ADMIN_FILE))) {
            if (!fileScanner.hasNextLine()) {
                return false;
            }
            String[] data = fileScanner.nextLine().split(" ");
            String username = data[0];
            String email = data[1];

            // Hash new password
            String newHashedPassword = UserManager.SimpleHasher.hash(newPassword);

            // Write new data
            try (FileWriter writer = new FileWriter(ADMIN_FILE)) {
                writer.write(username + " " + email + " " + newHashedPassword + "\n");
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error updating password.");
            return false;
        }
    }


    public ArrayList<String> getLoggedInUsers() {
        ArrayList<String> loggedIn = new ArrayList<>();
        return loggedIn;
    }
    // Check if admin email exists
    public boolean checkAdminEmail(String email) {
        if (email.equalsIgnoreCase("admin") ||
                email.equalsIgnoreCase("ayesha.naveed3101@gmail.com")) {

            File adminFile = new File(ADMIN_FILE);
            return adminFile.exists();
        }

        return false;
    }

    // Reset admin password by email/username
    public boolean resetAdminPassword(String email, String newPassword) {
        File adminFile = new File(ADMIN_FILE);
        if (!adminFile.exists()) {
            return false;
        }

        String hashedNewPassword = UserManager.SimpleHasher.hash(newPassword);

        try (Scanner fileScanner = new Scanner(adminFile)) {
            if (!fileScanner.hasNextLine()) {
                return false;
            }

            String line = fileScanner.nextLine();
            String[] parts = line.split(" ");

            if (parts.length >= 3) {
                String username = parts[0];
                String adminEmail = parts[1];
                String status = (parts.length > 3) ? parts[3] : "not active";

                try (FileWriter writer = new FileWriter(ADMIN_FILE)) {
                    writer.write(username + " " + adminEmail + " " + hashedNewPassword + " " + status + "\n");
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error updating admin password: " + e.getMessage());
            return false;
        }

        return false;
    }

}



