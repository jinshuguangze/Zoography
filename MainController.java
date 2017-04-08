package application;

import java.io.*;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML
    void Main_ListView1_MouseClicked(MouseEvent event) throws ClassNotFoundException, IOException {
    	Object item=Main_ListView1.getSelectionModel().getSelectedItem();
    	
    	String[] ListViewItems=ConfigHandle.getConfigStringData("ListViewItems","Option.cfg");
    	int[] ListViewEventSequence=ConfigHandle.getConfigIntData("ListViewEventSequence","Option.cfg");
    	int a=ListViewItems.length;
    	int b=ListViewEventSequence.length;
    	

    	
    	//日后放进Main加载!
    	if(a>b){
    		int[] addonListViewEventSequence=new int[a];
    		for (int i=0;i<a;i++) {
    			addonListViewEventSequence[i]=i<b?ListViewEventSequence[i]:i;    			
			}
    		ListViewEventSequence=addonListViewEventSequence;
    	}
    	else if(a<b){
    		String[] addonListViewItems=new String[b];
    		for (int i=0;i<b;i++) {
    			addonListViewItems[i]=i<a?ListViewItems[i]:"暂无名称";    			
			}
    		ListViewItems=addonListViewItems;
    	}
    	       	    	
    	HashMap hashMap=new HashMap();
    	for (int i : ListViewEventSequence) {
			hashMap.put(ListViewEventSequence[i], ListViewItems[i]);
		}
    	
    	//增加健壮性
    	int paneCount=Main_StackPane2.getChildren().toArray().length;
    	
    	if(paneCount<a){
    		for(int i=0;i<a-paneCount;i++){
    			ScrollPane addonScrollPane=new ScrollPane();
    			Main_StackPane2.getChildren().add(addonScrollPane);
    			System.out.println(paneCount+i+1);
    			addonScrollPane.setId("Main_ScrollPane"+(paneCount+i+1));   			
    		}    		
    	}

    	for (int i=0;i<a;i++) {
			if(item.equals(hashMap.get(i))){
				for(int j=0;j<a;j++){
					ScrollPane AllPane=(ScrollPane)Main_StackPane2.lookup("#Main_ScrollPane"+(j+1));
					AllPane.setVisible(false);
				}
				ScrollPane ActivityPane=(ScrollPane)Main_StackPane2.lookup("#Main_ScrollPane"+(i+1));
				ActivityPane.setVisible(true);
			}
		}
    }

}
