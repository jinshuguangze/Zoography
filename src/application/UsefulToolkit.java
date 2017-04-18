package application;

import java.io.*;
import java.util.*;

import javax.swing.GroupLayout.Alignment;

import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.ImageInputBuilder;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
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
				//预处理
				buttonList.add(new Button());
				Button aButton = buttonList.get(i);
				aButton.setPrefSize(210, 210);
				aButton.setBackground(null);
				
				//读取属性				
				String name = data[i + 1][0];
				String localName=data[i+1][1];
				String image=data[i+1][2];
				String label=data[i+1][3];
				String profile=data[i+1][4];
				String introduce=data[i+1][5];	
				
				//创建一个新进程用于加载图片URL与字体颜色
				setButtonImage(aButton,image);
							
				//添加阴影
				InnerShadow aShadow=new InnerShadow();
				aShadow.setRadius(20.0);
				aButton.setEffect(aShadow);
								
				//添加字体
				aButton.setText(name+"\r\n"+localName);
				aButton.setTextAlignment(TextAlignment.CENTER);
				aButton.setFont(new Font(30.0).font("BankGothic Md BT",30.0));											
				
				//添加鼠标进入事件
				aButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent m) -> {					
					if(aButton.getBackground()!=null){
						Image imageHandle=new Image(image);
						int ImageWidth=(int)imageHandle.getWidth();
						int ImageHeight=(int)imageHandle.getHeight();
						WritableImage wImage= new WritableImage(ImageWidth,ImageHeight);
						PixelReader pixelReader = imageHandle.getPixelReader(); 			
						PixelWriter pixelWriter=wImage.getPixelWriter();
						
						for (int x = 0; x < ImageWidth; x++) {
							for (int y = 0; y < ImageHeight; y++) {
								 Color color=pixelReader.getColor(x, y);
								 color = color.darker().darker().darker(); 
								 pixelWriter.setColor(x, y, color);  
							}
						}
						aButton.setBackground(new Background(new BackgroundImage(
								wImage, null, null, null, new BackgroundSize(210, 210, true, true, true, true))));
						StringBuilder aStringBuilder=new StringBuilder(profile);
						for (int j = 11; j < profile.length(); j+=12) {
							aStringBuilder.insert(j, "\n");					
						}
						aButton.setText(aStringBuilder.toString());
						aButton.setFont(new Font(14.0).font("Comic Sans MS",14.0));
						aButton.setTextFill(Paint.valueOf("#C7D7E6"));
					}		
				});	

				
				
				//添加鼠标离开事件
				aButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent m) -> {
					setButtonImage(aButton,image);
					aButton.setText(name+"\r\n"+localName);
					aButton.setTextAlignment(TextAlignment.CENTER);
					aButton.setFont(new Font(30.0).font("BankGothic Md BT",30.0));	
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
				
				//添加按钮
				paneName.getChildren().add(aButton);
			}
		}
	}
	
	/**
	 * 
	 * @param aButton
	 * @param imageURL
	 */
	private static void setButtonImage(Button aButton,String imageURL) {
		Runnable r=()->{
			Image aImage=new Image(imageURL);
			aButton.setBackground(new Background(new BackgroundImage(
					aImage, null, null, null, new BackgroundSize(210, 210, true, true, true, true))));
			aButton.setTextFill(Paint.valueOf(getImageAnalysis(aImage)));
		};
		Thread thread =new Thread(r);
		thread.start();		
	}
		
	/**
	 * 
	 * @param aImage
	 * @return
	 */
	private static String getImageAnalysis(Image aImage) {
		int ImageWidth=(int)aImage.getWidth();
		int ImageHeight=(int)aImage.getHeight();
		WritableImage wImage= new WritableImage(ImageWidth,ImageHeight);
		PixelReader pixelReader = aImage.getPixelReader(); 
		PixelWriter pixelWriter=wImage.getPixelWriter();
		
		String fontColor="#C7D7E6";
		ArrayList<Double[]> colorArray=new ArrayList<>();
		if(aImage!=null){			
			double opacityCount=0.0;
			double redCount=0.0;
			double greenCount=0.0;
			double blueCount=0.0;
			for (int x = ImageWidth/4; x < ImageWidth*3/4; x++) {
				for (int y = ImageHeight/4; y < ImageHeight*3/4; y++) {
					Color color=pixelReader.getColor(x, y);
					Double[] arrayDouble={color.getRed(),color.getGreen(),color.getBlue()};							
					colorArray.add(arrayDouble);
				}
			}
			
			int s=colorArray.size();
			for (Double[] doubles : colorArray) {
				redCount+=doubles[0];
				greenCount+=doubles[1];
				blueCount+=doubles[2];
			}

			String red=Integer.toHexString(Math.round((float)(255.0-redCount/s*255)));
			String green=Integer.toHexString(Math.round((float)(255.0-greenCount/s*255)));
			String blue=Integer.toHexString(Math.round((float)(255.0-blueCount/s*255)));
			fontColor="#"+red+green+blue;	
		}
		
		return fontColor;
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
