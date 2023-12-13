package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    String password;

    public MailSender() {

        //String filePath = "/Users/patricialagerhult/NetBeansProjects/networkprog/id1212-network-programming-patjo/Lab5/src/password.txt";
        String filePath = "C:\\Users\\PC\\git_projects\\id1212-network-programming-patjo\\Lab5\\src\\java\\service\\password.txt";
        File file = new File(filePath);

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            this.password = bufferedReader.readLine();

        } catch (Exception ex) {

        }
    }

    public void sendMail(String username, int score) {

      
        String host = "smtp.kth.se";
        String port = "587";
        final String senderMail = "patlag";

       
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

       
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderMail, password);
            }
        });

        try {
          
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("patlag@kth.se"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username));
            message.setSubject("Quiz Game Result");
            message.setText("Congratulations, your score is " + score + ".\n Good Job!");
            Transport.send(message);

            System.out.println("Mail sent successfully.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
