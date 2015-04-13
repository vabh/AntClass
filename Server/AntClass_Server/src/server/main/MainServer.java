package server.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.IMessage;

public class MainServer {

	private void doTest() {
		try {
			// fire to localhost port 1099
			Registry rmiRegistry = LocateRegistry.getRegistry("127.0.0.1", 1099);

			// search for myMessage service
			IMessage rmiMessage = (IMessage) rmiRegistry.lookup("myMessage");

			// call server's method
			rmiMessage.sayHello("edwin");

			System.out.println("IMessage Sent");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MainServer main = new MainServer();

		main.doTest();
	}
}