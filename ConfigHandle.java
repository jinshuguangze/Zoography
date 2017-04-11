package application;

import java.io.*;
import java.util.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class ConfigHandle {

	/**
	 * Reset a configuration option to default 设置某个选项至默认
	 * 
	 * @param fileName
	 *            Profile name
	 * @param item
	 *            Option name
	 * @return A hashMap that stores the default data for the option
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static HashMap<String,ArrayList<String>> setConfigInitialization(String fileName, String item)
			throws ClassNotFoundException, IOException {

		ArrayList<String> keys = new ArrayList<String>() {
			{
				add(null);
				add("ListViewItems");
				add("ListViewEventSequence");
			}
		};

		HashMap<String,ArrayList<String>> configLib = new HashMap<>();
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

	/**
	 * Reset all configuration options to default 设置所有选项至默认
	 * 
	 * @param fileName
	 *            Profile name
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void setAllConfigInitialization(String fileName) throws ClassNotFoundException, IOException {

		HashMap<String,ArrayList<String>> configLib = setConfigInitialization(fileName, null);
		String fileString = Class.forName(new Throwable().getStackTrace()[0].getClassName()).getResource(fileName)
				.getFile();
		File file = new File(fileString);

		if (file.exists())
			file.delete();
		file.createNewFile();

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
	 * Universal read function 通用读取函数
	 * 
	 * @param fileName
	 *            Profile name
	 * @param item
	 *            Option name
	 * @return An arrayList with line read the file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static ArrayList<String> generalReader(String fileName, String item)
			throws IOException, ClassNotFoundException {

		String fileString = Class.forName(new Throwable().getStackTrace()[0].getClassName()).getResource(fileName)
				.getFile();

		ArrayList<String> aArrayList = new ArrayList<String>();
		String s = "";

		try (BufferedReader aReader = new BufferedReader(new FileReader(fileString))) {
			while ((s = aReader.readLine()) != null) {
				if (!s.equals("") && !s.startsWith("#")) {
					aArrayList.add(s);
				}
			}
		}

		return aArrayList;
	}

	/**
	 * Gets the current string data for an option for a configuration file
	 * 得到一个配置文件的某个选项的当前字符串数据
	 * 
	 * @param fileName
	 *            Profile name
	 * @param item
	 *            Option name
	 * @return An array of strings that stores configuration data
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static String[] getConfigStringData(String fileName, String item)
			throws IOException, ClassNotFoundException {

		ArrayList<String> aArrayList = generalReader(fileName, item);
		int size = aArrayList.size();
		String[] array = null;

		for (int i = 0; i < size; i++) {
			String atempString = aArrayList.get(i);
			if (item.equals(atempString.substring(0, atempString.indexOf("=")))) {
				array = atempString.substring(atempString.indexOf("=") + 1).split(",");
			}
		}

		return array;
	}

	/**
	 * Gets the current int data for an option for a configuration file
	 * 得到一个配置文件的某个选项的当前整型数据
	 * 
	 * @param fileName
	 *            Profile name
	 * @param item
	 *            Option name
	 * @return An array of int that stores configuration data
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static int[] getConfigIntData(String fileName, String item) throws IOException, ClassNotFoundException {

		String[] Data = getConfigStringData(fileName, item);
		int[] NewData = new int[Data.length];

		for (int i = 0; i < Data.length; i++) {
			NewData[i] = Integer.parseInt(Data[i]);
		}

		return NewData;
	}

	/**
	 * Set an option to a value 设置一个选项到某个值
	 * 
	 * @param fileName
	 *            Profile name
	 * @param item
	 *            Option name
	 * @param modifyString
	 *            An String of strings that store the changed value
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void setConfigData(String fileName, String item, String modifyString)
			throws IOException, ClassNotFoundException {

		String fileString = Class.forName(new Throwable().getStackTrace()[0].getClassName()).getResource(fileName)
				.getFile();
		StringBuilder aBuilder = new StringBuilder();

		try (BufferedReader aReader = new BufferedReader(new FileReader(fileString))) {
			while (!aReader.ready()) {
				aBuilder.append(aReader.readLine());
				aBuilder.append("\r\n");
			}
			int position = aBuilder.indexOf(item);

			if (position != -1) {
				aBuilder.delete(position, aBuilder.length());
				aBuilder.append(modifyString);
			}
		}
	}

}
