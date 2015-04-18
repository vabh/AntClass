package server.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.IMessage;

public class MainServer {

	public synchronized void synchronizedFunction1() {
		try {
			Registry rmiRegistry = LocateRegistry.getRegistry("127.0.0.1", 1099); // fire to localhost port 1099
			IMessage rmiMessage = (IMessage) rmiRegistry.lookup(IMessage.messageTag); // search for myMessage service
			rmiMessage.remoteFunction1("param 1"); // call server's method
			System.out.println("IMessage Sent with param 1 to remoteSynchronizedFunction1()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void synchronizedFunction2() {
		try {
			Registry rmiRegistry = LocateRegistry.getRegistry("127.0.0.1", 2099); // fire to another localhost port 2099
			IMessage rmiMessage = (IMessage) rmiRegistry.lookup(IMessage.messageTag); // search for myMessage service
			rmiMessage.remoteFunction2("param 2"); // call server's method
			System.out.println("IMessage Sent with param 2 to remoteSynchronizedFunction2()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final MainServer main = new MainServer();

		// run the synchronizedFunction1() in a separate thread
		(new Thread() {
			public void run() {
				main.synchronizedFunction1();
			}
		}).start();

		// run the synchronizedFunction2() in another separate thread
		(new Thread() {
			public void run() {
				main.synchronizedFunction2();
			}
		}).start();
	}
}