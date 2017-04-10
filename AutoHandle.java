package application;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.javafx.geom.AreaOp.AddOp;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AutoHandle {
		
	/**
	 * 
	 * @throws IOException
	 */
	public AutoHandle() throws IOException {
	}
	

	/**
	 * @return 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	protected void autoItemCountConformity() throws ClassNotFoundException, IOException {				
		Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		StackPane Main_StackPane2=(StackPane)root.lookup("#Main_StackPane2");
		
		Object[] StackPaneItems=Main_StackPane2.getChildren().toArray();
    	String[] ListViewItems=ConfigHandle.getConfigStringData("Option.cfg","ListViewItems");
    	int[] ListViewEventSequence=ConfigHandle.getConfigIntData("Option.cfg","ListViewEventSequence");
    	    	
    	int StackPaneCount=StackPaneItems.length;
    	int ItemsCount=ListViewItems.length;
    	int SequenceCount=ListViewEventSequence.length;

    	if(StackPaneCount>ItemsCount){
    		ConfigHandle.setConfigInitialization("Option.cfg","ListViewItems");
    	}
    	else if (StackPaneCount<ItemsCount) {
    		String[] modify=new String[StackPaneCount];
    		for(int i=0;i<StackPaneCount;i++){
    			Array.set(modify, i, ListViewItems[i]);    			
    		}
    		ListViewItems=modify;
		}
    	
    	if(StackPaneCount>SequenceCount){
    		ConfigHandle.setConfigInitialization("Option.cfg","ListViewEventSequence");
    	}
    	else if (StackPaneCount<SequenceCount) {
    		int[] modify=new int[StackPaneCount];
    		for(int i=0;i<StackPaneCount;i++){
    			Array.set(modify, i, ListViewEventSequence[i]);    			
    		}
    		ListViewEventSequence=modify;
		}
    	
    	HashMap hashMap=new HashMap();
    	/*Main_StackPane2.getChildren().forEach((node)->{
    		hashMap.put(ListViewEventSequence[i], ListViewItems[i]);
    	});*/
    	//return
    	
	}
}
