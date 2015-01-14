package org.hawkular.alerts.sample.mdb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "AlertNotifEmailQueueName") })
public class AlertNotifEmailMDB implements MessageListener {

    @Resource(mappedName = "java:jboss/mail/Default")
    private Session mailSession;
    
    public AlertNotifEmailMDB() {
		System.out.println("===> AlertNotifEmailMDB init (AlertNotifEmailQueueName)");
	}

	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			System.out.println("===> AlertNotifEmailMDB Received (AlertNotifEmailQueueName): "
					+ textMessage.getText());

			List<String> addresses = new ArrayList<String>();
			addresses.add("jshaughn@redhat.com");
			sendEmail(addresses, "Alert Fired!", textMessage.getText());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Send email to the addresses passed in toAddresses with the passed subject and body. Invalid emails will be
     * reported back. This can only catch sender errors up to the first smtp gateway.
     * 
     * @param toAddresses
     *            list of email addresses to send to
     * @param messageSubject
     *            subject of the email sent
     * @param messageBody
     *            body of the email to be sent
     * 
     * @return list of email receivers for which initial delivery failed.
     */
    public Collection<String> sendEmail(Collection<String> toAddresses, String messageSubject, String messageBody) {

        MimeMessage mimeMessage = new MimeMessage(mailSession);
        try {
            String fromAddress = "jshaughn@redhat.com";
            InternetAddress from = new InternetAddress(fromAddress);
            mimeMessage.setFrom(from);
            mimeMessage.setSubject(messageSubject);
            mimeMessage.setContent(messageBody, "text/plain");
        } catch (MessagingException e) {
            e.printStackTrace(); // TODO: Customize this generated block
            return toAddresses;
        }

        Exception error = null;
        Collection<String> badAdresses = new ArrayList<String>(toAddresses.size());

        // Send to each recipient individually, do not throw exceptions until we try them all
        for (String toAddress : toAddresses) {
            try {
                System.out.println("Sending email [" + messageSubject + "] to recipient [" + toAddress + "]");
                InternetAddress recipient = new InternetAddress(toAddress);
                Transport.send(mimeMessage, new InternetAddress[] { recipient });
            } catch (Exception e) {
                System.out.println("Failed to send email [" + messageSubject + "] to recipient [" + toAddress + "]: "
                    + e.getMessage());
                badAdresses.add(toAddress);

                // Remember the first error - in case its due to a session initialization problem,
                // we don't want to lose the first error.
                if (error == null) {
                    error = e;
                }
            }
        }

        if (error != null) {
            System.out.println("Sending of emails failed for this reason: " + error.getMessage());
        }

        return badAdresses;
    }
	
}
