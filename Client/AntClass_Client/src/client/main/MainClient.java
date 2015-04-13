package client.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.MessageImpl;

public class MainClient {

	private void start() {
		try {
			// create on port 1099
			Registry registry = LocateRegistry.createRegistry(1099);

			// create a new service named myMessage
			registry.rebind("myMessage", new MessageImpl());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("system is ready");
	}

	public static void main(String[] args) {
		MainClient main = new MainClient();
		main.start();
	}
}