package application;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class ConfigHandle {
	
    /**
     * �õ������ļ��е��ַ�������
     * @param item �����ļ��еı�����
     * @param fileName �����ļ��ļ���
     * @return ������ֵ,���ַ����������ʽ����
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String[] getConfigStringData(String item,String fileName) throws IOException, ClassNotFoundException {
    	
        //�õ������ļ��ľ���·��,����Throwable�ķ���
    	String fileString=Class.forName(new Throwable().getStackTrace()[0].getClassName()).getResource(fileName).getFile();
    	
    	//��ʼ������   
        ArrayList<String> tempString=new ArrayList<String>();    
        String s="";
        
        try (BufferedReader areader = new BufferedReader(new FileReader(fileString))){       	
            //���ж�ȡ
            while ((s=areader.readLine())!=null){
            	if(!s.equals("")&&!s.startsWith("#")){
            		tempString.add(s);  
            	}     	         	
            }
        }
            
        //��ʼ������
        int size=tempString.size();
        String[] array=null;
        
        //��ȡ������Ϣ
        for (int i=0;i<size;i++) {
            String atempString=tempString.get(i);
			if (item.equals(atempString.substring(0,atempString.indexOf("=")))) {
				array=atempString.substring(atempString.indexOf("=")+1).split(",");
			}
		}
        
        //����
        return array;
    }   
}
