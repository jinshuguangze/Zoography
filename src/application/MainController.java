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

	public static final String MAIN_CFG = Main.MAIN_CFG;


    @FXML
    private VBox Left;

    @FXML
    private ListView<?> MainListView;

    @FXML
    private HBox Right;

    @FXML
    private StackPane HBox_Left;

    @FXML
    private BorderPane MainListViewItem1;

    @FXML
    private TilePane Item1CenterFrame;

    @FXML
    private ImageView Item1LeftFrame;

    @FXML
    private ImageView Item1RightFrame;

    @FXML
    private ImageView Item1BottomFrame;

    @FXML
    private StackPane Item1TopFrame;

    @FXML
    private BorderPane MainListViewItem2;

    @FXML
    private BorderPane MainListViewItem3;

    @FXML
    private BorderPane MainListViewItem4;

    @FXML
    private VBox HBox_Right;

    
	/**
	 * Event that left-clicking on the ListView 鼠标左击列表的触发器
	 * 
	 * @param event
	 *            Mouse input events
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	void MainListView_MouseClicked(MouseEvent event) throws ClassNotFoundException, IOException {

		List<Object> StackPaneItems = new ArrayList<>(Arrays.asList(HBox_Left.getChildren().toArray()));
		List<String> ListViewItems = new ArrayList<>(
				Arrays.asList(ConfigHandle.getConfigStringData(MAIN_CFG, "ListViewItems")));
		List<Integer> ListViewEventSequence = new ArrayList<>();
		int[] aIntArray = ConfigHandle.getConfigIntData(MAIN_CFG, "ListViewEventSequence");
		for (int i : aIntArray) {
			ListViewEventSequence.add(i);
		}

		HashMap<Integer, String> hashMap = new UsefulToolkit().autoItemCountConformity(StackPaneItems, ListViewItems,
				ListViewEventSequence);
		Object item = MainListView.getSelectionModel().getSelectedItem();		
		
		if (item != null) {
			for (int i = 0; i < hashMap.size(); i++) {
				// 过时方法!
				String urlString = ((ImageView) item).getImage().impl_getUrl();
				String imageName = urlString.substring(urlString.lastIndexOf("/") + 1, urlString.lastIndexOf("_"));

				if (imageName.equals(hashMap.get(i))) {
					for (int j = 0; j < hashMap.size(); j++) {
						BorderPane AllPane = (BorderPane) HBox_Left.lookup("#MainListViewItem" + (j + 1));
						AllPane.setVisible(false);
					}
					BorderPane ActivityPane = (BorderPane) HBox_Left.lookup("#MainListViewItem" + (i + 1));
					ActivityPane.setVisible(true);
				}
			}
		}
	}

}
