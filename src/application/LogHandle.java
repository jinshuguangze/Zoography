package application;

import java.io.*;
import java.util.*;

public class LogHandle {
	public static final String LOG_CODE = Main.LOG_CODE;

	public static void createNewLog(String fileName) throws IOException, ClassNotFoundException {
		File file = new File(getFilePath(fileName));
		if (file.exists()) {
			fileName += "_";
			createNewLog(fileName);
		}

		file.createNewFile();
	}

	protected static ArrayList<String> generalReader(String fileName) throws IOException, ClassNotFoundException {
		String filePath = getFilePath(fileName);

		ArrayList<String> aArrayList = new ArrayList<String>();
		String s = "";

		try (BufferedReader aReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath), LOG_CODE))) {
			while ((s = aReader.readLine()) != null) {
				aArrayList.add(s);
			}
		}

		return aArrayList;
	}

	public static void writeLog(String fileName, String log) throws ClassNotFoundException, IOException {
		String filePath = getFilePath(fileName);
		File file = new File(filePath);
		try (BufferedWriter aWriter = new BufferedWriter(new FileWriter(file, true))) {
			aWriter.append(log+"\r\n");			
		}
	}

	public static String readBeforeLog(String fileName, int bottomNumber) throws ClassNotFoundException, IOException {
		ArrayList<String> stringList = generalReader(fileName);
		if (bottomNumber > stringList.size()) {
			return null;
		} else {
			return stringList.get(stringList.size() - bottomNumber);
		}
	}
		
	protected static String getFilePath(String fileName) throws ClassNotFoundException {
		String filePath = System.getProperty("user.dir") + "\\log\\" + fileName;
		return filePath;
	}
}
