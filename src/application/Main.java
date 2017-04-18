package application;

import java.io.*;
import java.net.*;
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
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

public class Main extends Application {
	public static final String MAIN_CFG = "Option.cfg";
	public static final String MAIN_FXML = "MainScene.fxml";
	public static final String MAIN_CSS = "application.css";
	public static final String LISTVIEW_CSS = "listview.css";
	public static final String BIOLOGY_CSV = "Biology.csv";
	public static final String DATA_CODE = "UTF-8";
	public static final String CONFIG_CODE = "UTF-8";
	
	public static URL getFileURL(String relativePath) {
		File file=new File(System.getProperty("user.dir")+relativePath);
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
			
			ListView<ImageView> MainListView=(ListView<ImageView>) root.lookup("#MainListView");
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
			
			// 读取配置文件确定并填充分层查看
			int[] ListViewEventSequence = ConfigHandle.getConfigIntData(MAIN_CFG, "ListViewEventSequence");			
			for (int i = 0; i < ListViewEventSequence.length; i++) {
				if (ListViewEventSequence[i] == 0) {
					BorderPane layeredView = (BorderPane) root.lookup("#MainListViewItem" + (i + 1));
					TilePane AutoFillPane = (TilePane) layeredView.getChildren().get(0);					
					aToolkit.autoFillInterface(AutoFillPane, BIOLOGY_CSV);
				}
			}
	
			//属性绑定分层查看框架与中心区域			
			StackPane HBox_Left = (StackPane) root.lookup("#HBox_Left");
			TilePane Item1CenterFrame=(TilePane) root.lookup("#Item1CenterFrame");
			StackPane Item1TopFrame=(StackPane) root.lookup("#Item1TopFrame");
			ImageView StackPane_Frame=(ImageView)Item1TopFrame.getChildren().get(0);
			ImageView Item1LeftFrame=(ImageView) root.lookup("#Item1LeftFrame");
			ImageView Item1RightFrame=(ImageView) root.lookup("#Item1RightFrame");
			ImageView Item1BottomFrame=(ImageView) root.lookup("#Item1BottomFrame");
						
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
			
			// primaryStage.heightProperty().addListener((ov,t,t1)->{});

			// 显示
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}