package application;

import java.io.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AutoHandle {

	/**
	 * A function of automatic formatting length 一个自动格式化长度的函数
	 * 
	 * @param mainList
	 *            List as a reference length
	 * @param valueList
	 *            List as the value of HashMap(Generic string)
	 * @param keyList
	 *            List as the key of HashMap(Generic int)
	 * @return A HashMap that stores an automatically formatted valueList
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	protected HashMap autoItemCountConformity(List mainList, List valueList, List keyList)
			throws ClassNotFoundException, IOException {

		int mainCount = mainList.size();
		int valueCount = valueList.size();
		int keyCount = keyList.size();

		if (mainCount > valueCount) {
			ConfigHandle.setConfigInitialization("Option.cfg", "ListViewItems");
		} else if (mainCount < valueCount) {
			valueList = valueList.subList(0, mainCount);
		}

		if (mainCount > keyCount) {
			ConfigHandle.setConfigInitialization("Option.cfg", "ListViewEventSequence");
		} else if (mainCount < keyCount) {
			keyList = keyList.subList(0, mainCount);
		}

		HashMap hashMap = new HashMap();
		for (int i = 0; i < mainCount; i++) {
			hashMap.put(keyList.get(i), valueList.get(i));
		}

		return hashMap;
	}
}
