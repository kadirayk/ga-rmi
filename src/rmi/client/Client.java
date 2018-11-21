package rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

import rmi.ConfigurationData;
import rmi.ConfigurationService;

public class Client {

	private static final String HOST = "localhost"; // "131.234.250.145";
	private static final int PORT = 1099;
	private static Registry registry;

	public static void main(String[] args) throws Exception {
		String host;
		int port;
		if (args.length >= 1) {
			host = args[0];
		} else {
			host = HOST;
		}

		if (args.length >= 2) {
			port = Integer.valueOf(args[1]);
		} else {
			port = PORT;
		}

		registry = LocateRegistry.getRegistry(host, port);

		ConfigurationService service = (ConfigurationService) registry
				.lookup(ConfigurationService.class.getSimpleName());

		Map<String, String> configuration = new HashMap<String, String>();

		configuration.put("video-specific[b]", "3000000");
		configuration.put("video-specific[me_method]", "dia");
		configuration.put("video-specific[me_range]", "16");
		configuration.put("video-specific[intra_refresh]", "1");
		configuration.put("video-specific[refs]", "1");
		configuration.put("video-fps", "24");
		configuration.put("video-renderer", "hardware");

		ConfigurationData config = new ConfigurationData(configuration);
		config.setGameSelection("Neverball");
		config.setGameConf("server.neverball.conf");
		config.setGameServer("ga-server-event-driven");
		config.setGameWindow("Neverball 1.5.4");

		Double score = service.configureAndEvaluate(config);

		System.out.println(score);

	}

}
