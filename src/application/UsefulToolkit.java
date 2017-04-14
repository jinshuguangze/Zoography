package application;

import java.io.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import application.*;

public class UsefulToolkit {

	/**
	 * A function of automatic create the rest data file 一个生成剩余数据文件的函数
	 * 
	 * @param fileName
	 *            Data file Name
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void autoCreateDataFile(String fileName) throws ClassNotFoundException, IOException {
		ArrayList<ArrayList<String>> arrayData = DataHandle.generalReader(fileName);
		if (!DataHandle.finalData(fileName)) {
			if (DataHandle.existData(fileName)) {
				for (int i = 1; i < DataHandle.getLineCount(fileName); i++) {
					String addonFileName = DataHandle.getStringData(fileName, i, 0);
					DataHandle.createNewDataFile(
							fileName.substring(0, fileName.indexOf(".")) + "_" + addonFileName + ".csv");
				}
			}
		}
	}

	/**
	 * A function of automatic formatting length 一个自动格式化长度的函数
	 * 
	 * @param mainList
	 *            List as a reference length
	 * @param valueList
	 *            List as the value of HashMap(string)
	 * @param keyList
	 *            List as the key of HashMap(integer)
	 * @return A HashMap that stores an automatically formatted valueList
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public HashMap<Integer, String> autoItemCountConformity(List<?> mainList, List<String> valueList,
			List<Integer> keyList) throws ClassNotFoundException, IOException {

		int mainCount = mainList.size();
		int valueCount = valueList.size();
		int keyCount = keyList.size();

		if (mainCount > valueCount) {
			for (int i = 0; i < mainCount - valueCount; i++) {
				valueList.add("新建选项" + (i + 1));
			}
			ConfigHandle.setConfigData("Option.cfg", "ListViewItems",
					valueList.toString().substring(1, valueList.toString().length() - 1).replace(" ", ""));
		} else if (mainCount < valueCount) {
			valueList = valueList.subList(0, mainCount);
			ConfigHandle.setConfigData("Option.cfg", "ListViewItems",
					valueList.toString().substring(1, valueList.toString().length() - 1).replace(" ", ""));
		}

		if (mainCount > keyCount) {
			int[] aIntArray = ConfigHandle.getConfigIntData("Option.cfg", "ListViewEventSequence");
			HashMap<Integer, Integer> aHashMap = new HashMap<>();
			for (int i = 0; i < aIntArray.length; i++) {
				aHashMap.put(aIntArray[i], i);
			}

			int[] anOtherArray = aIntArray.clone();
			Arrays.sort(anOtherArray);
			for (int i = 0; i < aIntArray.length; i++) {
				keyList.set(i, aHashMap.get(anOtherArray[i]));
			}

			for (int i = 0; i < mainCount - keyCount; i++) {
				keyList.add(keyCount + i);
			}
			ConfigHandle.setConfigData("Option.cfg", "ListViewEventSequence",
					keyList.toString().substring(1, keyList.toString().length() - 1).replace(" ", ""));
		} else if (mainCount < keyCount) {
			keyList = keyList.subList(0, mainCount);
			ConfigHandle.setConfigData("Option.cfg", "ListViewEventSequence",
					keyList.toString().substring(1, keyList.toString().length() - 1).replace(" ", ""));
		}

		HashMap<Integer, String> hashMap = new HashMap<>();
		for (int i = 0; i < mainCount; i++) {
			hashMap.put((Integer) keyList.get(i), valueList.get(i).toString());
		}

		return hashMap;
	}
}
