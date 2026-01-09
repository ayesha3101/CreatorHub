package iseProject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.Scanner;

public class UserManager {
    private AdminManager adminManager;

    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }
    public UserManager() {

    }

    public void signup(String username,String email ,String password) {
        String Hashpass=SimpleHasher.hash(password);
        try (FileWriter signupFile = new FileWriter("creator.txt", true)) {
            signupFile.write(username + "  " + email + "  " + Hashpass + "  not active\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateUserStatus(String userInput, String status) {
        File inputFile = new File("creator.txt");
        File tempFile = new File("temp_status.txt");
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(tempFile)) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length >= 3) {
                    String fileUsername = parts[0];
                    String fileEmail = parts[1];

                    if (userInput.equalsIgnoreCase(fileUsername) || userInput.equalsIgnoreCase(fileEmail)) {
                        // Update status
                        writer.write(parts[0] + "  " + parts[1] + "  " + parts[2] + "  " + status + "\n");
                        found = true;
                    } else {
                        writer.write(line + "\n");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
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

    public static boolean isUsernameTaken(String username) {
        if(username.equalsIgnoreCase("admin")) {
            return true;
        }
        File file = new File("creator.txt");
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 1) continue;

                if (parts[0].equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isEmailTaken(String email) {
        if(email.equalsIgnoreCase("ayesha.naveed3101@gmail.com")){
            return true;
        }
        File file = new File("creator.txt");
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue; // skip blank lines

                String[] parts = line.split("\\s+"); // split on spaces/tabs
                if (parts.length < 2) continue;      // ensure email exists in column 2

                if (parts[1].equalsIgnoreCase(email)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private static boolean checkLoginBoolean(String fileName, String userInput, String password) {
        File file = new File(fileName);
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 3) continue;

                String fileUsername = parts[0];
                String fileEmail = parts[1];
                String storedHash = parts[2];

                // Match username or email
                if (userInput.equalsIgnoreCase(fileUsername) ||
                        userInput.equalsIgnoreCase(fileEmail)) {

                    String hashedInput = SimpleHasher.hash(password);

                    // Password correct?
                    return hashedInput.equals(storedHash);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean login(String userInput, String password) {
        if (userInput.equalsIgnoreCase("admin") ||
                userInput.equalsIgnoreCase("ayesha.naveed3101@gmail.com")) {

            return checkLoginBoolean("admin_data.txt", userInput, password);
        }

        return checkLoginBoolean("creator.txt", userInput, password);
    }

    boolean userVerification(String username) {
        if (username.equalsIgnoreCase("admin")) { // block "admin" in any case
            System.out.println("Username taken.");
            return false;
        }

        if (username.contains(" ")) {
            System.out.println("Username cannot have spaces.");
            return false;
        }

        File file = new File("creator.txt");
        if (!file.exists()) {
            return true;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] userData = fileScanner.nextLine().split("  ");
                if (userData.length > 0 && userData[0].equals(username)) {
                    System.out.println("Username already exists.");
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            return true;
        }

        return true;
    }

    boolean emailVerification(String email) {
        if (email.length() < 12) {
            System.out.println("The email must have at least 12 characters.");
            return false;
        }

        if (email.contains(" ")) {
            System.out.println("Email cannot have spaces.");
            return false;
        }

        if (!email.contains("@")) {
            System.out.println("Please use a valid email address.");
            return false;
        }

        File file = new File("creator.txt");
        if (!file.exists()) {
            return true;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] userData = fileScanner.nextLine().split("  ");
                if (userData.length > 1 && userData[1].equals(email)) {
                    System.out.println("Email already exists.");
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            return true;
        }

        return true;
    }
    public boolean passVerification(String password) {
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters.");
            return false;
        }

        if (password.contains(" ")) {
            System.out.println("Password cannot have spaces.");
            return false;
        }

        return true;
    }

    // Make SimpleHasher static and fix hexadecimal format
    public static class SimpleHasher {
        public static String hash(String input) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                for (byte b : hash) {
                    sb.append(String.format("%02x", b)); // Use lowercase for consistency
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Hash algorithm not supported");
            }
        }
    }
    // Check if email is registered in creator.txt
    public boolean isEmailRegistered(String email) {
        File file = new File("creator.txt");
        if (!file.exists()) return false;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length >= 2 && parts[1].equalsIgnoreCase(email)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Reset password for a creator by email
    public boolean resetPassword(String email, String newPassword) {
        File inputFile = new File("creator.txt");
        File tempFile = new File("temp_reset.txt");
        boolean found = false;

        String hashedNewPassword = SimpleHasher.hash(newPassword);

        try (Scanner fileScanner = new Scanner(inputFile);
             FileWriter writer = new FileWriter(tempFile)) {

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) {
                    writer.write(line + "\n");
                    continue;
                }

                String[] parts = line.split("\\s+");
                if (parts.length >= 3 && parts[1].equalsIgnoreCase(email)) {
                    String status = (parts.length > 3) ? parts[3] : "not active";
                    writer.write(parts[0] + "  " + parts[1] + "  " + hashedNewPassword + "  " + status + "\n");
                    found = true;
                } else {
                    writer.write(line + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
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

}
