package application;
	
import javafx.application.*;
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
			
			ListView Main_Left_Bottom_ListView=(ListView)root.lookup("#Main_Left_Bottom_ListView");
			ObservableList<String> Main_Left_Bottom_ListView_Item =FXCollections.observableArrayList ("�ֲ�鿴", "����ģʽ", "���ҳ��", "��������");
			Main_Left_Bottom_ListView.setItems(Main_Left_Bottom_ListView_Item);
			
			Scene scene = new Scene(root,1200,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
			
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
