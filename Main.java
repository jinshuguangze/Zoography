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
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			
			Scene scene = new Scene(root,1200,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());			
			
			ListView Main_BorderPane_VBox_ListView=(ListView)root.lookup("#Main_BorderPane_VBox_ListView");			
			ObservableList<String> Main_BorderPane_VBox_ListView_Item =FXCollections.observableArrayList ("分层查看", "搜索模式", "随机页面", "个性设置");	
			Main_BorderPane_VBox_ListView.setItems(Main_BorderPane_VBox_ListView_Item);			
								
			primaryStage.heightProperty().addListener((ov,t,t1)->{
				
			});
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
