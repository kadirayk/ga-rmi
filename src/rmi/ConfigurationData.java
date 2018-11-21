package rmi;

import java.io.Serializable;
import java.util.Map;

public class ConfigurationData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String gameSelection;

	private String gameConf;

	private String gameServer;

	private String gameWindow;

	private String gameExe; // only required for games which run with ga-server-periodic

	private Map<String, String> configuration;

	public ConfigurationData(Map<String, String> configuration) {
		this.configuration = configuration;
	}

	public Map<String, String> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Map<String, String> configuration) {
		this.configuration = configuration;
	}

	public String getGameSelection() {
		return gameSelection;
	}

	public void setGameSelection(String gameSelection) {
		this.gameSelection = gameSelection;
	}

	public String getGameConf() {
		return gameConf;
	}

	public void setGameConf(String gameConf) {
		this.gameConf = gameConf;
	}

	public String getGameServer() {
		return gameServer;
	}

	public void setGameServer(String gameServer) {
		this.gameServer = gameServer;
	}

	public String getGameWindow() {
		return gameWindow;
	}

	public void setGameWindow(String gameWindow) {
		this.gameWindow = gameWindow;
	}

	public String getGameExe() {
		return gameExe;
	}

	public void setGameExe(String gameExe) {
		this.gameExe = gameExe;
	}

}
