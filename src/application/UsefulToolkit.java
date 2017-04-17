package application;

import java.io.*;
import java.util.*;

import com.sun.javafx.css.Size;

import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import application.*;

public class UsefulToolkit {
	public static final String MAIN_CFG = Main.MAIN_CFG;

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
							fileName.substring(0, fileName.lastIndexOf(".")) + "_" + addonFileName + ".csv");
				}
			}
		}
	}

	/**
	 * A function of automatic fill interface 一个自动填充界面的函数
	 * 
	 * @param paneName
	 *            Container name
	 * @param fileName
	 *            Data file Name
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void autoFillInterface(TilePane paneName, String fileName) throws ClassNotFoundException, IOException {
		paneName.getChildren().clear();
		ArrayList<Button> buttonList = new ArrayList<>();		
		if (DataHandle.existData(fileName) && !DataHandle.finalData(fileName)) {
			int lineCount = DataHandle.getLineCount(fileName);

			String[][] data = DataHandle.getAllData(fileName);

			for (int i = 0; i < lineCount - 1; i++) {
				buttonList.add(new Button());

				Button aButton = buttonList.get(i);
				String name = data[i + 1][0];
				String localName=data[i+1][1];
				String image=data[i+1][2];
				String label=data[i+1][3];
				String profile=data[i+1][4];
				String introduce=data[i+1][5];

				buttonList.get(i).setPrefSize(200, 200);
				buttonList.get(i).setText(name+"\r\n"+localName);
				
				paneName.getChildren().add(buttonList.get(i));				
				aButton.setBackground(null);
				
				//创建一个新进程用于加载图片URL
				Runnable r=()->{
					aButton.setBackground(new Background(new BackgroundImage(
							new Image(image), null, null, null, new BackgroundSize(200, 200, true, true, true, true))));
				};
				Thread thread =new Thread(r);
				thread.start();
				
				Background defaultGround = aButton.getBackground();
				//添加鼠标进入事件
				aButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent m) -> {					
					aButton.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));					
				});
				
				//添加鼠标离开事件
				aButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent m) -> {
					aButton.setBackground(defaultGround);
				});
				
				//添加鼠标点击事件
				aButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent m) -> {
					if(m.isPrimaryButtonDown()){//左击
						try {
							autoFillInterface(paneName,
									fileName.substring(0, fileName.lastIndexOf(".")) + "_" + name + ".csv");
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					}
					else if(m.isSecondaryButtonDown()){//右击
						String dataFileName=fileName.substring(0, fileName.lastIndexOf("_"))+".csv";
						try {
							if(new File(DataHandle.getFilePath(dataFileName)).exists())
								autoFillInterface(paneName,dataFileName);						
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					}
				});				
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
			ConfigHandle.setConfigData(MAIN_CFG, "ListViewItems",
					valueList.toString().substring(1, valueList.toString().length() - 1).replace(" ", ""));
		} else if (mainCount < valueCount) {
			valueList = valueList.subList(0, mainCount);
			ConfigHandle.setConfigData(MAIN_CFG, "ListViewItems",
					valueList.toString().substring(1, valueList.toString().length() - 1).replace(" ", ""));
		}

		if (mainCount > keyCount) {
			int[] aIntArray = ConfigHandle.getConfigIntData(MAIN_CFG, "ListViewEventSequence");
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
			ConfigHandle.setConfigData(MAIN_CFG, "ListViewEventSequence",
					keyList.toString().substring(1, keyList.toString().length() - 1).replace(" ", ""));
		} else if (mainCount < keyCount) {
			keyList = keyList.subList(0, mainCount);
			ConfigHandle.setConfigData(MAIN_CFG, "ListViewEventSequence",
					keyList.toString().substring(1, keyList.toString().length() - 1).replace(" ", ""));
		}

		HashMap<Integer, String> hashMap = new HashMap<>();
		for (int i = 0; i < mainCount; i++) {
			hashMap.put((Integer) keyList.get(i), valueList.get(i).toString());
		}

		return hashMap;
	}
}
