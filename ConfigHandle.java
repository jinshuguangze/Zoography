package application;

import java.io.*;
import java.util.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class ConfigHandle {
	public ConfigHandle() {
	}

	/**
	 * ����ĳ�������ļ��������������б�
	 * 
	 * @param item
	 *            ��Ҫ����Ĭ��ֵ�������ļ�������
	 * @return ����һ�������������ñ����Ķ�̬����
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<String> setConfigInitialization(String fileName, String item)
			throws ClassNotFoundException, IOException {

		ArrayList<String> keys = new ArrayList<String>() {
			{
				add("");
				add("ListViewItems");
				add("ListViewEventSequence");
			}
		};

		HashMap configLib = new HashMap();
		int i = 0;
		// �������
		{
			configLib.put(keys.get(i), new ArrayList<String>() {
				{
					add("");
					add("");
					add("");
				}
			});
			i++;
		}
		;
		{
			configLib.put(keys.get(i), new ArrayList<String>() {
				{
					add("�б���Ŀ");
					add("�ֲ�鿴,����ģʽ,���ҳ��,��������");
					add("");
				}
			});
			i++;
		}
		;
		{
			configLib.put(keys.get(i), new ArrayList<String>() {
				{
					add("�б��¼�˳��");
					add("0,1,2,3,4");
					add("");
				}
			});
			i++;
		}
		;

		setConfigData(fileName, item, ((ArrayList<String>) configLib.get(item)).get(1));

		return keys;
	}

	public static void setAllConfigInitialization(String fileName) throws ClassNotFoundException, IOException {
		ArrayList<String> keys = setConfigInitialization(fileName, "");
		// δ���!
	}

	/**
	 * ͨ���ļ���ȡ
	 * 
	 * @param fileName
	 *            �����ļ��ļ���
	 * @param item
	 *            �����ļ��еı�����
	 * @return ��ȡ�ɹ�����ļ�,���ַ�����̬������ʽ����
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static ArrayList<String> generalReader(String fileName, String item)
			throws IOException, ClassNotFoundException {

		// �õ������ļ��ľ���·��,����Throwable�ķ���
		String fileString = Class.forName(new Throwable().getStackTrace()[0].getClassName()).getResource(fileName)
				.getFile();

		// ��ʼ������
		ArrayList<String> aArrayList = new ArrayList<String>();
		String s = "";

		// ���ж�ȡ,������StringBuilder
		try (BufferedReader aReader = new BufferedReader(new FileReader(fileString))) {
			while ((s = aReader.readLine()) != null) {
				if (!s.equals("") && !s.startsWith("#")) {
					aArrayList.add(s);
				}
			}
		}

		// ����
		return aArrayList;
	}

	/**
	 * �õ������ļ��е��ַ�������
	 * 
	 * @param fileName
	 *            �����ļ��ļ���
	 * @param item
	 *            �����ļ��еı�����
	 * @return ������ֵ,���ַ����������ʽ����
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static String[] getConfigStringData(String fileName, String item)
			throws IOException, ClassNotFoundException {

		// ��ʼ������
		ArrayList<String> aArrayList = generalReader(fileName, item);
		int size = aArrayList.size();
		String[] array = null;

		// ��ȡ������Ϣ
		for (int i = 0; i < size; i++) {
			String atempString = aArrayList.get(i);
			if (item.equals(atempString.substring(0, atempString.indexOf("=")))) {
				array = atempString.substring(atempString.indexOf("=") + 1).split(",");
			}
		}

		// ����
		return array;
	}

	/**
	 * �õ������ļ��е���������
	 * 
	 * @param fileName
	 *            �����ļ��ļ���
	 * @param item
	 *            �����ļ��еı�����
	 * @return ������ֵ,�������������ʽ����
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static int[] getConfigIntData(String fileName, String item) throws IOException, ClassNotFoundException {

		// �����ַ�����ȡ����
		String[] Data = getConfigStringData(fileName, item);

		// ���������鳤��
		int[] NewData = new int[Data.length];

		// ǿ��ת��Ϊ����
		for (int i = 0; i < Data.length; i++) {
			NewData[i] = Integer.parseInt(Data[i]);
		}

		// ����
		return NewData;
	}

	/**
	 * ����Ŀ�����õ�ֵ
	 * 
	 * @param fileName
	 *            �����ļ��ļ���
	 * @param item
	 *            �����ļ��еı�����
	 * @param modifyArray
	 *            ���ø��ĵ��ַ���
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void setConfigData(String fileName, String item, String modifyArray)
			throws IOException, ClassNotFoundException {

		// �õ������ļ��ľ���·��,����Throwable�ķ���
		String fileString = Class.forName(new Throwable().getStackTrace()[0].getClassName()).getResource(fileName)
				.getFile();
		StringBuilder aBuilder = new StringBuilder();

		// ���ж�ȡ,ʹ��StringBuilder
		try (BufferedReader aReader = new BufferedReader(new FileReader(fileString))) {
			while (!aReader.ready()) {
				aBuilder.append(aReader.readLine());
				aBuilder.append("\r\n");
			}
			int position = aBuilder.indexOf(item);

			// �滻
			if (position != -1) {
				aBuilder.delete(position, aBuilder.length());
				aBuilder.append(modifyArray);
			}
		}
	}

}
