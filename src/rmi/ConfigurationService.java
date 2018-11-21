package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConfigurationService extends Remote {
	
	public Double configureAndEvaluate(ConfigurationData config) throws RemoteException;

}
