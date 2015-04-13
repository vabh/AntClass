package server.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.Message;

public class Main {

	private void doTest() {
		try {
			// fire to localhost port 1099
			Registry myRegistry = LocateRegistry.getRegistry("127.0.0.1", 1099);

			// search for myMessage service
			Message impl = (Message) myRegistry.lookup("myMessage");

			// call server's method
			impl.sayHello("edwin");

			System.out.println("Message Sent");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.doTest();
	}
}