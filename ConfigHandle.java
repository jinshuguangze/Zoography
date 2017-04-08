package application;

import java.io.*;
import java.util.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class ConfigHandle {
	public ConfigHandle() {
	}

	/**
	 * 设置某个配置文件变量名的属性列表
	 * 
	 * @param item
	 *            需要设置默认值的配置文件变量名
	 * @return 返回一个包含所有配置变量的动态数组
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<String> setConfigInitialization(String fileName, String item)
			throws ClassNotFoundException, IOException {

		ArrayList<String> keys = new ArrayList<String>() {
			{
				add("");
				add("ListViewItems");
				add("ListViewEventSequence");
			}
		};

		HashMap configLib = new HashMap();
		int i = 0;
		// 添加数据
		{
			configLib.put(keys.get(i), new ArrayList<String>() {
				{
					add("");
					add("");
					add("");
				}
			});
			i++;
		}
		;
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
		;
		{
			configLib.put(keys.get(i), new ArrayList<String>() {
				{
					add("列表事件顺序");
					add("0,1,2,3,4");
					add("");
				}
			});
			i++;
		}
		;

		setConfigData(fileName, item, ((ArrayList<String>) configLib.get(item)).get(1));

		return keys;
	}

	public static void setAllConfigInitialization(String fileName) throws ClassNotFoundException, IOException {
		ArrayList<String> keys = setConfigInitialization(fileName, "");
		// 未完成!
	}

	/**
	 * 通用文件读取
	 * 
	 * @param fileName
	 *            配置文件文件名
	 * @param item
	 *            配置文件中的变量名
	 * @return 读取成功后的文件,以字符串动态数组形式返回
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static ArrayList<String> generalReader(String fileName, String item)
			throws IOException, ClassNotFoundException {

		// 得到配置文件的绝对路径,利用Throwable的方法
		String fileString = Class.forName(new Throwable().getStackTrace()[0].getClassName()).getResource(fileName)
				.getFile();

		// 初始化数据
		ArrayList<String> aArrayList = new ArrayList<String>();
		String s = "";

		// 逐行读取,不含有StringBuilder
		try (BufferedReader aReader = new BufferedReader(new FileReader(fileString))) {
			while ((s = aReader.readLine()) != null) {
				if (!s.equals("") && !s.startsWith("#")) {
					aArrayList.add(s);
				}
			}
		}

		// 返回
		return aArrayList;
	}

	/**
	 * 得到配置文件中的字符串数据
	 * 
	 * @param fileName
	 *            配置文件文件名
	 * @param item
	 *            配置文件中的变量名
	 * @return 变量的值,以字符串数组的形式返回
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static String[] getConfigStringData(String fileName, String item)
			throws IOException, ClassNotFoundException {

		// 初始化变量
		ArrayList<String> aArrayList = generalReader(fileName, item);
		int size = aArrayList.size();
		String[] array = null;

		// 提取返回信息
		for (int i = 0; i < size; i++) {
			String atempString = aArrayList.get(i);
			if (item.equals(atempString.substring(0, atempString.indexOf("=")))) {
				array = atempString.substring(atempString.indexOf("=") + 1).split(",");
			}
		}

		// 返回
		return array;
	}

	/**
	 * 得到配置文件中的整形数据
	 * 
	 * @param fileName
	 *            配置文件文件名
	 * @param item
	 *            配置文件中的变量名
	 * @return 变量的值,以整形数组的形式返回
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static int[] getConfigIntData(String fileName, String item) throws IOException, ClassNotFoundException {

		// 调用字符串读取函数
		String[] Data = getConfigStringData(fileName, item);

		// 设置新数组长度
		int[] NewData = new int[Data.length];

		// 强制转化为整形
		for (int i = 0; i < Data.length; i++) {
			NewData[i] = Integer.parseInt(Data[i]);
		}

		// 返回
		return NewData;
	}

	/**
	 * 更改目标配置的值
	 * 
	 * @param fileName
	 *            配置文件文件名
	 * @param item
	 *            配置文件中的变量名
	 * @param modifyArray
	 *            设置更改的字符串
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void setConfigData(String fileName, String item, String modifyArray)
			throws IOException, ClassNotFoundException {

		// 得到配置文件的绝对路径,利用Throwable的方法
		String fileString = Class.forName(new Throwable().getStackTrace()[0].getClassName()).getResource(fileName)
				.getFile();
		StringBuilder aBuilder = new StringBuilder();

		// 逐行读取,使用StringBuilder
		try (BufferedReader aReader = new BufferedReader(new FileReader(fileString))) {
			while (!aReader.ready()) {
				aBuilder.append(aReader.readLine());
				aBuilder.append("\r\n");
			}
			int position = aBuilder.indexOf(item);

			// 替换
			if (position != -1) {
				aBuilder.delete(position, aBuilder.length());
				aBuilder.append(modifyArray);
			}
		}
	}

}
