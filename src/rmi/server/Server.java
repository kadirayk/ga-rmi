package rmi.server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.ConfigurationService;

public class Server {
	private static int PORT = 1099;
	private static Registry registry;

	public static void startRegistry() throws RemoteException {
		// Create server registry
		registry = LocateRegistry.createRegistry(PORT);
	}

	public static void registerObject(String name, Remote remoteObj) throws RemoteException, AlreadyBoundException {
		//System.setProperty("java.rmi.server.hostname","localhost");
		// Bind the object in the registry.
		// It is bind with certain name.
		// Client will lookup on the registration of the name to get object.
		registry.bind(name, remoteObj);
		System.out.println("Registered: " + name + " -> " + remoteObj.getClass().getName() + "[" + remoteObj + "]");
	}
	
	public static void main(String[] args) throws Exception {
	       System.out.println("Server starting...");
	       startRegistry();
	       registerObject(ConfigurationService.class.getSimpleName(), new ConfigurationServiceImpl());
	 
	       // Server was the start, and was listening to the request from the client.
	       System.out.println("Server started!");
	   }
}
