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
import javafx.util.StringConverter;
import application.*;

public class UsefulToolkit {
	public static final String MAIN_CFG = Main.MAIN_CFG;
	public static final String LOGNAME_LOG = Main.LOGNAME_LOG;
	public static final String BIOLOGY_CSV = Main.BIOLOGY_CSV;

	/**
	 * A function of automatic create the rest data file 一个生成剩余数据文件的函数
	 * 
	 * @param fileName
	 *            Data file Name
	 * @throws ClassNotFoundException If fileName not found
	 * @throws IOException If IO connection failed
	 */
	public void autoCreateDataFile(String fileName) throws ClassNotFoundException, IOException {
		ArrayList<ArrayList<String>> arrayData = DataHandle.generalReader(fileName);
		if (!DataHandle.finalData(fileName)) {
			if (DataHandle.existData(fileName)) {
				for (int i = 1; i < DataHandle.getLineCount(fileName); i++) {
					String addonFileName = DataHandle.getStringData(fileName, i, 1);
					String newfileName=fileName.substring(0, fileName.lastIndexOf(".")) + "_" + addonFileName + ".csv";
					DataHandle.createNewDataFile(newfileName);
					LogHandle.writeLog(LOGNAME_LOG, 
							new Throwable().getStackTrace()[0].getMethodName() +":"+newfileName);
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
	 * @param fromNumber
	 *            From which number of button
	 * @param textName
	 *            Text Name
	 * @param titleName
	 *            Title Name
	 * @param imageName
	 *            Image Name
	 * @param labelName
	 *            Label Name
	 * @throws ClassNotFoundException
	 *             If fileName not found
	 * @throws IOException
	 *             If IO connection failed
	 */
	public void autoFillInterface(TilePane paneName, String fileName, int fromNumber, TextField textName,
			Label titleName, ImageView imageName, Label labelName) throws ClassNotFoundException, IOException {

		paneName.getChildren().clear();
		ArrayList<Button> buttonList = new ArrayList<>();
		if (DataHandle.existData(fileName) && !DataHandle.finalData(fileName)) {
			int lineCount = DataHandle.getLineCount(fileName);
			String[][] data = DataHandle.getAllData(fileName);
			int lastNumber = fromNumber;
			if (fromNumber > lineCount - 2) {
				lastNumber = 0;
			} else if (fromNumber < 0) {
				lastNumber = lineCount - 2;
			}

			for (int i = lastNumber; i < lineCount - 1; i++) {
				// 预处理
				int forLambda = i;
				Button aButton = new Button();
				buttonList.add(aButton);
				aButton.setPrefSize(210, 210);
				aButton.setBackground(null);

				// 写进日志文件
				LogHandle.writeLog(LOGNAME_LOG, "autoFillInterface" + ":" + fileName);

				// 读取属性
				String name = data[i + 1][0];
				String localName = data[i + 1][1];
				String image = data[i + 1][2];
				String label = data[i + 1][3];
				String profile = data[i + 1][4];
				String introduce = data[i + 1][5];

				// 添加阴影
				InnerShadow aShadow = new InnerShadow();
				aShadow.setRadius(20.0);
				aButton.setEffect(aShadow);

				// 添加字体
				aButton.setText((i + 1) + "\r\n" + name + "\r\n" + localName);
				int length = (name.length() - localName.length() > 0) ? name.length() : localName.length();
				double fontSize = 35.0 - length;

				aButton.setTextAlignment(TextAlignment.CENTER);
				aButton.setFont(new Font(fontSize).font("BankGothic Md BT", FontWeight.EXTRA_BOLD, fontSize));

				// 设置标题栏
				textName.setText(fileName.substring(0, fileName.lastIndexOf(".")).replace("_", ">"));

				// 添加详细信息以及初始大图
				if (titleName.getText() == "" || titleName.getText() == null) {
					titleName.setText("生物圈(Biosphere)");
					imageName.setImage(new Image(
							"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492677832108&di=4c050a5e4173d05ddc98b2eff2060887&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F130912%2F240385-1309120JA053.jpg"));
					labelName.setText(
							"	生物圈（Biosphere）是指地球上所有生态系统的统合整体，是地球的一个外层圈，其范围大约为海平面上下垂直约10公里。它包括地球上有生命存在和由生命过程变化和转变的空气、陆地、岩石圈和水。从地质学的广义角度上来看生物圈是结合所有生物以及它们之间的关系的全球性的生态系统，包括生物与岩石圈、水圈和空气的相互作用。生物圈是一个封闭且能自我调控的系统。地球是整个宇宙中唯一已知的有生物生存的地方。一般认为生物圈是从35亿年前生命起源后演化而来。");
				}

				// 添加鼠标进入事件
				aButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent m) -> {
					if (aButton.getBackground() != null) {
						Image imageHandle = new Image(image);
						int ImageWidth = (int) imageHandle.getWidth();
						int ImageHeight = (int) imageHandle.getHeight();
						WritableImage wImage = new WritableImage(ImageWidth, ImageHeight);
						PixelReader pixelReader = imageHandle.getPixelReader();
						PixelWriter pixelWriter = wImage.getPixelWriter();

						for (int x = 0; x < ImageWidth; x++) {
							for (int y = 0; y < ImageHeight; y++) {
								Color color = pixelReader.getColor(x, y);
								color = color.darker().darker().darker();
								pixelWriter.setColor(x, y, color);
							}
						}
						aButton.setBackground(new Background(new BackgroundImage(wImage, null, null, null,
								new BackgroundSize(210, 210, true, true, true, true))));
						StringBuilder aStringBuilder = new StringBuilder(profile);
						for (int j = 11; j < profile.length(); j += 12) {
							aStringBuilder.insert(j, "\n");
						}
						aButton.setText(aStringBuilder.toString());
						aButton.setFont(new Font(14.0).font("Comic Sans MS", 14.0));
						aButton.setTextFill(Paint.valueOf("#C7D7E6"));
					}
				});

				// 添加鼠标离开事件
				aButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent m) -> {
					setButtonImage(aButton, image);
					aButton.setText((forLambda + 1) + "\r\n" + name + "\r\n" + localName);
					aButton.setTextAlignment(TextAlignment.CENTER);
					aButton.setFont(new Font(fontSize).font("BankGothic Md BT", FontWeight.EXTRA_BOLD, fontSize));
				});

				// 添加鼠标点击事件
				aButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent m) -> {
					if (m.isPrimaryButtonDown()) {// 左击
						try {
							String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + localName
									+ ".csv";
							autoFillInterface(paneName, newFileName, 0, textName, titleName, imageName, labelName);
							titleName.setText(localName + " " + name);
							imageName.setImage(new Image(image));
							labelName.setText("	" + introduce);
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					} else if (m.isSecondaryButtonDown()) {// 右击
						int aint = fileName.lastIndexOf("_");
						String newFileName = (aint != -1) ? fileName.substring(0, aint) + ".csv" : "BIOLOGY_CSV";
						try {
							if (new File(DataHandle.getFilePath(newFileName)).exists()) {
								autoFillInterface(paneName, newFileName, 0, textName, titleName, imageName, labelName);
							}
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					}
				});

				// 添加按钮

				paneName.getChildren().add(aButton);

			}
			// 创建一个新进程用于加载图片URL与字体颜色
			String[] image = new String[lineCount - 1 - lastNumber];
			for (int i = lastNumber; i < lineCount - 1; i++) {
				image[i - lastNumber] = DataHandle.getStringData(fileName, i + 1, 2);
			}

			setButtonImage(buttonList, image);
		}
	}
	
	/**
	 * A function of asynchronous processing button background image and font
	 * color 一个异步处理按钮背景图片和字体颜色样式的函数
	 * 
	 * @param buttons
	 *            Handle buttons
	 * @param imageURL[]
	 *            Image URLs
	 */
	private static void setButtonImage(ArrayList<Button> buttons, String[] imageURL) {
		int size=buttons.size();
		
		if(buttons.size()==imageURL.length){
			Runnable r1 = () -> {
					for (int i = 0; i < buttons.size()/3; i++) {
						Image aImage = new Image(imageURL[i]);
						buttons.get(i).setBackground(new Background(new BackgroundImage(aImage, BackgroundRepeat.ROUND,
								BackgroundRepeat.ROUND, null, new BackgroundSize(210, 210, true, true, true, true))));
						buttons.get(i).setTextFill(Paint.valueOf(getImageAnalysis(aImage)));
					}
				
			};
			
			Runnable r2 = () -> {

					for (int i = buttons.size()/3; i < buttons.size()*2/3; i++) {
						Image aImage = new Image(imageURL[i]);
						buttons.get(i).setBackground(new Background(new BackgroundImage(aImage, BackgroundRepeat.ROUND,
								BackgroundRepeat.ROUND, null, new BackgroundSize(210, 210, true, true, true, true))));
						buttons.get(i).setTextFill(Paint.valueOf(getImageAnalysis(aImage)));
					}
				
			};
			
			Runnable r3 = () -> {

					for (int i = buttons.size()*2/3; i < buttons.size(); i++) {
						Image aImage = new Image(imageURL[i]);
						buttons.get(i).setBackground(new Background(new BackgroundImage(aImage, BackgroundRepeat.ROUND,
								BackgroundRepeat.ROUND, null, new BackgroundSize(210, 210, true, true, true, true))));
						buttons.get(i).setTextFill(Paint.valueOf(getImageAnalysis(aImage)));
					}
				
			};

			Thread thread1 = new Thread(r1);
			Thread thread2 = new Thread(r2);
			Thread thread3 = new Thread(r3);
			thread1.start();
			thread2.start();
			thread3.start();
		}
	}
	
	/**
	 * A function of asynchronous processing button background image and font
	 * color 一个异步处理按钮背景图片和字体颜色样式的函数
	 * 
	 * @param aButton
	 *            Handle button
	 * @param imageURL
	 *            Image URL
	 */
	private static void setButtonImage(Button aButton, String imageURL) {
		Runnable r = () -> {
			Image aImage = new Image(imageURL);
			aButton.setBackground(new Background(new BackgroundImage(aImage, BackgroundRepeat.ROUND,
					BackgroundRepeat.ROUND, null, new BackgroundSize(210, 210, true, true, true, true))));
			aButton.setTextFill(Paint.valueOf(getImageAnalysis(aImage)));
		};

		Thread thread = new Thread(r);
		thread.start();
	}
		
	/**
	 * A function of the average color picture analysis of core region and gives
	 * the color RGB string 一个分析图片核心区域的平均颜色并给出反色RGB字符串的函数
	 * 
	 * @param aImage
	 *            Analytical image
	 * @return The color of the string
	 */
	private static String getImageAnalysis(Image aImage) {
		int ImageWidth = (int) aImage.getWidth();
		int ImageHeight = (int) aImage.getHeight();
		//System.out.println(ImageWidth+"   "+ImageHeight+"    "+aImage.impl_getUrl());
		WritableImage wImage = new WritableImage(ImageWidth, ImageHeight);
		PixelReader pixelReader = aImage.getPixelReader();
		PixelWriter pixelWriter = wImage.getPixelWriter();

		String fontColor = "#C7D7E6";
		ArrayList<Double[]> colorArray = new ArrayList<>();
		if (aImage != null) {
			double opacityCount = 0.0;
			double redCount = 0.0;
			double greenCount = 0.0;
			double blueCount = 0.0;
			for (int x = ImageWidth / 4; x < ImageWidth * 3 / 4; x++) {
				for (int y = ImageHeight / 4; y < ImageHeight * 3 / 4; y++) {
					Color color = pixelReader.getColor(x, y);
					Double[] arrayDouble = { color.getRed(), color.getGreen(), color.getBlue() };
					colorArray.add(arrayDouble);
				}
			}

			int s = colorArray.size();
			for (Double[] doubles : colorArray) {
				redCount += doubles[0];
				greenCount += doubles[1];
				blueCount += doubles[2];
			}

			double red = 255.0 - redCount / s * 255.0;
			double green = 255.0 - greenCount / s * 255.0;
			double blue = 255.0 - blueCount / s * 255.0;
			if (!(red < 140.0 && red > 116.0 && green < 140.0 && green > 116.0 && blue < 140.0 && blue > 116.0)) {
				String redStr = Integer.toHexString(Math.round((float) red));
				String greenStr = Integer.toHexString(Math.round((float) green));
				String blueStr = Integer.toHexString(Math.round((float) blue));
				fontColor = "#" + redStr + greenStr + blueStr;
			}
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
	 *             If fileName not found
	 * @throws IOException
	 *             If IO connection failed If IO connection failed
	 */
	public HashMap<Integer, String> autoItemCountConformity(List<?> mainList, List<String> valueList,
			List<Integer> keyList) throws ClassNotFoundException, IOException {

		int mainCount = mainList.size();
		int valueCount = valueList.size();
		int keyCount = keyList.size();

		if (mainCount > valueCount) {
			List<?> oldList = valueList;

			for (int i = 0; i < mainCount - valueCount; i++) {
				valueList.add("新建选项" + (i + 1));
			}

			ConfigHandle.setConfigData(MAIN_CFG, "ListViewItems",
					valueList.toString().substring(1, valueList.toString().length() - 1).replace(" ", ""));
			LogHandle.writeLog(LOGNAME_LOG,
					new Throwable().getStackTrace()[0].getMethodName() + ":" + oldList + "->" + valueList);
		} else if (mainCount < valueCount) {
			List<?> oldList = valueList;

			valueList = valueList.subList(0, mainCount);

			ConfigHandle.setConfigData(MAIN_CFG, "ListViewItems",
					valueList.toString().substring(1, valueList.toString().length() - 1).replace(" ", ""));
			LogHandle.writeLog(LOGNAME_LOG,
					new Throwable().getStackTrace()[0].getMethodName() + ":" + oldList + "->" + valueList);
		}

		if (mainCount > keyCount) {
			List<?> oldList = keyList;

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
			LogHandle.writeLog(LOGNAME_LOG,
					new Throwable().getStackTrace()[0].getMethodName() + ":" + oldList + "->" + keyList);
		} else if (mainCount < keyCount) {
			List<?> oldList = keyList;

			keyList = keyList.subList(0, mainCount);

			ConfigHandle.setConfigData(MAIN_CFG, "ListViewEventSequence",
					keyList.toString().substring(1, keyList.toString().length() - 1).replace(" ", ""));
			LogHandle.writeLog(LOGNAME_LOG,
					new Throwable().getStackTrace()[0].getMethodName() + ":" + oldList + "->" + keyList);
		}

		HashMap<Integer, String> hashMap = new HashMap<>();
		for (int i = 0; i < mainCount; i++) {
			hashMap.put((Integer) keyList.get(i), valueList.get(i).toString());
		}

		return hashMap;
	}
}
