package application;
	
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
			//创建根节点
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			
			//读取屏幕大小
			double screenWidth=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
			double screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;			
			double defaultWidth=0.75*screenWidth;
			double defaultHeight=0.75*screenHeight;
			
			//添加样式
			Scene scene = new Scene(root,defaultWidth,defaultHeight);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			//primaryStage.initStyle(StageStyle.TRANSPARENT);
						
			//左侧ListView加标签
			ListView Main_BorderPane_VBox_ListView=(ListView)root.lookup("#Main_BorderPane_VBox_ListView");			
			ObservableList<String> Main_BorderPane_VBox_ListView_Item =FXCollections.observableArrayList ("分层查看", "搜索模式", "随机页面", "个性设置");	
			Main_BorderPane_VBox_ListView.setItems(Main_BorderPane_VBox_ListView_Item);			
								
			//primaryStage.heightProperty().addListener((ov,t,t1)->{});
			
			//显示
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
