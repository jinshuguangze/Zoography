package application;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
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
	public static final String MAIN_CFG = "Option.cfg";
	public static final String MAIN_FXML = "MainScene.fxml";
	public static final String MAIN_CSS = "application.css";
	public static final String LISTVIEW_CSS = "listview.css";
	public static final String BIOLOGY_CSV = "生物圈.csv";
	public static final String DATA_CODE = "UTF-8";
	public static final String CONFIG_CODE = "UTF-8";
	public static final String LOG_CODE = "UTF-8";
	
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
			// 开启日志文件
			LOGNAME_LOG = (new SimpleDateFormat("yyyyMMdd_HH_mm_ss")).format(new Date())+".log";
			LogHandle.createNewLog(LOGNAME_LOG);
			
			// 创建配置文件(第一次启动时)
			File main_cfg = new File(ConfigHandle.getFilePath(MAIN_CFG));
			if (!main_cfg.exists()) {
				main_cfg.createNewFile();
				ConfigHandle.setAllConfigInitialization(MAIN_CFG);
			}

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
			ListView<ImageView> MainListView=(ListView<ImageView>) root.lookup("#MainListView");
			Button Button_Inquiry=(Button)root.lookup("#Button_Inquiry");
			Button Botton_Return=(Button)root.lookup("#Botton_Return");
			TextField TextField_Information=(TextField)root.lookup("#TextField_Information");
			
			HBox Right=(HBox)root.lookup("#Right");
			
			ImageView Item1LeftFrame=(ImageView) root.lookup("#Item1LeftFrame");
			ImageView Item1RightFrame=(ImageView) root.lookup("#Item1RightFrame");
			ImageView Item1BottomFrame=(ImageView) root.lookup("#Item1BottomFrame");
			StackPane HBox_Left = (StackPane) root.lookup("#HBox_Left");
			TilePane Item1CenterFrame=(TilePane) root.lookup("#Item1CenterFrame");
			StackPane Item1TopFrame=(StackPane) root.lookup("#Item1TopFrame");
			
			BorderPane HBox_Right=(BorderPane) root.lookup("#HBox_Right");
			
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
					aToolkit.autoFillInterface(AutoFillPane, BIOLOGY_CSV, TextField_Information);
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
			Item1LeftFrame.fitHeightProperty().bind(HBox_Left.heightProperty().add(Item1TopFrame.heightProperty().negate()).add(Item1BottomFrame.fitHeightProperty().negate()));
			
			Item1RightFrame.fitWidthProperty().bind(Item1BottomFrame.fitWidthProperty().multiply(24.0/510.0));
			Item1RightFrame.fitHeightProperty().bind(Item1LeftFrame.fitHeightProperty());			
			
			
		/*	Right_Bottom.minHeight(14.0);
			Right_Bottom.maxHeight(14.0);
			Right_Bottom.fitWidthProperty().bind(Right.widthProperty());
			
			Right_Top.minHeight(20.0);
			Right_Top.maxHeight(20.0);
			Right_Top.fitWidthProperty().bind(Right_Bottom.fitWidthProperty());
			
			Right_Right.fitWidthProperty().bind(Right_Bottom.fitWidthProperty().multiply(10.0/440.0));
			Right_Right.fitHeightProperty().bind(HBox_Right.heightProperty().add(Right_Bottom.fitHeightProperty().negate()).add(Right_Top.fitHeightProperty().negate()));
		*/
			
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
			
			BorderImage dasdas=new BorderImage(new Image("http://p8.qhmsg.com/t01fe4f5744f3e9305b.jpg")
					, BorderWidths.FULL, new Insets(0, 0, 0, 0), BorderWidths.FULL, true, BorderRepeat.ROUND, BorderRepeat.ROUND);
			HBox_Right.setBorder(new Border(dasdas));
			System.out.println(dasdas);
			// 显示
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}