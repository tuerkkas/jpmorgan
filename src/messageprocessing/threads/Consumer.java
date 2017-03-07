package messageprocessing.threads;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import messageprocessing.messagetype.MessageType;
import messageprocessing.messagetype.MessageType1;
import messageprocessing.messagetype.MessageType2;
import messageprocessing.messagetype.MessageType3;
import messageprocessing.sales.Sale;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class Consumer implements Runnable, MessageListener {

	private static final String SUBHEADER_FINAL_REPORT = "----------------------Adjustements----------------------";
	private static final String HEADER_FINAL_REPORT = "--------------------LOG FINAL REPORT--------------------";
	private static final String HEADER_MESSAGE_REPORT = "---------NUMBER OF SALES OF EACH PRODUCT AND THEIR TOTAL VALUE---------";
	private static final String MSG_SERVICE_IS_PAUSING_STOP = "The service is pausing,Stop accepting new messages.";
	private static final int NUMBER_REPORT_SALES = 10;
	private static final int NUMBER_REPORT_SALES_FINAL = 50;

	private static final Logger logger = Logger.getLogger(Consumer.class);

	private static ActiveMQConnectionFactory connectionFactory;
	private static Connection connection;
	private MessageConsumer consumer;
	private Session session;
	private List<Sale> salesList;
	private Set<String> productNameSalesList;
	private List<MessageType> incomingSales;

	private boolean consumerOn = true;

	private int numPetition = 0;

	public Consumer() {

		if (salesList == null) {
			salesList = new ArrayList<Sale>();
			productNameSalesList = new HashSet<String>();
			incomingSales = new ArrayList<MessageType>();
		}
		// Create a ConnectionFactory
		connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		connectionFactory.setTrustedPackages(Arrays.asList(
				"messageprocessing.messagetype",
				"messageprocessing.messagetype.*"));

		try {
			// Create a Connection
			connection = connectionFactory.createConnection();
			this.session = connection.createSession(true,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("JPMORGAN.TEST");

			this.consumer = session.createConsumer(destination);
			this.consumer.setMessageListener(this);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void run() {
		try {
			logger.info("Connection starting...");
			connection.start();
		} catch (JMSException ex) {
			logger.error(ex.getMessage(), ex);
		}

	}

	@Override
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			Object objectMessage;
			try {
				if (consumerOn) {
					objectMessage = ((ObjectMessage) message).getObject();
					numPetition++;
					processMessage(objectMessage);
					session.commit();
				} else {
					System.out.println(MSG_SERVICE_IS_PAUSING_STOP);
				}
			} catch (JMSException e) {
				logger.error(e.getMessage() + e.getStackTrace());
				try {
					session.rollback();
				} catch (JMSException e1) {
					logger.error(e1.getMessage(), e1);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
	}

	private void processMessage(Object objectMessage) throws JMSException {

		if (objectMessage instanceof MessageType1) {
			saveMessage((MessageType1) objectMessage);
		} else if (objectMessage instanceof MessageType2) {
			saveMessage((MessageType2) objectMessage);
		} else if (objectMessage instanceof MessageType3) {
			saveMessage((MessageType3) objectMessage);
		}

		if (numPetition == NUMBER_REPORT_SALES) {
			afterEvery10MessageReport();
			numPetition = 0;
		}
		if (incomingSales.size() == NUMBER_REPORT_SALES_FINAL) {
			finalReport();
			consumerOn = false;
		}

	}

	private void afterEvery10MessageReport() {

		String name = "";
		int numSales = 0;
		BigDecimal values = new BigDecimal(0);

		System.out.println(HEADER_MESSAGE_REPORT);

		for (String productName : productNameSalesList) {
			numSales = 0;
			values = new BigDecimal(0);
			List<Sale> streamSales = salesList
					.stream()
					.filter(line -> productName.equals(line.getProductType()
							.getField())).collect(Collectors.toList());

			for (Sale sale : streamSales) {
				name = sale.getProductType().getField();
				values = values.add(sale.getProductType().getValue()
						.multiply(new BigDecimal(sale.getQuantity())));
				numSales += sale.getQuantity();
			}

			// Output Message
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("Number of Sales of ").append(name)
					.append(" is ").append(numSales)
					.append(" with a total value of: ").append(values);

			System.out.println(stringBuffer);

		}

	}

	private void finalReport() {

		List<MessageType> streamSales = incomingSales.stream()
				.filter(MessageType3.class::isInstance)
				.collect(Collectors.toList());

		Map<String, List<MessageType>> groupByName = streamSales.stream()
				.collect(Collectors.groupingBy(MessageType::getName));

		System.out.println(HEADER_FINAL_REPORT);
		System.out.println(SUBHEADER_FINAL_REPORT);

		// keys
		List<String> types = groupByName.entrySet().stream()
				.sorted(Comparator.comparing(Map.Entry::getKey))
				.map(Map.Entry::getKey).collect(Collectors.toList());

		for (String productType : types) {
			
			List<MessageType> ins = groupByName.get(productType);

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(productType);
			for (MessageType in : ins) {
				stringBuffer.append(" \n ");
				stringBuffer.append(in.toString());
			}
			System.out.println(stringBuffer);

		}

	}

	private void saveMessage(MessageType1 messageType) {
		productNameSalesList.add(messageType.getName());
		incomingSales.add(messageType);
		salesList
				.add(new Sale(messageType.getName(), messageType.getValue(), 1));
	}

	private void saveMessage(MessageType2 messageType) {
		productNameSalesList.add(messageType.getName());
		incomingSales.add(messageType);
		salesList.add(new Sale(messageType.getName(), messageType.getValue(),
				messageType.getOcurrencies()));
	}

	private void saveMessage(MessageType3 messageType) {

		incomingSales.add(messageType);

		List<Sale> streamSales = salesList
				.stream()
				.filter(line -> messageType.getName().equals(
						line.getProductType().getField()))
				.collect(Collectors.toList());

		streamSales.forEach(strSales -> {
			strSales.getProductType().setValue(
					messageType.getOperation().calculate(
							strSales.getProductType().getValue(),
							new BigDecimal(messageType.getValue())));
		});
	}

}
