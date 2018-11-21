package util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import rmi.Command;

/**
 * Serialization utility class
 * 
 * @author kadirayk
 *
 */
public class SerializationUtil {

	private SerializationUtil() {
	}


	public static List<Command> readResponseTimes(String path) {
		List<Command> commandList = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			Command[] commands = mapper.readValue(new File(path), Command[].class);
			commandList = Arrays.asList(commands);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return commandList;
	}


}
