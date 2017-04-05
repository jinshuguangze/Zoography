package application;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class ConfigHandle {
	
    /**
     * 得到配置文件中的字符串数据
     * @param item 配置文件中的变量名
     * @param fileName 配置文件文件名
     * @return 变量的值,以字符串数组的形式返回
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String[] getConfigStringData(String item,String fileName) throws IOException, ClassNotFoundException {
    	
        //得到配置文件的绝对路径,利用Throwable的方法
    	String fileString=Class.forName(new Throwable().getStackTrace()[0].getClassName()).getResource(fileName).getFile();
    	
    	//初始化数据   
        ArrayList<String> tempString=new ArrayList<String>();    
        String s="";
        
        try (BufferedReader areader = new BufferedReader(new FileReader(fileString))){       	
            //逐行读取
            while ((s=areader.readLine())!=null){
            	if(!s.equals("")&&!s.startsWith("#")){
            		tempString.add(s);  
            	}     	         	
            }
        }
            
        //初始化变量
        int size=tempString.size();
        String[] array=null;
        
        //提取返回信息
        for (int i=0;i<size;i++) {
            String atempString=tempString.get(i);
			if (item.equals(atempString.substring(0,atempString.indexOf("=")))) {
				array=atempString.substring(atempString.indexOf("=")+1).split(",");
			}
		}
        
        //返回
        return array;
    }   
}
