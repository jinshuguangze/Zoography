package application;

import java.io.*;
import java.util.*;
import application.*;

public class DataHandle {
	public static final String BIOLOGY_CSV = Main.BIOLOGY_CSV;
	public static final String DATACODE = Main.DATA_CODE;
	
	/**
	 * Create a child data file 创造一个子数据文件
	 * 
	 * @param fileName
	 *            Data file name
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
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
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
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
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
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
	 * @throws IOException If IO connection failed
	 * @throws ClassNotFoundException If fileName not found
	 */
	protected static ArrayList<ArrayList<String>> generalReader(String fileName)
			throws IOException, ClassNotFoundException {

		String filePath = getFilePath(fileName);

		ArrayList<ArrayList<String>> tArrayList = new ArrayList<>();
		String s = "";

		try (BufferedReader aReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath), DATACODE))) {
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
	 * Get all the data of the target file 得到目标文件的所有数据
	 * 
	 * @param fileName
	 *            Data file name
	 * @return Two-dimensional string array of target data file
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
	 */
	public static String[][] getAllData(String fileName) throws ClassNotFoundException, IOException {
		String[][] data = new String[getLineCount(fileName)][getColumnCount(fileName)];
		for (int i = 0; i < getLineCount(fileName); i++) {
			for (int j = 0; j < getColumnCount(fileName); j++) {
				data[i][j] = getStringData(fileName, i, j);
			}
		}

		return data;
	}

	/**
	 * Get the column of data file 得到数据文件的列数
	 * 
	 * @param fileName
	 *            Data file name
	 * @return Column of data file
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
	 */
	public static int getColumnCount(String fileName) throws ClassNotFoundException, IOException {
		ArrayList<ArrayList<String>> tArrayList = generalReader(fileName);
		return tArrayList.get(0).size();
	}

	/**
	 * Get all the data of the target Column 得到目标列的所有数据
	 * 
	 * @param fileName
	 *            Data file name
	 * @param position
	 *            Which Column
	 * @return String array of target columns
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
	 */
	public static String[] getColumnData(String fileName, int position) throws ClassNotFoundException, IOException {
		String[] aStrings = new String[getLineCount(fileName)];

		if (position < getColumnCount(fileName)) {
			for (int i = 0; i < getLineCount(fileName); i++) {
				aStrings[i] = getStringData(fileName, i, position);
			}
		}

		return aStrings;
	}

	/**
	 * Gets the absolute path to the destination file name of this class
	 * 得到本类目标文件名的绝对路径
	 * 
	 * @param fileName
	 *            Configuration file name
	 * @return Absolute path
	 * @throws ClassNotFoundException If fileName not found
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
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
	 */
	public static int getLineCount(String fileName) throws ClassNotFoundException, IOException {
		ArrayList<ArrayList<String>> tArrayList = generalReader(fileName);
		return tArrayList.size();
	}

	/**
	 * Get all the data of the target line 得到目标行的所有数据
	 * 
	 * @param fileName
	 *            Data file name
	 * @param position
	 *            Which line
	 * @return String array of target lines
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
	 */
	public static String[] getLineData(String fileName, int position) throws ClassNotFoundException, IOException {
		String[] aStrings = new String[getColumnCount(fileName)];

		if (position < getLineCount(fileName)) {
			for (int i = 0; i < getColumnCount(fileName); i++) {
				aStrings[i] = getStringData(fileName, position, i);
			}
		}

		return aStrings;
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
	 *             If fileName not found
	 * @throws IOException
	 *             If IO connection failed
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
