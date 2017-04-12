package application;

import java.io.*;
import java.util.*;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;


public class MainController {

    @FXML
    private BorderPane Main_BorderPane1;

    @FXML
    private VBox Main_VBox1;

    @FXML
    private ImageView Main_ImageView1;

    @FXML
    private HBox Main_HBox1;

    @FXML
    private StackPane Main_StackPane1;

    @FXML
    private ImageView Main_ImageView2;

    @FXML
    private ImageView Main_ImageView3;

    @FXML
    private Label Main_Label1;

    @FXML
    private ListView<?> Main_ListView1;

    @FXML
    private StackPane Main_StackPane2;

    @FXML
    private ScrollPane Main_ScrollPane1;

    @FXML
    private ScrollPane Main_ScrollPane2;

    @FXML
    private ScrollPane Main_ScrollPane3;

    @FXML
    private ScrollPane Main_ScrollPane4;

    /**
     * Event that left-clicking on the ListView
     * 鼠标左击列表的触发器
     * @param event Mouse input events
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @FXML
    void Main_ListView1_MouseClicked(MouseEvent event) throws ClassNotFoundException, IOException {
    	
		List<Object> StackPaneItems=new ArrayList<>(Arrays.asList(Main_StackPane2.getChildren().toArray()));
		List<String> ListViewItems=new ArrayList<>(Arrays.asList(ConfigHandle.getConfigStringData("Option.cfg","ListViewItems")));
		List<Integer> ListViewEventSequence=new ArrayList<>();
		int[] aIntArray=ConfigHandle.getConfigIntData("Option.cfg","ListViewEventSequence");
		for (int i:aIntArray) {
			ListViewEventSequence.add(i);	
		}
		
		HashMap hashMap=new AutoHandle().autoItemCountConformity(StackPaneItems,ListViewItems,ListViewEventSequence);   	
    	Object item=Main_ListView1.getSelectionModel().getSelectedItem();

    	for (int i=0;i<hashMap.size();i++) {
			if(item.equals(hashMap.get(i))){
				for(int j=0;j<hashMap.size();j++){
					ScrollPane AllPane=(ScrollPane)Main_StackPane2.lookup("#Main_ScrollPane"+(j+1));
					AllPane.setVisible(false);
				}
				ScrollPane ActivityPane=(ScrollPane)Main_StackPane2.lookup("#Main_ScrollPane"+(i+1));
				ActivityPane.setVisible(true);
			}
		}
    }

}
