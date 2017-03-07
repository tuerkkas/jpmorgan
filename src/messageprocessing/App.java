package messageprocessing;
import messageprocessing.messagetype.MessageType;
import messageprocessing.messagetype.MessageType1;
import messageprocessing.messagetype.MessageType2;
import messageprocessing.messagetype.MessageType3;
import messageprocessing.messagetype.Operation;
import messageprocessing.threads.Consumer;
import messageprocessing.threads.Producer;

public class App {

	private static final String STRAWBERRY = "Strawberry";
	private static final String ORANGE = "Orange";
	private static final String APPLE = "Apple";

	/**
	 * Starts the application
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// Messages that could be receibed by the Consumer. There are threads
		// and one message not be processed.

		MessageType appleType1 = new MessageType1(APPLE, 4);
		MessageType orangeType2 = new MessageType2(ORANGE, 3, 20);
		MessageType appleType2 = new MessageType2(APPLE, 2, 15);

		MessageType strawberryType1 = new MessageType1(STRAWBERRY, 25);
		MessageType orangeType3Multiply = new MessageType3(ORANGE, 10,
				Operation.MULTIPLY);
		MessageType orangeType3Add = new MessageType3(ORANGE, 10,
				Operation.ADD);
		MessageType orangeType3subtract = new MessageType3(ORANGE, 10,
				Operation.SUBTRACT);
		MessageType AppleType3 = new MessageType3(APPLE, 100, Operation.ADD);
		MessageType strawberryType3 = new MessageType3(STRAWBERRY, 10,
				Operation.SUBTRACT);

		thread(new Consumer(), false);

		thread(new Producer(appleType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(appleType2), false);
		thread(new Producer(strawberryType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(AppleType3), false);
		thread(new Producer(orangeType3Multiply), false);
		thread(new Producer(appleType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(appleType2), false);
		thread(new Producer(strawberryType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(AppleType3), false);
		thread(new Producer(orangeType3Add), false);
		thread(new Producer(appleType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(appleType2), false);
		thread(new Producer(strawberryType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(AppleType3), false);
		thread(new Producer(orangeType3Multiply), false);
		thread(new Producer(appleType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(appleType2), false);
		thread(new Producer(strawberryType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(AppleType3), false);
		thread(new Producer(orangeType3subtract), false);
		thread(new Producer(appleType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(appleType2), false);
		thread(new Producer(strawberryType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(AppleType3), false);
		thread(new Producer(orangeType3Multiply), false);
		thread(new Producer(appleType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(appleType2), false);
		thread(new Producer(strawberryType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(AppleType3), false);
		thread(new Producer(orangeType3subtract), false);
		thread(new Producer(appleType1), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(appleType2), false);
		thread(new Producer(strawberryType3), false);
		thread(new Producer(orangeType2), false);
		thread(new Producer(AppleType3), false);
		thread(new Producer(orangeType3subtract), false);
		thread(new Producer(appleType1), false);
		thread(new Producer(orangeType2), false);

	}

	public static void thread(Runnable runnable, boolean daemon) {

		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

}