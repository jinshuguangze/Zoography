package application;

import java.awt.Stroke;
import java.io.*;
import java.util.*;
import application.*;
import jdk.internal.dynalink.beans.StaticClass;
import sun.print.resources.serviceui;

public class DataHandle {
	public static final String BIOLOGY_CSV = Main.BIOLOGY_CSV;
	
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
		ArrayList<String> sArrayList= new ArrayList<>();		
		String s = "";

		try (BufferedReader aReader = new BufferedReader(new FileReader(filePath))) {
			while ((s = aReader.readLine()) != null) {	
				String[] aStrings=s.split(",");
				for (String str : aStrings) {
					sArrayList.add(str);
				}
				System.out.println(sArrayList);
				tArrayList.add(sArrayList);				
			}
		}

		return tArrayList;		
	}
	
	/**
	 * 
	 * @param fileName
	 * @param line
	 * @param column
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static String getStringData(String fileName,int line,int column) throws ClassNotFoundException, IOException {
		return generalReader(fileName).get(line).get(column);
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static boolean existData(String fileName) throws ClassNotFoundException, IOException {
		boolean b=getStringData(fileName, 1, 0).equals("")?false:true;
		return b;
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static boolean finalData(String fileName) throws ClassNotFoundException, IOException {
		boolean b=getStringData(fileName, 0, 0).equals("")?true:false;
		return b;
	}	
	
	/**
	 * 
	 * @param fileName
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void createNewDataFile(String fileName) throws ClassNotFoundException, IOException {
		File file=new File(getFilePath(fileName));
		System.out.println(fileName);
		if(!file.exists()){
			file.createNewFile();
		}
		else{
			//UsefulToolkit toolkit=new UsefulToolkit();
			//toolkit.autoCreateDataFile(fileName);
		}
		
		String[] aStringArray=new String[getColumnCount(fileName)];		
		for(int i=0;i<aStringArray.length;i++){
			aStringArray[i-1]=getStringData(fileName, 0, i);
		}
		
		boolean b=false;
		
		for(Biology aBiology:Biology.values()){
			if(aBiology.toString().equals(aStringArray[0])){
				b=true;
			}
			else if (b) {
				aStringArray[0]=aBiology.toString();
			}
		}
		
		if(b){
			try (BufferedWriter aWriter = new BufferedWriter(new FileWriter(file, true))) {
				for(int i=0;i<aStringArray.length;i++){
					aWriter.write(aStringArray[i]);
					if(i>=aStringArray.length-1){
						aWriter.write("\r\n");
					}else {
						aWriter.write(",");
					}
				}
			}
		}				
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static int getLineCount(String fileName) throws ClassNotFoundException, IOException {
		ArrayList<ArrayList<String>> tArrayList = generalReader(fileName);
		return tArrayList.size();
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
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
		String filePath = System.getProperty("user.dir") + "\\resource\\data\\" + BIOLOGY_CSV;
		return filePath;
	}
}
