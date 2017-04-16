package application;

import java.io.*;
import java.util.*;
import javafx.application.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class Main extends Application {
	public static final String MAIN_CFG = "Option.cfg";
	public static final String MAIN_FXML = "MainScene.fxml";
	public static final String MAIN_CSS = "application.css";
	public static final String LISTVIEW_CSS = "listview.css";
	public static final String BIOLOGY_CSV = "Biology.csv";
	public static final String DATA_CODE = "utf-8";
	public static final String CONFIG_CODE = "utf-8";
	
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
					.load(new File(System.getProperty("user.dir") + "\\resource\\fxml\\" + MAIN_FXML).toURI().toURL());

			// 初始化默认大小
			double screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
			double screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
			double defaultWidth = 0.75 * screenWidth;
			double defaultHeight = 0.75 * screenHeight;

			// 创建主场景及样式
			Scene scene = new Scene(root, defaultWidth, defaultHeight);
			scene.getStylesheets().add(new File(System.getProperty("user.dir") + "\\resource\\css\\" + MAIN_CSS).toURI()
					.toURL().toExternalForm());
			primaryStage.setScene(scene);

			// 读取配置文件及写进ListView
			String[] ListViewItems = ConfigHandle.getConfigStringData(MAIN_CFG, "ListViewItems");
			ArrayList<ImageView> ListViewItemsImage = new ArrayList<>();

			for (int i = 0; i < ListViewItems.length; i++) {
				ImageView aImageView = new ImageView(
						new File(System.getProperty("user.dir") + "\\resource\\textures\\" + ListViewItems[i] + ".png")
								.toURI().toURL().toString());
				aImageView.setFitWidth(252);
				aImageView.setFitHeight(80);

				ListViewItemsImage.add(aImageView);
			}

			ListView<ImageView> Main_ListView1 = (ListView) root.lookup("#Main_ListView1");
			ObservableList<ImageView> Main_ListView1_Item = FXCollections.observableArrayList(ListViewItemsImage);

			Main_ListView1.setItems(Main_ListView1_Item);
			Main_ListView1.setPadding(new Insets(-1, -8, 0, -8));

			// ListView监听事件:选择改变贴图
			Main_ListView1.getSelectionModel().selectedItemProperty().addListener(
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

			// 读取配置文件确定并填充1号ScrollPane
			int[] ListViewEventSequence = ConfigHandle.getConfigIntData(MAIN_CFG, "ListViewEventSequence");
			for (int i = 0; i < ListViewEventSequence.length; i++) {
				if (ListViewEventSequence[i] == 0) {
					ScrollPane Main_ScrollPane1 = (ScrollPane) root.lookup("#Main_ScrollPane" + (i + 1));
					TilePane Main_TilePane1 = (TilePane) Main_ScrollPane1.getContent();
					aToolkit.autoFillInterface(Main_TilePane1, BIOLOGY_CSV);
				}
			}

			// primaryStage.heightProperty().addListener((ov,t,t1)->{});

			// 显示
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}