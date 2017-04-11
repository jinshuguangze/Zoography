package application;

import java.util.*;
import javafx.application.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;


public class Main extends Application {
	

	@Override
	public void start(Stage primaryStage) {
		try {
			//创建跟节点
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			
			//初始化默认大小
			double screenWidth=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
			double screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;			
			double defaultWidth=0.75*screenWidth;
			double defaultHeight=0.75*screenHeight;
			
			//创建场景及样式
			Scene scene = new Scene(root,defaultWidth,defaultHeight);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			//primaryStage.initStyle(StageStyle.TRANSPARENT);
									
			//读取配置文件及写进ListView
			String[] ListViewItems=ConfigHandle.getConfigStringData("Option.cfg","ListViewItems");
			ListView Main_ListView1=(ListView)root.lookup("#Main_ListView1");
			ObservableList<String> Main_ListView1_Item =FXCollections.observableArrayList(ListViewItems);
			Main_ListView1.setItems(Main_ListView1_Item);
			
			//primaryStage.heightProperty().addListener((ov,t,t1)->{});
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}