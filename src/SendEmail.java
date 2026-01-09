package iseProject;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

class SendEmail{

    public static void sendEmail(String email,String otp){

    String from = "YOUR_EMAIL";
    final String pass="YOUR_APP_PASSWORD";

    Properties p = new Properties();//defines how the program connects to the email server (smtp)
    p.put("mail.smtp.host", "smtp.gmail.com");//tells which host to send the mail to
    p.put("mail.smtp.port", "587");//this sets the port number used to connect to the smtp server
    p.put("mail.smtp.auth", "true");// tells javaMail to use authentication to connect to mail server
    p.put("mail.smtp.starttls.enable", "true");// enables STARTTLS which is a way to encrypt because only then can you connect to the host server

    Session session = Session.getInstance(p, new Authenticator() {// a secure setuup through which java will send your mail ( takes the properties object and authenticator )
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from, pass); //whenever gmail asks for verification return this email and password
        }
    });
    try {
// message ek abstract class hai which represents an email message
        Message message = new MimeMessage(session); // mime message is a concrete class that is used to send emails containing text , attachments etc.
        message.setFrom(new InternetAddress(from)); //is used to confirm the sender of the mail
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // sets the recipient of the email
        message.setSubject("Your OTP Code"); // this sets the subject of the email
        message.setContent(
                "<h3>Your OTP Code</h3><p>Your OTP is: <strong>" + otp + "</strong></p>",
                "text/html"
        ); // used html for formatting the text so it looks better :)

        Transport.send(message); // okay now deliver the message
        System.out.println("Email sent successfully!"); // email sent succesfully !!!;)

    } catch (MessagingException e) { //in case something goes wrong catch the excpetion and display appropriate message
        System.err.println("Failed to send email: " + e.getMessage());
        e.printStackTrace();
    }
}
}

