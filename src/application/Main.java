package application;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import com.mysql.fabric.xmlrpc.base.Array;

import javafx.application.*;
import javafx.beans.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.util.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

public class Main extends Application {
	public static String LOGNAME_LOG;
	public static HashMap<Integer, String> FILENUMBER = new HashMap<>();
	public static final String ICON_PNG = "icon.png";
	public static final String MAIN_CFG = "Option.cfg";
	public static final String MAIN_FXML = "MainScene.fxml";
	public static final String MAIN_CSS = "application.css";
	public static final String LISTVIEW_CSS = "listview.css";
	public static final String BIOLOGY_CSV = "生物圈.csv";
	public static final String DATA_CODE = "UTF-8";
	public static final String CONFIG_CODE = "UTF-8";
	public static final String LOG_CODE = "UTF-8";
	
	/**
	 * getFileURL
	 * 
	 * @param relativePath
	 * @return file's URL
	 */
	public static URL getFileURL(String relativePath) {
		File file = new File(System.getProperty("user.dir") + relativePath);
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			String[] aastring=SqlHandle.readData(0);
			System.out.println(Arrays.toString(aastring));
			
			// 开启日志文件
			LOGNAME_LOG = (new SimpleDateFormat("yyyyMMdd_HH_mm_ss")).format(new Date())+".log";
			LogHandle.createNewLog(LOGNAME_LOG);
			
			// 创建配置文件(第一次启动时)
			File main_cfg = new File(ConfigHandle.getFilePath(MAIN_CFG));
			if (!main_cfg.exists()) {
				main_cfg.createNewFile();
				ConfigHandle.setAllConfigInitialization(MAIN_CFG);
			}
			
			// 给予图标
			primaryStage.getIcons().add(new Image(getFileURL("\\resource\\textures\\"+ICON_PNG).toString()));
			
			// 创建跟节点
			Parent root = FXMLLoader
					.load(getFileURL("\\resource\\fxml\\" + MAIN_FXML));

			// 初始化默认大小
			double screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
			double screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
			double defaultWidth = 0.75 * screenWidth;
			double defaultHeight = 0.75 * screenHeight;

			// 创建主场景及样式
			Scene scene = new Scene(root, defaultWidth, defaultHeight);
			scene.getStylesheets().add(getFileURL("\\resource\\css\\" + MAIN_CSS).toExternalForm());
			primaryStage.setScene(scene);
			
			//控件与容器的命名
			VBox Left=(VBox)root.lookup("#Left");			
			HBox Right=(HBox)root.lookup("#Right");
			
			ListView<ImageView> MainListView=(ListView<ImageView>) root.lookup("#MainListView");		
			Button Button_Inquiry=(Button)root.lookup("#Button_Inquiry");
			Button Botton_Return=(Button)root.lookup("#Botton_Return");
			TextField TextField_Information=(TextField)root.lookup("#TextField_Information");
			Button Button_Inquiry2=(Button)root.lookup("#Button_Inquiry2");
			Button Botton_Return2=(Button)root.lookup("#Botton_Return2");
			TextField TextField_Information2=(TextField)root.lookup("#TextField_Information2");
			
			StackPane HBox_Left = (StackPane) root.lookup("#HBox_Left");
			BorderPane HBox_Right=(BorderPane) root.lookup("#HBox_Right");
			
			ImageView Item1LeftFrame=(ImageView) root.lookup("#Item1LeftFrame");
			ImageView Item1RightFrame=(ImageView) root.lookup("#Item1RightFrame");
			ImageView Item1BottomFrame=(ImageView) root.lookup("#Item1BottomFrame");
			TilePane Item1CenterFrame=(TilePane) root.lookup("#Item1CenterFrame");
			StackPane Item1TopFrame=(StackPane) root.lookup("#Item1TopFrame");
			ImageView Item3LeftFrame=(ImageView) root.lookup("#Item3LeftFrame");
			ImageView Item3RightFrame=(ImageView) root.lookup("#Item3RightFrame");
			ImageView Item3BottomFrame=(ImageView) root.lookup("#Item3BottomFrame");
			TilePane Item3CenterFrame=(TilePane) root.lookup("#Item3CenterFrame");
			StackPane Item3TopFrame=(StackPane) root.lookup("#Item3TopFrame");
						
			ImageView Right_Bottom=(ImageView) root.lookup("#Right_Bottom");
			ImageView Right_Top=(ImageView) root.lookup("#Right_Top");			
			ImageView Right_Right=(ImageView) root.lookup("#Right_Right");
			VBox Right_Center=(VBox) root.lookup("#Right_Center");
			Label RightCenter_Title=(Label) root.lookup("#RightCenter_Title");
			ImageView RightCenter_ImageView=(ImageView) root.lookup("#RightCenter_ImageView");
			Label RightCenter_Label=(Label) root.lookup("#RightCenter_Label");
						
			// 读取配置文件及写进ListView
			String[] MainListViewItems = ConfigHandle.getConfigStringData(MAIN_CFG, "ListViewItems");
			ArrayList<ImageView> MainListViewImage = new ArrayList<>();

			for (int i = 0; i < MainListViewItems.length; i++) {
				ImageView aImageView = new ImageView(
						new Image(getFileURL("\\resource\\textures\\" + MainListViewItems[i] + ".png").toString()));
				aImageView.setFitWidth(252);
				aImageView.setFitHeight(80);

				MainListViewImage.add(aImageView);
			}
						
			ObservableList<ImageView> MainListView_ItemList = FXCollections.observableArrayList(MainListViewImage);			
			MainListView.setItems(MainListView_ItemList);
			MainListView.setPadding(new Insets(-1, -8, 0, -8));

			// ListView监听事件:选择改变贴图
			MainListView.getSelectionModel().selectedItemProperty().addListener(
					(ObservableValue<? extends ImageView> aObservable, ImageView oldValue, ImageView newValue) -> {
						if (oldValue != null) {
							String oldUrl = oldValue.getImage().impl_getUrl();
							String newUrl = oldUrl.replace("_按下", "");
							oldValue.setImage(new Image(newUrl));
						}

						if (newValue != null) {
							String oldUrl = newValue.getImage().impl_getUrl();
							String newUrl = oldUrl.substring(0, oldUrl.lastIndexOf(".")) + "_按下.png";
							newValue.setImage(new Image(newUrl));
						}
					});

			// 创建实用工具类的实例并自动生成数据文件
			UsefulToolkit aToolkit = new UsefulToolkit();
			aToolkit.autoCreateDataFile(BIOLOGY_CSV);
			
			// 给所有数据文件编号
			File dataFile=new File(getFileURL("\\resource\\data").toURI());
			int number=0;
			if(dataFile.exists()){
				File[] data=dataFile.listFiles();
				for (File file:data) {
					String fileName=file.getName();
					if(!file.isDirectory()
							&&!fileName.startsWith(".")
							&&fileName.endsWith(".csv")
							&&DataHandle.existData(fileName)){
						FILENUMBER.put(number, fileName);
						number++;
					}					
				}
			}
			
			// 读取配置文件确定并填充分层查看
			int[] ListViewEventSequence = ConfigHandle.getConfigIntData(MAIN_CFG, "ListViewEventSequence");			
			for (int i = 0; i < ListViewEventSequence.length; i++) {
				if (ListViewEventSequence[i] == 0) {
					BorderPane layeredView = (BorderPane) root.lookup("#MainListViewItem" + (i + 1));
					TilePane AutoFillPane = (TilePane) layeredView.getChildren().get(0);					
					aToolkit.autoFillInterface(AutoFillPane, BIOLOGY_CSV, 0, TextField_Information, RightCenter_Title,
							RightCenter_ImageView, RightCenter_Label);
				}
			}
						
			//属性绑定分层查看框架与中心区域			
			ImageView StackPane_Frame=(ImageView)Item1TopFrame.getChildren().get(0);					
			StackPane_Frame.minHeight(74.0);
			StackPane_Frame.maxHeight(74.0);
			StackPane_Frame.fitWidthProperty().bind(HBox_Left.widthProperty());
			
			Item1BottomFrame.minHeight(22.0);
			Item1BottomFrame.maxHeight(22.0);
			Item1BottomFrame.fitWidthProperty().bind(StackPane_Frame.fitWidthProperty());
			
			Item1LeftFrame.fitWidthProperty().bind(Item1BottomFrame.fitWidthProperty().multiply(11.0/510.0));
			Item1LeftFrame.fitHeightProperty().bind(HBox_Left.heightProperty().add(
					Item1TopFrame.heightProperty().negate()).add(Item1BottomFrame.fitHeightProperty().negate()));
			
			Item1RightFrame.fitWidthProperty().bind(Item1BottomFrame.fitWidthProperty().multiply(24.0/510.0));
			Item1RightFrame.fitHeightProperty().bind(Item1LeftFrame.fitHeightProperty());
			//-----------------------
			ImageView StackPane_Frame2=(ImageView)Item3TopFrame.getChildren().get(0);					
			StackPane_Frame2.minHeight(74.0);
			StackPane_Frame2.maxHeight(74.0);
			StackPane_Frame2.fitWidthProperty().bind(HBox_Left.widthProperty());
			
			Item3BottomFrame.minHeight(22.0);
			Item3BottomFrame.maxHeight(22.0);
			Item3BottomFrame.fitWidthProperty().bind(StackPane_Frame.fitWidthProperty());
			
			Item3LeftFrame.fitWidthProperty().bind(Item3BottomFrame.fitWidthProperty().multiply(11.0/510.0));
			Item3LeftFrame.fitHeightProperty().bind(HBox_Left.heightProperty().add(
					Item3TopFrame.heightProperty().negate()).add(Item3BottomFrame.fitHeightProperty().negate()));
			
			Item3RightFrame.fitWidthProperty().bind(Item3BottomFrame.fitWidthProperty().multiply(24.0/510.0));
			Item3RightFrame.fitHeightProperty().bind(Item3LeftFrame.fitHeightProperty());
			//-----------------------
			Right_Bottom.minHeight(13.0);
			Right_Bottom.maxHeight(13.0);			
			Right_Bottom.fitWidthProperty().bind(primaryStage.widthProperty()
					.add(Left.widthProperty().negate()).add(HBox_Left.widthProperty().negate()));
			
			Right_Top.minHeight(19.0);
			Right_Top.maxHeight(19.0);
			Right_Top.fitWidthProperty().bind(Right_Bottom.fitWidthProperty());
			
			Right_Right.fitWidthProperty().bind(Right_Bottom.fitWidthProperty().multiply(10.1/440.0));
			Right_Right.fitHeightProperty().bind(HBox_Right.heightProperty()
					.add(Right_Bottom.fitHeightProperty().negate()).add(Right_Top.fitHeightProperty().negate()));
		
			RightCenter_ImageView.fitWidthProperty().bind((Right_Bottom.fitWidthProperty()
					.add(Right_Right.fitWidthProperty().negate())).multiply(0.7));
			RightCenter_ImageView.fitHeightProperty().bind(primaryStage.heightProperty().multiply(0.5));
				
			
			// primaryStage.heightProperty().addListener((ov,t,t1)->{});
			
			//设置背景			
			Button_Inquiry.setBackground(new Background(new BackgroundImage(
					new Image(getFileURL("\\resouce\\texture\\Button_Inquiry.png").toString(), 24, 64, true, true, true),
					BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(24, 62, true, true, true, true))));
			
			Botton_Return.setBackground(new Background(new BackgroundImage(
					new Image(getFileURL("\\resouce\\texture\\Botton_Return.png").toString(), 115, 48, true, true, true),
					BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(115, 48, true, true, true, true))));
			
			TextField_Information.setBackground(new Background(new BackgroundImage(
					new Image(getFileURL("\\resouce\\texture\\MainFrame_Left_Top_Txt.png").toString(), 352, 37, true, true, true),
					BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(352, 37, true, true, true, true))));
			
			Button_Inquiry2.setBackground(new Background(new BackgroundImage(
					new Image(getFileURL("\\resouce\\texture\\Button_Inquiry.png").toString(), 24, 64, true, true, true),
					BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(24, 62, true, true, true, true))));
			
			Botton_Return2.setBackground(new Background(new BackgroundImage(
					new Image(getFileURL("\\resouce\\texture\\Botton_Return.png").toString(), 115, 48, true, true, true),
					BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(115, 48, true, true, true, true))));
			
			TextField_Information2.setBackground(new Background(new BackgroundImage(
					new Image(getFileURL("\\resouce\\texture\\MainFrame_Left_Top_Txt.png").toString(), 352, 37, true, true, true),
					BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(352, 37, true, true, true, true))));
			
			// 显示
			primaryStage.setFullScreen(true);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}