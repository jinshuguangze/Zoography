package application;

import java.io.*;
import java.util.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class ConfigHandle {
	public static final String MAIN_CFG = Main.MAIN_CFG;

	/**
	 * Clear configuration file content 清除配置文件内容
	 * 
	 * @param fileName
	 *            Configuration file name
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static File clearConfigFile(String fileName) throws ClassNotFoundException, IOException {
		File file = new File(getFilePath(fileName));
		if (file.exists())
			file.delete();
		file.createNewFile();
		return file;
	}

	/**
	 * Universal read function 通用读取函数
	 * 
	 * @param fileName
	 *            Configuration file name
	 * @param item
	 *            Option name
	 * @return An arrayList with line read the file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	protected static ArrayList<String> generalReader(String fileName) throws IOException, ClassNotFoundException {

		String filePath = getFilePath(fileName);

		ArrayList<String> aArrayList = new ArrayList<String>();
		String s = "";

		try (BufferedReader aReader = new BufferedReader(new FileReader(filePath))) {
			while ((s = aReader.readLine()) != null) {
				if (!s.equals("") && !s.startsWith("#")) {
					aArrayList.add(s);
				}
			}
		}

		return aArrayList;
	}

	/**
	 * Gets the current int data for an option for a configuration file
	 * 得到一个配置文件的某个选项的当前整型数据
	 * 
	 * @param fileName
	 *            Configuration file name
	 * @param item
	 *            Option name
	 * @return An array of int that stores configuration data
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static int[] getConfigIntData(String fileName, String item) throws IOException, ClassNotFoundException {

		String[] Data = getConfigStringData(fileName, item);
		int[] NewData = new int[Data.length];

		try {
			for (int i = 0; i < Data.length; i++) {
				NewData[i] = Integer.parseInt(Data[i]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			setConfigInitialization(fileName, item);
			NewData = getConfigIntData(fileName, item);
		}

		return NewData;
	}

	/**
	 * Gets the current string data for an option for a configuration file
	 * 得到一个配置文件的某个选项的当前字符串数据
	 * 
	 * @param fileName
	 *            Configuration file name
	 * @param item
	 *            Option name
	 * @return An array of strings that stores configuration data
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static String[] getConfigStringData(String fileName, String item)
			throws IOException, ClassNotFoundException {

		ArrayList<String> aArrayList = generalReader(fileName);
		int size = aArrayList.size();
		String[] array = null;

		for (int i = 0; i < size; i++) {
			String atempString = aArrayList.get(i);
			if (item.equals(atempString.substring(0, atempString.indexOf("=")))) {
				array = atempString.substring(atempString.indexOf("=") + 1).replace(" ", "").split(",");
			}
		}

		return array;
	}

	/**
	 * Gets the absolute path to the destination file name of this class
	 * 得到本类目标文件名的绝对路径
	 * 
	 * @param fileName
	 *            Configuration file name
	 * @return Absolute path
	 * @throws ClassNotFoundException
	 */
	protected static String getFilePath(String fileName) throws ClassNotFoundException {
		String filePath = System.getProperty("user.dir") + "\\config\\" + fileName;
		return filePath;
	}

	/**
	 * Reset all configuration options to default 设置所有选项至默认
	 * 
	 * @param fileName
	 *            Configuration file name
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void setAllConfigInitialization(String fileName) throws ClassNotFoundException, IOException {

		HashMap<String, ArrayList<String>> configLib = setConfigInitialization(fileName, null);
		File file = clearConfigFile(fileName);

		try (BufferedWriter aWriter = new BufferedWriter(new FileWriter(file, true))) {
			aWriter.write("#" + fileName.substring(0, fileName.indexOf(".")) + "\r\n");
			configLib.forEach((key, value) -> {
				if (!(key == null)) {
					ArrayList<String> Data = (ArrayList<String>) value;
					try {
						aWriter.write("\r\n");
						aWriter.write("#" + Data.get(0) + "(默认:" + Data.get(1) + ")" + "\r\n");
						aWriter.write(key.toString() + "=" + Data.get(1) + "\r\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * Set an option to a value 设置一个选项到某个值
	 * 
	 * @param fileName
	 *            Configuration file name
	 * @param item
	 *            Option name
	 * @param modifyString
	 *            An String of strings that store the changed value
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	protected static void setConfigData(String fileName, String item, String modifyString)
			throws IOException, ClassNotFoundException {

		String filePath = getFilePath(fileName);
		StringBuilder aBuilder = new StringBuilder();

		try (BufferedReader aReader = new BufferedReader(new FileReader(filePath))) {
			while (aReader.ready()) {
				aBuilder.append(aReader.readLine());
				aBuilder.append("\r\n");
			}
		}

		int positionStart = aBuilder.indexOf(item) + item.length() + 1;
		int positionEnd = aBuilder.indexOf("\r\n", positionStart);

		if ((positionStart != -1) && (positionEnd != -1)) {
			aBuilder.replace(positionStart, positionEnd, modifyString);
		}

		File file = clearConfigFile(fileName);
		try (BufferedWriter aWriter = new BufferedWriter(new FileWriter(file, false))) {
			aWriter.write(aBuilder.toString());
		}

	}

	/**
	 * Reset a configuration option to default 设置某个选项至默认
	 * 
	 * @param fileName
	 *            Configuration file name
	 * @param item
	 *            Option name
	 * @return A hashMap that stores the default data for the option
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static HashMap<String, ArrayList<String>> setConfigInitialization(String fileName, String item)
			throws ClassNotFoundException, IOException {

		ArrayList<String> keys = new ArrayList<String>() {
			{
				add(null);
				add("ListViewItems");
				add("ListViewEventSequence");
			}
		};

		HashMap<String, ArrayList<String>> configLib = new HashMap<>();
		int i = 0;
		{
			configLib.put(keys.get(i), null);
			i++;
		}

		{
			configLib.put(keys.get(i), new ArrayList<String>() {
				{
					add("列表项目");
					add("分层查看,搜索模式,随机页面,个性设置");
					add("");
				}
			});
			i++;
		}

		{
			configLib.put(keys.get(i), new ArrayList<String>() {
				{
					add("列表事件顺序");
					add("0,1,2,3");
					add("");
				}
			});
			i++;
		}

		if (item != null) {
			setConfigData(fileName, item, ((ArrayList<String>) configLib.get(item)).get(1));
		}

		return configLib;

	}
}