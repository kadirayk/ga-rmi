package rmi.server;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import org.ini4j.Wini;

import rmi.Command;
import rmi.ConfigurationData;
import rmi.ConfigurationService;
import util.FileUtil;
import util.SerializationUtil;

/**
 * 
 * @author kadirayk
 *
 */
public class ConfigurationServiceImpl extends UnicastRemoteObject implements ConfigurationService {

	protected ConfigurationServiceImpl() throws RemoteException {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Double configureAndEvaluate(ConfigurationData config) throws RemoteException {
		Double score = 0.0;

		for (Map.Entry<String, String> entry : config.getConfiguration().entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		configureGA(config);

		createGameServerStartScript(config);

		final ProcessBuilder pb = new ProcessBuilder("groundingroutine.bat").redirectOutput(Redirect.INHERIT)
				.redirectError(Redirect.INHERIT);
		System.out.print("Execute grounding process...");
		Process p;
		try {
			p = pb.start();
			while (p.isAlive()) {
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (final IOException e1) {
			e1.printStackTrace();
		}

		stopGameServer(config);

		String responseTimeFilePath = "C:/ga/responseTime.json";
		score = calculateScore(responseTimeFilePath);

		return score;
	}

	private void createGameServerStartScript(ConfigurationData conf) {
		StringBuilder content = new StringBuilder();

		content.append("@echo off\n");
		content.append("title grounding routine\n");
		content.append("cd /d %~dp0\n");
		content.append("cd C:/ga/\n");

		String gameConf = conf.getGameConf();
		String gameServer = conf.getGameServer();
		String gameExe = conf.getGameExe();
		String gameSelection = conf.getGameSelection();

		if (gameExe != null && !gameExe.isEmpty()) {
			content.append(gameExe).append("\n").append("TIMEOUT /T 5\n");
		}

		content.append(gameServer).append(" config/").append(gameConf).append(" >> ")
				.append(gameSelection + ".log 2>&1").append("\n");
		content.append("waitfor WaitForServerToBeReady /t 5\n ");
		content.append("ga-client ").append("config/client.abs.conf ").append("rtsp://localhost:").append("8554")
				.append("/desktop");

		FileUtil.writeToFile("groundingroutine.bat", content.toString());
	}

	private static void stopGameServer(ConfigurationData config) {
		String gameWindowTitle = config.getGameWindow();
		String killcommand = "taskkill /F /FI \"WindowTitle eq " + gameWindowTitle + "\" /T";
		try {
			Runtime.getRuntime().exec(killcommand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void configureGA(ConfigurationData config) {
		try {
			File confFile = new File("C:/ga/config/common/video-x264-param.conf");
			Wini ini = new Wini(confFile);
			for (Map.Entry<String, String> e : config.getConfiguration().entrySet()) {
				ini.put("video", e.getKey(), e.getValue());
			}
			ini.store();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	private static List<Command> readResponseTimes(String path) {
//		List<Command> commandList = null;
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			Command[] commands = mapper.readValue(new File(path), Command[].class);
//			commandList = Arrays.asList(commands);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return commandList;
//	}

	private static Double calculateScore(String responseTimeFilePath) {
		List<Command> commandList = SerializationUtil.readResponseTimes(responseTimeFilePath);
		Double totalDelay = 0.0;
		int numberOfCommandsWithReceiveTime = 0;
		for (Command c : commandList) {
			if (c.getReceivetimeStamp() != null && c.getReceivetimeStamp() > 0) {
				totalDelay += c.getDelay();
				numberOfCommandsWithReceiveTime++;
			}
		}

		return totalDelay / numberOfCommandsWithReceiveTime;
	}

}
