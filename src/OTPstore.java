package iseProject;
import java.util.HashMap;
import java.util.Map;

public class OTPstore {
    private static final Map<String, String> otpManager = new HashMap<>();
    public static String generateOTP() {
        int otp = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }
    public static void storeOTP(String email, String otp) {
        otpManager.put(email, otp);
    }
    public static boolean verifyOTP(String email, String enteredOtp) {
        return otpManager.containsKey(email) && otpManager.get(email).equals(enteredOtp);
    }

    public static void removeOTP(String email) {
        otpManager.remove(email);
    }
}