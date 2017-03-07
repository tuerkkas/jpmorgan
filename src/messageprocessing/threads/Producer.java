package messageprocessing.threads;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import messageprocessing.messagetype.MessageType;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class Producer implements Runnable {

	private static final Logger logger = Logger.getLogger(Producer.class);

	private static ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private MessageProducer producer;
	private Session session;
	ObjectMessage message;

	private void InitConection() {
		// Create a ConnectionFactory
		connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

		try {
			// Create a Connection
			connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination
			Destination destination = session.createQueue("JPMORGAN.TEST");

			// Create a MessageProducer from the Session to the Queue
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			message = session.createObjectMessage();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public Producer(MessageType messageType) throws JMSException {
		InitConection();

		try {

			message.setObject(messageType);
			producer.send(message);
		} catch (JMSException e) {
			logger.error(e.getMessage() + e.getStackTrace());
		}

	}


	public void run() {
		try {

			session.close();
			connection.close();
		} catch (Exception e) {
			logger.error(e.getMessage() + e.getStackTrace());
		}
	}

}
