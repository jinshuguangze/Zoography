package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private BorderPane Main_BorderPane;

    @FXML
    private VBox Main_BorderPane_VBox;

    @FXML
    private ImageView Main_BorderPane_VBox_ImageView;

    @FXML
    private HBox Main_BorderPane_VBox_HBox;

    @FXML
    private ImageView Main_BorderPane_VBox_HBox_ImageView;

    @FXML
    private Label Main_BorderPane_VBox_HBox_Label;

    @FXML
    private ListView<String> Main_BorderPane_VBox_ListView;

    @FXML
    void Main_BorderPane_VBox_ListView_MouseClicked(MouseEvent event) throws ClassNotFoundException, IOException {
    	String item=Main_BorderPane_VBox_ListView.getSelectionModel().getSelectedItem();
    	String[] ListViewItems=ConfigHandle.getConfigStringData("ListViewItems","Option.cfg");
    	for (int i=0;i<ListViewItems.length;i++) {
			if(item.equals(ListViewItems[i])){
				switch (i) {
				case 0:
					System.out.println("0");
					break;
					
				case 1:
					System.out.println("1");
					break;
				
				case 2:
					System.out.println("2");
					break;
					
				case 3:
					System.out.println("3");
					break;
					
				default:
					System.out.println("-1");
					break;
				}
			}
		}
    }

}
