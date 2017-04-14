package application;

import java.io.*;
import java.util.*;
import application.*;

public class DataHandle {
	public static final String BIOLOGY_CSV = Main.BIOLOGY_CSV;

	/**
	 * Create a child data file 创造一个子数据文件
	 * 
	 * @param fileName
	 *            Data file name
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void createNewDataFile(String fileName) throws ClassNotFoundException, IOException {
		File file = new File(getFilePath(fileName));
		if (file.exists()) {
			UsefulToolkit toolkit = new UsefulToolkit();
			toolkit.autoCreateDataFile(fileName);
		} else {
			file.createNewFile();
			String[] aStringArray = new String[getColumnCount(BIOLOGY_CSV)];
			for (int i = 0; i < aStringArray.length; i++) {
				aStringArray[i] = getStringData(BIOLOGY_CSV, 0, i);
			}

			boolean b = false;

			for (Biology aBiology : Biology.values()) {
				if (aBiology.toString().equals(aStringArray[0])) {
					b = true;
				} else if (b) {
					aStringArray[0] = aBiology.toString();
					break;
				}
			}

			if (b) {
				try (BufferedWriter aWriter = new BufferedWriter(new FileWriter(file, false))) {
					for (int i = 0; i < aStringArray.length; i++) {
						aWriter.write(aStringArray[i]);
						if (i >= aStringArray.length - 1) {
							aWriter.write("\r\n");
						} else {
							aWriter.write(",");
						}
					}
				}
			}
		}
	}

	/**
	 * Determine whether the file exist data 判断该文件是否含有数据
	 * 
	 * @param fileName
	 *            Data file name
	 * @return Ture if it exist data;false otherwise
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static boolean existData(String fileName) throws ClassNotFoundException, IOException {
		boolean b = getStringData(fileName, 1, 0).equals("") ? false : true;
		return b;
	}

	/**
	 * Determine whether the file is the final data file 判断该文件是否是最终子数据
	 * 
	 * @param fileName
	 *            Data file name
	 * @return Ture if it is final data file;false otherwise
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static boolean finalData(String fileName) throws ClassNotFoundException, IOException {
		boolean b = getStringData(fileName, 0, 0).equals("") ? true : false;
		return b;
	}

	/**
	 * Universal read function 通用读取函数
	 * 
	 * @param fileName
	 *            Data file name
	 * @return An arrayList with line read the file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	protected static ArrayList<ArrayList<String>> generalReader(String fileName)
			throws IOException, ClassNotFoundException {

		String filePath = getFilePath(fileName);

		ArrayList<ArrayList<String>> tArrayList = new ArrayList<>();
		String s = "";

		try (BufferedReader aReader = new BufferedReader(new FileReader(filePath))) {
			while ((s = aReader.readLine()) != null) {
				ArrayList<String> sArrayList = new ArrayList<>();
				String[] aStrings = s.split(",");
				for (String string : aStrings) {
					sArrayList.add(string);
				}

				tArrayList.add(sArrayList);
			}

		}

		return tArrayList;
	}

	/**
	 * Get the column of data file 得到数据文件的列数
	 * 
	 * @param fileName
	 *            Data file name
	 * @return Column of data file
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static int getColumnCount(String fileName) throws ClassNotFoundException, IOException {
		ArrayList<ArrayList<String>> tArrayList = generalReader(fileName);
		return tArrayList.get(0).size();
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
		String filePath = System.getProperty("user.dir") + "\\resource\\data\\" + fileName;
		return filePath;
	}

	/**
	 * Get the line of data file 得到数据文件的行数
	 * 
	 * @param fileName
	 *            Data file name
	 * @return Line of data file
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static int getLineCount(String fileName) throws ClassNotFoundException, IOException {
		ArrayList<ArrayList<String>> tArrayList = generalReader(fileName);
		return tArrayList.size();
	}

	/**
	 * Get file specific location of the string 得到文件特定位置的字符串
	 * 
	 * @param fileName
	 *            Data file name
	 * @param line
	 *            Line
	 * @param column
	 *            Column
	 * @return String data
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static String getStringData(String fileName, int line, int column)
			throws ClassNotFoundException, IOException {
		if (getLineCount(fileName) > line && getColumnCount(fileName) > column) {
			return generalReader(fileName).get(line).get(column);
		} else {
			return "";
		}
	}
}
