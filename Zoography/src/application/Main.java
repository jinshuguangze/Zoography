package application;

import java.io.*;
import java.util.*;
import javafx.application.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;

public class Main extends Application {
	public static final String MAIN_CFG = "Option.cfg";
	public static final String MAIN_FXML = "MainScene.fxml";
	public static final String MAIN_CSS = "application.css";
	public static final String LISTVIEW_CSS = "listview.css";

	@Override
	public void start(Stage primaryStage) {
		try {

			// 创建配置文件(第一次启动时)
			File file = new File(System.getProperty("user.dir") + "\\config\\" + MAIN_CFG);
			if (!file.exists()) {
				file.createNewFile();
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
			String[] ListViewItems = ConfigHandle.getConfigStringData("Option.cfg", "ListViewItems");
			ListView Main_ListView1 = (ListView) root.lookup("#Main_ListView1");
			ObservableList<String> Main_ListView1_Item = FXCollections.observableArrayList(ListViewItems);
			Main_ListView1.setItems(Main_ListView1_Item);

			// 读取ListView样式
			Main_ListView1.getStylesheets()
					.add(new File(System.getProperty("user.dir") + "\\resource\\css\\" + LISTVIEW_CSS).toURI().toURL()
							.toExternalForm());

			// primaryStage.heightProperty().addListener((ov,t,t1)->{});

			// 显示
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}