package loanbook.logic;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import loanbook.model.Model;
import loanbook.model.loan.Loan;

/**
 * The class is used for create reminder email and send the email.
 */
public class SendReminder {
    private String myEmailAccount;
    private String myEmailPassword;
    private String myEmailSmtpHost = "smtp.gmail.com";
    private Loan targetLoan;

    public SendReminder(Model model, String myEmailPassword, Loan targetLoan) {
        this.myEmailAccount = model.getMyEmail();
        this.myEmailPassword = myEmailPassword;
        this.targetLoan = targetLoan;
    }

    /**
     * Send a reminder email
     *
     * @throws Exception
     */
    public void send() throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", myEmailSmtpHost);
        props.setProperty("mail.smtp.auth", "true");

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        Session session = Session.getInstance(props);
        session.setDebug(true);

        MimeMessage message = createReminderEmail(session, myEmailAccount);

        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /**
     * Create a reminder email
     *
     * @throws Exception
     */
    public MimeMessage createReminderEmail(Session session, String sendMail) throws Exception {
        String content = "Dear " + targetLoan.getName().value + ",<br>";
        content += "<br>This is a gentle reminder for your loan. You rented " + targetLoan.getBike().getName().value;
        content += " on " + targetLoan.getLoanStartTime().toString() + " with rate $";
        content += targetLoan.getLoanRate().toString();
        content += "/hr. Please remember to return your bike after use as soon as possible.<br>";
        content += "<br>Thank you for using Loan Book! Enjoy your trip!<br>";
        content += "<br>Best Regards,<br>";
        content += "LoanBook Team";

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sendMail, "LoanBook", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress(targetLoan.getEmail().value, targetLoan.getName().value, "UTF-8"));
        message.setSubject("Your trip with Loan Book", "UTF-8");
        message.setContent(content, "text/html");
        message.setSentDate(new Date());
        message.saveChanges();

        return message;
    }
}
