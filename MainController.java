package application;

import java.io.*;
import java.util.*;

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
    	
		List StackPaneItems=Arrays.asList(Main_StackPane2.getChildren().toArray());
		List ListViewItems=Arrays.asList(ConfigHandle.getConfigStringData("Option.cfg","ListViewItems"));
		//由于自动装箱出了点问题,暂时用string读取配置
		List ListViewEventSequence=Arrays.asList(ConfigHandle.getConfigStringData("Option.cfg","ListViewEventSequence"));
		
    	AutoHandle aHandle=new AutoHandle();
    	HashMap hashMap=aHandle.autoItemCountConformity(StackPaneItems,ListViewItems,ListViewEventSequence);
    	
    	Object item=Main_ListView1.getSelectionModel().getSelectedItem();

    	for (int i=0;i<hashMap.size();i++) {
    		//强制转化为字符串先
			if(item.equals(hashMap.get(""+i))){
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
