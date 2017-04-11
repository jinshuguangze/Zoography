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
	 *            List as the key of HashMap(Generic integer)
	 * @return A HashMap that stores an automatically formatted valueList
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	protected HashMap<Integer,String> autoItemCountConformity(List<?> mainList, List<String> valueList, List<Integer> keyList)
			throws ClassNotFoundException, IOException {

		int mainCount = mainList.size();
		int valueCount = valueList.size();
		int keyCount = keyList.size();

		if (mainCount > valueCount) {
			String[] aIntArray=ConfigHandle.getConfigStringData("Option.cfg", "ListViewItems");
			String[] AnotherArray=Arrays.copyOf(aIntArray, mainCount);
			for(int i=0;i<mainCount-valueCount;i++){
				valueList.add("新建选项");		
			}				
			ConfigHandle.setConfigData("Option.cfg", "ListViewItems", valueList.toString().substring(1, valueList.toString().length()-1));
			System.out.println("1  "+valueList);
		} 
		else if (mainCount < valueCount) {
			valueList = valueList.subList(0, mainCount);
			ConfigHandle.setConfigData("Option.cfg", "ListViewItems", valueList.toString().substring(1, valueList.toString().length()-1));
			System.out.println("2  "+valueList);
		}

		if (mainCount > keyCount) {
			int[] aIntArray=ConfigHandle.getConfigIntData("Option.cfg", "ListViewEventSequence");
			int[] AnotherArray=Arrays.copyOf(aIntArray, mainCount);
			Arrays.sort(aIntArray);
			int intMax=aIntArray[aIntArray.length-1];
			for(int i=0;i<mainCount-keyCount;i++){
				keyList.add(intMax+i+1);
			}			
			ConfigHandle.setConfigData("Option.cfg", "ListViewEventSequence", keyList.toString().substring(1, keyList.toString().length()-1));
			System.out.println("3  "+keyList);
		} 
		else if (mainCount < keyCount) {
			keyList = keyList.subList(0, mainCount);
			ConfigHandle.setConfigData("Option.cfg", "ListViewEventSequence", keyList.toString().substring(1, keyList.toString().length()-1));
			System.out.println("4  "+keyList);
		}

		HashMap<Integer,String> hashMap = new HashMap<>();
		for (int i = 0; i < mainCount; i++) {
			hashMap.put((Integer)keyList.get(i), valueList.get(i).toString());
		}

		return hashMap;
	}
}
