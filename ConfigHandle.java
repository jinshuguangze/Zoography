package application;

import java.io.*;
import java.util.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class ConfigHandle {
	public ConfigHandle() {
	}


	public static HashMap setConfigInitialization(String fileName, String item)
			throws ClassNotFoundException, IOException {

		ArrayList<String> keys = new ArrayList<String>() {
			{
				add(null);
				add("ListViewItems");
				add("ListViewEventSequence");
			}
		};

		HashMap configLib = new HashMap();
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
		

		setConfigData(fileName, item, ((ArrayList<String>) configLib.get(item)).get(1));

		return configLib;

	}

	public static void setAllConfigInitialization(String fileName) throws ClassNotFoundException, IOException {
		
		HashMap configLib = setConfigInitialization(fileName, null);
		String fileString = Class.forName(new Throwable().getStackTrace()[0].getClassName()).getResource(fileName)
				.getFile();
		File file = new File(fileString);
		if(file.exists())file.delete();
		file.createNewFile();
		try (BufferedWriter aWriter = new BufferedWriter(new FileWriter(file, true))){
			aWriter.write("#"+fileName.substring(0,fileName.indexOf("."))+"\r\n");
			configLib.forEach((key,value)->{
				if(!(key==null)){
					ArrayList<String> Data=(ArrayList<String>)value;
					try {
						aWriter.write("\r\n");
						aWriter.write("#"+Data.get(0)+"(默认:"+Data.get(1)+")"+"\r\n");
						aWriter.write(key.toString()+"="+Data.get(1)+"\r\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} 						
	}


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


	public static int[] getConfigIntData(String fileName, String item) throws IOException, ClassNotFoundException {

		String[] Data = getConfigStringData(fileName, item);

		int[] NewData = new int[Data.length];

		for (int i = 0; i < Data.length; i++) {
			NewData[i] = Integer.parseInt(Data[i]);
		}

		return NewData;
	}


	public static void setConfigData(String fileName, String item, String modifyArray)
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
				aBuilder.append(modifyArray);
			}
		}
	}

}
